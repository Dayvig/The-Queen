package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.LoadCardImageAction;
import QueenMod.actions.UpgradeSpecificCardInDrawPileAction;
import QueenMod.characters.TheQueen;
import QueenMod.powers.BumbleBeeCommanderPower;
import QueenMod.powers.HornetCommanderPower;
import QueenMod.powers.Nectar;
import QueenMod.powers.StingerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class WASP extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(WASP.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 2;  // COST = ${COST}
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = ${UPGRADED_COST}
    public boolean isHornet;
    public boolean isBumblebee;
    public boolean isHoneyBee;
    public boolean isDrone;
    public boolean isHornetCommander;
    public boolean isBumbleBeeCommander;
    public boolean isHoneyBeeCommander;
    public boolean isDroneCommander;
    public boolean isUpgradedDrone;
    int hornet;
    int bumblebee;
    int honeybee;
    int drone;
    public boolean playedBySwarm = false;
    String[]SSText = new String[7];
    // /STAT DECLARATION/

    public WASP(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isHornet = false;
        isDrone = false;
        isBumblebee = false;
        isHoneyBee = false;
        isHornetCommander = false;
        isBumbleBeeCommander = false;
        isHoneyBeeCommander = false;
        isDroneCommander = false;
        isUpgradedDrone = false;
    }

    public WASP(int h, int b, int ho, int d) { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        hornet = h;
        bumblebee = b;
        honeybee = ho;
        drone = d;
        isHornet = (h!=0);
        isBumblebee = (b!=0);
        isHoneyBee = (ho!=0);
        isDrone = (d!=0);
        isUpgradedDrone = (d == 2 || d == 4);
        isHornetCommander = (h > 2);
        isBumbleBeeCommander = (b > 2);
        isHoneyBeeCommander = (ho > 2);
        isDroneCommander = (d > 2);
        if (ho > 2){
            this.target = CardTarget.ALL_ENEMY;
        }
        if (h != 0){
            this.type = CardType.ATTACK;
            this.target = CardTarget.ENEMY;
        }
        else {
            if (b != 0){
                this.target = CardTarget.SELF;
            }
            this.type = CardType.SKILL;
        }

        if (h == 1 || h == 3){
            if (AbstractDungeon.player.hasPower(StingerPower.POWER_ID)){
                this.baseDamage = damage = 11 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
            }
            else {
                this.baseDamage = damage = 11;
            }
        }
        else if (h == 2 || h == 4){
            if (AbstractDungeon.player.hasPower(StingerPower.POWER_ID)){
                this.baseDamage = damage = 15 + AbstractDungeon.player.getPower(StingerPower.POWER_ID).amount;
            }
            else {
                this.baseDamage = damage = 15;
            }
        }
        if (b == 1 || b == 3){
            this.baseBlock = block = 10;
        }
        else if (b == 2 || b == 4){
            this.baseBlock = block = 13;
        }
        if (ho == 1 || ho == 3){
            this.baseMagicNumber = magicNumber = 4;
        }
        else if (ho == 2 || ho == 4){
            this.baseMagicNumber = magicNumber = 6;
        }
        if (d == 1 || d == 3){
            this.defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 1;
        }
        else if (d == 2 || d == 4){
            this.defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 2;
        }
        if (h == 3){
            this.defaultBaseThirdMagicNumber = defaultThirdMagicNumber = 4;
        }
        else if (h == 4){
            this.defaultBaseThirdMagicNumber = defaultThirdMagicNumber = 5;
        }
        this.rawDescription = createText(h, b, ho, d);
        initializeDescription();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (playedBySwarm && m.isDeadOrEscaped()){
            m =  AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        }
        if (isHornet) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        if (isBumblebee) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
        if (isHoneyBee) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Nectar(p, p, magicNumber), magicNumber));
        }
        if (isDrone && !isUpgradedDrone) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        }
        if (isDrone && isUpgradedDrone) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
        }
        if (isHornetCommander) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HornetCommanderPower(p, p, this.defaultSecondMagicNumber), this.defaultSecondMagicNumber));
        }
        if (isBumbleBeeCommander) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BumbleBeeCommanderPower(p, p, this.defaultSecondMagicNumber), this.defaultSecondMagicNumber));
        }
        if (isDroneCommander) {
            AbstractDungeon.actionManager.addToBottom(new UpgradeSpecificCardInDrawPileAction(p, new Drone(), true));
        }
    }

    public String createText(int h, int b, int ho, int d){
        if (h != 0){
            SSText[0] = "Deal !D! damage.";
        }
        else {
            SSText[0] = "";
        }
        if (b != 0){
            SSText[1] = "Gain !B! Block.";
        }
        else {
            SSText[1] = "";
            SSText[4] = "";
        }
        if (ho != 0){
            SSText[2] = "Gain !M! Nectar.";
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
        for (int i=0;i<7;i++) {
            System.out.println(SSText[i]);
        }
        return SSText[0]+" NL "+SSText[1]+" NL "+SSText[2]+SSText[3]+SSText[4]+SSText[5]+ " NL "+SSText[6];
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    public AbstractCard makeCopy(){
        return new WASP(hornet, bumblebee, honeybee, drone);
    }
}
