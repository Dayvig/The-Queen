package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.RecruitAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.AttackNextTurnPower;
import QueenMod.powers.IndustryPower;
import QueenMod.powers.SkillNextTurnPower;
import QueenMod.powers.SwarmPower;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ReprogramAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.StrengthPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class PlanOfAction extends AbstractDynamicCard implements ModalChoice.Callback{

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(PlanOfAction.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int UPGRADED_COST = 0;
    private ModalChoice modal;
    // /STAT DECLARATION/


    public PlanOfAction() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isInnate = true;
        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.COLORLESS) // Sets color of any following cards to colorless
                .setTitle("Balanced")
                .addOption("Gain 1 Strength, 1 Dexterity, 1 Industry and 4 queenmod:Swarm. Add 3 queenmod:Drones to your draw pile.", CardTarget.NONE)
                .setColor(CardColor.RED) // Sets color of any following cards to colorless
                .setTitle("Aggressive")
                .addOption("Gain 3 Strength. queenmod:Recruit a queenmod:Hornet.", CardTarget.NONE)
                .setColor(CardColor.BLUE) // Sets color of any following cards to colorless
                .setTitle("Defensive")
                .addOption("Gain 3 Dexterity. queenmod:Recruit a queenmod:Bumblebee.", CardTarget.NONE)
                .setColor(CardColor.GREEN) // Sets color of any following cards to colorless
                .setTitle("Industrial")
                .addOption("Gain 3 Industry and 4 queenmod:Swarm. queenmod:Recruit a queenmod:Workerbee", CardTarget.NONE)
                .create();
        modal.generateTooltips();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    @Override
    public void optionSelected(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster, int i) {
        switch (i){
            case 0:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new IndustryPower(abstractPlayer, abstractPlayer, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new SwarmPower(abstractPlayer, abstractPlayer, 4), 4));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Drone(), 3, true, false));
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, 3), 3));
                AbstractDungeon.actionManager.addToBottom(new RecruitAction(new Hornet(), 1));
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, 3), 3));
                AbstractDungeon.actionManager.addToBottom(new RecruitAction(new BumbleBee(), 1));
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new IndustryPower(abstractPlayer, abstractPlayer, 3), 3));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new SwarmPower(abstractPlayer, abstractPlayer, 4), 4));
                AbstractDungeon.actionManager.addToBottom(new RecruitAction(new WorkerBee(), 1));
            default:
        }
    }
}
