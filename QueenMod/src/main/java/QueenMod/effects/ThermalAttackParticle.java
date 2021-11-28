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
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;

import javax.smartcardio.Card;

public class ThermalAttackParticle extends AbstractGameEffect {
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
    private final float DUR_1 = 0.15F;
    private final float DUR_2 = 1.65F;
    private final float DUR_3 = 0.25f;
    private final float TOTAL_DUR = 1.65F;
    private float wait;
    private Color c;
    private float sfXTimer = 0.2f;

    public ThermalAttackParticle(float sX, float sY, float tX, float tY, float r, float waitTime) {
        this.angle = (int)(Math.random()*360);
        System.out.println(angle);
        radius = r;
        this.img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX;
        this.sY = sY;
        this.tX = tX;
        this.tY = tY;
        this.vX = (this.tX) + (radius * (float)Math.cos(angle * (Math.PI / 180)));
        this.vY = (this.tY) + (radius * (float)Math.sin(angle * (Math.PI / 180)));
        this.x = this.sX;
        this.y = this.sY;
        this.scale = Settings.scale;
        this.wait = waitTime;
        this.startingDuration = DUR_1 + DUR_2 + DUR_3 + wait;
        this.duration = this.startingDuration;
        this.color = new Color(1.0F, 1.0F, MathUtils.random(0.0F, 0.2F), 1.0F);
        c = this.color;
    }

    public void update() {
        if (this.duration > (startingDuration - DUR_1)){
            //1.65 to 1.40
            //DUR1 = 0.25
            //startingDuration - DUR1 = 1.40
            this.x = Interpolation.linear.apply(vX, sX, (this.duration - (startingDuration-DUR_1)) / DUR_1);
            this.y = Interpolation.linear.apply(vY, sY, (this.duration - (startingDuration-DUR_1)) / DUR_1);
        }
        else if (this.duration > ((startingDuration - DUR_1 - wait))){
        }
        else if (this.duration > ((startingDuration - DUR_1 - wait - DUR_2))){
            //float currentDur = ((wait + DUR_2));
            if (sfXTimer < 0){
                //CardCrawlGame.sound.playA("ATTACK_FIRE", 0.2f);
                sfXTimer = 0.2f;
            }
            int newAngle = (int)(Math.random()*360);
            int newRadius = 15;
            this.x = vX + (newRadius * (float)Math.cos(newAngle * (Math.PI / 180)));
            this.y = vY + (newRadius * (float)Math.sin(newAngle * (Math.PI / 180)));
            this.color.g = Interpolation.linear.apply(0.0F, 1.0F, (this.duration - (startingDuration-DUR_1-DUR_2-wait)) / DUR_2+wait);
            this.color.b = Interpolation.linear.apply(0.0F, 1.0F, (this.duration - (startingDuration-DUR_1-DUR_2-wait)) / DUR_2+wait);
            //this.scale = Interpolation.pow2In.apply(1.0F, 1.5F, (this.duration - DUR_1 - currentDur) / currentDur) * Settings.scale;
        }
        else {
            this.color.g = 1.0f;
            this.color.b = 1.0f;
            //0.15 to 0.0
            //DUR3 is 0.15

            this.x = Interpolation.linear.apply(this.vX + ((radius+2) * (float)Math.cos(angle * (Math.PI / 180))), this.vX, (this.duration) / DUR_3);
            this.y = Interpolation.linear.apply(this.vY+ ((radius+2) * (float)Math.sin(angle * (Math.PI / 180))), this.vY, (this.duration) / DUR_3);
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        this.sfXTimer -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0){
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
