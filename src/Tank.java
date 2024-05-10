//Alex Chung
//Tank Class (Animation Project)
//3/24/2022

import java.awt.*;

public class Tank {
	
	//fields
	private int x;
	private int y;
	private int size;
	private int health;
	private double angle;
	private int direction;
	private double power;
	private double barrelLength;
	private int shotSize;
	private boolean showAim;

	//constructor for the tank
	public Tank (int x, int y, int size, int health, int direction) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.health = health; 
		this.angle = 0;
		this.direction = direction;
		this.power = 10;
		this.barrelLength = 1.8*size;
		this.shotSize = size/20;
		this.showAim = false;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * @return the power
	 */
	public double getPower() {
		return power;
	}

	/**
	 * @param power the power to set
	 */
	public void setPower(double power) {
		this.power = power;
	}


	/**
	 * @return the barrelLength
	 */
	public double getBarrelLength() {
		return barrelLength;
	}

	/**
	 * @param barrelLength the barrelLength to set
	 */
	public void setBarrelLength(double barrelLength) {
		this.barrelLength = barrelLength;
	}
	
	/**
	 * @return the shotSize
	 */
	public int getShotSize() {
		return shotSize;
	}

	/**
	 * @param shotSize the shotSize to set
	 */
	public void setShotSize(int shotSize) {
		this.shotSize = shotSize;
	}
	
	/**
	 * @return the showAim
	 */
	public boolean isShowAim() {
		return showAim;
	}

	/**
	 * @param showAim the showAim to set
	 */
	public void setShowAim(boolean showAim) {
		this.showAim = showAim;
	}
	
	public void drawTank(Graphics g) {
		
		//drawing the barrel
		drawBarrel(x+((size*direction)/1.5), y-(size*1.5), angle, (int)(size/4), power, g);
		
		//top part
		int[] xTop = {x-(size*direction), x-((size*direction)/2), x+((size*direction)/2), x+(size*direction)};
		int[] yTop = {y-size, y-(size*2), y-(size*2), y-size};
		g.setColor(Color.GREEN);
		g.fillPolygon(xTop, yTop, 4);
		g.setColor(Color.BLACK);
		g.drawPolygon(xTop, yTop, 4);
		
		//arcs of the bottom tread
		g.setColor(Color.GREEN.darker());
		g.fillArc((int)(x-(size*1.5)), y-size, size, size, 90, 180);
		g.setColor(Color.BLACK);
		g.drawArc((int)(x-(size*1.5)), y-size, size, size, 90, 180);
		
		g.setColor(Color.GREEN.darker());
		g.fillArc(x+(size/2), y-size, size, size, 270, 180);
		g.setColor(Color.BLACK);
		g.drawArc(x+(size/2), y-size, size, size, 270, 180);
		
		//bottom tread
		int[] xBottom = {x-(size*direction), x+(size*direction), x+(size*direction), x-(size*direction)};
		int[] yBottom = {y-size, y-size, y, y};
		g.setColor(Color.GREEN.darker());
		g.fillPolygon(xBottom, yBottom, 4);
		g.setColor(Color.BLACK);
		g.drawLine(x-(size*direction), y-size, x+(size*direction), y-size);
		g.drawLine(x-(size*direction), y, x+(size*direction), y);
		
		//bottom wheels
		g.setColor(Color.GREEN.darker().darker());
		g.fillOval((int)(x-(size*1.375)), (int)(y-(size*0.875)), (int)(size*0.75), (int)(size*0.75));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x-(size*1.375)), (int)(y-(size*0.875)), (int)(size*0.75), (int)(size*0.75));
		
		g.setColor(Color.GREEN.darker().darker());
		g.fillOval((int)(x+(size*0.625)), (int)(y-(size*0.875)), (int)(size*0.75), (int)(size*0.75));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x+(size*0.625)), (int)(y-(size*0.875)), (int)(size*0.75), (int)(size*0.75));
		
	}
	
	public void drawBarrel(double circleX, double circleY, double angle, int radius, double power, Graphics g) {
		
		//calculations for the barrel to keep the same shape even when the angle changes
		double radians = Math.toRadians(angle);
		double xLeft = (circleX - direction*(radius)*(Math.sin(radians)));
		double xLeftShift = (xLeft+(direction*barrelLength*(Math.cos(radians))));
		double xRight = (circleX + direction*(radius)*(Math.sin(radians)));
		double xRightShift = (xRight+(direction*barrelLength*(Math.cos(radians))));
		int[] xBarrel = {(int)(xLeft), (int)(xLeftShift), (int)(xRightShift), (int)(xRight)};
		double yDown = (circleY + (radius)*(Math.cos(radians)));
		double yDownShift = (yDown-(barrelLength*(Math.sin(radians))));
		double yUp = (circleY - (radius)*(Math.cos(radians)));
		double yUpShift = (yUp-(barrelLength*(Math.sin(radians))));
		int[] yBarrel = {(int)(yUp), (int)(yUpShift), (int)(yDownShift), (int)(yDown)};
		
		//drawing the barrel
		g.setColor(Color.GREEN.darker());
		g.fillPolygon(xBarrel, yBarrel, 4);
		g.setColor(Color.BLACK);
		g.drawPolygon(xBarrel, yBarrel, 4);
		
		//green circle to fill in any gaps (the axis of rotation for the barrel)
		g.setColor(Color.GREEN.darker());
		g.fillOval((int)(circleX-radius), (int)(circleY-radius), radius*2, radius*2);
		
		//the aimer which is essentially an extension of the barrel
		double aimerWidth = shotSize;
		double xLeftAim = circleX + (direction*(barrelLength + size*0.1)*(Math.cos(radians))) - direction*aimerWidth*(Math.sin(radians));
		double xLeftAimShift = xLeftAim + (direction*10*power*Math.cos(radians));
		double xRightAim = circleX + (direction*(barrelLength + size*0.1)*(Math.cos(radians))) + direction*aimerWidth*(Math.sin(radians));
		double xRightAimShift = xRightAim + (direction*10*power*Math.cos(radians));
		int[] xAimer = {(int)(xLeftAim), (int)(xLeftAimShift), (int)(xRightAimShift), (int)(xRightAim)};
		double yUpAim = circleY - ((barrelLength + size*0.1)*(Math.sin(radians))) - aimerWidth*(Math.cos(radians));
		double yUpAimShift = yUpAim - (10*power*Math.sin(radians));
		double yDownAim = circleY - ((barrelLength + size*0.1)*(Math.sin(radians))) + aimerWidth*(Math.cos(radians));
		double yDownAimShift = yDownAim - (10*power*Math.sin(radians));
		int[] yAimer = {(int)(yUpAim), (int)(yUpAimShift), (int)(yDownAimShift), (int)(yDownAim)};
		
		//only draws the aimer if it's the tanks turn to fire
		if (showAim == true) {
			g.setColor(Color.BLACK);
			g.fillPolygon(xAimer, yAimer, 4);
		}
	
	}

}
