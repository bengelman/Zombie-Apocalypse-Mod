package com.pdogmuncher.zombieapocalypse.events;

import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;

import net.minecraft.client.Minecraft;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockDispenser;
import net.minecraftforge.fml.relauncher.Side;

public final class BulletDispense extends BehaviorDefaultDispenseItem
{
    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
    	if (!par1IBlockSource.getWorld().isRemote){
    		EnumFacing enumfacing = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
            double d0 = par1IBlockSource.getX() + (double)enumfacing.getFrontOffsetX();
            double d1 = (double)((float)par1IBlockSource.getY() + 0.2F);
            double d2 = par1IBlockSource.getZ() + (double)enumfacing.getFrontOffsetZ();
            EntityBullet b = new EntityBullet(par1IBlockSource.getWorld(), d0, d1, d2);
            b.damage = 20;
            par1IBlockSource.getWorld().spawnEntityInWorld(b);
            b.addVelocity(enumfacing.getFrontOffsetX(), 0, enumfacing.getFrontOffsetZ());
            //Entity entity = AdvMobsPlacer.spawnCreature(par1IBlockSource.getWorld(), par2ItemStack.getItemDamage(), d0, d1, d2);

            par2ItemStack.stackSize--;
    	}
        
        return par2ItemStack;
    }
}
