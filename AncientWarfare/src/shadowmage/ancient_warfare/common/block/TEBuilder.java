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
package shadowmage.ancient_warfare.common.block;

import java.util.Iterator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import shadowmage.ancient_structures.common.template.build.StructureBuilderTicked;
import shadowmage.ancient_warfare.common.config.Config;
import shadowmage.ancient_warfare.common.utils.BlockPosition;

import com.google.common.collect.ImmutableSet;

public class TEBuilder extends TileEntity
{

private StructureBuilderTicked builder;

private boolean shouldRemove = false;

private Ticket tk = null;

public void setTicket(Ticket tk)
  {
  this.releaseTicket();
  this.tk = tk;
  if(this.tk!=null)
    {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setCompoundTag("pos", new BlockPosition(xCoord, yCoord, zCoord).writeToNBT(new NBTTagCompound()));
    tk.getModData().setCompoundTag("buildTE", tag);
    ImmutableSet tkCk = tk.getChunkList();
    Iterator<ChunkCoordIntPair> it = tkCk.iterator();
    while(it.hasNext())
      {
      ChunkCoordIntPair ccip = it.next();
      ForgeChunkManager.forceChunk(tk, ccip);
      }
    }
  }

public void removeBuilder()
  {
  this.invalidate();
  
  this.releaseTicket();
  }

public void releaseTicket()
  {
  if(this.tk!=null)
    {
    ForgeChunkManager.releaseTicket(tk);
    this.tk = null;
    }
  }

@Override
public void readFromNBT(NBTTagCompound par1nbtTagCompound)
  {
  super.readFromNBT(par1nbtTagCompound);
  if(par1nbtTagCompound.hasKey("builder"))
    {
    this.builder = new StructureBuilderTicked();
    this.builder.readFromNBT(par1nbtTagCompound.getCompoundTag("builder"));
    }
  else
    {
    this.shouldRemove = true;
    }
  }

@Override
public void writeToNBT(NBTTagCompound par1nbtTagCompound)
  {
  super.writeToNBT(par1nbtTagCompound);
  if(this.builder!=null)
    {
    NBTTagCompound tag = new NBTTagCompound();
    builder.writeToNBT(tag);
    par1nbtTagCompound.setCompoundTag("builder", tag);
    }
  }

@Override
public void updateEntity()
  {
  super.updateEntity();
  if(worldObj==null || this.worldObj.isRemote)
    {
    return;
    }
  if(this.shouldRemove)
    {    
    this.removeBuilder();
    this.worldObj.setBlock(xCoord, yCoord, zCoord, 0);
    return;
    }  
  if(builder==null)
    {
    Config.logError("Invalid builder in TE detected in builder block");
    /**
     * TODO remove this from world
     */
    return;
    }
  if(builder.getWorld()==null)
    {
    builder.setWorld(worldObj);
    }
  builder.tick();
  
  if(builder.isFinished())
    {
    this.removeBuilder();
    this.worldObj.setBlock(xCoord, yCoord, zCoord, 0);
    }
  }

@Override
public boolean canUpdate()
  {
  return true;
  }

public void setBuilder(StructureBuilderTicked builder)
  {
  if(this.builder==null)
    {
    this.builder = builder;
    }
  }


}
