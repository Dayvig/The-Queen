//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package QueenMod.enemies;

import QueenMod.QueenMod;
import QueenMod.cards.Scramble;
import basemod.BaseMod;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;

public class HappyGuy extends CustomMonster {
    public static final String ID = QueenMod.makeID(HappyGuy.class.getSimpleName());
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 46;
    private static final int HP_MAX = 50;
    private static final int A_2_HP_MIN = 48;
    private static final int A_2_HP_MAX = 52;
    private static final int STAB_DMG = 12;
    private static final int A_2_STAB_DMG = 13;
    private static final int RAKE_DMG = 7;
    private static final int A_2_RAKE_DMG = 8;
    private int slamDmg = 10;
    private int buffAmt = 10;
    private static final byte BUFF = 2;
    private static final byte SLAM = 1;

    public HappyGuy(float x, float y) {
        super(NAME, ID, 60, 0.0F, 0.0F, 240.0F, 240.0F, "QueenModResources/images/enemies/HappyGuy.png", x, y);

            this.setHp(56, 62);

        this.damage.add(new DamageInfo(this, this.slamDmg));
        this.animation = null;
    }

    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                if (hasPower(StrengthPower.POWER_ID)){
                     if (this.getPower(StrengthPower.POWER_ID).amount >= 10){
                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                     }
                     else {
                         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                     }
                }
                else {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                }
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, buffAmt), buffAmt));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERBLUE_1A"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERBLUE_1B"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_SLAVERBLUE_2A");
        } else {
            CardCrawlGame.sound.play("VO_SLAVERBLUE_2B");
        }

    }

    protected void getMove(int num) {
        if (this.lastMove(SLAM)) {
            this.setMove(BUFF, Intent.BUFF);
        }
        else {
            this.setMove(SLAM, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base);
        }
    }

    public void die() {
        super.die();
        this.playDeathSfx();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
