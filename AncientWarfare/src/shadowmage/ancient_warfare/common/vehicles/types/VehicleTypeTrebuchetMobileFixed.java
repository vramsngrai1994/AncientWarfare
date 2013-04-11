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


public class VehicleTypeTrebuchetMobileFixed extends VehicleTypeTrebuchet
{

/**
 * @param typeNum
 */
public VehicleTypeTrebuchetMobileFixed(int typeNum)
  {
  super(typeNum);
  this.displayName = "Trebuchet Mobile Fixed";
  this.displayTooltip = "A wheeled version of the personal trebuchet.";  
  this.width = 2.7f;
  this.height = 2.7f; 
  this.baseForwardSpeed = 3.7f*0.05f;
  this.baseStrafeSpeed = 1.0f;
  this.shouldRiderSit = true;
  this.riderForwardsOffset = 1.275f;
  this.riderVerticalOffset = 0.8f;
  this.turretVerticalOffset = (34.f + 67.5f + 24.0f+12.f)*0.0625f;
  }

}