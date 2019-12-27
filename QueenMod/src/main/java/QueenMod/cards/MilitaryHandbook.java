package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class MilitaryHandbook extends AbstractDynamicCard implements ModalChoice.Callback{

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(MilitaryHandbook.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}
    private static final int MAGIC = 2;
    private ModalChoice modal;
    // /STAT DECLARATION/


    public MilitaryHandbook() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 5){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 10 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 5){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                    .addOption("Draw "+magicNumber+" cards. Exhaust.", CardTarget.SELF)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Draw "+(magicNumber+1)+" cards. Exhaust.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 20 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                    .addOption("Draw "+magicNumber+" cards. Exhaust.", CardTarget.SELF)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Draw "+(magicNumber+1)+" cards. Exhaust.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Draw "+(magicNumber+2)+" cards. Exhaust.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();

        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 20){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                    .addOption("Draw "+magicNumber+" cards. Exhaust.", CardTarget.SELF)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Draw "+(magicNumber+1)+" cards. Exhaust.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Draw "+(magicNumber+2)+" cards. Exhaust.", CardTarget.SELF)
                    .setColor(CardColor.RED) // Sets color of any following cards to green
                    .addOption("Spend 20 Nectar. Draw " + (magicNumber+2) + " cards. Reduce the cost of Attacks in your hand by 1 this turn. Exhaust.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        }
    }

    // This is called when one of the option cards us chosen
    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        CardColor color;
        switch (i) {
            case 0:
                color = CardColor.COLORLESS;
                break;
            case 1:
                color = CardColor.GREEN;
                break;
            case 2:
                color = CardColor.BLUE;
                break;
            case 3:
                color = CardColor.RED;
                break;
            default:
                return;
        }

        if (color == CardColor.COLORLESS) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        } else if (color == CardColor.GREEN){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber+1));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 5));
        } else if (color == CardColor.BLUE){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber+2));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 10));
        } else if (color == CardColor.RED){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber+2));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 20));
            for (AbstractCard c : AbstractDungeon.player.hand.group){
                if (c.type.equals(CardType.ATTACK)){
                    System.out.println("Trace");
                    c.costForTurn--;
                }
            }
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

