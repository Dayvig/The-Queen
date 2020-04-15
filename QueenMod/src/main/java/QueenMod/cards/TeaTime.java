package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.*;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class TeaTime extends AbstractDynamicCard{

    public static final String ID = QueenMod.makeID(TeaTime.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("teatime.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;
    // /STAT DECLARATION/

    public TeaTime() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = 2;
        this.baseBlock = block = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, AbstractDungeon.player.getPower(Nectar.POWER_ID), 10));
        }
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }


    @Override
    public void applyPowers(){
        this.costForTurn = cost;
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.type.equals(CardType.ATTACK)){
                costForTurn++;
                if (costForTurn != COST){
                    isCostModifiedForTurn = true;
                }
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
