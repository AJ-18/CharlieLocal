package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;

import java.util.List;
import java.util.Random;

/**
 * Class HueyStay implements interfaced IBot and Runnable to create a bot that plays blackjack, staying on every hand.
 */
public class HueyStay implements Runnable, IBot  {

    protected int MAX_THINKING = 5;
    protected Seat mine;
    protected Hand myHand;
    protected Dealer dealer;
    protected Random ran = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public Hand getHand() {
        return myHand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sit(Seat seat) {
        this.mine = seat;
        // Construct a new hand id or Hid instance called, hid, using seat.
        Hid hid = new Hid(seat);
        // Construct a new Hand using hid and set it to the member myHand.
        this.myHand = new Hand(hid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame(List<Hid> list, int i) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame(int i) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deal(Hid hid, Card card, int[] ints) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insure() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bust(Hid hid) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void win(Hid hid) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void blackjack(Hid hid) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void charlie(Hid hid) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lose(Hid hid) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void push(Hid hid) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffling() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(Hid hid) {
        if(hid.getSeat() != mine)
            return;

        new Thread(this).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void split(Hid hid, Hid hid1) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        int thinking = ran.nextInt(MAX_THINKING * 1000);
        try {
            Thread.sleep(thinking);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        dealer.stay(this, myHand.getHid());
    }
}
