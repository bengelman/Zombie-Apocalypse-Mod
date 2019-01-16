package com.pdogmuncher.zombieapocalypse.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.pdogmuncher.zombieapocalypse.ZombieApocalypse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemMusicPlayer extends Item{
	JFileChooser fc = new JFileChooser();
	public ItemMusicPlayer(){
		super();
	}
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer player) {
		//Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("zombieapocalypse:music.bangarang")));
		return par1ItemStack;/*
		Minecraft.getMinecraft().displayGuiScreen(new net.minecraft.client.gui.GuiChat());
		fc = new JFileChooser();
		ImageFilter filter = new ImageFilter();
		fc.addChoosableFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	fc.setVisible(false);
        	//fc.setS
            File file = fc.getSelectedFile();
            if (file == null){
            	return par1ItemStack;
            }
            //file.
            URL url = null;
			try {
				url = new URL("file://".concat(file.getAbsolutePath()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            AudioInputStream ais;
            AudioInputStream shortenedStream;
            try {
            	Clip clip2 = AudioSystem.getClip();
            	
                //clip2.close();
                AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
                AudioFormat format = fileFormat.getFormat();
                ais = AudioSystem.getAudioInputStream(file);
                if (ais.getFrameLength() * format.getFrameSize() > clip2.getBufferSize()){
                	int bytesPerSecond = format.getFrameSize() * (int)format.getFrameRate();
                    ais.skip(0 * bytesPerSecond);
                    long framesOfAudioToCopy = (long)(clip2.getBufferSize() / format.getFrameSize()) - 1;
                    shortenedStream = new AudioInputStream(ais, format, framesOfAudioToCopy);
                    System.out.println("Bytes: " + shortenedStream.getFrameLength() * format.getFrameSize());
                    clip2.open(shortenedStream);
                }
                else{
                	clip2.open(ais);
                }
                    
                clip2.start();
            } catch (Exception ex) {
            	JOptionPane.showMessageDialog(null, "Invalid File! \nChances are, this file has been encoded in a complex WAV. \nTry using an online converter to simplify it.");
                Logger.getLogger(ItemMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            //This is where a real application would open the file.
        }
        else{
        	Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("zombieapocalypse:music.bangarang")));
        }
		return par1ItemStack;
		*/
	  }
}
class ImageFilter extends FileFilter{
	public boolean accept(File f) {
	    if (f.isDirectory()) {
	        return true;
	    }

	    String extension = Utils.getExtension(f);
	    if (extension != null) {
	        if (extension.equals(Utils.wav)) {
	                return true;
	        } else {
	            return false;
	        }
	    }

	    return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Sound Files";
	}
}
class Utils {

    public final static String wav = "wav";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
