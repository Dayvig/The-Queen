package QueenMod.powers;

import QueenMod.QueenMod;
import QueenMod.actions.DistributeSwarmAction;
import QueenMod.cards.*;
import QueenMod.cards.BlindingSwarm;
import QueenMod.effects.AmbushEffect;
import QueenMod.effects.SwarmEffect;
import QueenMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static QueenMod.QueenMod.makePowerPath;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY;
import static com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;

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

    private float particleTimer;
    private float particleTimer2;
    private ArrayList<AbstractMonster> targets = new ArrayList<>();
    private boolean playerHasSwarm = false;
    private int totalSwarm;
    private int realTotalSwarm;

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
    public void atStartOfTurn() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(KillerBee.ID)) {
                if (c.upgraded) {
                    c.baseDamage += 10;
                } else {
                    c.baseDamage += 8;
                }
            }
            if (c.cardID.equals(HiveDefenses.ID)) {
                AbstractMonster mon = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                AbstractDungeon.effectList.add(new AmbushEffect(c));
                CardCrawlGame.sound.playA("BEE_ATTACK2", (float)Math.random()*0.5f);
                c.calculateCardDamage(mon);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mon, new DamageInfo(AbstractDungeon.player, c.damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard c, UseCardAction a) {
        if (!(totalSwarm == 0)) {
            if (c.type.equals(AbstractCard.CardType.STATUS) || c.type.equals(AbstractCard.CardType.CURSE) || c.type.equals(AbstractCard.CardType.POWER)) {
                return;
            }
            else if (c.cardID.equals(Feast.ID)) {
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, true, totalSwarm, a));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(a.target, AbstractDungeon.player, SwarmPowerEnemy.POWER_ID));
            }
            else if (c.cardID.equals(Mark.ID)) {
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, true, totalSwarm, a));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(a.target, AbstractDungeon.player, SwarmPowerEnemy.POWER_ID));
            }
            else if (c.cardID.equals(Ravage.ID)) {
                AbstractCard tmp = c.makeStatEquivalentCopy();
                tmp.target = ALL_ENEMY;
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(tmp, true, totalSwarm, a));
            }
            else if (c.cardID.equals(Scatter.ID)) {
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, true, totalSwarm, a));
            }
            else if (c.cardID.equals(Feint.ID)){
                AbstractCard tmp = c.makeStatEquivalentCopy();
                tmp.target = AbstractCard.CardTarget.NONE;
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(tmp, false, totalSwarm, a));
            }
            else if (c.cardID.equals(Anticipate.ID) || c.cardID.equals(Parry.ID)) {
                AbstractCard tmp = c.makeStatEquivalentCopy();
                tmp.target = AbstractCard.CardTarget.SELF;
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(tmp, false, totalSwarm, a));
            } else if (c.cardID.equals(Frenzy.ID)) {
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, true, totalSwarm, a));
            } else if (c.cardID.equals(SplitStrike.ID) && totalSwarm > 1) {
                if (!a.target.isDying  && !a.target.halfDead) {
                    int temp = totalSwarm / 2;
                    totalSwarm -= temp;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new FocusedSwarm(AbstractDungeon.player, AbstractDungeon.player, temp), temp));
                    AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, true, totalSwarm, a));
                }
            } else if (c.cardID.equals(BlindingSwarm.ID)) {
                AbstractCard tmp = c.makeStatEquivalentCopy();
                tmp.target = ALL_ENEMY;
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(tmp, true, totalSwarm, a));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DistributeSwarmAction(c, false, totalSwarm, a));
            }
        }
    }

    @Override
    public void updateParticles(){
        totalSwarm = 0;
        realTotalSwarm = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(SwarmPowerEnemy.POWER_ID) && !m.isDying && !m.halfDead) {
                totalSwarm += m.getPower(SwarmPowerEnemy.POWER_ID).amount;
                realTotalSwarm += m.getPower(SwarmPowerEnemy.POWER_ID).amount;
            }
            if (m.hasPower(FocusedSwarmE.POWER_ID)){
                realTotalSwarm += m.getPower(FocusedSwarmE.POWER_ID).amount;
            }
        }
        if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
            totalSwarm += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
            realTotalSwarm += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(FocusedSwarm.POWER_ID)) {
            realTotalSwarm += AbstractDungeon.player.getPower(FocusedSwarm.POWER_ID).amount;
        }
        if (!Settings.DISABLE_EFFECTS && AbstractDungeon.getCurrRoom().phase.equals(COMBAT)) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (particleTimer < 0.0F) {
                playerHasSwarm = (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID) || AbstractDungeon.player.hasPower(FocusedSwarm.POWER_ID));
                targets.clear();
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if ((mo.hasPower(SwarmPowerEnemy.POWER_ID) || mo.hasPower(FocusedSwarmE.POWER_ID))) {
                        targets.add(mo);
                    }
                }
                this.particleTimer = 1.0F;
                if (!(realTotalSwarm == 0)) {
                    AbstractDungeon.effectsQueue.add(new SwarmEffect(realTotalSwarm, playerHasSwarm, targets));
                }
            }
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