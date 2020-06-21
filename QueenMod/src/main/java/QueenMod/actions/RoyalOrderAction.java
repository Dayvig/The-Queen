package QueenMod.actions;

import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.Hornet;
import QueenMod.cards.WorkerBee;
import QueenMod.interfaces.IsHive;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BeatOfDeathPower;
import com.megacrit.cardcrawl.powers.SharpHidePower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;


public class RoyalOrderAction extends AbstractGameAction {

    ArrayList<AbstractCard> cardMatrix = new ArrayList<>();
    String Text3 = "Wait! Hold your fire!";


    public RoyalOrderAction() {
    }

    public void update() {
        boolean h = false;
        boolean b = false;
        boolean d = false;
        boolean w = false;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof IsHive) {
                cardMatrix.add(c);
                System.out.println("test 27");
            }
        }
        AbstractMonster mo =  AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);

        while (!(h && b && d && w) && !cardMatrix.isEmpty()) {
            AbstractCard tmp = cardMatrix.remove((int) (cardMatrix.size() * Math.random()));
            if (tmp.cardID.equals(Hornet.ID) && !h) {
                tmp.costForTurn = 0;
                tmp.applyPowers();
                AbstractDungeon.player.limbo.group.add(tmp);
                Hornet tempHornet = (Hornet) tmp;
                if ((mo.hasPower(TimeWarpPower.POWER_ID) && mo.getPower(TimeWarpPower.POWER_ID).amount == 11) || mo.hasPower(ThornsPower.POWER_ID) || mo.hasPower(SharpHidePower.POWER_ID) || mo.hasPower(BeatOfDeathPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToTop(new DrawToHandAction(tmp));
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text3, true));
                } else {
                    AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, mo));
                }
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                h = true;
            }

            if (tmp.cardID.equals(BumbleBee.ID) && !b) {
                tmp.costForTurn = 0;
                tmp.applyPowers();
                AbstractDungeon.player.limbo.group.add(tmp);
                    AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, mo));
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                b = true;
            }
            if (tmp.cardID.equals(WorkerBee.ID) && !w) {
                tmp.costForTurn = 0;
                tmp.applyPowers();
                AbstractDungeon.player.limbo.group.add(tmp);
                AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, mo));
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                w = true;
            }
            if (tmp.cardID.equals(Drone.ID) && !d) {
                tmp.costForTurn = 0;
                tmp.applyPowers();
                AbstractDungeon.player.limbo.group.add(tmp);
                AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, mo));
                d = true;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.limbo.group){
            if (c.exhaust){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
            }
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}
