
package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 * This class is an incomplete implementation of the Basic Strategy.
 * <p>It is table-driven, missing most of the rules and all validation.
 * @author AJ- Arjun Suresh
 */
public class BasicStrategy {
    // These help make table formatting compact to look like the pocket card.
    public final static Play P = Play.SPLIT;
    public final static Play H = Play.HIT;
    public final static Play S = Play.STAY;
    public final static Play D = Play.DOUBLE_DOWN;
    
    /** Rules for section 1; see Instructional Services (2000) pocket card */
    Play[][] section1Rules = {
        /*         2  3  4  5  6  7  8  9  T  A  */
        /* 21 */ { S, S, S, S, S, S, S, S, S, S },
        /* 20 */ { S, S, S, S, S, S, S, S, S, S },
        /* 19 */ { S, S, S, S, S, S, S, S, S, S },
        /* 18 */ { S, S, S, S, S, S, S, S, S, S },
        /* 17 */ { S, S, S, S, S, S, S, S, S, S },
        /* 16 */ { S, S, S, S, S, H, H, H, H, H },
        /* 15 */ { S, S, S, S, S, H, H, H, H, H },
        /* 14 */ { S, S, S, S, S, H, H, H, H, H },
        /* 13 */ { S, S, S, S, S, H, H, H, H, H },
        /* 12 */ { H, H, S, S, S, H, H, H, H, H }
    };

    /** Rules for section 2; see Instructional Services (2000) pocket card */
    Play[][] section2Rules = {
            /*         2  3  4  5  6  7  8  9  T  A  */
            /* 11 */ { D, D, D, D, D, D, D, D, D, H },
            /* 10 */ { D, D, D, D, D, D, D, D, H, H },
            /* 09 */ { H, D, D, D, D, H, H, H, H, H },
            /* 08 */ { H, H, H, H, H, H, H, H, H, H },
            /* 07 */ { H, H, H, H, H, H, H, H, H, H },
            /* 06 */ { H, H, H, H, H, H, H, H, H, H },
            /* 05 */ { H, H, H, H, H, H, H, H, H, H }
    };

    /** Rules for section 3; see Instructional Services (2000) pocket card */
    Play[][] section3Rules = {
            /*          2  3  4  5  6  7  8  9  T  A  */
            /* A10 */ { S, S, S, S, S, S, S, S, S, S },
            /* A 9 */ { S, S, S, S, S, S, S, S, S, S },
            /* A 8 */ { S, S, S, S, S, S, S, S, S, S },
            /* A 7 */ { S, D, D, D, D, S, S, H, H, H },
            /* A 6 */ { H, D, D, D, D, H, H, H, H, H },
            /* A 5 */ { H, H, D, D, D, H, H, H, H, H },
            /* A 4 */ { H, H, D, D, D, H, H, H, H, H },
            /* A 3 */ { H, H, H, D, D, H, H, H, H, H },
            /* A 2 */ { H, H, H, D, D, H, H, H, H, H }
    };

    /** Rules for section 4; see Instructional Services (2000) pocket card */
    Play[][] section4Rules = {
            /*            2  3  4  5  6  7  8  9  T  A  */
            /* 10,10 */ { S, S, S, S, S, S, S, S, S, S },
            /*  9, 9 */ { P, P, P, P, P, S, P, P, S, S },
            /*  8, 8 */ { P, P, P, P, P, P, P, P, P, P },
            /*  7, 7 */ { P, P, P, P, P, P, H, H, H, H },
            /*  6, 6 */ { P, P, P, P, P, H, H, H, H, H },
            /*  5, 5 */ { D, D, D, D, D, D, D, D, H, H },
            /*  4, 4 */ { H, H, H, P, P, H, H, H, H, H },
            /*  3, 3 */ { P, P, P, P, P, P, H, H, H, H },
            /*  2, 2 */ { P, P, P, P, P, P, H, H, H, H },
            /*  A, A */ { P, P, P, P, P, P, P, P, P, P }
    };
    
    /**
     * Gets the play for player's hand vs. dealer up-card.
     * @param hand Hand player hand
     * @param upCard Dealer up-card
     * @return Play based on basic strategy
     */
    public Play getPlay(Hand hand, Card upCard) {
        if(!isValid(hand,upCard))
            return Play.NONE;

        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        
        if(hand.isPair()) {
            return doSection4(hand,upCard);
        }
        else if(hand.size() == 2 && (card1.getRank() == Card.ACE || card2.getRank() == Card.ACE)) {
            return doSection3(hand,upCard);
        }
        else if(hand.getValue() >=5 && hand.getValue() < 12) {
            return doSection2(hand,upCard);
        }
        else if(hand.getValue() >= 12)
            return doSection1(hand,upCard);

        return Play.NONE;
    }

