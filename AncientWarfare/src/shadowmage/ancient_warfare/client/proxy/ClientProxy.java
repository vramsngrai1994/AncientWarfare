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
package shadowmage.ancient_warfare.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import shadowmage.ancient_warfare.client.input.InputHelper;
import shadowmage.ancient_warfare.client.input.TickHandlerClientKeyboard;
import shadowmage.ancient_warfare.client.registry.RenderRegistry;
import shadowmage.ancient_warfare.client.render.AWRenderHelper;
import shadowmage.ancient_warfare.client.render.RenderOverlay;
import shadowmage.ancient_warfare.common.network.PacketBase;
import shadowmage.ancient_warfare.common.proxy.CommonProxy;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{

public ClientProxy()
  {
  }

@Override
public EntityPlayer getClientPlayer()
  {
  return Minecraft.getMinecraft().thePlayer;
  }

@Override
public void sendPacketToServer(PacketBase pkt)
  {
  PacketDispatcher.sendPacketToServer(pkt.get250Packet());
  }

@Override
public void registerClientData()
  {  
  /**
   * register single render for all vehicles, local renderManager handles from there...
   */
  RenderRegistry.instance().loadRenders();
  TickRegistry.registerTickHandler(new TickHandlerClientKeyboard(), Side.CLIENT);
  TickRegistry.registerTickHandler(new RenderOverlay(), Side.CLIENT);
  
  /**
   * load keybinds and register keybind handler
   */
  InputHelper.instance().loadKeysFromConfig();
  KeyBindingRegistry.registerKeyBinding(InputHelper.instance());
  
  /**
   * event helper for world-render ticks for BB rendering
   */
  MinecraftForge.EVENT_BUS.register(AWRenderHelper.instance());
  
  }

}
