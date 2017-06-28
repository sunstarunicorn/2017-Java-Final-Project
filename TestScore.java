// Name: Katelyn Peterson
// Class: CIS 2572-001 Spring 2017
// Instructor: Dan Khan
// Date: Apr 27th, 2017
// Prologue:  This is the TestScore Class

package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <h1> TestScore </h1>
 * The TestScore class includes a String and a Double.
 *
 * @author Katelyn Peterson
 * @version 1.0
 * @since 04-27-2017
 *
 */

public class TestScore
{
	// Private Variables
	private final SimpleStringProperty testNum;
	private final SimpleDoubleProperty score;
	
	// All Information
	// Function: public TestScore(String testNum, Double score)
	// Parameters:  testNum - the number of the test
	//						 score - test score
	// Returns:		 Constructor
	
	/**
	  * This is the full constructor for the TestScore class.
	  *  @param testNum  This is the number of the test.
	  *  @param score  This is the test score.
	  */
	public TestScore(String testNum, Double score)
	{
		this.testNum = new SimpleStringProperty(testNum);
		this.score = new SimpleDoubleProperty(score);
	}
	
	// Getters
	// Function: public String getTestNum()
	// Parameters:  none
	// Returns:		 String
	
	/**
	 * Returns a String containing the test number.
	 * @return String - This is the test number. 
	 */
	public String getTestNum()
	{
		return testNum.get();
	}
	
	// Function: public Double getScore()
	// Parameters:  none
	// Returns:		 Double
	
	/**
	 * Returns a Double containing the test score.
	 * @return Double - This is the test score. 
	 */
	public Double getScore()
	{
		return score.get();
	}
	
	// Properties
	// Function: public StringProperty getTestNumProperty()
	// Parameters:  none
	// Returns:		 testNum - test number
	
	/**
	  * This function retrieves the Property for the TestScore's test number.
	  * @return testNum - This is the test number.
	  */
	public StringProperty getTestNumProperty()
	{
		return testNum;
	}
	
	// Function: public DoubleProperty getScoreProperty()
	// Parameters:  none
	// Returns:		 score - test score
	
	/**
	  * This function retrieves the Property for the TestScore's test score.
	  * @return score - This is the test score.
	  */
	public DoubleProperty getScoreProperty()
	{
		return score;
	}
}