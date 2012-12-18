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
package shadowmage.ancient_warfare.common.aw_core.network;

import java.util.HashMap;
import java.util.Map;

import shadowmage.ancient_warfare.common.aw_core.config.Config;
import shadowmage.ancient_warfare.common.aw_core.utils.NBTWriter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

private Map packetTypes = new HashMap<Integer, Class<? extends PacketBase>>();

public PacketHandler()  
  {
  this.packetTypes.put(1, Packet01ModInit.class);
  this.packetTypes.put(2, Packet02Vehicle.class);
  }

@Override
public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
  {
  try
    {
    ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
    int packetType = data.readInt();
    boolean hasTag = data.readBoolean();
    NBTTagCompound tag = null;
    if(hasTag)
      {
      tag = NBTWriter.readTagFromStream(data);
      }
    PacketBase realPacket = this.constructPacket(packetType);
    if(realPacket==null)
      {
      //i think it will throw before this..but w/e
      return;
      }
    realPacket.packetData = tag;
    realPacket.player = (EntityPlayer)player;  
    realPacket.world = realPacket.player.worldObj;    
    realPacket.readDataStream(data);
    realPacket.execute();
    }
  catch(Exception e)
    {
    System.out.println("Extreme error during packet handling, could not instantiate packet instance, improper packetType info");
    Config.log("Exception During Packet Handling, problem reading packet data");
    e.printStackTrace();
    }
  
  }

/**
 * construct a new instance of a packet given only the packetType
 * used on receiving a packet, so that it may be populated by the data stream
 * in an intelligent manner
 * @param type
 * @return
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 */
public PacketBase constructPacket(int type) throws InstantiationException, IllegalAccessException
  {
  return (PacketBase) this.packetTypes.get(type).getClass().newInstance();
  }

}
