//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.effects.AmbushEffect;
import QueenMod.powers.BusyBeesPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class MakeTempCardInDrawPileActionFast extends AbstractGameAction {
    private AbstractCard cardToMake;
    private boolean randomSpot;
    private boolean autoPosition;
    private boolean toBottom;
    private float x;
    private float y;
    private final float dur = 0.5F;

    public MakeTempCardInDrawPileActionFast(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom, float cardX, float cardY) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = dur;
        this.cardToMake = card;
        this.randomSpot = randomSpot;
        this.autoPosition = autoPosition;
        this.toBottom = toBottom;
        this.x = cardX;
        this.y = cardY;
    }

    public MakeTempCardInDrawPileActionFast(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom) {
        this(card, amount, randomSpot, autoPosition, toBottom, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
    }

    public MakeTempCardInDrawPileActionFast(AbstractCard card, int amount, boolean shuffleInto, boolean autoPosition) {
        this(card, amount, shuffleInto, autoPosition, false);
    }

    public void update() {
        if (this.duration == dur) {
            if (AbstractDungeon.player.hasPower(BusyBeesPower.POWER_ID)){
                amount += AbstractDungeon.player.getPower(BusyBeesPower.POWER_ID).amount;
            }
            AbstractCard c;
            int i;
            if (this.amount < 6) {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    AbstractDungeon.effectList.add(new AmbushEffect(c));
                    AbstractDungeon.player.drawPile.addToRandomSpot(c);
                }
            } else {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    AbstractDungeon.effectList.add(new AmbushEffect(c));
                    AbstractDungeon.player.drawPile.addToRandomSpot(c);
                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
