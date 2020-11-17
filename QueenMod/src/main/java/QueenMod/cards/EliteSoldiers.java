package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.actions.UpgradeAllHive;
import QueenMod.characters.TheQueen;
import QueenMod.interfaces.IsHive;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class EliteSoldiers extends AbstractDynamicCard {

    public static final String ID = QueenMod.makeID(EliteSoldiers.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("elites.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int UPGRADED_COST = 0;  // COST = ${COST}

    AbstractCard chosen;

    // /STAT DECLARATION/


    public EliteSoldiers() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileActionFast(new Drone(), 1, true, false));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileActionFast(new Hornet(), 1, true, false));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileActionFast(new BumbleBee(), 1, true, false));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileActionFast(new WorkerBee(), 1, true, false));
        AbstractDungeon.actionManager.addToBottom(new UpgradeAllHive());
        CardCrawlGame.sound.play("ORB_SLOT_GAIN", -0.2F);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
