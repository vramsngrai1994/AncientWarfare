package shadowmage.ancient_structures.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import shadowmage.ancient_structures.common.config.AWLog;
import shadowmage.ancient_structures.common.item.AWStructuresItemLoader;
import shadowmage.ancient_structures.common.tile.TileAdvancedSpawner;
import shadowmage.ancient_warfare.common.network.GUIHandler;

public class BlockAdvancedSpawner extends Block
{

Icon transparentIcon;

public BlockAdvancedSpawner(int id, String regName)
  {
  super(id, Material.rock);
  this.setCreativeTab(AWStructuresItemLoader.structureTab);
  this.setUnlocalizedName(regName);
  this.setTextureName("ancientwarfare:civic/civicMineQuarrySides");
  }

@Override
public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
  {
  TileEntity te = world.getBlockTileEntity(x, y, z);
  if(te instanceof TileAdvancedSpawner)
    {
    TileAdvancedSpawner spawner = (TileAdvancedSpawner)te;
    if(spawner.getSettings().isTransparent())
      {
      AWLog.logDebug("returning transparent icon...");
      return transparentIcon;
      }
    }
  return super.getBlockTexture(world, x, y, z, side);
  }

@Override
public void registerIcons(IconRegister p_149651_1_)
  {
  super.registerIcons(p_149651_1_);
  transparentIcon = p_149651_1_.registerIcon("ancientwarfare:fooToFixThisReference");
  }

@Override
public boolean hasTileEntity(int metadata)
  {
  return true;
  }

@Override
public TileEntity createTileEntity(World world, int metadata)
  {
  return new TileAdvancedSpawner();
  }

@Override
public void breakBlock(World world, int x, int y, int z, int block, int meta)
  {
  TileEntity te = world.getBlockTileEntity(x, y, z);
  if(!world.isRemote && te instanceof TileAdvancedSpawner)
    {
    TileAdvancedSpawner spawner = (TileAdvancedSpawner)te;
    spawner.onBlockBroken();
    }
  super.breakBlock(world, x, y, z, block, meta);
  }

@Override
public float getBlockHardness(World world, int x, int y, int z)
  {
  TileEntity te = world.getBlockTileEntity(x, y, z);
  if(te instanceof TileAdvancedSpawner)
    {
    TileAdvancedSpawner spawner = (TileAdvancedSpawner)te;
    return spawner.getBlockHardness();
    }
  return super.getBlockHardness(world, x, y, z);
  }

@Override
public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
  {
  TileEntity te = world.getBlockTileEntity(x, y, z);
  if(te instanceof TileAdvancedSpawner)
    {
    ItemStack item = new ItemStack(this);
    NBTTagCompound settings = new NBTTagCompound();
    ((TileAdvancedSpawner) te).getSettings().writeToNBT(settings);
    item.setTagInfo("spawnerSettings", settings);
    return item;
    }
  return super.getPickBlock(target, world, x, y, z);
  }

@Override
public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int sideHit, float hitX, float hitY, float hitZ)
  {
  if(player.capabilities.isCreativeMode && !world.isRemote)
    {
    if(player.isSneaking())
      {
      GUIHandler.instance().openGUI(GUIHandler.SPAWNER_BLOCK_INVENTORY, player, world, x, y, z);
      }
    else
      {
      GUIHandler.instance().openGUI(GUIHandler.SPAWNER_BLOCK, player, world, x, y, z);    
      }
    return false;
    }
  return super.onBlockActivated(world, x, y, z, player, sideHit, hitX, hitY, hitZ);
  }

}
