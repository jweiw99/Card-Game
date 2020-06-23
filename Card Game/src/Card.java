
public class Card implements Comparable<Card>{
	private int cardno;
	private int suit; 
	private int rank; 
	private static final String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};	// Its Follow Chinese card game rule, From lowest to highest
	private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; // From lowest to highest
 
	@Override 
	public int compareTo(Card o){ 
		if (this.rank == o.rank){	// if rank is same then compare suit
			if(this.suit > o.suit) {
				return 1;
			}else {
				return -1;
			}
		} else if (this.rank > o.rank){
			return 1;
		} else {
			return -1;
		}
	}
	
	public void setsuit(int suit) {
		this.suit = suit;
	}
	
	public void setrank(int rank) {
		this.rank = rank;
	}
	
	public void setCardNo(int cardno) {
		this.cardno = cardno;
	}

	public int getCardNo() {
		return cardno;
	}
	
	public String getsuit() {
		return suits[suit];
	}
	
	public String getrank() {
		return ranks[rank];
	}
	
	public void printCard() { 
		System.out.println("\nCard Selected : \nCard Number = " + ranks[rank] + "\nCard Type = " + suits[suit]);
	}
}
