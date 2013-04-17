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
package shadowmage.ancient_warfare.common.soldiers.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import shadowmage.ancient_warfare.common.soldiers.NpcBase;
import shadowmage.ancient_warfare.common.soldiers.NpcTypeBase;
import shadowmage.ancient_warfare.common.soldiers.ai.NpcAIObjective;
import shadowmage.ancient_warfare.common.soldiers.ai.objectives.AIAttackTargets;
import shadowmage.ancient_warfare.common.soldiers.ai.objectives.AIFollowPlayer;
import shadowmage.ancient_warfare.common.soldiers.ai.objectives.AIPatrolPoints;
import shadowmage.ancient_warfare.common.soldiers.ai.objectives.AIStayNearHome;
import shadowmage.ancient_warfare.common.soldiers.ai.objectives.AIWander;
import shadowmage.ancient_warfare.common.soldiers.helpers.NpcTargetHelper;
import shadowmage.ancient_warfare.common.soldiers.helpers.targeting.AITargetEntry;
import shadowmage.ancient_warfare.common.soldiers.helpers.targeting.AITargetEntryNpc;
import shadowmage.ancient_warfare.common.soldiers.helpers.targeting.AITargetEntryPlayer;

public class NpcFootsoldier extends NpcTypeBase
{

/**
 * @param type
 */
public NpcFootsoldier(int type)
  {
  super(type);
  this.displayName = "Footsoldier";
  this.tooltip = "A footsoldier, adept at melee combat.";
  this.addLevel("Novice Footsoldier", "foo", getToolStack(0), getArmorStack(0));
  this.addLevel("Adept Footsoldier", "foo", getToolStack(1), getArmorStack(1));
  this.addLevel("Master Footsoldier", "foo", getToolStack(2), getArmorStack(2));  
  }

@Override
protected ItemStack getToolStack(int level)
  {
  ItemStack sword1 = null;// = new ItemStack(Item.swordSteel,1);
  Map enchMap = null;//new HashMap();
  switch(level)
  {
  case 0:
  sword1 = new ItemStack(Item.swordSteel,1);
  return sword1;
  
  case 1:
  sword1 = new ItemStack(Item.swordSteel,1);
  enchMap = new HashMap();
  enchMap.put(Enchantment.sharpness.effectId, 2);
  EnchantmentHelper.setEnchantments(enchMap, sword1);
  return sword1;
  
  case 2:  
  sword1 = new ItemStack(Item.swordDiamond,1);
  enchMap = new HashMap();
  enchMap.put(Enchantment.sharpness.effectId, 3);
  enchMap.put(Enchantment.flame.effectId, 1);
  EnchantmentHelper.setEnchantments(enchMap, sword1);
  return sword1;
  }
  return null;
  }

@Override
protected ItemStack[] getArmorStack(int level)
  {
  ItemStack[] stacks = new ItemStack[4];
  
  switch(level)
  {
  case 0:
  stacks[0] = new ItemStack(Item.helmetLeather, 1);
  stacks[1] = new ItemStack(Item.plateLeather, 1);
  stacks[2] = new ItemStack(Item.legsLeather, 1);
  stacks[3] = new ItemStack(Item.bootsLeather, 1);
  break;
  
  case 1:
  stacks[0] = new ItemStack(Item.helmetChain, 1);
  stacks[1] = new ItemStack(Item.plateChain, 1);
  stacks[2] = new ItemStack(Item.legsChain, 1);
  stacks[3] = new ItemStack(Item.bootsChain, 1);
  break;
  
  case 2:
  stacks[0] = new ItemStack(Item.helmetSteel, 1);
  stacks[1] = new ItemStack(Item.plateSteel, 1);
  stacks[2] = new ItemStack(Item.legsSteel, 1);
  stacks[3] = new ItemStack(Item.bootsSteel, 1);
  break;
  }
  return stacks;
  }

@Override
public void addTargets(NpcBase npc, NpcTargetHelper helper)
  {
  helper.addTargetEntry(new AITargetEntryPlayer(npc, NpcTargetHelper.TARGET_ATTACK,  40, false, true));
  helper.addTargetEntry(new AITargetEntryNpc(npc, NpcTargetHelper.TARGET_ATTACK, 0, 40, false, true));
  helper.addTargetEntry(new AITargetEntry(npc, NpcTargetHelper.TARGET_ATTACK, EntityMob.class, 0, true, 40));
  helper.addTargetEntry(new AITargetEntry(npc, NpcTargetHelper.TARGET_ATTACK, EntitySlime.class, 0, true, 40));
  }

@Override
public List<NpcAIObjective> getAI(NpcBase npc, int level)
  {
  ArrayList<NpcAIObjective> aiEntries = new ArrayList<NpcAIObjective>(); 
  aiEntries.add(new AIAttackTargets(npc, 9, 20, 20));
  aiEntries.add(new AIFollowPlayer(npc, 8));
  aiEntries.add(new AIPatrolPoints(npc, 7, 20));
  aiEntries.add(new AIStayNearHome(npc, 6, 20, 5));
  aiEntries.add(new AIAttackTargets(npc, 5, 40, 40));  
  aiEntries.add(new AIWander(npc, 1));
  return aiEntries;
  }
}
