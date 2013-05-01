/**
   Copyright 2012 John Cummens (aka Shadowmage, Shadowmage4513)
   This software is distributed under the terms of the GNU General Public Licence.
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
package shadowmage.ancient_warfare.common.civics.worksite.te.farm;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import shadowmage.ancient_warfare.common.civics.TECivic;
import shadowmage.ancient_warfare.common.civics.worksite.WorkPoint;
import shadowmage.ancient_warfare.common.config.Config;
import shadowmage.ancient_warfare.common.npcs.NpcBase;
import shadowmage.ancient_warfare.common.targeting.TargetType;
import shadowmage.ancient_warfare.common.utils.InventoryTools;

public class TEFarmReed extends TECivic
{


ItemStack plantableFilter = new ItemStack(Item.reed);
int mainBlockID = Block.reed.blockID;

/**
 * 
 */
public TEFarmReed()
  {
  }

@Override
public void updateWorkPoints()
  {
  super.updateWorkPoints();
  for(int x = this.minX; x<=this.maxX; x++)
    {
    for(int y = this.minY; y<=this.maxY; y++)
      {
      for(int z = this.minZ; z<=this.maxZ; z++)
        {        
        this.updateOrAddWorkPoint(x, y, z);
        }
      }
    }
  }

protected boolean isWater(int x, int y, int z)
  {
  int id = worldObj.getBlockId(x, y, z);
  return id==Block.waterMoving.blockID || id==Block.waterStill.blockID;
  }

protected void updateOrAddWorkPoint(int x, int y, int z)
  {  
  TargetType t = TargetType.NONE;
  boolean addPoint = false;
  boolean foundWater =false;
  int id = worldObj.getBlockId(x, y, z);
  int id2 = worldObj.getBlockId(x, y-1, z);
  if(id==Block.reed.blockID)
    {
    if(id2==Block.reed.blockID && worldObj.getBlockId(x, y-2, z)==Block.reed.blockID)
      {
      addPoint = true;
      t = TargetType.FARM_HARVEST;
      }    
    }
  else if(id==0 && id2==Block.dirt.blockID || id2==Block.grass.blockID || id2==Block.sand.blockID)
    {
    if(isWater(x-1, y-1, z) || isWater(x+1,y-1,z) || isWater(x,y-1,z-1) || isWater(x,y-1,z+1))
      {
      t = TargetType.FARM_PLANT;
      }       
    }
  if(addPoint)
    {
    this.workPoints.add(new WorkPoint(this, x,y,z, 1, t));
    }  
  }

@Override
public void onWorkFinished(NpcBase npc, WorkPoint point)
  {
  if(npc==null || point==null)
    {
    return;
    }
  if(point.hasWork(worldObj))
    {
    if(point.getTargetType()==TargetType.FARM_HARVEST)
      {
      Config.logDebug("harvesting crops!!");
      List<ItemStack> blockDrops = Block.crops.getBlockDropped(npc.worldObj, point.x(), point.y(), point.z(), 7, 0);
      worldObj.setBlockToAir(point.x(), point.y(), point.z());
      for(ItemStack item : blockDrops)
        {
        if(item==null){continue;}
        if(InventoryTools.doItemsMatch(item, plantableFilter) && inventory.canHoldItem(plantableFilter, item.stackSize))
          {
          item = inventory.tryMergeItem(item);
          if(item!=null)
            {
            item = npc.inventory.tryMergeItem(item);
            if(item!=null)
              {
              InventoryTools.dropItemInWorld(worldObj, item, xCoord+0.5d, yCoord, zCoord+0.5d);
              }
            }
          }
        else
          {
          item = npc.inventory.tryMergeItem(item);
          if(item!=null)
            {
            InventoryTools.dropItemInWorld(worldObj, item, xCoord+0.5d, yCoord, zCoord+0.5d);
            }
          }
        }
      }
    else if(point.getTargetType()==TargetType.FARM_PLANT)
      {
      if(inventory.containsAtLeast(plantableFilter, 1))
        {
        inventory.tryRemoveItems(plantableFilter, 1);
        worldObj.setBlock(point.x(), point.y(), point.z(), mainBlockID, 0, 3);
        }
      else
        {
        Config.logDebug("had plant job but no plantables!!");
        }
      }
    }
  super.onWorkFinished(npc, point);
  }

}
