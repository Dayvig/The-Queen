package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.actions.FormationAction;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.Formation;
import QueenMod.cards.Hornet;
import QueenMod.interfaces.IsHive;
import QueenMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.text.Normalizer;
import java.util.ArrayList;

import static QueenMod.QueenMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class GeneralPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = QueenMod.makeID("GeneralPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("generalform84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("generalform32.png"));

    private final ArrayList <AbstractCard> HiveMatrix = new ArrayList <>();
    private final ArrayList <AbstractCard> FormationMatrix = new ArrayList <>();

    public GeneralPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    /*
    @Override
    public void atStartOfTurn(){
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c instanceof IsHive) {
                HiveMatrix.add(c);
            }
        }
        AbstractCard[] comp = new AbstractCard[3];
        if (HiveMatrix.size() >= 3){
            for (int i=0;i<3;i++){
                FormationMatrix.add(HiveMatrix.remove((int)(Math.random()*HiveMatrix.size()-1)));
            }
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Formation(FormationMatrix.get(0), FormationMatrix.get(1), FormationMatrix.get(2))));
            HiveMatrix.clear();
            FormationMatrix.clear();
        }
    }*/

        @Override
    public void onAfterUseCard(AbstractCard c, UseCardAction a){
        if (c instanceof IsHive){
            if (c.cardID.equals(Hornet.ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount)));
            }
            else if (c.cardID.equals(BumbleBee.ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, this.amount)));
            }
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GeneralPower(owner, source, amount);
    }
}
