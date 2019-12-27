package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.actions.DistributeSwarmAction;
import QueenMod.cards.*;
import QueenMod.cards.BlindingSwarm;
import QueenMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static QueenMod.QueenMod.makePowerPath;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY;

//Gain 1 dex for the turn for each card played.

public class HeartOfTheSwarm extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = QueenMod.makeID("HeartOfTheSwarm");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("beat"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("beat"));

    public HeartOfTheSwarm(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
        this.loadRegion("beat");

        updateDescription();
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
    public void atStartOfTurn(){
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(KillerBee.ID)){
                c.baseDamage *= 2;
            }
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard c, UseCardAction a) {
        int totalSwarm = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                totalSwarm += m.getPower(SwarmPowerEnemy.POWER_ID).amount;
            }
        }
        if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
            totalSwarm += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
        }
        if (c.cardID.equals(Frenzy.ID) && totalSwarm != 0){
            if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)){
                totalSwarm += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            }
            AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, true, totalSwarm, a));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(a.target, AbstractDungeon.player, SwarmPowerEnemy.POWER_ID));
        }
       else if (c.cardID.equals(Mark.ID) && totalSwarm != 0){
            AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, true, totalSwarm, a));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(a.target, AbstractDungeon.player, SwarmPowerEnemy.POWER_ID));
        }
        else if (c.cardID.equals(CalculatedAttack.ID) && totalSwarm > 1){
            if (!a.target.isDying || !a.target.isDeadOrEscaped()) {
                int temp = totalSwarm / 2;
                totalSwarm -= temp;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, temp), temp));
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, false, totalSwarm, a));
            }
        }
        else if (c.cardID.equals(BlindingSwarm.ID) && totalSwarm != 0){
            System.out.println("Trace");
            AbstractCard tmp = c;
            tmp.target = ALL_ENEMY;
            AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(tmp, true, totalSwarm, a));
            int n = totalSwarm/c.magicNumber;
            if (n > 0) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, n, false), n));
                }
            }
        }
        else if (c.cardID.equals(SwarmEconomics.ID) && totalSwarm != 0){
            int temp = totalSwarm;
            totalSwarm = 0;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                    new FocusedSwarm(AbstractDungeon.player,AbstractDungeon.player,temp),temp));
            AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, false, totalSwarm, a));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SwarmPower.POWER_ID));
        }
        else if (totalSwarm != 0) {
            AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, false, totalSwarm, a));
        }
    }

    @Override
    public void onRemove() {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HeartOfTheSwarm(AbstractDungeon.player,AbstractDungeon.player,1),1));
    }

    public HeartOfTheSwarm makeCopy(){
        return new HeartOfTheSwarm(this.owner,this.source,this.amount);
    }
}