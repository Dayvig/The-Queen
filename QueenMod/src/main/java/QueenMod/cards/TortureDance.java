package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.DistributeSwarmAction;
import QueenMod.actions.UpgradeCardInDeckAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.FocusedSwarm;
import QueenMod.powers.FocusedSwarmE;
import QueenMod.powers.SwarmPower;
import QueenMod.powers.SwarmPowerEnemy;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class TortureDance extends AbstractDynamicCard {

    public static final String ID = QueenMod.makeID(TortureDance.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("torture.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    String desc;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Dance");
        TEXT = uiStrings.TEXT;
    }
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = -2;  // COST = ${COST}
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DAMAGE = 6;

    private int combo;
    private static final int COMBO_LENGTH = 3;
    CardType dance[] = new CardType[COMBO_LENGTH];
    String[] danceDescriptions = new String[COMBO_LENGTH];
    String danceDescAlt = "";
    int danceMoves;
    boolean danceSet = false;
    // /STAT DECLARATION/


    public TortureDance() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        isMultiDamage = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        this.cantUseMessage = EXTENDED_DESCRIPTION[1];
        return false;
    }

    @Override
    public void triggerOnOtherCardPlayed (AbstractCard c){
        if (c.type.equals(dance[combo])){
            combo++;
            if (combo == COMBO_LENGTH){
                AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
                this.flash();
                combo = 0;
                desc = "";
                for (int i=0;i<COMBO_LENGTH;i++) {
                    desc += danceDescriptions[i];
                }
                desc += " NL "+EXTENDED_DESCRIPTION[0];
                this.rawDescription = desc;
                initializeDescription();
            }
            else if (dance[combo].equals(CardType.ATTACK)){
                String temp = "[#ff0000]"+danceDescriptions[combo]+"[]";
                desc = "";
                desc += danceDescAlt;
                for (int i=1;i<COMBO_LENGTH;i++) {
                    if (!(i==combo)){ desc += danceDescriptions[i];}
                    else {desc += temp;}
                }
                desc += " NL "+EXTENDED_DESCRIPTION[0];
                this.rawDescription = desc;
                initializeDescription();
            }
            else if (dance[combo].equals(CardType.SKILL)){
                String temp = "[#00FF00]"+danceDescriptions[combo]+"[]";
                desc = "";
                desc += danceDescAlt;
                for (int i=1;i<COMBO_LENGTH;i++) {
                    if (!(i==combo)){ desc += danceDescriptions[i];}
                    else {desc += temp;}
                }
                desc += " NL "+EXTENDED_DESCRIPTION[0];
                this.rawDescription = desc;
                initializeDescription();
            }
            else if (dance[combo].equals(CardType.POWER)){
                String temp = "[#0000FF]"+danceDescriptions[combo]+"[]";
                desc = "";
                desc += danceDescAlt;
                for (int i=1;i<COMBO_LENGTH;i++) {
                    if (!(i==combo)){ desc += danceDescriptions[i];}
                    else {desc += temp;}
                }
                desc += " NL "+EXTENDED_DESCRIPTION[0];
                this.rawDescription = desc;
                initializeDescription();
            }
        }
        else {
            combo = 0;
            desc = "";
            for (int i=0;i<COMBO_LENGTH;i++) {
                desc += danceDescriptions[i];
            }
            desc += " NL "+EXTENDED_DESCRIPTION[0];
            this.rawDescription = desc;
            initializeDescription();
        }
        System.out.println(combo);
    }

    @Override
    public void atTurnStart() {
        setDanceMoves();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (!danceSet) {
            setDanceMoves();
        }
    }
    private boolean powersPresent(){
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.type.equals(CardType.POWER)){
                return true;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.type.equals(CardType.POWER)){
                return true;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c.type.equals(CardType.POWER)){
                return true;
            }
        }
        return false;
    }

    public void setDanceMoves(){
        danceMoves = AbstractDungeon.cardRandomRng.random(100);

        //determines if the player has a power in their current deck.
        boolean generatePowers = powersPresent();

        //Sets the first move of the dance
        if (danceMoves <= 45) {
            dance[0] = CardType.SKILL;
            danceDescriptions[0] = TEXT[1];
            danceDescriptions[0] += ", ";
            danceDescAlt = TEXT[2];
        } else if (danceMoves > 45 && danceMoves <= 90) {
            dance[0] = CardType.ATTACK;
            danceDescriptions[0] = TEXT[3];
            danceDescriptions[0] += ", ";
            danceDescAlt = TEXT[4];
        }
        //10% chance: if powers can be generated, generates a power, otherwise generates an attack.
        else {
            if (generatePowers) {
                dance[0] = CardType.POWER;
                danceDescriptions[0] = TEXT[5];
                danceDescriptions[0] += ", ";
                danceDescAlt = TEXT[6];
            }
            else {
                dance[0] = CardType.ATTACK;
                danceDescriptions[0] = TEXT[3];
                danceDescriptions[0] += ", ";
                danceDescAlt = TEXT[4];
            }
        }
        for (int i = 1; i < COMBO_LENGTH; i++) {
            danceMoves = AbstractDungeon.cardRandomRng.random(100);
            if (dance[i - 1].equals(CardType.ATTACK)) {
                if (danceMoves <= 40) {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = TEXT[7];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = TEXT[8];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            } else if (dance[i - 1].equals(CardType.SKILL)) {
                if (danceMoves <= 40) {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = TEXT[8];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = TEXT[7];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            } else if (dance[i - 1].equals(CardType.POWER)) {
                if (danceMoves <= 50) {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = TEXT[8];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = TEXT[7];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            } else {
                if (danceMoves <= 40) {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = TEXT[7];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else if (danceMoves <= 80) {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = TEXT[7];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.POWER;
                    danceDescriptions[i] = TEXT[7];
                    if (i == COMBO_LENGTH-1) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            }
        }
        String s;
        String s2 = "";
        if (upgraded){ s = EXTENDED_DESCRIPTION[0]; }
        else { s = EXTENDED_DESCRIPTION[3]; }
        for (int k = 0; k < COMBO_LENGTH; k++){
            s2 += danceDescriptions[k];
        }
        this.rawDescription = TEXT[9] + s2 +" NL "+ s;
        combo = 0;
        initializeDescription();
        danceSet = true;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
