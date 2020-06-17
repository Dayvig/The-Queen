package QueenMod.actions;


import QueenMod.cards.WorkerBee;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ProductionAction extends AbstractGameAction {

    private ArrayList<AbstractCard> upgradeMatrix = new ArrayList<AbstractCard>();
    int numTimes;

    public ProductionAction(int n) {
        numTimes = n;
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(WorkerBee.ID)) {
                upgradeMatrix.add(c);
            }
        }
        while (!upgradeMatrix.isEmpty() && numTimes > 0) {
            AbstractDungeon.actionManager.addToBottom(new DrawToHandAction(upgradeMatrix.remove((int) (Math.random() * upgradeMatrix.size()))));
            numTimes--;
        }
        upgradeMatrix.clear();
        this.isDone = true;
    }
}

