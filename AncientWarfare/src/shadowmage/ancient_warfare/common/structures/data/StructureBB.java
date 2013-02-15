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
package shadowmage.ancient_warfare.common.structures.data;

import shadowmage.ancient_warfare.common.utils.BlockPosition;
import shadowmage.ancient_warfare.common.utils.BlockTools;

public class StructureBB
{

public BlockPosition pos1;
public BlockPosition pos2;

public StructureBB(BlockPosition pos1, BlockPosition pos2)
  {
  this.pos1 = BlockTools.getMin(pos1, pos2);
  this.pos2 = BlockTools.getMax(pos1, pos2);
  }

@Override
public String toString()
  {
  return pos1.toString() + " : " +pos2.toString();
  }

/**
 * does the input bb share any blocks with this bounding box?
 * @param bb
 * @return
 */
public boolean collidesWith(StructureBB bb)
  {
  if(pos2.x < bb.pos1.x)
    {
    return false;
    }
  if(pos2.y < bb.pos1.y)
    {
    return false;
    }
  if(pos2.z < bb.pos1.z)
    {
    return false;
    }
  if(pos1.x > bb.pos2.x)
    {
    return false;
    }
  if(pos1.y > bb.pos2.y)
    {
    return false;
    }
  if(pos1.z > bb.pos2.z)
    {
    return false;
    }  
  return true;
  }
}
