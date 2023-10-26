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

package charlie.sidebet.test;

import charlie.card.Card;

/**
 * A unit test shoe
 * @author Ron.Coleman
 */
public class Shoe extends charlie.card.Shoe {
	/**
	 * Initializes the shoe.
	 */
    @Override
    public void init() {
        cards.clear();

        //Test case #1
        cards.add(new Card(7,Card.Suit.HEARTS)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); //Dealers Downcard

        cards.add(new Card(9,Card.Suit.SPADES)); //Player's 2nd Card
        cards.add(new Card(9,Card.Suit.DIAMONDS)); //Dealer's Upcard

        cards.add(new Card(3,Card.Suit.CLUBS)); //Player's Card on hit

        // Test case #2
        cards.add(new Card(7,Card.Suit.HEARTS)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); //Dealers Downcard

        cards.add(new Card(9,Card.Suit.SPADES)); //Player's 2nd Card
        cards.add(new Card(8,Card.Suit.DIAMONDS)); //Dealer's Upcard

        cards.add(new Card(3,Card.Suit.CLUBS)); //Player's Card on hit

        // Test case #3
        cards.add(new Card(9,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));

        cards.add(new Card(7,Card.Suit.SPADES));
        cards.add(new Card(8,Card.Suit.DIAMONDS));

        cards.add(new Card(3,Card.Suit.CLUBS));

        // Test case #4
        cards.add(new Card(7,Card.Suit.SPADES)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); //Dealers Downcard

        cards.add(new Card(9,Card.Suit.SPADES)); //Player's 2nd Card
        cards.add(new Card(10,Card.Suit.HEARTS)); //Dealer's Upcard

        cards.add(new Card(3,Card.Suit.CLUBS)); //Player's Card on hit

        // Test case #5
        cards.add(new Card(9,Card.Suit.CLUBS)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.HEARTS)); //Dealers Downcard

        cards.add(new Card(7,Card.Suit.SPADES)); //Player's 2nd Card
        cards.add(new Card(10,Card.Suit.DIAMONDS)); //Dealer's Upcard

        cards.add(new Card(3,Card.Suit.CLUBS)); //Player's Card on hit

        // Test case #6
        cards.add(new Card(Card.KING,Card.Suit.SPADES)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.HEARTS)); //Dealers Downcard

        cards.add(new Card(Card.QUEEN,Card.Suit.SPADES)); //Player's 2nd Card
        cards.add(new Card(8,Card.Suit.DIAMONDS)); //Dealer's Upcard

        // Test case #7
        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.SPADES)); //Dealers Downcard

        cards.add(new Card(Card.QUEEN,Card.Suit.HEARTS)); //Player's 2nd Card
        cards.add(new Card(8,Card.Suit.CLUBS)); //Dealer's Upcard

        // Test case #8
        cards.add(new Card(8,Card.Suit.DIAMONDS)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.SPADES)); //Dealers Downcard

        cards.add(new Card(5,Card.Suit.HEARTS)); //Player's 2nd Card
        cards.add(new Card(6,Card.Suit.CLUBS)); //Dealer's Upcard

        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); //Dealer card

        // Test case #9
        cards.add(new Card(7,Card.Suit.DIAMONDS)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.HEARTS)); //Dealers Downcard

        cards.add(new Card(6,Card.Suit.HEARTS)); //Player's 2nd Card
        cards.add(new Card(6,Card.Suit.DIAMONDS)); //Dealer's Upcard

        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS)); //Dealer Card

        // Test case #10
        cards.add(new Card(6,Card.Suit.DIAMONDS)); //Player's 1st Card
        cards.add(new Card(Card.KING,Card.Suit.SPADES)); //Dealers Downcard

        cards.add(new Card(8,Card.Suit.SPADES)); //Player's 2nd Card
        cards.add(new Card(6,Card.Suit.CLUBS)); //Dealer's Upcard

        cards.add(new Card(Card.KING,Card.Suit.HEARTS)); //Dealer Card

    }

    /**
     * Returns true if shuffle needed.
     * @return True if shuffle needed, false otherwise.
     */
    @Override
    public boolean shuffleNeeded() {
        return false;
    }
}
