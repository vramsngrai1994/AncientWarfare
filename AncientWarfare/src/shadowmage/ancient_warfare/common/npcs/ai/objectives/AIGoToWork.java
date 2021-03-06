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
package shadowmage.ancient_warfare.common.npcs.ai.objectives;

import net.minecraft.tileentity.TileEntity;
import shadowmage.ancient_warfare.common.interfaces.ITEWorkSite;
import shadowmage.ancient_warfare.common.interfaces.ITargetEntry;
import shadowmage.ancient_warfare.common.npcs.NpcBase;
import shadowmage.ancient_warfare.common.npcs.ai.NpcAIObjective;
import shadowmage.ancient_warfare.common.npcs.ai.NpcAITask;
import shadowmage.ancient_warfare.common.npcs.ai.tasks.AIDoWork;
import shadowmage.ancient_warfare.common.npcs.ai.tasks.AIMoveToTarget;
import shadowmage.ancient_warfare.common.npcs.waypoints.WayPoint;
import shadowmage.ancient_warfare.common.targeting.TargetType;

public class AIGoToWork extends NpcAIObjective
{
/**
 * @param npc
 * @param maxPriority
 */
public AIGoToWork(NpcBase npc, int maxPriority)
  {
  super(npc, maxPriority);
//  this.maxCooldownticks = 40;
  }

@Override
public void addTasks()
  {
  this.aiTasks.add(new AIMoveToTarget(npc, 1.8f, false));
  this.aiTasks.add(new AIDoWork(npc, this));
  }

@Override
public byte getObjectiveNum()
  {
  return work;
  }

@Override
public void updatePriority()
  {
  this.currentPriority =  0;
  if(this.findWorkSite())
    {
    this.currentPriority = this.maxPriority;
    }  
  }

protected boolean isWorkFinished()
  {
  if(npc.wayNav.getWorkSiteTile()!=null)
    {
    return !npc.wayNav.getWorkSiteTile().hasWork();
    }
  return true;
  }

protected boolean findWorkSite()
  {
  WayPoint p = npc.wayNav.getWorkSite();
  if(p!=null && p.isTargetLoaded(npc.worldObj) && isValidWorkSite(p.getTileEntity(npc.worldObj)))
    {
    npc.wayNav.setWorkSiteTile((ITEWorkSite)p.getTileEntity(npc.worldObj));
    return true;
    }
  npc.wayNav.setWorkSiteTile(null);
  ITargetEntry entry;
  TileEntity te;
  while(npc.targetHelper.hasTargetsOfType(TargetType.WORK))
    {
    entry = npc.targetHelper.getHighestAggroTarget(TargetType.WORK);
    te = npc.worldObj.getBlockTileEntity(entry.floorX(), entry.floorY(), entry.floorZ());
    if(entry.isTargetLoaded(npc.worldObj) && isValidWorkSite(te))
      {
      npc.wayNav.setWorkSite(new WayPoint(entry.floorX(), entry.floorY(), entry.floorZ(), entry.getTargetType()));
      npc.wayNav.setWorkSiteTile((ITEWorkSite)te);
      return true;
      }
    else
      {
      npc.targetHelper.removeTarget(entry);
      }   
    }  
  return false;
  }

protected boolean isValidWorkSite(TileEntity te)
  {
  if(te instanceof ITEWorkSite)
    {
    ITEWorkSite tec = (ITEWorkSite)te;
    if(tec.isWorkSite() && tec.hasWork() && tec.canHaveMoreWorkers(npc) && npc.npcType.getWorkTypes(npc.rank).contains(tec.getWorkType()))
      {
      return true;
      }
    }  
  return false;
  }

@Override
public void onRunningTick()
  { 
  setMoveToWork();
  if(isWorkFinished())
    {
    if(!findWorkSite())
      {
      this.setFinished();
      }
    this.updateWorkSiteWorkerStatus();
    }
  if(npc.getDistanceFromTarget(npc.getTarget())>3.5)
    {
    npc.setActionTicksToMax();
    }
  }

protected void updateWorkSiteWorkerStatus()
  {
  ITEWorkSite te = npc.wayNav.getWorkSiteTile();
  if(te!=null)
    {
    te.addWorker(npc);
    }
  }

@Override
public void onObjectiveStart()
  {
  if(npc.wayNav.getWorkSiteTile()!=null)  
    {
    npc.wayNav.getWorkSiteTile().addWorker(npc);
    this.setMoveToWork();
    }
  else
    {    
    this.setFinished();
    }
  for(NpcAITask task : this.aiTasks)
    {
    task.wasRunning = false;
    }
  }

protected void setMoveToWork()
  {
  npc.setTargetAW(npc.wayNav.getWorkSite());
  }

@Override
public void stopObjective()
  {
  if(npc.wayNav.getWorkSiteTile()!=null)
    {    
    npc.wayNav.getWorkSiteTile().removeWorker(npc);
    }
  npc.wayNav.setWorkSiteTile(null);
  }

}
