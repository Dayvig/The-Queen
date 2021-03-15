package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.RecruitAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class SwordsToPlowshares extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(SwordsToPlowshares.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("plowshares.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int MAGIC = 8;
    private static final int UPGRADE_MAGIC = 2;
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 2;
    boolean isActive = false;

    // /STAT DECLARATION/


    public SwordsToPlowshares() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseBlock = block = BLOCK;
        this.cardsToPreview = new HoneyBee();
        isActive = false;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Nectar(p, p, magicNumber), magicNumber));
        if (isActive) {
            this.target = CardTarget.SELF;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new HoneyBee(), 1));
        }
        else {
            this.target = CardTarget.NONE;
        }
    }

    public void triggerOnGlowCheck() {
        int numAttacks = 0;
        int numOther = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.type.equals(AbstractCard.CardType.ATTACK)){numAttacks++;}
            else if (c.type.equals(AbstractCard.CardType.SKILL)){numOther++;}
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.type.equals(AbstractCard.CardType.ATTACK)){numAttacks++;}
            else if (c.type.equals(AbstractCard.CardType.SKILL)){numOther++;}
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c.type.equals(AbstractCard.CardType.ATTACK)){numAttacks++;}
            else if (c.type.equals(AbstractCard.CardType.SKILL)){numOther++;}
        }
        if (numAttacks < numOther){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            isActive = true;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            isActive = false;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
