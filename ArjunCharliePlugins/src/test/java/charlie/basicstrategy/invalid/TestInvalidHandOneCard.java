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
 * Tests the scenario when the hand has only one card.
 * This should return Play.NONE as a hand with one card is not a valid scenario for basic strategy advice.
 */
public class TestInvalidHandOneCard {
    @Test
    public void test() {
        // Create a hand with only one card and a valid up-card
        Hand myHand = new Hand(new Hid(Seat.YOU));

        // Put one cards in hand: 7 + ?
        myHand.hit(new Card(7, Card.Suit.CLUBS));

        // Dealer upcard: 4
        Card upCard = new Card(4, Card.Suit.HEARTS);

        // Create advisor and get advice
        IAdvisor advisor = new Advisor();
        Play advice = advisor.advise(myHand, upCard);

        // Assert that the advice is NONE due to one-card hand
        assertEquals(Play.NONE, advice);
    }
}
