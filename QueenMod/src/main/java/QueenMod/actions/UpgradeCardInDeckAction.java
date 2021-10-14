package QueenMod.actions;

import QueenMod.effects.ShowCardUpgradedEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class UpgradeCardInDeckAction extends AbstractGameAction {
    AbstractMonster monster;
    int numCards;
    int numTimes;
    String Text;
    String Text2;
    AbstractCard[] upgradeMatrix;
    private AbstractCard c1;
    private AbstractCard c2;

    public UpgradeCardInDeckAction(int n) {
        numTimes = n;
        Text = "My troops are well trained.";
        Text2 = "I should probably recruit some more soldiers.";
    }

    public void update() {
        CardGroup p = AbstractDungeon.player.drawPile;
        if (p.group.isEmpty()){
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text2, true));
            isDone = true;
            return;
        }
        upgradeMatrix = new AbstractCard[p.size()];
        for (int i = 0;i<numTimes;i++) {
            numCards = 0;
            for (AbstractCard c : p.group) {
                if (!c.upgraded) {
                    upgradeMatrix[numCards] = c;
                    numCards++;
                }
            }
            if (numCards == 0) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
                isDone = true;
                return;
            }
            if (i == 0) {
                c1 = upgradeMatrix[AbstractDungeon.cardRandomRng.random(numCards - 1)];
                c1.upgrade();
            }
            else {
                c2 = upgradeMatrix[AbstractDungeon.cardRandomRng.random(numCards - 1)];
                c2.upgrade();
            }
        }
        if (numTimes == 1) {
            AbstractDungeon.effectsQueue.add(new ShowCardUpgradedEffect(c1));
        }
        else {
            System.out.println("Trace");
            AbstractDungeon.effectsQueue.add(new ShowCardUpgradedEffect(c1, c2));
        }
        isDone = true;
    }
}

