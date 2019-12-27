package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UpgradeSpecificCardInDrawPileAction extends AbstractGameAction {
    AbstractMonster monster;
    AbstractPlayer p;
    AbstractCard card1;
    boolean a;

    public UpgradeSpecificCardInDrawPileAction(AbstractPlayer sourceplayer, AbstractCard card, boolean isAll) {
        p = sourceplayer;
        card1 = card;
        a = isAll;
    }

    public void update() {
        CardGroup p = AbstractDungeon.player.drawPile;
        for (AbstractCard c : p.group){
            if (c.cardID.equals(card1.cardID)){
                c.upgrade();
            }
            if (!a){
                isDone = true;
                return;
            }
        }
        isDone = true;
    }
}

