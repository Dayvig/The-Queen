package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class DiscoHex extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(DiscoHex.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("discohex.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}

    private static final int BLOCK = 2;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private float grooveTimer = 0f;
    private float grooveWindow = 1.0f;
    private AbstractCard[] grooveList = { new Groove(), new LeftStep(), new RightStep() };
    private int index = 0;
    // /STAT DECLARATION/


    public DiscoHex() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = grooveList[index];
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int numToMake = BaseMod.MAX_HAND_SIZE - (p.hand.size()+1);
        int actualNumToMake = numToMake/2;
        numToMake = numToMake - actualNumToMake;

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(grooveList[0]));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(grooveList[1], numToMake));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(grooveList[2], actualNumToMake));
    }

    @Override
    public void update(){
        super.update();
        grooveTimer += Gdx.graphics.getDeltaTime();
        if (grooveTimer > grooveWindow){
            index++;
            if (index > grooveList.length-1){ index = 0; }
            this.cardsToPreview = grooveList[index];
            grooveTimer = 0.0f;
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            for (int i = 0;i<grooveList.length;i++){
                grooveList[i].upgrade();
            }
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
