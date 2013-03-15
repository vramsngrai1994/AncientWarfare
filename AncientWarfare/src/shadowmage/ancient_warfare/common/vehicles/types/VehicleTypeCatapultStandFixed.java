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
package shadowmage.ancient_warfare.common.vehicles.types;

import shadowmage.ancient_warfare.common.utils.Trig;
import shadowmage.ancient_warfare.common.vehicles.VehicleBase;
import shadowmage.ancient_warfare.common.vehicles.helpers.VehicleFiringVarsHelper;


public class VehicleTypeCatapultStandFixed extends VehicleTypeCatapult
{
/**
 * @param typeNum
 */
public VehicleTypeCatapultStandFixed(int typeNum)
  {
  super(typeNum);
  
  this.width = 2;
  this.height = 1.8f; 
  this.baseMissileVelocityMax = 37.f;  
  this.missileVerticalOffset = 2.70f* Trig.sinDegrees(70) + 0.1f;
  this.missileForwardsOffset = -2.70f* Trig.cosDegrees(70);
  this.riderForwardsOffset = 1.2f;
  this.riderVerticalOffset = 0.0f;
  this.displayName = "Catapult Stand Fixed";
  this.displayTooltip = "A fixed-placement catapult with limited aim capability.";
  this.storageBaySize = 0;
  this.armorBaySize = 4;
  this.upgradeBaySize = 4;
  this.canAdjustYaw = false;
  this.isDrivable = true;
  this.baseForwardSpeed = 0.f;  
  this.shouldRiderSit = false;
  this.moveRiderWithTurret = false;
  }



}
