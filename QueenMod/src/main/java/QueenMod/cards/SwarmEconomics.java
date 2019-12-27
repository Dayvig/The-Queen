package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.SwarmEconomicsAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class SwarmEconomics extends AbstractDynamicCard{

    public static final String ID = QueenMod.makeID(SwarmEconomics.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}

    // /STAT DECLARATION/
    int totalSwarm;

    public SwarmEconomics() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 5;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        totalSwarm = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.hasPower(SwarmPowerEnemy.POWER_ID)) {
                totalSwarm += mo.getPower(SwarmPowerEnemy.POWER_ID).amount;
            }
        }
        if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
            totalSwarm += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
        }
        AbstractDungeon.actionManager.addToBottom(new SwarmEconomicsAction(p, totalSwarm, magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
            initializeDescription();
        }
    }
}
