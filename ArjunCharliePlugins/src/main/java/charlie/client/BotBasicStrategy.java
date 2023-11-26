package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

public class BotBasicStrategy extends BasicStrategy {
    @Override
    public Play getPlay(Hand hand, Card upCard) {
        // First, get the basic strategy play
        Play play = super.getPlay(hand, upCard);

        // If it's not a split, return the suggested play
        if (play != Play.SPLIT)
            return play;

        // If it's a pair of 2s, return HIT instead of SPLIT
        if (hand.getValue() == 4) {
            return Play.HIT;
        }

        // If the hand value is greater than 11, re-lookup in section 1
        if (hand.getValue() > 11) {
            return doSection1(hand, upCard);
        }

        // If the hand value is between 5 and 11, re-lookup in section 2
        // Check if hand has more than two cards, then you can only hit/stay. (Cannot Double Down)
        if (hand.getValue() >= 5 && hand.getValue() <= 11) {
            return doSection2(hand, upCard);
        }

        // This assertion is to catch any unexpected scenarios
        //assert false : "Bad play";

        // If the assertion is disabled, return a default safe play
        return Play.STAY;
    }
}