package com.pdogmuncher.zombieapocalypse;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.pdogmuncher.zombieapocalypse.blocks.AluminumBlock;
import com.pdogmuncher.zombieapocalypse.blocks.AluminumOre;
import com.pdogmuncher.zombieapocalypse.blocks.BlockBlock1;
import com.pdogmuncher.zombieapocalypse.blocks.BlockBomb;
import com.pdogmuncher.zombieapocalypse.blocks.BlockCrops;
import com.pdogmuncher.zombieapocalypse.blocks.BlockGrown;
import com.pdogmuncher.zombieapocalypse.blocks.BlockLandmine;
import com.pdogmuncher.zombieapocalypse.blocks.BlockLandmine.MineType;
import com.pdogmuncher.zombieapocalypse.blocks.BlockLight;
import com.pdogmuncher.zombieapocalypse.blocks.BronzeBlock;
import com.pdogmuncher.zombieapocalypse.blocks.BronzeOre;
import com.pdogmuncher.zombieapocalypse.blocks.OilSand;
import com.pdogmuncher.zombieapocalypse.blocks.TileEntityBomb;
import com.pdogmuncher.zombieapocalypse.bronzetools.BattleAxe;
import com.pdogmuncher.zombieapocalypse.bronzetools.BattleHammer;
import com.pdogmuncher.zombieapocalypse.bronzetools.BronzeArmor;
import com.pdogmuncher.zombieapocalypse.bronzetools.BronzeAxe;
import com.pdogmuncher.zombieapocalypse.bronzetools.BronzeHoe;
import com.pdogmuncher.zombieapocalypse.bronzetools.BronzePickaxe;
import com.pdogmuncher.zombieapocalypse.bronzetools.BronzeSpade;
import com.pdogmuncher.zombieapocalypse.bronzetools.BronzeSword;
import com.pdogmuncher.zombieapocalypse.dimension.BlockActivator;
import com.pdogmuncher.zombieapocalypse.dimension.BlockEpicPortal;
import com.pdogmuncher.zombieapocalypse.dimension.WorldProviderEpic;
import com.pdogmuncher.zombieapocalypse.events.BulletDispense;
import com.pdogmuncher.zombieapocalypse.events.CommandConjure;
import com.pdogmuncher.zombieapocalypse.events.FMLEventsHandler;
import com.pdogmuncher.zombieapocalypse.events.FallHandler;
import com.pdogmuncher.zombieapocalypse.events.GuiBuffBar;
import com.pdogmuncher.zombieapocalypse.events.KeyBindings;
import com.pdogmuncher.zombieapocalypse.events.KeyInputHandler;
import com.pdogmuncher.zombieapocalypse.events.MyMessage;
import com.pdogmuncher.zombieapocalypse.events.TerrainEvents;
import com.pdogmuncher.zombieapocalypse.generation.ApocalypseBiome;
import com.pdogmuncher.zombieapocalypse.generation.BronzeGenerator;
import com.pdogmuncher.zombieapocalypse.generation.CustomBiome;
import com.pdogmuncher.zombieapocalypse.generation.HauntedForest;
import com.pdogmuncher.zombieapocalypse.generation.ObsidianSpikes;
import com.pdogmuncher.zombieapocalypse.generation.WorldTypeCustom;
import com.pdogmuncher.zombieapocalypse.items.ItemBackpack;
import com.pdogmuncher.zombieapocalypse.items.ItemBandaid;
import com.pdogmuncher.zombieapocalypse.items.ItemCar;
import com.pdogmuncher.zombieapocalypse.items.ItemCast;
import com.pdogmuncher.zombieapocalypse.items.ItemCustomSeeds;
import com.pdogmuncher.zombieapocalypse.items.ItemDefusalKit;
import com.pdogmuncher.zombieapocalypse.items.ItemDragonFlute;
import com.pdogmuncher.zombieapocalypse.items.ItemEntranceKey;
import com.pdogmuncher.zombieapocalypse.items.ItemFlare;
import com.pdogmuncher.zombieapocalypse.items.ItemHelicopter;
import com.pdogmuncher.zombieapocalypse.items.ItemJet;
import com.pdogmuncher.zombieapocalypse.items.ItemLantern;
import com.pdogmuncher.zombieapocalypse.items.ItemMedPack;
import com.pdogmuncher.zombieapocalypse.items.ItemMobTransporter;
import com.pdogmuncher.zombieapocalypse.items.ItemMusicPlayer;
import com.pdogmuncher.zombieapocalypse.items.ItemPizza;
import com.pdogmuncher.zombieapocalypse.items.ItemTank;
import com.pdogmuncher.zombieapocalypse.items.ItemTent;
import com.pdogmuncher.zombieapocalypse.items.PotionShotDamage;
import com.pdogmuncher.zombieapocalypse.lib.References;
import com.pdogmuncher.zombieapocalypse.mobs.Citizen;
import com.pdogmuncher.zombieapocalypse.mobs.CitizenFemale;
import com.pdogmuncher.zombieapocalypse.mobs.CuteOcelot;
import com.pdogmuncher.zombieapocalypse.mobs.EntityCar;
import com.pdogmuncher.zombieapocalypse.mobs.EntityCosmoDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityEarthDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityFireDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityHelicopter;
import com.pdogmuncher.zombieapocalypse.mobs.EntityIceDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityJet;
import com.pdogmuncher.zombieapocalypse.mobs.EntityKitPicker;
import com.pdogmuncher.zombieapocalypse.mobs.EntityLightningDragon;
import com.pdogmuncher.zombieapocalypse.mobs.EntityMobTransporter;
import com.pdogmuncher.zombieapocalypse.mobs.EntityNametag;
import com.pdogmuncher.zombieapocalypse.mobs.EntityTank;
import com.pdogmuncher.zombieapocalypse.mobs.EntityUltraZombie;
import com.pdogmuncher.zombieapocalypse.mobs.Ferret;
import com.pdogmuncher.zombieapocalypse.mobs.Soldier;
import com.pdogmuncher.zombieapocalypse.mobs.Survivor;
import com.pdogmuncher.zombieapocalypse.mobs.UberFerret;
import com.pdogmuncher.zombieapocalypse.proxy.ProxyCommon;
import com.pdogmuncher.zombieapocalypse.render.ModelRedDragon;
import com.pdogmuncher.zombieapocalypse.render.RenderCar;
import com.pdogmuncher.zombieapocalypse.render.RenderCitizen;
import com.pdogmuncher.zombieapocalypse.render.RenderCitizenFemale;
import com.pdogmuncher.zombieapocalypse.render.RenderCustomModelAsPlayer;
import com.pdogmuncher.zombieapocalypse.render.RenderFerret;
import com.pdogmuncher.zombieapocalypse.render.RenderHelicopter;
import com.pdogmuncher.zombieapocalypse.render.RenderJet;
import com.pdogmuncher.zombieapocalypse.render.RenderKitPicker;
import com.pdogmuncher.zombieapocalypse.render.RenderKitPickerInvisible;
import com.pdogmuncher.zombieapocalypse.render.RenderMobTransporter;
import com.pdogmuncher.zombieapocalypse.render.RenderRedDragon;
import com.pdogmuncher.zombieapocalypse.render.RenderSoldier;
import com.pdogmuncher.zombieapocalypse.render.RenderSurvivor;
import com.pdogmuncher.zombieapocalypse.render.RenderTank;
import com.pdogmuncher.zombieapocalypse.render.RenderUberFerret;
import com.pdogmuncher.zombieapocalypse.render.RenderUltraZombie;
import com.pdogmuncher.zombieapocalypse.staffs.AirStaff;
import com.pdogmuncher.zombieapocalypse.staffs.CosmoStaff;
import com.pdogmuncher.zombieapocalypse.staffs.EarthStaff;
import com.pdogmuncher.zombieapocalypse.staffs.FireStaff;
import com.pdogmuncher.zombieapocalypse.staffs.IceStaff;
import com.pdogmuncher.zombieapocalypse.staffs.LightningStaff;
import com.pdogmuncher.zombieapocalypse.tools.Blueprint;
import com.pdogmuncher.zombieapocalypse.tools.EntityBullet;
import com.pdogmuncher.zombieapocalypse.tools.EntityEarthball;
import com.pdogmuncher.zombieapocalypse.tools.EntityGrenade;
import com.pdogmuncher.zombieapocalypse.tools.EntityGrenade.GrenadeType;
import com.pdogmuncher.zombieapocalypse.tools.EntityFlare;
import com.pdogmuncher.zombieapocalypse.tools.EntityIceball;
import com.pdogmuncher.zombieapocalypse.tools.EntityKnife;
import com.pdogmuncher.zombieapocalypse.tools.EntityRocket;
import com.pdogmuncher.zombieapocalypse.tools.Firearm;
import com.pdogmuncher.zombieapocalypse.tools.Grenade;
import com.pdogmuncher.zombieapocalypse.tools.ItemCrossbow;
import com.pdogmuncher.zombieapocalypse.tools.ItemKnife;
import com.pdogmuncher.zombieapocalypse.tools.ItemSwissArmyKnife;
import com.pdogmuncher.zombieapocalypse.tools.MechaArmor;
import com.pdogmuncher.zombieapocalypse.tools.MilitaryArmor;
import com.pdogmuncher.zombieapocalypse.tools.Projectile;
import com.pdogmuncher.zombieapocalypse.tools.RiotShield;
import com.pdogmuncher.zombieapocalypse.tools.Slingshot;

@Mod(modid = References.MODID, version = References.VERSION, name = References.NAME)
//This is the main mod file, where items/biomes/etc. are created
//For info on specific blocks/items, check their source files. Items will be located in the bronzetools, items, staffs, and tools folders. Blocks are located in the blocks folder


public class ZombieApocalypse
{
	
	//Information on proxy
	@SidedProxy(clientSide = "com.pdogmuncher.zombieapocalypse.proxy.ProxyClient", serverSide = "com.pdogmuncher.zombieapocalypse.lib.proxy.ProxyCommon")
	public static ProxyCommon proxy;
	
	@Instance("zombieapocalypse")
	public static ZombieApocalypse instance;
	
    public static ArrayList <Firearm> firearms = new ArrayList<Firearm>();
    
	public static SimpleNetworkWrapper network;
	
	public static final Block block1 = new BlockBlock1(2000, Material.snow);
	public static final ToolMaterial bronzeMaterial = EnumHelper.addToolMaterial("BronzeMaterial", 2, 202, 5.0f, 1.5f, 22);
	public static final ArmorMaterial armorMat = EnumHelper.addArmorMaterial("BMaterial", "zombieapocalypse:bronze", 15, new int[]{2, 5, 5, 2}, 22);
	public static final ArmorMaterial kevlarArmor = EnumHelper.addArmorMaterial("KMaterial", "zombieapocalypse:kevlar", 15, new int[]{1, 3, 2, 1}, 15);
	public static final ArmorMaterial mechaArmor = EnumHelper.addArmorMaterial("MechaMaterial", "zombieapocalypse:mecha", 50, new int[]{3, 8, 6, 3}, 5);
	public static final ArmorMaterial militaryMaterial = EnumHelper.addArmorMaterial("MilitaryMaterial", "zombieapocalypse:military", 20, new int[]{3, 8, 6, 3}, 15);
	public static final ArmorMaterial backpackMaterial = EnumHelper.addArmorMaterial("BackpackMaterial", "zombieapocalypse:backpack", Integer.MAX_VALUE, new int[]{0, 0, 0, 0}, 0);
	public static final ToolMaterial battleMaterial = EnumHelper.addToolMaterial("BattleMaterial", 1, 202, 5.0f, 2.0f, 22);
	public static ToolMaterial swissknifeMaterial = EnumHelper.addToolMaterial("SwissKnifeMaterial", 2, 1000, 6.0f, 2.0f, 14);
	public static ToolMaterial loboMaterial = EnumHelper.addToolMaterial("LoboMaterial", 1, 1561, 6.0f, 8.0f, 1);
	
