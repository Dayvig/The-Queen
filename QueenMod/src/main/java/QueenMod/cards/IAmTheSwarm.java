package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.BecomeSwarmAction;
import QueenMod.characters.TheQueen;
import QueenMod.effects.GatherEffect;
import QueenMod.powers.FromScratch;
import QueenMod.powers.GeneralPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static QueenMod.QueenMod.makeCardPath;

public class IAmTheSwarm extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Weirdness Apply X (+1) keywords to yourself.
     */

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(IAmTheSwarm.class.getSimpleName());
    public static final String IMG = makeCardPath("iamswarm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 3;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/

    public IAmTheSwarm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new GatherEffect(true));
        CardCrawlGame.sound.playA("BEE_FADE", -0.2F + (float) Math.random() * -0.3F);
        AbstractDungeon.actionManager.addToBottom(new BecomeSwarmAction(magicNumber));
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