
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class animation extends Sprites 
{
	private int Hearts = 3;
    private int dx;
    private int dy;
    private ArrayList missiles;

    public animation(int x, int y) 	// checkType = 1 => player
    												// checkType = 2 => enemy
    {
        super(x, y);
        initCraft();
    }

    private void initCraft() 
    {
    
    		missiles = new ArrayList();
            loadImage("src/images/craft.png"); 
            getImageDimensions();
   
        
    }

    public void move() 
    {
        x += dx;
        y += dy;
    }

    public ArrayList getMissiles() 
    {
        return missiles;
    }

    public void keyPressed(KeyEvent e) 
    {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void fire() 
    {
        if(this.isVisible())
    	missiles.add(new missile(x + width/ 2, y + height ,1)); // !! warning
    }
    
    
//    public void fireLaser() 
//    {
//        if(this.isVisible())
//    	Laser beam = new Laser(x + width/ 2, y + height ); // !! warning
//    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
        
    }

	public int getHearts() {
		return Hearts;
	}

	public void setHearts(int hearts) {
		Hearts = hearts;
	}
}