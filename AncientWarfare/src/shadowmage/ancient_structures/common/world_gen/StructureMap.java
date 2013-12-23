/**
   Copyright 2012-2013 John Cummens (aka Shadowmage, Shadowmage4513)
   This software is distributed under the terms of the GNU General Public License.
   Please see COPYING for precise license information.

   This file is part of Ancient Warfare.

   Ancient Warfare is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Ancient Warfare is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Ancient Warfare.  If not, see <http://www.gnu.org/licenses/>.
 */
package shadowmage.ancient_structures.common.world_gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import shadowmage.ancient_framework.common.config.AWLog;
import shadowmage.ancient_framework.common.utils.NBTTools;
import shadowmage.ancient_structures.common.template.StructureTemplate;

public class StructureMap extends WorldSavedData
{

private StructureDimensionMap map;

public StructureMap(String name)
  {
  super(name);
  map = new StructureDimensionMap();
  }

public StructureMap()
  {
  super("AWStructureMap");
  map = new StructureDimensionMap();
  }

@Override
public void readFromNBT(NBTTagCompound nbttagcompound)
  {
  AWLog.logDebug("reading structure map from nbt...");
  NBTTagCompound mapTag = nbttagcompound.getCompoundTag("map");
  map.readFromNBT(mapTag);
  }

@Override
public void writeToNBT(NBTTagCompound nbttagcompound)
  {
  AWLog.logDebug("writing structure map to nbt...");
  NBTTagCompound mapTag = new NBTTagCompound();
  map.writeToNBT(mapTag);
  nbttagcompound.setTag("map", mapTag);
  List<String> lines = new ArrayList<String>();
  NBTTools.writeNBTToLines(mapTag, lines);
  for(String line : lines)
    {
    AWLog.logDebug(line);
    }
  }

public Collection<StructureEntry> getEntriesNear(World world, int worldX, int worldZ, int chunkRadius, Collection<StructureEntry> list)
  {
  int cx = worldX/16;
  int cz = worldZ/16;
  return map.getEntriesNear(world.provider.dimensionId, cx, cz, chunkRadius, list);
  }

public void setGeneratedAt(World world, int worldX, int worldY, int worldZ, int face, StructureTemplate structure)
  {
  int cx = worldX/16;
  int cz = worldZ/16;
  StructureEntry entry = new StructureEntry(worldX, worldY, worldZ, face, structure);  
  map.setGeneratedAt(world.provider.dimensionId, cx, cz, entry, structure.getValidationSettings().isUnique());
  this.markDirty();
  }

private class StructureDimensionMap
{
private HashMap<Integer, StructureWorldMap> mapsByDimension = new HashMap<Integer, StructureWorldMap>();
Set<String> generatedUniques = new HashSet<String>();

public Collection<StructureEntry> getEntriesNear(int dimension, int chunkX, int chunkZ, int chunkRadius, Collection<StructureEntry> list)
  {
  if(mapsByDimension.containsKey(dimension))
    {
    return mapsByDimension.get(dimension).getEntriesNear(chunkX, chunkZ, chunkRadius, list);
    }
  return Collections.emptyList();
  }

public void setGeneratedAt(int dimension, int chunkX, int chunkZ, StructureEntry entry, boolean unique)
  {
  if(!this.mapsByDimension.containsKey(dimension))
    {
    this.mapsByDimension.put(dimension, new StructureWorldMap());
    }
  this.mapsByDimension.get(dimension).setGeneratedAt(chunkX, chunkZ, entry);
  if(unique)
    {
    generatedUniques.add(entry.name);
    }
  }

public void readFromNBT(NBTTagCompound nbttagcompound)
  {
  NBTTagList uniquesList = nbttagcompound.getTagList("uniques");
  NBTTagList dimensionList = nbttagcompound.getTagList("dimensions");
  
  NBTTagCompound dimensionTag;
  int dim;
  for(int i = 0; i < dimensionList.tagCount(); i++)
    {
    dimensionTag = (NBTTagCompound) dimensionList.tagAt(i);
    dim = dimensionTag.getInteger("dim");     
    if(!this.mapsByDimension.containsKey(dim))
      {
      this.mapsByDimension.put(dim, new StructureWorldMap());
      }
    this.mapsByDimension.get(dim).readFromNBT(dimensionTag.getCompoundTag("data"));
    }
  
  NBTTagString uniqueTag;
  for(int i = 0; i < uniquesList.tagCount(); i++)
    {
    uniqueTag = (NBTTagString) uniquesList.tagAt(i);
    generatedUniques.add(uniqueTag.data);
    }
  }

public void writeToNBT(NBTTagCompound nbttagcompound)
  {
  NBTTagList dimensionsList = new NBTTagList();
  NBTTagList uniquesList = new NBTTagList();
  NBTTagCompound dimensionTag;
  NBTTagCompound dimensionData;
  for(Integer dim : this.mapsByDimension.keySet())
    {
    dimensionTag = new NBTTagCompound();
    dimensionData = new NBTTagCompound();
    dimensionTag.setInteger("dim", dim);
    mapsByDimension.get(dim).writeToNBT(dimensionData);
    dimensionTag.setTag("data", dimensionData);        
    dimensionsList.appendTag(dimensionTag);
    }  
  
  for(String name : this.generatedUniques)
    {
    uniquesList.appendTag(new NBTTagString("name", name));
    }
  nbttagcompound.setTag("dimensions", dimensionsList);
  nbttagcompound.setTag("uniques", uniquesList);
  }
}//end structure dimension map



private class StructureWorldMap
{
private HashMap<Integer, HashMap<Integer, StructureEntry>> worldMap = new HashMap<Integer, HashMap<Integer, StructureEntry>>();

public Collection<StructureEntry> getEntriesNear(int chunkX, int chunkZ, int chunkRadius, Collection<StructureEntry> list)
  {
  StructureEntry entry;
  for(int x = chunkX-chunkRadius; x<=chunkX+chunkRadius; x++)
    {
    if(worldMap.containsKey(x))
      {
      for(int z = chunkZ-chunkRadius; z<=chunkZ+chunkRadius; z++)
        {
        entry = worldMap.get(x).get(z);
        if(entry!=null)
          {
          list.add(entry);
          }
        }
      }
    }
  return list;
  }

public void setGeneratedAt(int chunkX, int chunkZ, StructureEntry entry)
  {
  if(!this.worldMap.containsKey(chunkX))
    {
    this.worldMap.put(chunkX, new HashMap<Integer, StructureEntry>());
    }
  this.worldMap.get(chunkX).put(chunkZ, entry);
  }

public void readFromNBT(NBTTagCompound nbttagcompound)
  {
  NBTTagList entryList = nbttagcompound.getTagList("entries");
  StructureEntry entry;
  NBTTagCompound entryTag;
  int x, z;
  for(int i = 0; i < entryList.tagCount(); i++)
    {
    entryTag = (NBTTagCompound) entryList.tagAt(i);
    x = entryTag.getInteger("x");
    z = entryTag.getInteger("z");
    entry = new StructureEntry();
    entry.readFromNBT(entryTag);
    if(!this.worldMap.containsKey(x))
      {
      this.worldMap.put(x, new HashMap<Integer, StructureEntry>());
      }
    this.worldMap.get(x).put(z, entry);
    }
  }

public void writeToNBT(NBTTagCompound nbttagcompound)
  {
  NBTTagList entryList = new NBTTagList();
  NBTTagCompound entryTag;
  for(Integer x : this.worldMap.keySet())
    {
    for(Integer z : this.worldMap.get(x).keySet())
      {
      entryTag = new NBTTagCompound();
      entryTag.setInteger("x", x);
      entryTag.setInteger("z", z);
      this.worldMap.get(x).get(z).writeToNBT(entryTag);
      entryList.appendTag(entryTag);
      }
    }
  nbttagcompound.setTag("entries", entryList);
  }
}//end structure X Map

private StructureEntry getEntryFor(int x, int y, int z, int face, StructureTemplate template)
  {
  StructureEntry entry = new StructureEntry(x, y, z, face, template);  
  return entry;
  }
}
