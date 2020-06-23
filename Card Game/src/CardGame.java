import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CardGame {
	public static final Scanner input = new Scanner(System.in);
	private static Deck deck = new Deck();
	
	//Instantiate Player
	private static ArrayList<Player> player = new ArrayList<Player>();
	private static final int HAND_SIZE = 7;
	private static final int MIN_PLAYER = 2;
	private static final int MAX_PLAYER = 4;
	private static int TOTAL_PLAYER = 2;
	
	public void startGame() {
		boolean gamecode = true;
		
		 do {
			 player.clear();	// Clear everything on repeat
			 gamecode = menu(); // False to repeat selection of Menu, True to start Game
			 if(gamecode) {
				 play();
			 }
		 }while(gamecode);
		 
		 // Close Scanner
		 input.close();
	 }
	 
	private static boolean menu() {
		 
		// instantiate
		 boolean invalid = true; // default : repeat
		 int action = 0;
		 String playername = "";
		 		 
		 while(invalid) {
			 
			 System.out.println("Card Game\n Player Options\n"
					 + "1. Start Game\n"
					 + "2. Exit Game");
			 System.out.print("Please provide your option : ");
			 
			 try {
				 action = input.nextInt();
				 input.nextLine();	// Skip input after first input
				 
				// ---------- Number of Player and Player name ----------- 
				 if(action == 1) {
					 do {
						 System.out.print("Provide the Number of Player (2-4) : ");
						 TOTAL_PLAYER = input.nextInt();
						 input.nextLine();	// Skip input after first input
						 
						 if(TOTAL_PLAYER >= MIN_PLAYER && TOTAL_PLAYER <= MAX_PLAYER) {	// if number within range
							 for(int i = 1; i <= TOTAL_PLAYER; i++) {
								 do {
									 System.out.print("Player " + i + " Name : ");
									 playername = input.next();
									 input.nextLine();	// Skip input after first input
									 
									 if(playername.trim().length() == 0) {	// If no enter player name
										 System.out.println("Please enter player name.");
									 }else {
										 invalid = false;
										 if(player.size() > 0) { // If First Player is defined
											 // Compare all player name, find Duplicate Name
											 invalid = checkAddPlayerName(player,playername,i);
											 if(invalid) System.out.println("Name \"" + playername + "\" had been used, try different name.");
											 
											 // If no Duplicate Name
											 if(!invalid) {
												 player.add(new Player(playername));
											 }
										 }else {
											 // First Player Name
											 player.add(new Player(playername));
										 }
										 
									 }
								 } while(invalid);
							 }
						 }else {
							 System.out.println("\nPlease enter between 2 - 4 players.\n");
						 }
					 }while(invalid);
					// --------- End of Number of Player and Player name ----------- 
					 
					 System.out.println("\nGame Started.....");
					 return true; // Start Game
					 
				 }else if(action == 2){	// Option 2 - Exit Game
					 System.out.println("\nGame Exiting.....\n");
					 return false; // End Game
					 
				 }else { // Number of Players out of range
					 System.out.println("\nPlease enter valid number.\n");
				 }
			 }catch(InputMismatchException e) {
				 System.out.println("\nPlease enter a valid input.\n");
				 input.nextLine();
			 }
		 }
		 return true;	// Repeat Menu
	 }
	 
	 private static void play() {
		 
		// instantiate
		 boolean invalid = true; // Default : repeat
		 int action = 0, cardno = 0;
		 
		 // Create deck of cards 
		 deck.fillDeck();
		 deck.shuffle();
		 
		 // ------------ Draw and Select Cards -------------------- 
		 for(int k = 0; k < HAND_SIZE; k ++) {
			 System.out.println("\n--------- Round " + (k+1) + " --------------");
			 for(int i = 1; i <= TOTAL_PLAYER; i++) {
				 invalid = true; // Default : repeat Player menu for each player
				 Player playerinfo = player.get(i-1);
				 if(k==0) playerinfo.draw(deck,HAND_SIZE); // if first round - Player Draw Cards
				 
				 do {
					 System.out.print("\nPlayer " + i + " - " + playerinfo.getName() + 
							 "\n1. display Cards available.\n2. Stop Game." +
							 "\nPlease provide your option : ");
					 try {
						 action = input.nextInt();
						 input.nextLine();	// Skip input after first input
						 
						 // Display Cards available
						 if(action == 1) {
							 for(int x = 1; x <= playerinfo.getHandsize(); x++) {
								 System.out.print(x + " "); // Print total Cards draw
							 }
							 
							 // Select Cards available
							 do {
								 System.out.print("\nSelect your card number : ");
								 cardno = input.nextInt();	// Skip input after first input
								 input.nextLine();
								 
								 // if card number within range
								 if(cardno > 0 && cardno <= playerinfo.getHandsize()) {
									 invalid = false;	// No repeat for player menu and selection
								 	 playerinfo.setPickno(cardno-1);	// Set selected card number
								 	 playerinfo.printPickcard();	// Print selected card suit and rank
								 }else {
									 invalid = true; // repeat selection of card
									 System.out.print("\nPlease enter valid number.\n");
								 }
							 }while(invalid);
							 
						 }else if(action == 2){ // Stop Game
							 System.out.println("\nReturn to Menu.\n");
							 return;
							 
						 }else { // Number not in option
							 System.out.print("\nPlease enter valid number.\n");
						 }
					 }catch(InputMismatchException e) {
						 System.out.println("\nPlease enter a valid input.");
						 input.nextLine();
					 }
				 }while(invalid);
			 }
			 
			 // ------------ End of Draw and Select Cards -------------------- 
				
			 compareCards(player);
			 nextRound(player);
			
			 // Print All Players Score
			 for(int i = 0; i < player.size(); i++) {
				 System.out.print("\nPlayer " + (i+1) + " Score -> " + player.get(i).getScore());
			 }
			 System.out.println("\n");
			 
		 }
		 
		 Player tmp = compareAllScore(player);
		 if(tmp!=null)
			 System.out.println("Player - " + tmp.getName() + " is the Winner!\n");
		 else {
			 System.out.println("No Winner in this game!\n");
		 }
		 
	 }
	 
	 public static boolean checkAddPlayerName(ArrayList<Player> player,String playername, int pno) {
		 int count = 1;
		 // Compare all player name, find Duplicate Name
		 for(Player p : player) {
			if(p.compareName(playername)) {
				if(pno!=count) {
					return true;	// Found duplicate
				}
		 	}
			count++;
		 }
		 return false;
	 }
	 
	// Compare All Players Cards
	 public static Player compareCards(ArrayList<Player> player) {
		 Player p1 =  player.get(0);
		 for(int i = 1; i < player.size(); i++) {
			 Player p2 = player.get(i);
			 p1 = p1.compareCards(p2);	// return highest Player get rank and suit of card 
		 }
		 p1.addScore();
		 return p1;
	 }
	 
	 // Next Round reset
	 public static void nextRound(ArrayList<Player> player) {
		 for(int i = 0; i < player.size(); i++) {
			 player.get(i).removeCard();
			 player.get(i).nextRound();
		 }
	 }
	 
	// Last Round Compare All Players Score
	 public static Player compareAllScore(ArrayList<Player> player) {
		 int max = 0;
		 Player p1 =  player.get(0);
		 for(int i = 1; i < player.size(); i++) {
			 Player p2 = player.get(i);
			 if(p1.getScore() < p2.getScore()) {
				p1 = p2;
			}else if(p1.getScore()==p2.getScore()){
				max = p1.getScore();
			}
		 }
		 if(max!=p1.getScore())
			 return p1;
		 else
			 return null;
	 }
	 
	 public static int[] getPlayerSetting() {
		 int[] set = {HAND_SIZE,MIN_PLAYER,MAX_PLAYER};
		 return set;
	 }
}
