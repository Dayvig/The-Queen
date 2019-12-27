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
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static QueenMod.QueenMod.makeCardPath;

public class Nourish extends AbstractDynamicCard implements ModalChoice.Callback
{
    public static final String ID = QueenMod.makeID(Nourish.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final int COST = 1;
    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = ${UPGRADED_COST}
    private ModalChoice modal;

    public Nourish()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 5){
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 10 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 5){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 5));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,5));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.hand));
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 20 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Heal " + 5 + " HP.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Heal " + 10 + " HP.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 20){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Heal " + 5 + " HP.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Heal " + 10 + " HP.", CardTarget.SELF)
                    .setColor(CardColor.RED) // Sets color of any following cards to green
                    .addOption("Spend 20 Nectar. Heal " + 15 + " HP and remove all Debuffs.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();
        }
        else {
        }
    }

    // This is called when one of the option cards us chosen
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

        AbstractCard c;

        if (color == CardColor.GREEN){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 5));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,5));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.hand));
        } else if (color == CardColor.BLUE){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 10));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,10));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.hand));
        } else if (color == CardColor.RED) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 20));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 15));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, p.hand));
            Iterator var2 = p.powers.iterator();
            while (var2.hasNext()) {
                AbstractPower pow = (AbstractPower) var2.next();
                if (pow.type.equals(AbstractPower.PowerType.DEBUFF)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, pow));
                }
            }
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Nourish();
    }
}