package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.client.BotBasicStrategy;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import charlie.util.Play;

import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class Huey implements Runnable, IBot {

    protected int MAX_THINKING = 5;
    protected Seat mine;
    protected Hand myHand;
    protected Dealer dealer;
    protected Card upCard;
    protected double defaultBet = 5.0;
    protected boolean running = true;
    protected static Logger LOG = Logger.getLogger(Huey.class);
    protected Random ran = new Random();


    @Override
    public Hand getHand() {
        return myHand;
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    @Override
    public void sit(Seat seat) {
        this.mine = seat;
        // Construct a new hand id or Hid instance called, hid, using seat.
        Hid hid = new Hid(seat);
        // Putting down a default bet
        hid.setAmt(defaultBet);

        // Construct a new Hand using hid and set it to the member myHand.
        this.myHand = new Hand(hid);

    }

    @Override
    public void startGame(List<Hid> list, int i) {

    }

    @Override
    public void endGame(int i) {

    }

    @Override
    public void deal(Hid hid, Card card, int[] ints) {
        // Broadcast channeled through deal - Anytime a card comes out of the shoe.
        // Check the Hid and Seat. - Is the card, not null, & what is the indicated hand ID (Hid). Create a member variable, store upCard.
        // getSeat on Hid
        // Check If you hit again, invoke play (Check for if it's your Hid (seat), if you have more than 2 cards in hand)

        // If the card is not null and it is the dealer's up card, store it
        if (card != null && hid.getSeat() == Seat.DEALER) {
            this.upCard = card;
        }

    }

    @Override
    public void insure() {

    }

    @Override
    public void bust(Hid hid) {
        if (hid.getSeat() == this.mine) {
            this.stopThread();
        }
    }

    @Override
    public void win(Hid hid) {
        if (hid.getSeat() == this.mine) {
            this.stopThread();
        }
    }

    @Override
    public void blackjack(Hid hid) {
        if (hid.getSeat() == this.mine) {
            this.stopThread();
        }
    }

    @Override
    public void charlie(Hid hid) {
        if (hid.getSeat() == this.mine) {
            this.stopThread();
        }
    }

    @Override
    public void lose(Hid hid) {
        if (hid.getSeat() == this.mine) {
            this.stopThread();
        }
    }

    @Override
    public void push(Hid hid) {
        if (hid.getSeat() == this.mine) {
            this.stopThread();
        }
    }

    @Override
    public void shuffling() {

    }

    @Override
    public void play(Hid hid) {
        if(hid.getSeat() != mine)
            return;

        new Thread(this).start();
    }

    @Override
    public void split(Hid hid, Hid hid1) {

    }

    @Override
    public void run() {

        int thinking = ran.nextInt(MAX_THINKING * 1000);
        try {
            // Only decide on the next play if the worker thread is still running
            while (running) {
                // Call to BotBasicStrategy to decide whether to hit, stay, double down, or split
                BotBasicStrategy botBasicStrategy = new BotBasicStrategy();
                Play botAdvice = botBasicStrategy.getPlay(myHand, upCard);
                // this.mine: Seat tells us whether its Huey or Dewey (Right or Left)
                LOG.info(this.mine + " " + botAdvice);
                Thread.sleep(thinking);
                if (botAdvice == Play.NONE) {
                    stopThread();
                }
                else if (botAdvice == Play.HIT) {
                    LOG.info( this.mine + " BotAdvice is hitting");
                    dealer.hit(this, this.myHand.getHid());
                } else if (botAdvice == Play.STAY) {
                    dealer.stay(this, this.myHand.getHid());
                    // Stop my thread so that the turn ends
                    this.stopThread();
                } else if (botAdvice == Play.DOUBLE_DOWN) {
                    if (this.myHand.size() > 2) {
                        LOG.info(this.mine + "BotAdvice cannot double down, is hitting");
                        // Cannot double down, only hit
                        dealer.hit(this, this.myHand.getHid());
                    } else {
                        dealer.doubleDown(this, this.myHand.getHid());
                        // Stop my thread so that the turn ends
                        this.stopThread();
                    }
                }
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void stopThread() {
        this.running = false;
    }

}
