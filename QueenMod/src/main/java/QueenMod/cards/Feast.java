package QueenMod.cards;

        import QueenMod.QueenMod;
        import QueenMod.actions.FeastAction;
        import QueenMod.characters.TheQueen;
        import QueenMod.powers.SwarmPower;
        import QueenMod.powers.SwarmPowerEnemy;
        import basemod.helpers.ModalChoice;
        import com.megacrit.cardcrawl.actions.animations.VFXAction;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.core.CardCrawlGame;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.localization.CardStrings;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 0;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int MAGIC = 6;
    private ModalChoice modal;
    int totalSwarm;

    // /STAT DECLARATION/


    public Feast() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new FeastAction(m, new DamageInfo(p, damage, damageTypeForTurn), this.magicNumber));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new BiteEffect(m.hb_x, m.hb_y)));
    }

    @Override
    public void applyPowers(){
        totalSwarm = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(SwarmPowerEnemy.POWER_ID)) {
                totalSwarm += m.getPower(SwarmPowerEnemy.POWER_ID).amount;
            }
        }
        if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
            totalSwarm += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
        }
        this.baseDamage = totalSwarm;
        if (upgraded){totalSwarm += UPGRADE_PLUS_DAMAGE;}
        this.rawDescription = EXTENDED_DESCRIPTION;
        this.initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
