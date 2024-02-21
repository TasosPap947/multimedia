package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		 try {
		        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("resources/mainWindow.fxml"));
		  //    Parent root = FXMLLoader.load(getClass().getResource("view/Calculatorview.fxml"));
		        AnchorPane root = (AnchorPane) loader.load();
		        Scene scene = new Scene(root);
		        primaryStage.setTitle("Medialab Hangman");
		        primaryStage.setScene(scene);
		        primaryStage.show();
		    } 
		       catch(Exception e)    {
		        e.printStackTrace();
		    }
	}
	public static void main(String[] args) {
		launch(args);
	}
}

