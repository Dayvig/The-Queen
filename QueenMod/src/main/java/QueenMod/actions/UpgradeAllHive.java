package QueenMod.actions;

import QueenMod.interfaces.IsHive;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UpgradeAllHive extends AbstractGameAction {

    public UpgradeAllHive() {
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof IsHive) {
                c.upgrade();
            }
        }
        this.isDone = true;
    }
}
