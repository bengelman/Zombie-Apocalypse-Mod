package com.pdogmuncher.zombieapocalypse.events;

import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;
import com.pdogmuncher.zombieapocalypse.lib.References;
import com.pdogmuncher.zombieapocalypse.mobs.EntityKitPicker;
import com.pdogmuncher.zombieapocalypse.mobs.EntityUltraZombie;
import com.pdogmuncher.zombieapocalypse.tools.Firearm;

//
// This is where I put all my custom overlay stuff, like scopes and titles and popups and such
//
public class GuiBuffBar extends Gui
{
  private static Minecraft mc;

  public GuiBuffBar(Minecraft mc)
  {
    super();
    fovNormal = Minecraft.getMinecraft().gameSettings.fovSetting;
    // We need this to invoke the render engine.
    this.mc = mc;
  }
  public static float fovNormal;
  private static final int BUFF_ICON_SIZE = 18;
  private static final int BUFF_ICON_SPACING = BUFF_ICON_SIZE + 2; // 2 pixels between buff icons
  private static final int BUFF_ICON_BASE_U_OFFSET = 0;
  private static final int BUFF_ICON_BASE_V_OFFSET = 198;
  private static final int BUFF_ICONS_PER_ROW = 8;
  static boolean cutsceneStart;
  public static int titleTime = 0;
  int cutsceneTime = 0;
  public static final ResourceLocation RESOURCE_DURABILITY_ICONS_PNG = new ResourceLocation("textures/durability_icons.png");
  //private static TextureManager textureManager = mc.func_110434_K(); //get the TextureManager instance
  
