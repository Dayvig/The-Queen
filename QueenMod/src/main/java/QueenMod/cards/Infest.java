package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.AmbushAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
import QueenMod.powers.SwarmPower;
import QueenMod.powers.SwarmPowerEnemy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Infest extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Infest.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("incubate.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 2;  // COST = ${COST}
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;
    private static final int MAGIC2 = 2;
    // /STAT DECLARATION/


    public Infest() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = MAGIC2;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amnt = magicNumber;
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            int boost = (int)Math.floor(AbstractDungeon.player.getPower(Nectar.POWER_ID).amount/10);
            for (int i = 0;i<boost;i++){
                amnt += defaultSecondMagicNumber;
            }
        }
         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SwarmPower(p, p, amnt), amnt));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new SwarmPowerEnemy(mo, p, amnt), amnt));
        }
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDefaultSecondMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
