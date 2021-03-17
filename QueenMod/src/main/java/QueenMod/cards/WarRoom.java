package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.AddToTopOfDrawPileAction;
import QueenMod.actions.DrawSpecificCardTypeAction;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javax.smartcardio.Card;

import static QueenMod.QueenMod.makeCardPath;
import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;

// public class ${NAME} extends AbstractDynamicCard
public class WarRoom extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(WarRoom.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("pheremones.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST};
    private CardType typeToDraw;

    // /STAT DECLARATION/


    public WarRoom() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
        this.selfRetain = false;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        typeToDraw = StrategicDraw(p);
        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(p.drawPile, magicNumber, typeToDraw));
    }

    public CardType StrategicDraw(AbstractPlayer p){
        int numAttacks = 0;
        int numSkills = 0;
        for (AbstractCard c : p.hand.group) {
            if (!c.equals(this)) {
                switch (c.type) {
                    case ATTACK:
                        numAttacks++;
                        break;
                    case SKILL:
                        numSkills++;
                        break;
                    default:
                }
            }
        }
        if (numAttacks > numSkills){
            System.out.println("Skills");
            return CardType.SKILL;
        }
        else if (numSkills > numAttacks){
            System.out.println("Attacks");
            return CardType.ATTACK;
        }
        else {
            System.out.println("Powers");
            return CardType.POWER;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
