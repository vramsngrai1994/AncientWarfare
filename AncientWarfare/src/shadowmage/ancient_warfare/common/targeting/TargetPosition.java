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
package shadowmage.ancient_warfare.common.targeting;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import shadowmage.ancient_warfare.common.interfaces.INBTTaggable;
import shadowmage.ancient_warfare.common.interfaces.ITargetEntry;

public class TargetPosition implements ITargetEntry, INBTTaggable
{

protected TargetType type;
protected int x;
protected int y; 
protected int z;
protected int side = -1;

public TargetPosition(NBTTagCompound tag)
  {
  this.readFromNBT(tag);
  }

public TargetPosition(TargetType type)
  {
  this.type = type;
  }

public TargetPosition(int x, int y, int z, TargetType type)
  {
  this(type);
  this.x = x;
  this.y = y;
  this.z = z;
  }

public TargetPosition(int x, int y, int z, int side, TargetType type)
  {
  this(x,y,z,type);
  this.side = side;
  }

@Override
public int floorX()
  {
  return x;
  }

@Override
public int floorY()
  {
  return y;
  }

@Override
public int floorZ()
  {
  return z;
  }

@Override
public float posX()
  {
  return x+0.5f;
  }

@Override
public float posY()
  {
  return y;
  }

@Override
public float posZ()
  {
  return z+0.5f;
  }

@Override
public Entity getEntity()
  {
  return null;
  }

@Override
public TileEntity getTileEntity()
  {
  return null;
  }

@Override
public TargetType getTargetType()
  {
  return type;
  }


@Override
public NBTTagCompound getNBTTag()
  {
  NBTTagCompound tag = new NBTTagCompound();
  tag.setInteger("t", this.type.ordinal());
  tag.setIntArray("pos", new int[]{x,y,z});
  tag.setInteger("s", side);
  return tag;
  }

@Override
public void readFromNBT(NBTTagCompound tag)
  {
  this.type = TargetType.values()[tag.getInteger("t")];
  int[] pos = tag.getIntArray("pos");
  if(tag.hasKey("s"))
    {
    this.side = tag.getInteger("s");
    }
  this.x = pos[0];
  this.y = pos[1];
  this.z = pos[2];
  }

@Override
public boolean isEntityEntry()
  {
  return false;
  }

@Override
public boolean isTileEntry()
  {
  return false;
  }

@Override
public String toString()
  {
  return String.format("WayPoint: %d, %d, %d :: %s", x,y,z,type);
  }

public static TargetPosition getNewTarget(Entity ent, TargetType type)
  {
  return new TargetPositionEntity(ent, type);
  }

public static TargetPosition getNewTarget(TileEntity ent, TargetType type)
  {
  return new TargetPositionTile(ent, type);
  }

public static TargetPosition getNewTarget(int x, int y, int z, TargetType type)
  {
  return new TargetPosition(x,y,z, type);
  }

}
