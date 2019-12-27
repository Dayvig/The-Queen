//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwarmEconomicsAction extends AbstractGameAction {

    AbstractCreature p;
    int swarmAmount;
    int swarmEfficiency;

    public SwarmEconomicsAction(AbstractCreature source, int amount, int effeciency) {

        p = source;
        swarmAmount = amount;
        swarmEfficiency = effeciency;

    }

    public void update(){
        int n = swarmAmount / swarmEfficiency;
        if (n > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(n));
            this.isDone = true;
        }
    }
}
