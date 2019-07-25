
public class Laser extends Sprites

{
	private final int BOARD_HEIGHT = 700;
	
	  public Laser(int x, int y) {
		super(x, y);
		
	}

	
	  private void initLaser() {
        
        loadImage("src/images/laser.gif");  
        getImageDimensions();
    }



}
