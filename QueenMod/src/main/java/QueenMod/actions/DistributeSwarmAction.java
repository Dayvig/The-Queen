package QueenMod.actions;

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

public class DistributeSwarmAction extends AbstractGameAction {
    AbstractCard c;
    int s;
    UseCardAction a;
    boolean f;

    public DistributeSwarmAction(AbstractCard cardPlayed, boolean isFocused, int swarmAmount, UseCardAction act) {
        c = cardPlayed;
        s = swarmAmount;
        a = act;
        f = isFocused;
    }

    public void update() {
        if (s == 0){
            isDone = true;
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
                boolean monstersHavePower = false;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(m, m, SwarmPowerEnemy.POWER_ID));
                        monstersHavePower = true;
                    }
                }
                if (monstersHavePower) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, s), s));
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
                        if (m.isDying){
                            s -= m.getPower(SwarmPowerEnemy.POWER_ID).amount;
                            System.out.println("trace: "+s);
                        }
                    }
                    n++;
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                int swarmdivide1;
                int swarmdivide2;
                if (s == 1) {
                    swarmdivide1 = 1;
                    swarmdivide2 = 0;
                } else {
                    if (n!=0) {
                        swarmdivide1 = (int) Math.floor(s / n);
                        swarmdivide2 = (int) Math.floor(s / n);
                        if (s % 2 == 1 && n != 1) {
                            swarmdivide1 += 1;
                        }
                    }
                    else {swarmdivide1 = 0; swarmdivide2 = 0; isDone = true;}
                }
                int firstMonster = n;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.isDying){
                        n--;
                        firstMonster--;
                        if (n!=0) {
                            swarmdivide1 = (int) Math.floor(s / n);
                            swarmdivide2 = (int) Math.floor(s / n);
                            if (s % 2 == 1 && n != 1) {
                                swarmdivide1 += 1;
                            }
                        }
                        else {swarmdivide1 = 0; swarmdivide2 = 0; isDone = true;}
                    }
                    else {
                        if (n == firstMonster) {
                            if (!f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player,
                                        new SwarmPowerEnemy(m, AbstractDungeon.player, swarmdivide1), swarmdivide1));
                                n--;
                            }
                            else {
                                System.out.println("Trace2");
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player,
                                        new FocusedSwarmE(m, AbstractDungeon.player, swarmdivide1), swarmdivide1));
                                n--;
                            }
                        } else {
                            if (!f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player,
                                        new SwarmPowerEnemy(m, AbstractDungeon.player, swarmdivide2), swarmdivide2));
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player,
                                        new FocusedSwarmE(m, AbstractDungeon.player, swarmdivide2), swarmdivide2));
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
                        if (m.isDying){
                            s -= m.getPower(SwarmPowerEnemy.POWER_ID).amount;
                            System.out.println("trace: "+s);
                        }
                    }
                    n++;
                }
                if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
                }
                n++;
                if (s == 1) {
                    swarmdivide1 = 1;
                    swarmdivide2 = 0;
                } else {
                    if (n!=0) {
                        swarmdivide1 = (int) Math.floor(s / n);
                        swarmdivide2 = (int) Math.floor(s / n);
                        if (s % 2 == 1 && n != 1) {
                            swarmdivide1 += 1;
                        }
                    }
                    else {swarmdivide1 = 0; swarmdivide2 = 0; isDone = true;}
                }
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.isDying){
                        n--;
                        if (n!=0) {
                            swarmdivide1 = (int) Math.floor(s / n);
                            swarmdivide2 = (int) Math.floor(s / n);
                            if (s % 2 == 1 && n != 1) {
                                swarmdivide1 += 1;
                            }
                        }
                        else {swarmdivide1 = 0; swarmdivide2 = 0; isDone = true;}
                    }
                    else {
                            if (!f) {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player,
                                        new SwarmPowerEnemy(m, AbstractDungeon.player, swarmdivide2), swarmdivide2));
                            } else {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player,
                                        new FocusedSwarmE(m, AbstractDungeon.player, swarmdivide2), swarmdivide2));
                            }
                        }
                    }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, swarmdivide1), swarmdivide1));
            default:
                break;
        }
        isDone = true;
    }
}