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
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
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
    public DistributeSwarmAction(AbstractCard cardPlayed, boolean isFocused, int swarmamount) {
        c = cardPlayed;
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
        if (a.target instanceof AbstractMonster){
                if (a.target.isDying || a.target.halfDead || a.target.isDead) {
                    this.isDone = true;
                }
        }
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
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player,
                                new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, s), s));
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player,
                                new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, s), s));
                    }
                }
                break;
            case 2:
                if (!a.target.isDying && !a.target.halfDead){
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                if (!f) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(a.target, AbstractDungeon.player,
                            new SwarmPowerEnemy(a.target, AbstractDungeon.player, s), s));
                }
                else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(a.target, AbstractDungeon.player,
                            new FocusedSwarmE(a.target, AbstractDungeon.player, s), s));
                    }
                }
                break;
            case 3:
                int numMonstersToApplyTo = 0;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                    }
                    if (!m.isDying && !m.halfDead && !m.isDead && !(m.currentHealth <= 0)){numMonstersToApplyTo++;}
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                int divisor;
                int remainder;
                if (numMonstersToApplyTo <= 0 || s <= 0){
                    isDone = true;
                }
                else if (s == 1){
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying && !m.halfDead && !m.isDead) {
                            if (f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, 1), 1));
                                isDone = true;
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, 1), 1));
                                isDone = true;
                            }
                        }
                    }
                }
                else if (numMonstersToApplyTo > s){
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying && !m.halfDead && !m.isDead) {
                            if (f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            }
                        }
                    }
                }
                else {
                    remainder = s % numMonstersToApplyTo;
                    divisor = s / numMonstersToApplyTo;
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying && !m.halfDead) {
                            if (f) {
                                int toApply = divisor;
                                if (remainder > 0){ toApply++; remainder--;}
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, toApply), toApply));
                            } else {
                                int toApply = divisor;
                                if (remainder > 0){ toApply++; remainder--;}
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, toApply), toApply));
                            }
                        }
                    }
                    if (remainder > 0){ System.out.println("Something went wrong. Incorrect swarm division");}
                }
                break;
            case 4:
                if (!a.target.isDying && !a.target.halfDead) {
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (m.hasPower(SwarmPowerEnemy.POWER_ID) && !m.equals(a.target)) {
                            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                        }
                    }
                    if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                    }
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(a.target, AbstractDungeon.player,
                            new SwarmPowerEnemy(a.target, AbstractDungeon.player, (int) Math.floor(s / 2)), (int) Math.floor(s / 2)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player,
                            new SwarmPower(a.target, AbstractDungeon.player, s / 2), s / 2));
                }
                break;
            case 5:
                numMonstersToApplyTo = 1;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                    }
                    if (!m.isDying && !m.halfDead && !m.isDead && !(m.currentHealth <= 0)){numMonstersToApplyTo++;}
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                if (numMonstersToApplyTo <= 0 || s <= 0){
                    isDone = true;
                }
                else if (s == 1) {
                    if (f) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player, new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
                        isDone = true;
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
                        isDone = true;
                    }
                }
                else if (numMonstersToApplyTo > s){
                    if (f) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player, new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
                        s--;
                        if (s <= 0){ isDone = true; }
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
                        s--;
                        if (s <= 0){ isDone = true; }
                    }
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying && !m.halfDead && !m.isDead) {
                            if (f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, 1), 1));
                                s--;
                                if (s <= 0){ isDone = true; }
                            }
                        }
                    }
                }
                else {
                    remainder = s % numMonstersToApplyTo;
                    divisor = s / numMonstersToApplyTo;
                    if (f) {
                        int toApply = divisor;
                        if (remainder > 0){ toApply++; remainder--;}
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player, new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, toApply), toApply));
                    } else {
                        int toApply = divisor;
                        if (remainder > 0){ toApply++; remainder--;}
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, toApply), toApply));
                    }

                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDying && !m.halfDead) {
                            if (f) {
                                int toApply = divisor;
                                if (remainder > 0){ toApply++; remainder--;}
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new FocusedSwarmE(m, AbstractDungeon.player, toApply), toApply));
                            } else {
                                int toApply = divisor;
                                if (remainder > 0){ toApply++; remainder--;}
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerActionFast(m, AbstractDungeon.player, new SwarmPowerEnemy(m, AbstractDungeon.player, toApply), toApply));
                            }
                        }
                    }
                    if (remainder > 0){ System.out.println("Something went wrong. Incorrect swarm division");}
                }
            default:
                break;
        }
        isDone = true;
    }
}