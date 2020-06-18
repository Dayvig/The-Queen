package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Feint extends AbstractDynamicCard {

    public static final String ID = QueenMod.makeID(Feint.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("feint.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}
    private static final int MAGIC = 1;
    public boolean attackPlayed;

    // /STAT DECLARATION/


    public Feint() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = this.magicNumber = MAGIC;
        attackPlayed = false;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(m, 1, false)));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        super.canUse(p,m);
        if (attackPlayed){
            return true;
        }
        else {
            this.cantUseMessage = "The last card I played has to be an attack.";
            return false;
        }
    }

    public void applyPowers() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() != 0) {
            attackPlayed =
                    AbstractDungeon.actionManager.cardsPlayedThisTurn.get(
                            AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1).type.equals(CardType.ATTACK);
        }
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
