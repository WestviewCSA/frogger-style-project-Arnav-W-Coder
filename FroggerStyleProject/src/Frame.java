import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//for any debugging code we add
	public static boolean debugging = false;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 20);
	int level = 0;
	
	Font myFont = new Font("Courier", Font.BOLD, 25);
	Font winFont = new Font("Courier", Font.BOLD, 30);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	Mario mario = new Mario();
	//Mario mario2 = new Mario(300, 100);
	
	Goomba[] row1 = new Goomba[10];
	
	Piranha[] row2 = new Piranha[10];
	
	Bowser bowser = new Bowser();
	
	Background levelOne = new Background(1);
	
	Log[] log = new Log[10];
	
	LavaRock[] lavaRock = new LavaRock[10];
	
	Koopa[] koopa = new Koopa[10];
	
	Koopa[] koopa2 = new Koopa[10];
	
	RedShell[] redShells = new RedShell[10];
	
	Trophy 	trophy = new Trophy(213, -20);
	
	LifeShroom[] life = new LifeShroom[3];
	
	int lives = 3;
	
	//frame width/height
	int width = 600;
	int height = 600;	
	
	boolean jumper = false;
	
	boolean level2 = false;
	
	boolean winner = false;

	public void paint(Graphics g) {
		
		super.paintComponent(g);
		
		levelOne.paint(g);
		
		for(Log obj : log) {
			obj.paint(g);
		}
		
		for(LavaRock obj : lavaRock) {
			obj.paint(g);
		}
		
		//paint objects on the screen
		mario.paint(g);
		//mario2.paint(g);
		
		for(Goomba obj : row1) {
			obj.paint(g);
		}
		for(Piranha obj : row2) {
			obj.paint(g);
		}
		
		for(LifeShroom obj : life) {
			obj.paint(g);
		}
		
		g.setColor(Color.red);
		g.setFont(timeFont);
		g.drawString("Lives: " + lives, 15, 50);
		
		//Collision detection
		for(Goomba obj: row1) {
			if(obj.collided(mario)) {
				lives --;
				mario.setX(200);
				mario.setY(500);
				break;
			}
		}
		
		for(Piranha obj: row2) {
			if(obj.collided(mario)) {
				lives --;
				mario.setX(200);
				mario.setY(500);
				break;
			}
		}
		
		for(LifeShroom obj : life) {
			if(obj.collided(mario)) {
				lives ++;
				obj.setX(800);
				break;
			}
		}
		
		for(Log obj: log) {
			if(obj.collided(mario)) {
				mario.setVx(2);
				jumper = true;
				break;
			}
		}
		
		//mario stops moving if not on rideable object
		if(!jumper && mario.getY() == 300) {
			mario.setVx(0);
			lives --;
			g.setColor(Color.red);
			g.drawString(lives + "", 100, 100);
			mario.setX(200);
			mario.setY(500);
		}
		
	
		
		for(LavaRock obj : lavaRock) {
			if(obj.collided(mario)) {
				mario.setVx(-3);
				jumper = true;
				break;
			}
		}
		
		if(!jumper && mario.getY() == 100) {
			mario.setVx(0);
			lives --;
			g.setColor(Color.red);
			g.drawString(lives + "", 100, 100);
			mario.setX(200);
			mario.setY(500);
		}
		
		
			
		if(mario.getX() >= 132 && mario.getX() <= 268 && mario.getY() == 0) {
			g.setColor(Color.yellow);
			g.setFont(myFont);
			g.drawString("Press SpaceBar to Enter Bowser's Castle" , 50, 100);
		}
		
		if(level2) {
			for(Koopa obj : koopa) {
				obj.paint(g);
			}
			bowser.paint(g);
			for(RedShell obj : redShells) {
				obj.paint(g);
			}
			for(Koopa obj : koopa2) {
				obj.paint(g);
			}
			trophy.paint(g);
			mario.paint(g);
		}
		
		for(Koopa obj : koopa) {
			if(obj.collided(mario) && level2) {
				lives --;
				mario.setX(200);
				mario.setY(500);
				break;
			}
		}
		
		for(Koopa obj : koopa2) {
			if(obj.collided(mario) && level2) {
				lives --;
				mario.setX(200);
				mario.setY(500);
				break;
			}
		}
		
		for(RedShell obj : redShells) {
			if(obj.collided(mario) && level2) {
				mario.setX(200);
				mario.setY(500);
				lives --;
				break;
			}
		}
		
		if(bowser.collided(mario) && level2) {
			if(!(mario.getX() == 200 && mario.getY() == 500) && !trophy.collided(mario)) {
				lives --;
				mario.setX(200);
				mario.setY(500);
			}
		}
		
		if(trophy.collided(mario) && level2) {
			g.setColor(Color.green);
			g.setFont(winFont);
			g.drawString("Winner! Press Space to reset", 100, 300);
			winner = true;
		}
		
		if(mario.outBounds()) {
			lives --;
			mario.setVx(0);
			mario.setX(200);
			mario.setY(500);
		}
		
		if(lives <= 0) {
			levelOne = new Background(3);
			levelOne.paint(g);
		}
			
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
	
		//backgroundMusic.play();
		
		//set up any 1D array here!
		for(int i = 0; i < row1.length; i++) {
			row1[i] = new Goomba(i*200, 450);
			row2[i] = new Piranha(i*350, 200);
			log[i] = new Log(i*250,320);
			lavaRock[i] = new LavaRock(i*300, 120);
		}
		
		for(int i = 0; i < row1.length; i++) {
			koopa[i] = new Koopa(i*350, 400);
			redShells[i] = new RedShell(i*250, 300);
			koopa2[i] = new Koopa(i*300, 140);
		}
		
		for(int i = 0; i < life.length; i++) {
			life[i] = new LifeShroom();
		}
		
	
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		System.out.println(mario.getX() + " , " + mario.getY());
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode() == 68) {
			mario.move(3);
			mario.changeImage("Mario FaceRight.png");
			mario.changeScale(1.10);
			jumper = false;
		}
		if(arg0.getKeyCode() == 65) {
			mario.move(2);
			mario.changeImage("Mario FaceLeft.png");
			mario.changeScale(1.10);
			jumper = false;
		}
		if(arg0.getKeyCode() == 87) {
			mario.move(0);
			mario.changeImage("Mario Forward.png");
			mario.changeScale(1.25);
			mario.setVx(0);
		}
		if(arg0.getKeyCode() == 83) {
			mario.move(1);
			mario.changeImage("Mario Front.png");
			mario.changeScale(0.90);
			mario.setVx(0);
		}
		
		if(mario.getX() >= 132 && mario.getX() <= 268 && mario.getY() == 0 && arg0.getKeyCode() == 32) {
			mario.setX(200);
			mario.setY(500);
			levelOne = new Background(2);
			//row1 = new Goomba[0];
			//row2 = new Piranha[0];
			//log = new Log[0];
			//lavaRock = new LavaRock[0];
			level2 = true;
			mario.changeJumper(3);
			//life = new LifeShroom[0]; 
			
			for(int i = 0; i < row1.length; i++) {
				row1[i].setY(-500);
				row2[i].setY(-500);
				log[i].setY(-500);
				lavaRock[i].setY(-500);
			}
			
			for(int i = 0; i < life.length; i++) {
				life[i].setY(-500);
			}
			
			for(int i = 0; i < row1.length; i++) {
				koopa[i].setY(400);
				redShells[i].setY(300);
				koopa2[i].setY(140);
			}
		}
		
		if(winner && arg0.getKeyCode() == 32) {
			level2 = false;
			mario.changeJumper(1);
			levelOne = new Background(1);
			mario.setX(200);
			mario.setY(500);
			for(int i = 0; i < row1.length; i++) {
				row1[i].setY(450);
				row2[i].setY(200);
				log[i].setY(320);
				lavaRock[i].setY(120);
			}
			
			for(int i = 0; i < row1.length; i++) {
				koopa[i].setY(-500);
				redShells[i].setY(-500);
				koopa2[i].setY(-500);
			}
			
			for(int i = 0; i < 6; i++) {
				life[i] = new LifeShroom();
			}
			
			winner = false;
			lives = 3;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
