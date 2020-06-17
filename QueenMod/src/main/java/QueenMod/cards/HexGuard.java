package QueenMod.cards;
import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.HoneyBoost;
import QueenMod.powers.Nectar;
import QueenMod.powers.SwarmPower;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static QueenMod.QueenMod.makeCardPath;

public class HexGuard extends AbstractDynamicCard
{
    public static final String ID = QueenMod.makeID(HexGuard.class.getSimpleName());
    public static final String IMG = makeCardPath("hexguard.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final int COST = 2;
    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;
    public static final int BLOCK = 14;
    public static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    public HexGuard()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            int boost = (int)Math.floor(AbstractDungeon.player.getPower(Nectar.POWER_ID).amount/10);
            for (int i = 0;i<boost;i++){
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, magicNumber));
            }
        }
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(HoneyBoost.POWER_ID)){
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
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new HexGuard();
    }
}