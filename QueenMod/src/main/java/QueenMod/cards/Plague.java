package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.AmbushAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.SwarmPower;
import QueenMod.powers.SwarmPowerEnemy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Plague extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Plague.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("plague.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = -1;  // COST = ${COST}
    private static final int MAGIC = 5;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    // /STAT DECLARATION/


    public Plague() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        freeToPlayOnce = false;
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect;
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        effect = this.energyOnUse;
        if (p.hasRelic(ChemicalX.ID)){ effect += 2; }
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
        for (int i=0;i<effect;i++){
            int numTargets = 1;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!(mo.isDead || mo.halfDead || mo.isDying)){
                    numTargets++;
                }
            }
            if ((int)(Math.random()*numTargets) == 0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SwarmPower(p, p, this.magicNumber), this.magicNumber));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), p, new SwarmPowerEnemy(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), p, this.magicNumber), this.magicNumber));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
