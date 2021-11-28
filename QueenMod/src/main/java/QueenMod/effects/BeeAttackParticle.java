//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BeeAttackParticle extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private AtlasRegion img;
    boolean attackSound = false;

    public BeeAttackParticle(float sX, float sY, float tX, float tY) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX;
        this.sY = sY;
        this.tX = tX;
        this.tY = tY;
        this.x = this.sX;
        this.y = this.sY;
        this.scale = 1.0F;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.renderBehind = MathUtils.randomBoolean(0.2F);
        this.color = new Color(1.0F, 1.0F, MathUtils.random(0.0F, 0.2F), 1.0F);
    }
    public BeeAttackParticle(float sX, float sY, float tX, float tY, boolean playAttack) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX;
        this.sY = sY;
        this.tX = tX;
        this.tY = tY;
        this.x = this.sX;
        this.y = this.sY;
        this.scale = 1.0F;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.renderBehind = MathUtils.randomBoolean(0.2F);
        this.color = new Color(1.0F, 1.0F, MathUtils.random(0.0F, 0.2F), 1.0F);
        attackSound = playAttack;
    }

    public void update() {

        this.x = Interpolation.pow2In.apply(this.tX, this.sX, this.duration / this.startingDuration);
        this.y = Interpolation.pow2In.apply(this.tY, this.sY, this.duration / this.startingDuration);

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            if (attackSound) {
                AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(tX, tY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLACK);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}
