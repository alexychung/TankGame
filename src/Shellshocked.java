//Alex Chung
//Animation Project
//3/24/2022

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet.ColorAttribute;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.List;

public class Shellshocked extends JPanel {
	
	private BufferedImage image;
	private Graphics g;
	private Timer timer;
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int GROUND = 880;
	
	//each tank
	private Tank tank1;
	private Tank tank2;
	//each healthbar
	private HealthBar h1;
	private HealthBar h2;
	//tracks which player's turn it is
	private int playerTurn;
	//movement
	private boolean up, down, left, right, more, less;
	//the projectile that fires
	private Projectile projectile;
	//determines whether the projectile is in motion or not
	private boolean projectileFire;
	//keeps track of the wind
	private int windFactor;
	private int windDirection;
	//trail of the projectile
	private List<Projectile> trail;
	//animated clouds in the background
	private Cloud[] cloud;
	
	//keeps track of the timer
	private double counter;
	//keeps track of when explosion started
	private double explode;
	//determines whether there is an explosion going on
	private boolean explosion;
	//determines whether damage has already been done to each tank (so the explosion doesn't hit multiple times)
	private boolean damageDone1, damageDone2;
	//determines whether the projectile is in a hitbox
	private boolean hitbox;
	
	//barrier dimensions
	private int barrierWidth = 200;
	private int barrierHeight = 400;
	private boolean hitBarrier;
	
	//x and y of the explosion
	private double explosionX;
	private double explosionY;
	
	//new color for the sky
	private Color sky = new Color(88, 139, 174);
	
	
	public Shellshocked() {
		//creating the frame
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		
		//background
		g.setColor(sky);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, GROUND, WIDTH, HEIGHT-GROUND);
		
		//barrier
		g.fillRect((int)((WIDTH/2)-(barrierWidth/2)), GROUND-barrierHeight, barrierWidth, barrierHeight);
		
		//each tank
		tank1 = new Tank(300, GROUND, 20, 100, 1);
		tank1.drawTank(g);
		tank2 = new Tank(1620, GROUND, 20, 100, -1);
		tank2.drawTank(g);
		
		//each healthbar
		h1 = new HealthBar(20, 20, 20, tank1.getHealth());
		h1.drawHealth(g);
		h2 = new HealthBar(1700, 20, 20, tank2.getHealth());
		h2.drawHealth(g);
		
		//creating projectile and setting initial variable values
		projectile = new Projectile(0, 0, 0, 0, 0, Color.RED, true);
		explosion = false;
		damageDone1 = false;
		damageDone2 = false;
		hitbox = false;
		hitBarrier = false;
		
		//random windfactor to start
		windFactor = (int)(Math.random()*40-20);
		if (windFactor > 0) {
			windDirection = 1;
		}
		else if (windFactor < 0) {
			windDirection = -1;
		}
		else {
			windDirection = 0;
		}
		
		//creating the trail ArrayList
		trail = new ArrayList<Projectile>();
		
		//creating the cloud array
		cloud = new Cloud[5];
		for (int i = 0; i < cloud.length; i++) {
			cloud[i] = new Cloud(i*410, 200, 50, Color.WHITE);
			cloud[i].setXSpeed(1);
		}
		
		//initial movement variable values
		up = false;
		down = false;
		left = false;
		right = false;
		more = false;
		less = false;
		
		//random first player turn
		if (Math.random() > 0.5) {
			playerTurn = 1;
			tank1.setShowAim(true);
		}
		else {
			playerTurn = 2;
			tank2.setShowAim(true);
		}
		
		//setting initial projectileFire variable
		projectileFire = false;
		
		//create and start the timer
		timer = new Timer(5, new TimerListener());
		timer.start();
		
