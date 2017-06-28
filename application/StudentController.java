// Name: Katelyn Peterson
// Class: CIS 2572-001 Spring 2017
// Instructor: Dan Khan
// Date: Apr 26th, 2017
// Prologue:  This class controls the Java application for the Student Scores.

package application;

// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Optional;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * <h1> StudentController </h1> 
 * The StudentController class controls the Java application that uses Students and TestScore.
 * 
 * @author Katelyn Peterson
 * @version 1.0
 * @since 04-26-2017
 *
 */

public class StudentController
{
	// Private Variables
	private Main mainApp;
	private Stage stage;
	private Students studs;
	private Set<String> studList;
	private String studKey;
	private Double[] testScores;
	private Integer scoreLocation;
	private File file;
	private Students original;
	private File existDirectory;
	private TextInputDialog dialog;
	private Optional<String> result;
	private DecimalFormat format;
	//private TextFormatter<Double> scoreValid;
	
	// FXML Variables
	@FXML
    private Menu fileMenu;
    @FXML
    private MenuItem menuNewFile;
    @FXML
    private MenuItem menuOpenFile;
    @FXML
    private MenuItem menuSaveFile;
    @FXML
    private MenuItem menuSaveAsFile;
    @FXML
    private MenuItem menuCloseProgram;
    @FXML
    private Menu studentMenu;
    @FXML
    private MenuItem menuAddStudent;
    @FXML
    private MenuItem menuChangeStudent;
    @FXML
    private MenuItem menuDeleteStudent;
    @FXML
    private Menu scoreMenu;
    @FXML
    private MenuItem menuAddScore;
    @FXML
    private MenuItem menuChangeScore;
    @FXML
    private MenuItem menuDeleteScore;
    @FXML
    private Label studName;
    @FXML
    private ListView<String> studentList;
    @FXML
    private TextField showStudentName;
    @FXML
    private TableView<TestScore> showStudentScores;
    @FXML
    private TableColumn<TestScore, String> testNumber;
    @FXML
    private TableColumn<TestScore, Double> testScore;
    
    // Controller Functions
    // Function: private void addScore(ActionEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Add Score menu item.
      * @param event - This is the action when the Add Score menu item is clicked by the Left Mouse Button.
      */
    @FXML
    private void addScore(ActionEvent event)
    {
    	dialog = new TextInputDialog();
    	dialog.setTitle("Add Score");
    	dialog.setHeaderText("Enter Test Score");
    	dialog.setContentText("Enter Test Score in Numerical Format: ");
    	//dialog.getEditor().setTextFormatter(scoreValid);
    	dialog.getEditor().setTextFormatter(new TextFormatter<>(c ->
    	{
    	    if (c.getControlNewText().isEmpty())
    	    {
    	        return c;
    	    }

    	    ParsePosition parsePosition = new ParsePosition(0);
    	    Object object = format.parse(c.getControlNewText(), parsePosition);

    	    if (object == null || parsePosition.getIndex() < c.getControlNewText().length())
    	    {
    	        return null;
    	    }
    	    else
    	    {
    	        return c;
    	    }
    	}));
    	
    	result = dialog.showAndWait();
    	result.ifPresent(score -> 
    	{
    		/*while (!testDouble(score))
    		{
    			final Optional<String> validate;
    			dialog.getEditor().clear();
    			validate = dialog.showAndWait();
    			score = validate.get();
    		}*/
    		
    		//System.out.println("Testing valid text");
    		
    		studs.addScore(studKey, score);
    		
    		loadTable();
    	});
    }
    
