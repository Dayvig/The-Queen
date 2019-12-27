package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class AmbushAction extends AbstractGameAction {
    AbstractMonster monster;
    int numCards;
    AbstractPlayer p;
    int energyOnUse;
    boolean upgr;
    boolean freeToPlayOnce;
    AbstractCard upgradeMatrix[];
    String Text2 = "I should recruit more soldiers.";
    String Text = "My Ambush is set.";

    public AmbushAction(AbstractPlayer source, int energyUse, boolean isUpgraded, boolean free) {
        p = source;
        energyOnUse = energyUse;
        upgr = isUpgraded;
        freeToPlayOnce = free;
    }

    public void update() {
        numCards = EnergyPanel.totalCount;
        CardGroup p = AbstractDungeon.player.drawPile;
        if (this.energyOnUse != -1) {
            numCards = this.energyOnUse;
        }
        if (this.p.hasRelic("Chemical X")) {
            numCards += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (this.upgr){
            numCards += 1;
        }
        if (p.group.isEmpty()){
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text2, true));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
            isDone = true;
            return;
        }
        upgradeMatrix = new AbstractCard[p.size()];
        int k = 0;
        for (int i=0;i<numCards;i++){
            k = 0;
            for (AbstractCard c : p.group) {
                if (!c.freeToPlayOnce && c.cost != 0) {
                    upgradeMatrix[k] = c;
                    k++;
                }
            }
            if (k == 0) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
                if (!this.freeToPlayOnce) {
                    this.p.energy.use(EnergyPanel.totalCount);
                }
                isDone = true;
                isDone = true;
                return;
            }
                AbstractCard c1 = upgradeMatrix[AbstractDungeon.cardRandomRng.random(k - 1)];
                c1.freeToPlayOnce = true;
        }
        if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
        }
        isDone = true;
    }
}