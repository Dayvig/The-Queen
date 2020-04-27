package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.DrawToHandAction;
import QueenMod.actions.RecruitAction;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThirdEyeEffect;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Anticipate extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Anticipate.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("anticipate.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}

    private AbstractMonster.Intent intentions;
    // /STAT DECLARATION/


    public Anticipate() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Drone.ID)) {
                AbstractDungeon.actionManager.addToTop(new DrawToHandAction(c));
                break;
            }
        }
        if (m.intent.equals(AbstractMonster.Intent.ATTACK) ||
                m.intent.equals(AbstractMonster.Intent.ATTACK_BUFF) ||
                m.intent.equals(AbstractMonster.Intent.ATTACK_DEBUFF) ||
                m.intent.equals(AbstractMonster.Intent.ATTACK_DEFEND)
        )
        {
            AbstractCard tmp = new BumbleBee();
            if (upgraded){ tmp.upgrade(); }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(tmp, 1));
        }
        else {
            AbstractCard tmp = new BumbleBee();
            if (upgraded){ tmp.upgrade(); }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(tmp, 1));
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThirdEyeEffect(p.hb.cX, p.hb.cY)));
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
