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

public class HexGuard extends AbstractDynamicCard
{
    public static final String ID = QueenMod.makeID(HexGuard.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final int COST = 2;
    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;
    public static final int BLOCK = 10;
    public static final int UPGRADE_PLUS_BLOCK = 2;
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
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) &&
                AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, p.getPower(Nectar.POWER_ID), 10));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
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