package charlie.server;

import charlie.card.Shoe01;
import charlie.card.Card;
import java.util.ArrayList;
import java.util.List; // Added import for List

public class DebugShoe01 extends Shoe01 {
    public void init() {
        super.init();
        List<Card> myCards = new ArrayList<>();

        // Add custom cards
        // Player's 1st card, then dealer's card, then player's 2nd, then dealer's upcard, then additional cards
        ////// CASE 01:
        myCards.add(new Card(Card.ACE, Card.Suit.SPADES)); // Player's 1st

        myCards.add(new Card(Card.KING, Card.Suit.CLUBS)); // Dealer's 1st

        myCards.add(new Card(Card.ACE, Card.Suit.HEARTS)); // Player's 2nd

        myCards.add(new Card(3,Card.Suit.DIAMONDS)); // Dealer's upCard

        myCards.add(new Card(Card.KING, Card.Suit.HEARTS)); // Additional
        myCards.add(new Card(10,Card.Suit.HEARTS)); // Additional

        myCards.add(new Card(9,Card.Suit.HEARTS)); // Dealer's Additional

        ////// CASE 02:

        // Front load my cards
        for (int k = 0; k < myCards.size(); k++) {
            Card myCard = myCards.get(k);
            super.cards.add(k, myCard); // Assuming 'cards' is accessible from Shoe01
        }
    }

    @Override
    public boolean shuffleNeeded() {
        return false;
    }
}