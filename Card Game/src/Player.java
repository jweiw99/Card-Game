import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> hand;
	private int pickno;
	private int totalScore;
	private boolean picked;
	
	// Constructor
	public Player(String name) {
		setName(name);
		hand = null;
		pickno = 0;
		totalScore = 0;
		picked = false;
	}
	
	public String getName() {
        return name;
    }
	
	public int getScore() {
        return totalScore;
    }
	
	public boolean getPickStatus() {
        return picked;
    }
	
	public int getHandsize() {
		return hand.size();
	}
	
	public int getCardNo(int no) {
		return hand.get(no).getCardNo();
	}
	
	public void setName(String name) {
       this.name = name;
    }
	
	public void setPickno(int pickno) {
		picked = true;
        this.pickno = pickno;
    }
	
	public void addScore() {
		totalScore++;
	}
	
	public void removeCard() {
		hand.remove(pickno);
	}
	
	public void resetPlayer() {
		hand = null;
		pickno = 0;
		totalScore = 0;
		picked = false;
	}
	
	public void nextRound() {
		pickno = 0;
		picked = false;
	}
	
	// Draw Cards
	public void draw(Deck deck, int size) { 
		hand = deck.deal(size); 
	} 
	
	// Print Selected Card Details
	public void printPickcard() { 
		hand.get(pickno).printCard();
	}
	
	// Get Card name for import card image
	public String getCardName() {
		return hand.get(pickno).getrank()+hand.get(pickno).getsuit();
	}
	
	// Compare Player Name
	public boolean compareName(String name) {
        return this.name.equalsIgnoreCase(name);
    } 
	
	// Compare Player Score
	public Player compareCards(Player p) {
		int result = hand.get(pickno).compareTo(p.hand.get(p.pickno)); // return -1 = smaller or 1 = bigger than other player
		if(result > 0) {
			return this;
		}else {
			return p;
		}
	}
	
}
