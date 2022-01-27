package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Lunge extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Lunge.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("lunge.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}

    private static final int DAMAGE = 4;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    public boolean skillPlayed;
    // /STAT DECLARATION/
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Lunge");
        TEXT = uiStrings.TEXT;
    }

    public Lunge() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        skillPlayed = false;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        super.canUse(p,m);
        if (skillPlayed){
            return true;
        }
        else {
            this.cantUseMessage = TEXT[0];
            return false;
        }
    }

    public void applyPowers() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() != 0) {
            skillPlayed =
                    AbstractDungeon.actionManager.cardsPlayedThisTurn.get(
                            AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1).type.equals(CardType.SKILL);
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
