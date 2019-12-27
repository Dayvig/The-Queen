package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawToDiscardAction extends AbstractGameAction {
    AbstractCard card;
    public DrawToDiscardAction(AbstractCard c){
        card = c;
    }

    public void update(){
        AbstractDungeon.player.drawPile.moveToDiscardPile(card);
        AbstractDungeon.player.drawPile.removeCard(card);
        isDone = true;
    }
}
