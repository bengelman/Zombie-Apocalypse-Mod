package com.pdogmuncher.zombieapocalypse.tools;

import java.util.HashSet;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.events.KeyBindings;
import com.pdogmuncher.zombieapocalypse.lib.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
/*
 * Welcome to the firearm class! It's (mostly) fully documented, and you'll find it super useful
 */
public class Firearm extends Item
{
	//Variables pertaining to firearms
	  public int damage;
	  public int maxcooldown;
	  public String name;
	  public double inaccuracy;
	  public boolean epicscope;
	  public int currentLoad;
	  public int clipSize;
	  int reloadTime;
	  public Item clipType;
	  public int bulletsLeft;
	  public int burst;
	  public int burstNow;
	  double recoilnow = 0;
	  public String weaponType;
	  public boolean rocket;
	  boolean loadedYet;
	  public boolean silenced;
	  public int weight = 0;
	  public int range;
	  public Firearm silencedVersion = null;
	  public Firearm scopedVersion = null;
	  public double spray = 0;
	  public boolean deathcombo = false;
	  public double recoil = 0;
	  public double recovery;
	  public boolean semiauto;
	  public boolean hasFired;
	  public long globalTicks;
	  public boolean PIE = false;
	  //public int firedthisburst = 0;
	  public boolean firing;
	 
	  //Damage is the damage in half-hearts. Cooldown is the minimum number of ticks between shots (fire rate)
	  //Name is it's reference name. DisplayName is it's display name. Inaccuracy is the amount of recoil it has,
	  //In degrees. Epicscope is whether or not you can scope with the gun. ClipSize is the number of bullets
	  //It holds. Description is the text under it's name (usually the type of gun it is, e.g: sniper). Weight
	  //Is the amount of slowness it gives the user, range is it's range in blocks, Silenced is whether it is
	  //a silent gun, cliptype is the item it uses to fill itself up other than bullets (usually a clip), spray
	  //is it's inaccuracy, deathcombo is whether the gun turns from an assault rifle to a sniper when you scope
	  //with it (like the chimera), and semiauto is whether it only fires one burst per button press (i.e: isn't
	  //fully automatic)
  public Firearm(int damage, int cooldown, String name, String displayName, double inaccuracy, boolean epicscope, int clipSize, String description, int weight, int range, boolean silenced, Item cliptype, double spray, boolean deathcombo, boolean semiauto)
  {
    super();
    this.reloadTime = 20;
    this.maxcooldown = cooldown;
    this.damage = damage;
    setUnlocalizedName(name);
    GameRegistry.registerItem(this, name);
    LanguageRegistry.addName(this, displayName);
    this.name = name;
    this.setMaxDamage(clipSize);
    this.inaccuracy = inaccuracy;
    this.epicscope = epicscope;
    this.clipSize = clipSize;
    this.currentLoad = clipSize;
    this.weaponType = description;
    this.weight = weight;
    this.range = range;
    this.silenced = silenced;
    this.clipType = cliptype;
    this.spray = spray;
    this.deathcombo = deathcombo;
    this.recovery = 1.0;
    this.semiauto = semiauto;
    ZombieApocalypse.firearms.add(this);
    
    this.setMaxStackSize(1);
  }
  public int getMaxItemUseDuration(ItemStack par1ItemStack)
  {
          return 100;
  }
  //Call this method somewhere in the main class to register the gun's rendering
  public void registerModelStuff(){
	  RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	  String realname = this.getUnlocalizedName().substring(5);
	  
	  if (realname.endsWith("silenced")){
		  realname = realname.substring(0, realname.length() - "silenced".length());
		  System.out.println("Silenced name: " + realname);
	  }
	  else if (realname.endsWith("scoped")){
		  realname = realname.substring(0, realname.length() - "scoped".length());
		  System.out.println("Silenced name: " + realname);
	  }
	  else{
		  if (this.clipType == ZombieApocalypse.m11_clip || this.clipType == ZombieApocalypse.smg_clip){
			  setCreativeTab(ZombieApocalypse.tabPistolsSMGs);
		  }
		  else{
			  setCreativeTab(ZombieApocalypse.tabGuns);
		  }
		  
	  }
	  if (this.silencedVersion != null){
		  System.out.println("Adding Recipe");
		  GameRegistry.addShapelessRecipe(new ItemStack(silencedVersion), this, ZombieApocalypse.silencer);
	  }
	  if (this.scopedVersion != null){
		  System.out.println("Adding Recipe");
		  GameRegistry.addShapelessRecipe(new ItemStack(scopedVersion), this, ZombieApocalypse.scope);
	  }
	  
	    renderItem.getItemModelMesher().register(this, 0, new ModelResourceLocation(References.MODID + ":" + realname, "inventory"));
	    
	    if (silencedVersion != null){
	    	silencedVersion.registerModelStuff();
	    }
	    if (scopedVersion != null){
	    	scopedVersion.registerModelStuff();
	    }
  }
  public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
	  //this.onItem
	  //System.out.println("Duration: " + ((EntityPlayer)par3Entity).getItemInUseDuration());
	  globalTicks++;
	  NBTTagCompound tag = par1ItemStack.getTagCompound();
	  if (tag == null) {
	  	tag = new NBTTagCompound();
	  	tag.setInteger("load", this.currentLoad);
	  	par1ItemStack.setTagCompound(tag);
	  	
	  }
	  if (KeyBindings.reload.isKeyDown() && par5){
		  par1ItemStack.getTagCompound().setBoolean("reloading", true);
	  }
	  if (!loadedYet && !par2World.isRemote){
		  loadedYet = true;
	  }
	  if (par1ItemStack.getTagCompound().getInteger("cooldown") > 0){
		  par1ItemStack.getTagCompound().setInteger("cooldown", par1ItemStack.getTagCompound().getInteger("cooldown") - 1);
	  }
	  EntityPlayer par3EntityPlayer = (EntityPlayer) par3Entity;
	  if (!par2World.isRemote){
		  if (par1ItemStack.getItemDamage() != this.clipSize - par1ItemStack.getTagCompound().getInteger("load")){
			  par1ItemStack.setItemDamage(this.clipSize - par1ItemStack.getTagCompound().getInteger("load"));
		  }
	  }
	  
