//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.cards.Drone;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;

public class AddToTopOfDrawPileAction extends AbstractGameAction {
    private CardGroup p;
    AbstractCard ret;
    ArrayList<AbstractCard> upgradeMatrix;
    int numTimes;
    String Text;
    String typeText;
    AbstractCard.CardType t;

    public AddToTopOfDrawPileAction(CardGroup player, int n, AbstractCard.CardType typeToDraw) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        p = player;
        numTimes = n;
        t = typeToDraw;
        switch(t){
            case ATTACK:
                typeText = "attacks";
            case SKILL:
                typeText = "non-Drone skills";
            case POWER:
                typeText = "powers";
            default:
                typeText = "cards of that type";
        }
        Text = "I have no "+typeText+" in my draw pile!";
    }

    public void update() {
        p = AbstractDungeon.player.drawPile;
        upgradeMatrix = new ArrayList<AbstractCard>();
        int numCards = 0;
            for (AbstractCard c : p.group) {
                if (c.type.equals(t) && !c.cardID.equals(Drone.ID)) {
                    upgradeMatrix.add(c);
                    numCards++;
                }
            }
            if (upgradeMatrix.isEmpty()) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
                isDone = true;
                return;
            }
        while (!upgradeMatrix.isEmpty()) {
            AbstractCard c1 = upgradeMatrix.remove(AbstractDungeon.cardRandomRng.random(numCards - 1));
            p.removeCard(c1);
            p.addToTop(c1);
            numTimes--;
            if (numTimes <= 0){
                isDone = true;
                return;
            }
        }
        isDone = true;
    }
}
