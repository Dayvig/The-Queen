package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustSpecificCardFasterAction extends AbstractGameAction {
        private AbstractCard targetCard;
        private CardGroup group;
        private float startingDuration;

        public ExhaustSpecificCardFasterAction(AbstractCard targetCard, CardGroup group, boolean isFast) {
            this.targetCard = targetCard;
            this.setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
            this.actionType = ActionType.EXHAUST;
            this.group = group;
            this.startingDuration = Settings.ACTION_DUR_FAST / 3;
            this.duration = this.startingDuration;
        }

        public ExhaustSpecificCardFasterAction(AbstractCard targetCard, CardGroup group) {
            this(targetCard, group, false);
        }

        public void update() {
            if (this.duration == this.startingDuration && this.group.contains(this.targetCard)) {
                this.group.moveToExhaustPile(this.targetCard);
                CardCrawlGame.dungeon.checkForPactAchievement();
                this.targetCard.exhaustOnUseOnce = false;
                this.targetCard.freeToPlayOnce = false;
            }

            this.tickDuration();
        }
    }
