import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Background{
	private Image background; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object					//movement variables
	double scaleWidth = 5.9;		//change to scale image
	double scaleHeight = 5.9; 		//change to scale image

	public Background(int num) {
		if(num == 1) {
			background = getImage("/imgs/"+"Level One.png");
		}else if(num == 2) {
			background = getImage("/imgs/" + "Final Level.png");
		}else if(num == 3) {
			background = getImage("/imgs/" + "Game Over.png");
		}

		
		//alter these
		width = 600;
		height = 600;
		x = 0;
		y = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//allow setting x and y during construction
	public Background(int x, int y) {
		
		//calls default constructor
		//this();
		
		this.x = x;
		this.y = y;
			
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
			
		
		init(x,y);
		
		g2.drawImage(background, tx, null);
		
	}

	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Background.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
