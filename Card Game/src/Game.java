import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Game extends Application {
	// Game
	private static final String Title = "Card Game";
	private static final int GAME_WIDTH = 900;
	private static final int GAME_HEIGHT = 800;
	private static Player winner = null;
	private static int PlayerTurn = 0;
	private static int Round = 1;
	private static Deck deck = new Deck();

	//Instantiate Player
	private static ArrayList<Player> player = new ArrayList<Player>();
	private static final int HAND_SIZE = CardGame.getPlayerSetting()[0];
	private static final int MIN_PLAYER = CardGame.getPlayerSetting()[1];
	private static final int MAX_PLAYER = CardGame.getPlayerSetting()[2];
	private static int TOTAL_PLAYER = 2;
		
	//Images
	private static final String imagePath = "/images";
	private static final String CardsPath = imagePath+"/images_deck";
	private static final String continuepng = imagePath+"/continue.png";
	private static final String backpng = imagePath+"/back.png";
	private static final String cardBackpng = imagePath+"/card_back.png";
	
	// Sound
	private static final MediaView mediaView = new MediaView();
	private static final String bgmPath = "/SFX";
	private static final String bgmMp3 = bgmPath+"/BGM.mp3";
	private static final String cardkMp3 = bgmPath+"/cardFX.mp3";
	private static final String victorykMp3 = bgmPath+"/victory.mp3";
	private static final String GameBgmMp3 = bgmPath+"/GameBGM.mp3";
	private static final String pressMp3 = bgmPath+"/btnPress.mp3";
	private static final String clickMp3 = bgmPath+"/btnClick.mp3";
	
	// Font
	private static final Font defaultFont = Font.font("Helvetica", FontWeight.BOLD, 35);
	private static final Font TextFieldFont = Font.font("Helvetica", 20);
	private static final Font PlayerFont = Font.font("Helvetica", FontWeight.BOLD, 20);
	private static final Font Warning = Font.font("Helvetica", FontWeight.BOLD, 20);
	
	 public void start(Stage primaryStage) {
		 playBgm (bgmMp3);
		 Scene scene = new Scene(getMenuScene(),GAME_WIDTH,GAME_HEIGHT);

         primaryStage.setTitle(Title);
         primaryStage.setResizable(false);
         primaryStage.setScene(scene);
         primaryStage.show();
	 }
	 
	 public BorderPane backgroundScene() {
		 BorderPane Pane = new BorderPane();
		 HBox hbox = new HBox();
		 
		 String backgroundpng = imagePath+"/background.gif";
		 String logopng = imagePath+"/logo.png";
		 
		 Pane.getChildren().add(setImageP(backgroundpng));
		 hbox.getChildren().add(setImageP(logopng));
		 hbox.setAlignment(Pos.CENTER);
		 
		 Pane.setTop(hbox);
		 
		 return Pane;
	 }
	 
	 public Pane getMenuScene() {
		 BorderPane Pane = backgroundScene();
		 GridPane Gpane = new GridPane();
		 String quitpng = imagePath+"/quit.png";
		 String playpng = imagePath+"/play.png";
		 
		 Button playbtn = new Button("Play");
		 Button exitbtn = new Button("Exit");
		 
		 setBtnImageP(playbtn,playpng);
		 setBtnImageP(exitbtn,quitpng);
		 
		 Gpane.add(playbtn,0,0);
		 Gpane.add(exitbtn,0,1);
		 Gpane.setAlignment(Pos.TOP_CENTER);
		 
		 // Play Game
		 playbtn.setOnAction(e->{
			 playBtnSoundP(pressMp3);
			 playbtn.getScene().setRoot(getPlayerScene1());
		 });
		 
		 // Exit Game
		 exitbtn.setOnAction(e->{
			 Stage primaryStage = (Stage) exitbtn.getScene().getWindow();
			 primaryStage.close();
		 });

		 Pane.setCenter(Gpane);
		 return Pane;
	 }
	 
	 public Pane getPlayerScene1() {
		 BorderPane Pane = backgroundScene();
		 GridPane Gpane = new GridPane();

		 String prevpng = imagePath+"/prev-button.png";
		 String nextpng = imagePath+"/next-button.png";

		 // Clear All previous player info
		 player.clear();
		 
		 Text tx = new Text("Number of Player");
		 tx.setFont(defaultFont);
		 tx.setFill(Color.WHITE);
		 tx.setStroke(Color.BLACK);
		 
		 Text PNo = new Text(""+TOTAL_PLAYER);
		 PNo.setFont(defaultFont);
		 PNo.setFill(Color.WHITE);
		 PNo.setStroke(Color.BLACK);
		 
		 Button prevbtn = new Button("Prev");
		 Button nextbtn = new Button("Next");
		 Button continuebtn = new Button("Continue");
		 Button backbtn = new Button("Back");

		 setBtnImageP(prevbtn,prevpng);
		 setBtnImageP(nextbtn,nextpng);
		 setBtnImageP(continuebtn,continuepng);
		 setBtnImageP(backbtn,backpng);
		 
		 Gpane.add(tx, 0, 0, 4, 1);
		 Gpane.addRow(1,new Text("        "),prevbtn, PNo, nextbtn);
		 Gpane.add(continuebtn, 1, 2, 3, 1);
		 Gpane.add(backbtn, 1, 3, 3, 1);
		 Gpane.setAlignment(Pos.TOP_CENTER);

		 // Decrease player
		 prevbtn.setOnAction(e->{
			 playBtnSoundP(clickMp3);
			 if(MIN_PLAYER < TOTAL_PLAYER) TOTAL_PLAYER = TOTAL_PLAYER - 1;
			 PNo.setText(""+TOTAL_PLAYER);
		 });

		 // Increase player
		 nextbtn.setOnAction(e->{
			 playBtnSoundP(clickMp3);
			 if(MAX_PLAYER > TOTAL_PLAYER) TOTAL_PLAYER = TOTAL_PLAYER + 1;
			 PNo.setText(""+TOTAL_PLAYER);
		 });

		 // Next Page to enter player name
		 continuebtn.setOnAction(e->{
			 playBtnSoundP(pressMp3);
			 backbtn.getScene().setRoot(getPlayerScene2());
		 });

		 // Back to Menu
		 backbtn.setOnAction(e->{
			 playBtnSoundP(pressMp3);
			 backbtn.getScene().setRoot(getMenuScene());
		 });
		 
		 Pane.setCenter(Gpane);
		 return Pane;
	 }
	 
	 public Pane getPlayerScene2() {
		 BorderPane Pane = backgroundScene();
		 GridPane Gpane = new GridPane();
		 StackPane notificationArea = new StackPane();
		 
		 Button continuebtn = new Button("Continue");
		 Button backbtn = new Button("Back");
		 
		 setBtnImageP(continuebtn,continuepng);
		 setBtnImageP(backbtn,backpng);
		 
		 Text notification = new Text("");
		 notification.setFont(Warning);
		 notification.setFill(Color.RED);
		 
		 notificationArea.getChildren().add(notification);
		 notificationArea.setAlignment(Pos.BASELINE_RIGHT);
		 
		 //Notification area
		 Gpane.add(notificationArea,0,0,2,1);

		 Text[] tx = new Text[TOTAL_PLAYER];
		 TextField[] tf = new TextField[TOTAL_PLAYER];
		 
		 for(int i = 0; i < TOTAL_PLAYER; i++) {
			 tx[i] = new Text("Player " + (i+1) + " Name : ");
			 tx[i].setFont(defaultFont);
			 tx[i].setFill(Color.WHITE);
			 tx[i].setStroke(Color.BLACK);
			 tf[i] = new TextField();
			 tf[i].setMaxWidth(250);
			 tf[i].setFont(TextFieldFont);
			 if(player.size() > i) tf[i].setText(player.get(i).getName());
			 Gpane.addRow(i+1,tx[i],tf[i]);
		 }

		 Gpane.addRow(TOTAL_PLAYER+1,continuebtn,backbtn);
		 Gpane.setAlignment(Pos.TOP_CENTER);
		 Gpane.setVgap(10);
		 
		 // Check Name and continue to start game
		 continuebtn.setOnAction(e->{
			 playBtnSoundP(pressMp3);
			 boolean invalid = false;
			 int i = 1;
			 for(TextField temptf : tf) {
				 String temptx = temptf.getText();
				 invalid = true;
				 if(temptx.length() > 0) {
					 if(temptx.trim().length() == 0) {	// If no enter player name
						 System.out.println("Please enter player name.");
					 }else {
						 if(player.size() > 0) { // If First Player is defined
							 // Compare all player name, find Duplicate Name
							 invalid = CardGame.checkAddPlayerName(player,temptx,i);
							 
							 // If no Duplicate Name
							 if(!invalid && player.size() < i) {
								 player.add(new Player(temptx));
							 }else if(!invalid){
								 player.set(i-1,new Player(temptx));
							 }else if(invalid) {
								// If Duplicate Name
								 notification.setText("Player " + i + " name had been used, try different name.");
								 break;
							 }
						 }else {
							 // First Player Name
							 player.add(new Player(temptx));
						 }
						 
					 }
				 }else {
					 notification.setText("Please enter Player " + i +" Name.");
					 break;
				 }
				 i++;
			 }
			 if(!invalid) {
				 backbtn.getScene().setRoot(getStartGameScene());
				 
			 }
		 });

		 // Back to Total Player
		 backbtn.setOnAction(e->{
			 playBtnSoundP(pressMp3);
			 backbtn.getScene().setRoot(getPlayerScene1());
		 });
		 
		 Pane.setCenter(Gpane);
		 return Pane;
	 }
	 
	 public BorderPane getStartGameScene() {
		 playBgm (GameBgmMp3);
		 
		 // Create deck of cards 
		 deck.fillDeck();
		 deck.shuffle();
		 
		 // Draw cards
		 for(int i = 0; i < TOTAL_PLAYER; i++) {
			 player.get(i).resetPlayer();
			 player.get(i).draw(deck,HAND_SIZE);
		 }
		 
		 BorderPane Pane = new BorderPane();
		 
		 // reset
		 PlayerTurn = 0;
		 Round = 1;
		 
		 Pane[] cardPane = new Pane[MAX_PLAYER];
		 Pane = table(cardPane,PlayerTurn,1);
		 		 
		 return Pane;
	 }
	 
	 public BorderPane table(Pane[] cardPane,int playerno,int round) {

		 BorderPane Pane = new BorderPane();

		 String backgroundpng = imagePath+"/PokerTable.png";
		 String exitGamepng = imagePath+"/exitGame.png";
		 String playagainpng = imagePath+"/playagain.png";
		 String nextroundpng = imagePath+"/nextRound.png";

		 Pane.getChildren().add(setImageP(backgroundpng));
		 
		 // Display Player info and cards on table
		 int i = 0;
		 for(; i < TOTAL_PLAYER; i++) {
			 cardPane[i] = new Pane();
			 Player playerinfo = player.get(playerno);
			 
			 // ------------ Display All Player name -------------
			 Label playername;
			 if(i%2==0) {
				 playername = new Label("Player - "+playerinfo.getName() + " Score : "+playerinfo.getScore());
				 cardPane[i].setPrefHeight(200);
				 playername.setLayoutY(10);
				 playername.setLayoutX(340);
			 }else {
				 playername = new Label("Player - "+playerinfo.getName() + "\nScore : "+playerinfo.getScore());
				 cardPane[i].setPrefWidth(200);
				 playername.setLayoutY(0);
				 playername.setLayoutX(20);
			 }

			 playername.setFont(PlayerFont);
			 playername.setTextFill(Color.WHITE);
			 cardPane[i].getChildren().add(playername);
			 // ----- End of Display All Player name ---------------
			 
			 if(i == 0) {
				 cardPane[i].setLayoutY(700);
				 				 
				 // Display Card available for player to select
				 if(PlayerTurn < TOTAL_PLAYER) {
					 for(int j = 0; j < playerinfo.getHandsize(); j++) {
						 Button cardbtn = new Button();
						 setBtnImageP(cardbtn,cardBackpng);
						 cardbtn.setText(""+j);
						 cardbtn.setLayoutX((j*50)+200);
						 cardbtn.setLayoutY(40);
						 cardPane[i].getChildren().add(cardbtn);
						 // ---- Hover -----
						 cardbtn.setOnMouseEntered(e->{
							 cardbtn.setLayoutY(20);
						 });
						 cardbtn.setOnMouseExited(e->{
							 cardbtn.setLayoutY(40);
						 });
						 // ---- End of Hover -----
						 cardbtn.setOnAction(e->{
							 playBtnSoundP(cardkMp3);
							 player.get(PlayerTurn).setPickno(Integer.parseInt(cardbtn.getText()));
							 int turn = (++PlayerTurn == TOTAL_PLAYER)?0:PlayerTurn;
							 if(PlayerTurn == TOTAL_PLAYER) {
								 winner = CardGame.compareCards(player);
							 }
							 // Refresh the Table
							 cardbtn.getScene().setRoot(table(cardPane, turn, Round));
						 });
					 }
					 
					 Pane.setBottom(cardPane[i]);
				 }else {
					 Pane.setBottom(displayCardonTable(cardPane[i], i, playerno));
				 }
				 
				 //Exit Game Button
				 Button exitbtn = new Button("Exit Game");
				 setBtnImageP(exitbtn,exitGamepng);
				 exitbtn.setLayoutX(800);
				 exitbtn.setLayoutY(60);
				 cardPane[i].getChildren().add(exitbtn);
				 exitbtn.setOnAction(e->{
					 playBtnSoundP(pressMp3);
					 playBgm (bgmMp3);
					 exitbtn.getScene().setRoot(getMenuScene());
				 });
				 
			 }else if(i == 1) {
				 Pane.setRight(displayCardonTable(cardPane[i], i, playerno));
			 }else if(i == 2) {
				 Pane.setTop(displayCardonTable(cardPane[i], i, playerno));
			 }else {
				 Pane.setLeft(displayCardonTable(cardPane[i], i, playerno));
			 }
			 playerno = (++playerno < TOTAL_PLAYER)?playerno:0;
		 }
		 
		 //Display none
		 for(; i < MAX_PLAYER; i++) {
			 cardPane[i] = new Pane();
			 if(i == 2) {
				 cardPane[i].setPrefHeight(200);
				 Pane.setTop(cardPane[i]);
			 }else if(i == 3){
				 cardPane[i].setPrefWidth(200);
				 Pane.setLeft(cardPane[i]);
			 }
		 }
		 
		 Player playerinfo = null;
		 Pane Cpane = new Pane();
		 Label RoundPane = new Label();
		 Button RoundBtn = new Button();
		 
		 RoundPane.setWrapText(true);
		 RoundPane.setFont(defaultFont);
		 RoundPane.setTextFill(Color.RED);
		 RoundPane.setTextAlignment(TextAlignment.CENTER);
		 
		 RoundBtn.setLayoutX(140);
		 RoundBtn.setLayoutY(250);

		 // If All player picked card
		 if(PlayerTurn == TOTAL_PLAYER && round < HAND_SIZE) {
			 // Compare All Players Score
			 CardGame.nextRound(player);
			 Round++;
			 PlayerTurn = 0;
			 // Display Winner
			 RoundPane.setText("Round " + round + "\nPlayer\n" + winner.getName() + "\n is the WINNER in this round!");
			 
			 RoundPane.setLayoutX(20);
			 RoundPane.setLayoutY(30);
			 
			 //Next Round Button
			 RoundBtn.setText("Next Round");
			 setBtnImageP(RoundBtn,nextroundpng);
			 Cpane.getChildren().add(RoundBtn);
			 
			 RoundBtn.setOnAction(e->{
				 playBtnSoundP(pressMp3);
				 RoundBtn.getScene().setRoot(table(cardPane, PlayerTurn, Round));
			 });
			 
		 }else if(PlayerTurn != TOTAL_PLAYER && round <= HAND_SIZE) {
			 // Display Round
			 
			 RoundPane.setLayoutX(192);
			 RoundPane.setLayoutY(30);
			 RoundPane.setText("Round " + round);
			 
		 }else {
			 
			 RoundPane.setLayoutY(40);
			 
			 playerinfo = CardGame.compareAllScore(player);
			 
			 // Display Winner
			 if(playerinfo!=null) {
				 playBtnSoundP(victorykMp3);
				 RoundPane.setLayoutX(100);
				 RoundPane.setText("Player\n" + playerinfo.getName() + "\n WINNER WINNER\nCHICKEN DINNER!!");
			 }else {
				 RoundPane.setLayoutX(140);
				 RoundPane.setText("No Winner\n in this game!");
			 } 
			 
			 //Play Again Button
			 RoundBtn.setText("Play Again");
			 setBtnImageP(RoundBtn,playagainpng);
			 Cpane.getChildren().add(RoundBtn);
			 
			 RoundBtn.setOnAction(e->{
				 playBtnSoundP(pressMp3);
				 RoundBtn.getScene().setRoot(getStartGameScene());
			 });
			 
		 }

		 Cpane.getChildren().add(RoundPane);
		 Pane.setCenter(Cpane);
		 
		 return Pane;
	 }
	 
	// Other Player card on the table
	 public Pane displayCardonTable(Pane cardPane, int i, int playerno) {
		 int j = 0;
		 if(player.get(playerno).getPickStatus()) j = 1;
		 for(; j < player.get(playerno).getHandsize(); j++) {
			 ImageView tmp = setImageP(cardBackpng);
			 if(i%2==0) {
				 // Top & Bottom
				 tmp.setLayoutX((j*30)+330);
				 tmp.setLayoutY(40);
			 }else {
				 // Left & Right
				 tmp.setLayoutX(60);
				 tmp.setLayoutY((j*30)+40);
				 tmp.setRotate(90);
			 }
			 cardPane.getChildren().add(tmp);
		 }
		 // if player had pick the card, display the selected card on table
		 if(player.get(playerno).getPickStatus()) {
			 ImageView pickcard = setImageP(CardsPath+"/"+player.get(playerno).getCardName()+".png",145,100);
			 if(i%2==0) {
				 // Top & Bottom
				 pickcard.setLayoutX(200);
				 pickcard.setLayoutY(40);
			 }else {
				 // Left & Right
				 pickcard.setLayoutX(60);
				 pickcard.setLayoutY(100);
			 }
			 cardPane.getChildren().add(pickcard);
		 }
		 return cardPane;
	 }
	 
	 public ImageView setImageP (String Filename){
		 try {
			 return new ImageView((new Image(getClass().getResourceAsStream(Filename))));
		 }catch(NullPointerException e) {
			 System.out.println("File : " + Filename + " Not Found");
			 return new ImageView();
		 }
	 }
	 
	 // Put image or set background image
	 public ImageView setImageP (String Filename, int height, int width){
		 try {
			 ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(Filename)));
			 imageView.setFitHeight(height);
			 imageView.setFitWidth(width);
			 return imageView;
		 }catch(NullPointerException e) {
			 System.out.println("File : " + Filename + " Not Found");
			 return new ImageView();
		 }
	 }
	 
	 public void setBtnImageP (Button btn,String Filename){
		 ImageView tmp = setImageP(Filename);
		 if(tmp.getImage() != null) {
			 btn.setGraphic(tmp);
			 btn.setStyle("-fx-background-color: transparent;-fx-text-fill: transparent;");
			 btn.setText("");
		 }
	 }
	 
	 // Button or any Sound Effect
	 public void playBtnSoundP (String Filename) {
		 try {
			 AudioClip audio = new AudioClip(getClass().getResource(Filename).toExternalForm());
			 audio.setVolume(0.5);
			 audio.stop();
	         audio.play();
		 }catch(NullPointerException e) {
			 System.out.println("File : " + Filename + " Not Found");
		 }
	 }
	 
	 // BGM Sound
	 public void playBgm (String Filename) {
		 try {
	         MediaPlayer audio = new MediaPlayer(new Media(getClass().getResource(Filename).toExternalForm()));
	         audio.setCycleCount(MediaPlayer.INDEFINITE);
	         audio.setVolume(0.5);
	         if(mediaView.getMediaPlayer()!=null) mediaView.getMediaPlayer().stop();
	         mediaView.setMediaPlayer(audio);
	         audio.play();
		 }catch(NullPointerException e) {
			 System.out.println("File : " + Filename + " Not Found");
		 }
	 }
	 
	 public static void main(String[] args) {
         launch(args);
     }
}
