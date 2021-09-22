//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.effects.AmbushEffect;
import QueenMod.effects.ShowCardAddToDrawPileFast;
import QueenMod.effects.ShowCardAndAddToDrawPileEffectFast;
import QueenMod.powers.BusyBeesPower;
import QueenMod.relics.QueenSceptre;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class MakeTempCardInDrawPileActionFast extends AbstractGameAction {
    private AbstractCard cardToMake;
    private boolean randomSpot;
    private boolean autoPosition;
    private boolean toBottom;
    private float x;
    private float y;
    private final float dur = 0.0F;

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
            if (AbstractDungeon.player.hasRelic(QueenSceptre.ID)){
                QueenSceptre q = (QueenSceptre)AbstractDungeon.player.getRelic(QueenSceptre.ID);
                if (q.isActive) {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardToMake, 1));
                    amount--;
                    q.isActive = false;
                    q.grayscale = true;
                    q.stopPulse();
                    if (amount <= 0) {
                        this.isDone = true;
                    }
                }
            }
            AbstractCard c;
            int i;
            if (this.amount < 6) {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        c.upgrade();
                    }

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffectFast(c, this.x, this.y, this.randomSpot, this.autoPosition, this.toBottom));
                }
            } else {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        c.upgrade();
                    }

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffectFast(c, this.x, this.y, this.randomSpot, this.autoPosition, this.toBottom));

                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }
        this.tickDuration();
    }
}
