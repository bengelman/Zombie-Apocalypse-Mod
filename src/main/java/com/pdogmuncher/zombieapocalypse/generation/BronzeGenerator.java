package com.pdogmuncher.zombieapocalypse.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.mobs.Citizen;
import com.pdogmuncher.zombieapocalypse.mobs.CitizenFemale;
import com.pdogmuncher.zombieapocalypse.mobs.EntityUltraZombie;
import com.pdogmuncher.zombieapocalypse.mobs.Ferret;
import com.pdogmuncher.zombieapocalypse.mobs.Soldier;
import com.pdogmuncher.zombieapocalypse.mobs.Survivor;

import net.minecraft.block.BlockLadder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.IWorldGenerator;
/*
 * This is where I put my custom generation code, for structures and such
 */
public class BronzeGenerator implements IWorldGenerator 
{
	public enum CityBuildingType{
		HOUSE, GREENHOUSE, OFFICE, WELL, TREEFARM, PASTURE
	}
	public ArrayList <BlockPos> invalidSpawns = new ArrayList <BlockPos>();
	public CityBuildingType[] weightedBuildings = new CityBuildingType[]{CityBuildingType.HOUSE, 
			CityBuildingType.HOUSE, 
			CityBuildingType.HOUSE, 
			CityBuildingType.HOUSE, 
			CityBuildingType.GREENHOUSE, 
			CityBuildingType.TREEFARM,
			CityBuildingType.OFFICE,
			CityBuildingType.PASTURE};
	static boolean cutscene;
	public static final int CITY_LIMITS = 40;
	public static BlockPos playerLocation;
	
	public BlockPos[] hotels = new BlockPos[]{
			new BlockPos(16, 0, 16),
	};
	public BlockPos[] publixes = new BlockPos[]{
			new BlockPos(-32, 0, 32),
	};
	public BlockPos[] researchFacilities = new BlockPos[]{
			new BlockPos(-64, 0, -64)
	};
	public BlockPos[] houses = new BlockPos[]{
			new BlockPos(0, 0, 16),
			new BlockPos(16, 0, 0),
			new BlockPos(32, 0, 16),
			new BlockPos(32, 0, 32),
			new BlockPos(-16, 0, 16),
			new BlockPos(-16, 0, -16),
	};
	public BlockPos[] clinics = new BlockPos[]{
			new BlockPos(0, 0, 48),
	};
	 public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	 {
	  switch (world.provider.getDimensionId())
	  {
	   case 3: generateEpic(world, random, chunkX*16, chunkZ*16); break;
	   case 0: generateSurface(world, random, chunkX*16, chunkZ*16);
	  }
	 }

	  

