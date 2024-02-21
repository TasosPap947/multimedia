package resources;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Dictionary;

public class DictionaryBox {
	
	public static void display(Dictionary dict) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Dictionary");
		window.setMinWidth(300);
		window.setMinHeight(100);
		
		Label label1 = new Label();
		Label label2 = new Label();
		Label label3 = new Label();
		Label label4 = new Label();
		label1.setText("Dictionary ID: "+dict.ID);
		label2.setText("Words of letters length   (6): "+String.format("%.2f", 100 * dict.shortPrc)+" %");
		label3.setText("Words of letters length (7-9): "+String.format("%.2f", 100 * dict.midPrc)+" %");
		label4.setText("Words of letters length (10+): "+String.format("%.2f", 100 * dict.longPrc)+" %");
		
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		layout.setPadding(new Insets(20));
		layout.getChildren().addAll(label1, label2, label3, label4, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
