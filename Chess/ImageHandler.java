package Chess;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageHandler {

    File file;   
    FileInputStream fis;
    BufferedImage image; //reading the image file  

    int rows; //You should decide the values for rows and cols variables  
    int cols;  
    int chunks;  
   
    int chunkWidth; // determines the chunk width and height  
    int chunkHeight;  
    int count;  
    private BufferedImage imgs[]; //Image array to hold image chunks  
    public final static int TREE = 0;
    public final static int GRASS = 1;
    public final static int STUMP = 2;
    public final static int DIRT = 3;
    
   //constructor for default spritesheet
	public ImageHandler()
	{
		try 
		{
			InputStream fis = getClass().getClassLoader().getResourceAsStream("Chess/sprite.png");
			image = ImageIO.read(fis);
			fis.close();
			image.flush();
		} 
		catch (IOException e) 
		{
			System.out.println("could not find spritesheet");
		}
		rows = 2;
		cols = 6;
		chunks = rows * cols;
		chunkWidth = image.getWidth() / cols; // determines the chunk width and height  
	    chunkHeight = image.getHeight() / rows;
	    count = 0;  
	    imgs = new BufferedImage[chunks]; //Imag
	    Split();
	}
 //constructor for future possible spritesheets
	   
		/**
		 * @param imageName -- Name of image PLUS extension i.e. .png
		 */
		public ImageHandler(String imageName, int rows, int cols){
			file = new File(imageName);
			
			try 
			{
				fis = new FileInputStream(file);
				image = ImageIO.read(fis);
				fis.close();
				image.flush();
			} 
			catch(IOException e) 
			{
				e.printStackTrace();
				System.out.println("imagehandler error");
			}
			this.rows = rows;
			this.cols = cols;
			chunks = rows * cols;
			chunkWidth = image.getWidth() / cols; // determines the chunk width and height  
		    chunkHeight = image.getHeight() / rows;  
		    count = 0;  
		    imgs = new BufferedImage[chunks]; //Imag
		    Split();
		}
	    
	        
	 public void Split(){       
	        for (int x = 0; x < rows; x++) 
	        {  
	            for (int y = 0; y < cols; y++) 
	            {  
	                //Initialize the image array with image chunks  
	                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());  
	  
	                // draws the image chunk  
	                Graphics2D gr = imgs[count++].createGraphics();  
	                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
	                gr.dispose();  
	            }  
	        }  
	 }
	 
	 public BufferedImage getImg(int index){
		 
		 return imgs[index];
	 }
}  


