//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class HiveFollowUpAction extends AbstractGameAction {
    public HiveFollowUpAction() {
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            Iterator var1 = HiveNetAction.hiveCards.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.cardID.equals(Hornet.ID) ||
                        c.cardID.equals(BumbleBee.ID) ||
                        c.cardID.equals(Drone.ID) ||
                        c.cardID.equals(WorkerBee.ID) ||
                        c.cardID.equals(HornetCommander.ID) ||
                        c.cardID.equals(BumbleBeeCommander.ID) ||
                        c.cardID.equals(DroneCommander.ID) ||
                        c.cardID.equals(WorkerBeeCommander.ID) ||
                        c.cardID.equals(WASP.ID)) {
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
                }
            }

            HiveNetAction.hiveCards.clear();
        }

        this.tickDuration();
    }
}
