package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.actions.DrawToDiscardAction;
import QueenMod.cards.BumbleBee;
import QueenMod.cards.WASP;
import QueenMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static QueenMod.QueenMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class DefenderPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = QueenMod.makeID("DefenderPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private int numTimes;
    private boolean isUpgraded;

    public DefenderPower(final AbstractCreature owner, final AbstractCreature source, final int amount, boolean upgr) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        numTimes = this.amount;
        isUpgraded = upgr;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (numTimes > 0 && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && this.owner.currentBlock < damageAmount) {
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                if (c.cardID.equals(BumbleBee.ID) && c.upgraded){
                    c.applyPowers();
                    damageAmount -= c.block;
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
                    if (!isUpgraded) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
                    }
                    else {
                        AbstractDungeon.actionManager.addToBottom(new DrawToDiscardAction(c));
                    }
                    numTimes--;
                    if (numTimes<=0){
                        break;
                    }
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                if (c.cardID.equals(BumbleBee.ID) && !c.upgraded){
                    c.applyPowers();
                    damageAmount -= c.block;
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
                    if (!isUpgraded) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
                    }
                    else {
                        AbstractDungeon.actionManager.addToBottom(new DrawToDiscardAction(c));
                    }
                    numTimes--;
                    if (numTimes<=0){
                        break;
                    }
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                if (c.cardID.equals(WASP.ID)){
                    c.applyPowers();
                    damageAmount -= c.block;
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
                    if (!isUpgraded) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
                    }
                    else {
                        AbstractDungeon.actionManager.addToBottom(new DrawToDiscardAction(c));
                    }
                    numTimes--;
                    if (numTimes<=0){
                        break;
                    }
                }
            }
        }
        if (numTimes > 0){numTimes--;}
        if (damageAmount == 0){
            return damageAmount;
        }
        else if (damageAmount < 0){
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner,this.owner,Math.abs(damageAmount)));
            return 0;
        }
        else {
            return damageAmount;
        }
    }

    // At the end of the turn, remove gained Dexterity.
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        numTimes = this.amount;
            }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DefenderPower(owner, source, amount, isUpgraded);
    }
}
