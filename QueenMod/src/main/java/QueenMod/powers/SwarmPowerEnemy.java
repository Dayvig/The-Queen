package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static QueenMod.QueenMod.makePowerPath;

public class SwarmPowerEnemy extends AbstractPower implements HealthBarRenderPower {
    public AbstractCreature source;
    public static final String POWER_ID = QueenMod.makeID("SwarmE");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("swarmpower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("swarmpower32.png"));

    public SwarmPowerEnemy (AbstractCreature owner, AbstractCreature source, int newAmount) {
        this.name = NAME;
        this.ID = this.POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = newAmount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        this.canGoNegative = false;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_STRENGTH", 0.05F);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.amount));
        if (!AbstractDungeon.player.hasPower(SwarmStayPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SwarmPowerEnemy(this.owner, this.owner, this.amount - 1), -1));
        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
        int am = this.amount;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, am), am));
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner,this.source, SwarmPowerEnemy.POWER_ID));
    }

    @Override
    public int getHealthBarAmount() {
        if (this.owner.hasPower(RavenousPower.POWER_ID)) { return this.amount * 2; }
        return this.amount;
    }

    @Override
    public Color getColor() {
        return Color.TAN;
    }


    @SpirePatch(
            clz=AbstractMonster.class,
            method="die",
            paramtypez = {boolean.class}
    )
    public static class swarmPatch  {
        @SpirePrefixPatch
        public static void die (AbstractMonster target, boolean t) {
            if (!t && target.hasPower(SwarmPowerEnemy.POWER_ID)) {
                int am = target.getPower(SwarmPowerEnemy.POWER_ID).amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, am), am));
            }
        }
    }
    @SpirePatch(
            clz=AbstractMonster.class,
            method="escape",
            paramtypez = {}
    )
    public static class swarmPatchEscape  {
        @SpirePrefixPatch
        public static void escape (AbstractMonster target) {
            if (target.hasPower(SwarmPowerEnemy.POWER_ID)) {
                int am = target.getPower(SwarmPowerEnemy.POWER_ID).amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, am), am));
            }
            if (target.hasPower(FocusedSwarmE.POWER_ID)) {
                int am = target.getPower(FocusedSwarmE.POWER_ID).amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, am), am));
            }
        }
    }
}