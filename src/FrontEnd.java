import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.*;



import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("serial")
public class FrontEnd extends JFrame //implements ActionListener 
{
	int diffucultyCheck;
	int check = 0;
	Database database;
	int score ;
	
	public FrontEnd () 
	{
		
		InitializeUI();
		
	}//Gui consturact
	



	public int getScore() {
		return score;
	}




	public void setScore(int score) {
		this.score = score;
	}




	private void InitializeUI()
	{
		database = new Database();
		
		ImageIcon menuPic  = new ImageIcon("src/images/MainMenu.png");
		ImageIcon background = new ImageIcon("src/images/latest.jpg");
		
		JLabel menuLabel = new JLabel(menuPic);
		JLabel backgroundLabel = new JLabel(background);
		diffucultyCheck = 1;
		
		
		JFrame frame = new JFrame();
		frame.setName(" Raiden Raiders II ");
		JButton Start = new JButton("Start");		//ADD pictures to these buttons
		//Start.setBounds(250, 500, 100, 50);
//		Start.setLocation(250, 400);
		JButton diffuculty = new JButton("Choose Difficulty");
		//diffuculty.setBounds(250, 450, 100,50);
//		diffuculty.setLocation(250, 450);
		JButton Help = new JButton("Help");
		//Help.setBounds(250, 400, 100, 50);
//		diffuculty.setLocation(250, 350);
		JButton login = new JButton(" LOGIN ");
		//login.setBounds(250, 450, 100,50);
//		login.setLocation(250, 450);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JTextField nameBox = new JTextField("Name");
  		JTextField passwordBox = new JTextField("Password");
		play();
		login.addActionListener(new ActionListener() 
		  { 
			
		  	public void actionPerformed(ActionEvent e) 
		  	{ 
		  		
		  		JDialog dialog = new JDialog();
		  		JLabel loginPOP = new JLabel();
		  		
		  		dialog.setTitle("LOGIN SCREEN");
                JButton EnterLOG = new JButton("Enter");
                EnterLOG.addActionListener(new ActionListener() 
      		  {

				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					String tempName;
					String tempPassword;
					String Str;
					tempName = nameBox.getText(); 
					tempPassword = passwordBox.getText();
					Str =  database.getUserDatabase().get(tempPassword);
					
//					You are comparing object references. 
//					This will return true only if they both refer to same same instance. 
//					If you wish to compare the contents of these objects, use .equals().
					
					if (tempName.equals(Str) )	//if the person already exist				
					{
						int tempr = (database.getScoreDatabase().get(tempName));
						JOptionPane.showMessageDialog(null, "Welcome Back " + Str + "\n Your score was = " + tempr , "Greetings", JOptionPane.PLAIN_MESSAGE);
						
						//System.out.println("found " + tempName + " hes score = " + tempr );
					}
					else
					{
						database.addNewUser(tempPassword,tempName);
						database.addNewScore( tempName , 0 );
						JOptionPane.showMessageDialog(null, "Welcome to RAIDEN FIGHTERS II \n I've added you to our database" , "Greetings", JOptionPane.PLAIN_MESSAGE);
						database.saveToFile();

					}
					//ShootingMissilesEx ex = new ShootingMissilesEx();
	
					dialog.setVisible(false);
					frame.setVisible(false);
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 0;
					c.gridy = 1;
					frame.add(Start,c);
					frame.remove(login);
					c.gridx = 0;
					c.gridy = 2;
					frame.add(Help,c);
					c.gridx = 0;
					c.gridy = 3;
					frame.add(diffuculty,c);
					
					frame.setVisible(true);
					//ex.setVisible(true);
       
				}

			
                	                       	
      		  });//EnterButton
               
                //dialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                
                dialog.add(nameBox);
                dialog.add(passwordBox);
                dialog.add(EnterLOG);
                dialog.setLayout(new FlowLayout());
                dialog.setSize(250, 200);
                dialog.setAlwaysOnTop(true);
                dialog.setModal(true);
                
                //frame2.setLocationByPlatform(true);
                dialog.setVisible(true);
		  		//Engine newGame = new Engine(ex);
		  		
				//Scanner in = new Scanner(System.in);
//				String username = in.nextLine();
//		
//				String password = in.nextLine();
					
//				String var=database.getUserDatabase().get(password);
//				System.out.println(var);
					
		
				
				//String var= engineHolder.userDatabase.get(passwordHolder); 
				//System.out.println(var); 		!! print these in credits
				
				
		  		
		  		
		  		
		  	}
		  
		  
		  });//Login
		
		
//////////////////////////////Let the game begin////////////////////////////////////////////////////////////////////////////			
		 Start.addActionListener(new ActionListener() 
 		  {

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				//System.out.println(" diff is = " + diffucultyCheck);
				GameScreen ex = new GameScreen(diffucultyCheck);
				ex.setVisible(true);
				//frame.remove(menuLabel);
				frame.setVisible(false);
				
				ex.addWindowListener(new WindowAdapter()
		        {
		            @Override
		            public void windowClosing(WindowEvent e)
		            {
//		                System.out.println("Closed");
//		                System.out.println("front end Score = " +ex.getScore());
						database.addNewScore(nameBox.getText(),ex.getScore());
						database.saveToFile();
		                e.getWindow().dispose();
		            }
		        });
				
				
				
				
			}
			
 		  });
		 
		 
		
