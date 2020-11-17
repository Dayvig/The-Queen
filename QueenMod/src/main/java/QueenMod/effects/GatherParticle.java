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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;

public class GatherParticle extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private int angle;
    private float radius;
    private AtlasRegion img;

    public GatherParticle(float sX, float sY, float r) {
        this.angle = (int)(Math.random()*360);
        System.out.println(angle);
        radius = r;
        this.img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX;
        this.sY = sY;
        this.vX = (sX * Settings.scale) + (r * (float)Math.cos(angle * (Math.PI / 180)));
        this.vY = (sX * Settings.scale) + (r * (float)Math.sin(angle * (Math.PI / 180)));
        this.tX = sX * Settings.scale;
        this.tY = sY * Settings.scale;
        this.x = this.vX;
        this.y = this.vY;
        this.scale = Settings.scale;
        this.startingDuration = 0.3F;
        this.duration = this.startingDuration;
        this.color = new Color(1.0F, 1.0F, MathUtils.random(0.0F, 0.2F), 1.0F);
    }

    public void update() {
        if (this.duration >= 0.0f) {
            this.x = Interpolation.linear.apply(this.tX, this.vX, (this.duration) / (this.startingDuration));
            this.y = Interpolation.linear.apply(this.tY, this.vY, (this.duration) / (this.startingDuration));
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(Color.YELLOW);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}
