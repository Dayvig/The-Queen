//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package QueenMod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import java.util.Collections;
import java.util.Iterator;

public class ApplyPowerActionFast extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPower powerToApply;
    private float startingDuration;

    public ApplyPowerActionFast(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AttackEffect effect) {
        if (Settings.FAST_MODE) {
            this.startingDuration = 0.01F;
        } else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }

        this.setValues(target, source, stackAmount);
        this.duration = this.startingDuration;
        this.powerToApply = powerToApply;
        if (AbstractDungeon.player.hasRelic("Snake Skull") && source != null && source.isPlayer && target != source && powerToApply.ID.equals("Poison")) {
            AbstractDungeon.player.getRelic("Snake Skull").flash();
            ++this.powerToApply.amount;
            ++this.amount;
        }

        if (powerToApply.ID.equals("Corruption")) {
            Iterator var7 = AbstractDungeon.player.hand.group.iterator();

            AbstractCard c;
            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }

            var7 = AbstractDungeon.player.drawPile.group.iterator();

            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }

            var7 = AbstractDungeon.player.discardPile.group.iterator();

            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }

            var7 = AbstractDungeon.player.exhaustPile.group.iterator();

            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }
        }

        this.actionType = ActionType.POWER;
        this.attackEffect = effect;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startingDuration = 0.0F;
            this.isDone = true;
        }

    }

    public ApplyPowerActionFast(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast) {
        this(target, source, powerToApply, stackAmount, isFast, AttackEffect.NONE);
    }

    public ApplyPowerActionFast(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply) {
        this(target, source, powerToApply, powerToApply.amount);
    }

    public ApplyPowerActionFast(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        this(target, source, powerToApply, stackAmount, false, AttackEffect.NONE);
    }

    public ApplyPowerActionFast(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, AttackEffect effect) {
        this(target, source, powerToApply, stackAmount, false, effect);
    }

    public void update() {
        if (this.target != null && !this.target.isDeadOrEscaped()) {
            if (this.duration == this.startingDuration) {
                if (this.powerToApply instanceof NoDrawPower && this.target.hasPower(this.powerToApply.ID)) {
                    this.isDone = true;
                    return;
                }

                if (this.source != null) {
                    Iterator var1 = this.source.powers.iterator();

                    while(var1.hasNext()) {
                        AbstractPower pow = (AbstractPower)var1.next();
                        pow.onApplyPower(this.powerToApply, this.target, this.source);
                    }
                }

                if (AbstractDungeon.player.hasRelic("Champion Belt") && this.source != null && this.source.isPlayer && this.target != this.source && this.powerToApply.ID.equals("Vulnerable") && !this.target.hasPower("Artifact")) {
                    AbstractDungeon.player.getRelic("Champion Belt").onTrigger(this.target);
                }

                if (this.target instanceof AbstractMonster && this.target.isDeadOrEscaped()) {
                    this.duration = 0.0F;
                    this.isDone = true;
                    return;
                }

                if (AbstractDungeon.player.hasRelic("Ginger") && this.target.isPlayer && this.powerToApply.ID.equals("Weakened")) {
                    AbstractDungeon.player.getRelic("Ginger").flash();
                    this.addToTop(new TextAboveCreatureAction(this.target, TEXT[1]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    return;
                }

                if (AbstractDungeon.player.hasRelic("Turnip") && this.target.isPlayer && this.powerToApply.ID.equals("Frail")) {
                    AbstractDungeon.player.getRelic("Turnip").flash();
                    this.addToTop(new TextAboveCreatureAction(this.target, TEXT[1]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    return;
                }

                if (this.target.hasPower("Artifact") && this.powerToApply.type == PowerType.DEBUFF) {
                    this.addToTop(new TextAboveCreatureAction(this.target, TEXT[0]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    CardCrawlGame.sound.play("NULLIFY_SFX");
                    this.target.getPower("Artifact").flashWithoutSound();
                    this.target.getPower("Artifact").onSpecificTrigger();
                    return;
                }

                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                boolean hasBuffAlready = false;
                Iterator var6 = this.target.powers.iterator();

                label148:
                while(true) {
                    AbstractPower p;
                    do {
                        do {
                            if (!var6.hasNext()) {
                                if (this.powerToApply.type == PowerType.DEBUFF) {
                                    //this.target.useFastShakeAnimation(0.5F);
                                }

                                if (!hasBuffAlready) {
                                    this.target.powers.add(this.powerToApply);
                                    Collections.sort(this.target.powers);
                                    this.powerToApply.onInitialApplication();
                                    this.powerToApply.flash();
                                    if (this.amount >= 0 || !this.powerToApply.ID.equals("Strength") && !this.powerToApply.ID.equals("Dexterity") && !this.powerToApply.ID.equals("Focus")) {
                                        if (this.powerToApply.type == PowerType.BUFF) {
                                            AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name));
                                        } else {
                                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name));
                                        }
                                    } else {
                                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                                    }

                                    AbstractDungeon.onModifyPower();
                                    if (this.target.isPlayer) {
                                        int buffCount = 0;
                                        Iterator var8 = this.target.powers.iterator();

                                        while(var8.hasNext()) {
                                            AbstractPower po = (AbstractPower)var8.next();
                                            if (po.type == PowerType.BUFF) {
                                                ++buffCount;
                                            }
                                        }

                                        if (buffCount >= 10) {
                                            UnlockTracker.unlockAchievement("POWERFUL");
                                        }
                                    }
                                }
                                break label148;
                            }

                            p = (AbstractPower)var6.next();
                        } while(!p.ID.equals(this.powerToApply.ID));
                    } while(p.ID.equals("Night Terror"));

                    p.stackPower(this.amount);
                    p.flash();
                    if ((p instanceof StrengthPower || p instanceof DexterityPower) && this.amount <= 0) {
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                    } else if (this.amount > 0) {
                        if (p.type != PowerType.BUFF && !(p instanceof StrengthPower) && !(p instanceof DexterityPower)) {
                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" + Integer.toString(this.amount) + " " + this.powerToApply.name));
                        } else {
                            AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" + Integer.toString(this.amount) + " " + this.powerToApply.name));
                        }
                    } else if (p.type == PowerType.BUFF) {
                        AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                    } else {
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                    }

                    p.updateDescription();
                    hasBuffAlready = true;
                    AbstractDungeon.onModifyPower();
                }
            }

            this.tickDuration();
        } else {
            this.isDone = true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        TEXT = uiStrings.TEXT;
    }
}
