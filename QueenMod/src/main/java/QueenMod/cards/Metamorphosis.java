package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.SwapCardAction;
import QueenMod.characters.TheQueen;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Metamorphosis extends AbstractDynamicCard implements ModalChoice.Callback {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Metamorphosis.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private ModalChoice modal;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;
    private static final int COST = 1;  // COST = ${COST}
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = ${UPGRADED_COST}
    public int k;
    public int v;
    // /STAT DECLARATION/


    public Metamorphosis() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        AbstractDynamicCard tmp1 = new Hornet();
        AbstractDynamicCard tmp2 = new BumbleBee();
        AbstractDynamicCard tmp3 = new WorkerBee();
        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.RED) // Sets color of any following cards to colorless
                .addOption("Hornet: Deal " + tmp1.baseDamage + " Damage.", CardTarget.NONE)
                .setColor(CardColor.BLUE) // Sets color of any following cards to red
                .addOption("Bumblebee: Gain " + (tmp2.baseBlock) + " Block.", CardTarget.NONE)
                .setColor(CardColor.GREEN) // Sets color of any following cards to green
                .addOption("Honey Bee: Gain " + tmp3.magicNumber + " Nectar", CardTarget.NONE)
                .create();
        modal.generateTooltips();
    }

    private void SwapAll(AbstractPlayer p, AbstractCard r){
        for (AbstractCard c : p.drawPile.group){
            if (c.cardID.equals(Drone.ID)){
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, r, p.drawPile));
            }
        }
        for (AbstractCard c : p.hand.group){
            if (c.cardID.equals(Drone.ID)){
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, r, p.hand));
            }
        }
        for (AbstractCard c : p.discardPile.group){
            if (c.cardID.equals(Drone.ID)){
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, r, p.discardPile));
            }
        }
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new RainbowCardEffect()));
    }

    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i) {
        CardColor color;
        switch (i) {
            case 0:
                color = CardColor.RED;
                break;
            case 1:
                color = CardColor.BLUE;
                break;
            case 2:
                color = CardColor.GREEN;
                break;
            default:
                return;
        }
        if (color == CardColor.RED) {
            SwapAll(p,new Hornet());
        } else if (color == CardColor.BLUE) {
            SwapAll(p,new BumbleBee());
        } else if (color == CardColor.GREEN) {
            SwapAll(p,new WorkerBee());
        }
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
}