	public static ToolMaterial knife_material = EnumHelper.addToolMaterial("KnifeMaterial", 1, -200, 2.0f, 1.0f, 15);
	
	public static final Block bronzeOre = new BronzeOre(2008, new Material(null));
	public static final Block aluminumOre = new AluminumOre(2014, new Material(null));
	public static final Block aluminumBlock = new AluminumBlock(2015, new Material(null));
	public static final Block bronzeBlock = new BronzeBlock(2015, new Material(null));
	public static final Block bomb = new BlockBomb(new Material(null));
	public static final Block light = new BlockLight(new Material(null));
	public static final Block landmine = new BlockLandmine(new Material(null), MineType.EXPLOSIVE);
	public static final Block fire_mine = new BlockLandmine(new Material(null), MineType.INCENDIARY);
	public static final Block alarm_mine = new BlockLandmine(new Material(null), MineType.ALARM);
	public static final Block epicPortal = new BlockEpicPortal();
	public static final Block portalActivator = new BlockActivator();
	
	public static final Item bronzePickaxe = new BronzePickaxe(2001, bronzeMaterial);
	public static final Item bronzeSpade = new BronzeSpade(2002, bronzeMaterial);
	public static final Item bronzeAxe = new BronzeAxe(2003, bronzeMaterial);
	public static final Item bronzeHoe = new BronzeHoe(2004, bronzeMaterial);
	public static final Item bronzeSword = new BronzeSword(2005, bronzeMaterial);
	public static final Item knife = new ItemKnife(knife_material);
	
	public static final Item lobo = new ItemSpade(loboMaterial);
	
	public static final Item swiss_army_knife = new ItemSwissArmyKnife(swissknifeMaterial);
	
	public static final Item battleHammer = new BattleHammer(2006, battleMaterial);
	public static final Item battleAxe = new BattleAxe(2007, battleMaterial);
	public static final Item lightningStaff = new LightningStaff(2009, bronzeMaterial);
	public static final Item fireStaff = new FireStaff(2010, bronzeMaterial);
	public static final Item airStaff = new AirStaff(2011, bronzeMaterial);
	public static final Item cosmoStaff = new CosmoStaff(2012, bronzeMaterial);
	public static final Item earthStaff = new EarthStaff(2013, bronzeMaterial);
	public static final Item iceStaff = new IceStaff(2016, bronzeMaterial);
	
	public static final Item riotShield = new RiotShield(bronzeMaterial);
	public static final Item bullets = new Item();
	public static final Item clip = new Item();
	public static final Item m16_clip = new Item();
	public static final Item m11_clip = new Item();
	public static final Item m500_clip = new Item();
	public static final Item m24_clip = new Item();
	public static final Item rocket_clip = new Item();
	public static final Item chain = new Item();
	public static final Item smg_clip = new Item();
	public static final Item pie_clip = new Item();
	
	public static final Item car = new ItemCar();
	public static final Item mob_transporter = new ItemMobTransporter();
	public static final Item jet = new ItemJet();
	public static final Item helicopter = new ItemHelicopter();
	
	//These are the guns added by the mod. You can use the information I used when creating them as a reference point when creating new ones
	//See the Firearm class for info on what each parameter does. You can use the same gun class to make any rocket/bullet firing gun you want
	
	public static final Firearm m72 = new Firearm(0, 1, "m72", "M72", 2, false, 1, "Rocket Launcher", 2, 1, false, rocket_clip, 0, false, true);
	public static final Firearm m9 = new Firearm(15, 4, "m9", "Beretta M9", 2, false, 10, "Pistol", 0, 15, false, m11_clip, 0.1, false, true);
	public static final Firearm m16 = new Firearm(10, 3, "m16", "M16A4", 2.5, false, 30, "Assault Rifle", 1, 40, false, m16_clip, 0.2, false, false);
	public static final Firearm m500 = new Firearm(25, 30, "m500", "M870", 2, false, 7, "Shotgun", 1, 12, false, null, 0.2, false, true);
	public static final Firearm m24 = new Firearm(20, 50, "m24", "M24", 0.0f, true, 5, "Sniper Rifle", 1, 150, false, m24_clip, 0.025, false, true);
	public static final Firearm m4 = new Firearm(10, 15, "m4", "M4", 2, true, 30, "Carbine", 1, 40, false, m16_clip, 0.05, false, true);
	public static final Firearm m249 = new Firearm(10, 2, "m249", "M249 SAW", 3.5, true, 108, "Light Machine Gun", 2, 40, false, chain, 0.2, false, false);
	public static final Firearm m134 = new Firearm(10, 1, "m134", "M134 Minigun", 2, false, 108, "Machine Gun", 3, 40, false, chain, 0.2, false, false);
	public static final Firearm m240 = new Firearm(15, 2, "m240", "M240 Machine Gun", 3, false, 108, "Machine Gun", 3, 40, false, chain, 0.2, false, false);
	
	//M9 Variants
	public static final Firearm glock = new Firearm(15, 4, "glock", "Glock 17", 2, false, 10, "Pistol", 0, 15, false, m11_clip, 0.1, false, true);
	public static final Firearm usp = new Firearm(13, 4, "usp", "USP Tactical", 2, false, 10, "Pistol", 0, 15, true, m11_clip, 0.1, false, true);
	public static final Firearm jericho_941 = new Firearm(15, 4, "jericho_941", "Jericho 941", 2, false, 10, "Pistol", 0, 15, false, m11_clip, 0.1, false, true);
	public static final Firearm fiveseven = new Firearm(11, 4, "fiveseven", "Five seveN", 1.5, false, 10, "Pistol", 0, 20, false, m11_clip, 0.1, false, true);
	public static final Firearm m45 = new Firearm(17, 4, "m45", "M45A1", 3, false, 10, "Pistol", 0, 15, false, m11_clip, 0.15, false, true);
	//M16/M4 Variants
	public static final Firearm galil = new Firearm(10, 3, "galil", "Galil", 2.2, false, 30, "Assault Rifle", 1, 30, false, m16_clip, 0.2, false, false);
	public static final Firearm g36 = new Firearm(10, 3, "g36", "G36", 2.5, false, 30, "Assault Rifle", 1, 40, false, m16_clip, 0.2, false, false);
	public static final Firearm famas = new Firearm(11, 2, "famas", "FAMAS", 2.5, false, 30, "Assault Rifle", 1, 25, false, m16_clip, 0.2, false, false);
	public static final Firearm hk416 = new Firearm(10, 3, "hk416", "HK416", 2, true, 30, "Carbine", 1, 40, false, m16_clip, 0.05, false, true);
	public static final Firearm sig556xi = new Firearm(10, 3, "sig556xi", "SIG556xi", 2, false, 30, "Carbine", 1, 40, false, m16_clip, 0.01, false, true);
	public static final Firearm aug = new Firearm(9, 4, "aug", "Steyr AUG", 1.75, true, 30, "Assault Rifle", 1, 40, false, m16_clip, 0.1, false, false);
	public static final Firearm sir = new Firearm(10, 7, "sir", "Standard Infantry Rifle", 2, false, 20, "P.I.E. Anti-Zombie Rifle", 1, 40, false, pie_clip, 0.01, false, true);
	//M249 Variants
	public static final Firearm negev = new Firearm(10, 2, "negev", "Negev", 3.5, false, 108, "Light Machine Gun", 2, 35, false, chain, 0.25, false, false);
	public static final Firearm mg4 = new Firearm(10, 3, "mg4", "MG4", 3.5, true, 108, "Light Machine Gun", 2, 50, false, chain, 0.3, false, false);
	//Other Variants
	public static final Firearm awm = new Firearm(25, 60, "awm", "AWM", 0.0f, true, 5, "Sniper Rifle", 1, 200, false, m24_clip, 0.025, false, true);
	public static final Firearm slp = new Firearm(25, 13, "slp", "FN SLP", 2, false, 7, "Shotgun", 1, 10, false, null, 0.2, false, true);
	public static final Firearm ssg08 = new Firearm(20, 60, "ssg08", "SSG08", 0.0f, true, 5, "Sniper Rifle", 1, 100, false, m24_clip, 0, false, true);
	
	public static final Firearm scarh = new Firearm(20, 5, "scarh", "SCAR-H", 7, false, 30, "Battle Rifle", 2, 20, false, m16_clip, 0.1, false, true);
	public static final Firearm shotgun = new Firearm(3, 60, "shotgun", "Shotgun", 5, false, 7, "Shotgun", 1, 10, false, null, 0.3, false, true);
	public static final Firearm rifle = new Firearm(15, 60, "rifle", "Hunting Rifle", 0.5, true, 5, "Sniper Rifle", 1, 30, false, m24_clip, 0.05, false, true);
	public static final Firearm pistol = new Firearm(7, 20, "pistol", "Pistol", 1.5, false, 10, "Pistol", 0, 11, false, m11_clip, 0.2, false, true);
	
	public static final Firearm ak47 = new Firearm(15, 4, "ak47", "AK 47", 3, false, 30, "Assault Rifle", 2, 20, false, m16_clip, 0.22, false, false);
	public static final Firearm ak74 = new Firearm(10, 3, "ak74", "AK 74", 1.5, false, 30, "Assault Rifle", 1, 40, false, m16_clip, 0.2, false, false);
	public static final Firearm dragunov = new Firearm(18, 3, "dragunov", "Dragunov", 10, true, 5, "Sniper Rifle", 1, 150, false, m24_clip, 0.05, false, true);
	public static final Firearm sig_sauer = new Firearm(16, 3, "sig_sauer", "SIG P320", 2, false, 10, "Pistol", 0, 16, false, m11_clip, 0.1, false, true);
	public static final Firearm mp5 = new Firearm(5, 2, "mp5", "MP5", 1.5, false, 50, "Submachine Gun", 0, 11, false, smg_clip, 0.2, false, false);
	public static final Firearm p90 = new Firearm(7, 3, "p90", "P90", 1.5, false, 50, "Submachine Gun", 0, 14, false, smg_clip, 0.2, false, false);
	
	//SMG variants
	public static final Firearm uzi = new Firearm(7, 3, "uzi", "Mini Uzi", 1.75, false, 50, "Submachine Gun", 0, 14, false, smg_clip, 0.2, false, false);
	public static final Firearm mpx = new Firearm(5, 3, "mpx", "SIG MPX", 1, false, 50, "Submachine Gun", 1, 14, false, smg_clip, 0.175, false, false);
	public static final Firearm mp7 = new Firearm(3, 2, "mp7", "MP7", 1, true, 50, "Submachine Gun", 0, 14, false, smg_clip, 0.1, false, false);
	//SIG variants
	public static final Firearm desert_eagle = new Firearm(20, 5, "desert_eagle", "Desert Eagle", 5, false, 10, "Pistol", 0, 16, false, m11_clip, 0.1, false, true);
	public static final Firearm scarhpr = new Firearm(20, 5, "scarhpr", "SCAR-H PR", 5, true, 5, "Precision Rifle", 2, 50, false, m24_clip, 0.1, false, true);
	