  //
  // This event is called by GuiIngameForge during each frame by
  // GuiIngameForge.pre() and GuiIngameForce.post().
  //
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onRenderExperienceBar(RenderGameOverlayEvent event)
  {
    // 
    // We draw after the ExperienceBar has drawn.  The event raised by GuiIngameForge.pre()
    // will return true from isCancelable.  If you call event.setCanceled(true) in
    // that case, the portion of rendering which this event represents will be canceled.
    // We want to draw *after* the experience bar is drawn, so we make sure isCancelable() returns
    // false and that the eventType represents the ExperienceBar event.
	if (event.type == ElementType.HOTBAR){
		
	}
    if(event.isCancelable() || event.type != ElementType.CROSSHAIRS)
    {
    	return;
    }
    
    this.mc.renderEngine.bindTexture(new ResourceLocation("textures/misc/pumpkinblur.png"));
    ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), this.mc.displayWidth, this.mc.displayHeight);
    
	this.drawDrawFullscreenImage(res.getScaledWidth(), res.getScaledHeight());
	
	if (titleTime > 0 && !Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()){
		//titleTime--;
		this.drawCutscene(res.getScaledWidth(), res.getScaledHeight(), "title");
	}
	else{
		titleTime = 0;
	}
	
	/*
	if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
		cutsceneTime = 300;
	}
	if (cutsceneTime < 100){
		this.drawCustscene(res.getScaledWidth(), res.getScaledHeight(), "1");
		cutsceneTime++;
	}
	else if (cutsceneTime < 200){
		this.drawCustscene(res.getScaledWidth(), res.getScaledHeight(), "2");
		cutsceneTime++;
	}
	else if (cutsceneTime < 300){
		Minecraft.getMinecraft().gameSettings.thirdPersonView = 2;
		cutsceneTime++;
	}
	else if (cutsceneTime < 301){
		Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
		cutsceneTime++;
	}
	*/
    // Starting position for the buff bar - 2 pixels from the top left corner.
    int xPos = 2;
    int yPos = 2;
    if (mc.objectMouseOver != null){
    	
    	if (mc.objectMouseOver.entityHit != null){
    	  	  //FontRenderer fontRender = mc.fontRendererObj;
    	  	  //mc.entityRenderer.setupOverlayRendering();
    	  	  //this.drawRect(0, 0, 100, 100, 2);
    	  	  if (mc.objectMouseOver.entityHit instanceof EntityMob || mc.objectMouseOver.entityHit instanceof EntityUltraZombie){
    	  		  //this.drawCutscene(res.getScaledWidth(), res.getScaledHeight(), "crosshairsred");
    	  	  }
    	  	  if (mc.objectMouseOver.entityHit instanceof EntityKitPicker){
    	  		  EntityKitPicker picker = (EntityKitPicker) mc.objectMouseOver.entityHit;
    	  		  //this.drawString(fontRender, mc.objectMouseOver.entityHit.getCustomNameTag(), 2, 2, 0x55FF55);
    	  	  }
    	  }
    }
    
    Collection collection = this.mc.thePlayer.getActivePotionEffects();
    if (!collection.isEmpty())
    {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(GL11.GL_LIGHTING);      
      this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));      
      
      for (Iterator iterator = this.mc.thePlayer.getActivePotionEffects()
          .iterator(); iterator.hasNext(); xPos += BUFF_ICON_SPACING)
      {
        PotionEffect potioneffect = (PotionEffect) iterator.next();
        Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
        if (potioneffect.getPotionID() == Potion.hunger.id){
        	FontRenderer fontRender = mc.fontRendererObj;
        	mc.entityRenderer.setupOverlayRendering();
        	// draw
        	String text = "Recharging Ability: ";
        	int x = 100;
        	int y = 2;
        	int color = 0x55FF55;
        	//fontRender.drawStringWithShadow(text, x, y, color);
        	text = text.concat("" + potioneffect.getDuration() / 20);
        	
        	this.drawString(fontRender, text, x, y, color);
        	//mc.ingameGUI.drawRect(100, 200, 500, 200, EnumChatFormatting.BLACK.hashCode());
        }
        /*if (potion.hasStatusIcon())
        {
          int iconIndex = potion.getStatusIconIndex();
          this.drawTexturedModalRect(
              xPos, yPos, 
              BUFF_ICON_BASE_U_OFFSET + iconIndex % BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE, BUFF_ICON_BASE_V_OFFSET + iconIndex / BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE,
              BUFF_ICON_SIZE, BUFF_ICON_SIZE);
        }*/
        
      }
      
    }
  }
  public void drawDrawFullscreenImage(int width, int height) {
	  boolean nightActive = false;
	  for (Iterator iterator = this.mc.thePlayer.getActivePotionEffects()
	          .iterator(); iterator.hasNext();){
	        PotionEffect potioneffect = (PotionEffect) iterator.next();
	        Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
	        if (potioneffect.getPotionID() == Potion.nightVision.id){
	        	if (potioneffect.getDuration() < 5){
	        		nightActive =true;
	        	}
	        	
	        }
	  }
	  if (!mc.thePlayer.isSneaking() || mc.thePlayer.getHeldItem() == null){
		  Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal;
		  Minecraft.getMinecraft().thePlayer.setSneaking(false);
		  if (nightActive){
			  Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.nightVision.id);
		  }
		  return;
	  }
	  if (mc.thePlayer.getHeldItem().getItem() == ZombieApocalypse.crossbow){
		  Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal / 1.5f;
		  mc.renderEngine.bindTexture(new ResourceLocation("zombieapocalypse:textures/misc/rail.png"));
    	  //Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2));
    	  Tessellator tess = Tessellator.getInstance();
          WorldRenderer tessellator = tess.getWorldRenderer();
          tessellator.startDrawingQuads();
          tessellator.addVertexWithUV(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
          tessellator.addVertexWithUV((double)width, (double)height, -90.0D, 1.0D, 1.0D);
          tessellator.addVertexWithUV((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
          tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
          tess.draw();
          return;
	  }
	  if (!(mc.thePlayer.getHeldItem().getItem() instanceof Firearm) && mc.thePlayer.getHeldItem().getItem() != ZombieApocalypse.crossbow){
		  Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal;
		  Minecraft.getMinecraft().thePlayer.setSneaking(false);
		  if (nightActive){
			  Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.nightVision.id);
		  }
		  return;
	  }
	  if (((Firearm)mc.thePlayer.getHeldItem().getItem()).maxcooldown > 10 && mc.thePlayer.getHeldItem().getTagCompound().getInteger("cooldown") > 0){
		  Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal;
		  Minecraft.getMinecraft().thePlayer.setSneaking(false);
		  if (nightActive){
			  Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.nightVision.id);
		  }
		  return;
	  }
	  //Minecraft.getMinecraft().thePlayer.
      Class c = EntityRenderer.class;
      Object o = Minecraft.getMinecraft().entityRenderer;
      Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal / 1.5f;
      Minecraft.getMinecraft().thePlayer.setSneaking(true);
      ItemStack stack = mc.thePlayer.getHeldItem();
      
      if (((Firearm)mc.thePlayer.getHeldItem().getItem()).epicscope){
    	  Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal / 4;
    	  mc.renderEngine.bindTexture(new ResourceLocation("zombieapocalypse:textures/misc/scope.png"));
    	  Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2));
    	  Tessellator tess = Tessellator.getInstance();
          WorldRenderer tessellator = tess.getWorldRenderer();
          tessellator.startDrawingQuads();
          tessellator.addVertexWithUV(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
          tessellator.addVertexWithUV((double)width, (double)height, -90.0D, 1.0D, 1.0D);
          tessellator.addVertexWithUV((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
          tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
          tess.draw();
      }
      else{
    	  mc.renderEngine.bindTexture(new ResourceLocation("zombieapocalypse:textures/misc/rail.png"));
    	  //Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2));
    	  Tessellator tess = Tessellator.getInstance();
          WorldRenderer tessellator = tess.getWorldRenderer();
          tessellator.startDrawingQuads();
          tessellator.addVertexWithUV(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
          tessellator.addVertexWithUV((double)width, (double)height, -90.0D, 1.0D, 1.0D);
          tessellator.addVertexWithUV((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
          tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
          tess.draw();
      }
      //ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 0.1f, "fovModifierHand", "field_78507_R");
      
      //this.drawTexturedModalRect(0, 0, 0, 0, width, height);
  }
  public void drawDrawCrosshairs(int width, int height, String suffix) {
	  
      Class c = EntityRenderer.class;
      Object o = Minecraft.getMinecraft().entityRenderer;
      //Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal / 4;
      mc.renderEngine.bindTexture(new ResourceLocation("zombieapocalypse:textures/misc/crosshairs" + suffix + ".png"));
   	  //Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2));
   	  Tessellator tess = Tessellator.getInstance();
      WorldRenderer tessellator = tess.getWorldRenderer();
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
      tessellator.addVertexWithUV((double)width, (double)height, -90.0D, 1.0D, 1.0D);
      tessellator.addVertexWithUV((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
      tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
      tess.draw();
      //ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 0.1f, "fovModifierHand", "field_78507_R");
      
      //this.drawTexturedModalRect(0, 0, 0, 0, width, height);
  }
public void drawCutscene(int width, int height, String suffix) {
	  
      Class c = EntityRenderer.class;
      Object o = Minecraft.getMinecraft().entityRenderer;
      //Minecraft.getMinecraft().gameSettings.fovSetting = fovNormal / 4;
      mc.renderEngine.bindTexture(new ResourceLocation("zombieapocalypse:textures/misc/" + suffix + ".png"));
   	  //Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2));
   	  Tessellator tess = Tessellator.getInstance();
      WorldRenderer tessellator = tess.getWorldRenderer();
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
      tessellator.addVertexWithUV((double)width, (double)height, -90.0D, 1.0D, 1.0D);
      tessellator.addVertexWithUV((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
      tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
      tess.draw();
      //ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 0.1f, "fovModifierHand", "field_78507_R");
      
      //this.drawTexturedModalRect(0, 0, 0, 0, width, height);
  }
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.currentScreen instanceof GuiMainMenu)
		{
			//mc.currentScreen.draw
			mc.currentScreen.drawString(mc.fontRendererObj, "uhhhhh", 0, 0, 0xAA0000);
		}
	}
  
}
