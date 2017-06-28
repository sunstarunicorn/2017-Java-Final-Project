// Name: Katelyn Peterson
// Class: CIS 2572-001 Spring 2017
// Instructor: Dan Khan
// Date: Apr 13th, 2017
// Prologue:  This class starts the Java application for the Student Data Program.

package application;

// Imports
import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application
{
	// Private Variables
	private Stage primaryStage;
	
	// Start Override
	// Function: public void start(Stage primaryStage) throws IOException
	// Parameters:  primaryStage - window for the application
	// Returns:		 zero
	
	/**
	  * This is the start method which loads StudentLayout.fxml and Menu.fxml and sets up the application window.
	  * @param primaryStage  This is the window for the application.
	  */
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
			
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("StudentView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			//AnchorPane window = (AnchorPane)FXMLLoader.load(getClass().getResource("StudentLayout.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("StudentCSS.css").toExternalForm());
			
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Student Scores - New");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			
			StudentController controller = loader.getController();
			controller.setMainApp(this);
			controller.setStage(primaryStage);
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
			{
				// Function: public void handle(WindowEvent we)
				// Parameters:  we - the event that can stop the window close
				// Returns:		 zero
				
				/**
				  * This is the function that controls the window's close button.
				  * @param we  This is the window event that can stop the program close.
				  */
		        @Override  
				public void handle(WindowEvent we)
		          {
						boolean userChoice;
						try
						{
							userChoice = controller.checkFileSave();
							//userChoice = true;
							//System.out.println("Testing");
							
							if (!userChoice)
							{
								we.consume();
							}
						}
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
		          }
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Function: public static void main (String[] args)
	// Parameters:  none - main
	// Returns:		 zero - main
	
	/**
	  * This is the main method which launches the application.
	  * @param args Unused.
	  */
	public static void main(String[] args)
	{
		launch(args);
	}
	
	// Function: public void updateStageTitle(File file)
	// Parameters:  file - file being used by the application
	// Returns:		 zero
	
	/**
	  * This is the function that updates the window title.
	  * @param file  This is the file currently open in the application.
	  */
	public void updateStageTitle(File file)
	{
		if (file != null)
		{
			primaryStage.setTitle("Student Scores - " + file.getName());
		}
		else
		{
			primaryStage.setTitle("Student Scores - New");
		}
	}
}