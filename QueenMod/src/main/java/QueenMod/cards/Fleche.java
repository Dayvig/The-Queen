package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static QueenMod.QueenMod.makeCardPath;

public class Fleche extends AbstractDynamicCard {

        // TEXT DECLARATION

        public static final String ID = QueenMod.makeID(Fleche.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
        public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
        // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        private static final String DESCRIPTION = cardStrings.DESCRIPTION;
        private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

        // /TEXT DECLARATION/


        // STAT DECLARATION

        private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
        private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
        private static final CardType TYPE = CardType.ATTACK;       //
        public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

        private static final int COST = 1;  // COST = ${COST}

        private static final int DAMAGE = 13;    // DAMAGE = ${DAMAGE}
        private static final int UPGRADE_PLUS_DAMAGE = 4;    // DAMAGE = ${DAMAGE}

    private int numLeft;
    ArrayList<AbstractCard> upgradeMatrix = new ArrayList<AbstractCard>();

        // /STAT DECLARATION/


        public Fleche() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
            super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
            baseDamage = DAMAGE;
        }


        // Actions the card should do.
        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
                for (AbstractCard c : p.drawPile.group) {
                    if (c.type.equals(CardType.SKILL)) {
                        upgradeMatrix.add(c);
                    }
                }
                for (int i = 0; i<2;i++){
                    if (!upgradeMatrix.isEmpty()) {
                        AbstractCard tmp = upgradeMatrix.remove(AbstractDungeon.cardRandomRng.random(upgradeMatrix.size()-1));
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(tmp, p.drawPile, true));
                    }
                    else {
                        break;
                    }
                }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
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
