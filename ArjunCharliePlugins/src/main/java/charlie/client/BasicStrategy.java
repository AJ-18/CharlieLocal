
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
        // Find the non-Ace card
        Card nonAceCard = hand.getCard(0).getRank() == Card.ACE ? hand.getCard(1) : hand.getCard(0);

        // Compute the row index based on the non-Ace card's rank.
        // The array starts at A,2 hence we subtract 2 to align with the array index (A,2 would be at index 0).
        int rowIndex = nonAceCard.getRank() - 2;

        // Check if the rowIndex is within the bounds of the section3Rules array
        if(rowIndex < 0 || rowIndex >= section3Rules.length) {
            return Play.NONE;
        }

        // Retrieve the row from the section3Rules matrix
        Play[] row = section3Rules[rowIndex];

        // Determine the column index based on the dealer's up-card
        // Subtract 2 from the rank to align with the array index (2 would be at index 0).
        int colIndex = upCard.getRank() - 2;

        // Correct for face cards being treated as 10
        if(upCard.isFace()) {
            colIndex = 10 - 2; // Index 8 for face cards (T)
        }

        // Correct for Ace being the last column
        else if(upCard.isAce()) {
            colIndex = 9; // Index 9 for Aces
        }

        // Retrieve the play from the correct column in the retrieved row
        Play play = row[colIndex];

        return play;
    }

    /**
     * Does section 4 processing of the basic strategy, pairs [(A,A),(2,2), etc..] (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     */
    protected Play doSection4(Hand hand, Card upCard) {
        // Since hand is a pair, we can just take the rank of the first card.
        int pairCardRank = hand.getCard(0).getRank();

        int rowIndex;

        // Determine the row index based on the pair card's rank.
        if(pairCardRank == Card.ACE) {
            // A pair of Aces is at the end of the array (index 9).
            rowIndex = 9;
        } else {
            // The array starts at 10,10, so we subtract 2 to align with the array index (10,10 would be at index 0).
            rowIndex = pairCardRank - 2;
        }

        // Check if the rowIndex is within the bounds of the section4Rules array
        if(rowIndex < 0 || rowIndex >= section4Rules.length) {
            return Play.NONE;
        }

        // Retrieve the row from the section4Rules matrix
        Play[] row = section4Rules[rowIndex];

        // Initialize colIndex to -1 to signify an invalid index.
        int colIndex;

        // Determine the column index based on the dealer's up-card
        if(upCard.isFace()) {
            // Face cards (J, Q, K) are treated as 10 and have index 8.
            colIndex = 10-2;
        } else if(upCard.isAce()) {
            // Ace is in the last column with index 9.
            colIndex = 9;
        } else {
            // For numerical cards, subtract 2 from the rank to align with the array index (2 would be at index 0).
            colIndex = upCard.getRank() - 2;
        }

        // Check if the colIndex is within the bounds of the row array
        if(colIndex < 0 || colIndex >= row.length) {
            return Play.NONE;
        }

        // Retrieve the play from the correct column in the retrieved row
        Play play = row[colIndex];

        return play;
    }

    protected boolean isValid(Hand hand,Card upCard) {
        return true;
    }
}
