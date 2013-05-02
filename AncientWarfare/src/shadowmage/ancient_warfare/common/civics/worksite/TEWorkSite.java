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
package shadowmage.ancient_warfare.common.civics.worksite;

import java.util.Iterator;
import java.util.LinkedList;

import net.minecraft.entity.Entity;
import shadowmage.ancient_warfare.common.civics.TECivic;
import shadowmage.ancient_warfare.common.npcs.NpcBase;
import shadowmage.ancient_warfare.common.targeting.TargetType;

public abstract class TEWorkSite extends TECivic
{

protected LinkedList<WorkSitePoint> workPoints = new LinkedList<WorkSitePoint>();

public TEWorkSite()
  {
  this.isWorkSite = true;    
  }

protected abstract void scan();

protected abstract void doWork(NpcBase npc, WorkSitePoint p);

protected abstract TargetType validateWorkPoint(WorkSitePoint p);

@Override
protected void onCivicUpdate()
  {
  validateWorkPoints();  
  if(!hasWork())
    {
    scan();
    }
  super.onCivicUpdate();
  }

@Override
protected void updateHasWork()
  {  
  this.setHasWork(!this.workPoints.isEmpty());
  }

@Override
public void doWork(NpcBase npc)
  {
  Iterator<WorkSitePoint> it = this.workPoints.iterator();
  WorkSitePoint p;
  while(it.hasNext())
    {
    p = it.next();
    if(validateWorkPoint(p)!=p.work)
      {
      it.remove();
      }
    else
      {
      this.doWork(npc, p);
      break;
      }
    }  
  this.updateHasWork();
  }

protected void validateWorkPoints()
  {
  Iterator<WorkSitePoint> it = this.workPoints.iterator();
  WorkSitePoint p;
  while(it.hasNext())
    {
    p = it.next();
    if(validateWorkPoint(p)!=p.work)
      {
      it.remove();
      }
    }
  setHasWork(!workPoints.isEmpty());
  }

protected void addWorkPoint(int x, int y, int z, TargetType work)
  {
  this.workPoints.add(new WorkSitePoint(x, y, z, work));
  }

protected void addWorkPoint(Entity ent, TargetType work)
  {
  this.workPoints.add(new WorkSitePoint(ent, work));
  }

}