    // Function: private void addStudent(ActionEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Add Student menu item.
      * @param event - This is the action when the Add Student menu item is clicked by the Left Mouse Button.
      */
    @FXML
    private void addStudent(ActionEvent event)
    {
    	dialog = new TextInputDialog();
    	dialog.setTitle("Add Student");
    	dialog.setHeaderText("Enter Student Name");
    	dialog.setContentText("Enter Student's Full Name: ");
    	
    	result = dialog.showAndWait();
    	result.ifPresent(name -> 
    	{
    		studs.addStudent(name);
    		studList = studs.getStudents();
    		studKey = name;
    		//System.out.println("Your name: " + studs.getStudents());
    		
    		loadStudList();
    	});
    }
    
    // Function: private void changeScore(ActionEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Change Score menu item.
      * @param event - This is the action when the Change Score menu item is clicked by the Left Mouse Button.
      */
    @FXML
    private void changeScore(ActionEvent event)
    {
    	ScoreChanger(scoreLocation);
    }
    
    // Function: private void changeStudent(ActionEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Change Student menu item.
      * @param event - This is the action when the Change Student menu item is clicked by the Left Mouse Button.
      */
    @FXML
    private void changeStudent(ActionEvent event)
    {
    	StudentChanger();
    }
    
    // Function: private void closeProgram(ActionEvent event) throws FileNotFoundException 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Close menu item.
      * @param event - This is the action when the Close menu item is clicked by the Left Mouse Button.
      */
    @FXML
    private void closeProgram(ActionEvent event) throws FileNotFoundException
    {
    	boolean userChoice;
    	userChoice = checkFileSave();
    	//userChoice = true;
    	
		if (userChoice)
		{
			stage.close();
		}
    }
    
