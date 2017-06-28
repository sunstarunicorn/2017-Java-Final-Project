// Name: Katelyn Peterson
// Class: CIS 2572-001 Spring 2017
// Instructor: Dan Khan
// Date: Apr 24th, 2017
// Prologue:  This is the Students Class

package application;

//Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * <h1> Students </h1>
 * The Students class includes a TreeMap with a String key and 
 * an ArrayList of Doubles, a Scanner, and a Set of Strings.
 *
 * @author Katelyn Peterson
 * @version 1.0
 * @since 04-24-2017
 *
 */
public class Students
{
	// Private Variables
	private TreeMap<String, ArrayList<Double>> studentData;
	private Scanner input;
	private Set<String> mapChecker;
	
	// Constructors
	// Default
	// Function: public Students()
	// Parameters:  none - default
	// Returns:		 Constructor
	
	/**
	  * This is the default constructor for the Students class.
	  */
	public Students()
	{
		studentData = new TreeMap<>();
		mapChecker = studentData.keySet();
	}
	
	// All Information
	// Function: public Students(File fileName) throws FileNotFoundException
	// Parameters:  fileName - the name of the class file
	// Returns:		 Constructor
	
	/**
	  * This is the full constructor for the Students class.
	  *  @param fileName  This is the name of the class File.
	  * @throws FileNotFoundException
	  */
	public Students(File fileName) throws FileNotFoundException
	{
		studentData = new TreeMap<>();
		initializeMap(fileName);
	}
	
	// Getters
	// Function: public Set<String> getStudents()
	// Parameters:  none
	// Returns:		 Set
	
	/**
	 * Returns a Set containing the Students names.
	 * @return Set - This is the 'list' of student names. 
	 */
	public Set<String> getStudents()
	{
		return mapChecker;
	}
	
	// Function: public ArrayList<Double> getScores(String key)
	// Parameters:  key - the student with test scores 
	// Returns:		 ArrayList
	
	/**
	 * Returns a ArrayList containing a Student's scores.
	 * @param key  This is the student with test scores.
	 * @return ArrayList - This is the 'list' of a student's scores. 
	 */
	public ArrayList<Double> getScores(String key)
	{
		return studentData.get(key);
	}
	
	// Function: public Double getScore(String key, int index)
	// Parameters:  none
	// Returns:		 Double
	
	/**
	 * Returns a Double containing a specific student's specific test score
	 * @return Double - This is one of a student's test scores. 
	 */
	public Double getScore(String key, int index)
	{
		return studentData.get(key).get(index);
	}
	
	// Setters
	// Function: public void addStudent(String name)
	// Parameters:  name - the new student
	// Returns:		 zero
	
	/**
	 * This function adds a new student. 
	 * @param name  This is the new student.
	 */
	public void addStudent(String name)
	{
		studentData.put(name, new ArrayList<Double>());
		mapChecker = studentData.keySet();
	}
	
	// Function: public void addScore(String key, String newVal)
	// Parameters:  key - the student with a new test score
	//						 newVal - the new score
	// Returns:		 zero
	
	/**
	 * This function adds a new test score to a specific student. 
	 * @param key  This is the student with the new test score.
	 * @param newVal  This is the new test score
	 */
	public void addScore(String key, String newVal)
	{
		studentData.get(key).add(Double.parseDouble(newVal));
	}
	
	// Function: public void initializeMap(File fileName) throws FileNotFoundException
	// Parameters:  newName - the corrected name of a student
	//						 oldName - the current name of a student
	// Returns:		 zero
	
	/**
	 * This function allows the name of a student in the file data to be changed. 
	 * @param newName  This is the updated name of a student.
	 * @param oldName  This is the currently stored name of a student.
	 */
	public void changeName(String newName, String oldName)
	{
		studentData.put(newName, studentData.get(oldName));
		studentData.remove(oldName);
		mapChecker = studentData.keySet();
	}
	
	// Function: public void changeScore(String key, int index, String newVal)
	// Parameters:  key - the student whose score must be changed
	//						 index - location of the score to be changed
	//						 newVal - the new score
	// Returns:		 zero
	
	/**
	 * This function allows a student's specific score to be changed.
	 * @param key  This is the student with the changing test score.
	 * @param index  This is the specific location of the test score to be changed.
	 * @param newVal  This is the new test score
	 */
	public void changeScore(String key, int index, String newVal)
	{
		studentData.get(key).set(index, Double.parseDouble(newVal));
	}
	
	// Parameters:  key - the student whose score must be changed
	//						 index - location of the score to be changed
	//						 newVal - the new score
	// Returns:		 zero
	
