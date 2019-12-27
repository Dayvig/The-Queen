package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawToHandAction extends AbstractGameAction {
    AbstractCard card;
    public DrawToHandAction(AbstractCard c){
        card = c;
    }

    public void update(){
        AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
        AbstractDungeon.player.drawPile.removeCard(card);
        isDone = true;
    }
}
