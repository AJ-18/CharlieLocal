package charlie.basicstrategy.section2;

import charlie.client.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests my 11 vs dealer ACE which should be HIT.
 * @author AJ - Arjun Suresh
 */
public class Test01_5_7 {
    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));

        // Put two cards in hand: 7+4
        Card card1 = new Card(7, Card.Suit.CLUBS);
        Card card2 = new Card(4, Card.Suit.DIAMONDS);

        myHand.hit(card1);
        myHand.hit(card2);

        // Create dealer up card: ACE
        Card upCard = new Card(1, Card.Suit.HEARTS);

        // Construct advisor and test it.
        IAdvisor advisor = new Advisor();

        Play advice = advisor.advise(myHand, upCard);
        // Validate the advice.
        assertEquals(advice, Play.HIT);
    }
}
