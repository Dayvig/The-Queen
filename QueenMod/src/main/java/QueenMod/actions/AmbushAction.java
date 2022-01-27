package QueenMod.actions;

import QueenMod.effects.AmbushEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_X;
import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_Y;

public class AmbushAction extends AbstractGameAction {
    int numCards;
    AbstractPlayer p;
    int energyOnUse;
    boolean upgr;
    boolean freeToPlayOnce;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("AmbushAction");
        TEXT = uiStrings.TEXT;
    }

    public AmbushAction(AbstractPlayer source, int energyUse, boolean isUpgraded, boolean free) {
        p = source;
        energyOnUse = energyUse;
        upgr = isUpgraded;
        freeToPlayOnce = free;
        startDuration = 0.5F;
        duration = startDuration;

    }

    public void update() {
        ArrayList<AbstractCard> upgradeMatrix = new ArrayList<>();
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
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
            isDone = true;
            return;
        }
        for (AbstractCard c : p.group) {
            if (!c.freeToPlayOnce && c.cost != 0 && !c.type.equals(AbstractCard.CardType.CURSE) && !c.type.equals(AbstractCard.CardType.STATUS)) {
                upgradeMatrix.add(c);
            }
        }
        for (int i=0;i<numCards;i++){
            if (upgradeMatrix.isEmpty()){
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
                this.p.energy.use(EnergyPanel.totalCount);
                this.isDone = true;
            }
            else {
                AbstractCard c1 = upgradeMatrix.get(AbstractDungeon.cardRandomRng.random(upgradeMatrix.size()-1));
                c1.freeToPlayOnce = true;
                AbstractDungeon.effectsQueue.add(new AmbushEffect(c1));
                upgradeMatrix.remove(c1);
            }
        }
        this.p.energy.use(EnergyPanel.totalCount);
        isDone = true;
    }
}