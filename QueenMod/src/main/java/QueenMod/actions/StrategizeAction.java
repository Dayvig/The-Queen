//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.powers.HeartOfTheSwarm;
import QueenMod.powers.SwarmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Iterator;
import java.util.UUID;

public class StrategizeAction extends AbstractGameAction {
    private int amount;
    AbstractPlayer player;
    private static final float DURATION = 0.1f;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("StrategizeAction");
        TEXT = uiStrings.TEXT;
    }
    public StrategizeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        player = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == 0.1F) {
            if (AbstractDungeon.player.hand.group.size() != 0) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false, true);
            }
            else {
                this.isDone = true;
                return;
            }
            this.addToBot(new WaitAction(0.05F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                AbstractDungeon.player.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();

                switch (c.type){
                    case ATTACK:
                        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(player.hand, 2, AbstractCard.CardType.SKILL));
                        break;
                    case SKILL:
                        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(player.hand, 2, AbstractCard.CardType.ATTACK));
                        break;
                    default:
                        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            this.tickDuration();
        }
    }


    public AbstractCard.CardType StrategicDraw(AbstractPlayer p){
        int numAttacks = 0;
        int numSkills = 0;
        for (AbstractCard c : p.hand.group) {
            if (!c.equals(this)) {
                switch (c.type) {
                    case ATTACK:
                        numAttacks++;
                        break;
                    case SKILL:
                        numSkills++;
                        break;
                    default:
                }
            }
        }
        if (numAttacks > numSkills){
            return AbstractCard.CardType.SKILL;
        }
        else if (numSkills > numAttacks){
            return AbstractCard.CardType.ATTACK;
        }
        else {
            return AbstractCard.CardType.POWER;
        }
    }

}
