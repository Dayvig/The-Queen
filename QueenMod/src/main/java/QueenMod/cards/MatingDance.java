package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.UpgradeCardInDeckAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.SwarmPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class MatingDance extends AbstractDynamicCard {

    public static final String ID = QueenMod.makeID(MatingDance.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    String desc;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = -2;  // COST = ${COST}
    private int combo;
    CardType dance[] = new CardType[4];
    String[] danceDescriptions = new String[4];
    int danceMoves;
    // /STAT DECLARATION/


    public MatingDance() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 5;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 2;
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
            if (dance[combo].equals(CardType.ATTACK)){
                String temp = "[#ff0000]"+danceDescriptions[combo]+"[]";
                desc = "";
                for (int i=0;i<dance.length;i++) {
                    if (!(i==combo)){ desc += danceDescriptions[i];}
                    else {desc += temp;}
                }
                desc += " NL "+EXTENDED_DESCRIPTION[0];
                this.rawDescription = desc;
            }
            else if (dance[combo].equals(CardType.SKILL)){
                String temp = "[#00FF00]"+danceDescriptions[combo]+"[]";
                desc = "";
                for (int i=0;i<dance.length;i++) {
                    if (!(i==combo)){ desc += danceDescriptions[i];}
                    else {desc += temp;}
                }
                desc += " NL "+EXTENDED_DESCRIPTION[0];
                this.rawDescription = desc;
            }
            else if (dance[combo].equals(CardType.POWER)){
                String temp = "[#0000FF]"+danceDescriptions[combo]+"[]";
                desc = "";
                for (int i=0;i<dance.length;i++) {
                    if (!(i==combo)){ desc += danceDescriptions[i];}
                    else {desc += temp;}
                }
                desc += " NL "+EXTENDED_DESCRIPTION[0];
                this.rawDescription = desc;
            }
            combo++;
            initializeDescription();
        }
        else {
            combo = 0;
            desc = "";
            for (int i=0;i<dance.length;i++) {
                desc += danceDescriptions[i];
            }
            desc += " NL "+EXTENDED_DESCRIPTION[0];
            this.rawDescription = desc;
            initializeDescription();
        }
        if (combo == 4){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Drone(), defaultSecondMagicNumber, true, false));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, magicNumber),magicNumber));
            AbstractDungeon.actionManager.addToBottom(new UpgradeCardInDeckAction(defaultSecondMagicNumber));
            combo = 0;
            desc = "";
            for (int i=0;i<dance.length;i++) {
                desc += danceDescriptions[i];
            }
            desc += " NL "+EXTENDED_DESCRIPTION[0];
            this.rawDescription = desc;
            initializeDescription();
        }
        System.out.println(combo);
    }

    public void atTurnStart() {
        danceMoves = AbstractDungeon.cardRandomRng.random(100);
        if (danceMoves <= 40) {
            dance[0] = CardType.SKILL;
            danceDescriptions[0] = "Skill";
            danceDescriptions[0] += ", ";
        } else if (danceMoves > 40 && danceMoves <= 80) {
            dance[0] = CardType.ATTACK;
            danceDescriptions[0] = "Attack";
            danceDescriptions[0] += ", ";
        } else {
            dance[0] = CardType.POWER;
            danceDescriptions[0] = "Power";
            danceDescriptions[0] += ", ";
        }
        for (int i = 1; i < 4; i++) {
            danceMoves = AbstractDungeon.cardRandomRng.random(100);
            if (dance[i - 1].equals(CardType.ATTACK)) {
                if (danceMoves <= 40) {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = "Attack";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else if (danceMoves > 40 && danceMoves <= 90) {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = "Skill";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.POWER;
                    danceDescriptions[i] = "Power";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            } else if (dance[i - 1].equals(CardType.SKILL)) {
                if (danceMoves <= 40) {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = "Skill";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else if (danceMoves > 40 && danceMoves <= 90) {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = "Attack";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.POWER;
                    danceDescriptions[i] = "Power";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            } else if (dance[i - 1].equals(CardType.POWER)) {
                if (danceMoves <= 48) {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = "Skill";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else if (danceMoves > 48 && danceMoves <= 96) {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = "Attack";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.POWER;
                    danceDescriptions[i] = "Power";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            } else {
                if (danceMoves <= 40) {
                    dance[i] = CardType.SKILL;
                    danceDescriptions[i] = "Attack";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else if (danceMoves > 40 && danceMoves <= 80) {
                    dance[i] = CardType.ATTACK;
                    danceDescriptions[i] = "Attack";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                } else {
                    dance[i] = CardType.POWER;
                    danceDescriptions[i] = "Attack";
                    if (i == 3) {
                        danceDescriptions[i] += ".";
                    } else {
                        danceDescriptions[i] += ", ";
                    }
                }
            }
        }
        this.rawDescription = danceDescriptions[0]+danceDescriptions[1]+danceDescriptions[2]+danceDescriptions[3]+" NL "+
                EXTENDED_DESCRIPTION[0];
        combo = 0;
        initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(3);
            this.upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }
}
