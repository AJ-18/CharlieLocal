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
 * Tests the scenario when the up-card is null.
 * This should return Play.NONE as a null up-card is invalid.
 */
public class TestInvalidUpCardNull {
    @Test
    public void test() {
        // Create a valid hand and a null up-card
        Hand myHand = new Hand(new Hid(Seat.YOU));

        // Put two cards in hand: 7+5
        myHand.hit(new Card(7, Card.Suit.CLUBS));
        myHand.hit(new Card(5, Card.Suit.DIAMONDS));

        // Dealer upcard: null
        Card upCard = null;

        // Create advisor and get advice
        IAdvisor advisor = new Advisor();
        Play advice = advisor.advise(myHand, upCard);

        // Assert that the advice is NONE due to invalid up-card
        assertEquals(Play.NONE, advice);
    }
}
