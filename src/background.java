import java.util.ArrayList;

class background extends Sprites
{
	private int dx;
	private int dy;

	public background(int x, int y,int select) 
	{
	    super(x, y);
	    initBack(select);
	}
	
	private void initBack(int select) 
	{
	
		if(select ==1)
		{
	    loadImage("src/images/source.gif"); 
	    getImageDimensions();
		}
		else if (select ==2)
		{
			loadImage("src/images/gameover.gif"); 
		    getImageDimensions();
			
		}
		else if (select ==3)
		{
			loadImage("src/images/heart.png"); 
		    getImageDimensions();
			
		}
		
	}
	
	
	

	
}
	
	

