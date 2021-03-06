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
package shadowmage.ancient_warfare.common.civics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import shadowmage.ancient_warfare.common.block.BlockLoader;
import shadowmage.ancient_warfare.common.civics.types.Civic;
import shadowmage.ancient_warfare.common.interfaces.IEntityContainerSynch;
import shadowmage.ancient_warfare.common.inventory.AWInventoryBase;
import shadowmage.ancient_warfare.common.inventory.AWInventoryBasic;
import shadowmage.ancient_warfare.common.inventory.AWInventoryMapped;
import shadowmage.ancient_warfare.common.network.GUIHandler;
import shadowmage.ancient_warfare.common.tracker.TeamTracker;
import shadowmage.ancient_warfare.common.utils.BlockPosition;
import shadowmage.ancient_warfare.common.utils.InventoryTools;

public class TECivicWarehouse extends TECivic implements IEntityContainerSynch
{

int storageSize = 0;

public AWInventoryBase inputSlots = new AWInventoryBasic(9);
public AWInventoryBase withdrawSlots = new AWInventoryBasic(9);
List<BlockPosition> storageBlocks = new ArrayList<BlockPosition>();

public TECivicWarehouse()
  {
  this.renderBounds = true;
  }

@Override
public IInventory[] getInventoryToDropOnBreak()
  {
  return new IInventory[]{inventory, inputSlots, withdrawSlots};
  }

public void onPlaced()
  {
  int id;
  int meta;
  for(int y = this.minY; y <=this.maxY; y++)
    {
    for(int z = this.minZ ;z <=this.maxZ ; z++)
      {
      for(int x = this.minX; x <=this.maxX; x++)
        {
        id = worldObj.getBlockId(x, y, z);
        if(id==BlockLoader.warehouseStorage.blockID)
          {
          meta = worldObj.getBlockMetadata(x, y, z);
          this.addWareHouseBlock(x, y, z, BlockWarehouseStorage.getStorageSizeFromMeta(meta));
          }
        }
      }
    }
  }

@Override
public void setCivic(Civic civ)
  {
  this.civic = civ;
  if(inventory==null)
    {
    inventory = new AWInventoryMapped(this.storageSize);
    }
  ((AWInventoryMapped)this.inventory).setInventorySize(this.storageSize);
  this.setupSidedInventoryIndices(civ);
  }

@Override
protected void onCivicUpdate()
  {
  for(int i = 0; i < this.inputSlots.getSizeInventory(); i++)
    {
    if(this.inputSlots.getStackInSlot(i)!=null)
      {
      this.inputSlots.setInventorySlotContents(i, this.inventory.tryMergeItem(this.inputSlots.getStackInSlot(i)));
      }
    }
  super.onCivicUpdate();
  }

@Override
public boolean onInteract(World world, EntityPlayer player)
  {
  if(!world.isRemote)
    {
    GUIHandler.instance().openGUI(GUIHandler.CIVIC_WAREHOUSE, player, world, xCoord, yCoord, zCoord);
    }
  return true;
  }

public void addWareHouseBlock(int x, int y, int z, int size)
  {  
  boolean found = false;
  for(BlockPosition p : this.storageBlocks)
    {
    if(p.x==x && p.y==y && p.z==z)
      {
      found = true;
      break;
      }
    }
  if(!found)
    {
    this.storageSize += size;
    ((AWInventoryMapped)this.inventory).setInventorySize(this.storageSize);
    this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    this.storageBlocks.add(new BlockPosition(x,y,z));
    }
  }

public void removeWarehouseBlock(int x, int y, int z, int size)
  {
  Iterator<BlockPosition> it = this.storageBlocks.iterator();
  BlockPosition p;
  while(it.hasNext())
    {
    p = it.next();
    if(p.x==x && p.y==y && p.z==z)
      {
      it.remove();
      for(int i = this.inventory.getSizeInventory() - size; i < this.getSizeInventory(); i++)
        {
        InventoryTools.dropItemInWorld(worldObj, inventory.getStackInSlotOnClosing(i), xCoord+0.5d, yCoord+1.d, zCoord+0.5d);
        }
      this.storageSize-= size;
      if(this.storageSize<0)
        {
        this.storageSize = 0;
        }
      ((AWInventoryMapped)this.inventory).setInventorySize(this.storageSize);
      this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
      break;
      }
    }
  }

@Override
protected void readCivicDataFromNBT(NBTTagCompound tag)
  {
  if(tag.hasKey("warehouseSize"))
    {    
    storageSize = tag.getInteger("warehouseSize");
    }
  if(tag.hasKey("inputInventory"))
    {
    this.inputSlots.readFromNBT(tag.getCompoundTag("inputInventory"));
    }
  if(tag.hasKey("withdrawInventory"))
    {
    this.withdrawSlots.readFromNBT(tag.getCompoundTag("withdrawInventory"));
    }
  if(tag.hasKey("blockList"))
    {
    NBTTagList list = tag.getTagList("blockList");
    for(int i = 0; i <list.tagCount(); i++)
      {
      this.storageBlocks.add(new BlockPosition((NBTTagCompound) list.tagAt(i)));
      }
    }
  super.readCivicDataFromNBT(tag);
  }

@Override
public void writeToNBT(NBTTagCompound tag)
  {
  super.writeToNBT(tag);
  tag.setInteger("warehouseSize", storageSize);
  tag.setCompoundTag("inputInventory", this.inputSlots.getNBTTag());
  tag.setCompoundTag("withdrawInventory", this.withdrawSlots.getNBTTag());
  NBTTagList list = new NBTTagList();
  for(int i = 0; i < this.storageBlocks.size(); i++)
    {
    list.appendTag(this.storageBlocks.get(i).writeToNBT(new NBTTagCompound()));
    }
  tag.setTag("blockList", list);
  }

@Override
public Packet getDescriptionPacket()
  {
  Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
  packet.data.setInteger("warehouseSize", this.storageSize);
  return packet;
  }

@Override
public void handleClientInput(NBTTagCompound tag)
  {
  // TODO Auto-generated method stub  
  }

@Override
public void addPlayer(EntityPlayer player)
  {
  this.viewingPlayers.add(player);  
  }

@Override
public void removePlayer(EntityPlayer player)
  {  
  this.viewingPlayers.remove(player);
  }

protected Set<EntityPlayer> viewingPlayers = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());

@Override
public boolean canInteract(EntityPlayer player)
  {
  return TeamTracker.instance().getTeamForPlayer(player)==this.teamNum;
  }


}
