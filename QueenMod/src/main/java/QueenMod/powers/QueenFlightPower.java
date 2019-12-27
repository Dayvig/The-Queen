package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static QueenMod.QueenMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class QueenFlightPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = QueenMod.makeID("QueenFlightPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private int drain;
    private boolean isUpgraded;

    public QueenFlightPower(final AbstractCreature owner, final AbstractCreature source, final int amount, int dr, boolean upgr) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        drain = dr;
        isUpgraded = upgr;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.loadRegion("flight");

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount/2 >= AbstractDungeon.player.currentBlock){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, QueenFlightPower.POWER_ID,1));
        }
        return damageAmount/2;
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (this.owner.hasPower(Nectar.POWER_ID) && this.owner.getPower(Nectar.POWER_ID).amount >= this.amount){
            int n;
            if (isUpgraded){ n = 4;}
            else { n = 5; }
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, Nectar.POWER_ID, n));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this,1));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
       if (isUpgraded){
           description = DESCRIPTIONS[1];
       }
       else {
           description = DESCRIPTIONS[0];
       }
    }

    @Override
    public AbstractPower makeCopy() {
        return new QueenFlightPower(owner, source, amount, drain, isUpgraded);
    }
}
