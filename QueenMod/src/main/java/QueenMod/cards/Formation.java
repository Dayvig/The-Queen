package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.interfaces.IsHive;
import QueenMod.powers.Nectar;
import QueenMod.powers.PollinatePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Formation extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Drain.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("drain.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 2;  // COST = ${COST}

    private static int DAMAGE = 0;    // DAMAGE = ${DAMAGE}
    private static int BLOCK = 0;
    private static int MAGIC = 0;
    private static int NECTAR = 0;
    private String[] CardText = new String[3];
    private int [] intent = new int[3];
    private int[] value = new int[3];
    // /STAT DECLARATION/


    public Formation() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public Formation(AbstractCard c1, AbstractCard c2, AbstractCard c3) { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        if (c1 instanceof IsHive){
            addEffect(c1, c1.cardID, 0);
        }
        if (c2 instanceof IsHive){
            addEffect(c2, c2.cardID, 1);
        }
        if (c3 instanceof IsHive){
            addEffect(c3, c3.cardID, 2);
        }
        if (DAMAGE > 0){
            TARGET = CardTarget.ENEMY;
            if (BLOCK > 0){
                TARGET = CardTarget.SELF_AND_ENEMY;
                this.baseBlock = block = BLOCK;
            }
        }
        else if (BLOCK > 0){
            TARGET = CardTarget.SELF;
        }
        else {
            TARGET = CardTarget.NONE;
            this.target = TARGET;
        }
    }

    private void addEffect(AbstractCard card, String ID, int num){
        switch (ID){
            case "Drone":
                MAGIC += card.baseMagicNumber;
                CardText[num] = EXTENDED_DESCRIPTION[0] + card.baseMagicNumber + EXTENDED_DESCRIPTION[1] + " NL ";
                break;
            case "Hornet":
                DAMAGE += card.baseDamage;
                CardText[num] = EXTENDED_DESCRIPTION[2] + card.baseDamage + EXTENDED_DESCRIPTION[3] + " NL ";
                break;
            case "BumbleBee":
                BLOCK += card.baseBlock;
                CardText[num] = EXTENDED_DESCRIPTION[4] + card.baseBlock + EXTENDED_DESCRIPTION[5] + " NL ";
                break;
            case "HoneyBee":
                NECTAR += card.baseMagicNumber;
                CardText[num] = EXTENDED_DESCRIPTION[6] + card.baseBlock + EXTENDED_DESCRIPTION[7] + " NL ";
                break;
            default:
                System.out.println("Something went wrong");
        }
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
