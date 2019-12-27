//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;
import java.util.Iterator;


public class DevelopmentAction extends AbstractGameAction {
    String TEXT = " to add to your hand permanently.";
    String text2 = "I have no subjects to knight.";
    private ArrayList<AbstractCard> nonHive = new ArrayList();
    AbstractPlayer p;
    int n;

    public DevelopmentAction(int numRequired) {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        n = numRequired;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID.equals(Hornet.ID) ||
                        c.cardID.equals(BumbleBee.ID) ||
                        c.cardID.equals(Drone.ID) ||
                        c.cardID.equals(WorkerBee.ID) ||
                        c.cardID.equals(HornetCommander.ID) ||
                        c.cardID.equals(BumbleBeeCommander.ID) ||
                        c.cardID.equals(DroneCommander.ID) ||
                        c.cardID.equals(WorkerBeeCommander.ID)) {
                } else {
                    this.nonHive.add(c);
                }
            }

            if (this.p.hand.group.size() - this.nonHive.size() < n) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, text2, true));
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.nonHive.size() == n) {
                for (AbstractCard c : p.hand.group) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand, true));
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HoneyFactory(), 1, true, false));
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.nonHive);
            if (this.p.hand.group.size() > n) {
                AbstractDungeon.handCardSelectScreen.open(TEXT, 2, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == n) {
                for (AbstractCard c : p.hand.group) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand, true));
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HoneyFactory(), 1, true, false));
                returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.handCardSelectScreen.selectedCards, true));
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HoneyFactory(), 1, true, false));
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            returnCards();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private void returnCards() {
        Iterator var1 = this.nonHive.iterator();

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }
}