    // Function: private void deleteScore(ActionEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Delete Score menu item.
      * @param event - This is the action when the Delete Score menu item is clicked by the Left Mouse Button.
      */
    @FXML
    private void deleteScore(ActionEvent event)
    {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Delete Score?");
    	alert.setHeaderText("Are You Sure You Want To Delete This Test Score?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK)
    	{
    	    // User chose OK
        	studs.removeScore(studKey, scoreLocation);
        	
        	// Disables
        	showStudentName.requestFocus();
        	menuChangeScore.setDisable(true);
        	menuDeleteScore.setDisable(true);
        	
        	loadTable();
    	}
    	else
    	{
    	    // User chose CANCEL or closed the dialog
    	}
    }
    
    // Function: private void deleteStudent(ActionEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Delete Student menu item.
      * @param event - This is the action when the Delete Student menu item is clicked by the Left Mouse Button.
      */
    @FXML
    private void deleteStudent(ActionEvent event)
    {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Delete Student?");
    	alert.setHeaderText("Are You Sure You Want To Delete This Student?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK)
    	{
    		// User chose OK
        	studs.removeStudent(studKey);
        	studList = studs.getStudents();
        	studKey = null;
        	
        	// Disables
        	studentList.requestFocus();
        	menuChangeStudent.setDisable(true);
        	menuDeleteStudent.setDisable(true);
        	
        	if (!menuAddScore.isDisable())
        	{
	        	menuAddScore.setDisable(true);
        		menuChangeScore.setDisable(true);
	        	menuDeleteScore.setDisable(true);
        	}
        	
        	clearStudData();
        	loadStudList();
    	}
    	else
    	{
    	    // User chose CANCEL or closed the dialog
    	}
    }
    
    // Function: private void handleScore(MouseEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Test Score TableView.
      * @param event - This is the action when the Left Mouse Button is clicked on a 
      * Test and Score in the Test Score TableView.
      */
    @FXML
    private void handleScore(MouseEvent event)
    {
    	String transferIndex;
    	
    	if (showStudentScores.getSelectionModel().getSelectedItem() != null)
    	{
        	// Enables
        	menuChangeScore.setDisable(false);
        	menuDeleteScore.setDisable(false);
    		
    		// Pulls out the index number of the selected Score
	    	transferIndex = showStudentScores.getSelectionModel().getSelectedItem().getTestNum();
	    	transferIndex = transferIndex.substring(transferIndex.length() - 1);
	    	//System.out.println("transferIndex is: " + transferIndex);
	    	
	    	scoreLocation = (Integer.parseInt(transferIndex) - 1);
	    	//System.out.println("scoreLocation is: " + scoreLocation);
	    	
	    	// Look for doubleClick
	    	if (event.getClickCount() > 1)
	    	{
	    		ScoreChanger(scoreLocation);
	    	}
    	}
    	else
    	{
    		//System.out.println("Testing " + showStudentScores.getSelectionModel().getSelectedItem());
    		
        	// Enables
        	menuChangeScore.setDisable(true);
        	menuDeleteScore.setDisable(true);
    	}
    }
    
    // Function: private void loadStudentData(MouseEvent event) 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Student List View.
      * @param event - This is the action when the Left Mouse Button is clicked on a 
      * Student in the Student List View.
      */
    @FXML
    private void loadStudentData(MouseEvent event)
    {    	
    	studKey = studentList.getSelectionModel().getSelectedItem();
    	
    	// Enables
    	menuChangeStudent.setDisable(false);
    	menuDeleteStudent.setDisable(false);
    	
    	// Disables
    	if (!menuChangeScore.isDisable())
    	{
    		menuChangeScore.setDisable(true);
    		menuDeleteScore.setDisable(true);
    	}
    	
		//Load the student
		loadTable();
		
    	// Look for doubleClick
    	if (event.getClickCount() > 1)
    	{
    		StudentChanger();
    	}
    }
    
    // Function: private void newFile(ActionEvent event) throws FileNotFoundException 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the New menu item.
      * @param event - This is the action when the New menu item is clicked by the Left Mouse Button. 
      * @throws FileNotFoundException
      */
    @FXML
    private void newFile(ActionEvent event) throws FileNotFoundException
    {
    	Boolean userChoice = checkFileSave();
    	if (userChoice)
    	{
    		file = null;
	    	
	    	studs = new Students();
	    	studList = studs.getStudents();
	    	original = new Students();
	    	
	    	mainApp.updateStageTitle(file);
	    	
	    	clearStudData();
	    	loadStudList();
    	}
    }
    
    // Function: private void openFile(ActionEvent event) throws FileNotFoundException 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Open menu item.
      * @param event - This is the action when the Open menu item is clicked by the Left Mouse Button. 
      * @throws FileNotFoundException
      */
    @FXML
    private void openFile(ActionEvent event) throws FileNotFoundException
    {
    	Boolean userChoice = checkFileSave();
    	File currFile = null;
    	
    	if (userChoice)
    	{
	    	FileChooser fileChooser = new FileChooser();
    		if (existDirectory != null)
        	{
        		fileChooser.setInitialDirectory(existDirectory);
        	}
        	else
        	{
        		existDirectory = new File(System.getProperty("user.home"));
    	    	if (!existDirectory.exists())
    	    	{
    	    		existDirectory.mkdirs();
    	    	}
    	    	fileChooser.setInitialDirectory(existDirectory);
        	}
	    	
	    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
	    	fileChooser.getExtensionFilters().add(extFilter);
	    	fileChooser.setInitialDirectory(existDirectory);
	    	
	    	try
	    	{
	    		if (file != null)
		    	{
	    			currFile = file;
		    	}
	    		
	    		file = fileChooser.showOpenDialog(null);
		    	
		    	if (file != null)
		    	{
		    		studs = new Students(file);
		    		studKey = null;
		    		studList = studs.getStudents();
		    		original = new Students(file);
		    		
		    		existDirectory = file.getParentFile();
		    		mainApp.updateStageTitle(file);
		    		
		    		clearStudData();
		    		loadStudList();	    		
		    		
		    		//System.out.println("Testing FileChoser");
		    	}
	    	}
			catch (NumberFormatException e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("File Error");
				alert.setHeaderText("Student Scores cannot open " + file.getName());
				
				alert.showAndWait();
				
				if (currFile != null)
				{
					file = currFile;
				}
				else
				{
					file = null;
				}
			}
    	}
    }
    
    // Function: private void saveAsFile(ActionEvent event) throws FileNotFoundException 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Save As menu item.
      * @param event - This is the action when the Save As menu item is clicked by the Left Mouse Button. 
      * @throws FileNotFoundException
      */
    @FXML
    private void saveAsFile(ActionEvent event) throws FileNotFoundException
    {
    	FileChooser fileChooser = new FileChooser();
    	
    	if (existDirectory != null)
    	{
    		fileChooser.setInitialDirectory(existDirectory);
    	}
    	else
    	{
    		existDirectory = new File(System.getProperty("user.home"));
	    	if (!existDirectory.exists())
	    	{
	    		existDirectory.mkdirs();
	    	}
	    	fileChooser.setInitialDirectory(existDirectory);
    	}
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    	fileChooser.getExtensionFilters().add(extFilter);
    	file = fileChooser.showSaveDialog(null);
    	    	
    	if (file != null)
    	{
    		studs.saveMap(file);
    		original = new Students(file);
    		
    		mainApp.updateStageTitle(file);
    	}
    }
    
    // Function: private void saveFile(ActionEvent event) throws FileNotFoundException 
    // Parameters:  event - Left Mouse button
    // Returns:		 zero
 	
    /**
      * This function controls the Save menu item.
      * @param event - This is the action when the Save menu item is clicked by the Left Mouse Button. 
      * @throws FileNotFoundException
      */
    @FXML
    private void saveFile(ActionEvent event) throws FileNotFoundException
    {
    	if (file != null)
    	{
    		//System.out.println(studs.equals(original));    		
    		if (!studs.equals(original))
    		{
	    		studs.saveMap(file);
	    		original = new Students(file);
    		}
    	}
    	else
    	{
    		saveAsFile(event);
    	}
    }
    
	// Initialization
	// Function: public void initialize()
	// Parameters:  none
	// Returns:		 zero
	
	/**
	 * This function initializes the initial Students and Student List for the application.
	 * @throws FileNotFoundException 
	 */
    @FXML
	private void initialize() throws FileNotFoundException
	{
		//File testFile = new File("TestStudents.csv");
		//studs = new Students(testFile);
    	
    	file = null;
    	
    	studs = new Students();
    	studList = studs.getStudents();
    	original = new Students();
    	
    	// Set Up TextFormat
    	format = new DecimalFormat( "#.0" );
    	
    	// DOES NOT WORK, MUST MAKE A NEW TEXTFORMATTER EVERY TIME
    	/*scoreValid = new TextFormatter<>(c ->
    	{
    	    if (c.getControlNewText().isEmpty())
    	    {
    	        return c;
    	    }

    	    ParsePosition parsePosition = new ParsePosition(0);
    	    Object object = format.parse(c.getControlNewText(), parsePosition);

    	    if (object == null || parsePosition.getIndex() < c.getControlNewText().length())
    	    {
    	        return null;
    	    }
    	    else
    	    {
    	        return c;
    	    }
    	});*/
		
		//System.out.println("Testing Map");
	}
    
    // Other Functions
	// Function: private void clearStudData()
	// Parameters:  none
	// Returns:		 zero
	
	/**
	 * This function clears the Student Name and Test Scores TableView. 
	 */
    private void clearStudData()
    {
    	showStudentName.setText("");
    	showStudentScores.getItems().clear();
    }
    
	// Function: private void loadStudList()
	// Parameters:  none
	// Returns:		 zero
	
	/**
	 * This function reloads the student list that controls the Student ListView. 
	 */
    private void loadStudList()
    {
    	studentList.getItems().removeAll(studentList.getItems());
    	
    	if (studList != null)
    	{
	    	//System.out.println("Testing");
    		studentList.setDisable(false);
    		
    		for (String x : studList)
	    	{
	    		studentList.getItems().add(x);
	    	}
	    	
	    	// Ensure Student Data and Scores Remain Disabled if
	    	// this is a fresh load of data or a student has been deleted
	    	if (studKey == null)
	    	{
		    	// Window Disable
	    		studName.setDisable(true);
		    	showStudentName.setDisable(true);
		    	showStudentScores.setDisable(true);
		    	
		    	// Student Menu Disable
		    	menuChangeStudent.setDisable(true);
		    	menuDeleteStudent.setDisable(true);
		    	
		    	// Score Menu Disable
		    	menuAddScore.setDisable(true);
		    	menuChangeScore.setDisable(true);
		    	menuDeleteScore.setDisable(true);
	    	}
	    	else
	    	{
	    		loadTable();
	    	}
    	}
    	else
    	{
    		//System.out.println("Testing");
    		studName.setDisable(true);
    		showStudentName.setDisable(true);
    		studentList.setDisable(true);
    	}
    }
    
	// Function: private void loadTable()
	// Parameters:  none
	// Returns:		 zero
	
	/**
	 * This function reloads the array of test scores and recreates the Student Score TableView. 
	 */
    private void loadTable()
    {
    	if (studKey != null)
    	{
	    	clearStudData();
	    	
			testScores = new Double[studs.getScores(studKey).size()];
			studs.getScores(studKey).toArray(testScores);
			
			// Enables 
			menuAddScore.setDisable(false);
			studName.setDisable(false);
			showStudentName.setDisable(false);
			showStudentScores.setDisable(true);
			menuChangeStudent.setDisable(false);
			menuDeleteStudent.setDisable(false);
	    	
	    	showStudentName.setText(studKey);
	    	
	    	if (testScores.length > 0)
			{
		    	// Additional Enable
	    		showStudentScores.setDisable(false);
		    	
				ObservableList<TestScore> list = FXCollections.observableArrayList();
						
		    	for (int x = 0; x < testScores.length; x++)
				{
					// Add data to list
		    		list.add(new TestScore("Test " + (x + 1), testScores[x]));
				}
				
				// Set Cell Factory
		    	//System.out.println("Testing");
		    	testNumber.setCellValueFactory(cellData -> cellData.getValue().getTestNumProperty());
		    	testScore.setCellValueFactory(cellData -> cellData.getValue().getScoreProperty().asObject());
		    	
		    	// add list to Table
		    	showStudentScores.setItems(list);
			}
    	}
    	else
    	{
    		//System.out.println("Testing " + studKey);
    		
    		clearStudData();
    		
    		// Disable
			menuAddScore.setDisable(true);
			studName.setDisable(true);
			showStudentName.setDisable(true);
			showStudentScores.setDisable(true);
			menuChangeStudent.setDisable(true);
			menuDeleteStudent.setDisable(true);
    	}
    }
    
    /*private boolean testDouble(String score)
    {
		try
    	{
			Double.parseDouble(score);
			return true;
		}
    	catch (NumberFormatException e)
    	{
			//e.printStackTrace();
    		System.out.println("Testing invalid");
    		return false;
		}
    }*/
    
	// Function: private void StudentChanger()
	// Parameters:  none
	// Returns:		 zero
	
	/**
	 * This function runs the dialog for a student's name change.
	 */
    private void StudentChanger()
    {
    	dialog = new TextInputDialog(studKey);
    	//dialog = new TextInputDialog();
    	dialog.setTitle("Change Student Name");
    	dialog.setHeaderText("Enter Student Name");
    	dialog.setContentText("Enter Student's Full Name: ");
    	
    	result = dialog.showAndWait();
    	
		//System.out.println("Student Key: " + studKey);
		//System.out.println("Result: " + result.get());
    	result.ifPresent(name -> 
    	{
    		if (!name.equals(studKey))
    		{
	    		//System.out.println(name);
	    		//studs.addStudent(name);
	    		studs.changeName(name, studKey);
	    		studList = studs.getStudents();
	    		studKey = name;
	    		
	    		loadStudList();
	    		loadTable();
    		}
    	});
    }
    
	// Function: private void ScoreChanger(Integer index)
	// Parameters:  index - internal location of a test score
	// Returns:		 zero
	
	/**
	 * This function runs the dialog for a student's test score change.
	 * @param index - This is the internal location of the test score being changed.
	 */
    private void ScoreChanger(Integer index)
    {
    	dialog = new TextInputDialog(studs.getScore(studKey, index).toString());
    	dialog.setTitle("Change Score");
    	dialog.setHeaderText("Enter New Test Score");
    	dialog.setContentText("Enter New Test Score in Numerical Format: ");
    	dialog.getEditor().setTextFormatter(new TextFormatter<>(c ->
    	{
    	    if (c.getControlNewText().isEmpty())
    	    {
    	        return c;
    	    }

    	    ParsePosition parsePosition = new ParsePosition(0);
    	    Object object = format.parse(c.getControlNewText(), parsePosition);

    	    if (object == null || parsePosition.getIndex() < c.getControlNewText().length())
    	    {
    	        return null;
    	    }
    	    else
    	    {
    	        return c;
    	    }
    	}));
    	
    	result = dialog.showAndWait();
    	
    	result.ifPresent(score -> 
    	{
        	if (Double.parseDouble(result.get()) != studs.getScore(studKey, index))
        	{
	    		studs.changeScore(studKey, index, score);
	    		loadTable();
	    		//System.out.println("Testing");
        	}
    	});
    	//System.out.println("Testing");
    }
    
	// Function: public boolean checkFileSave() throws FileNotFoundException
	// Parameters:  none
	// Returns:		 zero
	
	/**
	 * This function serves as a safety in case a user accidently closes or tries to open a new file 
	 * without saving the current file.
	 * @throws FileNotFoundException 
	 */
    public boolean checkFileSave() throws FileNotFoundException
    {
    	// Set up so studs is compared to original before program can close
    	/*System.out.println("Testing");
    	System.out.println("Testing: " + studs.equals(original));
    	System.out.println("Testing");*/
    	if (!studs.equals(original) && studList != null)
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Student Scores");
    		if (file != null)
    		{
    			alert.setHeaderText("Do you want to save the changes to " + file.getName() + "?");
    		}
    		else
    		{
    			alert.setHeaderText("Do you want to save the current file?");
    		}
    		
    		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
    		ButtonType no = new ButtonType("No", ButtonData.NO);
    		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    		
    		alert.getButtonTypes().setAll(yes, no, cancel);

    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == yes)
    		{
    		    // User chose Yes
    			//System.out.println("User Chose Yes!");
    			saveFile(new ActionEvent());
    			if (file == null)
    			{
        			//System.out.println("User Chose Cancel!");
    				return false;
    			}    			
    			return true;
    		}
    		else if (result.get() == no)
    		{
    			// User chose No
    			//System.out.println("User Chose No!");
    			return true;
    		}
    		else
    		{
    		    // User chose CANCEL or closed the dialog
    			//System.out.println("User Chose Cancel!");
    			return false;
    		}
    	}
    	return true;
    }
    
	// Function: public void setMainApp(Main mainApp)
	// Parameters:  mainApp - link between controller and main
	// Returns:		 zero
	
	/**
	 * This function connects main to the controller.
	 * @param mainApp - This is the controller's access to main.
	 */
    public void setMainApp(Main mainApp)
    {
        this.mainApp = mainApp;
    }
    
	// Function: public void setStage(Stage primaryStage)
	// Parameters:  primaryStage - link between controller and main's Stage
	// Returns:		 zero
	
	/**
	 * This function connects main's Stage to the controller.
	 * @param primaryStage - This is the controller's access to main's Stage.
	 */
    public void setStage(Stage primaryStage)
    {
    	this.stage = primaryStage;
    }
}
