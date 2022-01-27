//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.cards.Drone;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;

public class DrawSpecificCardTypeAction extends AbstractGameAction {
    private CardGroup p;
    AbstractCard ret;
    ArrayList<AbstractCard> upgradeMatrix;
    int numTimes;
    String Text;
    String typeText;
    AbstractCard.CardType t;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DrawSpecificCardAction");
        TEXT = uiStrings.TEXT;
    }
    public DrawSpecificCardTypeAction(CardGroup player, int n, AbstractCard.CardType typeToDraw) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        p = player;
        numTimes = n;
        t = typeToDraw;
        switch(t){
            case ATTACK:
                typeText = TEXT[0];
                break;
            case SKILL:
                typeText = TEXT[1];
                break;
            case POWER:
                typeText = TEXT[2];
                break;
            default:
                typeText = TEXT[3];
        }
        Text = TEXT[4] + typeText + TEXT[5];
    }

    public void update() {
        p = AbstractDungeon.player.drawPile;
        upgradeMatrix = new ArrayList<AbstractCard>();
        int numCards = 0;
        for (AbstractCard c : p.group) {
            if (c.type.equals(t)) {
                upgradeMatrix.add(c);
                numCards++;
            }
        }
        while (numTimes > 0) {
            if (upgradeMatrix.isEmpty()) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(numTimes));
                isDone = true;
                return;
            }
            AbstractCard c1 = upgradeMatrix.remove(AbstractDungeon.cardRandomRng.random(numCards - 1));
            p.removeCard(c1);
            p.addToTop(c1);
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
            numCards--;
            numTimes--;
        }
        isDone = true;
    }
}
