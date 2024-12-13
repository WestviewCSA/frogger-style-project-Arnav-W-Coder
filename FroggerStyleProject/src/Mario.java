import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Mario{
	private Image forward; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	int jumper = 1;
	double scaleWidth = 1.25;		//change to scale image
	double scaleHeight = 1.25; 		//change to scale image

	public Mario() {
		forward = getImage("/imgs/"+"Mario Forward.png"); //load the image for Tree
		//right = getImage("/imgs/" + "Mario FaceRight.png");
		//left = getImage("/imgs/" + "Mario FaceLeft.png");
		
		//alter these
		width = 100;
		height = 100;
		x = 266;
		y = 500;
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//allow setting x and y during construction
	public Mario(int x, int y) {
		
		//calls default constructor
		this();
		
		this.x = x;
		this.y = y;
	}	
	
	public void setX(int newX) {
		x = newX;
	}
	public void setY(int newY) {
		y = newY;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	
	public boolean outBounds() {
		if(x >= 630 || x <= -80 || y >= 680 || y <= -200) {
			return true;
		}else {
			return false;
		}
	}
	
	public void changeScale(double n) {
		scaleWidth = n;
		scaleHeight = n;
	}
	
	public void addX(int newX) {
		x += newX;
	}
	
	public void addY(int newY) {
		y += newY;
	}
	
	public void changeImage(String pic) {
		forward = getImage("/imgs/"+ pic);
	}
	
	public void changeJumper(int j) {
		jumper = j;
	}
	
	public void move(int dir) {
		
		switch(dir) {
		
		case 0: //hop up
			y -= height/jumper;
			break;
		case 1: //hop down
			y += height/jumper;
			break;
		case 2: //hop left
			//x -= width/2;
			x -= 30;
			break;
		case 3: //hop right
			//x += width/2;
			x += 30;
			break;
			
		}
		
	}
	
	public void setVx(int vel) {
		vx = vel;
	}
	
	public int getVx() {
		return vx;
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
		
		//draw hit box based on x,y,width,height for collision detection
		if(Frame.debugging) {
			g.setColor(Color.green);
			g.drawRect(x + 50, y + 30, width/2 - 30, height - 50);
		}
		
		
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Mario.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
