package QueenMod.actions;

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

    public UpgradeCardInDeckAction(int n) {
        numTimes = n;
        Text = "My troops are well trained.";
        Text2 = "I should probably recruit some more soldiers.";
    }

    public void update() {
        CardGroup p = AbstractDungeon.player.drawPile;
        if (p.group.isEmpty()){
            System.out.println("Trace");
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
            AbstractCard c1 = upgradeMatrix[AbstractDungeon.cardRandomRng.random(numCards-1)];
            c1.upgrade();
        }
        isDone = true;
    }
}

