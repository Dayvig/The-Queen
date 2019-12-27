package QueenMod.cards;
import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static QueenMod.QueenMod.makeCardPath;

public class HoneycombSmash extends AbstractDynamicCard implements ModalChoice.Callback
{
    public static final String ID = QueenMod.makeID(HoneycombSmash.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final int COST = 1;
    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;
    public static final int DAMAGE = 8;
    public static final int UPGRADE_PLUS_DAMAGE = 3;
    private ModalChoice modal;

    public HoneycombSmash()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 5){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1,false), 1));
        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount < 10 &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 5){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                    .addOption("Deal " + baseDamage + " Damage. Apply 1 Weak.", CardTarget.ENEMY)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Spend 5 Nectar. Deal " + (baseDamage+5) + " Damage. Apply 2 Weak.", CardTarget.ENEMY)
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
                    .addOption("Deal " + baseDamage + " Damage. Apply 1 Weak.", CardTarget.ENEMY)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Deal " + (baseDamage+4) + " Damage. Apply 2 Weak.", CardTarget.ENEMY)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Deal " + (baseDamage + 8) + " Damage. Apply 3 Weak.", CardTarget.ENEMY)
                    .create();
            modal.generateTooltips();
            modal.open();

        }
        else if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 20){
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                    .addOption("Deal " + baseDamage + " Damage. Apply 1 Weak.", CardTarget.ENEMY)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Deal " + (baseDamage+4) + " Damage. Apply 2 Weak.", CardTarget.ENEMY)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Spend 10 Nectar. Deal " + (baseDamage + 8) + " Damage. Apply 3 Weak.", CardTarget.ENEMY)
                    .setColor(CardColor.RED) // Sets color of any following cards to green
                    .addOption("Spend 20 Nectar. Gain " + (baseDamage + 12) + " Damage. Apply 3 Weak. Enemy Loses 6 Strength this turn.", CardTarget.ENEMY)
                    .create();
            modal.generateTooltips();
            modal.open();
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1,false), 1));
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
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1,false), 1));
        } else if (color == CardColor.GREEN){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 5));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage+4, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 2,false), 2));
        } else if (color == CardColor.BLUE){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 5));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage+8, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 3,false), 3));
        } else if (color == CardColor.RED){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, Nectar.POWER_ID, 5));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage+12, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 3,false), 3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -6), -6));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, 6), 6));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new HoneycombSmash();
    }
}