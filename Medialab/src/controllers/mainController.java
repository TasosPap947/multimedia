package controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import resources.AlertBox;
import resources.CreateBox;
import resources.DictionaryBox;
import resources.LoadBox;

import java.io.FileNotFoundException;
import java.util.List;

import application.*;

public class mainController {
	
	private Game game;
	private Dictionary dict;
	private History record;
	private Integer positionChoice;
	private Character letterChoice;

	@FXML
    private Label rightguess;
	
	@FXML
    private Label lives;

    @FXML
    private Label totalwords;
    
    @FXML
    private Label positionLabel;
    
    @FXML
    private Label letterLabel;

    @FXML
    private ImageView image;

    @FXML
    private MenuItem startButton;
    
    @FXML
    private MenuItem roundsButton;

    @FXML
    private MenuItem solutionButton;

    @FXML
    private MenuItem dictionaryButton;

    @FXML
    private MenuButton letter;

    @FXML
    private Button okButton;

    @FXML
    private MenuButton position;
    
    @FXML
    private HBox iterations;

    @FXML
    private HBox word;

    @FXML
    private Label letters;

    @FXML
    private Label points;
    
    @FXML
    public void initialize() {
    	this.record = new History();
    	lives.setVisible(false);
    	totalwords.setVisible(false);
    	points.setVisible(false);
    	word.setVisible(false);
    	letters.setVisible(false);
    	rightguess.setVisible(false);
    	position.setVisible(false);
    	letter.setVisible(false);
        okButton.setVisible(false);
        startButton.setDisable(true);
        roundsButton.setDisable(false);
        solutionButton.setDisable(true);
        dictionaryButton.setDisable(true);
    }

    @FXML
    void start(ActionEvent event) {
    	this.game = new Game(dict.pickWord(), dict);
    	this.game.createLetters();
    	
    	System.out.println("words:");
    	this.game.printWords();
    	System.out.println("ANSWER:" + this.game.word.word);	
    	
    	
    	points.textProperty().bind(new SimpleIntegerProperty(game.points).asString());
    	rightguess.textProperty().bind(new SimpleStringProperty(String.format("%.2f", game.percentange) + " %"));
    	letters.textProperty().bind(new SimpleStringProperty(game.lettersString()));
    	lives.textProperty().bind(new SimpleIntegerProperty(game.lives).asString());
    	
    	this.word.getChildren().clear();
    	for (int i=0; i<this.game.word.len; i++) {
    		Label l = new Label();
    		l.setText(String.valueOf(this.game.state.get(i)));
    		this.word.getChildren().add(l);
    	}
    	
    	this.iterations.getChildren().clear();
    	for (int i=0; i<this.game.word.len; i++) {
    		Label l = new Label();
    		l.setText(Integer.toString(i));
    		this.iterations.getChildren().add(l);
    	}
    	
    	this.updatePositionMenu();
    	this.letter.getItems().clear();
    	
    	this.letterLabel.setText("");
    	this.positionLabel.setText("");
		
    	image.setImage(new Image("resources/images/hang0.png"));	
    	
    	lives.setVisible(true);
		points.setVisible(true);
    	word.setVisible(true);
    	letters.setVisible(true);
    	rightguess.setVisible(true);
    	position.setVisible(true);
    	letter.setVisible(true);
        okButton.setVisible(true);
        startButton.setDisable(false);
        roundsButton.setDisable(false);
        dictionaryButton.setDisable(false);
        solutionButton.setDisable(false);
        image.setVisible(true);
    }

    @FXML
    void load(ActionEvent event) {
    	String ID = LoadBox.display();
    	try {
			dict = new Dictionary(ID);
			totalwords.setText(Integer.toString(dict.totalWords));
			totalwords.setVisible(true);
			dictionaryButton.setDisable(false);
			startButton.setDisable(false);
	    	points.setVisible(false);
	    	word.setVisible(false);
	    	letters.setVisible(false);
	    	rightguess.setVisible(false);
	    	position.setVisible(false);
	    	letter.setVisible(false);
	        okButton.setVisible(false);
	        solutionButton.setDisable(true);
			image.setVisible(false);
	        
		} catch (FileNotFoundException e) {
			AlertBox.display("Error", "Dictionary not found!");
		}
    }

