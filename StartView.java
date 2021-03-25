package view;
import javax.sound.sampled.Clip;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StartView {
	private Scene startViewScene;
	private GameView gameViewScene;
	private Stage stage;
	private Clip slashingSound;
	
	public StartView(Stage stage) {
		this.stage = stage;
	}
	
	public void prepareStartView() {
		
		
		try { 
            Image image1 = new Image("resources/Start.jpg");
            ImageView imageView1 = new ImageView(image1);
            Image image2 = new Image("resources/About Button/About.png");
            ImageView imageView2 = new ImageView(image2);
            Image image3 = new Image("resources/Play Button/Play.png");
            ImageView imageView3 = new ImageView(image3);
            Image image4 = new Image("resources/About Button/Instructions.png");
            ImageView imageView4 = new ImageView(image4);
            
            Button playButton = new Button("PLAY" , imageView3);
            playButton.setMinSize(150 , 150);
            playButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            playButton.setLayoutX(450);
            playButton.setLayoutY(320);
            Button aboutButton = new Button("About" , imageView2);
            aboutButton.setMinSize(190 , 150);
            aboutButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            aboutButton.setLayoutX(450);
            aboutButton.setLayoutY(520);
            
            RadioButton easyRadioButton = new RadioButton("EASY");
            RadioButton mediumRadioButton = new RadioButton("MEDIUM");
            RadioButton hardRadioButton = new RadioButton("HARD");
            easyRadioButton.setFont(Font.font("Verdana", FontWeight.BOLD , 20));
            easyRadioButton.setTextFill(Color.web("#ffffff"));
            mediumRadioButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            mediumRadioButton.setTextFill(Color.web("#ffffff"));
            hardRadioButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            hardRadioButton.setTextFill(Color.web("#ffffff"));
            easyRadioButton.setLayoutX(375);
            easyRadioButton.setLayoutY(250);
            mediumRadioButton.setLayoutX(500);
            mediumRadioButton.setLayoutY(250);
            hardRadioButton.setLayoutX(650);
            hardRadioButton.setLayoutY(250);
            ToggleGroup radioGroup = new ToggleGroup();
            easyRadioButton.setToggleGroup(radioGroup);
            mediumRadioButton.setToggleGroup(radioGroup);
            hardRadioButton.setToggleGroup(radioGroup);

			Alert instructions = new Alert(Alert.AlertType.INFORMATION);
			instructions.setGraphic(imageView4);
			instructions.setHeaderText(null);
			instructions.setTitle("About");

			Alert chooseDifficulty = new Alert(Alert.AlertType.ERROR);
			
			EventHandler<ActionEvent> aboutEvent = new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					instructions.showAndWait();
					
				}
			};
			aboutButton.setOnAction(aboutEvent);
            
			EventHandler<ActionEvent> playEvent = new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					RadioButton selectedRadioButton =(RadioButton) radioGroup.getSelectedToggle();
					try {
					if(selectedRadioButton.getText().equals("EASY")) {
						gameViewScene.setAnimationDuration(3000); //in miliSeconds
						gameViewScene.setThrowDuration(3.5); //in Seconds
					}
					else if(selectedRadioButton.getText().equals("MEDIUM")) {
						gameViewScene.setAnimationDuration(2500); //in miliSeconds
						gameViewScene.setThrowDuration(3); //in Seconds
					}
					else {
						//HARD
						gameViewScene.setAnimationDuration(2000); //in miliSeconds
						gameViewScene.setThrowDuration(2.5); //in Seconds
						
					}
					slashingSound.start();
					gameViewScene.prepareGameView();
					stage.setScene(gameViewScene.getGameViewScene());
					}catch (NullPointerException e) {
						chooseDifficulty.setContentText("Please Choose Difficulty First");
						chooseDifficulty.showAndWait();
					}
					
				}
			};
			playButton.setOnAction(playEvent);
			
			StackPane stackPane = new StackPane();
			stackPane.setMaxSize(1124, 750);
			Pane pane = new Pane();
			pane.setMaxSize(1124, 750);
			stackPane.getChildren().add(imageView1);
			stackPane.getChildren().add(pane);
			pane.getChildren().add(playButton);
			pane.getChildren().add(aboutButton);
			pane.getChildren().add(easyRadioButton);
			pane.getChildren().add(mediumRadioButton);
			pane.getChildren().add(hardRadioButton);

            startViewScene = new Scene(stackPane, 1124, 750); 
   

        } 
  
        catch (Exception e) { 
  
            System.out.println(e.getMessage()); 
        } 
    }

	public Scene getStartViewScene() {
		return startViewScene;
	}

	public void setGameViewScene(GameView gameViewScene) {
		this.gameViewScene = gameViewScene;
	}

	public Clip getSlashingSound() {
		return slashingSound;
	}

	public void setSlashingSound(Clip slashingSound) {
		this.slashingSound = slashingSound;
	}

	
	

}
