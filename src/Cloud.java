import java.awt.*;

public class Cloud {
	
	private int x;
	private int y;
	private Color color;
	private int diameter;
	private int xSpeed;
	
	public Cloud(int x, int y, int diameter, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.diameter = diameter;
		this.xSpeed = 0;

	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		// middle row
		g.fillOval(x-(diameter+diameter/2)+1, y-diameter/2, diameter, diameter);
		g.fillOval(x-diameter/2, y-diameter/2, diameter, diameter);
		g.fillOval(x+diameter/2, y-diameter/2, diameter, diameter);
		
		// top row
		g.fillOval(x-diameter, y-diameter, diameter, diameter);
		g.fillOval(x-1, y-diameter, diameter, diameter);
		
		// bottom row
		g.fillOval(x-diameter, y+1, diameter, diameter);
		g.fillOval(x-1, y, diameter, diameter);
	}
	
	//Animation Methods
	public void setXSpeed(int speed) {
		xSpeed = speed;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public int getXSpeed() {
		return xSpeed;
	}
	
	//Define move method for the cloud
	public void move(int rightEdge) {
		//update the x position to allow the cloud to shift to the right
		x = x+xSpeed;
		//check to see if it has reached the right edge of the screen
		if (x - (diameter*2) > rightEdge) {
			x = 0-diameter;
		}
	}
}