package QueenMod.actions;

import QueenMod.powers.Nectar;
import QueenMod.powers.SwarmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArtOfWarAction extends AbstractGameAction {

    public ArtOfWarAction() {

    }

    public void update() {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type.equals(AbstractCard.CardType.ATTACK) && c.costForTurn > 0) {
                    c.setCostForTurn(c.costForTurn -1);
                    c.isCostModifiedForTurn = true;
                }
            }
        this.isDone = true;
    }
}