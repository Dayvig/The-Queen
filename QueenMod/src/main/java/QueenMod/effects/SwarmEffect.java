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

public class SwarmEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.5F;
    private AbstractCard card;
    private boolean cardOffset;
    int n;
    boolean p;
    ArrayList<AbstractMonster> tar;
    float particleTimer;

    public SwarmEffect(int num, boolean isPlayer, ArrayList<AbstractMonster> targets) {
        this.startingDuration = 2.0F;
        this.duration = startingDuration;
        n = num;
        p = isPlayer;
        tar = targets;
        particleTimer = 0.0F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (particleTimer <= 0.0F) {
            particleTimer = 1.0F/n;
            int numTargets = tar.size();
            if (p){numTargets++;}
            int index = (int) (Math.random() * numTargets);
            System.out.println("Index number is: " + index);
            if (index == tar.size() || tar.isEmpty()) {
                AbstractDungeon.effectsQueue.add(new SwarmParticle(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
                System.out.println("Swarm on player");
            } else if (tar.get(index) != null) {
                AbstractDungeon.effectsQueue.add(new SwarmParticle(
                        tar.get(index).hb.cX, tar.get(index).hb.cY,
                        tar.get(index).hb.cX, tar.get(index).hb.cY));
                System.out.println("Swarm on enemy");
            }
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