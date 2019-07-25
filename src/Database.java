import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Database 

{

	private static HashMap<String, String> userDatabase = new HashMap<String, String>();
	
	private static HashMap<String, Integer> scoreDatabase = new HashMap<String, Integer>();
	
	

	Database()
	{
		getUserDatabase().clear();
		getScoreDatabase().clear();
		loadFromFile();
	}
	
	private void loadFromFile() 
	
	{
		BufferedReader bReader = null;
		FileReader fr = null;
		int lineCount = 0;
		String tempName ="";
		String tempPassword="";
		
///////////////////////Read From User Database File ///////////////////////////////////			
		try 
		{
			
			fr = new FileReader("src/database.txt");
			bReader = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = bReader.readLine()) != null) 
			{
				lineCount++;
				if(lineCount%2 != 0)
				{
					tempName = sCurrentLine;
					
				}
				else if (lineCount%2 == 0)
				{
					tempPassword = sCurrentLine;
					addNewUser(tempPassword,tempName);
					
				}
				//System.out.println(sCurrentLine + " " + lineCount );
			}		
		}
		catch (IOException e) 
		{
			System.out.println( "Error when reading from database file ");	                
		}
		finally 
		{

			try
			{
			if (bReader != null)
				bReader.close();
			if (fr != null)
				fr.close();
			} 
			catch (Exception ex) 
			{ex.printStackTrace();}

		}

///////////////////////Read From User Score File ///////////////////////////////////			
		BufferedReader bReader1 = null;
		FileReader fr1 = null;
		lineCount = 0;
		String tempScoreName ="";
		int tempScore = 0;
		
		try 
		{
			
			fr1 = new FileReader("src/score.txt");
			bReader1 = new BufferedReader(fr1);

			String sCurrentLine;

			while ((sCurrentLine = bReader1.readLine()) != null) 
			{
				lineCount++;
				if(lineCount%2 == 0)
				{
					tempScore = Integer.valueOf(sCurrentLine);
					addNewScore(tempScoreName,tempScore);
//					System.out.println(sCurrentLine + "line = " + lineCount);
//					System.out.println("temp score = " +tempScore);
				}
				else if (lineCount%2 != 0)
				{
					tempScoreName = sCurrentLine;
					
					
				}
				
			}
		}
		catch (IOException e) 
		{
			System.out.println( "Error when reading from score file ");	                
		}
		finally 
		{

			try
			{
			if (bReader1 != null)
				bReader1.close();
			if (fr1 != null)
				fr1.close();
			} 
			catch (Exception ex) 
			{ex.printStackTrace();}

		}
			
	}//loadFunc

////////////////////////////Add To Hash Map/////////////////////////////////////////////////////////	
	public void addNewUser(String passwordHolder,String nameHolder)
	{
		getUserDatabase().put(passwordHolder,nameHolder);
		
	}
	
	public void addNewScore( String nameHolder ,int scoreHolder)  //might be Integer instead int
	{
		getScoreDatabase().put(nameHolder,scoreHolder);
		
	}


	
	
	public void saveToFile()
	{
		
		String fileName = "src/database.txt";
		String fileName2 = "src/score.txt";
		String tempName ="";
		String tempPassword="";
		Iterator it = userDatabase.entrySet().iterator();
///////////////////////Write To User Database File ///////////////////////////////////		
		try {

            FileWriter fWriter = new FileWriter(fileName);

            // Wrap FileWriter in BufferedWriter.
            BufferedWriter bWriter =new BufferedWriter(fWriter);
         
            Map.Entry pair;
            while (it.hasNext()) 
			{
	            pair = (Map.Entry)it.next();
	            tempPassword = pair.getKey().toString();
	            tempName = pair.getValue().toString();
	            bWriter.write(tempName);
	            bWriter.newLine();
	            bWriter.write(tempPassword);
	            bWriter.newLine();
			}
            bWriter.close();
            fWriter = null;
            bWriter= null;
        }
        catch(IOException ex) {
            System.out.println("Error writing to file");

        }
///////////////////////Write To ScoreBoard File ///////////////////////				
		
		String tempScoreName ="";
		String tempScore ="";
		Iterator scoreIterator = scoreDatabase.entrySet().iterator();
		try {

            FileWriter fWriter = new FileWriter(fileName2);

            // Wrap FileWriter in BufferedWriter.
            BufferedWriter bWriter =new BufferedWriter(fWriter);
         
            Map.Entry pair;
            while (scoreIterator.hasNext()) 
			{
	            pair = (Map.Entry)scoreIterator.next();
	            tempScoreName = pair.getKey().toString();
	            tempScore = pair.getValue().toString();
	           
	            bWriter.write(tempScoreName);
	            bWriter.newLine();
	            bWriter.write(tempScore);
	            bWriter.newLine();
			}
            bWriter.close();
            fWriter = null;
            bWriter= null;
        }
        catch(IOException ex) {
            System.out.println("Error writing to file");

        }
		
		
	}
	
	
////////////////////////// Getters & Setters /////////////////////////////	
	public HashMap<String, String> getUserDatabase() 
	{
		return userDatabase;
	}

	public static void setUserDatabase(HashMap<String, String> userDatabase) 
	{
		Database.userDatabase = userDatabase;
	}
	
	
	public  HashMap<String, Integer> getScoreDatabase() 
	{
		return scoreDatabase;
	}

	public static void setScoreDatabase(HashMap<String, Integer> scoreDatabase) 
	{
		Database.scoreDatabase = scoreDatabase;
	}
		
//////////////////////////////////Sort the Score Hash/////////////////////////////////////////
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}
	
}

