package QueenMod.actions;

import QueenMod.cards.Hornet;
import QueenMod.powers.StingerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class updateExistingHornetsAction extends AbstractGameAction {

    public updateExistingHornetsAction() {
    }

    public void update() {
        if (!AbstractDungeon.player.hasPower(StingerPower.POWER_ID)){
            this.isDone = true;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Hornet.ID)) {
                if (!c.upgraded) {
                    c.baseDamage = 11 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
                } else {
                    c.baseDamage = 15 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Hornet.ID)) {
                if (!c.upgraded) {
                    c.baseDamage = 11 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
                } else {
                    c.baseDamage = 15 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(Hornet.ID)) {
                if (!c.upgraded) {
                    c.baseDamage = 11 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
                } else {
                    c.baseDamage = 15 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
                }
            }
        }
        this.isDone = true;
    }
}
