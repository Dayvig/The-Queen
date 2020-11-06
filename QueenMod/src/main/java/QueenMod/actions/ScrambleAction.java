package QueenMod.actions;

import QueenMod.powers.SwarmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ScrambleAction extends AbstractGameAction {
    int amount;
    AbstractPlayer player;
    public ScrambleAction(int m, AbstractPlayer p){
        amount = m;
        player = p;
    }

    public void update() {
        int theSize = AbstractDungeon.player.hand.size();
        AbstractDungeon.actionManager.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SwarmPower(player, player, amount), amount));
        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(AbstractDungeon.player.drawPile, this.amount, AbstractCard.CardType.ATTACK));
        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(AbstractDungeon.player.drawPile, this.amount, AbstractCard.CardType.SKILL));

        this.isDone = true;
    }
}
