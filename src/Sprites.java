
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sprites {

    protected int x;
    public void setX(int x) {
		this.x = x;
	}

	public void setY(int f) {
		this.y = f;
	}

	protected int y;
    protected int width;
    protected int height;
    public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	protected boolean vis;
    protected Image image;

    public Sprites(int x, int y) //starts in these coordinates
    {

        this.x = x;			
        this.y = y;
        vis = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon pic = new ImageIcon(imageName);
        image = pic.getImage();
    }
    
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}