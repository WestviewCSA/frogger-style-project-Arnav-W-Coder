import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class RedShell{
	private Image forward; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 0.5;		//change to scale image
	double scaleHeight = 0.5; 		//change to scale image

	public RedShell() {
		forward = getImage("/imgs/" + "Red Shell.png"); //load the image for Tree

		
		//alter these
		width = 100;
		height = 100;
		x = -width;
		y = 500;
		vx = -5;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//allow setting x and y during construction
	public RedShell(int x, int y) {
		
		//calls default constructor
		this();
		
		this.x = x;
		this.y = y;
			
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
		
		Rectangle thisObject = new Rectangle(x + 8, y + 20, width/2 - 15, height/2 - 40);
		
		//user built-in method to check intersection
		return main.intersects(thisObject);
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
		
		//draw hit box based on x,y,width,height for collision detection
		
		if(x < -50) {
			x = 700;
		}
		
		if(Frame.debugging) {
			g.setColor(Color.green);
			g.drawRect(x + 8, y + 20, width/2 - 15, height/2 - 40);
		}
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = RedShell.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