    @FXML
    void create(ActionEvent event) {
    	String[] ids = CreateBox.display();
    	new MakeDictionary(ids[1], ids[0]);
    }

    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void dictionary(ActionEvent event) {
    	DictionaryBox.display(dict);
    }

    @FXML
    void rounds(ActionEvent event) {
    	AlertBox.display("History", this.record.getString());
    }

    @FXML
    void solution(ActionEvent event) {
    	okButton.setVisible(false);
        for(int i=0; i<this.game.word.len; i++) {
        	this.game.state.set(i, this.game.word.word.charAt(i));
        }
        this.word.getChildren().clear();
    	for (int i=0; i<this.game.word.len; i++) {
    		Label l = new Label();
    		l.setText(String.valueOf(this.game.state.get(i)));
    		this.word.getChildren().add(l);
    	}
        AlertBox.display("Solution", "You lost! The solution was " + this.game.word.word + ". Play again by pressing Application -> start");
        this.record.add(this.game.word.word, this.game.tries, "Computer");
    }

    @FXML
    void ok(ActionEvent event) {
    	if (!this.game.guess(this.positionChoice, this.letterChoice)) {
    		this.image.setImage(new Image("resources/images/hang"+Integer.toString(6-this.game.lives)+".png"));
    		if (this.game.lives == 0) {
    	        okButton.setVisible(false);
    	        for(int i=0; i<this.game.word.len; i++) {
    	        	this.game.state.set(i, this.game.word.word.charAt(i));
    	        }
    	        AlertBox.display("Loose", "You lost! Play again by pressing Application -> start");
    	        this.record.add(this.game.word.word, this.game.tries, "Computer");
    		}
    	}
    	else if (this.game.rightGuesses == this.game.word.len) {
    		okButton.setVisible(false);
    		AlertBox.display("Win", "You won! Play again by pressing Application -> start");
    		this.record.add(this.game.word.word, this.game.tries, "Player");
    	}
    	points.textProperty().bind(new SimpleIntegerProperty(game.points).asString());
    	rightguess.textProperty().bind(new SimpleStringProperty(String.format("%.2f", 100*game.percentange) + " %"));
    	letters.textProperty().bind(new SimpleStringProperty(game.lettersString()));
    	lives.textProperty().bind(new SimpleIntegerProperty(game.lives).asString());
    	this.updatePositionMenu();
    	this.letter.getItems().clear();
    	this.letterLabel.setText("");
    	this.positionLabel.setText("");
    	this.word.getChildren().clear();
    	for (int i=0; i<this.game.word.len; i++) {
    		Label l = new Label();
    		l.setText(String.valueOf(this.game.state.get(i)));
    		this.word.getChildren().add(l);
    	}
    }
    
    void updateLetterMenu(int position) {
    	this.letter.getItems().clear();
    	List<Character> list = this.game.getLetters(position);
    	for (Character c : list) {
    		MenuItem m = new MenuItem(String.valueOf(c));
    		m.setOnAction(e -> {
    			this.letterChoice = c;
    			this.letterLabel.setText(String.valueOf(c));
    		});
    		this.letter.getItems().add(m);
    	}
    }
    
    void updatePositionMenu () {
    	this.position.getItems().clear();
    	List<Integer> list = this.game.getPositions();
    	for (Integer i : list) {
    		MenuItem m = new MenuItem(Integer.toString(i));
    		m.setOnAction(e -> {
    			updateLetterMenu(i);
    			this.positionChoice = i;
    			this.letterChoice = null;
    			this.positionLabel.setText(Integer.toString(i));
    			this.letterLabel.setText("");
    		});
    		this.position.getItems().add(m);
    	}
    }

}
