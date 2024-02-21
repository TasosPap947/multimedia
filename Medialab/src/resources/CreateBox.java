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

public class CreateBox {
	
	static String dictID;
	static String bookID;
	
	public static String[] display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Input");
		window.setMinWidth(250);
		Text label1 = new Text("Give dictionary ID");
		TextField text1 = new TextField();
		Text label2 = new Text("Give Book ID");
		TextField text2 = new TextField();
		Button button = new Button("Create");	
		button.setOnAction(e -> {
			dictID = text1.getText();
			bookID = text2.getText();
			window.close();
		});	
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		layout.getChildren().addAll(label1, text1, label2, text2, button);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		String[] res = new String[2];
		res[0] = dictID;
		res[1] = bookID;
		return res;
	}
	
}