		//adding the listeners
		addKeyListener(new Keyboard());
		//says I want to listen to the keyboard on this device for input (attached keyboard)
		setFocusable(true); 
		
	}
	
	private class Keyboard implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			//aiming with the arrow keys and moving with A and D
			if (key == KeyEvent.VK_LEFT) {
				up = true;
			}
			if (key == KeyEvent.VK_RIGHT) {
				down = true;
			}
			if (key == KeyEvent.VK_UP) {
				more = true;
			}
			if (key == KeyEvent.VK_DOWN) {
				less = true;
			}
			if (key == KeyEvent.VK_A) {
				left = true;
			}
			if (key == KeyEvent.VK_D) {
				right = true;
			}
			//shooting with spacebar
			if (key == KeyEvent.VK_SPACE) {
				if (projectileFire == false) {
					if (playerTurn == 1) {
						//sets all the projectile properties
						projectile.setX((int)(tank1.getX()+(tank1.getDirection()*(tank1.getSize()/1.5))+(tank1.getDirection()*(tank1.getBarrelLength()*Math.cos(Math.toRadians(tank1.getAngle()))))));
						projectile.setY((int)(tank1.getY()-(tank1.getSize()*1.5)-(tank1.getBarrelLength()*Math.sin(Math.toRadians(tank1.getAngle())))));
						projectile.setDiameter(tank1.getShotSize()*8);
						projectile.setxSpeed(tank1.getDirection()*(Math.cos(Math.toRadians(tank1.getAngle())))*tank1.getPower());
						projectile.setySpeed((Math.sin(Math.toRadians(tank1.getAngle())))*tank1.getPower());
						projectile.setyAcceleration(-0.1);
						projectile.setxAcceleration(windFactor*0.0001);
						projectileFire = true;
						tank1.setShowAim(false);
					}
					else if (playerTurn == 2) {
						//sets all the projectile properties
						projectile.setX((int)(tank2.getX()+((tank2.getSize()*tank2.getDirection())/1.5))+(tank2.getDirection()*(tank2.getBarrelLength()*Math.cos(Math.toRadians(tank2.getAngle())))));
						projectile.setY((int)(tank2.getY()-(tank2.getSize()*1.5)-(tank2.getBarrelLength()*Math.sin(Math.toRadians(tank2.getAngle())))));
						projectile.setDiameter(tank2.getShotSize()*8);
						projectile.setxSpeed(tank2.getDirection()*(Math.cos(Math.toRadians(tank2.getAngle())))*tank2.getPower());
						projectile.setySpeed((Math.sin(Math.toRadians(tank2.getAngle())))*tank2.getPower());
						projectile.setyAcceleration(-0.1);
						projectile.setxAcceleration(windFactor*0.0001);
						projectileFire = true;
						tank2.setShowAim(false);
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			
			//setting the variables to false when key is released
			if (key == KeyEvent.VK_LEFT) {
				up = false;
			}
			if (key == KeyEvent.VK_RIGHT) {
				down = false;
			}
			if (key == KeyEvent.VK_UP) {
				more = false;
			}
			if (key == KeyEvent.VK_DOWN) {
				less = false;
			}
			if (key == KeyEvent.VK_A) {
				left = false;
			}
			if (key == KeyEvent.VK_D) {
				right = false;
			}
		}

		//unused method to satisfy the Keyboard class
		@Override
		public void keyTyped(KeyEvent e) {
	
		}
		
	}
	
	//updateKeyInput class in order to avoid the movement lag
	public void updateKeyInput() {
		//makes sure that it is the correct time for the player to move/shoot (both tanks freeze as the projectile is flying through the air)
		if (tank1.isShowAim() == true || tank2.isShowAim() == true) {
			if (up == true) {
				//changes angle of launch depending on arrow key and tank
				if (playerTurn == 1) {
					//stops the angle at 90 degrees
					if (tank1.getAngle() < 90) {
						tank1.setAngle(tank1.getAngle() + 0.5);
					}
				}
				else if (playerTurn == 2) {
					//stops the angle at 0 degrees
					if (tank2.getAngle() > 0) {
						tank2.setAngle(tank2.getAngle() - 0.5);
					}
				}
			}
			if (down == true) {
				//changes angle of launch depending on arrow key and tank
				if (playerTurn == 1) {
					//stops the angle at 0 degrees
					if (tank1.getAngle() > 0) {
						tank1.setAngle(tank1.getAngle() - 0.5);
					}
				}
				else if (playerTurn == 2) {
					//stops the angle at 90 degrees
					if (tank2.getAngle() < 90) {
						tank2.setAngle(tank2.getAngle() + 0.5);
					}
				}
			}
			if (more == true) {
				//increasing power
				if (playerTurn == 1) {
					//power is capped at 25
					if (tank1.getPower() < 25) {
						tank1.setPower(tank1.getPower() + 0.25);
					}
				}
				else if (playerTurn == 2) {
					//power is capped at 25
					if (tank2.getPower() < 25) {
						tank2.setPower(tank2.getPower() + 0.25);
					}
				}
			}
			if (less == true) {
				//decreasing power
				if (playerTurn == 1) {
					//power can't go lower than 0
					if (tank1.getPower() > 0) {
						tank1.setPower(tank1.getPower() - 0.25);
					}
				}
				else if (playerTurn == 2) {
					//power can't go lower than 0
					if (tank2.getPower() > 0) {
						tank2.setPower(tank2.getPower() - 0.25);
					}
				}
			}
			if (left == true) {
				//tanks moves left with A
				if (playerTurn == 1) {
					//makes sure it's not hitting the barrier or the side of the screen
					if ((tank1.getX()-(tank1.getSize()*1.5) > 0 && tank1.getX()<((WIDTH/2)-(barrierWidth/2))) || (tank1.getX()-(tank1.getSize()*1.5) > ((WIDTH/2)+(barrierWidth/2)) && tank1.getX()<WIDTH)) {
						tank1.setX(tank1.getX() - 4);
					}
				}
				else if (playerTurn == 2) {
					//makes sure it's not hitting the barrier or the side of the screen
					if ((tank2.getX()-(tank2.getSize()*1.5) > 0 && tank2.getX()<((WIDTH/2)-(barrierWidth/2))) || (tank2.getX()-(tank2.getSize()*1.5) > ((WIDTH/2)+(barrierWidth/2)) && tank2.getX()<WIDTH)) {
						tank2.setX(tank2.getX() - 4);
					}

				}
			}
			if (right == true) {
				//tanks move right with D
				if (playerTurn == 1) {
					//makes sure it's not hitting the barrier or the side of the screen
					if ((tank1.getX()+(tank1.getSize()*1.5) > 0 && tank1.getX()+(tank1.getSize()*1.5)<((WIDTH/2)-(barrierWidth/2))) || (tank1.getX()+(tank1.getSize()*1.5) > ((WIDTH/2)+(barrierWidth/2)) && tank1.getX()+(tank1.getSize()*1.5)<WIDTH)) {
						tank1.setX(tank1.getX() + 4);
					}
				}
				else if (playerTurn == 2) {
					//makes sure it's not hitting the barrier or the side of the screen
					if ((tank2.getX()+(tank2.getSize()*1.5) > 0 && tank2.getX()+(tank2.getSize()*1.5)<((WIDTH/2)-(barrierWidth/2))) || (tank2.getX()+(tank2.getSize()*1.5) > ((WIDTH/2)+(barrierWidth/2)) && tank2.getX()+(tank2.getSize()*1.5)<WIDTH)) {
						tank2.setX(tank2.getX() + 4);
					}
				}
			}
		}
	}
	
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//counter keeps track of the timer
			counter += 5;
			
			//backdrop
			g.setColor(sky);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, GROUND, WIDTH, HEIGHT-GROUND);
			
			//barrier
			g.fillRect((int)((WIDTH/2)-(barrierWidth/2)), GROUND-barrierHeight, barrierWidth, barrierHeight);
			
			//showing the wind factor
			g.setColor(Color.WHITE);
			g.setFont(new Font("Blackout", Font.BOLD, 20));
			g.drawString("Wind: " + (windFactor), (WIDTH/2)-41, 30);
			//drawing the arrow to indicate direction
			if (windDirection != 0) {
				int[] arrowX = {(WIDTH/2)-(40*windDirection), (WIDTH/2)+(20*windDirection), (WIDTH/2)+(20*windDirection), (WIDTH/2)+(40*windDirection), (WIDTH/2)+(20*windDirection), (WIDTH/2)+(20*windDirection), (WIDTH/2)-(40*windDirection)};
				int[] arrowY = {45, 45, 40, 50, 60, 55, 55};
				g.setColor(Color.RED);
				g.fillPolygon(arrowX, arrowY, 7);
				g.setColor(Color.BLACK);
				g.drawPolygon(arrowX, arrowY, 7);
			}
			
			//moving each cloud
			for (int i = 0; i < cloud.length; i++) {
				cloud[i].move(WIDTH);
				cloud[i].draw(g);
			}
			
			//if spacebar has launched the projectile
			if (projectileFire == true) {
				//if the projectile has not hit the ground, another tank, or the barrier
				if (projectile.getY() < GROUND && hitbox == false && hitBarrier == false){
					//determines whether the projectile has hit the barrier
					if ((projectile.getX()+projectile.getRadius() > (WIDTH/2)-(barrierWidth/2)) && (projectile.getX()-projectile.getRadius() < (WIDTH/2) + (barrierWidth/2)) && (projectile.getY() > GROUND-barrierHeight)) {
						hitBarrier = true;
						//if it has hit the barrier, it starts an explosion
						explosionX = projectile.getX();
						explosionY = projectile.getY();
					}
					else if (playerTurn == 1) {
						//makes sure that if the first player shoots, it's not hitting the second tank before it moves the projectile
						if ((Math.abs(tank2.getX()-projectile.getX())>(tank2.getSize())+projectile.getRadius()) || (tank2.getY()-tank2.getSize()*2)>(projectile.getRadius()+projectile.getY())) {
							//adds the old "shadow" of the projectile into the trail
							trail.add(new Projectile((int)(projectile.getX()), (int)(projectile.getY()), 0, 0, (int)(projectile.getDiameter()), Color.WHITE, false));
							//draws the trail
							for (int i = 0; i < trail.size(); i++) {
								trail.get(i).drawProjectile(g);
							}
							//gets the projectile's x and y values for the explosion
							explosionX = projectile.getX();
							explosionY = projectile.getY();
							//moves and draws the projectile
							projectile.move();
							projectile.drawProjectile(g);
						}
						//if the projectile is hitting the second tank, hitbox is set to true
						else {
							hitbox = true;
						}
					}
					else if (playerTurn == 2) {
						//makes sure that if the second player shoots, it's not hitting the first tank before it moves the projectile
						if ((Math.abs(tank1.getX()-projectile.getX())>(tank1.getSize())+projectile.getRadius()) || (tank1.getY()-tank1.getSize()*2)>(projectile.getRadius()+projectile.getY())) {
							//adds the old "shadow" of the projectile into the trail
							trail.add(new Projectile((int)(projectile.getX()), (int)(projectile.getY()), 0, 0, (int)(projectile.getDiameter()), Color.WHITE, false));
							//draws the trail
							for (int i = 0; i < trail.size(); i++) {
								trail.get(i).drawProjectile(g);
							}
							//gets the projectile's x and y values for the explosion
							explosionX = projectile.getX();
							explosionY = projectile.getY();
							//moves and draws the projectile
							projectile.move();
							projectile.drawProjectile(g);
						}
						//if the projectile is hitting the first tank, hitbox is set to true
						else {
							hitbox = true;
						}
					}
				}
				//if the projectile has hit the barrier, another tank, or the ground
				else {
					if (explosion == false) {
						//sets explode = counter in order to remember the time when the projectile hit the ground
						explode = counter;
						//no damage has been done to either tank
						damageDone1 = false;
						damageDone2 = false;
						//an explosion is taking place
						explosion = true;
					}
					//for 50 milliseconds
					if (counter < explode + 50) {
						//draws small red circle
						g.setColor(Color.RED);
						g.fillOval((int)(explosionX-projectile.getRadius()*2), (int)(explosionY-projectile.getRadius()*2), (int)(projectile.getDiameter()*2), (int)(projectile.getDiameter()*2));
						//if no damage has been done to the first tank during this turn
						if (damageDone1 == false) {
							//if the first tank is touching the inner explosion, it does 20 damage
							if (Math.abs(tank1.getX() - explosionX) <= (projectile.getRadius()*2)+(tank1.getSize()*1.5)) {
								tank1.setHealth(tank1.getHealth()-20);
								h1.setHealth(tank1.getHealth());
								damageDone1 = true;
							}
						}
						//if no damage has been done to the second tank during this turn
						if (damageDone2 == false) {
							//if the second tank is touching the inner explosion, it does 20 damage
							if (Math.abs(tank2.getX() - (explosionX)) <= (projectile.getRadius()*2)+(tank2.getSize()*1.5)) {
								tank2.setHealth(tank2.getHealth()-20);
								h2.setHealth(tank2.getHealth());
								damageDone2 = true;
							}
						}
					}
					//from 50-100 milliseconds
					else if (counter < explode + 100) {
						//second orange explosion that does no damage, but adds to the animation (it's bigger than the red)
						g.setColor(new Color(255, 215, 0));
						g.fillOval((int)(explosionX-projectile.getRadius()*4), (int)(explosionY-projectile.getRadius()*4), (int)(projectile.getDiameter()*4), (int)(projectile.getDiameter()*4));
					}
					//from 100-150 milliseconds
					else if (counter < explode + 150) {
						//third yellow explosion that is the biggest circle of the explosion animation
						g.setColor(Color.YELLOW);
						g.fillOval((int)(explosionX-projectile.getRadius()*6), (int)(explosionY-projectile.getRadius()*6), (int)(projectile.getDiameter()*6), (int)(projectile.getDiameter()*6));
						//if no damage has been done to the first tank on this turn
						if (damageDone1 == false) {
							//if the first tank is touching the outer explosion, it does 10 damage
							if (Math.abs(tank1.getX() - explosionX) <= (projectile.getRadius()*6)+(tank1.getSize()*1.5)) {
								tank1.setHealth(tank1.getHealth()-10);
								h1.setHealth(tank1.getHealth());
								damageDone1 = true;
							}
						}
						//if no damage has been done to the second tank on this turn
						if (damageDone2 == false) {
							//if the second tank is touching the outer explosion, it does 10 damage
							if (Math.abs(tank2.getX() - explosionX) <= (projectile.getRadius()*6)+(tank2.getSize()*1.5)) {
								tank2.setHealth(tank2.getHealth()-10);
								h2.setHealth(tank2.getHealth());
								damageDone2 = true;
							}
						}
					}
					//when the explosion is over
					else {
						explosion = false;
						//new wind factor
						windFactor = (int)(Math.random()*40-20);
						if (windFactor > 0) {
							windDirection = 1;
						}
						else if (windFactor < 0) {
							windDirection = -1;
						}
						else {
							windDirection = 0;
						}
					}
					//changes the player's turn
					if (explosion == false) {
						projectileFire = false;
						hitbox = false;
						hitBarrier = false;
						if (playerTurn == 1) {
							playerTurn = 2;
							tank2.setShowAim(true);
						}
						else if (playerTurn == 2) {
							playerTurn = 1;
							tank1.setShowAim(true);
						}
					}
					//removes the trail
					while(trail.size() > 0) {
						trail.remove(0);
					}

				}
			}
			
			//updateKeyInput to get rid of movement/input lag
			updateKeyInput();
			
			//draws both tanks
			tank1.drawTank(g);
			tank2.drawTank(g);
			
			//draws both healthbars
			h1.drawHealth(g);
			h2.drawHealth(g);
			
			//ending screen when one tank is out of health
			if (tank1.getHealth() <= 0 || tank2.getHealth() <= 0) {
				g.setColor(Color.RED);
				g.setFont(new Font("Blackout", Font.BOLD, 100));
				if (tank1.getHealth() == 0) {
					g.drawString("Player 2 Wins!", (WIDTH/2)-350, 400);
				}
				else {
					g.drawString("Player 1 Wins!", (WIDTH/2)-350, 400);
				}
				timer.stop();
			}
			
			//don't forget - always call repaint last!
			repaint();
		}
	}
	
	//paintComponent for all animations
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
	
	public static void main(String[] args) {
		
		//create a frame and set properties
		JFrame frame = new JFrame("Shellshocked");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(50, 50);
		
		//set instructions for the program to exit when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//put the panel on the frame and make it visible
		frame.setContentPane(new Shellshocked());
		frame.setVisible(true);
		
	}

}