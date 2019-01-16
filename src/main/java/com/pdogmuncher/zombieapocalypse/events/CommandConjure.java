package com.pdogmuncher.zombieapocalypse.events;

import java.util.ArrayList;
import java.util.List;

import com.pdogmuncher.zombieapocalypse.mobs.EntityKitPicker;
import com.pdogmuncher.zombieapocalypse.mobs.EntityNametag;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class CommandConjure implements ICommand
{ 
    private final List aliases;
  
    protected String fullEntityName; 
    protected EntityKitPicker conjuredEntity; 
  
    public CommandConjure() 
    { 
        aliases = new ArrayList(); 
        aliases.add("conjure"); 
        aliases.add("conj"); 
    } 
  
    @Override 
    public int compareTo(Object o)
    { 
        return 0; 
    } 

    @Override 
    public String getName() 
    { 
        return "conjure"; 
    } 

    @Override         
    public String getCommandUsage(ICommandSender var1) 
    { 
        return "conjure <text>"; 
    } 

    @Override 
    public List getAliases() 
    { 
        return this.aliases;
    } 

    @Override 
    public void execute(ICommandSender sender, String[] argString)
    { 
        World world = sender.getEntityWorld(); 
    
        if (world.isRemote) 
        { 
            System.out.println("Not processing on Client side"); 
        } 
        else 
        { 
            System.out.println("Processing on Server side"); 
            if(argString.length < 2) 
            { 
                sender.addChatMessage(new ChatComponentText("Invalid argument")); 
                return; 
            } 
            if (true)
            { 
                //conjuredEntity = EntityList.createEntityByName(fullEntityName, world);
            	conjuredEntity = new EntityKitPicker(world);
            	int offset = 1;
            	int x;
            	int y; 
            	int z; 
            	if (argString[0].equalsIgnoreCase("~")){
            		x = sender.getPosition().getX();
            		y = sender.getPosition().getY();
            		z = sender.getPosition().getZ();
            	}
            	else{
            		x = Integer.parseInt(argString[0]);
            		y = Integer.parseInt(argString[1]);
            		z = Integer.parseInt(argString[2]);
            		offset = 3;
            	}
            	conjuredEntity.setCustomNameTag(argString[0 + offset]);
            	EntityNametag tag = new EntityNametag(conjuredEntity.worldObj);
            	tag.setCustomNameTag(argString[0 + offset]);
            	conjuredEntity.outputmessage = argString[1 + offset];
                conjuredEntity.setPosition(sender.getPosition().getX(),       
                sender.getPosition().getY(), 
                sender.getPosition().getZ()); 
                //conjuredEntity.rotationYaw = sender.getCommandSenderEntity().rotationYaw;
                int rotationPos = 2 + offset;
                String messagetext = argString[1 + offset];
                if (messagetext.charAt(0) == '"');{
                	for (int i = offset + 2; i < argString.length; i++){
                		messagetext += " ";
                		messagetext = messagetext.concat(argString[i]);
                		if (messagetext.endsWith('"' + "")){
                			rotationPos = i + 1;
                			messagetext = messagetext.replace('"', '\0');
                			messagetext = messagetext.replace('@', '\n');
                			break;
                		}
                	}
                	conjuredEntity.outputmessage = messagetext;
                }
                for (int i = rotationPos; i < argString.length; i++){
                	rotationPos = i+1;
                	if (argString[i].equalsIgnoreCase("rotation:")){
                		rotationPos = i+1;
                		break;
                	}
                	conjuredEntity.addGivenItem(argString[i]);
                }
                if (argString.length > rotationPos){
                	
                	int yaw = Integer.parseInt(argString[rotationPos]);
                	conjuredEntity.setPositionAndRotation(conjuredEntity.posX, conjuredEntity.posY, conjuredEntity.posZ, yaw, conjuredEntity.rotationPitch);
                	System.out.println("Yaw: " + conjuredEntity.rotationYaw);
                	if (argString[rotationPos+1].equalsIgnoreCase("premium")){
                		conjuredEntity.cost = true;
                	}
                }
                
                world.spawnEntityInWorld(conjuredEntity);
                world.spawnEntityInWorld(tag);
                tag.setPosition(conjuredEntity.posX, conjuredEntity.posY, conjuredEntity.posZ);
            	tag.mountEntity(conjuredEntity);
            }
        } 
    } 

    @Override 
    public boolean canCommandSenderUse(ICommandSender var1) 
    { 
        return true;
    } 

    @Override  
    public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos p) 
    { 
        // TODO Auto-generated method stub 
        return null; 
    } 

    @Override 
    public boolean isUsernameIndex(String[] var1, int var2) 
    { 
        // TODO Auto-generated method stub 
        return false;
    } 
}
