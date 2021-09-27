package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.ApplyPowerActionFast;
import QueenMod.actions.FeastAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Feast extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Feast.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("feast.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int DAMAGE = 10;
    // /STAT DECLARATION/


    public Feast() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.isEthereal = true;
    }

    @Override
    public void applyPowers(){
        calcSwarm();
        super.applyPowers();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c){
        applyPowers();
    }

    public void atTurnStart(){
        applyPowers();
    }

    void calcSwarm(){
        int totalSwarm = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(SwarmPowerEnemy.POWER_ID) && !m.isDying) {
                totalSwarm += m.getPower(SwarmPowerEnemy.POWER_ID).amount;
            }
            if (m.hasPower(FocusedSwarmE.POWER_ID) && !m.isDying) {
                totalSwarm += m.getPower(FocusedSwarmE.POWER_ID).amount;
            }
        }
        if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
            totalSwarm += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(FocusedSwarm.POWER_ID)) {
            totalSwarm += AbstractDungeon.player.getPower(FocusedSwarm.POWER_ID).amount;
        }
        baseDamage = damage = totalSwarm + DAMAGE;
        initializeDescription();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("BEE_SLOW", -0.2F);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect((m.hb.cX + (float)Math.random()*40f - 20f) * Settings.scale, (m.hb.cY + (float)Math.random()*40.0F - 20f - 40.0F) * Settings.scale, Color.SCARLET.cpy()), 0.5F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
