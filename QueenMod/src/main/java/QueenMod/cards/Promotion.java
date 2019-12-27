package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.PromotionAction;
import QueenMod.characters.TheQueen;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Promotion extends AbstractDynamicCard implements ModalChoice.Callback {

    public static final String ID = QueenMod.makeID(Promotion.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}
    private ModalChoice modal;
    public boolean seriousUpgrade=false;
    public String [] modalID = new String[4];


    // /STAT DECLARATION/


    public Promotion() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean containsHornet=false;
        boolean containsBumblebee=false;
        boolean containsHoneybee=false;
        boolean containsDrone=false;
        for (AbstractCard c :p.drawPile.group){
            if (c.cardID.equals(Hornet.ID)){
                containsHornet=true;
            }
            if (c.cardID.equals(BumbleBee.ID)){
                containsBumblebee=true;
            }
            if (c.cardID.equals(WorkerBee.ID)){
                containsHoneybee=true;
            }
            if (c.cardID.equals(Drone.ID)){
                containsDrone=true;
            }
        }
        buildMassivePromotionChoice(containsHornet,containsBumblebee,containsHoneybee,containsDrone,upgraded);
    }

    public void buildMassivePromotionChoice(boolean h, boolean b, boolean hb, boolean d, boolean upgr){
        if (!upgr) {
            if (h && b && hb && d) {
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modalID[2] = WorkerBee.ID;
                modalID[3] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 4 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 1 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 2 Pollinate.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && b && hb) {
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modalID[2] = WorkerBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 4 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 1 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 2 Pollinate.", CardTarget.ENEMY)
                        .create();
                modal.open();
                return;
            } else if (h && b && d) {
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modalID[2] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 4 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 1 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && hb && d) {
                modalID[0] = Hornet.ID;
                modalID[1] = WorkerBee.ID;
                modalID[2] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 4 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 2 Pollinate.", CardTarget.ENEMY)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (b && hb && d) {
                modalID[0] = BumbleBee.ID;
                modalID[1] = WorkerBee.ID;
                modalID[2] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 1 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 2 Pollinate.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && b) {
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 4 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 1 block next turn.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && hb) {
                modalID[0] = Hornet.ID;
                modalID[1] = WorkerBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 4 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 2 Pollinate.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && d) {
                modalID[0] = Hornet.ID;
                modalID[1] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 4 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (b && hb) {
                modalID[0] = BumbleBee.ID;
                modalID[1] = WorkerBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 1 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 2 Pollinate.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (b && d) {
                modalID[0] = BumbleBee.ID;
                modalID[1] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 1 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (hb && d) {
                modalID[0] = WorkerBee.ID;
                modalID[1] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 2 Pollinate.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, Hornet.ID, false));
            } else if (b) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, BumbleBee.ID, false));
            } else if (hb) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, WorkerBee.ID, false));
            } else if (d) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, Drone.ID, false));
            }
        }
        else {
            if (h && b && hb && d) {
                seriousUpgrade = true;
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modalID[2] = WorkerBee.ID;
                modalID[3] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 5 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 2 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 3 Pollinate.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && b && hb) {
                seriousUpgrade = true;
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modalID[2] = WorkerBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 5 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 2 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 3 Pollinate.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && b && d) {
                seriousUpgrade = true;
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modalID[2] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 5 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 2 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && hb && d) {
                seriousUpgrade = true;
                modalID[0] = Hornet.ID;
                modalID[1] = WorkerBee.ID;
                modalID[2] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 5 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 3 Pollinate.", CardTarget.ENEMY)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (b && hb && d) {
                seriousUpgrade = true;
                modalID[0] = BumbleBee.ID;
                modalID[1] = WorkerBee.ID;
                modalID[2] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 2 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 3 Pollinate.", CardTarget.ENEMY)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && b) {
                seriousUpgrade = true;
                modalID[0] = Hornet.ID;
                modalID[1] = BumbleBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 5 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 2 block next turn.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && hb) {
                seriousUpgrade = true;
                modalID[0] = Hornet.ID;
                modalID[1] = WorkerBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 5 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 3 Pollinate.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h && d) {
                seriousUpgrade = true;
                modalID[0] = Hornet.ID;
                modalID[1] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.RED) // Sets color of any following cards to colorless
                        .addOption("Hornet Commander: Promotes a Hornet. When you play a Hive card, deal 5 damage to a random enemy.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (b && hb) {
                seriousUpgrade = true;
                modalID[0] = BumbleBee.ID;
                modalID[1] = WorkerBee.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 2 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 3 Pollinate.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (b && d) {
                seriousUpgrade = true;
                modalID[0] = BumbleBee.ID;
                modalID[1] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.BLUE) // Sets color of any following cards to red
                        .addOption("Bumblebee Commander: Promotes a Bumblebee. When you play a Hive card, gain 2 block next turn.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (hb && d) {
                seriousUpgrade = true;
                modalID[0] = WorkerBee.ID;
                modalID[1] = Drone.ID;
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.GREEN)
                        .addOption("Honeybee Commander: Promotes a Honeybee to apply 3 Pollinate.", CardTarget.NONE)
                        .setColor(CardColor.COLORLESS) // Sets color of any following cards to red
                        .addOption("Drone Commander: Promotes a Drone to upgrade all Drones in your draw pile.", CardTarget.NONE)
                        .create();
                modal.open();
                return;
            } else if (h) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, Hornet.ID, true));
            } else if (b) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, BumbleBee.ID, true));
            } else if (hb) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, WorkerBee.ID, true));
            } else if (d) {
                AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, Drone.ID, true));
            }
        }
    }

        @Override
        public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
        {
            String id;
            System.out.println("Case: "+i);
            CardColor color;
            switch (i) {
                case 0:
                    id = modalID[0];
                    break;
                case 1:
                    id = modalID[1];
                    break;
                case 2:
                    id = modalID[2];
                    break;
                case 3:
                    id = modalID[3];
                    break;
                default:
                    return;
            }
            AbstractDungeon.actionManager.addToBottom(new PromotionAction(AbstractDungeon.player, id, seriousUpgrade));
        }

        @Override
        public boolean canUse(AbstractPlayer p, AbstractMonster m){
        super.canUse(p, m);
        for (AbstractCard c : p.drawPile.group){
            if (c.cardID.equals(Hornet.ID) ||
                    c.cardID.equals(BumbleBee.ID) ||
                    c.cardID.equals(Drone.ID) ||
                    c.cardID.equals(WorkerBee.ID)){
                return true;
            }
        }
        return false;
        }


        public void applyPowers() {
            boolean containsHornet = false;
            boolean containsBumblebee = false;
            boolean containsHoneybee = false;
            boolean containsDrone = false;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.cardID.equals(Hornet.ID)) {
                    containsHornet = true;
                }
                if (c.cardID.equals(BumbleBee.ID)) {
                    containsBumblebee = true;
                }
                if (c.cardID.equals(WorkerBee.ID)) {
                    containsHoneybee = true;
                }
                if (c.cardID.equals(Drone.ID)) {
                    containsDrone = true;
                }
            }
            if (containsHornet && !containsBumblebee && !containsHoneybee && !containsDrone){
                this.rawDescription += " NL (Promotes a Hornet)";
            }
            else if (!containsHornet && containsBumblebee && !containsHoneybee && !containsDrone){
                this.rawDescription += " NL (Promotes a Bumblebee)";
            }
            else if (!containsHornet && !containsBumblebee && containsHoneybee && !containsDrone){
                this.rawDescription += " NL (Promotes a Honeybee)";
            }
            else if (!containsHornet && !containsBumblebee && !containsHoneybee && containsDrone){
                this.rawDescription += " NL (Promotes a Drone)";
            }
            else {
                this.rawDescription = DESCRIPTION;
            }
        }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