    protected boolean isValid(Hand hand, Card upCard) {
        return isValidHand(hand) && isValidCard(upCard);
    }

    // Check for invalid cases involving the hand
    protected boolean isValidHand(Hand hand) {
        if (hand == null || hand.size() == 0 || hand.size() < 2 || hand.isBlackjack()) {
            return false;
        }
        return true;
    }

    // Check for invalid cases involving the dealer's upCard
    protected boolean isValidCard(Card upCard) {
        return upCard != null && upCard.value() >= 0;
    }

    /**
     * Does section 1 processing of the basic strategy, 12-21 (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection1(Hand hand, Card upCard) {
        int value = hand.getValue();
        
//        // Section 1 currently only supports hands >= 20 (see above).
//        if(value < 20)
//            return Play.NONE;
        
        ////// Get the row in the table.
        
        // Subtract 21 since the player's hand starts at 21 and we're working
        // our way down through section 1 from index 0.
        int rowIndex = 21 - value;
        
        Play[] row = section1Rules[rowIndex];
        
        ////// Get the column in the table
        
        // Subtract 2 since the dealer's up-card starts at 2
        int colIndex = upCard.getRank() - 2;
         
        if(upCard.isFace())
            colIndex = 10 - 2;

        // Ace is the 10th card (index 9)
        else if(upCard.isAce())
            colIndex = 9;
        
        
        // At this row, col we have the correct play defined.
        Play play = row[colIndex];
        
        return play;
    }


    /**
     * Does section 2 processing of the basic strategy, 5-11 (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection2(Hand hand, Card upCard) {
        int value = hand.getValue();

        // Subtract 21 since the player's hand starts at 21 and we're working
        // our way down through section 1 from index 0.
        int rowIndex = 11 - value;

        Play[] row = section2Rules[rowIndex];

        ////// Get the column in the table

        // Subtract 2 since the dealer's up-card starts at 2
        int colIndex = upCard.getRank() - 2;

        if(upCard.isFace())
            colIndex = 10 - 2;

            // Ace is the 10th card (index 9)
        else if(upCard.isAce())
            colIndex = 9;


        // At this row, col we have the correct play defined.
        Play play = row[colIndex];

        return play;
    }

    /**
     * Does section 3 processing of the basic strategy, Ace & 2-10 (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection3(Hand hand, Card upCard) {
        Card nonAceCard;
        if (hand.getCard(0).getRank() == Card.ACE) {
            nonAceCard = hand.getCard(1);
        } else {
            nonAceCard = hand.getCard(0);
        }
        int nonAceRank = nonAceCard.getRank();

        // Calculate rowIndex for non-Ace card. "A,10" is at index 0 and "A,2" is at index 8.
        // We need to invert the order, so "A,2" corresponds to 0 and "A,10" to 8.
        int rowIndex = 10 - nonAceRank;

        // Bounds check for row index
        if (rowIndex < 0 || rowIndex >= section3Rules.length) {
            return Play.NONE;
        }

        Play[] row = section3Rules[rowIndex];

        // Determine column index based on dealer's up-card
        int colIndex;
        if (upCard.isFace()) {
            // Face cards (J, Q, K) are treated as 10
            colIndex = 8;
        } else if (upCard.isAce()) {
            // Ace is in the last column
            colIndex = 9;
        } else {
            // For numerical cards
            colIndex = upCard.getRank() - 2;
        }

        // Bounds check for column index
        if (colIndex < 0 || colIndex >= row.length) {
            return Play.NONE;
        }

        return row[colIndex];
    }

    /**
     * Does section 4 processing of the basic strategy, pairs [(A,A),(2,2), etc..] (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection4(Hand hand, Card upCard) {
        // Since hand is a pair, we can just take the rank of the first card.
        int pairCardRank = hand.getCard(0).getRank();

        // Determine the row index. The array starts with "10,10" at index 0 and ends with "A,A" at index 9.
        int rowIndex;
        if (pairCardRank == Card.ACE) {
            // "A,A" is at the last index (9).
            rowIndex = 9;
        } else {
            // Other pairs follow in descending order.
            rowIndex = 10 - pairCardRank;
        }

        // Bounds check for row index
        if (rowIndex < 0 || rowIndex >= section4Rules.length) {
            return Play.NONE;
        }

        Play[] row = section4Rules[rowIndex];

        // Determine column index based on dealer's up-card
        int colIndex;
        if (upCard.isFace()) {
            // Face cards (J, Q, K) are treated as 10.
            colIndex = 8;
        } else if (upCard.isAce()) {
            // Ace is in the last column.
            colIndex = 9;
        } else {
            // For numerical cards.
            colIndex = upCard.getRank() - 2;
        }

        // Bounds check for column index
        if (colIndex < 0 || colIndex >= row.length) {
            return Play.NONE;
        }

        return row[colIndex];
    }

}
