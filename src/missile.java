public class missile extends Sprites {

    private final int BOARD_HEIGHT = 700;
    int MISSILE_SPEED = 3;

    public missile(int x, int y,int checkType) // type => 1 means enemy 2 means player 
    {
        super(x, y);
        if (checkType == 1)
        {initMissilePlayer();}
        else if (checkType == 2)
        {initMissileEnemy();}
    }
    
    private void initMissilePlayer() {
        
        loadImage("src/images/missile.png");  
        getImageDimensions();
    }

    private void initMissileEnemy() {
        
        loadImage("src/images/missile.png");  
        getImageDimensions();
    }
    
    

    public void moveEnemyMissile() 
    {
    	y += MISSILE_SPEED-2;
        
        if (y > BOARD_HEIGHT) 
        {
            vis = false;
        }
    	
    	
    }
    
    public void movePlayerMissile() {
        
        y -= MISSILE_SPEED;
        
        if (y > BOARD_HEIGHT) {
            vis = false;
        }
    }
}