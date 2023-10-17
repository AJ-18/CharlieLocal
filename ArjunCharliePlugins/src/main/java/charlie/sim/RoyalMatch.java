package charlie.sim;

import charlie.card.Shoe01;
import charlie.card.Card;


/**
 * Estimates the probability of winning the Royal Match side bet in blackjack
 * through a Monte Carlo simulation using the provided `charlie.card.Shoe01` and `charlie.card.Card` classes.
 */
public class RoyalMatch {

    public static void main(String[] args) {
        int count = 0; // Initialize a counter to keep track of the number of Royal Matches found.
        int N = 100000; // Number of games to simulate.

        for (int i = 0; i < N; i++) {
            // Initialize the shoe
            Shoe01 shoe = new Shoe01();
            shoe.init(); // Initialize the shoe with cards and shuffle it.

            // Draw cards from the shoe
            Card card1 = shoe.next(); // Draw the first card for YOU.
            Card card2 = shoe.next(); // Draw the second card for DEALER.
            Card card3 = shoe.next(); // Draw the third card for YOU.

            // Check if YOU have a Royal Match
            if (isRoyalMatch(card1, card3)) {
                count++; // Increment the count if a Royal Match is found.
            }
        }

        double P = (double) count / N; // Calculate the probability of winning the Royal Match.
        double odds = (1 - P) / P; // Calculate the odds of winning the Royal Match.

        // Format output
        String output = String.format("Royal Match prob = %.6f odds = %d:1", P, (int) odds);

        // Display output
        System.out.println(output);

    }


    // Check if two cards form a Royal Match
    private static boolean isRoyalMatch(Card card1, Card card2) {
        int rank1 = card1.getRank();
        int rank2 = card2.getRank();
        Card.Suit suit1 = card1.getSuit();
        Card.Suit suit2 = card2.getSuit();

        // Check if both cards have a rank of 12 (Q), or 13 (K) and have the same suit.
        return (isRoyalCard(rank1) && isRoyalCard(rank2) && suit1 == suit2);
    }

    // Check if the cards are royal (Queen or King)
    private static boolean isRoyalCard(int rank) {
        return (rank >= Card.QUEEN && rank <= Card.KING);
    }
}



