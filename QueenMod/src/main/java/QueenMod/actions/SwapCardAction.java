package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class SwapCardAction extends AbstractGameAction {
    AbstractCard card;
    AbstractCard card2;
    CardGroup SwapGroup;

    public SwapCardAction(AbstractCard c, AbstractCard d, CardGroup g){
        card = c;
        card2 = d;
        SwapGroup = g;
    }

    public void update(){
        if (!card.upgraded){
            SwapGroup.removeCard(card);
            SwapGroup.addToRandomSpot(card2.makeStatEquivalentCopy());
        }
        else {
            SwapGroup.removeCard(card);
            AbstractCard d = card2.makeStatEquivalentCopy();
            d.upgrade();
            SwapGroup.addToRandomSpot(d);
        }
        isDone = true;
    }
}
