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
package shadowmage.ancient_warfare.common.aw_structure.data.swap_groups;

import net.minecraft.block.Block;
import shadowmage.ancient_warfare.common.aw_structure.data.BlockData;
import shadowmage.ancient_warfare.common.aw_structure.data.BlockSwapGroup;

public class BlockSwapGroupCobble extends BlockSwapGroup
{


public BlockSwapGroupCobble()
  {
  this.baseID = new BlockData(Block.cobblestone.blockID, 0);
  this.alternateIDs.add(new BlockData(Block.cobblestoneMossy.blockID,0));
  
  this.stairID = Block.stairCompactCobblestone.blockID;
  
  this.slabID = Block.stoneSingleSlab.blockID;
  this.slabMeta = 3;
  
  this.slabDoubleID = Block.stoneDoubleSlab.blockID;
  this.slabDoubleMeta = 3;
  
  }

}
