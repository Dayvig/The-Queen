package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.FocusedSwarm;
import QueenMod.powers.FocusedSwarmE;
import QueenMod.powers.SwarmPower;
import QueenMod.powers.SwarmPowerEnemy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static QueenMod.QueenMod.makeCardPath;

public class BlindingSwarm extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(BlindingSwarm.class.getSimpleName());
    public static final String IMG = makeCardPath("blindingswarm.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int MAGIC = 2;


    // /STAT DECLARATION/


    public BlindingSwarm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, magicNumber, false), magicNumber));
            CardCrawlGame.sound.playA("BEE_ATTACK2", -1.0F);

        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (mo.hasPower(SwarmPowerEnemy.POWER_ID) || mo.hasPower(FocusedSwarmE.POWER_ID)){
                return true;
            }
        }
        if (p.hasPower(SwarmPower.POWER_ID) || p.hasPower(FocusedSwarm.POWER_ID)){
            return true;
        }
        else { return false; }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
