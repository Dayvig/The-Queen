package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static QueenMod.QueenMod.makePowerPath;

public class FocusedSwarm extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = QueenMod.makeID("FocusedSwarm");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("focusedswarm84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("focusedswarm32.png"));

    public FocusedSwarm(final AbstractCreature owner, final AbstractCreature source, int newAmount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = newAmount;
        this.type = PowerType.BUFF;
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

    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
        if (this.amount > 1 && !AbstractDungeon.player.hasPower(SwarmStayPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SwarmPower(this.owner, this.owner, this.amount - 1), this.amount - 1));
        }
        else {
            if (AbstractDungeon.player.hasPower(SwarmStayPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SwarmPower(this.owner, this.owner, this.amount), this.amount));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
    }
}
