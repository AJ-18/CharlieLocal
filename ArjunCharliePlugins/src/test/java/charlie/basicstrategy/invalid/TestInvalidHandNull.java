package charlie.basicstrategy.invalid;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.client.Advisor;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the scenario when the hand is null.
 */
public class TestInvalidHandNull {
    @Test
    public void test() {
        //Create a null hand
        Hand myHand = null;

        //Dealer up card: 2
        Card upCard = new Card(2, Card.Suit.HEARTS);

        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();

        Play advice = advisor.advise(myHand, upCard);
        // Assert that the advice is NONE due to invalid hand
        assertEquals(Play.NONE, advice);
    }
}
