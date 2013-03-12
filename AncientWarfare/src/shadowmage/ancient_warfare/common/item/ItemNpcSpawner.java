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
package shadowmage.ancient_warfare.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import shadowmage.ancient_warfare.common.config.Config;
import shadowmage.ancient_warfare.common.registry.NpcRegistry;
import shadowmage.ancient_warfare.common.utils.BlockPosition;
import shadowmage.ancient_warfare.common.utils.BlockTools;

public class ItemNpcSpawner extends AWItemClickable
{
/**
 * @param itemID
 * @param hasSubTypes
 */
public ItemNpcSpawner(int itemID)
  {
  super(itemID, true);
  this.setCreativeTab(CreativeTabAWVehicle.instance());
  this.setTextureFile("/shadowmage/ancient_warfare/resources/item/npcs.png");
  this.setItemName("awNpcItem");
  }

@Override
public String getItemNameIS(ItemStack par1ItemStack)
  {
  return "Npc" + String.valueOf(par1ItemStack.getItemDamage()); 
  }

@Override
public boolean onUsedFinal(World world, EntityPlayer player, ItemStack stack,    BlockPosition hit, int side)
  {
  if(hit==null || world.isRemote || stack == null)
    {
    return false;
    }
  if(stack.hasTagCompound() && stack.getTagCompound().hasKey("AWNpcSpawner"))
    {
    int level = stack.getTagCompound().getCompoundTag("AWNpcSpawner").getInteger("lev");    
    hit = BlockTools.offsetForSide(hit, side);      
//    VehicleBase vehicle = VehicleRegistry.instance().getVehicleForType(world, stack.getItemDamage(), level);
//    vehicle.setPosition(hit.x+0.5d, hit.y, hit.z+0.5d);
//    vehicle.prevRotationYaw = vehicle.rotationYaw = player.rotationYaw;
//    world.spawnEntityInWorld(vehicle);      
//    if(!player.capabilities.isCreativeMode)
//      {
//      stack.stackSize--;
//      if(stack.stackSize<=0)
//        {
//        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);    
//        }
//      }
    return true;
    }
  Config.logError("Npc spawner item was missing NBT data, something may have corrupted this item");
  return false;
  }

@Override
public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
  {
  super.addInformation(stack, par2EntityPlayer, par3List, par4); 
  if(stack!=null)
    {
    if(stack.hasTagCompound() && stack.getTagCompound().hasKey("AWNpcSpawner"))
      {
      par3List.add("NPC Rank: "+stack.getTagCompound().getCompoundTag("AWNpcSpawner").getInteger("lev"));
      }
    }  
  }

@Override
public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
  {
  List displayStacks = NpcRegistry.instance().getCreativeDisplayItems();
  par3List.addAll(displayStacks);
  }
}