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
 * Tests the scenario when the hand has no cards.
 * This should return Play.NONE as a hand with no cards is not a valid scenario for basic strategy advice.
 */
public class TestInvalidHandNoCards {
    @Test
    public void test() {
        // Create an empty hand and a valid up-card
        Hand myHand = new Hand(new Hid(Seat.YOU));

        // No hand cards

        // Dealer upcard: 5
        Card upCard = new Card(5, Card.Suit.HEARTS);

        // Create advisor and get advice
        IAdvisor advisor = new Advisor();
        Play advice = advisor.advise(myHand, upCard);

        // Assert that the advice is NONE due to empty hand
        assertEquals(Play.NONE, advice);
    }
}
