package QueenMod.actions;

import QueenMod.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PromotionAction extends AbstractGameAction {
    String id;
    AbstractPlayer p;
    boolean isupgr;
    AbstractCard promoted;

    public PromotionAction(AbstractPlayer play, String cardIDToPromote, boolean isSeriousUpgrade) {
        p = play;
        id = cardIDToPromote;
        isupgr = isSeriousUpgrade;
        System.out.println(id);
        if (id.equals(Hornet.ID)) {
            if (!isupgr) {
                promoted = new HornetCommander();
            }
            else {
                promoted = new HornetCommander();
                promoted.upgrade();
            }
        }
        else if (id.equals(BumbleBee.ID)) {
            if (!isupgr) {
                promoted = new BumbleBeeCommander();
            }
            else {
                promoted = new BumbleBeeCommander();
                promoted.upgrade();
            }
        }
        else if (id.equals(WorkerBee.ID)) {
            if (!isupgr) {
                promoted = new WorkerBeeCommander();
            }
            else {
                promoted = new WorkerBeeCommander();
                promoted.upgrade();
            }
        }
        else {
            if (!isupgr) {
                promoted = new DroneCommander();
            }
            else {
                promoted = new DroneCommander();
                promoted.upgrade();
            }
        }
    }

    public void update() {
        for (AbstractCard c : p.drawPile.group){
            if (c.cardID.equals(id)){
                AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c, promoted, p.drawPile));
                isDone = true;
                return;
            }
        }
        isDone = true;
    }
}
