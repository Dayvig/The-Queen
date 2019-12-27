package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.SwapCardAction;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class PheremoneSwap extends AbstractDynamicCard {

    public static final String ID = QueenMod.makeID(PheremoneSwap.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = ${UPGRADED_COST}
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /STAT DECLARATION/


    public PheremoneSwap() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard h = new Hornet();
        AbstractCard b = new BumbleBee();
        AbstractCard bu = new BumbleBee();
        bu.upgrade();
        AbstractCard hu = new Hornet();
        hu.upgrade();
        for (AbstractCard c : p.drawPile.group){
            if (c.cardID.equals(Hornet.ID)){
                if (!c.upgraded){
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, b, p.drawPile));
                }
                else {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, bu, p.drawPile));
                }
            }
            else if (c.cardID.equals(BumbleBee.ID)) {
                if (!c.upgraded) {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, h, p.drawPile));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, hu, p.drawPile));
                }
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.cardID.equals(Hornet.ID)) {
                if (!c.upgraded) {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, b, p.hand));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, bu, p.hand));
                }
            } else if (c.cardID.equals(BumbleBee.ID)) {
                if (!c.upgraded) {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, h, p.hand));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, hu, p.hand));
                }
            }
        }
        for (AbstractCard c : p.discardPile.group){
            if (c.cardID.equals(Hornet.ID)) {
                if (!c.upgraded) {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, b, p.discardPile));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, bu, p.discardPile));
                }
            } else if (c.cardID.equals(BumbleBee.ID)) {
                if (!c.upgraded) {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, h, p.discardPile));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, hu, p.discardPile));
                }
            }
        }
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
