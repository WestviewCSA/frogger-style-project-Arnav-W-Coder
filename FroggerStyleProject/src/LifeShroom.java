import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class LifeShroom{
	private Image forward; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	double scaleWidth = 0.75;		//change to scale image
	double scaleHeight = 0.75; 		//change to scale image

	public LifeShroom() {
		forward = getImage("/imgs/" + "LifeShroom.png"); //load the image for Tree

		
		//alter these
		width = 100;
		height = 100;
		x = (int)(Math.random() * (501));
		int level = (int)(Math.random() * (3) + 1);
		if(level == 1) {
			y = 500;
		}else if(level == 2) {
			y = 200;
		}else {
			y = 0;
		}
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public void setX(int newX) {
		x = newX;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean collided(Mario character) {
		Rectangle main = new Rectangle(
				character.getX() + 50, 
				character.getY() + 30, 
				character.getWidth()/2 - 30,
				character.getHeight()- 50);
		
		Rectangle thisObject = new Rectangle(x + 10, y + 20, width/2, height/3);
		
		//user built-in method to check intersection
		return main.intersects(thisObject);
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
		
		//draw hit box based on x,y,width,height for collision detection
		if(Frame.debugging) {
			g.setColor(Color.green);
			g.drawRect(x + 10, y + 20, width/2, height/3);
		}
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = LifeShroom.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
