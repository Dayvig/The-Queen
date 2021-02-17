package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawToDiscardAction extends AbstractGameAction {
    AbstractCard card;
    CardGroup group;
    public DrawToDiscardAction(AbstractCard c){
        card = c;
        group = AbstractDungeon.player.drawPile;
    }

    public DrawToDiscardAction(AbstractCard c, CardGroup g){
        card = c;
        group = g;
    }

    public void update(){
        group.moveToDiscardPile(card);
        group.removeCard(card);
        isDone = true;
    }
}
