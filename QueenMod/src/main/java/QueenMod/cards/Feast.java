package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
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
    public static final String IMG = makeCardPath("ruthless.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 2;
    // /STAT DECLARATION/


    public Feast() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage= damage = 0;
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        this.rawDescription = EXTENDED_DESCRIPTION[0];
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
        baseDamage = totalSwarm + magicNumber;
        initializeDescription();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SwarmPower(p,p,this.magicNumber),this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
