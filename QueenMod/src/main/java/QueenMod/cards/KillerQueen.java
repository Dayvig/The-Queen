package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class KillerQueen extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(KillerQueen.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 2;  // COST = ${COST}
    private static final int UPGRADED_COST = 2; // UPGRADED_COST = ${UPGRADED_COST}
    public static final String EXTENDED_DESCRIPTION = "I must have more Attacks than Skills and Powers to play this!";
    private static final int DAMAGE = 24;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_DMG = 6;  // UPGRADE_PLUS_DMG = ${UPGRADED_DAMAGE_INCREASE}
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /STAT DECLARATION/


    public KillerQueen() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            this.rawDescription = DESCRIPTION;
            initializeDescription();
            int numAttacks = 0;
            int numOther = 0;
            for (AbstractCard c : p.hand.group){
                if (c.type.equals(CardType.ATTACK)){numAttacks++;}
                else if (c.type.equals(CardType.SKILL) || c.type.equals(CardType.POWER)){numOther++;}
            }
            for (AbstractCard c : p.drawPile.group){
                if (c.type.equals(CardType.ATTACK)){numAttacks++;}
                else if (c.type.equals(CardType.SKILL) || c.type.equals(CardType.POWER)){numOther++;}
            }
            for (AbstractCard c : p.discardPile.group){
                if (c.type.equals(CardType.ATTACK)){numAttacks++;}
                else if (c.type.equals(CardType.SKILL) || c.type.equals(CardType.POWER)){numOther++;}
            }
            this.rawDescription = DESCRIPTION + " NL Other: " +numOther+" | Attacks: "+numAttacks;
            initializeDescription();
            if (numAttacks > numOther){return true;}
            else {
                this.cantUseMessage = EXTENDED_DESCRIPTION;
                return false;}
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
