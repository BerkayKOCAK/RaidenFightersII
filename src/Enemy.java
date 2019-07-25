import java.util.ArrayList;

public class Enemy  extends Sprites 
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
	
	public Enemy(int x, int y) 
	{
	    super(x, y);
	    initCraft();
	}
	
	private void initCraft() 
	{
	
	    missiles = new ArrayList();
	    
	    loadImage("src/images/enemy.png"); 
	    getImageDimensions();
	}
	
	public void move(int state) //keeps going in given vector
	{
		if (state == 1) //ex: goes -x direction with 1 speed
		{
			//x = 0;
			x += -2;
			//y += 1;
			
		}
		else if (state == 2)
		{
			x += 2;
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