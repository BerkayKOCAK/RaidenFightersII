import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Engine extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6377211818306189574L;
	private final int ICRAFT_X = 250;
    private final int ICRAFT_Y = 500;
    int hearts;
    private final int enem_Y = 50;
    private final int enem_X = 50;
    private final int enem_Y2 = 100;
    private final int enem_X2 = 50;
    private final int[][] pos = {{50,50},{200,200},{0,0},{100,100},{300,300}};
    Random rand = new Random();  
    private final int[][] meteorPos = {	{rand.nextInt(300),rand.nextInt(300)},
    									{rand.nextInt(300),rand.nextInt(300)},
    									{rand.nextInt(300),rand.nextInt(300)}
    								  };
  
    
    int fireTimer;
    private Enemy Meteor;
    private Enemy enemy;
    private Enemy2 slidingMeteor;
    private Enemy2 enemy2; 		// create new enemy class difficulty
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> meteors;
    private final int DELAY = 10;
    private Timer timer;
    private animation player;
    int score ;
    public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		
	}

	int difficulty;
    private background background;
    private background gameOver;
    private background heart1;
    private background heart2;
    private background heart3;
	private int respawnCountEnemy = 0;
	private int respawnCountEnemy2 = 0;
	private int respawnCountMeteor = 0;
	private int respawnSlideMeteor = 0;
	private int count = 0;
	int borderReachX = 0;
	int borderReachY = 0;
    public Engine(int diffucultyCheck ) {

        this.difficulty = diffucultyCheck;
        this.score = 0;
        initEnemies();
        initMeteors();
        initEngine();
        
    }

    private void initEngine() {

        addKeyListener(new TAdapter());
        setFocusable(true);
       // System.out.println(" diff is in engine= " + diffuculty);
        hearts = 3;
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        background = new background(0,0,1);
        gameOver = new background(0,0,2);
        heart1 = new background(10,10,3);
        heart2 = new background(10,20,3);
        heart3 = new background(10,30,3);
        Random rand = new Random(); int value = rand.nextInt(100); 
        player = new animation(ICRAFT_X, ICRAFT_Y);
        //player.setHearts(player.getHearts()*difficulty);
        enemy = new Enemy(enem_X, enem_Y);
        enemy2 = new Enemy2(enem_X2, enem_Y2);
        Meteor = new Enemy(value,value);
        Meteor.loadImage("src/images/meteor.png");
        slidingMeteor = new Enemy2(value,value);
        slidingMeteor.loadImage("src/images/meteor.png");
        timer = new Timer(DELAY, this);
       
        fireTimer = difficulty;
        timer.start();
        
    
        
        
    }
    
    public void initEnemies() {
    	enemies = new ArrayList<>();

        for (int[] p : pos) {
        	enemies.add(new Enemy(p[0], p[1]));
        }
    }
    
    
    public void initMeteors() 
    {
    	meteors = new ArrayList<>();

        for (int[] p : meteorPos) 
        {
        	Enemy meteor = new Enemy(p[0], p[1]);
        	meteor.loadImage("src/images/meteor.png");
        	meteors.add(meteor);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(player.isVisible())
        {
        	drawBack(g);
	        drawPlayer(g);
	        
	        if (player.getHearts()==3)
	        {draw3Heart(g);}
	        if (player.getHearts()==2)
	        {draw2Heart(g);}
	        if (player.getHearts()==1)
	        {draw1Heart(g);}

	        if(difficulty == 1)
	        {
	        easyMode(g);
	        drawMeteor(g);
	        }
	
	        if (difficulty == 2)
	        {normalMode(g);drawMeteor(g);}
	        if (difficulty == 4)
	        {hardMode(g);}
	
        }
        else
        {
        	
        	drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    
private void draw3Heart(Graphics g)
    
    
    {
    	 Graphics2D g2d = (Graphics2D) g;
    	 g2d.drawImage(heart1.getImage(), 0,0, heart1.getWidth(), heart1.getHeight(), this);
    	 g2d.drawImage(heart2.getImage(), 10,0, heart2.getWidth(), heart2.getHeight(), this);
    	 g2d.drawImage(heart3.getImage(), 20,0, heart3.getWidth(), heart3.getHeight(), this);
    }
private void draw2Heart(Graphics g)


{
	 Graphics2D g2d = (Graphics2D) g;
	 g2d.drawImage(heart1.getImage(), 0,0, heart1.getWidth(), heart1.getHeight(), this);
	 g2d.drawImage(heart2.getImage(), 10,0, heart2.getWidth(), heart2.getHeight(), this);
	}
private void draw1Heart(Graphics g)


{
	 Graphics2D g2d = (Graphics2D) g;
	 g2d.drawImage(heart1.getImage(), 0,0, heart1.getWidth(), heart1.getHeight(), this);
	
}
    
    
    private void drawBack(Graphics g)
    
    
    {
    	 Graphics2D g2d = (Graphics2D) g;
    	 g2d.drawImage(background.getImage(), 0,0, background.getWidth(), background.getHeight(), this);
    }
    
    private void drawPlayer(Graphics g) 
    {
    	
    	  Graphics2D g2d = (Graphics2D) g;
          if (player.isVisible())
          {
          	g2d.drawImage(player.getImage(), player.getX(),
          		player.getY(), this);

          }
          
          //Draw Player Missile
          @SuppressWarnings("rawtypes")
		ArrayList ms = player.getMissiles();

          for (Object m1 : ms) 
          	{
  	            missile m = (missile) m1;
  	            g2d.drawImage(m.getImage(), m.getX(),
  	                    m.getY(), this);
          	}
    	
    	
    }
    private void drawGameOver(Graphics g)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.drawImage(gameOver.getImage(), 0,0, gameOver.getWidth(), gameOver.getHeight(), this);
    	
    }
    
    private void drawMeteor(Graphics g) 
    {
    	
    	Graphics2D graphMeteor= (Graphics2D) g;
    	for (Enemy meteor : meteors)    
        {
	    	//Draw Meteor	    		        
	        if(meteor.isVisible())
	        {   
	        	graphMeteor.drawImage(meteor.getImage(), meteor.getX(),
	        			meteor.getY(), this);
	        }
        
       }//if isVisible
    	
    }
    private void easyMode(Graphics g) 
    {
    	
    	//Draw Enemy
    	 Graphics2D graphEnemy = (Graphics2D) g;
         
         if(enemy.isVisible())
         {   
         	graphEnemy.drawImage(enemy.getImage(), enemy.getX(),
         		enemy.getY(), this);

         
        }//if isVisible
         //Draw Enemy Missile,
         @SuppressWarnings("rawtypes")
		ArrayList misileEnemy = enemy.getMissiles();

         for (Object m1 : misileEnemy) 
 	        {
 	        	
 	        	missile m = (missile) m1;
 	        	
 	            graphEnemy.drawImage(m.getImage(), m.getX(),
 	                    m.getY(), this);
 	        }
         
         
         
       //Draw Enemy2
    	 Graphics2D graphEnemy2 = (Graphics2D) g;
         
         if(enemy2.isVisible())
         {   
        	 graphEnemy2.drawImage(enemy2.getImage(), enemy2.getX(),
         		enemy2.getY(), this);

         
        }//if isVisible
         //Draw Enemy2 Missile,
         @SuppressWarnings("rawtypes")
		ArrayList misileEnemy2 = enemy2.getMissiles();

         for (Object m2 : misileEnemy2) 
 	        {
 	        	
 	        	missile mi2 = (missile) m2;
 	        	
 	        	graphEnemy2.drawImage(mi2.getImage(), mi2.getX(),
 	                    mi2.getY(), this);
 	        }

     
         
    	
    	
    }
    
    
    
    private void normalMode(Graphics g) 
    {
    	//Draw slidingMeteor
      	 Graphics2D graphSlidingMeteor = (Graphics2D) g;
           
           if(slidingMeteor.isVisible())
           {   
        	   graphSlidingMeteor.drawImage(slidingMeteor.getImage(), slidingMeteor.getX(),
           			slidingMeteor.getY(), this);

           
           }
    
    	
    	
    //Draw Enemy
   	 Graphics2D graphEnemy = (Graphics2D) g;
        
        if(enemy.isVisible())
        {   
        	graphEnemy.drawImage(enemy.getImage(), enemy.getX(),
        		enemy.getY(), this);

        
       }//if isVisible
        //Draw Enemy Missile,
        @SuppressWarnings("rawtypes")
		ArrayList misileEnemy = enemy.getMissiles();

        for (Object m1 : misileEnemy) 
	        {
	        	
	        	missile m = (missile) m1;
	        	
	            graphEnemy.drawImage(m.getImage(), m.getX(),
	                    m.getY(), this);
	        }
        
        
        
      //Draw Enemy2
   	 Graphics2D graphEnemy2 = (Graphics2D) g;
        
        if(enemy2.isVisible())
        {   
       	 graphEnemy2.drawImage(enemy2.getImage(), enemy2.getX(),
        		enemy2.getY(), this);

        
       }//if isVisible
        //Draw Enemy2 Missile,
        @SuppressWarnings("rawtypes")
		ArrayList misileEnemy2 = enemy2.getMissiles();

        for (Object m2 : misileEnemy2) 
	        {
	        	
	        	missile mi2 = (missile) m2;
	        	
	        	graphEnemy2.drawImage(mi2.getImage(), mi2.getX(),
	                    mi2.getY(), this);
	        }

    
    	
    	
    	
    }
    
    
    
    
    private void hardMode(Graphics g) 
    {
    	       
    	
//////////////////////////////////////////////Draw Enemy//////////////////////////////////////////////
        Graphics2D graphEnemy = (Graphics2D) g;
        for (Enemy soldier : enemies)
            
        {
        if(soldier.isVisible())
        	{   
        	
	        graphEnemy.drawImage(soldier.getImage(), soldier.getX(),soldier.getY(), this);			
	        }
        
       }//if isVisible
//////////////////////////////////////Draw Enemy Missile///////////////////////////////////////////////
        for (Enemy soldier : enemies)
        {
        @SuppressWarnings("rawtypes")
		ArrayList misileEnemy = soldier.getMissiles();
        for (Object m1 : misileEnemy) 
	        {
	        	
	        	missile m = (missile) m1;
	            graphEnemy.drawImage(m.getImage(), m.getX(),
	                    m.getY(), this);
	        }

    
        }
    
     }
    
//////////////////////////////   Action Update   //////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) 
    {
    	
    	
    	
    	score = score +(difficulty * 5);
    	 if(difficulty == 1)
         {
    		 for(Enemy tempMeteor : meteors)
 	    	{ updateMeteor(tempMeteor);}
    		 
    		 updatePlayer();
    		 updateMissiles();
    		 updateEnemy(enemy);
    		 checkCollisionsEasy();
    		 updateMissilesEnemy(enemy);
//    		 updateMeteor(Meteor);
    		 //checkCollisionsEasy();
    		 updateEnemy2(enemy2);
    		 updateMissilesEnemy2(enemy2);
    		 //checkCollisionsEasy();

    		

	     }
         else if (difficulty == 2)
         {
        	 for(Enemy tempMeteor : meteors)
  	    	{ updateMeteor(tempMeteor);}
     		 
        	 updatePlayer();
         	updateMissiles();
     		 updateEnemy(enemy);
     		 updateEnemy2(enemy2);
     		checkCollisionsNormal();
     		 updateMeteor(slidingMeteor);
     		 
    		 
    		checkCollisionsNormal();
     		 updateMissilesEnemy(enemy);
     		 updateMissilesEnemy2(enemy2);
     		checkCollisionsNormal();
     		
       
         }
         
         else if (difficulty == 4)
         {
    	for(Enemy temp : enemies)
	    	{
    		updatePlayer();
        	updateMissiles();
    		updateEnemy(temp);
	    	checkCollisionsHard();
	
	        updateMissilesEnemy(temp);
	        count++;
	        if((count == 200) &&(!temp.isDestroyed())) //fires every 2 sec
		        {
		        temp.fire();
		        count = 0;
		        }
	
	    	}
    	
    	}

    	 repaint();
    }

    private void updateMissiles() 
    {

        @SuppressWarnings("rawtypes")
		ArrayList ms = player.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

        	missile m = (missile) ms.get(i);

            if (m.isVisible()) {

                m.movePlayerMissile();
            } else {

                ms.remove(i);
            }
        }
    }
    
    private void updateMissilesEnemy(Enemy enemyHolder) 
    {
    	
    	@SuppressWarnings("rawtypes")
		ArrayList misileEnemy = enemyHolder.getMissiles();
    	
        for (int i = 0; i < misileEnemy.size(); i++) {

        	missile m = (missile) misileEnemy.get(i);
        	
        	if (m.isVisible() )           
            	{m.moveEnemyMissile();}
        	else
        		misileEnemy.remove(i);
            
            
        }
    }
     
    
    private void updateMissilesEnemy2(Enemy2 enemyHolder) 
    {
    	
        @SuppressWarnings("rawtypes")
		ArrayList misileEnemy = enemyHolder.getMissiles();

        for (int i = 0; i < misileEnemy.size(); i++) {

        	
        	missile m = (missile) misileEnemy.get(i);
        	m.MISSILE_SPEED = 5;
        	if (m.isVisible() )           
            	{m.moveEnemyMissile();}
        	else
        		misileEnemy.remove(i);
            
            
        }
    }
    
    
    
    
   
    
    private void updateEnemy(Enemy enemyHolder) 
    
    {

    	if (enemyHolder.x == 452)
    	{
    		enemyHolder.move(1);
    		borderReachX = 1 ;
    	}
  
    	else if (borderReachX == 1)
    	{
    		enemyHolder.move(1);
    		
    	}
    	
    	 if (enemyHolder.x == 2)
    	{
    		borderReachX = 0 ;
    		enemyHolder.move(2);
    	}
    	else if  (borderReachX == 0)
    	{
    		enemyHolder.move(2);
    		
    	}
    		
    	count++;
    	if (enemyHolder.isDestroyed()==true)
    	{
    		respawnCountEnemy++;
    		//System.out.println("enemy respawnCount =  "+respawnCountEnemy);
    	}
 
    	
//    	System.out.println("count=  "+count);
 //////////////////////Respawn Timer For Enemy//////////////////////	
    	if(respawnCountEnemy == 400/difficulty)
    	{
    		//System.out.println("enters to ressurrect =  ");
    		enemyHolder.setVisible(true);
    		enemyHolder.setDestroyed(false);
    		count = 0;
    		respawnCountEnemy = 0;
    	}
//////////////////////Missile Timer For Enemy//////////////////////  
    	if(difficulty == 4){fireTimer = 2;}
    	
    	if((count == 200/fireTimer) &&(!enemyHolder.isDestroyed())) 
        {
    	//System.out.println("fires enemy1 at count=  "+count);
    	enemyHolder.fire();
        count = 0;
        
        }
    	
    }

    
    private void updateEnemy2(Enemy2 enemyHolder)
    {
    	if (enemyHolder.y == 500)
    	{
    		enemyHolder.move(1);
    		borderReachY = 1 ;
    	}
  
    	else if (borderReachY == 1)
    	{
    		enemyHolder.move(1);
    		
    	}
    	
    	 if (enemyHolder.y == 0)
    	{
    		 borderReachY = 0 ;
    		enemyHolder.move(2);
    	}
    	else if  (borderReachY == 0)
    	{
    		enemyHolder.move(2);
    		
    	}
    		
    	count++;
    	if (enemyHolder.isDestroyed()==true)
    	{
    		respawnCountEnemy2++;
    		//System.out.println("enemy2 respawnCount =  "+respawnCountEnemy2);
    	}
		
    	
		//////////////////////Respawn Timer For Enemy//////////////////////	
		if(respawnCountEnemy2 == (200/difficulty))
		{
		enemyHolder.setVisible(true);
		enemyHolder.setDestroyed(false);
		count = 0;
		respawnCountEnemy2 = 0;
		}
		//////////////////////Missile Timer For Enemy//////////////////////    	
		if((count == 400/difficulty) &&(!enemyHolder.isDestroyed())) //fires every 2 sec
		{enemyHolder.fire();
		count = 0;}
    		
    }
    
    
    
    private void updateMeteor(Enemy meteorHolder)
    {
    	
    	if (meteorHolder.isDestroyed()==true)
    	{
    		respawnCountMeteor++;
    		//System.out.println("meteor respawnCount =  "+respawnCountMeteor);
    		
    	}
		
    	
		//////////////////////Respawn Timer For Meteor//////////////////////	
		if(respawnCountMeteor == (200/difficulty))
		{
		meteorHolder.setVisible(true);
		meteorHolder.setDestroyed(false);

		respawnCountMeteor = 0;
		}
	
    		
    }
    
    
    private void updateMeteor(Enemy2 slidingMeteorHolder) 
    
    {
    	
    	slidingMeteorHolder.move(2);
    		
    	
    	if (slidingMeteorHolder.isDestroyed()==true)
    	{
    		respawnSlideMeteor++;
    	}
		
    	if(slidingMeteorHolder.getY() > 700)
    	{
    		
    		Random rand = new Random(); int value = rand.nextInt(400); 
    		slidingMeteorHolder.setX(value);
         	value = rand.nextInt(10); 
         	slidingMeteorHolder.setY(value);
         	slidingMeteorHolder.setVisible(true);
    		slidingMeteorHolder.setDestroyed(false);
    		
    	
    	}
    	
 //////////////////////Respawn Timer For Meteor//////////////////////	
    	if(respawnSlideMeteor == 200/difficulty)
    	{
    		slidingMeteorHolder.setVisible(true);
    		slidingMeteorHolder.setDestroyed(false);
    		
    		respawnSlideMeteor = 0;
    	}

    	
    }  
    
    
    
    

    private void updatePlayer() {
    	if(player.isVisible())
    	player.move();
    	
 
    }
    
    
    public void checkCollisionsEasy()
    {
    	Rectangle playerCOL = player.getBounds();
    	//Rectangle meteorCOL = Meteor.getBounds();
        Rectangle enemyCOL = enemy.getBounds();
        Rectangle enemyCOL2 = enemy2.getBounds();
        
        for(Enemy tempMeteor : meteors)
        {Rectangle meteorCOL = tempMeteor.getBounds();
        
	    
        
        if (tempMeteor.isDestroyed() == true)
        {
        	meteorCOL.setBounds(0, 0, 0, 0);
        	
        }
        
        if (enemy.isDestroyed() == true)
        {
        	enemyCOL.setBounds(0, 0, 0, 0);
        	
        }
        
        
        if (enemy2.isDestroyed() == true)
        {
        	enemyCOL2.setBounds(0, 0, 0, 0);
        	
        }
        
        if (player.getHearts() == 0)
        {
        	playerCOL.setBounds(0, 0, 0, 0);
        	
        }
//        Rectangle playerSpawnCol =  new Rectangle(ICRAFT_X, ICRAFT_Y, player.width, player.height);
//        Rectangle enemySpawnCol =  new Rectangle(enem_X, enem_Y, enemy.width, enemy.height);
            
        if ((playerCOL.intersects(meteorCOL))&&(Meteor.isDestroyed() != true)) 
        {
    		player.setHearts(player.getHearts()-1);
//    		System.out.println("hearts after crash = "+player.getHearts());
    		if(player.getHearts() <= 0)
 			{player.setVisible(false);}
    		tempMeteor.setDestroyed(true);
    		tempMeteor.setVisible(false);
    		Random rand = new Random(); int value = rand.nextInt(200); 
    		tempMeteor.setX(value);
        	value = rand.nextInt(100); 
        	tempMeteor.setY(value);
        	score = score + (difficulty*4); 
        	respawnCountMeteor = 0;
        }
        
        
        if ((playerCOL.intersects(enemyCOL))&&(enemy.isDestroyed() != true)) 
            {
        		player.setHearts(player.getHearts()-1);
        		if(player.getHearts() <= 0)
     			{player.setVisible(false);}
        		enemy.setDestroyed(true);
            	enemy.setVisible(false);
            	score = score + (difficulty*5);
            	respawnCountEnemy = 0;
            }
        
        
        if ((playerCOL.intersects(enemyCOL2))&&(enemy2.isDestroyed() != true)) 
        {
    		player.setHearts(player.getHearts()-1);
    		if(player.getHearts() <= 0)
 			{player.setVisible(false);}
    		enemy2.setDestroyed(true);
        	enemy2.setVisible(false);
        	score = score + (difficulty*5);
        	respawnCountEnemy2 = 0;
            
        }
       
//////////////////////////PLayer Missile Hits Enemy/////////////////////////////7
        ArrayList<missile> ms = player.getMissiles();

        for (missile m : ms) {

            Rectangle playerMissileCol = m.getBounds();
           // Rectangle enemyCol = enemy.getBounds();
            	if(player.getHearts() == 0)
            	{
            		playerMissileCol.setBounds(0, 0, 0, 0);
            		
            	}
            
            	if (playerMissileCol.intersects(meteorCOL) ) //hits meteor
                {
                    
                	m.setVisible(false);
//                    System.out.printf("kiltha bitch at x");
//            		System.out.println(playerMissileCol.getX());
//            		System.out.printf("kiltha bitch y");
//            		System.out.println(playerMissileCol.getY());
            		tempMeteor.setVisible(false);
            		tempMeteor.setDestroyed(true);
                    Random rand = new Random(); int value = rand.nextInt(300); 
                    tempMeteor.setX(value);
                	value = rand.nextInt(100); 
                	tempMeteor.setY(value);
                	score = score + (difficulty*3);
                	respawnCountMeteor = 0;
                
                    
                    
                }
            	
            	
            	
                if (playerMissileCol.intersects(enemyCOL) ) // hits enemy
                {
                    
                	m.setVisible(false);
//                    System.out.printf("kiltha bitch at x");
//            		System.out.println(playerMissileCol.getX());
//            		System.out.printf("kiltha bitch y");
//            		System.out.println(playerMissileCol.getY());
                    enemy.setVisible(false);
                    enemy.setDestroyed(true);
                    score = score + (difficulty*5);
                    respawnCountEnemy = 0;
                 
                  
                    
                    
                    
                }
                
                
                if (playerMissileCol.intersects(enemyCOL2) ) // hits enemy2
                {
                    
                	m.setVisible(false);
//                    System.out.printf("kiltha bitch at x");
//            		System.out.println(playerMissileCol.getX());
//            		System.out.printf("kiltha bitch y");
//            		System.out.println(playerMissileCol.getY());
                    enemy2.setVisible(false);
                    enemy2.setDestroyed(true);
                    score = score + (difficulty*5);
                    respawnCountEnemy2 = 0;
                    
                    
                }
            }
        }
/////////////////////////////Enemy Missile Hits Player/////////////////////////////////
        ArrayList<missile> missileList = enemy.getMissiles();
        int chi =0;
        for (missile missileEnemy : missileList) {
        	
            Rectangle enemyMissileCol = missileEnemy.getBounds();
           // Rectangle enemyCol = enemy.getBounds();

            	if(enemy.isDestroyed())
            	{
            		
            	}
            	
                if (enemyMissileCol.intersects(playerCOL))// &&  (!enemyMissileCol.intersects(playerSpawnCol)) ) //Faulty logic
                {											//it still hits player at spawn point but this not effect hearts (strange!!)
                	missileEnemy.setVisible(false);
                	player.setHearts(player.getHearts()-1);
                	if(player.getHearts() <= 0)
                	{player.setVisible(false);}
                }
            }

        

        
    	
       
        
        	//System.out.println("hearts in engine = "+player.getHearts());
//        	System.out.println("chi in if = "+chi);
//        	player.setHearts(player.getHearts()-1);
//        	if(player.getHearts() <= 0)
//    		{player.setVisible(false);}
        
        
/////////////////////////////Enemy2 Missile Hits Player/////////////////////////////////        
        ArrayList<missile> missileList2 = enemy2.getMissiles();

        for (missile missileEnemy2 : missileList2) {
        	
            Rectangle enemyMissileCol2 = missileEnemy2.getBounds();
           // Rectangle enemyCol = enemy.getBounds();

            	if(enemy2.isDestroyed())
            	{
            		
            	}
            	
                if (enemyMissileCol2.intersects(playerCOL))// &&  (!enemyMissileCol.intersects(playerSpawnCol)) ) //Faulty logic
                {											//it still hits player at spawn point but this not effect hearts (strange!!)
                	missileEnemy2.setVisible(false);
                	player.setHearts(player.getHearts()-1);
                	if(player.getHearts() <= 0)
                	{player.setVisible(false);}

              
            }
        
    	
        }
    }
    
    public void checkCollisionsNormal()
    {
    	Rectangle playerCOL = player.getBounds();
    	Rectangle slidingMeteorCOL = slidingMeteor.getBounds();
        Rectangle enemyCOL = enemy.getBounds();
        Rectangle enemyCOL2 = enemy2.getBounds();
        
        for(Enemy tempMeteor : meteors)
        {Rectangle meteorCOL = tempMeteor.getBounds();
        
	    
        if (slidingMeteor.isDestroyed() == true)
        {
        	slidingMeteorCOL.setBounds(0, 0, 0, 0);
        	
        }
        
        
        if (tempMeteor.isDestroyed() == true)
        {
        	meteorCOL.setBounds(0, 0, 0, 0);
        	
        }
        
        if (enemy.isDestroyed() == true)
        {
        	enemyCOL.setBounds(0, 0, 0, 0);
        	
        }
        
        
        if (enemy2.isDestroyed() == true)
        {
        	enemyCOL2.setBounds(0, 0, 0, 0);
        	
        }
        
        if (player.getHearts() == 0)
        {
        	playerCOL.setBounds(0, 0, 0, 0);
        	
        }
//        Rectangle playerSpawnCol =  new Rectangle(ICRAFT_X, ICRAFT_Y, player.width, player.height);
//        Rectangle enemySpawnCol =  new Rectangle(enem_X, enem_Y, enemy.width, enemy.height);
            
        if ((playerCOL.intersects(slidingMeteorCOL))&&(slidingMeteor.isDestroyed() != true)) 
        {
    		player.setHearts(player.getHearts()-3);
//    		System.out.println("hearts after crash with sliding meteor = "+player.getHearts());
    		if(player.getHearts() <= 0)
 			{player.setVisible(false);}
    		slidingMeteor.setDestroyed(true);
    		slidingMeteor.setVisible(false);
    		Random rand = new Random(); int value = rand.nextInt(400); 
    		slidingMeteor.setX(value);
        	value = rand.nextInt(10); 
        	slidingMeteor.setY(value);
        	score = score + (difficulty*5);
        	respawnSlideMeteor = 0;
        }
        
        
        
        
        
        
        if ((playerCOL.intersects(meteorCOL))&&(Meteor.isDestroyed() != true)) 
        {
    		player.setHearts(player.getHearts()-3);
//    		System.out.println("hearts after crash = "+player.getHearts());
    		if(player.getHearts() <= 0)
 			{player.setVisible(false);}
    		tempMeteor.setDestroyed(true);
    		tempMeteor.setVisible(false);
    		Random rand = new Random(); int value = rand.nextInt(200); 
    		tempMeteor.setX(value);
        	value = rand.nextInt(100); 
        	tempMeteor.setY(value);
        	score = score + (difficulty*5);
        	respawnCountMeteor = 0;
        }
        
        
        if ((playerCOL.intersects(enemyCOL))&&(enemy.isDestroyed() != true)) 
            {
        		player.setHearts(player.getHearts()-1);
        		if(player.getHearts() <= 0)
     			{player.setVisible(false);}
        		enemy.setDestroyed(true);
            	enemy.setVisible(false);
            	score = score + (difficulty*5);
            	respawnCountEnemy = 0;
            }
        
        
        if ((playerCOL.intersects(enemyCOL2))&&(enemy2.isDestroyed() != true)) 
        {
    		player.setHearts(player.getHearts()-1);
    		if(player.getHearts() <= 0)
 			{player.setVisible(false);}
    		enemy2.setDestroyed(true);
        	enemy2.setVisible(false);
        	score = score + (difficulty*5);
        	respawnCountEnemy2 = 0;
            
        }
       
//////////////////////////PLayer Missile Hits Enemy/////////////////////////////7
        ArrayList<missile> ms = player.getMissiles();

        for (missile m : ms) {

            Rectangle playerMissileCol = m.getBounds();
           // Rectangle enemyCol = enemy.getBounds();
            	if(player.getHearts() == 0)
            	{
            		playerMissileCol.setBounds(0, 0, 0, 0);
            		
            	}
            
            	
            	if (playerMissileCol.intersects(slidingMeteorCOL) ) //hits  sliding meteor
                {
                    
                	m.setVisible(false);
//                  System.out.printf("kiltha bitch at x");
//            		System.out.println(playerMissileCol.getX());
//            		System.out.printf("kiltha bitch y");
//            		System.out.println(playerMissileCol.getY());
            		slidingMeteor.setVisible(false);
            		slidingMeteor.setDestroyed(true);
                    Random rand = new Random(); int value = rand.nextInt(400); 
                    slidingMeteor.setX(value);
                	value = rand.nextInt(10); 
                	slidingMeteor.setY(value);
                	score = score + (difficulty*4);
                	respawnSlideMeteor = 0;
                    
                    
                }
            	
            	
            	
            	if (playerMissileCol.intersects(meteorCOL) ) //hits meteor
                {
                    
                	m.setVisible(false);
//                    System.out.printf("kiltha bitch at x");
//            		System.out.println(playerMissileCol.getX());
//            		System.out.printf("kiltha bitch y");
//            		System.out.println(playerMissileCol.getY());
            		tempMeteor.setVisible(false);
            		tempMeteor.setDestroyed(true);
                    Random rand = new Random(); int value = rand.nextInt(300); 
                    tempMeteor.setX(value);
                	value = rand.nextInt(100); 
                	tempMeteor.setY(value);
                	score = score + (difficulty*4);
                	respawnCountMeteor = 0;
                    
                    
                }
            	
            	
            	
                if (playerMissileCol.intersects(enemyCOL) ) // hits enemy
                {
                    
                	m.setVisible(false);
//                    System.out.printf("kiltha bitch at x");
//            		System.out.println(playerMissileCol.getX());
//            		System.out.printf("kiltha bitch y");
//            		System.out.println(playerMissileCol.getY());
                    enemy.setVisible(false);
                    enemy.setDestroyed(true);
                    score = score + (difficulty*5);
                    respawnCountEnemy = 0;
                 
                  
                    
                    
                    
                }
                
                
                if (playerMissileCol.intersects(enemyCOL2) ) // hits enemy2
                {
                    
                	m.setVisible(false);
//                    System.out.printf("kiltha bitch at x");
//            		System.out.println(playerMissileCol.getX());
//            		System.out.printf("kiltha bitch y");
//            		System.out.println(playerMissileCol.getY());
                    enemy2.setVisible(false);
                    enemy2.setDestroyed(true);
                    score = score + (difficulty*5);
                    respawnCountEnemy2 = 0;
                    
                    
                }
            }
        }
/////////////////////////////Enemy Missile Hits Player/////////////////////////////////
        ArrayList<missile> missileList = enemy.getMissiles();

        for (missile missileEnemy : missileList) {
        	
            Rectangle enemyMissileCol = missileEnemy.getBounds();
           // Rectangle enemyCol = enemy.getBounds();

            	if(enemy.isDestroyed())
            	{
            		
            	}
            	
                if (enemyMissileCol.intersects(playerCOL))// &&  (!enemyMissileCol.intersects(playerSpawnCol)) ) //Faulty logic
                {											//it still hits player at spawn point but this not effect hearts (strange!!)
                	missileEnemy.setVisible(false);
                	player.setHearts(player.getHearts()-1);
//                	System.out.printf("gotcha at x");
//            		System.out.println(enemyMissileCol.getX());
//            		System.out.printf("gotcha at y");
//            		System.out.println(enemyMissileCol.getY());
                    if(player.getHearts() <= 0)
            		{player.setVisible(false);}
                }
            }
/////////////////////////////Enemy2 Missile Hits Player/////////////////////////////////        
        ArrayList<missile> missileList2 = enemy2.getMissiles();

        for (missile missileEnemy2 : missileList2) {
        	
            Rectangle enemyMissileCol2 = missileEnemy2.getBounds();
           // Rectangle enemyCol = enemy.getBounds();

            	if(enemy2.isDestroyed())
            	{
            		
            	}
            	
                if (enemyMissileCol2.intersects(playerCOL))// &&  (!enemyMissileCol.intersects(playerSpawnCol)) ) //Faulty logic
                {											//it still hits player at spawn point but this not effect hearts (strange!!)
                	missileEnemy2.setVisible(false);
                	player.setHearts(player.getHearts()-1);
//                	System.out.printf("gotcha at x");
//            		System.out.println(enemyMissileCol.getX());
//            		System.out.printf("gotcha at y");
//            		System.out.println(enemyMissileCol.getY());
                    if(player.getHearts() <= 0)
            		{player.setVisible(false);}
                }
            }
        
    	
        
    }
    
    
    public void checkCollisionsHard() {
    	
        Rectangle playerCOL = player.getBounds();
        for(Enemy temp : enemies)
        {Rectangle enemyCOL = temp.getBounds();
        
	        if (temp.isDestroyed() == true)
	        {
	        	enemyCOL.setBounds(0, 0, 0, 0);
	        	
	        }
        
        
        if (player.getHearts() == 0)
        {
        	playerCOL.setBounds(0, 0, 0, 0);
        	
        }

            
        if ((playerCOL.intersects(enemyCOL))&&(temp.isDestroyed() == false)) 
            {
        		player.setHearts(player.getHearts()-1);
        		if(player.getHearts() <= 0)
     			{player.setVisible(false);}
        		temp.setDestroyed(true);
        		temp.setVisible(false);
        		score = score + (difficulty*5);
        		
        		//enemies.remove(temp); causes null exception
                
            }
        
       
//////////////////////////PLayer Missile Hits Enemy/////////////////////////////7
        ArrayList<missile> ms = player.getMissiles();

        for (missile m : ms) {

            Rectangle playerMissileCol = m.getBounds();
           // Rectangle enemyCol = enemy.getBounds();
            	if(player.getHearts() == 0)
            	{
            		playerMissileCol.setBounds(0, 0, 0, 0);
            		
            	}
            
                if (playerMissileCol.intersects(enemyCOL) ) 
                {
                    
                	m.setVisible(false);

                	score = score + (difficulty*5);
                
                	temp.setVisible(false);
                	temp.setDestroyed(true);
                	score = score + (difficulty*5);
                	
                	//enemies.remove(temp); causes null exception
                    
                    
                }
            }
         
/////////////////////////////Enemy Missile Hits Player/////////////////////////////////
        ArrayList<missile> missileList = temp.getMissiles();

        for (missile missileEnemy : missileList) {
        	
            Rectangle enemyMissileCol = missileEnemy.getBounds();
           // Rectangle enemyCol = enemy.getBounds();

            
            	
                if (enemyMissileCol.intersects(playerCOL))// &&  (!enemyMissileCol.intersects(playerSpawnCol)) ) //Faulty logic
                {											//it still hits player at spawn point but this not effect hearts (strange!!)
                	missileEnemy.setVisible(false);
                	player.setHearts(player.getHearts()-1);
                    if(player.getHearts() <= 0)
            		{player.setVisible(false);}
                }
            }
        
        }
        
        
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	player.keyPressed(e);
        }
    }

   
    
  
		 
		 
	
  
    
    
    
    
    
}//Engine


