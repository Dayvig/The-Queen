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
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class GatherEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.5F;
    private AbstractCard card;
    private boolean cardOffset;
    int n;
    boolean p;
    ArrayList<AbstractMonster> tar;
    float particleTimer;

    public GatherEffect(boolean isPlayer) {
        this.startingDuration = 1.0F;
        this.duration = startingDuration;
        p = isPlayer;
        particleTimer = 0.0F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (particleTimer <= 0.0F) {
            particleTimer = 0.05F;
            AbstractDungeon.effectsQueue.add(new GatherParticle(AbstractDungeon.player.hb.x + 50F, AbstractDungeon.player.hb.y + 50F, 200f));
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }
    public void dispose() {
    }
}