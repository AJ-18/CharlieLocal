package charlie.sim;

import charlie.card.Shoe01;
import charlie.card.Card;


/**
 * Monte Carlo simulation to estimate the probability of winning the "Exactly 13" side bet in blackjack.
 */
public class Exactly13 {
    public static void main(String[] args) {
        int count = 0;  // Initialize a counter to keep track of the number of Exactly 13 wins.
        int N = 100000; // Number of simulations to run.

        for (int i = 0; i < N; i++) {
            // Initialize the shoe for a new game.
            Shoe01 shoe = new Shoe01();
            shoe.init();

            // Draw two cards for the player.
            Card card1 = shoe.next();
            Card card2 = shoe.next();

            // Check if the two cards have a total value of exactly 13.
            if (isExactly13(card1, card2)) {
                // Increment the count if the condition is met.
                count++;
            }
        }

        // Calculate the probability of winning Exactly 13.
        double P = (double) count / N;

        // Calculate the odds of winning Exactly 13.
        double odds = (1 - P) / P;

        // Format the output in the specified format.
        String output = String.format("Exactly 13 prob = %.6f odds = %d:1", P, (int) odds);

        // Display the output on the console.
        System.out.println(output);

    }

    // Check if two cards have a total value of exactly 13.
    private static boolean isExactly13(Card card1, Card card2) {
        int total = card1.value() + card2.value();
        return total == 13;
    }

}
