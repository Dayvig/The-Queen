package QueenMod.cards;

        import QueenMod.QueenMod;
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
        import com.megacrit.cardcrawl.actions.common.DamageAction;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.core.CardCrawlGame;
        import com.megacrit.cardcrawl.core.Settings;
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
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 0;
    private int totalSwarm;

    // /STAT DECLARATION/


    public Feast() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void applyPowers(){
        totalSwarm = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if ((mo.hasPower(SwarmPowerEnemy.POWER_ID)) && !mo.isDying) {
                totalSwarm += mo.getPower(SwarmPowerEnemy.POWER_ID).amount;
            }
            if ((mo.hasPower(FocusedSwarmE.POWER_ID)) && !mo.isDying) {
                totalSwarm += mo.getPower(FocusedSwarmE.POWER_ID).amount;
            }

        }
        if (AbstractDungeon.player.hasPower(SwarmPower.POWER_ID)) {
            totalSwarm += AbstractDungeon.player.getPower(SwarmPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(FocusedSwarm.POWER_ID)){
            totalSwarm += AbstractDungeon.player.getPower(FocusedSwarm.POWER_ID).amount;
        }
        this.baseDamage = totalSwarm;
        if (upgraded) {
            this.rawDescription = EXTENDED_DESCRIPTION[0];
        }
        else {
            this.rawDescription = EXTENDED_DESCRIPTION[0];
            }
        this.initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
