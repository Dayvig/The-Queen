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
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class pollenEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.5F;
    private AbstractCard card;
    private boolean cardOffset;
    int n;
    boolean p;
    ArrayList<AbstractMonster> tar;
    private AbstractCreature t;

    public pollenEffect(boolean isPlayer, AbstractCreature target) {
        this.startingDuration = 0.0F;
        this.duration = startingDuration;
        p = isPlayer;
        t = target;
    }

    public void update() {
        AbstractDungeon.effectsQueue.add(new PollenParticle(t.drawX, t.drawY));
        AbstractDungeon.effectsQueue.add(new PollenParticle(t.drawX, t.drawY));
        AbstractDungeon.effectsQueue.add(new PollenParticle(t.drawX, t.drawY));
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }
    public void dispose() {
    }
}