/**
   Copyright 2012 John Cummens (aka Shadowmage, Shadowmage4513)
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
package shadowmage.ancient_warfare.common.tracker;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import shadowmage.ancient_warfare.common.config.Config;


public class AWGameData extends WorldSavedData
{
/**
 * http://www.minecraftforge.net/forum/index.php?topic=8520.0
 * https://github.com/diesieben07/Questology/blob/e9f46d8b3aa41b82f9021c892bca7ee558e7899a/source/demonmodders/questology/QuestologyWorldData.java
 */
static final String name = "AW_GAME_DATA";


public AWGameData()
  {
  super(name);
  }

public AWGameData(String name)
  {
  super(name);
  }

@Override
public void readFromNBT(NBTTagCompound tag)
  {
  if(tag.hasKey("playerData"))
    {
    Config.log("Loading Player Research Data from old format");
    ResearchTracker.instance().loadOldData(tag.getCompoundTag("playerData"));
    PlayerTracker.instance().readFromNBT(tag.getCompoundTag("playerData"));
    tag.removeTag("playerData");
    this.markDirty();
    }
  if(tag.hasKey("teamData"))
    {
    Config.log("Loading Team Data from old format");
    TeamTracker.instance().loadOldData(tag.getCompoundTag("teamData"));
    tag.removeTag("teamData");
    this.markDirty();
    }
  if(tag.hasKey("npcMap"))
    {
    GameDataTracker.instance().loadNpcMap(tag.getCompoundTag("npcMap"));
    }  
  }

@Override
public void writeToNBT(NBTTagCompound tag)
  {
  tag.setCompoundTag("npcMap", GameDataTracker.instance().getNpcMapTag());
  }

public static AWGameData get(World world) 
  {
  AWGameData data = (AWGameData)world.mapStorage.loadData(AWGameData.class, name);
  if(data==null) 
    {
    data = new AWGameData();
    world.mapStorage.setData(name, data);
    }
  return data;
  }

}
