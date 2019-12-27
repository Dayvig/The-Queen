package QueenMod.cards;
import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
import QueenMod.powers.SwarmPower;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

public class HexGuard extends AbstractDynamicCard implements ModalChoice.Callback
{
    public static final String ID = QueenMod.makeID(HexGuard.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final int COST = 2;
    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;
    public static final int BLOCK = 10;
    public static final int UPGRADE_PLUS_BLOCK = 3;
    private ModalChoice modal;

    public HexGuard()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 5){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 10 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 5){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                    .addOption("Gain " + baseBlock + " Block.", CardTarget.SELF)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Gain " + (baseBlock + 6) + " Block.", CardTarget.SELF)
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
                    .addOption("Gain " + baseBlock + " Block.", CardTarget.SELF)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Gain " + (baseBlock + 6) + " Block.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Gain " + (baseBlock + 12) + " Block.", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();

        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 20){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                    .addOption("Gain " + baseBlock + " Block.", CardTarget.SELF)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Gain " + (baseBlock + 6) + " Block.", CardTarget.SELF)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Gain " + (baseBlock + 12) + " Block.", CardTarget.SELF)
                    .setColor(CardColor.RED) // Sets color of any following cards to green
                    .addOption("Spend 20 Nectar. Gain " + (baseBlock + 12) + " Block and 4 Swarm", CardTarget.SELF)
                    .create();
            modal.generateTooltips();
            modal.open();
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
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

        AbstractCard c;
        if (color == CardColor.COLORLESS) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        } else if (color == CardColor.GREEN){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 5));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block+6));
        } else if (color == CardColor.BLUE){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 10));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block+12));
        } else if (color == CardColor.RED){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 20));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block+12));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(p,p, 4), 4));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new HexGuard();
    }
}