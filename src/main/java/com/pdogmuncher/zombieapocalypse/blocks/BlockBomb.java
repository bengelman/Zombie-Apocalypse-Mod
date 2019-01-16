package com.pdogmuncher.zombieapocalypse.blocks;

import java.util.Random;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockBomb extends Block implements ITileEntityProvider{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 600);
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public BlockBomb(Material arg0) {
		super(arg0);
		this.setUnlocalizedName("bomb");
		this.setCreativeTab(ZombieApocalypse.tabPVP);
		this.setStepSound(soundTypeMetal);
		this.setTickRandomly(true);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player){
		if (player.getCurrentEquippedItem().getItem() == ZombieApocalypse.defusal_kit && !world.isRemote){
			world.setBlockState(pos, Blocks.air.getDefaultState());
			player.addChatComponentMessage(new ChatComponentText(player.getDisplayNameString() + " has defused the bomb"));
		}
	
	}
	public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn)
    {
        if (MathHelper.abs((float)entityIn.posX - (float)clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F)
        {
            double d0 = entityIn.posY + (double)entityIn.getEyeHeight();
            
            if (d0 - (double)clickedBlock.getY() > 2.0D)
            {
                return EnumFacing.UP;
            }

            if ((double)clickedBlock.getY() - d0 > 0.0D)
            {
                return EnumFacing.DOWN;
            }
        }
        return entityIn.getHorizontalFacing().getOpposite();
    } 
	@Override
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase entity, ItemStack stack){
		
		world.setBlockState(position, state.withProperty(FACING, getFacingFromEntity(world, position, entity)), 2);
		if (world.getBlockState(position.add(0, -1, 0)).getBlock() != Blocks.redstone_block && !world.isRemote){
			world.setBlockState(position, Blocks.air.getDefaultState());
			((EntityPlayer)entity).inventory.addItemStackToInventory(new ItemStack(this));
			if (!world.isRemote){
				entity.getCommandSenderEntity().addChatMessage(new ChatComponentText("Please place the bomb on redstone block"));
			}
		}
		else{
			if (!world.isRemote){
				entity.getCommandSenderEntity().addChatMessage(new ChatComponentText(entity.getName() + " has placed a bomb!"));
			}
		}
	}
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
	    return new TileEntityBomb();
	}
	@Override
	public boolean hasTileEntity(IBlockState state){
		return true;
	}
	protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }
    
    public int getMetaFromState(IBlockState state)
    {
        byte b0 = 0;
        int i = b0 | ((EnumFacing)state.getValue(FACING)).getIndex();

       
        return i;
    }
    
}
