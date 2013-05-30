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
package shadowmage.ancient_warfare.common.crafting;

import shadowmage.ancient_warfare.common.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TEAWResearch extends TEAWCrafting
{

/**
 * 
 */
public TEAWResearch()
  {
  this.modelID = 0;
  }

@Override
public void onBlockClicked(EntityPlayer player)
  {
  Config.logDebug("TEResearch block clicked");
  super.onBlockClicked(player);
  }

@Override
public void readDescriptionPacket(NBTTagCompound tag)
  {
  // TODO Auto-generated method stub  
  }

@Override
public void writeDescriptionData(NBTTagCompound tag)
  {
  
  }

@Override
public void writeExtraNBT(NBTTagCompound tag)
  {
  // TODO Auto-generated method stub  
  }

@Override
public void readExtraNBT(NBTTagCompound tag)
  {
  // TODO Auto-generated method stub
  
  }


}
