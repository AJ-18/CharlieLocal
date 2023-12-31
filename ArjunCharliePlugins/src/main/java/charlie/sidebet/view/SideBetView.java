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

package charlie.sidebet.view;

import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.sidebet.rule.SideBetRule;
import charlie.view.AHand;
import charlie.view.AMoneyManager;

import charlie.view.sprite.Chip;
import charlie.view.sprite.ChipButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This class implements the side bet view
 * @author Ron.Coleman
 */
public class SideBetView implements ISideBetView {
    private final Logger LOG = Logger.getLogger(SideBetView.class);

    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;

    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);

    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;

    // ArrayList to store chip instances
    protected List<Chip> chips = new ArrayList<>();
    private String outcome = "";

    public SideBetView() {
        LOG.info("side bet view constructed");
    }

    // Declare an instance of SideBetRule
    private SideBetRule sideBetRule = new SideBetRule();

    /**
     * Sets the money manager.
     * @param moneyManager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }

    /**
     * Registers a click for the side bet.
     * This method gets invoked on right mouse click.
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;

        // Test if any chip button has been pressed.
        for(ChipButton button: buttons) {
            if(button.isPressed(x, y)) {
                amt += button.getAmt();
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);

                // Increments x based on amount of chips
                int xOffset = (int) (Math.random() * 20 + 10 * chips.size());
                int yOffset = (int) (Math.random() * 20 - 4);
                // Construct a Chip instance and add it to the chips ArrayList
                Chip chip = new Chip(button.getImage(), X + 15 + xOffset, Y - 20 + yOffset, button.getAmt());
                chips.add(chip);


                // Play the CHIPS_IN sound
                SoundFactory.play(Effect.CHIPS_IN);

            }
        }

        if(oldAmt == amt) {
            amt = 0;
            LOG.info("B. side bet amount cleared");

            // Clear the chips ArrayList
            chips.clear();

            // Play the CHIPS_OUT sound
            SoundFactory.play(Effect.CHIPS_OUT);

        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();

        if(bet == 0)
            return;

        LOG.info("side bet outcome = "+bet);

        // Update the bankroll
        moneyManager.increase(bet);

        if (bet < 0) {
            this.outcome = "Lose";
        }
        else {
            this.outcome = "Win";
        }

        LOG.info("new bankroll = "+moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting.
     */
    @Override
    public void starting() {
        // Clears outcome
        outcome = "";
    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view.
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view.
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // Draw the at-stake place on the table (Render the at stake area)
        g.setColor(Color.RED);
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);

        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        String amtText = "" + amt;
        int textWidth = g.getFontMetrics().stringWidth(amtText);
        g.drawString(""+amt, X-5, Y+5);

        // Render side bet descriptions to the right of the at-stake region
        Font sidebetFont = new Font("Arial", Font.BOLD, 14);
        g.setFont(sidebetFont);
        g.setColor(Color.YELLOW);
        g.drawString("SUPER 7 pays 3:1", X + DIAMETER, Y - 80);
        g.drawString("ROYAL MATCH pays 25:1", X + DIAMETER, Y - 60);
        g.drawString("EXACTLY 13 pays 1:1", X + DIAMETER, Y - 40);

        // Simulate chips being placed on the table to the right of the at-stake area
        for (Chip chip : chips) {
            g.drawImage(chip.getImage(), chip.getX(), chip.getY(), null);
       }
        //Setting Font metrics and outcome font style
        Font outcomeFont = new Font("Arial", Font.BOLD, 18);
        FontMetrics fm = g.getFontMetrics(outcomeFont);
        String outcomeText = "";
        if(outcome == "Win" || outcome == "Lose")
            outcomeText += " " + outcome.toString().toUpperCase() + " ! ";
        // Check the side bet outcome using the SideBetRule
        // Render "WIN" or "LOSE" over the at-stake chips based on the outcome
        if (outcome == "Win") {
            //Create foreground and background color
            Color winColorFg = Color.BLACK;
            Color winColorBg = new Color(116,255,4);
            //Set Font
            //outcomeText = "WIN";
            int w = fm.charsWidth(outcomeText.toCharArray(), 0, outcomeText.length());
            int h = fm.getHeight();
            // Color for a win
            g.setColor(winColorBg);
            g.fillRoundRect(X + 20, Y-h+5, w, h, 5, 5);
            g.setColor((winColorFg));
            g.setFont(outcomeFont);
            g.drawString(outcomeText, X + 20, Y);
            //g.drawString("WIN", X + DIAMETER + 150, Y + 10);
        } else if (outcome == "Lose") {
            //Create foreground and background color
            Color loseColorBg = new Color(250,58,5);
            Color loseColorFg = Color.WHITE;
            //Set Font
            //outcomeText = "LOSE";
            int w = fm.charsWidth(outcomeText.toCharArray(), 0, outcomeText.length());
            int h = fm.getHeight();
            // Color for a lose
            g.setColor(loseColorBg);
            g.fillRoundRect(X + 20, Y-h+5, w, h, 5, 5);
            g.setColor((loseColorFg));
            g.setFont(outcomeFont);
            g.drawString(outcomeText, X + 20, Y);
            //g.drawString("LOSE", X + DIAMETER + 150, Y + 10);
        }
    }
}