	  if (par3EntityPlayer.getHeldItem() != null & par1ItemStack.getItem().getUnlocalizedName().equalsIgnoreCase(this.getUnlocalizedName())){
		  
		  if (!par2World.isRemote){
			  if (par1ItemStack.getItemDamage() != this.clipSize - par1ItemStack.getTagCompound().getInteger("load")){
				  par1ItemStack.setItemDamage(this.clipSize - par1ItemStack.getTagCompound().getInteger("load"));
			  }
			  
		  }
		  if (!par3EntityPlayer.isSwingInProgress && par1ItemStack.getTagCompound().getInteger("cooldown") == 0 && par3EntityPlayer.getHeldItem().getItem() != null & par3EntityPlayer.getHeldItem().getItem().getUnlocalizedName().equals(this.getUnlocalizedName())){
			  
			  if (this.weight > 0){
				  par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2, weight-1));
			  }
			  if (par1ItemStack.getTagCompound().getBoolean("reloading")){
				  if (clipType != null){
					  if (par2World.isRemote){
					  }
					  else if (par3EntityPlayer.capabilities.isCreativeMode){
						  par1ItemStack.getTagCompound().setInteger("load", this.clipSize);
						  par1ItemStack.getTagCompound().setBoolean("reloading", false);
						  par3EntityPlayer.isSwingInProgress = true;
						  par3EntityPlayer.swingProgress = 0;
						  par1ItemStack.getTagCompound().setInteger("cooldown", this.reloadTime);
						  par2World.playSoundAtEntity(par3EntityPlayer, "random.click", 1.0f, 1.0f);
						  par1ItemStack.setItemDamage(this.clipSize - par1ItemStack.getTagCompound().getInteger("load"));
						  return;
					  }
					  else if (par3EntityPlayer.inventory.consumeInventoryItem(clipType)){
						  if (!par2World.isRemote){
							  par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(ZombieApocalypse.bullet, par1ItemStack.getTagCompound().getInteger("load")));
						  }
						  par2World.playSoundAtEntity(par3EntityPlayer, "random.click", 1.0f, 1.0f);
						  par1ItemStack.getTagCompound().setInteger("cooldown", this.reloadTime);
						  par1ItemStack.getTagCompound().setInteger("load", this.clipSize);
						  par1ItemStack.getTagCompound().setBoolean("reloading", false);
						  par3EntityPlayer.isSwingInProgress = true;
						  par3EntityPlayer.swingProgress = 0;
						  par1ItemStack.setItemDamage(this.clipSize - par1ItemStack.getTagCompound().getInteger("load"));
						  return;
					  }
				  }
				  if ((par1ItemStack.getTagCompound().getInteger("load") >= this.clipSize || (!par3EntityPlayer.capabilities.isCreativeMode && !par3EntityPlayer.inventory.hasItem(ZombieApocalypse.bullet) && !par3EntityPlayer.inventory.hasItem(clipType)))){
					  par1ItemStack.getTagCompound().setBoolean("reloading", false);
					  return;
				  }
				  par3EntityPlayer.isSwingInProgress = true;
				  par3EntityPlayer.swingProgress = 0;
				  if (!rocket){
					  par1ItemStack.getTagCompound().setInteger("load", par1ItemStack.getTagCompound().getInteger("load")+1);
					  par1ItemStack.setItemDamage(this.clipSize - par1ItemStack.getTagCompound().getInteger("load"));
				  }
				  if (!par3EntityPlayer.capabilities.isCreativeMode && !par2World.isRemote && !rocket){
					  par3EntityPlayer.inventory.consumeInventoryItem(ZombieApocalypse.bullet);
				  }
				  
			  }
		  }
	  }
	  firing = false;
	  if (par1ItemStack != par3EntityPlayer.getCurrentEquippedItem()){
		  return;
	  }
	  if (!Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()){
		  //System.out.println("Hasn't Fired");
		  if (!par3EntityPlayer.worldObj.isRemote){
			  //System.out.println("Resetting fire");
		  }
		  hasFired = false;
	  }
	  if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()){
		  
		  //System.out.println("Has fired: " + this.hasFired);
		  if (this.hasFired && this.semiauto){
			  if (!par2World.isRemote){
				  
				  //System.out.println("Returning null");
			  }
			  
			  this.recoil -= recovery;
			  return;
		  }
		  if (!par2World.isRemote){
			  this.hasFired = true;
		  }
		  
		  if (!par2World.isRemote){
			  //System.out.println("Executing on server");
		  }
		  par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		  
		  if (par1ItemStack.getTagCompound().getInteger("cooldown") > 0){
			  return;
		  }
		  if (!par2World.isRemote){
			  
		  }
		  //System.out.println("In Use");
		  //par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		  for (int num = 0; num <= burst; num++){
			  if(par1ItemStack.getTagCompound().getInteger("load") > 0 && !(par1ItemStack.getTagCompound().getBoolean("reloading") && num > 0))
			    {
				  par1ItemStack.getTagCompound().setBoolean("reloading", false); 
				  if (!silenced){
					  par2World.playSoundAtEntity(par3EntityPlayer, "random.explode", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
					  //par2World.playSoundAtEntity(par3EntityPlayer, "zombieapocalypse:music.bangarang", 0.5F, 1.0f);
					  if (par2World.isRemote){
						  //Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("zombieapocalypse:music.bangarang")));
						  
					  }
					}
				  else{
					  par2World.playSoundAtEntity(par3EntityPlayer, "random.explode", 0.1f, 1.0f);
				  }
			        par3EntityPlayer.addVelocity(-par3EntityPlayer.motionX, 0, -par3EntityPlayer.motionZ);
			        if (par2World.isRemote && rocket){
			        	EntityThrowable p = new EntityBullet(par2World, par3EntityPlayer, 0, 0, PIE);
			        	par3EntityPlayer.addVelocity(-p.motionX, -p.motionY, -p.motionZ);
			        	p.setDead();
			        }
			        //par3EntityPlayer.setAngles(0, 0);
			        //par3EntityPlayer.rotationPitch = 0;
			        //par3EntityPlayer.rotationYaw = 0;
			        if (!par2World.isRemote || true)
			        {
			        	
			        	EntityThrowable p = new EntityBullet(par2World, par3EntityPlayer, this.damage, this.range, PIE);
			        	if (recoil < 20){
			        		//par3EntityPlayer.rotationPitch += recoil;
			        	}
			        	else{
			        		//par3EntityPlayer.rotationPitch += 20;
			        	}
			        	recoil = this.inaccuracy;
			        	if (recoil < 20){
			        		par3EntityPlayer.rotationPitch -= recoil;
			        	}
			        	else{
			        		par3EntityPlayer.rotationPitch -= 20;
			        	}
			        	this.recoilnow += this.inaccuracy;
			        	if (rocket){
			        		p = new EntityRocket(par2World, par3EntityPlayer);
			        	}
			        	if (!par2World.isRemote){
			        		par2World.spawnEntityInWorld(p);
			        	}
			            
			            if (!rocket){
			            	//((EntityBullet)p).damage = this.damage;
			            	if (par3EntityPlayer.getActivePotionEffect(ZombieApocalypse.shotDamage) != null){
			            		((EntityBullet)p).damage += par3EntityPlayer.getActivePotionEffect(ZombieApocalypse.shotDamage).getAmplifier() * 5;
			            	}
			            	if (deathcombo && par3EntityPlayer.isSneaking()){
			            		System.out.println("Epic Boost");
			            		((EntityBullet)p).damage *= 3;
			            	}
			            	((EntityBullet)p).start = par3EntityPlayer.getPosition();
			            	//((EntityBullet)p).range = this.range;
				            p.addVelocity(p.motionX * 3, p.motionY * 3, p.motionZ * 3);
			            }
			            else{
			            	p.addVelocity(p.motionX, p.motionY, p.motionZ);
			            	par3EntityPlayer.addVelocity(-p.motionX / 2, -p.motionY / 2, -p.motionZ / 2);
			            }
			            if (!par2World.isRemote){
			            	par1ItemStack.getTagCompound().setInteger("cooldown", maxcooldown);
			            	par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() +1);
			            	if (deathcombo && par3EntityPlayer.isSneaking()){
			            		par1ItemStack.getTagCompound().setInteger("cooldown", maxcooldown * 10);
			            	}
			            	/*burstNow++;
				            if (burstNow >= burst){
				            	cooldown = maxcooldown;
				            	burstNow = 0;
				            }*/
			            }
			            double inaccuracy = spray;
			            if (deathcombo && par3EntityPlayer.isSneaking()){
		            		inaccuracy = 0;
		            	}
			            if (par3EntityPlayer.getActivePotionEffect(ZombieApocalypse.accuracy) != null){
			            	inaccuracy /= par3EntityPlayer.getActivePotionEffect(ZombieApocalypse.accuracy).getAmplifier() + 2;
			            }
			            double inaccuracymod = 1.0D;
			            inaccuracymod += (recoilnow * 0.1);
			            //inaccuracy += (((double)par1ItemStack.getTagCompound().getInteger("firedthisburst")) * 0.025D);
			            //System.out.println("Fired This Burst Inaccuracy: " + (((double)par1ItemStack.getTagCompound().getInteger("firedthisburst")) * 0.025D));
			            if (par3EntityPlayer.isAirBorne){
			            	inaccuracymod++;
			            }
			            if (recoil > 20){
			            	inaccuracymod ++;
			            }
			            double miss = ((Math.random() * ((inaccuracy * inaccuracymod) * 2)) - (inaccuracy * inaccuracymod));
			            p.motionX += miss;
			            miss = ((Math.random() * ((inaccuracy * inaccuracymod) * 2)) - (inaccuracy * inaccuracymod));
			            //p.motionY += miss;
			            miss = ((Math.random() * ((inaccuracy * inaccuracymod) * 2)) - (inaccuracy * inaccuracymod));
			            p.motionZ += miss;
			            
			            if (num == burst || this.burst < 4){
			            	par1ItemStack.getTagCompound().setInteger("load", par1ItemStack.getTagCompound().getInteger("load") - 1);
			            }
			            
			            if (par1ItemStack.getTagCompound().getInteger("load") == 0){
			            	par1ItemStack.getTagCompound().setBoolean("reloading", true);
			            }
			            if (num == this.burst){
			            	
				            if (par1ItemStack.getTagCompound().getInteger("firedthisburst") < 10){
				            	par1ItemStack.getTagCompound().setInteger("firedthisburst", par1ItemStack.getTagCompound().getInteger("firedthisburst") + 1);
				            }
				            else{
				            	par1ItemStack.getTagCompound().setInteger("firedthisburst", 10);
				            }
			            }
			            
			            
			        }
			        double inaccuracy = this.inaccuracy / 2;
		            if (par3EntityPlayer.getActivePotionEffect(ZombieApocalypse.accuracy) != null){
		            	inaccuracy /= par3EntityPlayer.getActivePotionEffect(ZombieApocalypse.accuracy).getAmplifier() + 2;
		            }
		            double inaccuracymod = 1.0D;
		            if (par3EntityPlayer.isAirBorne){
		            	inaccuracymod++;
		            }
		            if (par3EntityPlayer.isSneaking()){
		            	inaccuracymod -= 0.5;
		            }
		            if (par3EntityPlayer.ridingEntity != null){
		            	inaccuracymod /= 4;
		            }
		            double miss = ((Math.random() * ((inaccuracy * inaccuracymod) * 2)) - (inaccuracy * inaccuracymod));
		            //par3EntityPlayer.rotationPitch -= inaccuracy * inaccuracymod;
		            miss = ((Math.random() * ((inaccuracy * inaccuracymod) * 2)) - (inaccuracy * inaccuracymod));
		            //par3EntityPlayer.rotationYaw += miss;
		            
		            //miss = ((Math.random() * ((inaccuracy * inaccuracymod) * 2)) - (inaccuracy * inaccuracymod));
		            //p.motionZ += miss;
			    }
		  }
		    
		    if (par1ItemStack.getTagCompound().getInteger("load") < 1){
		    	par1ItemStack.getTagCompound().setBoolean("reloading", true);
		    	//reloading = true;
		    }
		    firing = true;
	  }
	  else{
		  if (this.recoilnow > 0){
			  if (par1ItemStack.getTagCompound().getInteger("cooldown") > 0 && this.maxcooldown < 5 && par1ItemStack.getTagCompound().getInteger("cooldown") < 5){
				  
			  }
			  else{
				  this.recoilnow -= recovery;
				  par3EntityPlayer.rotationPitch += recovery;
			  }
			  if (this.recoilnow > this.inaccuracy * 6){
				  if (par1ItemStack.getTagCompound().getInteger("cooldown") > 0 && this.maxcooldown < 5){
					  
				  }
				  else{
					  this.recoilnow -= recovery;
					  par3EntityPlayer.rotationPitch += recovery;
				  }
			  }
			  
		  }
		  else{
			  if (recoilnow < 0){
				  
			  }
			  recoilnow = 0;
		  }
		  
	  }
  }
  @Override
  public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
  {
	  par3List.add(weaponType);
	  if (par1ItemStack.getTagCompound() != null){
		 par3List.add("Ammo: " + par1ItemStack.getTagCompound().getInteger("load"));
	  }
	  par3List.add("Damage: " + this.damage);
	  par3List.add("Fire Rate: " + this.maxcooldown);
	  par3List.add("Spread: " + this.spray);
	  par3List.add("Recoil: " + this.inaccuracy);
	  par3List.add("Range: " + this.range);
  }
  @Override
  public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	  super.onCreated(itemStack, world, player);
	  itemStack.setTagCompound(new NBTTagCompound());
	}
  public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int uses){
	  ModelResourceLocation location = new ModelResourceLocation("zombieapocalypse:" + this.name, "inventory");
	  if ((stack.getDisplayName().equalsIgnoreCase("glock") || player.inventory.hasItem(ZombieApocalypse.india_tag)) && this == ZombieApocalypse.m9){
		  stack.setStackDisplayName("Glock");
		  location = new ModelResourceLocation("zombieapocalypse:glock", "inventory");
		  return location;
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("usp") || player.inventory.hasItem(ZombieApocalypse.german_tag)) && this == ZombieApocalypse.m9){
		  stack.setStackDisplayName("USP");
		  location = new ModelResourceLocation("zombieapocalypse:usp", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("jericho 941") || player.inventory.hasItem(ZombieApocalypse.israel_tag)) && this == ZombieApocalypse.m9){
		  stack.setStackDisplayName("Jericho 941");
		  location = new ModelResourceLocation("zombieapocalypse:jericho_941", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("desert eagle") || player.inventory.hasItem(ZombieApocalypse.israel_tag)) && this == ZombieApocalypse.sig_sauer){
		  stack.setStackDisplayName("Desert Eagle");
		  location = new ModelResourceLocation("zombieapocalypse:desert_eagle", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("uzi") || player.inventory.hasItem(ZombieApocalypse.india_tag) || player.inventory.hasItem(ZombieApocalypse.german_tag)) && this == ZombieApocalypse.mp5){
		  stack.setStackDisplayName("Uzi");
		  location = new ModelResourceLocation("zombieapocalypse:uzi", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("galil") || player.inventory.hasItem(ZombieApocalypse.israel_tag)) && this == ZombieApocalypse.m16){
		  stack.setStackDisplayName("Galil");
		  location = new ModelResourceLocation("zombieapocalypse:galil", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("negev") || player.inventory.hasItem(ZombieApocalypse.israel_tag)) && this == ZombieApocalypse.m249){
		  stack.setStackDisplayName("Negev");
		  location = new ModelResourceLocation("zombieapocalypse:negev", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("hk416") || player.inventory.hasItem(ZombieApocalypse.german_tag)) && this == ZombieApocalypse.m4){
		  stack.setStackDisplayName("HK416");
		  location = new ModelResourceLocation("zombieapocalypse:hk416", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("g36") || player.inventory.hasItem(ZombieApocalypse.german_tag)) && this == ZombieApocalypse.m16){
		  stack.setStackDisplayName("G36");
		  location = new ModelResourceLocation("zombieapocalypse:g36", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("mg4") || player.inventory.hasItem(ZombieApocalypse.german_tag)) && this == ZombieApocalypse.m249){
		  stack.setStackDisplayName("MG4");
		  location = new ModelResourceLocation("zombieapocalypse:mg4", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("awm") || player.inventory.hasItem(ZombieApocalypse.german_tag)) && this == ZombieApocalypse.m24){
		  stack.setStackDisplayName("AWM");
		  location = new ModelResourceLocation("zombieapocalypse:awm", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("insas") || player.inventory.hasItem(ZombieApocalypse.india_tag)) && this == ZombieApocalypse.m16){
		  stack.setStackDisplayName("INSAS");
		  location = new ModelResourceLocation("zombieapocalypse:INSAS", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("famas") || player.inventory.hasItem(ZombieApocalypse.french_tag)) && this == ZombieApocalypse.m16){
		  stack.setStackDisplayName("FAMAS");
		  location = new ModelResourceLocation("zombieapocalypse:famas", "inventory");
	  }
	  else if ((stack.getDisplayName().equalsIgnoreCase("five seven") || player.inventory.hasItem(ZombieApocalypse.french_tag)) && this == ZombieApocalypse.m9){
		  stack.setStackDisplayName("Five seveN");
		  location = new ModelResourceLocation("zombieapocalypse:fiveseven", "inventory");
	  }
	  return location;
  }
}