	/**
	 * This function allows a student to be removed. 
	 * @param key  This is the student to be removed.
	 */
	public void removeStudent(String key)
	{
		studentData.remove(key);
		mapChecker = studentData.keySet();
	}
	
	// Parameters:  key - the student whose score must be changed
	//						 index - location of the score to be changed
	//						 newVal - the new score
	// Returns:		 zero
	
	/**
	 * This function allows a student's specific score to be removed.
	 * @param key  This is the student with the test score to be removed.
	 * @param index  This is the specific location of the test score to be removed.
	 */
	public void removeScore(String key, int index)
	{
		studentData.get(key).remove(index);
	}
	
	// Other Functions
	// Function: public void initializeMap(File fileName) throws FileNotFoundException
	// Parameters:  fileName - the name of the class file
	// Returns:		 zero
	
	/**
	 * Sets up the Map from the class File.
	 * @param fileName  This is the name of the class File.
	 * @throws FileNotFoundException
	 */
	public void initializeMap(File fileName) throws FileNotFoundException
	{
		if (fileName.exists())
		{
			input = new Scanner(fileName);
			
			// Initialize Map
			while (input.hasNext())
			{
				String line = input.nextLine();
				String [] strArr = line.split(",");
				
				studentData.put(strArr[0], new ArrayList<Double>());
				
				// Add Scores
				if (strArr.length > 1)
				{
					for (int x = 1; x < strArr.length; x++)
					{
						studentData.get(strArr[0]).add(Double.parseDouble(strArr[x]));
					}
				}
			}
			
			mapChecker = studentData.keySet();
		}
		else
		{
			return;
		}
	}
	
	// Function: public void saveMap(File fileName) throws FileNotFoundException
	// Parameters:  fileName - the name of the class file
	// Returns:		 zero
	
	/**
	 * Saves the map to a File.
	 * @param fileName  This is the name of the class File.
	 * @throws FileNotFoundException
	 */
	public void saveMap(File fileName) throws FileNotFoundException
	{
		PrintWriter write = new PrintWriter(fileName);
		Iterator<String> mapWriter = mapChecker.iterator();
		String key;
		
		while (mapWriter.hasNext())
		{
			//System.out.println(mapWriter.next());
			key = mapWriter.next();
			
			write.print(key);
			//System.out.print(key);
			
			if (!studentData.get(key).isEmpty() && mapWriter.hasNext())
			{
				write.print(",");
				//System.out.print(",");
				for (int x = 0; x < studentData.get(key).size() - 1; x++)
				{
					write.print(studentData.get(key).get(x) + ",");
					//System.out.print(studentData.get(key).get(x) + ",");
				}
				//System.out.println((studentData.get(key).get(studentData.get(key).size() - 1)));
				//System.out.println();
				write.println((studentData.get(key).get(studentData.get(key).size() - 1)));
			}
			else if (studentData.get(key).isEmpty() && mapWriter.hasNext())
			{
				write.println();
				//System.out.println();
			}
			else if (!studentData.get(key).isEmpty())
			{
				write.print(",");
				//System.out.print(",");
				for (int x = 0; x < studentData.get(key).size() - 1; x++)
				{
					write.print(studentData.get(key).get(x) + ",");
					//System.out.print(studentData.get(key).get(x) + ",");
				}
				//System.out.print((studentData.get(key).get(studentData.get(key).size() - 1)));
				//System.out.println();
				write.print((studentData.get(key).get(studentData.get(key).size() - 1)));
			}
			else
			{
				write.print("");
				//System.out.print("");
			}
		}
		
		write.close();
	}
	
	// equals()
	// Function: public boolean equals(Object x)
	// Parameters:  x - the object being compared to the calling Students
	// Returns:		 true or false
	
	 /**
	   * This is the override of Object's equals() function.
	   * @param x  This is the Object being compared to the calling Students.
	   * @return true - This tells the calling Students that the Object is equal to it.
	   * @return false - This tells the calling Students that the Object is not equal to it.
	   */
	//@Override
	public boolean equals(Object x)
	{
		//Integer scoreNum = 0;
		//Integer valid = 0;
		
		if (this.mapChecker != null)
		{
			if (this.mapChecker.equals(((Students)x).mapChecker))
			{
				for (String y : mapChecker)
				{
					//scoreNum = scoreNum + 1;
					
					if (!studentData.get(y).equals(((Students)x).studentData.get(y)))
					{
						//valid = valid + 1;
						//return true;
						return false;
					}
				}
				
				return true;
				
				/*if (scoreNum == valid)
				{
					return true;
				}*/
			}
		}
		return false;
	}
}