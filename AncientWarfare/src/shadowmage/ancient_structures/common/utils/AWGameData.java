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
package shadowmage.ancient_structures.common.utils;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

public class AWGameData
{

private static HashMap<String, GameData> gameDatas = new HashMap<String, GameData>();
private static HashMap<String, Class<? extends GameData>> datasToLoad = new HashMap<String, Class<? extends GameData>>();



public static void addDataClass(String name, Class<? extends GameData> clz)
  {
  datasToLoad.put(name, clz);
  }

public static <T>T get(World world, String name, Class <T> saveDataClass) 
  {
  T data = (T) world.loadItemData(saveDataClass, name);
  if(data==null)
    {
    try
      {
      data = (T) saveDataClass.newInstance();
      world.mapStorage.setData(name, (GameData)data);
      } 
    catch (Exception e)
      {
      e.printStackTrace();
      return null;
      }
    }
  gameDatas.put(name, (GameData)data);
  return data;
  }

public static void markDirty(String name)
  {
  WorldSavedData data = gameDatas.get(name);
  markDirty(data);
  }

public static void markDirty(WorldSavedData data)
  {
  if(data!=null)
    {
    data.markDirty();    
    }
  }

public static void handleWorldLoad(World world)
  {
  WorldSavedData data;
  for(String name : datasToLoad.keySet())
    {
    data = gameDatas.get(name);
    if(data==null)
      {
      data = get(world, name, datasToLoad.get(name));
      }
    }
  }

public static void handleWorldSave(World world)
  {
  
  }

public static void resetTrackedData()
  {
  gameDatas.clear();
  }

public static void handlePacketData(String name, NBTTagCompound data)
  {

  }

@ForgeSubscribe
public void onWorldLoad(WorldEvent.Load evt)
  {
  if(evt.world instanceof WorldServer)
    {
    handleWorldLoad(evt.world);    
    }
  }

}
