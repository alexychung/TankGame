import java.awt.Color;
import java.awt.Graphics;

public class Projectile {
	
	private double x;			//x-coordinate of the center of the projectile
	private double y;			//y-coordinate of the center of the projectile
	private double diameter;	//diameter of the projectile
	private double radius = this.diameter/2;		//radius of the projectile
	private Color color;		//color of the projectile
	private double xSpeed;		//x-speed = change in x-position
	private double ySpeed;		//y-speed = change in y-position
	private double xAcceleration;	//change in x speed
	private double yAcceleration;	//change in y speed
	private boolean border; //border or not
	
	//constructor for the projectile
	public Projectile (double x, double y, double velocity, double angle, int diameter, Color color, boolean border) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.radius = diameter/2;
		this.xSpeed = velocity*(Math.cos(Math.toRadians(angle)));
		this.ySpeed = velocity*(Math.sin(Math.toRadians(angle)));
		this.color = color;
		this.border = border;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the diameter
	 */
	public double getDiameter() {
		return diameter;
	}
	/**
	 * @param diameter the diameter to set
	 */
	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}
	/**
	 * @return the radius
	 */
	public double getRadius() {
		return diameter/2;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.diameter = radius*2;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @return the xSpeed
	 */
	public double getxSpeed() {
		return xSpeed;
	}
	/**
	 * @param xSpeed the xSpeed to set
	 */
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	/**
	 * @return the ySpeed
	 */
	public double getySpeed() {
		return ySpeed;
	}
	/**
	 * @param ySpeed the ySpeed to set
	 */
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	/**
	 * @return the xAcceleration
	 */
	public double getxAcceleration() {
		return xAcceleration;
	}
	/**
	 * @param xAcceleration the xAcceleration to set
	 */
	public void setxAcceleration(double xAcceleration) {
		this.xAcceleration = xAcceleration;
	}
	/**
	 * @return the yAcceleration
	 */
	public double getyAcceleration() {
		return yAcceleration;
	}
	/**
	 * @param yAcceleration the yAcceleration to set
	 */
	public void setyAcceleration(double yAcceleration) {
		this.yAcceleration = yAcceleration;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return the border
	 */
	public boolean isBorder() {
		return border;
	}

	/**
	 * @param border the border to set
	 */
	public void setBorder(boolean border) {
		this.border = border;
	}
	
	//drawing the projectile (just a circle)
	public void drawProjectile(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (x-(diameter/2)), (int) (y-(diameter/2)), (int) (diameter), (int) (diameter));
		//optional border (for the trail in the actual game)
		if (border == true) {
			g.setColor(Color.BLACK);
			g.drawOval((int) (x-(diameter/2)), (int) (y-(diameter/2)), (int) (diameter), (int) (diameter));
		}
	}
	
	//move method
	public void move() {
		//update speeds according to accelerations
		xSpeed = xSpeed + xAcceleration;
		ySpeed = ySpeed + yAcceleration;
		
		//update positions according to speeds
		x = x + xSpeed;
		y = y - ySpeed;
	}

}
