package resources;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoadBox {
	
	static String ID;
	
	public static String display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Input");
		window.setMinWidth(250);
		Text label = new Text("Give dictionary ID");
		TextField text = new TextField();	
		Button button = new Button("Load");	
		button.setOnAction(e -> {
			ID = text.getText();
			window.close();
		});	
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		layout.getChildren().addAll(label, text, button);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return ID;
	}
	
}
