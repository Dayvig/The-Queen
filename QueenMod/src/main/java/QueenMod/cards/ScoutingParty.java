package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.actions.RecruitAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
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
    public class ScoutingParty extends AbstractDynamicCard {

        // TEXT DECLARATION

        public static final String ID = QueenMod.makeID(ScoutingParty.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
        public static final String IMG = makeCardPath("honeycorps.png");// "public static final String IMG = makeCardPath("${NAME}.png");
        // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        private static final String DESCRIPTION = cardStrings.DESCRIPTION;


        // /TEXT DECLARATION/


        // STAT DECLARATION

        private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
        private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
        private static final CardType TYPE = CardType.SKILL;       //
        public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

        private static final int COST = 1;  // COST = ${COST}
        private static final int MAGIC = 3;
        // /STAT DECLARATION/


        public ScoutingParty() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
            super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
            this.baseMagicNumber = this.magicNumber = MAGIC;
        }


        // Actions the card should do.
        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));
            if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
                int boost = (int)Math.floor(AbstractDungeon.player.getPower(Nectar.POWER_ID).amount/10);
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(boost));
            }
        }

        public void triggerOnGlowCheck() {
            if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                this.rawDescription = DESCRIPTION;
                this.rawDescription += "(x"+(int)Math.floor(AbstractDungeon.player.getPower(Nectar.POWER_ID).amount / 10)+")";
                initializeDescription();
            } else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
                this.rawDescription = DESCRIPTION;
                initializeDescription();
            }
        }



        // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                this.upgradeMagicNumber(1);
                initializeDescription();
            }
        }
    }

