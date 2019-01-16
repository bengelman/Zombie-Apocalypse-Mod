package com.pdogmuncher.zombieapocalypse.events;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.ai.EntityAITargetFlare;
import com.pdogmuncher.zombieapocalypse.bronzetools.BronzePickaxe;
import com.pdogmuncher.zombieapocalypse.generation.BronzeGenerator;
import com.pdogmuncher.zombieapocalypse.items.ItemBackpack;
import com.pdogmuncher.zombieapocalypse.mobs.CitizenFemale;
import com.pdogmuncher.zombieapocalypse.mobs.EntityBossDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityCosmoDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityEarthDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityFireDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityHelicopter;
import com.pdogmuncher.zombieapocalypse.mobs.EntityIceDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityJet;
import com.pdogmuncher.zombieapocalypse.mobs.EntityLightningDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityUltraZombie;
import com.pdogmuncher.zombieapocalypse.mobs.Ferret;
import com.pdogmuncher.zombieapocalypse.mobs.Soldier;
import com.pdogmuncher.zombieapocalypse.mobs.UberFerret;
import com.pdogmuncher.zombieapocalypse.render.RenderCustomModelAsPlayer;
import com.pdogmuncher.zombieapocalypse.staffs.LightningStaff;
import com.pdogmuncher.zombieapocalypse.tools.EntityFlare;
import com.pdogmuncher.zombieapocalypse.tools.Firearm;
import com.pdogmuncher.zombieapocalypse.tools.ItemCrossbow;
import com.pdogmuncher.zombieapocalypse.tools.ItemShield;
/*
 * This is where I keep all my event handlers. It's honestly a mess. You shouldn't have to change any of it,
 * only add stuff, but if you do have to change something and don't understand the code you can 
 * message me and I can try to walk you through it
 */
