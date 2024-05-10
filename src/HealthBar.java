//Alex Chung
//3/30/2022
//Health Bar

import java.awt.*;

public class HealthBar {
	
	private int x;
	private int y;
	private double size;
	private double health;
	
	//constructor for the healthbar
	public HealthBar(int x, int y, double size, double health) {
	
		this.x = x;
		this.y = y;
		this.size = size;
		this.health = health;
		
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
	public double getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(double size) {
		this.size = size;
	}

	/**
	 * @return the health
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(double health) {
		this.health = health;
	}
	
	//drawing the healthbar (green is health left, red shows health lost)s
	public void drawHealth(Graphics g) {
		
		g.setColor(Color.RED);
		g.fillRect(x, y, (int)(size*10), (int)(size*2));
		g.setColor(Color.GREEN);
		g.fillRect(x, y, (int)(size*10*(health/100)), (int)(size*2));
		g.setColor(Color.BLACK);
		g.drawRect(x, y, (int)(size*10), (int)(size*2));
		g.drawRect(x, y, (int)(size*10*(health/100)), (int)(size*2));
		
	}

}
