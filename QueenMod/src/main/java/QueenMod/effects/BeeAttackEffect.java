//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.effects;

import QueenMod.cards.Hornet;
import QueenMod.effects.HornetParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BeeAttackEffect extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private int count = 0;
    private float timer = 0.0F;
    boolean attackSound = true;
    boolean playBeeSound = true;

    public BeeAttackEffect(float sX, float sY, float tX, float tY, int count) {
        this.sX = sX + ((float)Math.random()*320.0F - 160.0F);
        this.sY = sX + ((float)Math.random()*320.0F - 160.0F);
        this.tX = tX;
        this.tY = tY;
        this.count = count;
    }

    public BeeAttackEffect(float sX, float sY, float tX, float tY, int count, boolean playAttack, boolean playBee) {
        this.sX = sX + ((float)Math.random()*320.0F - 160.0F);
        this.sY = sX + ((float)Math.random()*320.0F - 160.0F);
        this.tX = tX;
        this.tY = tY;
        this.count = count;
        attackSound = playAttack;
        playBeeSound = playBee;
    }

    public BeeAttackEffect(float sX, float sY, float tX, float tY, int count, boolean playAttack) {
        this.sX = sX + ((float)Math.random()*320.0F - 160.0F);
        this.sY = sX + ((float)Math.random()*320.0F - 160.0F);
        this.tX = tX;
        this.tY = tY;
        this.count = count;
        attackSound = playAttack;
    }

    public void update() {
        if (Math.random() > 0.6 && playBeeSound) {
            CardCrawlGame.sound.play("BEE_ATTACK2", 0.3F);
        }
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += MathUtils.random(0.05F, 0.15F);
            AbstractDungeon.effectsQueue.add(new BeeAttackParticle(this.sX, this.sY, this.tX, this.tY, attackSound));
            --this.count;
            if (this.count <= 0) {
                this.isDone = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
