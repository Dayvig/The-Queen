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
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text2, true));
            this.isDone = true;
            return;
        }
            for (AbstractCard c : p.group) {
                if (c.cardID.equals(Hornet.ID) ||
                        c.cardID.equals(BumbleBee.ID) ||
                        c.cardID.equals(WorkerBee.ID) ||
                        c.cardID.equals(HornetCommander.ID) ||
                        c.cardID.equals(BumbleBeeCommander.ID) ||
                        c.cardID.equals(WorkerBeeCommander.ID) ||
                        c.cardID.equals(WASP.ID)) {
                    upgradeMatrix.add(c);
                }
            }
            if (upgradeMatrix.isEmpty()) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
                this.isDone = true;
                return;
            }
            AbstractCard c1 = upgradeMatrix.remove(AbstractDungeon.cardRandomRng.random(upgradeMatrix.size()-1));
            c1.freeToPlayOnce = true;
            c1.applyPowers();
            AbstractDungeon.player.limbo.group.add(c1);
        if (AbstractDungeon.player.hand.group.contains(c1)){
            AbstractDungeon.player.hand.removeCard(c1);
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
            if (c1.cardID.equals(Hornet.ID)){
                Hornet tmp = (Hornet) c1;
                tmp.playedBySwarm = true;
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng)));
            }
            else if (c1.cardID.equals(HornetCommander.ID)){
                HornetCommander tmp = (HornetCommander) c1;
                tmp.playedBySwarm = true;
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng)));
            }
            else if (c1.cardID.equals(WASP.ID)){
                WASP tmp = (WASP) c1;
                tmp.playedBySwarm = true;
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng)));
            }
            else {
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, null));
            }
        for (AbstractCard c : AbstractDungeon.player.limbo.group){
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}

