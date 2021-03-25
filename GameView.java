package view;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.Clip;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.gameobjects.Bomb;
import model.gameobjects.Coconut;
import model.gameobjects.DeadlyBomb;
import model.gameobjects.Field;
import model.gameobjects.GameObject;
import model.gameobjects.Kiwi;
import model.gameobjects.Lemon;
import model.gameobjects.Pineapple;
import model.gameobjects.Special1;
import model.gameobjects.Special2;
import model.gameobjects.SpriteAnimation;
import model.gameobjects.Watermelon;
import model.player.Player;



public class GameView {
	private Player player;
	private Image backgorud;
	private Image[] images;
	private StartView startViewScene;
	private GameOverView gameOverView;
	private Scene gameViewScene;
	private Stage stage;
	private Random rg;
	private Clip slashingSound;
	private Clip explosionSound;
	private Clip deadlyExplosionSound;
	private ParallelTransition parallel = new ParallelTransition();
	private ParallelTransition parallel2 = new ParallelTransition();
	private Timeline timeline ;
	private ImageView objectImageView ;
	private PathTransition pathTransition = new PathTransition();
	private RotateTransition rotateTransition = new RotateTransition();
	private SequentialTransition sequence = new SequentialTransition();
	private Point2D objectPosition = new Point2D(0,0);
	private double throwDuration;
	private double animationDuration;
	
	
	public GameView(Stage stage) {
		this.stage = stage;
		player = player.getInstance();
		rg = new Random();
	}
	
