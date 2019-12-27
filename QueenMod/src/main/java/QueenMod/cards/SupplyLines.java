package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.*;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class SupplyLines extends AbstractDynamicCard implements ModalChoice.Callback{

    public static final String ID = QueenMod.makeID(SupplyLines.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}

    // /STAT DECLARATION/
    int totalSwarm;
    ModalChoice modal;

    public SupplyLines() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = 6;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 5){
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 10 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 5){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p,Nectar.POWER_ID,5));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SwarmPower(p,p,magicNumber+2),magicNumber+2));
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 20 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Gain " + (magicNumber) + " Swarm.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Gain " + (magicNumber+4) + " Swarm.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();

        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 20){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Gain " + (magicNumber) + " Swarm.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Gain " + (magicNumber+4) + " Swarm.", CardTarget.SELF)
                    .setColor(CardColor.RED) // Sets color of any following cards to green
                    .addOption("Spend 20 Nectar. Gain " + (magicNumber+4) + " Swarm. Draw 2 cards.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();
        }
    }

    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        CardColor color;
        switch (i) {
            case 0:
                color = CardColor.GREEN;
                break;
            case 1:
                color = CardColor.BLUE;
                break;
            case 2:
                color = CardColor.RED;
                break;
            default:
                return;
        }
        if (color == CardColor.GREEN){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p,Nectar.POWER_ID,5));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SwarmPower(p,p,magicNumber),magicNumber));
        } else if (color == CardColor.BLUE){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p,Nectar.POWER_ID,10));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SwarmPower(p,p,magicNumber+4),magicNumber+4));
        } else if (color == CardColor.RED){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p,Nectar.POWER_ID,20));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SwarmPower(p,p,magicNumber+4),magicNumber+4));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}
