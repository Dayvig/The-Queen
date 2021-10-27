//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.effects;

import QueenMod.powers.FocusedSwarm;
import QueenMod.powers.FocusedSwarmE;
import QueenMod.powers.SwarmPower;
import QueenMod.powers.SwarmPowerEnemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class ThermalAttackEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.5F;
    private AbstractCard card;
    private boolean cardOffset;
    int n;
    boolean p;
    ArrayList<AbstractMonster> tar;
    float particleTimer;
    AbstractMonster m;
    int d;
    DamageInfo.DamageType t;
    int mag;
    private float damageTimer = 0.8f;
    boolean damageOff = false;

    public ThermalAttackEffect(boolean isPlayer, AbstractMonster target, int damage, DamageInfo.DamageType type, int magicnum) {
        this.startingDuration = EFFECT_DUR;
        this.duration = startingDuration;
        p = isPlayer;
        particleTimer = 0.0F;
        m = target;
        d = damage;
        t = type;
        mag = magicnum;
    }

    public void update() {

        this.duration -= Gdx.graphics.getDeltaTime();
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        this.damageTimer -= Gdx.graphics.getDeltaTime();

        if (particleTimer <= 0.0F) {
            particleTimer = 0.025F;
            if ((Math.floor(Math.random()*2)) == 1) {
                CardCrawlGame.sound.playA("BEE_ATTACK1", (float)Math.random()*0.3F + 0.3F);
            }
            else {
                CardCrawlGame.sound.playA("BEE_ATTACK2", (float)Math.random()*0.3F + 0.3F);
            }
            AbstractDungeon.effectsQueue.add(new ThermalAttackParticle(
                    AbstractDungeon.player.hb.x + 50F,
                    AbstractDungeon.player.hb.y + 50F,
                    m.hb.x + m.hb.width/2, m.hb.y+m.hb.height/2, 100f, this.duration));
        }
        if (damageTimer < 0.0f && !damageOff){
            for (int i = 0; i < mag; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, d, t), AbstractGameAction.AttackEffect.FIRE));
            }
            damageOff = true;
        }
        if (this.duration < 0.0F && damageTimer < 0.0f) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }
    public void dispose() {
    }
}