public class FallHandler {
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (event.entity instanceof EntityJet){
			event.setCanceled(true);
		}
		if (event.entity instanceof EntityHelicopter){
			event.setCanceled(true);
		}
		if (event.entity != null && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			if (player.capabilities.isCreativeMode){
				
			}
			else if (player.getCurrentEquippedItem() != null){
				if (player.getCurrentEquippedItem().getItem().equals(ZombieApocalypse.airStaff))
					event.setCanceled(true);
				else if (player.getCurrentEquippedItem().getUnlocalizedName().equalsIgnoreCase(ZombieApocalypse.bronzePickaxe.getUnlocalizedName()) && !player.worldObj.isRemote){
					System.out.println("Holding Valid Thing");
					if (((BronzePickaxe)player.getCurrentEquippedItem().getItem()).pounding){
						List pounded = event.entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.fromBounds((double)player.posX, (double)player.posY, (double)player.posZ, (double)(player.posX + 1), (double)(player.posY + 1), (double)(player.posZ + 1)).expand((double)(3), 4.0D, (double)(3)));
						for (int i = 0; i < pounded.size(); i++){
							if (pounded.get(i) instanceof EntityLiving){
								EntityLiving living = (EntityLiving) pounded.get(i);
								living.attackEntityFrom(DamageSource.magic, 5.0f);
								living.addVelocity(0, 1.0f, 0);
							}
						}
						((BronzePickaxe)player.getCurrentEquippedItem().getItem()).pounding = false;
						event.setCanceled(true);
						player.worldObj.playSound(player.posX, player.posY, player.posZ, this.findBlockUnderEntity(player).stepSound.getBreakSound(), 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F), true);
					}
				}
				else{
					if (event.distance > 4){
						player.addPotionEffect(new PotionEffect(ZombieApocalypse.broken_leg.id, 120 * 20));
					}
				}
			}
		}
	}
	@SubscribeEvent
	public void onLivingFallEvent(PlayerFlyableFallEvent event) {
		if (event.entity != null && event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if (player.getCurrentEquippedItem() != null){
				if (player.getCurrentEquippedItem().getUnlocalizedName().equalsIgnoreCase(ZombieApocalypse.bronzePickaxe.getUnlocalizedName())){
					if (((BronzePickaxe)player.getCurrentEquippedItem().getItem()).pounding){
						List pounded = event.entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.fromBounds((double)player.posX, (double)player.posY, (double)player.posZ, (double)(player.posX + 1), (double)(player.posY + 1), (double)(player.posZ + 1)).expand((double)(5), 4.0D, (double)(5)));
						for (int i = 0; i < pounded.size(); i++){
							if (pounded.get(i) instanceof EntityLiving){
								EntityLiving living = (EntityLiving) pounded.get(i);
								living.attackEntityFrom(DamageSource.magic, 5.0f);
								living.addVelocity(0, 1.0f, 0);
							}
						}
						((BronzePickaxe)player.getCurrentEquippedItem().getItem()).pounding = false;
						player.worldObj.playSound(player.posX, player.posY, player.posZ, this.findBlockUnderEntity(player).stepSound.getBreakSound(), 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F), true);
						//event.setCanceled(true);
					}
				}
				else{
					
				}
			}
		}
	}
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        
    }
	@SubscribeEvent
	   public void onEntityDrop(LivingDropsEvent event) {
		if (event.entity.worldObj.isRemote){
			return;
		}
	         if (event.entityLiving instanceof Ferret){
	            if (Math.random() < 0.5D) {
	               event.entityLiving.dropItem(ZombieApocalypse.bronzeIngot, 1);
	            }
	         }
	         
	         if (event.entityLiving instanceof UberFerret){
		            if (true) {
		               event.entityLiving.dropItem(ZombieApocalypse.lightningStaff, 1);
		               
		            }
		     }
	         if (event.entityLiving instanceof EntityVillager){
	        	 event.entityLiving.dropItem(ZombieApocalypse.tomato, 1);
	         }
	         if (event.entityLiving instanceof EntityUltraZombie){
	        	 
	         }
	         if (event.entityLiving instanceof EntityFireDragon){
	        	 event.entityLiving.dropItem(ZombieApocalypse.fireStaff, 1);
	         }
	         if (event.entityLiving instanceof EntityEarthDragon){
	        	 event.entityLiving.dropItem(ZombieApocalypse.earthStaff, 1);
	         }
	         if (event.entityLiving instanceof EntityIceDragon){
	        	 event.entityLiving.dropItem(ZombieApocalypse.iceStaff, 1);
	         }
	         if (event.entityLiving instanceof EntityCosmoDragon){
	        	 event.entityLiving.dropItem(ZombieApocalypse.cosmoStaff, 1);
	         }
	         if (event.entityLiving instanceof EntityLightningDragon){
	        	 event.entityLiving.dropItem(ZombieApocalypse.lightningStaff, 1);
	         }
	         if (event.entityLiving instanceof EntityBossDragon){
	        	 event.entityLiving.dropItem(ZombieApocalypse.exit_key, 1);
	         }
	      }
	
	   @SubscribeEvent
	   public void lanternHolding(RenderLivingEvent.Pre event)
	   {
	      if (!event.isCanceled() && event.entity instanceof EntityPlayer)
	      {
	         ItemStack item = event.entity.getHeldItem();

	         if (item == null)
	         {
	            return;
	         }

	         RenderPlayer rp;
	         if (item.getItem() == ZombieApocalypse.crossbow){
	        	 if (item.getTagCompound().getInteger("next") - ((ItemCrossbow)ZombieApocalypse.crossbow).ticks <= ((ItemCrossbow)ZombieApocalypse.crossbow).USE_TIME / 4){
	        		 rp = (RenderPlayer)event.renderer;
	 	            //rp.modelArmorChestplate.heldItemRight = rp.modelArmor.heldItemRight = rp.modelBipedMain.heldItemRight = 6;
	 	            //rp.getPlayerModel().heldItemRight = 270;
	 	            rp.getPlayerModel().aimedBow = true;
	 	            
	        	 }
	         }
	         if (item.getItem() instanceof Firearm || item.getItem() instanceof ItemShield)
	         {
	            rp = (RenderPlayer)event.renderer;
	            //rp.modelArmorChestplate.heldItemRight = rp.modelArmor.heldItemRight = rp.modelBipedMain.heldItemRight = 6;
	            //rp.getPlayerModel().heldItemRight = 270;
	            if (event.entity.isSneaking() || Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()){
	            	rp.getPlayerModel().aimedBow = true;
	            }
	            else{
	            	rp.getPlayerModel().aimedBow = false;
	            }
	         }
	      }
	   }
	   @SubscribeEvent
	   public void shield(LivingHurtEvent event){
		   if (event.entity instanceof EntityPlayer && !event.source.isUnblockable()){
			   EntityPlayer player = (EntityPlayer)event.entity;
			   if (player == null)return;
			   if (event.ammount > 4 && new Random().nextInt() % 10 == 0){
				   player.addPotionEffect(new PotionEffect(ZombieApocalypse.bleeding.id, 12000));
			   }
			   if (player.getCurrentEquippedItem() != null){
				   if (player.getCurrentEquippedItem().getItem().getUnlocalizedName().contains("shield") & !event.entity.worldObj.isRemote){
					   player.getCurrentEquippedItem().attemptDamageItem(1, new Random());
					   //player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 10, 0 ) );
					   event.setCanceled(true);
				   }
			   }
		   }
	   }
	   @SubscribeEvent
	   public void death(LivingDeathEvent event){
		   if (event.entityLiving instanceof Soldier && !event.entity.worldObj.isRemote){
	        	 System.out.println("Dropping");
	        	 int index = Math.abs(new Random().nextInt() % ZombieApocalypse.firearms.size() - 1);
	        	 System.out.println("Size: " + ZombieApocalypse.firearms.size());
	        	 System.out.println("Index: " + index);
	        	 event.entityLiving.dropItem(ZombieApocalypse.firearms.get(index), 1);
	         }
		   if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote){
			   if (event.source.damageType.equals("uzombie")){
				   EntityUltraZombie z = new EntityUltraZombie(event.entity.worldObj);
				   z.setPositionAndUpdate(event.entity.posX, event.entity.posY, event.entity.posZ);
				   z.setCurrentItemOrArmor(1, ((EntityPlayer)event.entityLiving).getCurrentArmor(0));
				   z.setCurrentItemOrArmor(2, ((EntityPlayer)event.entityLiving).getCurrentArmor(1));
				   z.setCurrentItemOrArmor(3, ((EntityPlayer)event.entityLiving).getCurrentArmor(2));
				   z.setCurrentItemOrArmor(4, ((EntityPlayer)event.entityLiving).getCurrentArmor(3));
				   z.setCurrentItemOrArmor(0, ((EntityPlayer)event.entityLiving).getCurrentEquippedItem());
				   z.setCustomNameTag(event.entity.getName());
				   event.entity.worldObj.spawnEntityInWorld(z);
			   }
			   if (LightningStaff.savedWorld != null){
				   event.entity.worldObj = LightningStaff.savedWorld;
			   }
		   }
		   if (event.entity instanceof CitizenFemale && !event.entity.worldObj.isRemote){
			   EntityUltraZombie z = new EntityUltraZombie(event.entity.worldObj);
			   z.setPositionAndUpdate(event.entity.posX, event.entity.posY, event.entity.posZ);
			   event.entity.worldObj.spawnEntityInWorld(z);
		   }
	   }
	   private static PositionedSound entry;
	   @SubscribeEvent
	   public void tntPower (EntityJoinWorldEvent event){
		   if (event.entity instanceof EntityTNTPrimed){
			   //((EntityTNTPrimed)event.entity).fuse = 1;
		   }
		   
		   if (event.entity instanceof EntityCreature){
			   EntityCreature base = (EntityCreature) event.entity;
			   base.targetTasks.addTask(1, new EntityAITargetFlare(base, EntityPlayer.class, true));
			   base.targetTasks.addTask(0, new EntityAINearestAttackableTarget(base, EntityFlare.class, true));
		   }
		   
		   if (event.entity instanceof EntityPlayer){
			   EntityPlayer player = (EntityPlayer) event.entity;
			   ExtendedPlayer props = ExtendedPlayer.get(player);
			   System.out.println("Player spawning");
			   boolean works = false;
			   for (int i = 0; i < player.inventory.getSizeInventory(); i++){
				   if (player.inventory.getStackInSlot(i) != null){
					   works = true;
					   break;
				   }
			   }
			   if (props.initialDeath && !works && player.worldObj.getWorldType() == ZombieApocalypse.APOCALYPSE){
				   Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("zombieapocalypse:wwz")));
				   GuiBuffBar.titleTime = 60;
				   props.initialDeath = false;
			   }
			   
			   if (!event.entity.worldObj.isRemote && props.firstSpawn){
				   props.firstSpawn = false;
				   if (event.world.getWorldType() == ZombieApocalypse.SURVIVOR){
					   player.inventory.addItemStackToInventory(((ItemBackpack)ZombieApocalypse.backpack).fillWithSet(ItemBackpack.FillType.SPAWN));
				   }
				   else if (event.world.getWorldType() == ZombieApocalypse.SOLDIER){
					   player.inventory.addItemStackToInventory(((ItemBackpack)ZombieApocalypse.backpack).fillWithSet(ItemBackpack.FillType.SOLDIER));
				   }
				   else if (event.world.getWorldType() == ZombieApocalypse.CITIZEN){
					   player.inventory.addItemStackToInventory(((ItemBackpack)ZombieApocalypse.backpack).fillWithSet(ItemBackpack.FillType.CITIZEN));
				   }
				   else if (event.world.getWorldType() == ZombieApocalypse.APOCALYPSE_EXPERT){
					   player.inventory.addItemStackToInventory(((ItemBackpack)ZombieApocalypse.backpack).fillWithSet(ItemBackpack.FillType.BUNKER));
				   }
				   else if (event.world.getWorldType() == ZombieApocalypse.APOCALYPSE){
					   if (BronzeGenerator.playerLocation != null){
						   player.setSpawnPoint(new BlockPos(-1, BronzeGenerator.playerLocation.getY(), -1), true);
						   player.setPosition(-1, BronzeGenerator.playerLocation.getY(), -1);
					   }
					   
					   System.out.println("Setting player location");
						   //player.setDead();
						   
						   //player.setDead();
						   //player.setPosition(BronzeGenerator.playerLocation.getX(), BronzeGenerator.playerLocation.getY(), BronzeGenerator.playerLocation.getZ());
					   
					   
				   }
				   //props.initialDeath = false;
			   }
		   }
	   }
	   @SubscribeEvent
	   public void update (LivingUpdateEvent event){
		   if (event.entityLiving == null){
			   return;
		   }
		   if (event.entityLiving instanceof EntityPlayer){
			   EntityPlayer player = (EntityPlayer) event.entityLiving;
			   if (player.getActivePotionEffect(ZombieApocalypse.infected_wound) != null){
				   if (new Random().nextInt() % 200 == 0){
					   player.attackEntityFrom(DamageSource.generic, 1);
				   }
			   }
			   if (player.getActivePotionEffect(ZombieApocalypse.bleeding) != null){
				   if (player.getActivePotionEffect(ZombieApocalypse.bleeding) != null){
					   if (new Random().nextInt() % 3000 == 0 && player.getActivePotionEffect(ZombieApocalypse.bleeding).getDuration() < 2400){
						   player.addPotionEffect(new PotionEffect(ZombieApocalypse.infected_wound.id, 2000));
					   }
				   }
				   if (player.getActivePotionEffect(ZombieApocalypse.bleeding).getDuration() < 200){
					   player.attackEntityFrom(new DamageSource("bled out"), 2);
					   player.addPotionEffect(new PotionEffect(Potion.confusion.id, 3));
				   }
				   if (player.getActivePotionEffect(ZombieApocalypse.bleeding).getDuration() < 20){
					   player.attackEntityFrom(new DamageSource("bled out"), 200);
					   player.addPotionEffect(new PotionEffect(Potion.confusion.id, 2));
				   }
			   }
			   if (player.getActivePotionEffect(ZombieApocalypse.broken_leg) != null){
				   player.setSprinting(false);
			   }
			   if (player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)).biomeID == ZombieApocalypse.hauntedForestBiome.biomeID){
				   //player.worldObj.setWorldTime(15000);
				   //System.out.println("Set Time");
			   }
			   if (player.getCurrentEquippedItem() != null && player != null && player.worldObj != null){
				   if (player.getCurrentEquippedItem().getItem() == ZombieApocalypse.lantern){
					   
					   //player.worldObj.
				   }
			   }
			   Random random = new Random();
			   if (random.nextInt() % 400 == 0 && !player.worldObj.isRemote){
				   EntityUltraZombie zombie = new EntityUltraZombie(player.worldObj);
				   int y = 0;
				   int x = (int) (player.posX + (random.nextInt() % 20));
				   if (x > player.posX){
					   x += 10;
				   }
				   else{
					   x -= 10;
				   }
				   int z = (int) (player.posZ + (random.nextInt() % 20));
				   if (z > player.posZ){
					   z += 10;
				   }
				   else{
					   z -= 10;
				   }
				   for (y = 0; y < 200; y++){
					   if (player.worldObj.canSeeSky(new BlockPos(x, y, z))){
						   zombie.setPosition(x, y, z);
						   System.out.println("Spawning Zombie at " + x + " " + y + " " + z);
						   break;
					   }
				   }
				   //player.worldObj.spawnEntityInWorld(zombie);
				   
			   }
			   if (random.nextInt() % 6000 == 0 && !player.worldObj.isRemote && player.worldObj.isDaytime()){
				   //player.addChatMessage(new ChatComponentText("A Huge Wave of Zombies is Approaching!"));
				   
				   int y = 0;
				   int x = (int) (player.posX + (random.nextInt() % 20));
				   if (x > player.posX){
					   x += 10;
				   }
				   else{
					   x -= 10;
				   }
				   int z = (int) (player.posZ + (random.nextInt() % 20));
				   if (z > player.posZ){
					   z += 10;
				   }
				   else{
					   z -= 10;
				   }
				   for (int i = 0; i < 20; i++){
					   EntityUltraZombie zombie = new EntityUltraZombie(player.worldObj);
					   for (y = 0; y < 200; y++){
						   if (player.worldObj.canSeeSky(new BlockPos(x, y, z))){
							   zombie.setPosition(x, y, z);
							   System.out.println("Spawning Zombie at " + x + " " + y + " " + z);
							   break;
						   }
					   }
					   //player.worldObj.spawnEntityInWorld(zombie);
				   }
				   
				   
			   }
			   
		   }
		   /*
		   if (event.entity instanceof EntityTNTPrimed){
			   System.out.println("Prime");
			   EntityTNTPrimed prime = (EntityTNTPrimed) event.entity;
			   prime.fuse = 1;
		   }*/
		   if (event.entityLiving.getActivePotionEffect(ZombieApocalypse.shotDamage) == null){
			   
		   }
		   else if (event.entityLiving.getActivePotionEffect(ZombieApocalypse.shotDamage).getDuration()==0) {
				event.entityLiving.removePotionEffect(ZombieApocalypse.shotDamage.id);
		   }
		   if (event.entityLiving instanceof EntityUltraZombie){
			   EntityUltraZombie zombie = (EntityUltraZombie)event.entityLiving;
			   if (zombie.worldObj.rainingStrength > 0.5f && zombie.worldObj.canSnowAt(new BlockPos(zombie.posX, zombie.posY, zombie.posZ), true)){
				   zombie.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 1, 10));
				   
			   }
			   if (zombie.getCurrentArmor(0) == null){
				   zombie.setCurrentItemOrArmor(4, new ItemStack(ZombieApocalypse.light));
			   }
		   }
	   }
	   public Block findBlockUnderEntity(Entity parEntity)
	   {
	       int blockX = MathHelper.floor_double(parEntity.posX);
	       int blockY = MathHelper.floor_double(parEntity.posY)-1; 
	       int blockZ = MathHelper.floor_double(parEntity.posZ);
	       return parEntity.worldObj.getBlockState(new BlockPos(blockX, blockY, blockZ)).getBlock();
	   }
	   
	   @SubscribeEvent (priority = EventPriority.HIGHEST)
	   public void renderHand(RenderHandEvent event){
		   //System.out.println("Cancelling");
		   try{
			   if (Minecraft.getMinecraft().thePlayer.isSneaking() && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() == ZombieApocalypse.crossbow){
				   //System.out.println("Cancelling");
				   event.setCanceled(true);
				   
			   }
		   }
		   catch (Exception e){
			   
		   }
		   try{
		   if (Minecraft.getMinecraft().thePlayer.isSneaking() && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof Firearm){
			   //System.out.println("Cancelling");
			   if(((Firearm)Minecraft.getMinecraft().thePlayer.getHeldItem().getItem()).maxcooldown > 10 && Minecraft.getMinecraft().thePlayer.getHeldItem().getTagCompound().getInteger("cooldown") > 0){
				   
			   }
			   else{
				   event.setCanceled(true);
			   }
			   
		   }
		   }
		   catch (Exception e){
			   
		   }
	   }
	   @SubscribeEvent
		public void onEntityConstructing(EntityConstructing event)
		{
			
			if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null)
				// This is how extended properties are registered using our convenient method from earlier
				ExtendedPlayer.register((EntityPlayer) event.entity);
				if (event.entity.worldObj.getWorldType() == ZombieApocalypse.APOCALYPSE){
					
					try{
						((EntityPlayer)event.entity).setSpawnPoint(BronzeGenerator.playerLocation, false);
						System.out.println("Setting spawn point");
						//event.entity.setDead();
					}
					catch(Exception e){
						
					}
					
				}
				
			if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(ExtendedPlayer.EXT_PROP_NAME) == null)
				event.entity.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer((EntityPlayer) event.entity));
		}
	   @SubscribeEvent
	    public void onSound(PlaySoundEvent event) {
	        if (event.name.startsWith("music")) {
	            event.result = null;
	            
	            //event.sound = null;
	        }
	    }
	   
}

