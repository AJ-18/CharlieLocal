/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package charlie.sidebet.rule;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import org.apache.log4j.Logger;

/**
 * This class implements the side bet rule for Super 7, Royal Match, and Exactly13.
 * @author Ron.Coleman
 */
public class SideBetRule implements ISideBetRule {
    private final Logger LOG = Logger.getLogger(SideBetRule.class);

    private final Double PAYOFF_SUPER7 = 3.0;
    private final Double PAYOFF_ROYAL_MATCH = 25.0;
    private final Double PAYOFF_EXACTLY_13 = 1.0;
    public boolean Super7 = false;
    public boolean RoyalMatched = false;
    public boolean Exactly13 = false;

    /**
     * Apply rule to the hand and return the payout if the rule matches
     * and the negative bet if the rule does not match.
     * @param hand Hand to analyze.
     * @return Payout amount: <0 lose, >0 win, =0 no bet
     */
    @Override
    public double apply(Hand hand) {
        Double bet = hand.getHid().getSideAmt();
        LOG.info("side bet amount = " + bet);

        if (bet == 0) {
            return 0.0;
        }

        LOG.info("side bet rule applying hand = " + hand);

        Card card = hand.getCard(0);

        if (card.getRank() == 7) {
            LOG.info("side bet SUPER 7 matches");
            Super7 = true;
            return bet * PAYOFF_SUPER7;
        }

        // Check for the suited Royal Match
        if (isSuitedRoyalMatch(hand)) {
            LOG.info("side bet SUITED ROYAL MATCH matches");
            RoyalMatched = true;
            return bet * PAYOFF_ROYAL_MATCH;
        }

        // Check for Exactly 13
        if (isExactly13(hand)) {
            LOG.info("side bet EXACTLY 13 matches");
            Exactly13 = true;
            return bet * PAYOFF_EXACTLY_13;
        }

        LOG.info("side bet rule no match");

        return -bet;
    }


    /**
     * Checks if the hand has a Suited Royal Match.
     * @param hand Hand to check.
     * @return True if Suited Royal Match, false otherwise.
     */
    private boolean isSuitedRoyalMatch(Hand hand) {
        if (hand.size() >= 2) {
            Card firstCard = hand.getCard(0);
            Card secondCard = hand.getCard(1);

            if(firstCard.getSuit() == secondCard.getSuit()) {
                if(firstCard.getRank() == Card.KING && secondCard.getRank() == Card.QUEEN ) {
                    return true;
                }
                else if(firstCard.getRank() == Card.QUEEN && secondCard.getRank() == Card.KING ) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the hand has Exactly 13.
     * @param hand Hand to check.
     * @return True if Exactly 13, false otherwise.
     */
    private boolean isExactly13(Hand hand) {
        if (hand.size() >= 2) {
            Card firstCard = hand.getCard(0);
            Card secondCard = hand.getCard(1);

            return (firstCard.getRank() + secondCard.getRank() == 13);
        }

        return false;
    }

}