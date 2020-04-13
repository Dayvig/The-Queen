package QueenMod.cards;

        import QueenMod.QueenMod;
        import QueenMod.actions.DistributeSwarmAction;
        import QueenMod.actions.FeastAction;
        import QueenMod.characters.TheQueen;
        import QueenMod.powers.FocusedSwarm;
        import QueenMod.powers.FocusedSwarmE;
        import QueenMod.powers.SwarmPower;
        import QueenMod.powers.SwarmPowerEnemy;
        import basemod.helpers.ModalChoice;
        import com.badlogic.gdx.graphics.Color;
        import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.animations.VFXAction;
        import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
        import com.megacrit.cardcrawl.actions.common.DamageAction;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.core.CardCrawlGame;
        import com.megacrit.cardcrawl.core.Settings;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.localization.CardStrings;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;
        import com.megacrit.cardcrawl.powers.WeakPower;
        import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

        import static QueenMod.QueenMod.makeCardPath;

public class Feast extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Feast.class.getSimpleName());
    public static final String IMG = makeCardPath("feast.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int MAGIC = 5;

    // /STAT DECLARATION/


    public Feast() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && mo.currentHealth >= 0) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(mo.hb.cX, mo.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
                    AbstractDungeon.actionManager.addToBottom(new FeastAction(mo, new DamageInfo(p, damage, damageTypeForTurn), damage, this.uuid, magicNumber));
                }
            }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
