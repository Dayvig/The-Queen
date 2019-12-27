package QueenMod.actions;

import QueenMod.cards.Drone;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;

public class RecruitAction extends AbstractGameAction {
    AbstractCard cardToReplace;
    AbstractCard cardToReplaceu;
    int numTimes;
    ArrayList<AbstractCard> upgradeMatrix = new ArrayList<AbstractCard>();
    int numCards;
    String Text = "I need more Drones...";

    public RecruitAction(AbstractCard hive, int n){
        cardToReplace = hive;
        cardToReplaceu = hive.makeStatEquivalentCopy();
        cardToReplaceu.upgrade();
        numTimes = n;
    }

    public void update(){
        CardGroup g = AbstractDungeon.player.hand;
            for (AbstractCard c : g.group){
                if (c.cardID.equals(Drone.ID)){
                    if (!c.upgraded) {
                        AbstractDungeon.actionManager.addToTop(new SwapCardAction(c, cardToReplace, g));
                        AbstractDungeon.player.hand.refreshHandLayout();
                    }
                    else {
                        AbstractDungeon.actionManager.addToTop(new SwapCardAction(c, cardToReplaceu, g));
                        AbstractDungeon.player.hand.refreshHandLayout();
                    }
                    numTimes--;
                    if (numTimes <= 0) {
                        this.isDone = true;
                        return;
                    }
                }
            }
            System.out.println("Hand done. numTimes = "+numTimes);
            g = AbstractDungeon.player.drawPile;
                numCards = 0;
                for (AbstractCard c : g.group) {
                    if (c.cardID.equals(Drone.ID)) {
                        upgradeMatrix.add(c);
                    }
                }
                System.out.println("Size: "+upgradeMatrix.size());
                while (!upgradeMatrix.isEmpty()) {
                        AbstractCard c1 = upgradeMatrix.remove(AbstractDungeon.cardRandomRng.random(upgradeMatrix.size()-1));
                        AbstractDungeon.actionManager.addToTop(new SwapCardAction(c1, cardToReplace, g));
                        numTimes--;
                        if (numTimes <= 0) {
                            this.isDone = true;
                            return;
                        }
                }
        System.out.println("Drawpile done. numTimes = "+numTimes);
        g = AbstractDungeon.player.discardPile;
        numCards = 0;
        for (AbstractCard c : g.group) {
            if (c.cardID.equals(Drone.ID)) {
                upgradeMatrix.add(c);
            }
        }
        System.out.println("Size: "+upgradeMatrix.size());
        while (!upgradeMatrix.isEmpty()) {
            AbstractCard c1 = upgradeMatrix.remove(AbstractDungeon.cardRandomRng.random(upgradeMatrix.size()-1));
            AbstractDungeon.actionManager.addToBottom(new SwapCardAction(c1, cardToReplace, g));
            System.out.println("Trace");
            numTimes--;
            if (numTimes <= 0) {
                this.isDone = true;
                return;
            }
        }
        if (numTimes > 0){
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
        }
        isDone = true;
    }
}