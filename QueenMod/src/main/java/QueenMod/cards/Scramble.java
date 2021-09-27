package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.DrawSpecificCardTypeAction;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.actions.RecruitAction;
import QueenMod.actions.ScrambleAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
import QueenMod.powers.SwarmPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Scramble extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Scramble.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("scramble.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 2;  // COST = ${COST}
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 2;
    private static final int SECOND_MAGIC = 4;
    private static final int UPGRADE_SECOND_MAGIC = 1;
    // /STAT DECLARATION/
    private CardType typeToDraw;

    public Scramble() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, p.hand.size(), true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SwarmPower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(defaultSecondMagicNumber));
    }

    /*
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
    }*/

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDefaultSecondMagicNumber(UPGRADE_SECOND_MAGIC);
            initializeDescription();
        }
    }
}

