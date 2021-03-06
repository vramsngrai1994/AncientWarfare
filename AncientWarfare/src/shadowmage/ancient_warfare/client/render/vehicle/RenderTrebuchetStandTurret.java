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
package shadowmage.ancient_warfare.client.render.vehicle;

import shadowmage.ancient_warfare.client.model.ModelTrebuchetStandTurret;
import shadowmage.ancient_warfare.client.render.RenderVehicleBase;
import shadowmage.ancient_warfare.common.vehicles.VehicleBase;
import shadowmage.ancient_warfare.common.vehicles.helpers.VehicleFiringVarsHelper;

public class RenderTrebuchetStandTurret extends RenderVehicleBase
{

ModelTrebuchetStandTurret model = new ModelTrebuchetStandTurret();

@Override
public void renderVehicle(VehicleBase vehicle, double x, double y, double z,   float yaw, float tick)
  {
  VehicleFiringVarsHelper var = vehicle.firingVarsHelper;
  model.setTurretRotation(yaw - vehicle.localTurretRotation - tick*vehicle.currentTurretYawSpeed);
  model.setArmRotations(var.getVar1() + tick*var.getVar2(), var.getVar3()+tick*var.getVar4());
  model.render(vehicle, 0, 0, 0, 0, 0, 0.0625f);
  }

@Override
public void renderVehicleFlag()
  {
  model.renderFlag();
  }

}
