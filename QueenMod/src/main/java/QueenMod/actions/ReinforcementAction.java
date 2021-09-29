package QueenMod.actions;

import QueenMod.cards.*;
import QueenMod.powers.ReinforcementsPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;

public class ReinforcementAction extends AbstractGameAction {
    AbstractMonster monster;
    int numCards;
    int numTimes;
    ArrayList<AbstractCard> upgradeMatrix = new ArrayList<AbstractCard>();
    boolean isPlayer;
    AbstractCreature th;
    String Text = "My draw pile is empty!";
    String Text2 = "I have no soldiers in my draw pile!";
    String Text3 = "Wait! Hold your fire!";

    public ReinforcementAction(AbstractCreature t, int n){
        numTimes = n;
        th = t;
        isPlayer = t.equals(AbstractDungeon.player);
    }

    public void update(){
        if (!isPlayer){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(th, th, ReinforcementsPower.POWER_ID));
            this.isDone = true;
            return;
        }
        CardGroup p = AbstractDungeon.player.drawPile;
        if (p.group.isEmpty()){
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
            this.isDone = true;
            return;
        }
            for (AbstractCard c : p.group) {
                if (c.cardID.equals(Hornet.ID) ||
                        c.cardID.equals(BumbleBee.ID)){
                    upgradeMatrix.add(c);
                }
            }
            if (upgradeMatrix.isEmpty()) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text2, true));
                this.isDone = true;
                return;
            }
            for (int i = 0;i < numTimes;i++) {
                AbstractCard c1 = upgradeMatrix.remove(AbstractDungeon.cardRandomRng.random(upgradeMatrix.size() - 1));
                c1.freeToPlayOnce = true;
                c1.applyPowers();
                AbstractDungeon.player.limbo.group.add(c1);
                AbstractDungeon.player.drawPile.group.remove(c1);
                if (AbstractDungeon.player.hand.group.contains(c1)) {
                    AbstractDungeon.player.hand.removeCard(c1);
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
                }

                AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
                for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (mon.hasPower(HexPower.POWER_ID)) {
                        mo = mon;
                    }
                }
                if (c1.cardID.equals(Hornet.ID)) {
                    Hornet tmp = (Hornet) c1;
                    tmp.playedBySwarm = true;
                    if ((mo.hasPower(TimeWarpPower.POWER_ID) && mo.getPower(TimeWarpPower.POWER_ID).amount == 11) || mo.hasPower(ThornsPower.POWER_ID) || mo.hasPower(SharpHidePower.POWER_ID) || mo.hasPower(BeatOfDeathPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToTop(new DrawToHandAction(c1));
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text3, true));
                    } else {
                        AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, mo));
                    }
                } else {
                    if (mo.hasPower(AngerPower.POWER_ID) || AbstractDungeon.player.hasPower(HexPower.POWER_ID) || (mo.hasPower(TimeWarpPower.POWER_ID) && mo.getPower(TimeWarpPower.POWER_ID).amount == 11)) {
                        AbstractDungeon.actionManager.addToTop(new DrawToHandAction(c1));
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text3, true));
                    } else {
                        AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, mo));
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.limbo.group) {
                    AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
                }
                AbstractDungeon.player.hand.refreshHandLayout();
            }
        this.isDone = true;
    }
}

