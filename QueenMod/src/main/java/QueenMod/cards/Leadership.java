package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.RecruitAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.ProductionPower;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Leadership extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Leadership.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("leadership.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int MAGIC = 1;
    // /STAT DECLARATION/


    public Leadership() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isInnate = false;
        this.baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
