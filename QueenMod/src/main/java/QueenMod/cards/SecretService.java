package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.LoadCardImageAction;
import QueenMod.characters.TheQueen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.ArrayList;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class SecretService extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(SecretService.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int UPGRADED_COST = 0;
    String[] SSText = new String[7];
    // /STAT DECLARATION/
    AbstractCard cardToPreview1 = new WASP();
    boolean bullshit;
    int containsHornet;
    int containsBumblebee;
    int containsHoneybee;
    int containsDrone;
    ArrayList<AbstractCard> hornetMatrix = new ArrayList<>();
    ArrayList<AbstractCard> bumblebeeMatrix = new ArrayList<>();
    ArrayList<AbstractCard> honeybeeMatrix = new ArrayList<>();
    ArrayList<AbstractCard> droneMatrix = new ArrayList<>();

    AbstractCard toRemove;

    public SecretService() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        containsHornet = 0;
        containsBumblebee = 0;
        containsHoneybee = 0;
        containsDrone = 0;
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCard c = new WASP(containsHornet, containsBumblebee, containsHoneybee, containsDrone);

        if (!hornetMatrix.isEmpty()){
            toRemove = hornetMatrix.remove(AbstractDungeon.cardRandomRng.random(hornetMatrix.size()));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(toRemove, p.drawPile, true));
        }
        if (!bumblebeeMatrix.isEmpty()){
            toRemove = bumblebeeMatrix.remove(AbstractDungeon.cardRandomRng.random(bumblebeeMatrix.size()));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(toRemove, p.drawPile, true));
        }
        if (!honeybeeMatrix.isEmpty()){
            toRemove = honeybeeMatrix.remove(AbstractDungeon.cardRandomRng.random(honeybeeMatrix.size()));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(toRemove, p.drawPile, true));
        }
        if (!droneMatrix.isEmpty()){
            toRemove = droneMatrix.remove(AbstractDungeon.cardRandomRng.random(droneMatrix.size()));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(toRemove, p.drawPile, true));
        }
        System.out.println(c.cost);
        System.out.println(c.cardID);
        if (upgraded){c.upgrade();}
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
        this.unhover();
        AbstractDungeon.player.hand.refreshHandLayout();
    }


    @Override
    public void hover() {
        if (!this.bullshit) {
            containsHornet = 0;
            containsBumblebee = 0;
            containsHoneybee = 0;
            containsDrone = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.cardID.equals(Hornet.ID) && containsHornet < 3) {
                    containsHornet = 1;
                    if (c.upgraded) {
                        containsHornet = 2;
                    }
                    hornetMatrix.add(c);
                }
                if (c.cardID.equals(HornetCommander.ID)) {
                    containsHornet = 3;
                    if (c.upgraded) {
                        containsHornet = 4;
                    }
                    for (AbstractCard d : hornetMatrix) {
                        if (d.cardID.equals(Hornet.ID)) {
                            hornetMatrix.remove(d);
                        }
                    }
                    hornetMatrix.add(c);
                }
                if (c.cardID.equals(BumbleBee.ID) && containsBumblebee < 3) {
                    containsBumblebee = 1;
                    if (c.upgraded) {
                        containsBumblebee = 2;
                    }
                    bumblebeeMatrix.add(c);
                }
                if (c.cardID.equals(BumbleBeeCommander.ID)) {
                    containsBumblebee = 3;
                    if (c.upgraded) {
                        containsBumblebee = 4;
                    }
                    for (AbstractCard d : bumblebeeMatrix) {
                        if (d.cardID.equals(BumbleBee.ID)) {
                            bumblebeeMatrix.remove(d);
                        }
                    }
                    bumblebeeMatrix.add(c);
                }
                if (c.cardID.equals(WorkerBee.ID) && containsHoneybee < 3) {
                    containsHoneybee = 1;
                    if (c.upgraded) {
                        containsHoneybee = 2;
                    }
                    honeybeeMatrix.add(c);
                }
                if (c.cardID.equals(WorkerBeeCommander.ID)) {
                    containsHoneybee = 3;
                    if (c.upgraded) {
                        containsHoneybee = 4;
                    }
                    for (AbstractCard d : honeybeeMatrix) {
                        if (d.cardID.equals(WorkerBee.ID)) {
                            honeybeeMatrix.remove(d);
                        }
                    }
                    bumblebeeMatrix.add(c);
                }
                if (c.cardID.equals(Drone.ID) && containsDrone < 3) {
                    containsDrone = 1;
                    if (c.upgraded) {
                        containsDrone = 2;
                    }
                    droneMatrix.add(c);
                }
                if (c.cardID.equals(DroneCommander.ID)) {
                    containsDrone = 3;
                    if (c.upgraded) {
                        containsDrone = 4;
                    }
                    for (AbstractCard d : droneMatrix) {
                        if (d.cardID.equals(Drone.ID)) {
                            droneMatrix.remove(d);
                        }
                    }
                    droneMatrix.add(c);
                }
            }
            cardToPreview1.rawDescription = createText(containsHornet, containsBumblebee, containsHoneybee, containsDrone);
            cardToPreview1.initializeDescription();
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(cardToPreview1, makeCardPath("Attack.png"), true));
            super.hover();
            this.bullshit = true;
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        this.bullshit = false;
    }

    public void renderCardTip(SpriteBatch sb) {
        if ((this.cardToPreview1 != null) && (!Settings.hideCards) && (this.bullshit)) {
            float tmpScale = this.drawScale / 1.5F;

            if ((AbstractDungeon.player != null) && (AbstractDungeon.player.isDraggingCard)) {
                return;
            }

            if (this.current_x > Settings.WIDTH * 0.75F) {
                this.cardToPreview1.current_x = this.current_x + (((AbstractCard.IMG_WIDTH / 2.0F) + ((AbstractCard.IMG_WIDTH / 2.0F) / 1.5F) + (16.0F)) * this.drawScale);
            } else {
                this.cardToPreview1.current_x = this.current_x - (((AbstractCard.IMG_WIDTH / 2.0F) + ((AbstractCard.IMG_WIDTH / 2.0F) / 1.5F) + (16.0F)) * this.drawScale);
            }

            this.cardToPreview1.current_y = this.current_y + ((AbstractCard.IMG_HEIGHT / 2.0F)) * this.drawScale;

            this.cardToPreview1.drawScale = tmpScale;

            this.cardToPreview1.render(sb);

            if ((!Settings.hideCards) && (this.bullshit)) {
                if ((SingleCardViewPopup.isViewingUpgrade) && (this.isSeen) && (!this.isLocked)) {
                    AbstractCard copy = makeStatEquivalentCopy();
                    copy.current_x = this.current_x;
                    copy.current_y = this.current_y;
                    copy.drawScale = this.drawScale;
                    copy.upgrade();

                    TipHelper.renderTipForCard(copy, sb, copy.keywords);
                } else {
                    super.renderCardTip(sb);
                }
            }
        }
    }

        public String createText(int h, int b, int ho, int d){
        if (h != 0){
            if (h == 1 || h == 3) {
                SSText[0] = "Deal 11 damage.";
            }
            else {
                SSText[0] = "Deal 16 damage.";
            }
        }
        else {
            SSText[0] = "";
        }
        if (b != 0){
            if (b == 1 || b == 3) {
                SSText[1] = "Gain 10 Block.";
            }
            else {
                SSText[1] = "Gain 13 Block.";
            }
        }
        else {
            SSText[1] = "";
            SSText[4] = "";
        }
        if (ho != 0){
            if (ho == 1 || ho == 3){
                SSText[2] = "Gain 4 Nectar.";
            }
            else {
                SSText[2] = "Gain 6 Nectar.";
            }
        }
        else {
            SSText[2] = "";
        }
        if (d == 1 || d == 3){
            SSText[3] = "Draw a card.";
        }
        else if (d == 2 || d == 4){
            SSText[3] = "Draw 2 cards.";
        }
        else if (d == 0){
            SSText[3] = "";
        }
        if (d < 3){
            SSText[5] = "";
        }
        if (b != 0){
            SSText[4] = "Recall your queenmod:Swarm.";
        }
        if (d > 2){
            SSText[5] = "Upgrade all queenmod:Drones in your draw pile.";
        }
        if (h > 2 && b < 3){
            SSText[6] = "When you play a queenmod:Hive card this turn, deal !queenmod:M2! damage to a random enemy.";
        }
        else if (b > 2 && h < 3){
            SSText[6] = "When you play a queenmod:Hive card this turn, gain !queenmod:M3! Block next turn.";
        }
        else if (b > 2 && h > 2){
            SSText[6] = "When you play a queenmod:Hive card this turn, deal !queenmod:M2! damage to a random enemy and gain !queenmod:M3! Block next turn.";
        }
        else {
            SSText[6] = "";
        }
        return SSText[0]+SSText[1]+" NL "+SSText[2]+SSText[3]+SSText[4]+SSText[5]+ " NL "+SSText[6];
        }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
