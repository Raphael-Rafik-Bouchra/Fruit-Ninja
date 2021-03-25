package view;





import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class GameOverView {

	private boolean win;
	private Stage stage;
	private Scene GameOverView;
	private GameView gameView;
	private StartView startView;
	
	public GameOverView(Stage stage) {
		this.stage = stage;
		this.win = false;
	}
	
	public void prepareGameOverView() {
		Image winImage = new Image("/resources/GameOver/Win.png");
		Image loseImage = new Image("/resources/GameOver/Lose.png");
		Image playImage = new Image("/resources/GameOver/Play Again/Play Again.png");
		Image returnImage = new Image("/resources/GameOver/Return To Menu/Return To Menu.png");
		ImageView playView = new ImageView(playImage);
		ImageView returnView = new ImageView(returnImage);
		ImageView imageView;
		
		Button playAgainButton = new Button("RETRY" , playView);
		playAgainButton.setMinSize(100 , 100);
		playAgainButton.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
		Button returnToMenuButton = new Button("MENU" , returnView);
		returnToMenuButton.setMinSize(100 , 100);
		returnToMenuButton.setFont(Font.font("Verdana", FontWeight.BOLD, 25));

		HBox hBox = new HBox(playAgainButton , returnToMenuButton);
		hBox.setSpacing(20);
		hBox.setLayoutX(390);
		hBox.setLayoutY(450);
		Pane pane = new Pane();
		pane.setMaxSize(1124, 750);
		pane.getChildren().add(hBox);
		
		
		if(win == false) {
			imageView = new ImageView(loseImage);
		}else {
			imageView = new ImageView(winImage);
		}
		
		EventHandler<ActionEvent> playEvent = new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				gameView.prepareGameView();
				stage.setScene(gameView.getGameViewScene());
				
			}
		};
		
		EventHandler<ActionEvent> menuEvent = new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				startView.prepareStartView();
				stage.setScene(startView.getStartViewScene());
				
			}
		};
		playAgainButton.setOnAction(playEvent);
		returnToMenuButton.setOnAction(menuEvent);
		
		StackPane root = new StackPane();
		root.getChildren().add(imageView);
		root.getChildren().add(pane);
		GameOverView = new Scene(root, 1124, 750);
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public Scene getGameOverView() {
		return GameOverView;
	}

	public void setGameOverView(Scene gameOverView) {
		GameOverView = gameOverView;
	}

	public GameView getGameView() {
		return gameView;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	public StartView getStartView() {
		return startView;
	}

	public void setStartView(StartView startView) {
		this.startView = startView;
	}
	
	
}