	public void prepareGameView(){
	
			backgorud = new Image("resources/Background.png");
			ImageView backgroudImageView = new ImageView(backgorud);
			images = new Image[9];
			images[0] = new Image("resources/Fruits/Coconut/CoconutSprite.png");
			images[1] = new Image("resources/Fruits/Kiwi/kiwiSprite.png");
			images[2] = new Image("resources/Fruits/Lemon/LemonSprite.png");
			images[3] = new Image("resources/Fruits/Pineapple/PineappleSprite.png");
			images[4] = new Image("resources/Fruits/Watermelon/WatermeloneSprite.png");
			images[5] = new Image("resources/Fruits/Special 1/Special1Sprite.png");
			images[6] = new Image("resources/Fruits/Special 2/Special2Sprite.png");
			images[7] = new Image("resources/Bombs/Bomb/BombSprite.png");
			images[8] = new Image("resources/Bombs/DeadlyBomb/DeadlyExplosionSprite.png");
			
			
			StackPane stackPane = new StackPane();
			stackPane.setMaxSize(1124, 750);
			stackPane.getChildren().add(backgroudImageView);
			
			Pane pane = new Pane();
			pane.setMaxSize(1124, 750);
			stackPane.getChildren().add(pane);
			

			Label highestScoreLabel = new Label("Best: " + loadHighestScore());
			highestScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
			highestScoreLabel.setTextFill(Color.web("#f8e349"));
			highestScoreLabel.setLayoutX(20);
			highestScoreLabel.setLayoutY(100);
			pane.getChildren().add(highestScoreLabel);
			
			Image scoreImage = new Image("resources/Score.png");
			ImageView scoreImageView = new ImageView(scoreImage);
			scoreImageView.setLayoutX(0);
			scoreImageView.setLayoutY(0);
			pane.getChildren().add(scoreImageView);
			Label scoreLabel = new Label(Integer.toString(player.getScore()));
			scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
			scoreLabel.setTextFill(Color.web("#f8e349"));
			scoreLabel.setLayoutX(90);
			scoreLabel.setLayoutY(20);
			pane.getChildren().add(scoreLabel);
			
			Image livesImage = new Image("resources/Heart.png");
			ImageView livesImageView = new ImageView(livesImage);
			livesImageView.setLayoutX(1050);
			livesImageView.setLayoutY(0);
			pane.getChildren().add(livesImageView);
			Label livesLabel = new Label(Integer.toString(player.getLives()));
			livesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
			livesLabel.setTextFill(Color.web("#f8e349"));
			livesLabel.setLayoutX(1020);
			livesLabel.setLayoutY(5);
			pane.getChildren().add(livesLabel);
			
			
			

			Group objectGroup = new Group();
			Field field = new Field();


			EventHandler<ActionEvent> test = new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					if(field.getObject().isHit() == false) {
						if(field.getObject().getPoints() != 0) {
							player.subtractLife();
							livesLabel.setText(Integer.toString(player.getLives()));
							if(player.getLives() <= 0) {
								if(player.getScore() > loadHighestScore()) {
									//win
									timeline.stop();
									saveHighestScore(player.getScore());
									player.setLives(3);
									player.setScore(0);
									gameOverView.setWin(true);
									gameOverView.prepareGameOverView();
									stage.setScene(gameOverView.getGameOverView());
								}
								else {
									timeline.stop();
									player.setLives(3);
									player.setScore(0);
									gameOverView.setWin(false);
									gameOverView.prepareGameOverView();
									stage.setScene(gameOverView.getGameOverView());
								}
							}
						}
					}
					
				}
			};
			

			timeline = new Timeline(new KeyFrame(Duration.seconds(throwDuration), ev -> {
				field.setObject(newObject());
				objectImageView = new ImageView(images[field.getObject().getType()]);
				objectGroup.getChildren().add(objectImageView);
				double rand;
					if(field.getObject().getX() < 600)
						rand = field.getObject().getVelocityX() + rg.nextInt(250) +500;
					else
						rand = field.getObject().getVelocityX() - rg.nextInt(250) - 500;

					
				throwObject(objectImageView , field.getObject().getX() , field.getObject().getY() , field.getObject().getVelocityX() , field.getObject().getVelocityY() ,rand );
				spriteObject(objectImageView , field.getObject().getX() , field.getObject().getY() , field.getObject().getVelocityX() , field.getObject().getVelocityY() ,rand);
				sequence.play();
				parallel.setOnFinished(test);
			}));
			
			EventHandler<MouseEvent> mousePressed = new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					 field.getBlade().setStartPosition(event.getX(), event.getY());
					 field.getBlade().setSwiped(false);
				}
			};

			EventHandler<MouseEvent> mouseDragged = new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					try {
					objectPosition = objectImageView.localToParent(objectImageView.getLayoutX(), objectImageView.getLayoutY());
					field.getObject().setPosition(objectPosition.getX(), objectPosition.getY());
					if(field.getObject().insideBounds(event.getX(), event.getY()))
						field.getBlade().setSwiped(true);
					}catch (NullPointerException e) {
						System.out.println("NO OBJECT YET");
					}
					
				}
			};
			EventHandler<MouseEvent> mouseReleased = new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					field.getBlade().setEndPosition(event.getX(), event.getY());
					if(field.getBlade().isSwiped()) {
						if(field.getObject().isHit() == false) {
							if(field.getObject().getType() == 7) {
								sequence.stop();
								parallel2.playFrom(parallel.getCurrentTime());
								explosionSound.setFramePosition(0);
								explosionSound.start();
								field.getObject().setHit(true);
								player.subtractLife();
								livesLabel.setText(Integer.toString(player.getLives()));
								if(player.getLives() <= 0) {
									if(player.getScore() > loadHighestScore()) {
										//win
										timeline.stop();
										saveHighestScore(player.getScore());
										player.setLives(3);
										player.setScore(0);
										gameOverView.setWin(true);
										gameOverView.prepareGameOverView();
										stage.setScene(gameOverView.getGameOverView());
									}
									else {
										timeline.stop();
										gameOverView.setWin(false);
										player.setLives(3);
										player.setScore(0);
										gameOverView.prepareGameOverView();
										stage.setScene(gameOverView.getGameOverView());
									}
								}
							}
							else if(field.getObject().getType() == 8) {
								sequence.stop();
								parallel2.playFrom(parallel.getCurrentTime());
								
								deadlyExplosionSound.setFramePosition(0);
								deadlyExplosionSound.start();
								field.getObject().setHit(true);
								player.setLives(0);
								livesLabel.setText(Integer.toString(player.getLives()));
								if(player.getLives() <= 0) {
									if(player.getScore() > loadHighestScore()) {
										//win
										timeline.stop();
										saveHighestScore(player.getScore());
										player.setLives(3);
										player.setScore(0);
										gameOverView.setWin(true);
										gameOverView.prepareGameOverView();
										stage.setScene(gameOverView.getGameOverView());
									}
									else {
										timeline.stop();
										gameOverView.setWin(false);
										player.setLives(3);
										player.setScore(0);
										gameOverView.prepareGameOverView();
										stage.setScene(gameOverView.getGameOverView());
									}
								}
							
							}
							else {
								sequence.stop();
								parallel2.playFrom(parallel.getCurrentTime());
								slashingSound.setFramePosition(0);
								slashingSound.start();
								field.getObject().setHit(true);
								player.addScore(field.getObject().getPoints());
								scoreLabel.setText(Integer.toString(player.getScore()));
							}
						}
					}
				}
			};

			pane.addEventFilter(MouseEvent.MOUSE_PRESSED,mousePressed);
			pane.addEventFilter(MouseEvent.MOUSE_DRAGGED,mouseDragged);
			pane.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseReleased);
			
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
			
			pane.getChildren().add(objectGroup);
			
			gameViewScene = new Scene(stackPane , 1124 , 750);
			
	}

	public Scene getGameViewScene() {
		return gameViewScene;
	}

	public void setGameViewScene(Scene gameViewScene) {
		this.gameViewScene = gameViewScene;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public StartView getStartViewScene() {
		return startViewScene;
	}

	public void setStartViewScene(StartView startViewScene) {
		this.startViewScene = startViewScene;
	}

	public void saveHighestScore(int score) {
		File file = new File("HighestScore.txt");
		try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(""+score);
            bufferedWriter.newLine();
             bufferedWriter.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	public int loadHighestScore() {
        File file = new File("HighestScore.txt");
        String line;
        int highestScore=0;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while(bufferedReader.ready()) {
                 line = bufferedReader.readLine();
                 highestScore = Integer.parseInt(line);
              
            }
        } catch (FileNotFoundException e) {
            saveHighestScore(0);
        } catch (IOException e) {
        	saveHighestScore(0);
        }
        return highestScore;
	}

	private GameObject newObject() {
		// Create random GameObject
		int rand = rg.nextInt(20);
		GameObject object;
		switch (rand) {
		case 0:
			object = new Coconut();
			break;
		case 1:
			object = new Kiwi();
			break;
		case 2:
			object = new Lemon();
			break;
		case 3:
			object = new Pineapple();
			break;
		case 4:
			object = new Watermelon();
			break;
		case 5:
			object = new Special1();
			break;
		case 6:
			object = new Special2();
			break;
		case 7:
			object = new Bomb();
			break;
		case 8:
			object = new DeadlyBomb();
			break;
		case 9:
			object = new Coconut();
			break;
		case 10:
			object = new Kiwi();
			break;
		case 11:
			object = new Lemon();
			break;
		case 12:
			object = new Pineapple();
			break;
		case 13:
			object = new Watermelon();
			break;
		default:
			object = new Bomb();
			break;
		}
		// Set random position and speed
		rand =rg.nextInt(15);
		switch (rand) {
		case 0:
			object.setPosition(-150, 150);
			object.setVelocity(500,150);
			break;
		case 1:
			object.setPosition(-150, 300);
			object.setVelocity(100, -10);
			break;
		case 2:
			object.setPosition(-150, 700);
			object.setVelocity(300, 100);
			break;
		case 3:
			object.setPosition(300, 900);
			object.setVelocity(700, 100);
			break;
		case 4:
			object.setPosition(500, 900);
			object.setVelocity(600, 200);
			break;
		case 5:
			object.setPosition(700, 900);
			object.setVelocity(300, 100);
			break;
		case 6:
			object.setPosition(1000, 900);
			object.setVelocity(500, 300);
			break;
		case 7:
			object.setPosition(1300, 300);
			object.setVelocity(500, 200);
			break;
		case 8:
			object.setPosition(1300, 700);
			object.setVelocity(500, 300);
			break;
		default:
			object.setPosition(550, 900);
			object.setVelocity(700, -500);
			break;
		}
		return object;
	}
	
	public void throwObject(ImageView objectImageView , int ix , int iy , double cx , double cy , double fx ) {

		Path path = new Path();
		path.getElements().add(new MoveTo(ix, iy));
	    path.getElements().add(new QuadCurveTo(cx, cy, fx, 900));
	    
	    
	    rotateTransition = new RotateTransition();
	    rotateTransition.setDuration(Duration.millis(animationDuration));
	    rotateTransition.setNode(objectImageView);
	    rotateTransition.setByAngle(500);
	   
	    
		pathTransition = new PathTransition();
	    pathTransition.setDuration(Duration.millis(animationDuration));
	    //Set node to be animated
	    pathTransition.setNode(objectImageView);
	    pathTransition.setPath(path);
	    parallel = new ParallelTransition(pathTransition , rotateTransition );
	    sequence = new SequentialTransition(parallel , setSpriteAnimation(objectImageView));
	}
	public void spriteObject(ImageView objectImageView , double ix , double iy , double cx , double cy , double fx ) {

		Path path = new Path();
		path.getElements().add(new MoveTo(ix, iy));
	    path.getElements().add(new QuadCurveTo(cx, cy, fx, 900));
	    
	    
	    rotateTransition = new RotateTransition();
	    rotateTransition.setDuration(Duration.millis(animationDuration));
	    rotateTransition.setNode(objectImageView);
	    rotateTransition.setByAngle(500);
	   
	    
		pathTransition = new PathTransition();
	    pathTransition.setDuration(Duration.millis(animationDuration));
	    //Set node to be animated
	    pathTransition.setNode(objectImageView);
	    pathTransition.setPath(path);
	    
	    parallel2 = new ParallelTransition(pathTransition , rotateTransition , setSpriteAnimation(objectImageView) );
	}
	
	public Animation setSpriteAnimation(ImageView object) {
		 object.setViewport(new Rectangle2D(0, 0, 125, 125));

	        final Animation animation = new SpriteAnimation(
	                object,
	                Duration.millis(100),
	                2, 2,
	                0, 0,
	                125, 125
	        );
	        animation.setCycleCount(1);
	        return animation;
	}

	public Clip getSlashingSound() {
		return slashingSound;
	}

	public void setSlashingSound(Clip slashingSound) {
		this.slashingSound = slashingSound;
	}

	public Clip getExplosionSound() {
		return explosionSound;
	}

	public void setExplosionSound(Clip explosionSound) {
		this.explosionSound = explosionSound;
	}

	public Clip getDeadlyExplosionSound() {
		return deadlyExplosionSound;
	}

	public void setDeadlyExplosionSound(Clip deadlyExplosionSound) {
		this.deadlyExplosionSound = deadlyExplosionSound;
	}

	public GameOverView getGameOverView() {
		return gameOverView;
	}

	public void setGameOverView(GameOverView gameOverView) {
		this.gameOverView = gameOverView;
	}

	public double getThrowDuration() {
		return throwDuration;
	}

	public void setThrowDuration(double throwDuration) {
		this.throwDuration = throwDuration;
	}

	public double getAnimationDuration() {
		return animationDuration;
	}

	public void setAnimationDuration(double animationDuration) {
		this.animationDuration = animationDuration;
	}

	
	
	
}
