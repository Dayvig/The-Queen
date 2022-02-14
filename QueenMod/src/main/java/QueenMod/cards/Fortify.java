package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.effects.BeeAttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import static QueenMod.QueenMod.makeCardPath;

public class Fortify extends AbstractDynamicCard {



    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Fortify.class.getSimpleName());
    public static final String IMG = makeCardPath("fortify.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 2;
    public int numCards = 0;

    // /STAT DECLARATION/


    public Fortify() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new VFXAction(new BeeAttackEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 5, false, true), 0.01F));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    @Override
    public void update() {
        super.update();
            //checks if player is in combat or not to update text appropriately
        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)){
            if (upgraded) {
                this.rawDescription = UPGRADE_DESCRIPTION;
            }
            else {
                this.rawDescription = DESCRIPTION;
            }
            return;
        }

        if (upgraded) {
            baseBlock = AbstractDungeon.player.drawPile.group.size() + 5;
        }
        else {
            baseBlock = AbstractDungeon.player.drawPile.group.size();
        }
        if (upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0] + block + EXTENDED_DESCRIPTION[1];
        }
        else {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + block + EXTENDED_DESCRIPTION[1];
        }
        initializeDescription();
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
