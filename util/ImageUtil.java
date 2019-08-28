package util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import data.world.World;

public class ImageUtil {

	/**
	 * Get a pixel image of the given world 
	 */
	public static BufferedImage createImage(World world) {
		if (world == null) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		
	    BufferedImage image = new BufferedImage(world.width(), world.height(), BufferedImage.TYPE_INT_ARGB);
	    
	    for(int column = 0; column < world.width(); column++) {
	        for(int row = 0; row < world.height(); row++) {
	        	   // Image coords are reverse of world coords
	        	   Color color = world.getColorValue(row, column);
	        	   
	            image.setRGB(column, row, color.getRGB());
	        }
	    }
	    
	    return image;
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, Image.SCALE_DEFAULT);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
	    g.dispose();

	    return resizedImage;
	}
}