	public static final Firearm nighthawk = new Firearm(3, 3, "nighthawk", "MC5 Raven", 0, true, 50, "Submachine Gun", 0, 20, true, smg_clip, 0.1, false, false);
	public static final Firearm chimera = new Firearm(10, 4, "chimera", "MC23 Chimera", 2.5, true, 30, "Assault Rifle/Battle Rifle", 1, 40, false, m16_clip, 0.2, true, false);
	public static final Firearm hydra = new Firearm(5, 30, "hydra", "MC10 Hydra", 2, false, 7, "Shotgun", 1, 10, false, null, 0.3, false, true);
	public static final Firearm falcon = new Firearm(10, 3, "falcon", "MC9 Falcon", 4, false, 10, "Machine Pistol", 0, 10, false, m11_clip, 0.1, false, false);
	public static final Firearm snow_leopard = new Firearm(16, 3, "snow_leopard", "MC7 Snow Leopard", 2, false, 10, "Rifle", 1, 40, false, m16_clip, 0.1, false, true);
	public static final Firearm wolverine = new Firearm(1, 2, "wolverine", "M99 Wolverine", 0.5, false, 108, "Machine Gun", 3, 15, false, chain, 0.5, false, false);
	
	//This is where custom biomes are declared.
	public static BiomeGenBase bronzeBiome;
	public static BiomeGenBase obsidianSpikesBiome;
	public static BiomeGenBase hauntedForestBiome;
	public static BiomeGenBase apocalypseBiome;
	
	public static Item bronzeHelmet;
	public static Item bronzeChestplate;
	public static Item bronzeLeggings;
	public static Item bronzeBoots;
	public static Item militaryHelmet;
	public static Item militaryChestplate;
	public static Item militaryLeggings;
	public static Item militaryBoots;
	public static Item mechaHelmet;
	public static Item mechaChestplate;
	public static Item mechaLeggings;
	public static Item mechaBoots;
	public static Item kevlarChestplate;
	public static Item kevlarHelmet;
	public static Item slingshot = new Slingshot();
	public static Item grenade = new Grenade(GrenadeType.EXPOSIVE);
	public static Item smokeGrenade = new Grenade(GrenadeType.SMOKE);
	public static Item flashbang = new Grenade(GrenadeType.FLASHBANG);
	public static Item incendiary = new Grenade(GrenadeType.INCENDIARY);
	public static Item bronzeIngot = new Item();
	public static Item aluminumIngot = new Item();
	public static Item bullet = new Item();
	public static Item aluminumSheet = new Item();
	public static Item bulletPic = new Item();
	public static Item amulet = new ItemHoe(bronzeMaterial);
	public static Item petroleum = new Item();
	public static Item plastic = new Item();
	public static ItemFood cheese = new ItemFood(3, false);
	public static ItemFood tomato = new ItemFood(2, false);
	public static ItemFood cannedBeans = new ItemFood(3, false);
	public static ItemFood pizza = new ItemPizza(20, true);
	public static Item tomatoSauce = new Item();
	public static ItemTank tankItem = new ItemTank();
	public static Block oilSand = new OilSand();
	public static Block tomatoPlant = new BlockGrown(Material.grass, "tomato_plant", tomato);
	public static Block tomatoSeedling = new BlockCrops(tomatoPlant, tomato, "tomato_seedling");
	public static Item tomatoSeeds = new ItemCustomSeeds(tomatoSeedling);
	public static Item barrel = new Item();
	public static Item stock = new Item();
	public static Item scope = new Item();
	public static Item silencer = new Item();
	public static Item bulletPack = new Item();
	public static Potion shotDamage;
	public static Potion accuracy;
	public static Potion broken_leg;
	public static Potion bleeding;
	public static Potion infected_wound;
	
	public static Item music_player = new ItemMusicPlayer();
	public static Item defusal_kit = new ItemDefusalKit();
	
	//These are blueprints. See the Blueprint class for more details
	public static Item m9Blueprint = new Blueprint(Arrays.asList(
			new ItemStack(aluminumSheet, 3), 
			new ItemStack(Blocks.stone_button), 
			new ItemStack(barrel), 
			new ItemStack(Items.iron_ingot, 5), 
			new ItemStack(m11_clip)
			), m9); 
	public static Item m16Blueprint = new Blueprint(Arrays.asList(
			new ItemStack(aluminumSheet, 6), 
			new ItemStack(Items.iron_ingot, 5), 
			new ItemStack(Blocks.stone_button), 
			new ItemStack(plastic, 5), new ItemStack(barrel), 
			new ItemStack(stock), 
			new ItemStack(m16_clip)
			), m16); 
	public static Item m500Blueprint = new Blueprint(Arrays.asList(
			new ItemStack(aluminumSheet, 8), 
			new ItemStack(Blocks.lever), 
			new ItemStack(barrel, 4), 
			new ItemStack(stock),
			new ItemStack(m500_clip)
			), m500); 
	public static Item m24Blueprint = new Blueprint(Arrays.asList(
			new ItemStack(aluminumSheet, 3),
			new ItemStack(Items.iron_ingot, 10),
			new ItemStack(barrel, 2),
			new ItemStack(stock),
			new ItemStack(scope),
			new ItemStack(m24_clip)
			), m24); 
	public static Item lantern = new ItemLantern();
	public static ItemBackpack backpack = new ItemBackpack();
	public static Item tent = new ItemTent();
	public static Item medpack = new ItemMedPack();
	public static Item bandage = new ItemBandaid();
	public static Item cast = new ItemCast();
	
	public static Entity projectile = new Projectile(Minecraft.getMinecraft().theWorld);
	public static Entity entityBullet = new EntityBullet(Minecraft.getMinecraft().theWorld);
	public static Entity entityGrenade = new EntityGrenade(Minecraft.getMinecraft().theWorld);
	
	public static Achievement getAmuletAchievement;
	public static Achievement lightningStrike;
	public static AchievementPage magicPage;
	public static Achievement snipeAchievement;
	public static Achievement craftGunAchievement;
	public static AchievementPage gunPage;
	
	//These "skin" items don't do anything, I stopped using them, but I haven't removed them as it'll cause some errors since I reference them other places
	public static Item skinglock = new Item();
	public static Item skinusp = new Item();
	public static Item skinjericho941 = new Item();
	public static Item skindeserteagle = new Item();
	public static Item skinuzi = new Item();
	public static Item skingalil = new Item();
	public static Item skinnegev = new Item();
	public static Item skinhk416 = new Item();
	public static Item sking36 = new Item();
	public static Item skinmg4 = new Item();
	public static Item skinawm = new Item();
	public static Item skininsas = new Item();
	public static Item skinfamas = new Item();
	public static Item skinfiveseven = new Item();
	
	public static Item german_tag = new Item();
	public static Item israel_tag = new Item();
	public static Item india_tag = new Item();
	public static Item french_tag = new Item();
	
	
	public static WorldType SURVIVOR;
	public static WorldType SOLDIER;
	public static WorldType CITIZEN;
	public static WorldType APOCALYPSE_EXPERT;
	public static WorldType APOCALYPSE;
	
