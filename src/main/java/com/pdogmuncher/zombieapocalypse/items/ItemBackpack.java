package com.pdogmuncher.zombieapocalypse.items;

import java.util.Random;
import java.util.TreeMap;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.Constants;
/*
 * This is the backpack item! Super useful! It has the capability to generate with a specific set of
 * items inside it, or it can just be empty. It's really great for giving the player a set of items
 * to start with, you just give them a filled-up backpack
 */
public class ItemBackpack extends ItemArmor{
	public enum FillType{
		STILT_HOUSE, ARMORED_HOUSE, SPAWN, SOLDIER, CITIZEN, BUNKER, APOCALYPSE
	}
	public TreeMap <FillType, ItemStack[]> fillOptions = new TreeMap<FillType, ItemStack[]>();
	ItemStack[] apocalypseOptions = new ItemStack[]{
			new ItemStack(ZombieApocalypse.cannedBeans, 10),
			new ItemStack(ZombieApocalypse.shotgun),
			new ItemStack(ZombieApocalypse.bullet, 64),
			new ItemStack(Items.stone_pickaxe),
			ZombieApocalypse.manual
	};
	ItemStack[] stiltsOptions = new ItemStack[]{
			new ItemStack(Items.cooked_beef),
			new ItemStack(Items.cooked_beef),
			new ItemStack(Items.cooked_beef),
			new ItemStack(ZombieApocalypse.rifle),
			new ItemStack(Items.apple, 5),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 3)
	};
	/*ItemStack[] stiltsOptions = new ItemStack[]{
			new ItemStack(ZombieApocalypse.tent)
	};*/
	ItemStack[] spawnOptions = new ItemStack[]{
			new ItemStack(ZombieApocalypse.grenade, 5), 
			new ItemStack(ZombieApocalypse.swiss_army_knife), 
			new ItemStack(ZombieApocalypse.tent),
			new ItemStack(ZombieApocalypse.pizza),
			new ItemStack(Items.apple)
	};
	ItemStack[] bunkerOptions = new ItemStack[]{
			new ItemStack(ZombieApocalypse.incendiary, 5), 
			new ItemStack(ZombieApocalypse.swiss_army_knife), 
			new ItemStack(ZombieApocalypse.m11_clip, 5),
			new ItemStack(ZombieApocalypse.pistol, 1),
			new ItemStack(Items.cooked_beef, 5)
	};
	ItemStack[] citizenOptions = new ItemStack[]{
			new ItemStack(Items.leather_helmet), 
			new ItemStack(Items.leather_chestplate),
			new ItemStack(Items.leather_leggings),
			new ItemStack(Items.leather_boots),
			new ItemStack(Items.apple)
	};
	ItemStack[] soldierOptions = new ItemStack[]{
			new ItemStack(ZombieApocalypse.grenade, 5), 
			new ItemStack(Items.bread, 3),
			new ItemStack(ZombieApocalypse.m16),
			new ItemStack(ZombieApocalypse.m16_clip, 5),
			new ItemStack(Items.apple)
	};
	ItemStack[] armoredHouseOptions = new ItemStack[]{
			new ItemStack(Items.melon_seeds),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.melon_seeds),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 2),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Items.apple, 1),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(Blocks.sapling),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.bullet),
			new ItemStack(ZombieApocalypse.desert_eagle)
	};
	public ItemBackpack(){
		super(ZombieApocalypse.backpackMaterial, 0, 1);
		fillOptions.put(FillType.STILT_HOUSE, stiltsOptions);
		fillOptions.put(FillType.ARMORED_HOUSE, armoredHouseOptions);
		this.maxStackSize = 1;
		ItemStack book = new ItemStack(Items.writable_book);
		try {
			book.setTagCompound(net.minecraft.nbt.JsonToNBT.func_180713_a(
					"{pages:[\"Day 1\nI've decided to start writing down my experiences " +
					"in this journal that I found. Why not? It might become a famous artifact someday." +
					"Weird stuff is happening on TV. Some kind of Uber-Virus has sprung up in South Africa. " +
					"People say it's rabies. I saw pictures, though. Seems more like zombies.\", \"(Days 2-29 contain recounts of ordinary life-stuff)\", \"Day 30 \nHad " +
					"a normal-ish day today. I hate mondays. South Africa's government has fallen because of that virus. " +
					"Everyone here is saying we'll be fine, but I'm not so sure. An entire government " +
					"fell?! Seems like a cause for concern.\", \"Day 31\nSomething happened at the hospital today. " +
					"I live close by, and I heard gunfire, screams, and a bunch of really awful moans. " +
					"Other than that, nothing much to report\", \"(Days 32-40 contain more everyday " +
					"stuff)\", \"Day 41\nI've been watching the news recently, and it does not look " +
					"good. The virus is spreading like crazy. Most of Africa is anarchy. Recently " +
					"an American city was overrun. The military is trying to take it back, with little success. " +
					"I've started stockpiling resources.\", \"Day 42\nIT'S HAPPENING. This city has been hit. " +
					"The streets are anarchy, with looters, zombies, and people trying to escape everwhere." +
					"I've holed up inside my house with some friends. We fortified the place pretty well. We " +
					"have enough supplies to last about a week. The power has been cut.\", \"(Days 43-50 detail uneventful life" +
					" inside the house)\", \"Day 51\nWell, our supplies are running out. We've decided to try and" +
					" escape the city. Easier said than done. We've managed to drive all the way to the edge of town, " +
					"avoiding major highways which are essentially car-filled graveyards. We're holing up for the night." +
					"We'll have to go on foot tomorrow.\", \"Day 52\nWe lost two people today. We snuck " +
					"out of the city at dawn, but we were ambushed by zombies. Two of us were infected, so " +
					"we had to leave them behind. We made it to my friend's vacation home in the mountains, which is safe, for now.\", " +
					"\"(Days 53-100 detail uneventful life in the mountains, hunting for food)\", \"" +
					"Day 101\nThe American and Canadian governments have fallen, apparently. We " +
					"heard from some people passing by. Apparently there's a massive horde moving towards the mountains." +
					"We've begun making preparations to move. Man, and I was starting to like this place.\"," +
					"\"Day 102\n Our house was overrun before we could flee today. I'm the only one of my group left." +
					" I managed to barely escape uninfected. Now I'm left wandering the zombie-infected wilderness with" +
					" nothing but five grenades, a pizza, a sword, and this book.\"," +
					" \"(The journal ends here. This is your current situation. " +
					"You may continue filling up this journal if you want. Your story is not over yet.)\"]}"
					));
		} catch (NBTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		spawnOptions[4] = book;
		
		ItemStack manual = new ItemStack(Items.written_book);
		try {
			manual.setTagCompound(JsonToNBT.func_180713_a("{title:\"Survival Manual\", author:\"" +
					"Ferret_Skill\", pages:[\"Apocalypse Survival Manual\", \"Section 1: Zombies\n" +
					"Currently, the world is infested with Z1 Disease Zombies. These zombies can kill" +
					"players, citizens, soldiers, and survivors, turning them into zombies. They can" +
					"move very fast and break down doors. They deal large amounts of damage.\",\"They do not " +
					"spawn naturally, though, so clear and fortify an area and you're safe.\",\"Section 2: " +
					"Medical\nBroken Leg: caused by 4+ meter falls - prevents sprinting - cast reduces time" +
					"\nBleeding: sometimes happens after taking damage - kills you if it reaches the " +
					"last 20 seconds of it's time, and can become infected - curable by using bandages or a med kit\",\"" +
					"Infected Wound: caused by bleeding for too long - like poison, but can kill you - curable with a med kit\"]}"));
		} catch (NBTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		apocalypseOptions[4] = manual;
		
		
		fillOptions.put(FillType.SPAWN, spawnOptions);
		fillOptions.put(FillType.BUNKER, bunkerOptions);
		fillOptions.put(FillType.SOLDIER, soldierOptions);
		fillOptions.put(FillType.CITIZEN, citizenOptions);
		fillOptions.put(FillType.APOCALYPSE, apocalypseOptions);
	}
	public ItemStack fillWithRandom(FillType type){
		ItemStack stack = new ItemStack(this);
		stack.setTagCompound(new NBTTagCompound());
		NBTTagList taglist = new NBTTagList();
		for (int i = 0; i < 5; i++){
			if (true){
				NBTTagCompound tag = new NBTTagCompound();
				ItemStack currentItem = fillOptions.get(type)[Math.abs(new Random().nextInt()) % fillOptions.get(type).length];
				tag.setInteger("itemname" + i, Item.getIdFromItem(currentItem.getItem()));
				tag.setInteger("itemcount" + i, currentItem.stackSize);
				if (currentItem.getTagCompound() != null){
					tag.setTag("nbt" + i, currentItem.getTagCompound());
				}
				try{
					//tag.setInteger("damage" + i, currentItem.getItemDamage());
				}
				catch (Exception e){
					
				}
				
				taglist.appendTag(tag);
			}
		}
		stack.getTagCompound().setTag("items", taglist);
		return stack;
	}
	public ItemStack fillWithSet(FillType type){
		//System.out.println("Tent display name: " + new ItemStack(ZombieApocalypse.tent).getDisplayName());
		this.spawnOptions[2] = new ItemStack(ZombieApocalypse.tent);
		fillOptions.put(FillType.SPAWN, spawnOptions);
		//this.apocalypseOptions[4] = ZombieApocalypse.manual;
		//fillOptions.put(FillType.APOCALYPSE, apocalypseOptions);
		ItemStack stack = new ItemStack(this);
		stack.setTagCompound(new NBTTagCompound());
		NBTTagList taglist = new NBTTagList();
		for (int i = 0; i < 5; i++){
			if (true){
				NBTTagCompound tag = new NBTTagCompound();
				ItemStack currentItem = fillOptions.get(type)[i];
				tag.setInteger("itemname" + i, Item.getIdFromItem(currentItem.getItem()));
				tag.setInteger("itemcount" + i, currentItem.stackSize);
				if (currentItem.getTagCompound() != null){
					tag.setTag("nbt" + i, currentItem.getTagCompound());
				}
				try{
					//tag.setInteger("damage" + i, currentItem.getItemDamage());
				}
				catch (Exception e){
					
				}
				
				taglist.appendTag(tag);
			}
		}
		stack.getTagCompound().setTag("items", taglist);
		return stack;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
			NBTTagList taglist = new NBTTagList();
			for (int i = 0; i < 5; i++){
				if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i) != stack){
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("itemname" + i, Item.getIdFromItem(player.inventory.getStackInSlot(i).getItem()));
					tag.setInteger("itemcount" + i, player.inventory.getStackInSlot(i).stackSize);
					if (player.inventory.getStackInSlot(i).getTagCompound() != null){
						tag.setTag("nbt" + i, player.inventory.getStackInSlot(i).getTagCompound());
					}
					try{
						tag.setInteger("damage" + i, player.inventory.getStackInSlot(i).getItemDamage());
					}
					catch (Exception e){
						
					}
					
					taglist.appendTag(tag);
					player.inventory.setInventorySlotContents(i, null);
				}
				else{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("itemname" + i, Item.getIdFromItem(Item.getItemFromBlock(Blocks.air)));
					tag.setInteger("itemcount" + i, 0);
					taglist.appendTag(tag);
				}
			}
			stack.getTagCompound().setTag("items", taglist);
			
			
		}
		else{
			NBTTagList tagList = stack.getTagCompound().getTagList("items", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i < tagList.tagCount(); i++)
		 	{
		 		NBTTagCompound tag = tagList.getCompoundTagAt(i);
		 		ItemStack givenStack = new ItemStack(Item.getItemById(tag.getInteger("itemname" + i)), tag.getInteger("itemcount" + i));
		 		givenStack.setTagCompound(tag.getCompoundTag("nbt" + i));
		 		try{
		 			givenStack.setItemDamage(tag.getInteger("damage" + i));
		 		}
		 		catch (Exception e){
		 			
		 		}
		 		
		 		
		 		player.inventory.addItemStackToInventory(givenStack);
		 		//blocks.add(i, s);
		 	}
			stack.setTagCompound(null);
		}
		return stack;
	}
}
