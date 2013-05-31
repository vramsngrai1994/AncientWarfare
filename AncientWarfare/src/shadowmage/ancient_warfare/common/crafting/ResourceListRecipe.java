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
package shadowmage.ancient_warfare.common.crafting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import shadowmage.ancient_warfare.common.config.Config;
import shadowmage.ancient_warfare.common.research.IResearchGoal;
import shadowmage.ancient_warfare.common.research.ResearchGoal;
import shadowmage.ancient_warfare.common.tracker.PlayerTracker;
import shadowmage.ancient_warfare.common.utils.ItemStackWrapper;
import shadowmage.ancient_warfare.common.utils.ItemStackWrapperCrafting;

public class ResourceListRecipe
{

protected HashSet<Integer> neededResearch = new HashSet<Integer>();
protected HashSet<IResearchGoal> neededResearchCache = new HashSet<IResearchGoal>();

ItemStack result;
List<ItemStackWrapperCrafting> resources = null;
String displayName;

public ResourceListRecipe(ItemStack result, List<ItemStackWrapperCrafting> resources)
  {
  this.result = result;
  this.resources = resources;
  displayName = result.getDisplayName();
  }

public ResourceListRecipe(ItemStack result)
  {
  this.result = result;
  displayName = result.getDisplayName();
  this.resources = new ArrayList<ItemStackWrapperCrafting>();
  }

public ResourceListRecipe copy()
  {
  ResourceListRecipe recipe = new ResourceListRecipe(result.copy());
  for(ItemStackWrapperCrafting item : this.resources)
    {
    recipe.addResource(item.getFilter().copy(), item.getQuantity());
    }
  return recipe;
  }

public ResourceListRecipe addResource(ItemStack stack, int qty)
  {
  if(this.resources==null)
    {
    this.resources = new ArrayList<ItemStackWrapperCrafting>();
    }
  this.resources.add(new ItemStackWrapperCrafting(stack, qty));
  return this;
  }

public ResourceListRecipe addResources(Collection<ItemStack> items)
  {
  if(this.resources==null)
    {
    this.resources = new ArrayList<ItemStackWrapperCrafting>();
    }
  for(ItemStack stack : items)
    {
    this.resources.add(new ItemStackWrapperCrafting(stack, stack.stackSize));
    }
  return this;
  }

public List<ItemStackWrapperCrafting> getResourceList()
  {
  return resources;
  }

public ItemStack getResult()
  {
  return result;
  }

public String getDisplayName()
  {
  return this.displayName;
  }

public ResourceListRecipe setDisplayName(String name)
  {
  this.displayName = name;
  return this;
  }

/**
 * called on an active crafting recipe to see if any of its resources still need items 
 * @return
 */
public boolean isFinished()
  {
  return false;
  }

public boolean isResource(ItemStack filter)
  {
  for(ItemStackWrapper resource : this.resources)
    {
    if(resource.matches(filter))
      {
      return true;
      }
    }
  return false;
  }

public boolean canBeCraftedBy(EntityPlayer player)
  {  
  return (Config.disableResearch || player.capabilities.isCreativeMode) ? true : (this.neededResearch==null || this.neededResearch.isEmpty()) ? true : PlayerTracker.instance().getEntryFor(player)!=null && PlayerTracker.instance().getEntryFor(player).hasDoneResearchByNumbers(neededResearch);
  }

public HashSet<IResearchGoal> getNeededResearch()
  {
  if(this.neededResearchCache.size()==this.neededResearch.size())
    {
    return this.neededResearchCache;
    }
  this.neededResearchCache.clear();
  for(Integer i : neededResearch)
    {
    neededResearchCache.add(ResearchGoal.getGoalByID(i));
    }
  return neededResearchCache;
  }

public void addNeededResearch(int res)
  {
  this.neededResearch.add(res);
  }

public void addNeededResearch(Collection<Integer> nums)
  {
  this.neededResearch.addAll(nums);
  }

public void addNeededResearch(IResearchGoal goal)
  {
  this.neededResearch.add(goal.getGlobalResearchNum());
  }

}
