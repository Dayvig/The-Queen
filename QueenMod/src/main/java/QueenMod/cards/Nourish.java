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

public class Nourish extends AbstractDynamicCard
{
    public static final String ID = QueenMod.makeID(Nourish.class.getSimpleName());
    public static final String IMG = makeCardPath("nourish.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final int COST = 1;
    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int UPGRADED_COST = 0; // UPGRADED_COST = ${UPGRADED_COST}
    private static final int MAGIC = 10;

    public Nourish()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        baseMagicNumber = magicNumber = MAGIC;
        this.tags.add(CardTags.HEALING);
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, p.getPower(Nectar.POWER_ID), 10));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, magicNumber));
        }
    }

    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
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