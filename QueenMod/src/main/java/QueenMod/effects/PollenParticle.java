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

public class PollenParticle extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private float startingScale;
    private float endingScale;
    private AtlasRegion img;

    public PollenParticle(float sX, float sY) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX * Settings.scale;
        this.sY = sY * Settings.scale;
        this.tX = (sX + MathUtils.random(-150.0F, 150.0F)) * Settings.scale;
        this.tY = (sY + MathUtils.random(-150.0F, 150.0F)) * Settings.scale;
        this.x = this.sX;
        this.y = this.sY;
        this.startingScale = Settings.scale - MathUtils.random(0.0F, 0.5F*Settings.scale);
        this.endingScale = Settings.scale*4 - MathUtils.random(0.0F, 2.0F*Settings.scale);
        this.scale = startingScale;
        this.startingDuration = 1.2F;
        this.duration = this.startingDuration;
        this.renderBehind = false;
        this.color = new Color(1.0F, 1.0F, MathUtils.random(0.0F, 0.2F), 1.2F);
    }

    public void update() {
        if (this.duration >= 0.0F) {
            this.x = Interpolation.linear.apply(this.tX, this.sX, this.duration / this.startingDuration);
            this.y = Interpolation.linear.apply(this.tY, this.sY, this.duration / this.startingDuration);
            this.scale = Interpolation.circleIn.apply(this.endingScale, this.startingScale, this.duration / this.startingDuration);
            this.color = new Color(1.0F, 1.0F, MathUtils.random(0.0F, 0.2F), duration/2);

        }
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}
