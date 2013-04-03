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
package shadowmage.ancient_warfare.common.soldiers.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import shadowmage.ancient_warfare.common.soldiers.NpcAI;
import shadowmage.ancient_warfare.common.soldiers.NpcBase;
import shadowmage.ancient_warfare.common.utils.Trig;
import shadowmage.meim.common.config.Config;

public class AIMoveToTarget extends NpcAI
{

float prevDistance;
float distance;

int delayTicksMax = 10;
int delayTicks = 0;
int stuckTicks = 0;

/**
 * @param npc
 */
public AIMoveToTarget(NpcBase npc)
  {
  super(npc);  
  this.successTicks = 20;
  this.failureTicks = 20;
  this.taskType = MOVE_TO;
  this.taskName = "MoveToTarget";
  }

@Override
public int exclusiveTasks()
  {  
  return ATTACK +  REPAIR + HEAL + HARVEST; //action tasks
  }

@Override
public void onAiStarted()
  {
  stuckTicks = 0;
  delayTicks = 0;
  }

@Override
public void onTick()
  {
  if(npc.getTarget()==null)
    {
    this.finished = true;
    this.success = true;
    return;
    }
  float bX = npc.getTarget().posX();
  float bY = npc.getTarget().posY();
  float bZ = npc.getTarget().posZ();
  if(npc.getTarget().isEntityEntry)
    {
    Entity ent = npc.getTarget().getEntity();
    if(ent!=null && !ent.onGround)
      {
      Config.logDebug("setting new target height for flying target");
      int x = MathHelper.floor_float(bX);
      int y = MathHelper.floor_float(bY);
      int z = MathHelper.floor_float(bZ);
      if(npc.worldObj.getBlockId(x, y, z)==Block.ladder.blockID)
        {
        Config.logDebug("target on ladder, not adjusting");
        }
      else
        {
        while(y>1)
          {
          if(npc.worldObj.isBlockNormalCube(x, y, z))
            {
            break;
            }
          y--;
          }
        bY = y+1;
        }      
      }
    }
//  Config.logDebug("targetPos: "+bX+","+bY+","+bZ);
  this.prevDistance = this.distance;
  this.distance = (float) npc.getDistance(bX, bY, bZ);  
//  Config.logDebug("calc targetDist: "+npc.targetHelper.getAttackDistance(npc.getTarget()));
  if(distance < npc.targetHelper.getAttackDistance(npc.getTarget()))
    {
    this.finished = true;
    this.success = true;
    if(npc.getTargetType().equals(TARGET_WANDER))
      {
      npc.setTargetAW(null);
      }
//    Config.logDebug("MoveToTarget finished");
    return;
    }  
  delayTicks--;
  if(delayTicks>0)
    {
    return;
    }
  delayTicks = delayTicksMax;
  if(Trig.getAbsDiff(distance, prevDistance)<0.05f)
    {
    stuckTicks++;
//    Config.logDebug("NPC could not move, or did not move between AIMoveToTarget ticks");
    if(stuckTicks>10)
      {
      npc.setTargetAW(null);
      stuckTicks = 0;
      }
    }
//  if(distance>12)
//    {
//    float angle = Trig.getYawTowardsTarget(npc.posX, npc.posZ, bX, bZ);
//    bX = (float)npc.posX - Trig.sinDegrees(angle-90)*12;
//    bZ = (float)npc.posZ + Trig.cosDegrees(angle-90)*12;
//    Config.logDebug("adjustedMovePos: "+bX+","+bY+","+bZ);
//    }
  npc.nav.setMoveTo(MathHelper.floor_float(bX), MathHelper.floor_float(bY), MathHelper.floor_float(bZ));
//  if(!npc.getNavigator().tryMoveToXYZ(bX, bY, bZ, npc.getAIMoveSpeed()))
//    {
//    this.finished = true;
//    this.success = false;
//    }
//  Config.logDebug("setting moveToTarget: ");
  }


}
