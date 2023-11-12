package charlie.basicstrategy.invalid;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.client.Advisor;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the scenario when the hand is a blackjack.
 * This should return Play.NONE as a blackjack hand is not a scenario for basic strategy advice.
 */
public class TestInvalidHandBlackjack {
    @Test
    public void test() {
        // Create a blackjack hand and a valid up-card
        Hand myHand = new Hand(new Hid(Seat.YOU));

        //Put two cards in hand: Ace + King
        myHand.hit(new Card(Card.ACE, Card.Suit.CLUBS));
        myHand.hit(new Card(Card.KING, Card.Suit.DIAMONDS));

        // Dealer upcard: 6 (it doesn't matter in this case)
        Card upCard = new Card(6, Card.Suit.HEARTS);

        // Create advisor and get advice
        IAdvisor advisor = new Advisor();
        Play advice = advisor.advise(myHand, upCard);

        // Assert that the advice is NONE due to blackjack hand
        assertEquals(Play.NONE, advice);
    }
}