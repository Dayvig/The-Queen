package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

public class Fortify extends AbstractDynamicCard {



    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Fortify.class.getSimpleName());
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

    private static final int COST = 3;
    private static final int BLOCK = 4;

    // /STAT DECLARATION/


    public Fortify() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : p.drawPile.group){
            if (c.cardID.equals(Hornet.ID) ||
                    c.cardID.equals(BumbleBee.ID) ||
                    c.cardID.equals(Drone.ID) ||
                    c.cardID.equals(WorkerBee.ID) ||
                    c.cardID.equals(HornetCommander.ID) ||
                    c.cardID.equals(BumbleBeeCommander.ID) ||
                    c.cardID.equals(DroneCommander.ID) ||
                    c.cardID.equals(WorkerBeeCommander.ID) ||
                    c.cardID.equals(WASP.ID)){
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            }
        }
    }

    @Override
    public void triggerWhenDrawn(){
        int n = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Hornet.ID) ||
                    c.cardID.equals(BumbleBee.ID) ||
                    c.cardID.equals(Drone.ID) ||
                    c.cardID.equals(WorkerBee.ID) ||
                    c.cardID.equals(HornetCommander.ID) ||
                    c.cardID.equals(BumbleBeeCommander.ID) ||
                    c.cardID.equals(DroneCommander.ID) ||
                    c.cardID.equals(WorkerBeeCommander.ID) ||
                    c.cardID.equals(WASP.ID)){
                n++;
            }
        }
        this.rawDescription = "Gain "+ block +" Block "+n+" times. (Equal to number of queenmod:Hive cards in your Draw Pile)";
        initializeDescription();
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        int n = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Hornet.ID) ||
                    c.cardID.equals(BumbleBee.ID) ||
                    c.cardID.equals(Drone.ID) ||
                    c.cardID.equals(WorkerBee.ID) ||
                    c.cardID.equals(HornetCommander.ID) ||
                    c.cardID.equals(BumbleBeeCommander.ID) ||
                    c.cardID.equals(DroneCommander.ID) ||
                    c.cardID.equals(WorkerBeeCommander.ID) ||
                    c.cardID.equals(WASP.ID)){
                n++;
            }
        }
        this.rawDescription = "Gain "+ block +" Block "+n+" times. (Equal to number of queenmod:Hive cards in your Draw Pile)";
        initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(1);
            initializeDescription();
        }
    }
}
