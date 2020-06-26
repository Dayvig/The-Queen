package QueenMod.actions;

import QueenMod.powers.FocusedSwarm;
import QueenMod.powers.FocusedSwarmE;
import QueenMod.powers.SwarmPower;
import QueenMod.powers.SwarmPowerEnemy;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DistributeSwarmAction extends AbstractGameAction {
    AbstractCard c;
    int s;
    UseCardAction a;
    boolean f;

    public DistributeSwarmAction(AbstractCard cardPlayed, boolean isFocused, int swarmamount, UseCardAction act) {
        c = cardPlayed;
        a = act;
        f = isFocused;
        s = swarmamount;
    }

    public void update() {
        if (s == -1){
            s = 0;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.hasPower(SwarmPowerEnemy.POWER_ID) && !m.isDying) {
                    s += m.getPower(SwarmPowerEnemy.POWER_ID).amount;
                }
            }
            if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                s += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
            }
            if (s == 0){
                isDone = true;
            }
        }
        System.out.println(s);
        int i = 0;
        if (c.target.equals(AbstractCard.CardTarget.SELF)) {
            i = 1;
        } else if (c.target.equals(AbstractCard.CardTarget.ENEMY)) {
            i = 2;
        } else if (c.target.equals(AbstractCard.CardTarget.ALL_ENEMY)) {
            i = 3;
        } else if (c.target.equals(AbstractCard.CardTarget.SELF_AND_ENEMY)) {
            i = 4;
        }
        else if (c.target.equals(AbstractCard.CardTarget.ALL)){
            i = 5;
        }
        System.out.println(i);
        switch (i) {
            case 1:
                boolean moveSwarm = false;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                        moveSwarm = true;
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                    }
                }
                if ((AbstractDungeon.player.hasPower(SwarmPower.POWER_ID) && f) || moveSwarm){
                    moveSwarm = true;
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                if (moveSwarm) {
                    if (!f) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                                new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, s), s));
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                                new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, s), s));
                    }
                }
                break;
            case 2:
                if (!a.target.isDying){
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                if (!f) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(a.target, AbstractDungeon.player,
                            new SwarmPowerEnemy(a.target, AbstractDungeon.player, s), s));
                }
                else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(a.target, AbstractDungeon.player,
                            new FocusedSwarmE(a.target, AbstractDungeon.player, s), s));
                    }
                }
                break;
            case 3:
                int n = 0;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                    }
                    if (!m.isDying){n++;}
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                int swarmdivide1;
                int swarmdivide2;
                if (n == 0 || s == 0){
                    isDone = true;
                }
                else if (s == 1){
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying) {
                            if (f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, 1), 1));
                                isDone = true;
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, 1), 1));
                                isDone = true;
                            }
                        }
                    }
                }
                else if (n > s){
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying) {
                            if (f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            }
                        }
                    }
                }
                else {
                    swarmdivide1 = (int) Math.floor(s / n);
                    while (swarmdivide1*n < s){
                        swarmdivide1++;
                    }
                    int toApply;
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying) {
                            if (f) {
                                toApply = swarmdivide1;
                                while (toApply > s){
                                    toApply--;
                                }
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, toApply), toApply));
                                s -= toApply;
                                if (s <= 0){ isDone = true; }
                            } else {
                                toApply = swarmdivide1;
                                while (toApply > s){
                                    toApply--;
                                }
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, toApply), toApply));
                                s -= toApply;
                                if (s <= 0){ isDone = true; }
                            }
                        }
                    }
                }
                break;
            case 4:
                if (!a.target.isDying) {
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (m.hasPower(SwarmPowerEnemy.POWER_ID) && !m.equals(a.target)) {
                            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                        }
                    }
                    if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                    }
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(a.target, AbstractDungeon.player,
                            new SwarmPowerEnemy(a.target, AbstractDungeon.player, (int) Math.floor(s / 2)), (int) Math.floor(s / 2)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new SwarmPower(a.target, AbstractDungeon.player, s / 2), s / 2));
                }
                break;
            case 5:
                n = 0;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                    }
                    if (!m.isDying){n++;}
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                n++;
                System.out.println("Swarm amount: "+s+", Number of targets: "+n);
                if (n == 0 || s == 0){
                    isDone = true;
                }
                else if (s == 1){
                    if (f){
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, 1),1));
                        isDone = true;
                    }
                    else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, 1),1));
                        isDone = true;
                    }
                }
                else if (n > s){
                    if (f){
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, 1),1));
                        s--;
                        if (s <= 0){ isDone = true; }
                    }
                    else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, 1),1));
                        s--;
                        if (s <= 0){ isDone = true; }
                    }
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying) {
                            if (f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            }
                        }
                    }
                }
                else {
                    swarmdivide1 = (int) Math.floor(s / n);
                    swarmdivide2 = (int) Math.floor(s / n);
                    swarmdivide1 += s % n;
                    int toApply = swarmdivide1;
                    System.out.println(swarmdivide1);
                    if (f){
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, toApply),toApply));
                        s -= toApply;
                        if (s <= 0){ isDone = true; }
                    }
                    else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, toApply),toApply));
                        s -= toApply;
                        if (s <= 0){ isDone = true; }
                    }
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying) {
                            if (f) {
                                toApply = swarmdivide2;
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, toApply), toApply));
                                s -= toApply;
                                if (s <= 0){ isDone = true; }
                            } else {
                                toApply = swarmdivide2;
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, toApply), toApply));
                                s -= toApply;
                                if (s <= 0){ isDone = true; }
                            }
                        }
                    }
                }
            default:
                break;
        }
        isDone = true;
    }
}