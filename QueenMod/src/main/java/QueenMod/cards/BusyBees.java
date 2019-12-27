package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class BusyBees extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(BusyBees.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = ${UPGRADED_COST}

    // /STAT DECLARATION/


    public BusyBees() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            for (AbstractCard c : p.hand.group) {
                if (!c.cardID.equals(BusyBees.ID) && !c.type.equals(CardType.STATUS) &&  !c.type.equals(CardType.CURSE)) {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c.makeStatEquivalentCopy(), 1, true, false, false));
                    c.upgrade();
                }
            }
        } else {
            for (AbstractCard c : p.hand.group) {
                if (c.cardID.equals(Hornet.ID) ||
                        c.cardID.equals(BumbleBee.ID) ||
                        c.cardID.equals(Drone.ID) ||
                        c.cardID.equals(WorkerBee.ID) ||
                        c.cardID.equals(HornetCommander.ID) ||
                        c.cardID.equals(BumbleBeeCommander.ID) ||
                        c.cardID.equals(DroneCommander.ID) ||
                        c.cardID.equals(WorkerBeeCommander.ID)) {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c.makeStatEquivalentCopy(), 1, true, false, false));
                    c.upgrade();
                }
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