	  private void generateSurface(World world, Random random, int blockX, int blockZ) 
	 {
		  for(int k = 0; k < 100; k++) {
			  int Xcoord = blockX + random.nextInt(16);
			  int Ycoord = random.nextInt(60);
			  int Zcoord = blockZ + random.nextInt(16);
			  BlockPos pos = new BlockPos(Xcoord, Ycoord, Zcoord);
			  (new WorldGenMinable(ZombieApocalypse.bronzeOre.getDefaultState(), 5)).generate(world, random, pos);
		  }
		  for(int k = 0; k < 1; k++) {
			  if (random.nextInt(10) < 5){
				  int Xcoord = blockX + random.nextInt(16);
				  int Ycoord = random.nextInt(60);
				  int Zcoord = blockZ + random.nextInt(16);
				  BlockPos pos = new BlockPos(Xcoord, Ycoord, Zcoord);
				  (new WorldGenMinable(ZombieApocalypse.oilSand.getDefaultState(), 50)).generate(world, random, pos);
			  }
			  
		  }
		  if (world.getBiomeGenForCoords(new BlockPos(blockX, 60, blockZ)) == BiomeGenBase.desert || 
				  world.getBiomeGenForCoords(new BlockPos(blockX, 60, blockZ)) == BiomeGenBase.desertHills){
			  for(int k = 0; k < 4; k++) {
				  int Xcoord = blockX + random.nextInt(16);
				  int Ycoord = random.nextInt(60);
				  int Zcoord = blockZ + random.nextInt(16);
				  BlockPos pos = new BlockPos(Xcoord, Ycoord, Zcoord);
				  (new WorldGenMinable(ZombieApocalypse.oilSand.getDefaultState(), 50)).generate(world, random, pos);
			  }
		  }
		  /*
		  if (random.nextInt(1000) < 2){
			  new City().generate(world, random, blockX, 127, blockZ);
			  EntityVillager vill = new EntityVillager(world);
			  vill.setPosition(blockX+5, 130, blockZ + 4);
			  //world.setBlockState(new BlockPos(blockX-5, 129, blockZ-5), Blocks.mob_spawner.getDefaultState());
			  //TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(blockX-5, 129, blockZ-5));
			  //tileentitymobspawner.getSpawnerBaseLogic().setEntityName("Ferret");
		  }*/
		  for(int k = 0; k < 100; k++) {
			  int Xcoord = blockX + random.nextInt(16);
			  int Ycoord = random.nextInt(12);
			  int Zcoord = blockZ + random.nextInt(16);
			  BlockPos pos = new BlockPos(Xcoord, Ycoord, Zcoord);
			  (new WorldGenMinable(ZombieApocalypse.aluminumOre.getDefaultState(), 5)).generate(world, random, pos);
		  }
		  if ((world.getWorldType() != ZombieApocalypse.APOCALYPSE || (Math.abs(blockX) > CITY_LIMITS || Math.abs(blockZ) > CITY_LIMITS))){
			  if (random.nextBoolean()){
				  for (int y = 0; y < 200; y++){
					  if (world.getLight(new BlockPos(blockX, y, blockZ)) > 14 && world.canBlockSeeSky(new BlockPos(blockX, y, blockZ))){
						  EntityUltraZombie zombie = new EntityUltraZombie(world);
						  zombie.setPosition(blockX, y, blockZ);
						  world.spawnEntityInWorld(zombie);
						  break;
					  }
				  }
			  }
			  
		  }
		  
		  if (world.getWorldType() == ZombieApocalypse.APOCALYPSE && !world.isRemote){
			  world.setSpawnPoint(new BlockPos(0, 10, 0));
			  //System.out.println("Generating");
			  if (blockX == 0 && blockZ == 0 && playerLocation == null){
				  boolean keepGoing = false;
				  for (int y = 0; y < 200; y++){
					  if (world.getBlockState(new BlockPos(blockX - 3, y, blockZ - 2)).getBlock() == Blocks.planks){
						  playerLocation = new BlockPos(2, 2, 2);
						  break;
					  }
					  if (world.canBlockSeeSky(new BlockPos(blockX, y, blockZ)) && world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
						  
						  //world.setSpawnPoint(new BlockPos(blockX, y + 5, blockZ));
						  this.playerLocation = new BlockPos(blockX, y + 1, blockZ);
						  /*
						  System.out.println("Setting location");
						  for (int x = world.getSpawnPoint().getX() - 11; x < world.getSpawnPoint().getX() + 12; x++){
							  for (int z = world.getSpawnPoint().getZ() - 11; z < world.getSpawnPoint().getZ() + 12; z++){
								  world.setBlockState(new BlockPos(x, y, z), Blocks.planks.getDefaultState());
							  }
						  }
						  */
						  for (int y2 = y + 1; y2 < 200; y2++){
							  for (int x = - 3; x < 3; x++){
								  for (int z = - 3; z < + 3; z++){
									  world.setBlockState(new BlockPos(x, y2, z), Blocks.air.getDefaultState());
								  }
							  }
						  }
						  /*
						  for (int x = world.getSpawnPoint().getX() - 11; x < world.getSpawnPoint().getX() + 12; x++){
							  for (int z = world.getSpawnPoint().getZ() - 11; z < world.getSpawnPoint().getZ() + 12; z++){
								  world.setBlockState(new BlockPos(x, y + 4, z), Blocks.glass.getDefaultState());
							  }
						  }
						  for (int y2 = 0; y2 < 5; y2++){
							  for (int x = blockX - 11; x < blockX + 12; x++){
								  if (!(x == 0 && (y == 1 || y == 2))){
									  world.setBlockState(new BlockPos(x, y2 + y, blockZ - 11), Blocks.planks.getDefaultState());
								  }
								  
								  world.setBlockState(new BlockPos(x, y2 + y, blockZ + 11), Blocks.planks.getDefaultState());
							  }
						  }
						  for (int y2 = 0; y2 < 5; y2++){
							  for (int z = blockZ - 11; z < blockX + 12; z++){
								  world.setBlockState(new BlockPos(blockX - 11, y2 + y, z), Blocks.planks.getDefaultState());
								  world.setBlockState(new BlockPos(blockZ + 11, y2 + y, z), Blocks.planks.getDefaultState());
							  }
						  }*/
						  new City().generateHerosHouse(world, random, -3, y, -3);
						  System.out.println("Generated house at " + y);
						  break;
					  }
				  }
			  }
			  
			  for (int i = 0; i < hotels.length; i++){
				  if (hotels[i].getX() == blockX && hotels[i].getZ() == blockZ){
					  for (int y = 0; y < 200; y++){
						  if (world.canBlockSeeSky(new BlockPos(blockX, y, blockZ)) && world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
							  for (int y2 = y; y2 < 200;y2++){
								  for (int x = blockX; x < 15 + blockX; x++){
									  for (int z = blockZ; z < 9 + blockZ; z++){
										  world.setBlockState(new BlockPos(x, y2, z), Blocks.air.getDefaultState());
									  }
								  }
							  }
							  new City().generateHotel(world, random, blockX, y, blockZ);
							  break;
						  }
						  else{
							  for (int x = blockX; x < blockX + 15; x++){
								  for (int z = blockZ; z < blockZ + 9; z++){
									  world.setBlockState(new BlockPos(x, y, z), Blocks.stone.getDefaultState());
								  }
							  }
						  }
					  }
				  }
			  }
			  for (int i = 0; i < publixes.length; i++){
				  if (publixes[i].getX() == blockX && publixes[i].getZ() == blockZ){
					  for (int y = 0; y < 200; y++){
						  if (world.canBlockSeeSky(new BlockPos(blockX, y, blockZ)) && world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
							  for (int y2 = y; y2 < 200;y2++){
								  for (int x = blockX; x < 20 + blockX; x++){
									  for (int z = blockZ; z < 20 + blockZ; z++){
										  world.setBlockState(new BlockPos(x, y2, z), Blocks.air.getDefaultState());
									  }
								  }
							  }
							  new City().generatePublix(world, random, blockX, y, blockZ);
							  break;
						  }
						  else{
							  for (int x = blockX; x < blockX + 20; x++){
								  for (int z = blockZ; z < blockZ + 20; z++){
									  world.setBlockState(new BlockPos(x, y, z), Blocks.stone.getDefaultState());
								  }
							  }
						  }
					  }
				  }
			  }
			  for (int i = 0; i < clinics.length; i++){
				  if (clinics[i].getX() == blockX && clinics[i].getZ() == blockZ){
					  for (int y = 0; y < 200; y++){
						  if (world.canBlockSeeSky(new BlockPos(blockX, y, blockZ)) && world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
							  for (int y2 = y; y2 < 200;y2++){
								  for (int x = blockX; x < blockX + 8; x++){
									  for (int z = blockZ; z < blockZ + 13; z++){
										  world.setBlockState(new BlockPos(x, y2, z), Blocks.air.getDefaultState());
									  }
								  }
							  }
							  new City().generateClinic(world, random, blockX, y, blockZ);
							  break;
						  }
						  else{
							  for (int x = blockX; x < blockX + 8; x++){
								  for (int z = blockZ; z < blockZ + 13; z++){
									  world.setBlockState(new BlockPos(x, y, z), Blocks.stone.getDefaultState());
								  }
							  }
						  }
					  }
				  }
			  }
			  for (int i = 0; i < researchFacilities.length; i++){
				  if (clinics[i].getX() == blockX && researchFacilities[i].getZ() == blockZ){
					  for (int y = 0; y < 200; y++){
						  if (world.canBlockSeeSky(new BlockPos(blockX, y, blockZ)) && world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
							  for (int y2 = y; y2 < 200;y2++){
								  for (int x = blockX; x < blockX + 10; x++){
									  for (int z = blockZ; z < blockZ + 10; z++){
										  world.setBlockState(new BlockPos(x, y2, z), Blocks.air.getDefaultState());
									  }
								  }
							  }
							  new City().generateResearchFacility(world, random, blockX, y, blockZ);
							  break;
						  }
						  else{
							  for (int x = blockX; x < blockX + 10; x++){
								  for (int z = blockZ; z < blockZ + 10; z++){
									  world.setBlockState(new BlockPos(x, y, z), Blocks.stone.getDefaultState());
								  }
							  }
						  }
					  }
				  }
			  }
			  for (int i = 0; i < houses.length; i++){
				  if (houses[i].getX() == blockX && houses[i].getZ() == blockZ){
					  for (int y = 0; y < 200; y++){
						  if (world.canBlockSeeSky(new BlockPos(blockX, y, blockZ)) && world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
							  for (int y2 = y; y2 < 200;y2++){
								  for (int x = blockX; x < 6 + blockX; x++){
									  for (int z = blockZ; z < 6 + blockZ; z++){
										  world.setBlockState(new BlockPos(x, y2, z), Blocks.air.getDefaultState());
									  }
								  }
							  }
							  new City().generateCity(world, random, blockX, y, blockZ, CityBuildingType.HOUSE);
							  Entity male = new Citizen(world);
							  Entity female = new CitizenFemale(world);
							  male.setPosition(blockX + 2, y + 1, blockZ + 2);
							  female.setPosition(blockX + 2, y + 1, blockZ + 2);
							  world.spawnEntityInWorld(male);
							  world.spawnEntityInWorld(female);
							  break;
						  }
						  else{
							  for (int x = blockX; x < 6 + blockX; x++){
								  for (int z = blockZ; z < 6 + blockZ; z++){
									  world.setBlockState(new BlockPos(x, y, z), Blocks.stone.getDefaultState());
								  }
							  }
						  }
					  }
				  }
			  }

			  if (world.getWorldType() == ZombieApocalypse.APOCALYPSE && playerLocation == null){
				  //ForgeChunkManager.forceChunk(ForgeChunkManager.requestTicket(ZombieApocalypse.instance, world, ForgeChunkManager.Type.NORMAL), new ChunkCoordIntPair(0, 0));
				  world.getChunkProvider().provideChunk(0, 0);
				  this.generateSurface(world, random, 0, 0);
				  
				  //ChunkCoordIntPair pair;
			  }
			  if (!(Math.abs(blockX) > CITY_LIMITS && Math.abs(blockZ) > CITY_LIMITS)){
				  return;
			  }
			 
		  }
		  if (blockX == 0 && blockZ == 0){
			  for (int y = 0; y < 200; y++){
				  if (world.canBlockSeeSky(new BlockPos(blockX, y, blockZ))){
					  new City().generateHerosHouse(world, random, blockX, y, blockZ);
					  break;
				  }
			  }
		  }
		  if (random.nextInt(ZombieApocalypse.militaryBaseRarity) < 2){
			  for (int y = 0; y < 100; y++){
				  if (world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
					  new City().generate2(world, random, blockX, y, blockZ);
					  
					  for (int i = 0; i < 3; i++){
						  Soldier soldier = new Soldier(world);
						  soldier.setPosition(blockX + 8, y+2, blockZ+8);
						  world.spawnEntityInWorld(soldier);
					  }
					  
					  Soldier soldier2 = new Soldier(world);
					  soldier2.setPosition(blockX + 8, y+2, blockZ+8);
					  world.spawnEntityInWorld(soldier2);
					  Soldier soldier3 = new Soldier(world);
					  soldier3.setPosition(blockX + 8, y+2, blockZ+8);
					  world.spawnEntityInWorld(soldier3);
					  break;
				  }
				  else{
					  for (int z = 0; z < 16; z++){
						  for (int x = 0; x < 16; x++){
							  world.setBlockState(new BlockPos(blockX + x, y, blockZ + z), Blocks.stone.getDefaultState());
						  }
					  }
				  }
			  }
		  }
		  if (random.nextInt(ZombieApocalypse.stiltHouseRarity) < 2){
			  System.out.println("Spawning Stilt House At" + blockX + " " + blockZ);
			  int heightAbove = 0;
			  for (int y = 0; y < 100; y++){
				  if (world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
					  heightAbove++;
					  if (heightAbove < 20){
						  for (int z = 0; z < 6; z++){
							  for (int x = 0; x < 6; x++){
								  if (x == 0 || x == 5){
									  if (z == 0 || z == 5){
										  world.setBlockState(new BlockPos(blockX + x, y, blockZ + z), Blocks.log.getDefaultState());
									  }
								  }
							  }
						  }
					  }
					  else{
						  new City().generateSurvivorStiltsHouse(world, random, blockX, y, blockZ);
						  for (int i = 0; i < 3; i++){
							  Survivor soldier = new Survivor(world);
							  soldier.setPosition(blockX + 2, y+2, blockZ+2);
							  world.spawnEntityInWorld(soldier);
						  }
						  
						  break;
					  }
					  
				  }
				  else{
					  for (int z = 0; z < 6; z++){
						  for (int x = 0; x < 6; x++){
							  if (x == 0 || x == 5){
								  if (z == 0 || z == 5){
									  world.setBlockState(new BlockPos(blockX + x, y, blockZ + z), Blocks.log.getDefaultState());
								  }
							  }
						  }
					  }
				  }
			  }
		  }
		  if (random.nextInt(ZombieApocalypse.armouredVillageRarity) < 2){
			  System.out.println("Spawning village at " + blockX + " " + blockZ);
			  for (int y = 0; y < 100; y++){
				  if (world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
					  List<Entity> entities = world.getEntitiesWithinAABB(EntityUltraZombie.class, AxisAlignedBB.fromBounds(blockX, 0, blockZ, blockX + 64, 200, blockZ + 64));
					  for (int i = 0; i < entities.size(); i++){
						  if (entities.get(i) instanceof EntityUltraZombie){
							  entities.get(i).setDead();
						  }
					  }
					  for (int z = 0; z < 64; z++){
						  for (int x = 0; x < 64; x++){
							  world.setBlockState(new BlockPos(blockX + x, y-1, blockZ + z), Blocks.cobblestone.getDefaultState());
						  }
					  }
					  //System.out.println("Platform Made");
					  //new City().generate2(world, random, blockX, y, blockZ);
					  
					  for (int xclear = blockX; xclear < blockX + 64; xclear++){
						  for (int zclear = blockZ; zclear < blockZ + 64; zclear++){
							  for (int yclear = y; yclear < y + 10; yclear++){
								  //System.out.println("Clearing " + xclear + " " + yclear + " " + zclear);
								  world.setBlockState(new BlockPos(xclear, yclear, zclear), Blocks.air.getDefaultState());
							  }
						  }
					  }
					  
					  //System.out.println("Space Cleared");
					  for (int zwall = -1; zwall < 65; zwall++){
						  for (int ywall = 0; ywall < 4; ywall++){
							  world.setBlockState(new BlockPos(blockX-1, y + ywall, blockZ + zwall), ZombieApocalypse.aluminumBlock.getDefaultState());
						  }
					  }
					  for (int zwall = -1; zwall < 65; zwall++){
						  for (int ywall = 0; ywall < 4; ywall++){
							  world.setBlockState(new BlockPos(blockX + 64, y + ywall, blockZ + zwall), ZombieApocalypse.aluminumBlock.getDefaultState());
						  }
					  }
					  for (int xwall = -1; xwall < 65; xwall++){
						  for (int ywall = 0; ywall < 4; ywall++){
							  world.setBlockState(new BlockPos(blockX + xwall, y + ywall, blockZ-1), ZombieApocalypse.aluminumBlock.getDefaultState());
						  }
					  }
					  for (int xwall = -1; xwall < 65; xwall++){
						  for (int ywall = 0; ywall < 4; ywall++){
							  world.setBlockState(new BlockPos(blockX + xwall, y + ywall, blockZ + 64), ZombieApocalypse.aluminumBlock.getDefaultState());
						  }
					  }
					  //System.out.println("Walls Built");
					  for (int i = 0; i < 3; i++){
						  Soldier soldier = new Soldier(world);
						  soldier.setPosition(blockX + 8, y+2, blockZ+8);
						  world.spawnEntityInWorld(soldier);
					  }
					  //System.out.println("Soldiers Spawned");
					  for (int xcell = 0; xcell < 8; xcell++){
						  for (int zcell = 0; zcell < 4; zcell++){
							  if (zcell == 2 && xcell == 4){
								  new City().generateCity(world, random, blockX + (xcell * 8) + 1, y, blockZ + (zcell * 16) + 1, CityBuildingType.WELL);
							  }
							  else{
								  CityBuildingType building = weightedBuildings[random.nextInt(weightedBuildings.length)];
								  new City().generateCity(world, random, blockX + (xcell * 8) + 1, y, blockZ + (zcell * 16) + 1, building);
								  if (building == CityBuildingType.HOUSE){
									  Citizen male = new Citizen(world);
									  male.setPosition(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
									  male.homeBase = new BlockPos(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
									  world.spawnEntityInWorld(male);
									  CitizenFemale female = new CitizenFemale(world);
									  female.setPosition(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
									  female.homeBase = new BlockPos(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
									  world.spawnEntityInWorld(female);
								  }
							  }  
						  }
					  }
					  //System.out.println("Houses Built");
					  break;
				  }
				  else{
					  for (int x = 0; x < 64; x++){
						  for (int z = 0; z < 64; z++){
							  world.setBlockState(new BlockPos(x + blockX, y, z + blockZ), Blocks.dirt.getDefaultState());
						  }
					  }
				  }
			  }
		  }
		  
		  
		  if (world.getWorldType() == ZombieApocalypse.SOLDIER){
			  if (blockX <= world.getSpawnPoint().getX() && blockX + 16 > world.getSpawnPoint().getX() && blockZ <= world.getSpawnPoint().getZ() && blockZ + 16 > world.getSpawnPoint().getZ()){
				  System.out.println("Spawnpoint at "+ blockX + " " + blockZ);
				  for (int y = 0; y < 100; y++){
					  if (world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
						  new City().generate2(world, random, blockX, y, blockZ);
						  
						  for (int i = 0; i < 3; i++){
							  Soldier soldier = new Soldier(world);
							  soldier.setPosition(blockX + 8, y+2, blockZ+8);
							  world.spawnEntityInWorld(soldier);
						  }
						  
						  Soldier soldier2 = new Soldier(world);
						  soldier2.setPosition(blockX + 8, y+2, blockZ+8);
						  world.spawnEntityInWorld(soldier2);
						  Soldier soldier3 = new Soldier(world);
						  soldier3.setPosition(blockX + 8, y+2, blockZ+8);
						  world.spawnEntityInWorld(soldier3);
						  break;
					  }
					  else{
						  for (int z = 0; z < 16; z++){
							  for (int x = 0; x < 16; x++){
								  world.setBlockState(new BlockPos(blockX + x, y, blockZ + z), Blocks.stone.getDefaultState());
							  }
						  }
					  }
				  }
			  }
		  }
		  if (world.getWorldType() == ZombieApocalypse.APOCALYPSE_EXPERT){
			  if (blockX <= world.getSpawnPoint().getX() && blockX + 16 > world.getSpawnPoint().getX() && blockZ <= world.getSpawnPoint().getZ() && blockZ + 16 > world.getSpawnPoint().getZ()){
				  new City().generateBunker(world, random, blockX, 40, blockZ);
				  int i = 44;
				  for (i = 44; i < 200; i++){
					  if (world.canBlockSeeSky(new BlockPos(blockX, i, blockZ))){
						  break;
					  }
					  
					  world.setBlockState(new BlockPos(blockX, i, blockZ + 1), Blocks.stonebrick.getDefaultState());
					  world.setBlockState(new BlockPos(blockX + 1, i, blockZ), Blocks.stonebrick.getDefaultState());
					  world.setBlockState(new BlockPos(blockX + 1, i, blockZ + 2), Blocks.stonebrick.getDefaultState());
					  world.setBlockState(new BlockPos(blockX + 2, i, blockZ + 1), Blocks.stonebrick.getDefaultState());
					  world.setBlockState(new BlockPos(blockX + 1, i, blockZ + 1), Blocks.ladder.getDefaultState().withProperty(((BlockLadder)Blocks.ladder).FACING, EnumFacing.SOUTH));
				  }
				  world.setBlockState(new BlockPos(blockX + 1, i, blockZ), Blocks.stonebrick.getDefaultState());
				  world.setBlockState(new BlockPos(blockX + 1, i, blockZ + 2), Blocks.stonebrick.getDefaultState());
				  world.setBlockState(new BlockPos(blockX + 2, i, blockZ + 1), Blocks.stonebrick.getDefaultState());
				  
				  world.setBlockState(new BlockPos(blockX + 1, i + 1, blockZ), Blocks.stonebrick.getDefaultState());
				  world.setBlockState(new BlockPos(blockX + 1, i + 1, blockZ + 2), Blocks.stonebrick.getDefaultState());
				  world.setBlockState(new BlockPos(blockX + 2, i + 1, blockZ + 1), Blocks.stonebrick.getDefaultState());
			  }
		  }
		  if (world.getWorldType() == ZombieApocalypse.CITIZEN){
			  if (blockX <= world.getSpawnPoint().getX() && blockX + 16 > world.getSpawnPoint().getX() && blockZ <= world.getSpawnPoint().getZ() && blockZ + 16 > world.getSpawnPoint().getZ()){
				  blockX -= 16;
				  blockZ -= 16;
				  for (int y = 0; y < 100; y++){
					  if (world.getLight(new BlockPos(blockX, y, blockZ)) > 14){
						  List<Entity> entities = world.getEntitiesWithinAABB(EntityUltraZombie.class, AxisAlignedBB.fromBounds(blockX, 0, blockZ, blockX + 64, 200, blockZ + 64));
						  for (int i = 0; i < entities.size(); i++){
							  if (entities.get(i) instanceof EntityUltraZombie){
								  entities.get(i).setDead();
							  }
						  }
						  for (int z = 0; z < 64; z++){
							  for (int x = 0; x < 64; x++){
								  world.setBlockState(new BlockPos(blockX + x, y-1, blockZ + z), Blocks.cobblestone.getDefaultState());
							  }
						  }
						  //System.out.println("Platform Made");
						  //new City().generate2(world, random, blockX, y, blockZ);
						  
						  for (int xclear = blockX; xclear < blockX + 64; xclear++){
							  for (int zclear = blockZ; zclear < blockZ + 64; zclear++){
								  for (int yclear = y; yclear < y + 10; yclear++){
									  //System.out.println("Clearing " + xclear + " " + yclear + " " + zclear);
									  world.setBlockState(new BlockPos(xclear, yclear, zclear), Blocks.air.getDefaultState());
								  }
							  }
						  }
						  
						  //System.out.println("Space Cleared");
						  for (int zwall = -1; zwall < 65; zwall++){
							  for (int ywall = 0; ywall < 4; ywall++){
								  world.setBlockState(new BlockPos(blockX-1, y + ywall, blockZ + zwall), ZombieApocalypse.aluminumBlock.getDefaultState());
							  }
						  }
						  for (int zwall = -1; zwall < 65; zwall++){
							  for (int ywall = 0; ywall < 4; ywall++){
								  world.setBlockState(new BlockPos(blockX + 64, y + ywall, blockZ + zwall), ZombieApocalypse.aluminumBlock.getDefaultState());
							  }
						  }
						  for (int xwall = -1; xwall < 65; xwall++){
							  for (int ywall = 0; ywall < 4; ywall++){
								  world.setBlockState(new BlockPos(blockX + xwall, y + ywall, blockZ-1), ZombieApocalypse.aluminumBlock.getDefaultState());
							  }
						  }
						  for (int xwall = -1; xwall < 65; xwall++){
							  for (int ywall = 0; ywall < 4; ywall++){
								  world.setBlockState(new BlockPos(blockX + xwall, y + ywall, blockZ + 64), ZombieApocalypse.aluminumBlock.getDefaultState());
							  }
						  }
						  //System.out.println("Walls Built");
						  for (int i = 0; i < 3; i++){
							  Soldier soldier = new Soldier(world);
							  soldier.setPosition(blockX + 8, y+2, blockZ+8);
							  world.spawnEntityInWorld(soldier);
						  }
						  //System.out.println("Soldiers Spawned");
						  for (int xcell = 0; xcell < 8; xcell++){
							  for (int zcell = 0; zcell < 4; zcell++){
								  if (zcell == 2 && xcell == 4){
									  new City().generateCity(world, random, blockX + (xcell * 8) + 1, y, blockZ + (zcell * 16) + 1, CityBuildingType.WELL);
								  }
								  else{
									  CityBuildingType building = weightedBuildings[random.nextInt(weightedBuildings.length)];
									  new City().generateCity(world, random, blockX + (xcell * 8) + 1, y, blockZ + (zcell * 16) + 1, building);
									  if (building == CityBuildingType.HOUSE){
										  Citizen male = new Citizen(world);
										  male.setPosition(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
										  male.homeBase = new BlockPos(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
										  world.spawnEntityInWorld(male);
										  CitizenFemale female = new CitizenFemale(world);
										  female.setPosition(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
										  female.homeBase = new BlockPos(blockX + (xcell * 8) + 4, y + 1, blockZ + (zcell * 16) + 4);
										  world.spawnEntityInWorld(female);
									  }
								  }  
							  }
						  }
						  //System.out.println("Houses Built");
						  break;
					  }
					  else{
						  for (int x = 0; x < 64; x++){
							  for (int z = 0; z < 64; z++){
								  world.setBlockState(new BlockPos(x + blockX, y, z + blockZ), Blocks.dirt.getDefaultState());
							  }
						  }
					  }
				  }
			  }
		  }
	 }
	 
	 private void generateEpic(World world, Random random, int blockX, int blockZ) 
	 {
		 
	 }

}
