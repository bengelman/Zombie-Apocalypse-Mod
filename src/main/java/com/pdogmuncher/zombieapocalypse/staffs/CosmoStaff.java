package com.pdogmuncher.zombieapocalypse.staffs;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.block.BlockAnvil.Anvil;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class CosmoStaff extends ItemSpade {

	public CosmoStaff(int i, ToolMaterial p_i45353_1_) {
		super(p_i45353_1_);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName("Cosmo Staff");
	}
	public static final String name = "cosmo_staff";
	public String getName(){
		return name;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
    	if(Minecraft.getMinecraft().objectMouseOver!=null && player.inventory.hasItem(ZombieApocalypse.amulet) && !par2World.isRemote)
    	{
    		double xm = 0, ym = 0, zm = 0;
    		if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
    			xm = Minecraft.getMinecraft().objectMouseOver.entityHit.posX;
    			ym = Minecraft.getMinecraft().objectMouseOver.entityHit.posY;
    			zm = Minecraft.getMinecraft().objectMouseOver.entityHit.posZ;
    		}
    		if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
    			xm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getX();
        		ym=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getY();
        		zm=Minecraft.getMinecraft().objectMouseOver.getBlockPos().getZ();  	
    		}
    		player.playSound("zombieapocalypse:music.bangarang", 1.0f, 1.0f);
    		//par2World.spawnEntityInWorld(new EntityLightningBolt(par2World, xm, ym, zm));
    		/*EntityFallingBlock meteor = new EntityAnvil();
    		meteor.motionX = 0;
    		meteor.motionY = -1;
    		meteor.motionZ = 0;
    		meteor.setPositionAndUpdate(xm, ym + 5, zm);*/
    		par2World.setBlockState(new BlockPos(xm, ym+7, zm), Blocks.anvil.getDefaultState());
    		//par2World.setRainStrength(5.0f);
    		//par2World.setThunderStrength(5.0f);
    		ItemStack itemstack = null;
			for (int i = 0; i < player.inventory.mainInventory.length; i++) {
				ItemStack s = player.inventory.mainInventory[i];
				if (s != null && s.getItem().getUnlocalizedName().equalsIgnoreCase("item.Amulet")) {
					if (!player.capabilities.isCreativeMode){
						s.setItemDamage(s.getItemDamage() + 1);
					}
					if (s.getMaxDamage() <= s.getItemDamage()){
						s.stackSize = -1;
						player.inventory.setInventorySlotContents(i, null);
					}
					System.out.println("Damage item");
					break;
				}
			}
    	}
	
    	return par1ItemStack;
}
	@EventHandler
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
		this.onItemRightClick(stack, target.worldObj, (EntityPlayer)attacker);
		return true;
	}
	public EnumRarity getRarity (ItemStack par1){ 
		return EnumRarity.EPIC; //uncommon= Yellow rare= Light Blue epic= Purple 
	} 
}
