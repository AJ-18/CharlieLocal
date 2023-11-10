
package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

/**
 * This class gives play advice.
 * @author AJ - Arjun Suresh
 */
public class Advisor implements IAdvisor {
    protected BasicStrategy bs = new BasicStrategy();

    /**
     * Gets advice using the Basic Strategy.
     * @param myHand Player hand
     * @param upCard Dealer up-card
     * @return Play advice
     */
    @Override
    public Play advise(Hand myHand, Card upCard) {
        return bs.getPlay(myHand, upCard);

    }


}
