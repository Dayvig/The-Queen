package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.DrawToHandAction;
import QueenMod.actions.RecruitAction;
import QueenMod.actions.SpecificCardToHandAction;
import QueenMod.characters.TheQueen;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static QueenMod.QueenMod.makeCardPath;

public class CallToArms extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(CallToArms.class.getSimpleName());
    public static final String IMG = makeCardPath("calltoarms.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    ArrayList<AbstractCard> droneMatrix = new ArrayList<>();


    // /STAT DECLARATION/


    public CallToArms() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /*int handsize = p.hand.size();
        for (AbstractCard c : p.drawPile.group){
            if (c.cardID.equals(Drone.ID)){
                droneMatrix.add(c);
            }
        }
        while (!droneMatrix.isEmpty() && handsize < BaseMod.MAX_HAND_SIZE){
            AbstractDungeon.actionManager.addToBottom(new DrawToHandAction(droneMatrix.remove(AbstractDungeon.cardRandomRng.random(droneMatrix.size()-1))));
            handsize++;
        }
        */

        CardCrawlGame.sound.playA("TRUMPET", (float)Math.random()*0.2F);

        int handsize = p.hand.size();
        while (handsize < BaseMod.MAX_HAND_SIZE){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Drone(), 1));
            handsize++;
        }
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new BumbleBee(), 1));
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new Hornet(), 1));
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new WorkerBee(), 1));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
