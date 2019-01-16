package com.pdogmuncher.zombieapocalypse.mobs;

import java.util.ArrayList;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class EntityKitPicker extends EntityLiving{
	public String outputmessage;
	public ArrayList<String> blocks = new ArrayList<String>();
	public boolean cost = false;
	public EntityKitPicker(World p_i1595_1_) {
		super(p_i1595_1_);
		this.setSize(1.0f, 2.5f);
		this.setCanPickUpLoot(true);
		//this.setCurrentItemOrArmor(0, new ItemStack(BronzeAge.bronzeHelmet));
		this.targetTasks.taskEntries.clear();
		this.tasks.taskEntries.clear();
		//this.setCustomNameTag(name);
		//this.outputmessage = outputmessage;
		//this.setNoAI(true);
	}
	@Override
	public void damageEntity(DamageSource source, float amount){
		super.damageEntity(source, amount);
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(arg0);
		for (int i = 0; i < 5; i++){
			this.setCurrentItemOrArmor(i, null);
		}
		outputmessage = arg0.getString("Output");
		NBTTagList tagList = arg0.getTagList("Blocks", Constants.NBT.TAG_COMPOUND);
		 	for(int i = 0; i < tagList.tagCount(); i++)
		 	{
		 		NBTTagCompound tag = tagList.getCompoundTagAt(i);
		 		String s = tag.getString("Block" + i);
		 		this.addGivenItem(s);
		 		//blocks.add(i, s);
		 	}
		 	cost = arg0.getBoolean("premium");
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound arg0) {
		
		super.writeEntityToNBT(arg0);
		// TODO Auto-generated method stub
		
		arg0.setString("Output", outputmessage);
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < blocks.size(); i++)
		{
		 	String s = blocks.get(i);
		 	if(s != null)
		 	{
		 		NBTTagCompound tag = new NBTTagCompound();
		 		tag.setString("Block" + i, s);
		 		tagList.appendTag(tag);
		 	}
		 }
		 arg0.setTag("Blocks", tagList);
		 arg0.setBoolean("premium", this.cost);
		 System.out.println("NBT: " + arg0);
	}
	@Override
	public boolean interact(EntityPlayer p){
		if (!p.worldObj.isRemote){
			if (cost){
				if (p.inventory.consumeInventoryItem(Items.emerald)){
					p.addChatMessage(new ChatComponentText("Successfully Purchased " + this.getCustomNameTag()));
					//MinecraftServer.getServer().getCommandManager().executeCommand(this.getCommandSenderEntity(), "/particle largeexplode " + this.posX + " " + this.posY + " " + this.posZ +  " 1 1 1 4 5");
					MinecraftServer.getServer().getCommandManager().executeCommand(this.getCommandSenderEntity(), "/summon FireworksRocketEntity ~0 ~1 ~0 {LifeTime:0,FireworksItem:{id:401,Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[65297,16770560],FadeColors:[16777215]}]}}}}");
				}
				else{
					return true;
				}
			}
			ChatComponentText message = new ChatComponentText(this.getCustomNameTag());
			ChatStyle colour = new ChatStyle();
			colour.setColor(EnumChatFormatting.GOLD);
			colour.setBold(true);
			colour.setUnderlined(true);
			message.setChatStyle(colour);
			
			p.getCommandSenderEntity().addChatMessage(message);
			p.getCommandSenderEntity().addChatMessage(new ChatComponentText(outputmessage));
			//p.worldObj.setBlockState(this.getPosition(), Blocks.redstone_torch.getDefaultState());
			p.inventory.clear();
			for (int i = 0; i < blocks.size(); i++){
				ItemStack stack = new ItemStack(Item.getByNameOrId(blocks.get(i)));
				if (stack.getItem() == ZombieApocalypse.bronzeSpade){
					NBTTagCompound tag = stack.getTagCompound();
					if (tag == null){
						tag = new NBTTagCompound();
					}
					NBTTagList list = new NBTTagList();
					//list.set(0, new NBTTagString("minecraft:dirt"));
					list.appendTag(new NBTTagString("minecraft:dirt"));
					tag.setTag("CanDestroy", list);
					System.out.println(tag.toString());
					stack.setTagCompound(tag);
					/*
					try {
						//tag = net.minecraft.nbt.JsonToNBT.func_180713_a("{CanDestroy:[\"minecraft:dirt\"],}");
						stack.setTagCompound(tag);
					} catch (NBTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
				
				p.inventory.addItemStackToInventory(stack);
			}
			/*if (p.worldObj.getBlockState(this.getPosition()).getBlock() == Blocks.stone_button){
				System.out.println("Creating Button");
				BlockButtonStone button = (BlockButtonStone) p.worldObj.getBlockState(this.getPosition()).getBlock();
				button.onBlockClicked(p.worldObj, this.getPosition(), p);
				
			}*/
		}
		return true;
	}
	@Override
	public boolean canDespawn(){
		return false;
	}
	@Override
	public boolean canBePushed(){
		return true;
	}
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.67D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(Double.MAX_VALUE);
        
    }
	@Override
	public void onEntityUpdate(){
		super.onEntityUpdate();
		
		//if (worldObj.getBlockState(this.getPosition()).getBlock() == Blocks.redstone_torch){
			//worldObj.setBlockToAir(this.getPosition());
		//}
	}
	public void addGivenItem(String item){
		ItemStack stack =  new ItemStack(Item.getByNameOrId(item));
		if (this.getHeldItem() == null){
			this.setCurrentItemOrArmor(0, stack);
		}
		if (stack.getItem() instanceof ItemArmor){
			ItemArmor armor = (ItemArmor) stack.getItem();
			int armortype = 0;
			if (armor.armorType == 0){
				armortype = 4;
			}
			if (armor.armorType == 1){
				armortype = 3;
			}
			if (armor.armorType == 2){
				armortype = 2;
			}
			if (armor.armorType == 3){
				armortype = 1;
			}
			this.setCurrentItemOrArmor(armortype, new ItemStack(armor));
		}
		blocks.add(item);
	}
	@Override
	public double getMountedYOffset(){
		return 0.0D;
	}
}
