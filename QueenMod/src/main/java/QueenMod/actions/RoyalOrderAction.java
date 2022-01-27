package QueenMod.actions;

import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.Hornet;
import QueenMod.cards.HoneyBee;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;


public class RoyalOrderAction extends AbstractGameAction {

    ArrayList<AbstractCard> cardMatrix = new ArrayList<>();


    public RoyalOrderAction() {
    }

    /*
    public void update() {
        boolean h = false;
        boolean b = false;
        boolean d = false;
        boolean w = false;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof IsHive) {
                cardMatrix.add(c);
            }
        }
        while (!(h && b && d && w) && !cardMatrix.isEmpty()) {
            AbstractCard tmp = cardMatrix.remove((int) (cardMatrix.size() * Math.random()));
            if (tmp.cardID.equals(Hornet.ID) && !h) {
                tmp.freeToPlayOnce = true;
                tmp.applyPowers();
                Hornet tempHornet = (Hornet) tmp;
                AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
                if ((mo.hasPower(TimeWarpPower.POWER_ID) && mo.getPower(TimeWarpPower.POWER_ID).amount == 11) || mo.hasPower(ThornsPower.POWER_ID) || mo.hasPower(SharpHidePower.POWER_ID) || mo.hasPower(BeatOfDeathPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToTop(new DrawToHandAction(tmp));
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text3, true));
                } else {
                    AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, mo));
                }
                //AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                h = true;
            }

            if (tmp.cardID.equals(BumbleBee.ID) && !b) {
                tmp.freeToPlayOnce = true;
                tmp.applyPowers();
                AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, true));
                //AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                b = true;
            }
            if (tmp.cardID.equals(HoneyBee.ID) && !w) {
                tmp.freeToPlayOnce = true;
                tmp.applyPowers();
                AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, true));
                //AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                w = true;
            }
            if (tmp.cardID.equals(Drone.ID) && !d) {
                tmp.freeToPlayOnce = true;
                tmp.applyPowers();
                AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, true));
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                d = true;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.limbo.group) {
            if (c.exhaust) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
            }
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }*/

    public void update(){
        playCard(findInDrawPile(new Hornet()));
        playCard(findInDrawPile(new BumbleBee()));
        playCard(findInDrawPile(new Drone()));
        playCard(findInDrawPile(new HoneyBee()));
        this.isDone = true;
    }

    public AbstractCard findInDrawPile(AbstractCard c) {
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if (card.cardID.equals(c.cardID) && card.upgraded) {
                return card;
            }
        }
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if (card.cardID.equals(c.cardID)) {
                return card;
            }
        }
        return null;
    }

    public void playCard(AbstractCard c){
        if (c != null){
            AbstractCard tmp = c.makeStatEquivalentCopy();
            tmp.freeToPlayOnce = true;
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, true));
            if (!c.exhaust) {
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.drawPile));
            }
            else {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
            }
        }
    }
}
