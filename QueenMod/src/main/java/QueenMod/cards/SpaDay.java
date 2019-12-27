package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.RecruitAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.Nectar;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static QueenMod.QueenMod.makeCardPath;

public class SpaDay extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(SpaDay.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public SpaDay() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(WeakPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Nectar(p, p, p.getPower(WeakPower.POWER_ID).amount*magicNumber), p.getPower(WeakPower.POWER_ID).amount*magicNumber));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p, WeakPower.POWER_ID));
        }
        if (p.hasPower(VulnerablePower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Nectar(p, p, p.getPower(VulnerablePower.POWER_ID).amount*magicNumber), p.getPower(VulnerablePower.POWER_ID).amount*magicNumber));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p, VulnerablePower.POWER_ID));
        }
        if (p.hasPower(FrailPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Nectar(p, p, p.getPower(FrailPower.POWER_ID).amount*magicNumber), p.getPower(FrailPower.POWER_ID).amount*magicNumber));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p, FrailPower.POWER_ID));
        }
        AbstractDungeon.actionManager.addToBottom(new RecruitAction(new WorkerBee(), 1));
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        defaultSecondMagicNumber = 0;
        if (AbstractDungeon.player.hasPower(WeakPower.POWER_ID)){
            defaultSecondMagicNumber += AbstractDungeon.player.getPower(WeakPower.POWER_ID).amount*magicNumber;
        }
        if (AbstractDungeon.player.hasPower(VulnerablePower.POWER_ID)){
            defaultSecondMagicNumber += AbstractDungeon.player.getPower(VulnerablePower.POWER_ID).amount*magicNumber;
        }
        if (AbstractDungeon.player.hasPower(FrailPower.POWER_ID)){
            defaultSecondMagicNumber += AbstractDungeon.player.getPower(FrailPower.POWER_ID).amount*magicNumber;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
