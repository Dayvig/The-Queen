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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ScrambleAction extends AbstractGameAction {
    int amount;
    int drawamount;
    AbstractPlayer player;
    public ScrambleAction(int m, int d, AbstractPlayer p){
        amount = m;
        player = p;
        drawamount = d;
    }

    public void update() {
        int theSize = AbstractDungeon.player.hand.size();
        AbstractDungeon.actionManager.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
        CardCrawlGame.sound.play("BEE_FADE", 0.1F + (float)Math.random()*0.2F);
        CardCrawlGame.sound.play("BEE_FADE", -0.2F + (float)Math.random()*0.2F);
        CardCrawlGame.sound.play("BEE_FADE", -0.1F + (float)Math.random()*0.2F);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SwarmPower(player, player, amount), amount));
        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(AbstractDungeon.player.drawPile, drawamount, AbstractCard.CardType.ATTACK));
        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(AbstractDungeon.player.drawPile, drawamount, AbstractCard.CardType.SKILL));

        this.isDone = true;
    }
}