	public static CreativeTabs tabCustom = new CreativeTabs("Magic") {
        @Override
        public Item getTabIconItem() {
        	
            return amulet;
        }
    };
    public static CreativeTabs tabIndustrial = new CreativeTabs("Industrial") {
        @Override
        public Item getTabIconItem() {
        	
            return aluminumSheet;
        }
    };
    public static CreativeTabs tabGuns = new CreativeTabs("Guns") {
        @Override
        public Item getTabIconItem() {
        	
            return m16;
        }
    };
    public static CreativeTabs tabPistolsSMGs = new CreativeTabs("Pistols") {
        @Override
        public Item getTabIconItem() {
        	
            return m9;
        }
    };
    public static CreativeTabs tabSurvival = new CreativeTabs("Survival") {
        @Override
        public Item getTabIconItem() {
        	
            return backpack;
        }
    };
    public static CreativeTabs tabPVP = new CreativeTabs("PVP") {
        @Override
        public Item getTabIconItem() {
        	
            return Item.getItemFromBlock(bomb); 
        }
    };
    public static CreativeTabs tabAmmo = new CreativeTabs("Military Tools") {
        @Override
        public Item getTabIconItem() {
        	
            return bullets; 
        }
    };
    public static Item entrance_key = new ItemEntranceKey(true);
    public static Item exit_key = new ItemEntranceKey(false);
    public static Item dragon_flute = new ItemDragonFlute();
    public static Item crossbow = new ItemCrossbow();
    public static Item flare = new ItemFlare();
    public static Item flare_lit = new Item();
    public void changeNames(){
    	amulet.setUnlocalizedName("amulet");
    	bronzeIngot.setUnlocalizedName("bronze_ingot");
    	aluminumIngot.setUnlocalizedName("aluminum_ingot");
    	bullet.setUnlocalizedName("bullet");
    	aluminumSheet.setUnlocalizedName("aluminum_sheet");
    	bulletPic.setUnlocalizedName("bullet_pic");
    	grenade.setUnlocalizedName("grenade");
    	grenade.setCreativeTab(tabGuns);
    	block1.setUnlocalizedName("nuke");
    	
    	m72.rocket = true;
    	//m134.rocket = true;
    	m72.registerModelStuff();
    	m9.registerModelStuff();
    	m16.registerModelStuff();
    	m500.registerModelStuff();
    	m24.registerModelStuff();
    	m4.registerModelStuff();
    	m249.registerModelStuff();
    	m134.registerModelStuff();
    	m240.registerModelStuff();
    	dragunov.registerModelStuff();
    	ak47.registerModelStuff();
    	ak74.registerModelStuff();
    	scarh.registerModelStuff();
    	sig_sauer.registerModelStuff();
    	shotgun.registerModelStuff();
    	rifle.registerModelStuff();
    	pistol.registerModelStuff();
    	mp5.registerModelStuff();
    	p90.registerModelStuff();
    	nighthawk.registerModelStuff();
    	chimera.registerModelStuff();
    	hydra.registerModelStuff();
    	falcon.registerModelStuff();
    	snow_leopard.registerModelStuff();
    	wolverine.registerModelStuff();
    	glock.registerModelStuff();
    	usp.registerModelStuff();
    	jericho_941.registerModelStuff();
    	fiveseven.registerModelStuff();
    	desert_eagle.registerModelStuff();
    	galil.registerModelStuff();
    	g36.registerModelStuff();
    	famas.registerModelStuff();
    	hk416.registerModelStuff();
    	negev.registerModelStuff();
    	mg4.registerModelStuff();
    	uzi.registerModelStuff();
    	awm.registerModelStuff();
    	sig556xi.registerModelStuff();
    	mpx.registerModelStuff();
    	slp.registerModelStuff();
    	aug.registerModelStuff();
    	ssg08.registerModelStuff();
    	scarhpr.registerModelStuff();
    	mp7.registerModelStuff();
    	m45.registerModelStuff();
    	sir.registerModelStuff();
    	sir.PIE = true;
    	
    	bomb.setCreativeTab(tabPVP);

    	//This is a handy little method I created. Simply put in this.initItem(<item>, <reference name> <display name> <creative tab>)
    	this.initItem(clip, "clip", "Empty Clip", tabAmmo);
    	this.initItem(m16_clip, "m16_clip", "Assault Rifle Clip", tabAmmo);
    	this.initItem(m11_clip, "m11_clip", "Pistol Clip", tabAmmo);
    	this.initItem(m500_clip, "m500_clip", "Shotgun Clip", tabAmmo);
    	this.initItem(m24_clip, "m24_clip", "Sniper Clip", tabAmmo);
    	this.initItem(smg_clip, "smg_clip", "SMG Clip", tabAmmo);
    	this.initItem(rocket_clip, "rocket_clip", "Rocket", tabAmmo);
    	this.initItem(chain, "chain", "Chain", tabAmmo);
    	this.initItem(bulletPack, "bullet_pack", "Bullet 36-Pack", tabAmmo);
    	
    	this.initItem(petroleum, "petroleum", "Petroleum", tabIndustrial);
    	this.initItem(plastic, "plastic", "Plastic", tabIndustrial);
    	this.initItem(cheese, "cheese", "Cheese", CreativeTabs.tabFood);
    	this.initItem(tomato, "tomato", "Tomato", CreativeTabs.tabFood);
    	this.initItem(cannedBeans, "canned_beans", "Canned Beans", CreativeTabs.tabFood);
    	this.initItem(tomatoSauce, "tomato_sauce", "Tomato Sauce", CreativeTabs.tabFood);
    	this.initItem(pizza, "pizza", "Pizza", CreativeTabs.tabFood);
    	this.initItem(bullets, "bullets", "Bullet 4-Pack", tabAmmo);
    	this.initItem(tankItem, "itemTank", "Machine Gun Nest", CreativeTabs.tabTransport);
    	this.initItem(tomatoSeeds, "tomato_seeds", "Tomato Seeds", CreativeTabs.tabMaterials);
    	this.initItem(grenade, "grenade", "Frag Grenade", tabAmmo);
    	this.initItem(smokeGrenade, "smoke_grenade", "Smoke Grenade", tabAmmo);
    	this.initItem(flashbang, "flashbang", "Flashbang Grenade", tabAmmo);
    	this.initItem(incendiary, "incendiary", "Incendiary Grenade", tabAmmo);
    	this.initItem(barrel, "barrel", "Barrel", tabIndustrial);
    	this.initItem(stock, "stock", "Stock", tabIndustrial);
    	this.initItem(scope, "scope", "Scope", tabIndustrial);
    	this.initItem(silencer, "silencer", "Silencer", tabIndustrial);
    	this.initItem(music_player, "music_player", "Music Player", CreativeTabs.tabMisc);
    	this.initItem(defusal_kit, "defusal_kit", "Defusal Kit", CreativeTabs.tabTools);
    	this.initItem(m9Blueprint, "m9_blueprint", "M9 Blueprint", tabIndustrial);
    	this.initItem(m16Blueprint, "m16_blueprint", "M16A4 Blueprint", tabIndustrial);
    	this.initItem(m500Blueprint, "m500_blueprint", "M500 Blueprint", tabIndustrial);
    	this.initItem(m24Blueprint, "m24_blueprint", "M24 Blueprint", tabIndustrial);
    	this.initItem(swiss_army_knife, "swiss_army_knife", "Swiss Army Knife", tabSurvival);
    	this.initItem(lantern, "lantern", "Lantern", tabSurvival);
    	this.initItem(backpack, "backpack", "Backpack", tabSurvival);
    	this.initItem(tent, "tent", "Tent", tabSurvival);
    	this.initItem(medpack, "medpack", "First-Aid Kit", tabSurvival);
    	this.initItem(bandage, "bandage", "Bandage", tabSurvival);
    	this.initItem(cast, "cast", "Cast", tabSurvival);
    	this.initItem(car, "car", "Car", CreativeTabs.tabTransport);
    	this.initItem(mob_transporter, "mob_transporter", "Mob Transporter", CreativeTabs.tabTransport);
    	this.initItem(jet, "jet", "Jet", CreativeTabs.tabTransport);
    	this.initItem(helicopter, "helicopter", "Helicopter", CreativeTabs.tabTransport);
    	this.initItem(pie_clip, "pie_clip", "P.I.E. Clip", tabAmmo);
    	this.initItem(lobo, "lobo", "Standard Infantry Entrenchment Tool (Lobo)", tabSurvival);
    	this.initItem(entrance_key, "entrance_key", "X-Dimensional Entrance Key", tabCustom);
    	this.initItem(exit_key, "exit_key", "X-Dimensional Exit Key", tabCustom);
    	this.initItem(crossbow, "crossbow", "Crossbow", tabSurvival);
    	this.initItem(new Item(), "crossbow_full", "MISSINGNO", null);
    	this.initItem(new Item(), "crossbow_1", "MISSINGNO", null);
    	this.initItem(new Item(), "crossbow_2", "MISSINGNO", null);
    	this.initItem(flare, "flare", "Flare", tabSurvival);
    	this.initItem(flare_lit, "flare_lit", "MISSINGNO", null);
    	this.initItem(knife, "knife", "Knife", tabSurvival);
    	
    	this.initItem(german_tag, "german_tag", "Germany GSG 9 Tag", tabPVP);
    	this.initItem(israel_tag, "israel_tag", "Israel IDF Tag", tabPVP);
    	this.initItem(india_tag, "india_tag", "India ATS Tag", tabPVP);
    	this.initItem(french_tag, "french_tag", "France GIGN Tag", tabPVP);
    	
    	//Blocks are similar. You'll have to set their tabs manually though
    	this.initBlock(bronzeBlock, "bronze_block", "Block of Bronze");
    	this.initBlock(bomb, "bomb", "Bomb");
    	this.initBlock(light, "light", "Light");
    	this.initBlock(landmine, "landmine", "Landmine");
    	this.initBlock(fire_mine, "fire_mine", "Incendiary Mine");
    	this.initBlock(alarm_mine, "alarm_mine", "Alarm Mine");
    	this.initBlock(epicPortal, "epic_portal", "Epic Portal");
    	landmine.setCreativeTab(ZombieApocalypse.tabAmmo);
    	fire_mine.setCreativeTab(ZombieApocalypse.tabAmmo);
    	alarm_mine.setCreativeTab(ZombieApocalypse.tabAmmo);
    	
    	this.initItem(riotShield, "riot_shield", "Riot Shield", tabAmmo);
    	this.initItem(iceStaff, "ice_staff", "Ice Staff", tabCustom);
    	this.initItem(dragon_flute, "dragon_flute", "Dragon Flute", tabCustom);
    	
    	//If you want to make a gun shoot many bullets at a time, use the burst variables on guns like so
    	m4.burst = 2;
    	shotgun.burst = 6;
    	hydra.burst = 7;
    	wolverine.burst = 4;
    	WeightedRandomChestContent content;
    	ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new net.minecraft.util.WeightedRandomChestContent(new ItemStack(tent), 1, 1, 16));
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(null, 0, new ModelResourceLocation("zombieapocalypse:glock", "inventory"));
    }
    
    //TODO Configuration variables
    public static int armouredVillageRarity;
    public static int militaryBaseRarity;
    public static int stiltHouseRarity;
    public static Configuration config;
    
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	//This is where config files get loaded
    	FMLCommonHandler.instance().bus().register(new KeyInputHandler());
    	KeyBindings.init();
    	config = new Configuration(event.getSuggestedConfigurationFile());

        // loading the configuration from its file
        config.load();
        
        armouredVillageRarity = config.getInt("ArmouredVillageRarity", Configuration.CATEGORY_GENERAL, 2000, 1, Integer.MAX_VALUE, "The likelihood of an armoured village spawning in a chunk (e.g: if the value was 2000 they would spawn every 2000 chunks)");
        militaryBaseRarity = config.getInt("MilitaryBaseRarity", Configuration.CATEGORY_GENERAL, 5000, 1, Integer.MAX_VALUE, "The likelihood of a military base spawning in a chunk");
        stiltHouseRarity = config.getInt("StiltHouseRarity", Configuration.CATEGORY_GENERAL, 1000, 1, Integer.MAX_VALUE, "The likelihood of a survivor stilt house spawning in a chunk");
        // saving the configuration to its file
        config.save();
    }
    public static ItemStack manual;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	LanguageRegistry.instance().addStringLocalization("death.attack.uzombie", "Killed by Z1 Zombie");
    	proxy.registerRenderInformation();
		// some example code
    	this.changeNames();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
        network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.SERVER);
        
        shotDamage = new PotionShotDamage(32, null, false, 0, "Gun Damage", 7, 1);
        accuracy = new PotionShotDamage(33, null, false, 0, "Accuracy", 4, 1);
        broken_leg = new PotionShotDamage(34, null, false, 0, "Broken Leg", 1, 0);
        bleeding = new PotionShotDamage(35, null, false, 0, "Bleeding", 4, 0);
        infected_wound = new PotionShotDamage(36, null, false, 0, "Infected Wound", 6, 0);
        
        GameRegistry.registerTileEntity(TileEntityBomb.class, "tile_entity_bomb");
        
        //DON'T register items like this. Use the initItem method. I created it for a reason
        GameRegistry.registerBlock(block1, "nuke");
        LanguageRegistry.addName(block1, "Nuke");
        GameRegistry.registerBlock(bronzeOre, "bronze_ore");
        LanguageRegistry.addName(bronzeOre, "Copper Ore");
        GameRegistry.registerBlock(tomatoSeedling, "tomato_seedling");
        LanguageRegistry.addName(tomatoSeedling, "Tomato Seedling");
        GameRegistry.registerBlock(tomatoPlant, "tomato_plant");
        LanguageRegistry.addName(tomatoPlant, "Tomato Plant");
        
        GameRegistry.registerBlock(aluminumOre, "aluminum_ore");
       LanguageRegistry.addName(aluminumOre, "Aluminum Ore");
        
        GameRegistry.registerBlock(aluminumBlock, "aluminum_block");
        LanguageRegistry.addName(aluminumBlock, "Block of Aluminum");
        
        GameRegistry.registerBlock(oilSand, "oil_sand");
        LanguageRegistry.addName(oilSand, "Oil Sand");
        
        GameRegistry.registerItem(bronzeAxe, "bronzeAxe");
        LanguageRegistry.addName(bronzeAxe, "Bronze Axe");
        GameRegistry.registerItem(bronzePickaxe, "bronzePickaxe");
        LanguageRegistry.addName(bronzePickaxe, "Bronze Pickaxe");
        GameRegistry.registerItem(bronzeSpade, "bronzeSpade");
        LanguageRegistry.addName(bronzeSpade, "Bronze Shovel");
        GameRegistry.registerItem(bronzeHoe, "bronzeHoe");
        LanguageRegistry.addName(bronzeHoe, "Bronze Hoe");
        GameRegistry.registerItem(bronzeSword, "bronzeSword");
        LanguageRegistry.addName(bronzeSword, "Bronze Sword");
        GameRegistry.registerItem(kevlarChestplate = new BronzeArmor("kevlar_chestplate", kevlarArmor, "tutorial", 1), "kevlar_chestplate");
        GameRegistry.registerItem(kevlarHelmet = new BronzeArmor("kevlar_helmet", kevlarArmor, "tutorial", 0), "kevlar_helmet");
        GameRegistry.registerItem(bronzeHelmet = new BronzeArmor("bronze_helmet", armorMat, "tutorial", 0), "bronze_helmet"); //0 for helmet
        GameRegistry.registerItem(bronzeChestplate = new BronzeArmor("bronze_chestplate", armorMat, "tutorial", 1), "bronze_chestplate"); // 1 for chestplate
        GameRegistry.registerItem(bronzeLeggings = new BronzeArmor("bronze_leggings", armorMat, "tutorial", 2), "bronze_leggings"); // 2 for leggings
        GameRegistry.registerItem(bronzeBoots = new BronzeArmor("bronze_boots", armorMat, "tutorial", 3), "bronze_boots"); // 3 for boots
        GameRegistry.registerItem(mechaHelmet = new MechaArmor("mecha_helmet", mechaArmor, "tutorial", 0), "mecha_helmet"); //0 for helmet
        GameRegistry.registerItem(mechaChestplate = new MechaArmor("mecha_chestplate", mechaArmor, "tutorial", 1), "mecha_chestplate"); // 1 for chestplate
        GameRegistry.registerItem(mechaLeggings = new MechaArmor("mecha_leggings", mechaArmor, "tutorial", 2), "mecha_leggings"); // 2 for leggings
        GameRegistry.registerItem(mechaBoots = new MechaArmor("mecha_boots", mechaArmor, "tutorial", 3), "mecha_boots"); // 3 for boots
        GameRegistry.registerItem(militaryHelmet = new MilitaryArmor("military_helmet", militaryMaterial, "tutorial", 0), "military_helmet"); //0 for helmet
        GameRegistry.registerItem(militaryChestplate = new MilitaryArmor("military_chestplate", militaryMaterial, "tutorial", 1), "military_chestplate"); // 1 for chestplate
        GameRegistry.registerItem(militaryLeggings = new MilitaryArmor("military_leggings", militaryMaterial, "tutorial", 2), "military_leggings"); // 2 for leggings
        GameRegistry.registerItem(militaryBoots = new MilitaryArmor("military_boots", militaryMaterial, "tutorial", 3), "military_boots");
        LanguageRegistry.addName(bronzeHelmet, "Bronze Helmet");
        LanguageRegistry.addName(bronzeChestplate, "Bronze Chestplate");
        LanguageRegistry.addName(bronzeLeggings, "Bronze Leggings");
        LanguageRegistry.addName(bronzeBoots, "Bronze Boots");
        LanguageRegistry.addName(militaryHelmet, "Military Helmet");
        LanguageRegistry.addName(militaryChestplate, "Military Jacket");
        LanguageRegistry.addName(militaryLeggings, "Military Pants");
        LanguageRegistry.addName(militaryBoots, "Military Boots");
        LanguageRegistry.addName(mechaHelmet, "Mecha Helmet");
        LanguageRegistry.addName(mechaChestplate, "Mecha Chestplate");
        LanguageRegistry.addName(mechaLeggings, "Mecha Leggings");
        LanguageRegistry.addName(mechaBoots, "Mecha Boots");
        LanguageRegistry.addName(kevlarChestplate, "Kevlar Vest");
        LanguageRegistry.addName(kevlarHelmet, "Police Helmet");
        GameRegistry.registerItem(slingshot, "slingshot");
        LanguageRegistry.addName(slingshot, "Slingshot");
        GameRegistry.registerItem(bronzeIngot, "bronze_ingot");
        LanguageRegistry.addName(bronzeIngot, "Bronze Ingot");
        GameRegistry.registerItem(aluminumIngot, "aluminum_ingot");
        LanguageRegistry.addName(aluminumIngot, "Aluminum Ingot");
        GameRegistry.registerItem(battleHammer, "battle_hammer");
        LanguageRegistry.addName(battleHammer, "Battle Hammer");
        GameRegistry.registerItem(battleAxe, "battle_axe");
        LanguageRegistry.addName(battleAxe, "Battle Axe");
        GameRegistry.registerItem(lightningStaff, "lightning_staff");
        LanguageRegistry.addName(lightningStaff, "Lightning Staff");
        GameRegistry.registerItem(fireStaff, "fire_staff");
        LanguageRegistry.addName(fireStaff, "Fire Staff");
        GameRegistry.registerItem(airStaff, "air_staff");
        LanguageRegistry.addName(airStaff, "Wind Staff");
        GameRegistry.registerItem(cosmoStaff, "cosmo_staff");
        LanguageRegistry.addName(cosmoStaff, "Cosmo Staff");
        GameRegistry.registerItem(earthStaff, "earth_staff");
        LanguageRegistry.addName(earthStaff, "Earth Staff");
        GameRegistry.registerItem(amulet, "amulet");
        LanguageRegistry.addName(amulet, "Amulet");
        GameRegistry.registerItem(bullet, "bullet");
        LanguageRegistry.addName(bullet, "Bullet");
        GameRegistry.registerItem(aluminumSheet, "aluminum_sheet");
        LanguageRegistry.addName(aluminumSheet, "Aluminum Sheet");
        
        GameRegistry.registerItem(bulletPic, "bullet_pic");
        
        //This is where entities are registered.
        
        //To give them a spawn egg, do it like so: (the config.getInt allows the user to change entity IDs in config files, because otherwise lots of conflicts happen)
        Color c = Color.DARK_GRAY;
        int color_int = (c.getRed() << 16) + (c.getGreen() << 8) + c.getBlue();
        Color c2 = Color.black;
        int color_int2 = (c2.getRed() << 16) + (c2.getGreen() << 8) + c2.getBlue();
        config.load();
        int ferretNum = config.getInt("FerretID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Ferret Entity ID (0 = find unique ID)");
        if (ferretNum == 0){
        	EntityRegistry.registerGlobalEntityID(Ferret.class, "Ferret", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(Ferret.class, "Ferret", ferretNum, color_int, color_int2);
        }
        int tankNum = config.getInt("TankID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Tank Entity ID (0 = find unique ID)");
        if (tankNum == 0){
        	EntityRegistry.registerGlobalEntityID(EntityTank.class, "Tank", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(EntityTank.class, "Tank", tankNum, color_int, color_int2);
        }
        int maleNum = config.getInt("MaleCitizenID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Male Citizen Entity ID (0 = find unique ID)");
        if (maleNum == 0){
        	EntityRegistry.registerGlobalEntityID(Citizen.class, "Male Citizen", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(Citizen.class, "Male Citizen", maleNum, color_int, color_int2);
        }
        
        int femaleNum = config.getInt("FemaleCitizenID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Female Citizen Entity ID (0 = find unique ID)");
        if (femaleNum == 0){
        	EntityRegistry.registerGlobalEntityID(CitizenFemale.class, "Female Citizen", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(CitizenFemale.class, "Female Citizen", femaleNum, color_int, color_int2);
        }
        color_int = (Color.GREEN.getRed() << 16) + (Color.GREEN.getGreen() << 8) + Color.GREEN.getBlue();
        color_int2 = (Color.DARK_GRAY.getRed() << 16) + (Color.DARK_GRAY.getGreen() << 8) + Color.DARK_GRAY.getBlue();
        
        int soldierNum = config.getInt("SoldierID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Soldier Entity ID (0 = find unique ID)");
        if (soldierNum == 0){
        	EntityRegistry.registerGlobalEntityID(Soldier.class, "Soldier", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(Soldier.class, "Soldier", soldierNum, color_int, color_int2);
        }
        color_int = (Color.LIGHT_GRAY.getRed() << 16) + (Color.LIGHT_GRAY.getGreen() << 8) + Color.LIGHT_GRAY.getBlue();
        color_int2 = (Color.RED.getRed() << 16) + (Color.RED.getGreen() << 8) + Color.RED.getBlue();
        
        int zombieNum = config.getInt("Z1ZombieID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Z1 Zombie Entity ID (0 = find unique ID)");
        if (zombieNum == 0){
        	EntityRegistry.registerGlobalEntityID(EntityUltraZombie.class, "Ultra Zombie", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(EntityUltraZombie.class, "Ultra Zombie", zombieNum, color_int, color_int2);
        }
        
        color_int = (Color.GRAY.getRed() << 16) + (Color.GRAY.getGreen() << 8) + Color.GRAY.getBlue();
        color_int2 = (Color.ORANGE.getRed() << 16) + (Color.ORANGE.getGreen() << 8) + Color.ORANGE.getBlue();
        
        int survivorNum = config.getInt("SurvivorID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Survivor Entity ID (0 = find unique ID)");
        if (survivorNum == 0){
        	EntityRegistry.registerGlobalEntityID(Survivor.class, "Survivor", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(Survivor.class, "Survivor", survivorNum, color_int, color_int2);
        }
        
        color_int = (128 << 16) + (0 << 8) + 0;
        color_int2 = (Color.LIGHT_GRAY.getRed() << 16) + (Color.LIGHT_GRAY.getGreen() << 8) + Color.LIGHT_GRAY.getBlue();
        
        int dragonNum = config.getInt("RedDragonID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Red Dragon Entity ID (0 = find unique ID)");
        if (dragonNum == 0){
        	EntityRegistry.registerGlobalEntityID(EntityFireDragon.class, "RedDragon", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(EntityFireDragon.class, "RedDragon", dragonNum, color_int, color_int2);
        }
        
        color_int = (128 << 16) + (64 << 8) + 0;
        color_int2 = (Color.LIGHT_GRAY.getRed() << 16) + (Color.LIGHT_GRAY.getGreen() << 8) + Color.LIGHT_GRAY.getBlue();
        
        int earthdragonNum = config.getInt("EarthDragonID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Brown Dragon Entity ID (0 = find unique ID)");
        if (earthdragonNum == 0){
        	EntityRegistry.registerGlobalEntityID(EntityEarthDragon.class, "EarthDragon", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(EntityEarthDragon.class, "EarthDragon", earthdragonNum, color_int, color_int2);
        }
        
        color_int = (Color.WHITE.getRed() << 16) + (Color.WHITE.getGreen() << 8) + Color.WHITE.getBlue();
        color_int2 = (Color.CYAN.getRed() << 16) + (Color.CYAN.getGreen() << 8) + Color.CYAN.getBlue();
        
        int icedragonNum = config.getInt("IceDragonID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Ice Dragon Entity ID (0 = find unique ID)");
        if (icedragonNum == 0){
        	EntityRegistry.registerGlobalEntityID(EntityIceDragon.class, "IceDragon", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(EntityIceDragon.class, "IceDragon", icedragonNum, color_int, color_int2);
        }
        
        color_int = (Color.BLACK.getRed() << 16) + (Color.BLACK.getGreen() << 8) + Color.BLACK.getBlue();
        color_int2 = (Color.GREEN.getRed() << 16) + (Color.GREEN.getGreen() << 8) + Color.GREEN.getBlue();
        
        int cosmodragonNum = config.getInt("CosmoDragonID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Cosmo Dragon Entity ID (0 = find unique ID)");
        if (cosmodragonNum == 0){
        	
        	EntityRegistry.registerGlobalEntityID(EntityCosmoDragon.class, "CosmoDragon", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(EntityCosmoDragon.class, "CosmoDragon", cosmodragonNum, color_int, color_int2);
        }
        
        color_int = (Color.YELLOW.getRed() << 16) + (Color.YELLOW.getGreen() << 8) + Color.YELLOW.getBlue();
        color_int2 = (Color.CYAN.getRed() << 16) + (Color.CYAN.getGreen() << 8) + Color.CYAN.getBlue();
        
        int voltdragonNum = config.getInt("CosmoDragonID", Configuration.CATEGORY_GENERAL, 0, 0, 255, "The Cosmo Dragon Entity ID (0 = find unique ID)");
        if (voltdragonNum == 0){
        	
        	EntityRegistry.registerGlobalEntityID(EntityLightningDragon.class, "LightningDragon", EntityRegistry.findGlobalUniqueEntityId(), color_int, color_int2);
        }
        else{
        	EntityRegistry.registerGlobalEntityID(EntityLightningDragon.class, "LightningDragon", voltdragonNum, color_int, color_int2);
        }
        config.save();
        
        //Use these types of statements to make the spawn eggs get named properly
        LanguageRegistry.instance().addStringLocalization("entity.Ferret.name", "Ferret");
        LanguageRegistry.instance().addStringLocalization("entity.Survivor.name", "Survivor");
        LanguageRegistry.instance().addStringLocalization("entity.Ultra Zombie.name", "Z-1 Disease Zombie");
        LanguageRegistry.instance().addStringLocalization("entity.Tank.name", "Machine Gun Nest");
        LanguageRegistry.instance().addStringLocalization("entity.Soldier.name", "Soldier");
        LanguageRegistry.instance().addStringLocalization("entity.Male Citizen.name", "Male Citizen");
        LanguageRegistry.instance().addStringLocalization("entity.Female Citizen.name", "Female Citizen");
        LanguageRegistry.instance().addStringLocalization("entity.RedDragon.name", "Red Dragon");
        LanguageRegistry.instance().addStringLocalization("entity.EarthDragon.name", "Brown Dragon");
        LanguageRegistry.instance().addStringLocalization("entity.IceDragon.name", "Ice Dragon");
        LanguageRegistry.instance().addStringLocalization("entity.CosmoDragon.name", "Cosmo Dragon");
        LanguageRegistry.instance().addStringLocalization("entity.LightningDragon.name", "Lightning Dragon");
        
        //You can register entities here if you don't want them to have spawn eggs
        EntityRegistry.registerModEntity(Projectile.class, "Projectile", 2000, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", 300, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 301, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityEarthball.class, "EarthBall", 310, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityIceball.class, "IceBall", 311, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityRocket.class, "Rocket", 303, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityKitPicker.class, "KitPicker", 304, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityNametag.class, "Nametag", 305, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityCar.class, "Car", 306, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityMobTransporter.class, "Mob Transporter", 307, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityJet.class, "Jet", 308, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityHelicopter.class, "Helicopter", 309, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityFlare.class, "Flare", 312, this, 80, 1, true);
        EntityRegistry.registerModEntity(EntityKnife.class, "Knife", 313, this, 80, 1, true);
        
        //This is just where I put my string localizations
        LanguageRegistry.instance().addStringLocalization("entity.zombieapocalypse.Ferret.name", "Ferret");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Magic", "Magic");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Guns", "Large Firearms");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Industrial", "Industrial");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Survival", "Survival Items");
        LanguageRegistry.instance().addStringLocalization("itemGroup.PVP", "PVP");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Pistols", "Pistols & SMGs");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Military Tools", "Military Equipment");
        LanguageRegistry.instance().addStringLocalization("item.Amulet.name", "Amulet");
        LanguageRegistry.instance().addStringLocalization("key.ping", "Scope");
        LanguageRegistry.instance().addStringLocalization("key.pong", "Reset FOV");
        LanguageRegistry.instance().addStringLocalization("key.categories.zombieapocalypse", "Zombie Apocalypse");
        GameRegistry.registerWorldGenerator(new BronzeGenerator(), 100);
        //GameRegistry.registerWorldGenerator(new SurviveCity(), 200);
        bronzeIngot.setCreativeTab(CreativeTabs.tabMaterials);
        aluminumIngot.setCreativeTab(CreativeTabs.tabMaterials);
        aluminumSheet.setCreativeTab(this.tabIndustrial);
        
        //This makes ferrets spawn naturally. Copy to make others spawn naturally
        for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++)
        {
        if (BiomeGenBase.getBiomeGenArray()[i] != null)
        {
        EntityRegistry.addSpawn(Ferret.class, 50, 5, 10, EnumCreatureType.CREATURE, BiomeGenBase.getBiomeGenArray()[i]);
        }
        }
        
        
        
        BiomeManager.strongHoldBiomes.add(new CustomBiome(200));
        if(event.getSide() == Side.CLIENT)
        {
        	//DON'T REGISTER ITEM RENDERERS LIKE THIS. USE THE METHOD
        	RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            
        	renderItem.getItemModelMesher().register(bronzeAxe, 0, new ModelResourceLocation(References.MODID + ":" + ((BronzeAxe) bronzeAxe).getName(), "inventory"));
        	renderItem.getItemModelMesher().register(bronzePickaxe, 0, new ModelResourceLocation(References.MODID + ":" + "bronzePickaxe", "inventory"));
        	renderItem.getItemModelMesher().register(bronzeSpade, 0, new ModelResourceLocation(References.MODID + ":" + "bronzeSpade", "inventory"));
        	renderItem.getItemModelMesher().register(bronzeHoe, 0, new ModelResourceLocation(References.MODID + ":" + "bronzeHoe", "inventory"));
        	renderItem.getItemModelMesher().register(bronzeSword, 0, new ModelResourceLocation(References.MODID + ":" + "bronzeSword", "inventory"));
        	renderItem.getItemModelMesher().register(battleHammer, 0, new ModelResourceLocation(References.MODID + ":" + "battle_hammer", "inventory"));
        	renderItem.getItemModelMesher().register(battleAxe, 0, new ModelResourceLocation(References.MODID + ":" + "battle_axe", "inventory"));
        	
        	renderItem.getItemModelMesher().register(bronzeHelmet, 0, new ModelResourceLocation(References.MODID + ":" + "bronze_helmet", "inventory"));
        	renderItem.getItemModelMesher().register(bronzeChestplate, 0, new ModelResourceLocation(References.MODID + ":" + "bronze_chestplate", "inventory"));
        	renderItem.getItemModelMesher().register(bronzeLeggings, 0, new ModelResourceLocation(References.MODID + ":" + "bronze_leggings", "inventory"));
        	renderItem.getItemModelMesher().register(bronzeBoots, 0, new ModelResourceLocation(References.MODID + ":" + "bronze_boots", "inventory"));
        	renderItem.getItemModelMesher().register(kevlarChestplate, 0, new ModelResourceLocation(References.MODID + ":" + "kevlar_chestplate", "inventory"));
        	renderItem.getItemModelMesher().register(kevlarHelmet, 0, new ModelResourceLocation(References.MODID + ":" + "kevlar_helmet", "inventory"));
        	renderItem.getItemModelMesher().register(mechaHelmet, 0, new ModelResourceLocation(References.MODID + ":" + "mecha_helmet", "inventory"));
        	renderItem.getItemModelMesher().register(mechaChestplate, 0, new ModelResourceLocation(References.MODID + ":" + "mecha_chestplate", "inventory"));
        	renderItem.getItemModelMesher().register(mechaLeggings, 0, new ModelResourceLocation(References.MODID + ":" + "mecha_leggings", "inventory"));
        	renderItem.getItemModelMesher().register(mechaBoots, 0, new ModelResourceLocation(References.MODID + ":" + "mecha_boots", "inventory"));
        	renderItem.getItemModelMesher().register(militaryHelmet, 0, new ModelResourceLocation(References.MODID + ":" + "military_helmet", "inventory"));
        	renderItem.getItemModelMesher().register(militaryChestplate, 0, new ModelResourceLocation(References.MODID + ":" + "military_chestplate", "inventory"));
        	renderItem.getItemModelMesher().register(militaryLeggings, 0, new ModelResourceLocation(References.MODID + ":" + "military_leggings", "inventory"));
        	renderItem.getItemModelMesher().register(militaryBoots, 0, new ModelResourceLocation(References.MODID + ":" + "military_boots", "inventory"));
        	
        	renderItem.getItemModelMesher().register(bronzeIngot, 0, new ModelResourceLocation(References.MODID + ":" + "bronze_ingot", "inventory"));
        	renderItem.getItemModelMesher().register(aluminumIngot, 0, new ModelResourceLocation(References.MODID + ":" + "aluminum_ingot", "inventory"));
        	renderItem.getItemModelMesher().register(aluminumSheet, 0, new ModelResourceLocation(References.MODID + ":" + "aluminum_sheet", "inventory"));
        	
        	renderItem.getItemModelMesher().register(slingshot, 0, new ModelResourceLocation(References.MODID + ":" + "slingshot", "inventory"));
        	renderItem.getItemModelMesher().register(grenade, 0, new ModelResourceLocation(References.MODID + ":" + "grenade", "inventory"));
        	renderItem.getItemModelMesher().register(lightningStaff, 0, new ModelResourceLocation(References.MODID + ":" + "lightning_staff", "inventory"));
        	renderItem.getItemModelMesher().register(fireStaff, 0, new ModelResourceLocation(References.MODID + ":" + "fire_staff", "inventory"));
        	renderItem.getItemModelMesher().register(airStaff, 0, new ModelResourceLocation(References.MODID + ":" + "air_staff", "inventory"));
        	renderItem.getItemModelMesher().register(cosmoStaff, 0, new ModelResourceLocation(References.MODID + ":" + "cosmo_staff", "inventory"));
        	renderItem.getItemModelMesher().register(earthStaff, 0, new ModelResourceLocation(References.MODID + ":" + "earth_staff", "inventory"));
        	
        	renderItem.getItemModelMesher().register(amulet, 0, new ModelResourceLocation(References.MODID + ":" + "amulet", "inventory"));
        	renderItem.getItemModelMesher().register(bullet, 0, new ModelResourceLocation(References.MODID + ":" + "bullet", "inventory"));
        	renderItem.getItemModelMesher().register(bulletPic, 0, new ModelResourceLocation(References.MODID + ":" + "bullet_pic", "inventory"));
        	
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(block1), 0, new ModelResourceLocation(References.MODID + ":" + "nuke", "inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(bronzeOre), 0, new ModelResourceLocation(References.MODID + ":" + "bronze_ore", "inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(aluminumOre), 0, new ModelResourceLocation(References.MODID + ":" + "aluminum_ore", "inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(aluminumBlock), 0, new ModelResourceLocation(References.MODID + ":" + "aluminum_block", "inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(oilSand), 0, new ModelResourceLocation(References.MODID + ":" + "oil_sand", "inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(landmine), 0, new ModelResourceLocation(References.MODID + ":" + "landmine", "inventory"));
        	
        	//Register entity renderers over here
        	RenderingRegistry.registerEntityRenderingHandler(Ferret.class, new RenderFerret(Minecraft.getMinecraft().getRenderManager(), new ModelWolf(), 0.5F));
        	
        	RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderFerret(Minecraft.getMinecraft().getRenderManager(), new CuteOcelot(), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(UberFerret.class, new RenderUberFerret(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelZombie(), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(EntityTank.class, new RenderTank(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelBoat(), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(Citizen.class, new RenderCitizen(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelPlayer(0.0f, false), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(CitizenFemale.class, new RenderCitizenFemale(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelPlayer(0.0f, true), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(Soldier.class, new RenderSoldier(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelZombie(0.0f, false), 0.5F, "zombieapocalypse:textures/entity/Soldier.png"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityKitPicker.class, new RenderKitPicker(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelZombie(0.0f, false), 0.5F, "minecraft:textures/entity/steve.png"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityUltraZombie.class, new RenderUltraZombie(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelZombie(0.0f, false), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(EntityNametag.class, new RenderKitPickerInvisible(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelZombie(0.0f, false), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(EntityCar.class, new RenderCar(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelMinecart(), 0.5F, "zombieapocalypse:textures/entity/car.png"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityMobTransporter.class, new RenderMobTransporter(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelMinecart(), 0.5F, "zombieapocalypse:textures/entity/mob_transporter.png"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityJet.class, new RenderJet(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelMinecart(), 0.5F, "zombieapocalypse:textures/entity/jet.png"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityHelicopter.class, new RenderHelicopter(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelMinecart(), 0.5F, "zombieapocalypse:textures/entity/helicopter.png"));
        	RenderingRegistry.registerEntityRenderingHandler(Survivor.class, new RenderSurvivor(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelPlayer(0.0f, false), 0.5F));
        	RenderingRegistry.registerEntityRenderingHandler(EntityFireDragon.class, new RenderRedDragon(Minecraft.getMinecraft().getRenderManager(), new ModelRedDragon(1.0f), 10.0f, "red_dragon"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityEarthDragon.class, new RenderRedDragon(Minecraft.getMinecraft().getRenderManager(), new ModelRedDragon(1.0f), 10.0f, "brown_dragon"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityIceDragon.class, new RenderRedDragon(Minecraft.getMinecraft().getRenderManager(), new ModelRedDragon(1.0f), 10.0f, "ice_dragon"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityCosmoDragon.class, new RenderRedDragon(Minecraft.getMinecraft().getRenderManager(), new ModelRedDragon(1.0f), 10.0f, "cosmo_dragon"));
        	RenderingRegistry.registerEntityRenderingHandler(EntityLightningDragon.class, new RenderRedDragon(Minecraft.getMinecraft().getRenderManager(), new ModelRedDragon(1.0f), 10.0f, "lightning_dragon"));
        	
        	//RenderingRegistry.registerEntityRenderingHandler(Projectile.class, new RenderProjectile(Minecraft.getMinecraft().getRenderManager(), new net.minecraft.client.model.ModelHumanoidHead(), 0.5F));//New yourmodel
        	RenderingRegistry.registerEntityRenderingHandler(Projectile.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), Items.clay_ball, renderItem));
        	RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), grenade, renderItem));
        	RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), bulletPic, renderItem));
        	RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), bulletPic, renderItem));
        	RenderingRegistry.registerEntityRenderingHandler(EntityEarthball.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), Items.clay_ball, renderItem));
        	RenderingRegistry.registerEntityRenderingHandler(EntityIceball.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), Items.snowball, renderItem));
        	RenderingRegistry.registerEntityRenderingHandler(EntityFlare.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), flare_lit, renderItem));
        	RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), knife, renderItem));
        	
        	lightningStaff.setCreativeTab(tabCustom);
        	amulet.setCreativeTab(tabCustom);
        	fireStaff.setCreativeTab(tabCustom);
        	airStaff.setCreativeTab(tabCustom);
        	cosmoStaff.setCreativeTab(tabCustom);
        	earthStaff.setCreativeTab(tabCustom);
        	bullet.setCreativeTab(tabAmmo);
        	amulet.setUnlocalizedName("Amulet");
        	amulet.setMaxDamage(50);
        	
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderCustomModelAsPlayer(new ModelWolf(), "Wolf.png", 1.0f));
        BiomeManager.addSpawnBiome(new CustomBiome(200));
        
        //Crafting recipes
        //TODO Food
        GameRegistry.addShapelessRecipe(new ItemStack(cheese), Items.milk_bucket);
        GameRegistry.addShapelessRecipe(new ItemStack(tomatoSauce), tomato, Items.bucket);
        GameRegistry.addShapedRecipe(new ItemStack(pizza), " CC", " TT", " BB", 'T', tomatoSauce, 'B', Items.bread, 'C', cheese);
        GameRegistry.addShapelessRecipe(new ItemStack(tomatoSeeds, 4), tomato);
        //TODO Smelting
        GameRegistry.addShapedRecipe(new ItemStack(Blocks.bedrock), "LOL", "LOL", "LOL", 'L', Items.lava_bucket, 'O', Blocks.obsidian);
        GameRegistry.addRecipe(new ItemStack(aluminumBlock), "AAA", "AAA", "AAA", 'A', aluminumIngot);
        GameRegistry.addSmelting(new ItemStack(bronzeOre), new ItemStack(bronzeIngot), 20.0f);
        GameRegistry.addSmelting(new ItemStack(aluminumOre), new ItemStack(aluminumIngot), 20.0f);
        GameRegistry.addSmelting(aluminumBlock, new ItemStack(aluminumSheet), 20.0f);
        GameRegistry.addSmelting(oilSand, new ItemStack(petroleum), 20.0f);
        GameRegistry.addSmelting(petroleum, new ItemStack(plastic), 20.0f);
        //TODO Bronzeage
        GameRegistry.addRecipe(new ItemStack(crossbow), "TS ", "TTS", "TS ", 'T', Items.stick, 'S', Items.string);
        GameRegistry.addRecipe(new ItemStack(bronzeAxe), "BB ", "BS ", " S ", 'B', bronzeIngot, 'S', Items.stick);
        GameRegistry.addRecipe(new ItemStack(bronzePickaxe), "BBB", " S ", " S ", 'B', bronzeIngot, 'S', Items.stick);
        GameRegistry.addRecipe(new ItemStack(bronzeHoe), "BB ", " S ", " S ", 'B', bronzeIngot, 'S', Items.stick);
        GameRegistry.addRecipe(new ItemStack(bronzeSword), " B ", " B ", " S ", 'B', bronzeIngot, 'S', Items.stick);
        GameRegistry.addRecipe(new ItemStack(bronzeSpade), " B ", " S ", " S ", 'B', bronzeIngot, 'S', Items.stick);
        System.out.println("Got through tools");
        GameRegistry.addRecipe(new ItemStack(bronzeHelmet), "BBB", "B B", "   ", 'B', bronzeIngot);
        GameRegistry.addRecipe(new ItemStack(bronzeChestplate), "B B", "BBB", "BBB", 'B', bronzeIngot);
        GameRegistry.addRecipe(new ItemStack(bronzeLeggings), "BBB", "B B", "B B", 'B', bronzeIngot);
        GameRegistry.addRecipe(new ItemStack(bronzeBoots), "   ", "B B", "B B", 'B', bronzeIngot);
        GameRegistry.addRecipe(new ItemStack(battleHammer), "BBB", "BSB", " S ", 'B', bronzeIngot, 'S', Items.stick);
        GameRegistry.addRecipe(new ItemStack(battleAxe), "BSB", "BSB", " S ", 'B', bronzeIngot, 'S', Items.stick);
        GameRegistry.addRecipe(new ItemStack(amulet), "BBB", "BDB", "BBB", 'B', bronzeIngot, 'D', Items.diamond);
        GameRegistry.addRecipe(new ItemStack(slingshot), "TST", " T ", " T ", 'T', Items.stick, 'S', Items.string);
        GameRegistry.addShapelessRecipe(new ItemStack(airStaff), fireStaff, lightningStaff, iceStaff, cosmoStaff, earthStaff);
        //TODO weapons
        GameRegistry.addRecipe(new ItemStack(alarm_mine), "PPP", "ANA", "AAA", 'P', Blocks.stone_pressure_plate, 'A', this.aluminumIngot, 'N', Blocks.noteblock);
        GameRegistry.addRecipe(new ItemStack(grenade), " A ", "ATA", " A ", 'A', aluminumIngot, 'T', Blocks.tnt);
        GameRegistry.addRecipe(new ItemStack(bullet, 5), " BB", "BFG", " BB", 'B', bronzeIngot, 'F', Items.flint, 'G', Items.gunpowder);
        GameRegistry.addRecipe(new ItemStack(rocket_clip, 1), " AA", "AGG", " AA", 'A', aluminumIngot, 'F', Items.flint, 'G', Blocks.tnt);
        GameRegistry.addShapelessRecipe(new ItemStack(bullets), bullet, bullet, bullet, bullet);
        GameRegistry.addShapedRecipe(new ItemStack(bulletPack), "BBB", "BBB", "BBB", 'B', bullets);
        GameRegistry.addShapedRecipe(new ItemStack(clip), "P P", "PBP", "PPP", 'P', plastic, 'B', bullet);
        GameRegistry.addShapelessRecipe(new ItemStack(m16_clip), clip, bullet, bullets, bullets, bullets, bullets, bullets, bullets, bullets);
        GameRegistry.addShapelessRecipe(new ItemStack(m11_clip), clip, bullet, bullets, bullets);
        GameRegistry.addShapelessRecipe(new ItemStack(m500_clip), clip, bullet, bullets, bullet);
        GameRegistry.addShapelessRecipe(new ItemStack(m24_clip), clip, bullets);
        GameRegistry.addShapelessRecipe(new ItemStack(pie_clip), clip, bullets, bullets, bullets, bullets, bullet, bullet, bullet);
        GameRegistry.addShapelessRecipe(new ItemStack(smg_clip), clip, bulletPack, bullets, bullets, bullets, bullet);
        GameRegistry.addShapedRecipe(new ItemStack(chain), "BS ", "BS ", "BS ", 'B', bulletPack, 'S', Items.string);
        GameRegistry.addShapedRecipe(new ItemStack(barrel, 3), "AAA", "   ", "AAA", 'A', aluminumSheet);
        GameRegistry.addShapedRecipe(new ItemStack(scope, 3), "AAA", "GGG", "AAA", 'A', aluminumSheet, 'G', Blocks.glass);
        GameRegistry.addShapedRecipe(new ItemStack(stock, 3), "AAA", "AA ", "A  ", 'A', aluminumSheet);
        GameRegistry.addShapedRecipe(new ItemStack(pistol), "RAA", " AA", " BP", 'A', aluminumSheet, 'B', Blocks.stone_button, 'R', barrel, 'P', m11_clip); 
        GameRegistry.addShapedRecipe(new ItemStack(rifle), " S ", "BAK", " CT", 'S', scope, 'B', barrel, 'A', aluminumSheet, 'K', stock, 'C', m24_clip, 'T', Blocks.stone_button); 
        GameRegistry.addShapedRecipe(new ItemStack(shotgun), "BBK", "BBT", " C ", 'S', scope, 'B', barrel, 'A', aluminumSheet, 'K', stock, 'C', m500_clip, 'T', Blocks.stone_button); 
        GameRegistry.addShapedRecipe(new ItemStack(sir), "BAA", "AAS", " CT", 'B', barrel, 'A', aluminumSheet, 'C', pie_clip, 'T', Blocks.stone_button);
        
        GameRegistry.addShapelessRecipe(new ItemStack(this.m16Blueprint), Items.dye, Items.paper, m16_clip);
        GameRegistry.addShapelessRecipe(new ItemStack(m9Blueprint), Items.dye, Items.paper, m11_clip);
        GameRegistry.addShapelessRecipe(new ItemStack(m500Blueprint), Items.dye, Items.paper, m500_clip);
        GameRegistry.addShapelessRecipe(new ItemStack(m24Blueprint), Items.dye, Items.paper, m24_clip);
        
        //Survival stuff
        GameRegistry.addShapelessRecipe(new ItemStack(flare), Blocks.redstone_torch, Items.gunpowder);
        GameRegistry.addShapedRecipe(new ItemStack(lobo), " A ", "ASA", " S ", 'A', aluminumSheet, 'S', Items.stick);
        GameRegistry.addShapedRecipe(new ItemStack(lantern), "III", "GTG", "III", 'I', Items.iron_ingot, 'G', Blocks.glass, 'T', Blocks.torch);
        GameRegistry.addShapedRecipe(new ItemStack(this.swiss_army_knife), "ACS", "PKP", "PPP", 'A', Items.iron_axe, 'C', Items.iron_pickaxe, 'S', Items.iron_shovel, 'K', Items.iron_sword, 'P', this.plastic);
        //GameRegistry.addShapedRecipe(new ItemStack(tent), " W ", "WDW", "CBF", 'W', Blocks.wool, 'D', Blocks.acacia_door, 'C', Blocks.crafting_table, 'B', Items.bed, 'F', Blocks.furnace);
        GameRegistry.addShapedRecipe(new ItemStack(tent), " W ", "WDW", "CBF", 'W', Blocks.wool, 'D', Items.oak_door, 'C', Blocks.crafting_table, 'B', Items.bed, 'F', Blocks.furnace);
        //GameRegistry.addShapelessRecipe(new ItemStack(tent), Blocks.dirt);
        //GameRegistry.addShapedRecipe(new ItemStack(tent), " W ", "WDW", "CBF", 'W', Blocks.wool, 'D', Blocks.dark_oak_door, 'C', Blocks.crafting_table, 'B', Items.bed, 'F', Blocks.furnace);
        //GameRegistry.addShapedRecipe(new ItemStack(tent), " W ", "WDW", "CBF", 'W', Blocks.wool, 'D', Blocks.spruce_door, 'C', Blocks.crafting_table, 'B', Items.bed, 'F', Blocks.furnace);
        //GameRegistry.addShapedRecipe(new ItemStack(tent), " W ", "WDW", "CBF", 'W', Blocks.wool, 'D', Blocks.birch_door, 'C', Blocks.crafting_table, 'B', Items.bed, 'F', Blocks.furnace);
        //GameRegistry.addShapedRecipe(new ItemStack(tent), " W ", "WDW", "CBF", 'W', Blocks.wool, 'D', Blocks.jungle_door, 'C', Blocks.crafting_table, 'B', Items.bed, 'F', Blocks.furnace);
        GameRegistry.addShapedRecipe(new ItemStack(backpack), "LLL", "LCL", "LLL", 'L', Items.leather, 'C', Blocks.chest);
        GameRegistry.addShapedRecipe(new ItemStack(medpack), "III", "BTW", "III", 'I', Items.iron_ingot, 'B', bandage, 'T', Items.glass_bottle, 'W', Blocks.wool);
        GameRegistry.addShapedRecipe(new ItemStack(cast, 9), "W W", "W W", "WWW", 'W', Blocks.wool);
        GameRegistry.addShapedRecipe(new ItemStack(bandage, 2), " W ", "WWW", " W ", 'W', Blocks.wool);
        GameRegistry.addShapedRecipe(new ItemStack(car), "LLL", "DMD", "DDD", 'L', Items.redstone, 'D', Items.diamond, 'M', Items.minecart);
        GameRegistry.addShapedRecipe(new ItemStack(mob_transporter), "LLL", "DMD", "DDD", 'L', Items.lead, 'D', Items.diamond, 'M', Items.boat);
        GameRegistry.addShapedRecipe(new ItemStack(knife), " A ", " A ", " S ", 'A', aluminumIngot, 'S', Items.stick);
        
        manual = new ItemStack(Items.written_book);
        NBTTagCompound compound = new NBTTagCompound();
        try {
			compound = JsonToNBT.func_180713_a("{pages:[\"The Zombie Apocalypse Survival Guide\", \"Section 1: Zombies\"]}");
		} catch (NBTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        compound.setString("title", "Zombie Apocalypse Survival Manual");
        compound.setString("author", "Ferret_Skill");
        manual.setTagCompound(compound);
        
        
        getAmuletAchievement  = new Achievement("amuletachievement", "AmuletAchievement", 0, 0, amulet, null);
        lightningStrike  = new Achievement("lightningachievement", "LightningAchievement", 2, 0, lightningStaff, getAmuletAchievement);
        craftGunAchievement = new Achievement("craftgunachievement", "CraftGunAchievement", 0, 0, m9, null);
        snipeAchievement = new Achievement("snipeachievement", "SnipeAchievement", 2, 0, m24, craftGunAchievement);
        this.addAchievementName("AmuletAchievement", "Ancient Power");
        this.addAchievementDesc("AmuletAchievement", "Create an Amulet");
        this.addAchievementName("LightningAchievement", "Lightning Strike");
        this.addAchievementDesc("LightningAchievement", "Use a staff to bring down a bolt of lightning!");
        
        this.addAchievementName("CraftGunAchievement", "Arsenal");
        this.addAchievementDesc("CraftGunAchievement", "Craft a gun");
        this.addAchievementName("SnipeAchievement", "Sniper");
        this.addAchievementDesc("SnipeAchievement", "Shoot a mob with a gun");
        lightningStrike.setSpecial();
        magicPage = new AchievementPage("Magic Achievements", getAmuletAchievement, lightningStrike);
        gunPage = new AchievementPage("Gun Achievements", snipeAchievement, craftGunAchievement);
        
        BlockDispenser.dispenseBehaviorRegistry.putObject(this.bullet, new BulletDispense());
        AchievementPage.registerAchievementPage(magicPage);
        AchievementPage.registerAchievementPage(gunPage);
        bronzeBiome = new CustomBiome(154).setColor(353825).setBiomeName("Bronze Plains");
        obsidianSpikesBiome = new ObsidianSpikes(155).setColor(353825).setBiomeName("Obsidian Spikes");
        hauntedForestBiome = new HauntedForest(156).setColor(353825).setBiomeName("Haunted Forest");
        apocalypseBiome = new ApocalypseBiome(157).setColor(353825).setBiomeName("Apocalypse Biome");
        
        BiomeDictionary.registerBiomeType(bronzeBiome, Type.DEAD);
        BiomeManager.addSpawnBiome(bronzeBiome);
        BiomeDictionary.registerBiomeType(apocalypseBiome, Type.SPARSE);
        BiomeManager.addSpawnBiome(apocalypseBiome);
        BiomeDictionary.registerBiomeType(hauntedForestBiome, Type.DENSE);
        BiomeManager.addSpawnBiome(hauntedForestBiome);
        BiomeDictionary.registerBiomeType(obsidianSpikesBiome, Type.DEAD);
        BiomeManager.addSpawnBiome(obsidianSpikesBiome);
        MinecraftForge.EVENT_BUS.register(new GuiBuffBar(Minecraft.getMinecraft()));
        MinecraftForge.TERRAIN_GEN_BUS.register(new TerrainEvents());
        FallHandler handler = new FallHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(new FMLEventsHandler());
        System.out.println("Init");
        //NetworkRegistry.INSTANCE.registerGuiHandler(this, this.proxy);
        instance = this;
        
        
        
        DimensionManager.registerProviderType(3, WorldProviderEpic.class, true);
        DimensionManager.registerDimension(3, 3);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    	LanguageRegistry.instance().addStringLocalization("generator.Default", "Default (No Zombies)");

    	APOCALYPSE = new WorldTypeCustom(7, "Apocalypse");
    	LanguageRegistry.instance().addStringLocalization("generator.Apocalypse", "Apocalypse");
    	
    	
    	
    	
    	
    	
    	SOLDIER = new WorldTypeCustom(3, "Soldier");
    	LanguageRegistry.instance().addStringLocalization("generator.Soldier", "Lv1: Soldier");
    	
    	CITIZEN = new WorldTypeCustom(4, "Citizen");
    	LanguageRegistry.instance().addStringLocalization("generator.Citizen", "Lv2: Citizen");
    	
    	SURVIVOR = new WorldTypeCustom(5, "Survivor");
    	LanguageRegistry.instance().addStringLocalization("generator.Survivor", "Lv3: Survivor");
    	
    	APOCALYPSE_EXPERT = new WorldTypeCustom(6, "ApocalypseExpert");
    	LanguageRegistry.instance().addStringLocalization("generator.ApocalypseExpert", "Lv4: Apocalypse Expert");
    	
    	WorldType CUSTOM = new WorldTypeCustom(9, "Epic");
    	LanguageRegistry.instance().addStringLocalization("generator.Epic", "Magical");
    	
    	
    }
    public static void addVillagePiece(Class c, String s) 
    { 
    try 
    { 
    //MapGenStructureIO.func_143031_a(c, s); 
    MapGenStructureIO.registerStructureComponent(c, s);
    } 
    catch (Exception localException) {} 
    } 

    public static void addVillageCreationHandler(VillagerRegistry.IVillageCreationHandler v) 
    { 
    VillagerRegistry.instance().registerVillageCreationHandler(v); 
    }
    public void initItem(Item item, String name, String display, CreativeTabs tab){
    	item.setUnlocalizedName(name);
    	GameRegistry.registerItem(item, name);
    	LanguageRegistry.addName(item, display);
    	item.setCreativeTab(tab);
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(References.MODID + ":" + name, "inventory"));
    }
    public void initBlock(Block block, String name, String display){
    	block.setUnlocalizedName(name);
    	GameRegistry.registerBlock(block, name);
        LanguageRegistry.addName(block, display);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(References.MODID + ":" + name, "inventory"));
    }
    private void addAchievementName(String ach, String name)
    {
            LanguageRegistry.instance().addStringLocalization("achievement." + ach, "en_US", name);
    }

    private void addAchievementDesc(String ach, String desc)
    {
            LanguageRegistry.instance().addStringLocalization("achievement." + ach + ".desc", desc);
    }
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	Potion[] potionTypes = null;

    	for (Field f : Potion.class.getDeclaredFields()) {
    			f.setAccessible(true);
    			try {
    				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
    					Field modfield = Field.class.getDeclaredField("modifiers");
    					modfield.setAccessible(true);
    					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

    					potionTypes = (Potion[])f.get(null);
    					final Potion[] newPotionTypes = new Potion[256];
    					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
    					f.set(null, newPotionTypes);
    				}
    			}
    			catch (Exception e) {
    				System.err.println("Severe error, please report this to the mod author:");
    				System.err.println(e);
    			}
    	}

    }
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        // register server commands

    	event.registerServerCommand(new CommandConjure());
    }
}
