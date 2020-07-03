package QueenMod.cards;
import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.HoneyBoost;
import QueenMod.powers.Nectar;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static QueenMod.QueenMod.makeCardPath;

public class Honeyblaster extends AbstractDynamicCard
{
    public static final String ID = QueenMod.makeID(Honeyblaster.class.getSimpleName());
    public static final String IMG = makeCardPath("blaster.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final int COST = 1;
    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DAMAGE = 1;
    private static final int MAGIC = 1;

    public Honeyblaster()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int boost = 0;
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10) {
            boost += (int) Math.floor(AbstractDungeon.player.getPower(Nectar.POWER_ID).amount / 10);
        }
        for (int i = 0; i < 2 + boost; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID) && AbstractDungeon.player.getPower(Nectar.POWER_ID).amount >= 10){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            this.rawDescription = DESCRIPTION;
            this.rawDescription += "(x"+(int)Math.floor(AbstractDungeon.player.getPower(Nectar.POWER_ID).amount / 10)+")";
            initializeDescription();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            this.rawDescription = DESCRIPTION;
            initializeDescription();
        }
    }



    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Honeyblaster();
    }
}