//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.cards.*;
import QueenMod.powers.HoneyBoost;
import QueenMod.powers.Nectar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;
import java.util.Iterator;


public class BusyBeesAction extends AbstractGameAction {

    public BusyBeesAction() {
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!c.cardID.equals(BusyBees.ID) && !c.type.equals(AbstractCard.CardType.STATUS) && !c.type.equals(AbstractCard.CardType.CURSE)) {
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileActionFast(c.makeStatEquivalentCopy(), 1, true, false, false));
            }
        }
        this.isDone = true;
    }
}