//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package QueenMod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import java.util.Iterator;

public class ShowCardUpgradedEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.4F;
    private AbstractCard card;
    private AbstractCard card2;
    private static final float PADDING;
    private boolean randomSpot;
    private boolean cardOffset;

    public ShowCardUpgradedEffect(AbstractCard srcCard, float x, float y) {
        this.randomSpot = false;
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.card2 = null;
        this.duration = EFFECT_DUR;
            this.card.target_x = x;
            this.card.target_y = y;

        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 0.4F;
        this.card.flash(Color.YELLOW);

    }


    public ShowCardUpgradedEffect(AbstractCard srcCard) {
        this.randomSpot = false;
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.card2 = null;
        this.duration = 0.75F;
        this.card.target_x = (float)Settings.WIDTH * 0.15F;
        this.card.target_y = (float)Settings.HEIGHT * 0.1F;

        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 0.4F;
        this.card.flash(Color.YELLOW);

    }

    public ShowCardUpgradedEffect(AbstractCard srcCard, AbstractCard srcCard2) {
        this.randomSpot = false;
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.card2 = srcCard2.makeStatEquivalentCopy();
        this.duration = 1.0F;
        this.card.target_x = (float)Settings.WIDTH * 0.15F;
        this.card.target_y = (float)Settings.HEIGHT * 0.1F;
        this.card2.target_x = (float)Settings.WIDTH * 0.25F;
        this.card2.target_y = (float)Settings.HEIGHT * 0.1F;

        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 0.4F;
        this.card.flash(Color.YELLOW);

        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card2.target_x, this.card2.target_y));
        this.card2.drawScale = 0.01F;
        this.card2.targetDrawScale = 0.4F;
        this.card2.flash(Color.YELLOW);

    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (card2 != null) {
            this.card2.update();
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
            AbstractDungeon.getCurrRoom().souls.onToDeck(this.card, this.randomSpot, true);
            if (card2 != null) {
                this.card2.shrink();
                AbstractDungeon.getCurrRoom().souls.onToDeck(this.card2, this.randomSpot, true);
            }
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
            if (card2 != null) {
                this.card2.render(sb);
            }
        }
    }

    public void dispose() {
    }

    static {
        PADDING = 30.0F * Settings.scale;
    }
}
