package com.pdogmuncher.zombieapocalypse.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemCustomSeeds extends Item implements IPlantable
{
    private final Block theBlockPlant;
    /**
     * Block ID of the soil this seed food should be planted on.
     */
    //private final Block soilId;

    public ItemCustomSeeds(Block parBlockPlant)
    {
    	super();
        theBlockPlant = parBlockPlant;
    }

    @Override
    public boolean onItemUse(ItemStack parItemStack, EntityPlayer parPlayer, 
          World parWorld, BlockPos p, EnumFacing par7, float par8, 
          float par9, float par10)
    {
     // not sure what this parameter does, copied it from potato
        System.out.println("THE SEEDS");
        // check if player has capability to edit
        if (parPlayer.canPlayerEdit(new BlockPos(p.getX(), p.getY()+1, p.getZ()), par7, parItemStack))
        {
            // check that the soil block can sustain the plant
            // and that block above is air so there is room for plant to grow
            if (parWorld.getBlockState(p).getBlock().canSustainPlant(parWorld, 
                  p, par7, this) && parWorld
                  .isAirBlock(new BlockPos(p.getX(), p.getY()+1, p.getZ())))
            {
            	System.out.println("They Placin'");
             // place the plant block
                parWorld.setBlockState(new BlockPos(p.getX(), p.getY()+1, p.getZ()), theBlockPlant.getDefaultState());
                // decrement the stack of seed items
                --parItemStack.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    @Override
	public IBlockState getPlant(IBlockAccess arg0, BlockPos arg1) {
		// TODO Auto-generated method stub
		return theBlockPlant.getDefaultState();
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess arg0, BlockPos arg1) {
		// TODO Auto-generated method stub
		return EnumPlantType.Crop;
	}
}