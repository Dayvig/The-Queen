package QueenMod.actions;

import QueenMod.cards.BumbleBee;
import QueenMod.cards.Hornet;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class ManualAction extends AbstractGameAction {

    public ManualAction() {

    }

    public void update() {
        int k = AbstractDungeon.player.drawPile.group.size() / 20;
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(k));
        isDone = true;
    }
}
