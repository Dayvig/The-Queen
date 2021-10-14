package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.relics.WhiteHibiscus;
import QueenMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import static QueenMod.QueenMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class PollinatePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = QueenMod.makeID("PollinatePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("pollinate84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("pollinate32.png"));

    public PollinatePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
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
        if (AbstractDungeon.player.hasRelic(WhiteHibiscus.ID) && stackAmount > 0){
            AbstractDungeon.player.getRelic(WhiteHibiscus.ID).flash();
            activateHibiscus(this.owner, stackAmount);
        }

    }


    public void activateHibiscus(AbstractCreature m, int am){
        System.out.println("Hibiscus test");
        this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -am), -am));
        if (m != null && !m.hasPower("Artifact")) {
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new GainStrengthPower(m, am), am));
        }
    }

    @Override
    public void onInitialApplication(){
        if (AbstractDungeon.player.hasRelic(WhiteHibiscus.ID)){
            AbstractDungeon.player.getRelic(WhiteHibiscus.ID).flash();
            activateHibiscus(this.owner, this.amount);
        }
    }

    @Override
    public int onAttacked (DamageInfo info, int damageAmount){
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && this.owner.currentBlock < damageAmount) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new Nectar(this.source, this.source, 2), 2));
        }
            return damageAmount;
        }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new PollinatePower(this.owner, this.owner, this.amount - 1), -1));
    }

    @Override
    public String getHoverMessage() {
        this.description = DESCRIPTIONS[0];
        return this.name + ":\n" + this.description;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        this.canGoNegative = false;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PollinatePower(owner, source, amount);
    }
}
