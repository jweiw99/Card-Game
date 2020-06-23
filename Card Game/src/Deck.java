import java.util.ArrayList;

public class Deck {
	 public static final int NUMBER_OF_SUITS = 4;
	 public static final int NUMBER_OF_CARDS_IN_SUIT = 13;
	 private static final int DECK_SIZE = NUMBER_OF_SUITS * NUMBER_OF_CARDS_IN_SUIT;
	 private static Card[] deck = new Card[DECK_SIZE];
	 private static int deckPosition = 0;
	 
	 // fill 52 cards into deck, each card contain suit and rank
	 public void fillDeck() {
		 deckPosition = 0;	// reset each session of game to prevent over 52 from draw cards
		 int counter = 0;
		 for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
			 for (int rank = 0; rank < NUMBER_OF_CARDS_IN_SUIT; rank++) {
				 deck[counter] = new Card();
				 deck[counter].setCardNo(0);
				 deck[counter].setsuit(suit);
				 deck[counter].setrank(rank);
				 counter++;
			 }
		 }
	 }
	 
	 // Shuffle Deck of Cards
	 public void shuffle(){ 
		 for (int i = 0; i < DECK_SIZE; i++){ 
			 int index = (int)(Math.random() * DECK_SIZE);
			 Card temp = deck[i]; 
			 deck[i] = deck[index]; 
			 deck[index] = temp; 
		 } 
	 } 
	 
	 // Draw Cards
	 public ArrayList<Card> deal(int size){ 
		ArrayList<Card> hand = new ArrayList<Card>(); 
		int curPos = deckPosition;
		// each player draw next 7 available Cards
	 	for (; deckPosition < (curPos + size); deckPosition++){ 
	 		deck[deckPosition].setCardNo((deckPosition % size)+1);
	 		hand.add(deck[deckPosition]); 
	 	} 
		return hand; 
	}

}