//////////////////////////////////////////Difficulty Button//////////////////////////////////////////////////////////////////////				
		 diffuculty.addActionListener(new ActionListener() 
		  { 
			
		  	public void actionPerformed(ActionEvent e) 
		  	{ 
		  		JDialog difficultyDialog = new JDialog();
		  		JRadioButton option1 = new JRadioButton(" Easy ");
		  		JRadioButton option2 = new JRadioButton(" Normal ");
		  		JRadioButton option3 = new JRadioButton(" Hard ");
		  		JButton apply = new JButton(" Apply ");
		  		ButtonGroup group = new ButtonGroup();
		  		
		  		apply.addActionListener(new ActionListener() 
		 		  {

					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						if (option1.isSelected()) {
							diffucultyCheck = 1;
						} else if (option2.isSelected()) {
							diffucultyCheck = 2;
						} else if (option3.isSelected()) {
							diffucultyCheck = 4;
						}
						 
						 difficultyDialog.dispose();
						
	
						
					}
					
		 		  });		
				group.add(option1);
				group.add(option2);
				group.add(option3);
				difficultyDialog.add(option1);
				difficultyDialog.add(option2);
				difficultyDialog.add(option3);
				difficultyDialog.add(apply);
				
				
				
				
				
				difficultyDialog.setLayout(new FlowLayout());
				difficultyDialog.setSize(250, 200);
				difficultyDialog.setVisible(true);
		  		
		  	}
		  	
		  	
		  	
		  });
		 
		 
		 
		 
		 
		 
///////////////////////////////////////Scoreboard//////////////////////////////////////////////////////////////////////	
		 Help.addActionListener(new ActionListener() 
		  {

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Iterator scoreIterator = database.sortByValue(database.getScoreDatabase()).entrySet().iterator();
				String tempScoreName ="";
				String tempScore ="";
				ArrayList<JTextField> listOfTextFields = new ArrayList<JTextField>();
				//database.sortByValue(database.getScoreDatabase());
				Map.Entry pair;
				//frame.setVisible(false);
				JFrame scoreFrame = new JFrame("ScoreBoard");
				//scroreFrame.setLayout(new GridBagLayout());
				scoreFrame.setLayout(new FlowLayout());
	            while (scoreIterator.hasNext()) 
				{
	            	
	            	pair = (Map.Entry)scoreIterator.next();
		            tempScoreName = pair.getKey().toString();
		            tempScore = pair.getValue().toString();
		            JLabel  labelScore= new JLabel(" Player ="+ tempScoreName + " Score = " + tempScore +" \n ");//                           !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		           
		            scoreFrame.add(labelScore);																								//burda bir defadan fazla help e basinca sapitiyor
		           
		            //frame.setLayout(new FlowLayout());
		            //System.out.println(" "+ tempScoreName + " Score = " + tempScore);
	            	
				}
	            scoreFrame.setSize(300, 500);
	            scoreFrame.setVisible(true);
				
			}
			
		  });
		 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			 

		menuLabel.setSize(500, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(menuLabel);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		frame.add(login,c);
		
		
	
		
        frame.setSize(500, 700);
        frame.setVisible(true);
       
		
	}
	
	
public void play() {
		  try {
		   File file = new File("src/images/her.wav");
		   Clip clip = AudioSystem.getClip();
		   clip.open(AudioSystem.getAudioInputStream(file));
		   clip.start();
		   //Thread.sleep(clip.getMicrosecondLength());
		  } catch (Exception e) {
		   System.err.println(e.getMessage());
		  }
		 }	
	
public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	FrontEnd Start = new FrontEnd();
            	
            }
        });
}

}//FrontEndClass




class GameScreen extends JFrame {
	int diffucultyCheck;
	int score;
    public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public GameScreen(int diffuculty) 
    {
    	this.diffucultyCheck = diffuculty;
        initUI();
        
    }
    
    private void initUI()
    {
    	
    	
        setSize(500, 700);
        setResizable(false);
  
        setTitle("GAME");
        //System.out.println(" diff is in class = " + diffucultyCheck);
        Engine newGame = new Engine(diffucultyCheck);

		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
//                System.out.println("Closed");
//                System.out.println("in game Score = " +newGame.getScore());
               score = newGame.getScore();
//               System.out.println("in class Score = " +score);
                e.getWindow().dispose();
            }
        });
        this.add(newGame);
        
        
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}




