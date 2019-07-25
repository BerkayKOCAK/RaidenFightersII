import java.util.ArrayList;

public class Enemy2  extends Sprites 
{
	private int dx;
	private int dy;
	private boolean destroyed = false;
	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	private ArrayList missiles;
	
	public Enemy2(int x, int y) 
	{
	    super(x, y);
	    initCraft();
	}
	
	private void initCraft() 
	{
	
	    missiles = new ArrayList();
	    
	    loadImage("src/images/enemy2.gif"); 
	    getImageDimensions();
	}
	
	public void move(int state) //keeps going in given vector
	{
		if (state == 1) //ex: goes -y direction with 1 speed
		{
			//x = 0;
			y += -1;
			//y += 1;
			
		}
		else if (state == 2)
		{
			y += 1;
			//y += -1;
			
			
		}

	}
	
	public ArrayList getMissiles() 
	{
	    return missiles;
	}
	
	public void fire() 
	    {
	        
			missiles.add(new missile(x , y + height , 2)); // !! warning
	
	    }

	

	
}