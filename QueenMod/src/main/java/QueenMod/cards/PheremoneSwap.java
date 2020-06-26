package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.SwapCardAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.ProductionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javafx.concurrent.Worker;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class PheremoneSwap extends AbstractDynamicCard {

    public static final String ID = QueenMod.makeID(PheremoneSwap.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("metamorphosis.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /STAT DECLARATION/


    public PheremoneSwap() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.retain = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SwapAll(p.drawPile);
        SwapAll(p.hand);
        SwapAll(p.discardPile);
        if (upgraded){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        }
    }

    private void SwapAll(CardGroup g){
        for (AbstractCard c : g.group) {
            if (c.cardID.equals(Hornet.ID)) {
                AbstractCard d = new BumbleBee();
                if (c.upgraded){
                    d.upgrade();
                }
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, d, g));
            }
            else if (c.cardID.equals(BumbleBee.ID)) {
                AbstractCard d = new Hornet();
                if (c.upgraded){
                    d.upgrade();
                }
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, d, g));
            }
            else if (c.cardID.equals(WorkerBee.ID)) {
                AbstractCard d = new Drone();
                if (c.upgraded){
                    d.upgrade();
                }
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, d, g));
            }
            else if (c.cardID.equals(Drone.ID)) {
                AbstractCard d = new WorkerBee();
                if (c.upgraded){
                    d.upgrade();
                }
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, d, g));
            }
        }
    }

        // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
