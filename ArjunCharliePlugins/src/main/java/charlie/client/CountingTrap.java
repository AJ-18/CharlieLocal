package charlie.client;

import charlie.card.Card;
import charlie.message.Message;
import charlie.message.view.to.Deal;
import charlie.message.view.to.GameStart;
import charlie.message.view.to.Shuffle;
import charlie.plugin.ITrap;
import org.apache.log4j.Logger;

public class CountingTrap implements ITrap {
    Logger LOG = Logger.getLogger(CountingTrap.class);
    int runningCount = 0;

    //Number of cards, not number of decks
    int shoeSize = -1;

    boolean shufflePending = false;

    @Override
    public void onSend(Message msg) {


    }

    @Override
    public void onReceive(Message msg) {
        if(msg instanceof GameStart) {
            GameStart gameStartMsg = (GameStart) msg;
            shoeSize = gameStartMsg.shoeSize();

            if (shufflePending == true) {
                shufflePending = false;
                runningCount = 0;
                LOG.info("Reseting After Shuffle");
            }
        }

        if(msg instanceof Deal) {
            Deal deal = (Deal) msg;
            try {
                Card card = deal.getCard();
                //Week 2 Lecture notes
                if (card.getRank() == 10 || card.isFace() || card.isAce())
                    runningCount -= 1;
                else if (card.getRank() >= 2 || card.getRank() <= 6)
                    runningCount += 1;
                shoeSize--;
            }
            catch (NullPointerException e) {
                LOG.info("Passing Whole Card");
            }

        }

        if(msg instanceof Shuffle) {
            shufflePending = true;

        }

        //Shuffle & GameStart
        double numDecks = shoeSize / 52.;
        double trueCount = runningCount / numDecks;
        double betAmt = Math.max(1, trueCount + 1);

        //Formatting Fixes: Shoe(2 places before decimal), Bet round properly
        //shoe: ##.# running count: ### true count: ###.# bet: ###
        String output = String.format("shoe: %02.1f running count: %03d true counter: %03.1f bet: %03.0f",
                numDecks,
                runningCount,
                trueCount,
                betAmt);

        LOG.info(output);


    }
}