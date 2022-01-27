package QueenMod.actions;

import QueenMod.powers.SwarmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.util.Iterator;

public class ScrambleAction extends AbstractGameAction {
    int amount;
    int drawAmount;
    AbstractPlayer player;
    private static final float DURATION = 0.5F;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ScrambleAction");
        TEXT = uiStrings.TEXT;
    }
    public ScrambleAction(int m, AbstractPlayer p, int d){
        amount = m;
        player = p;
        drawAmount = d;
        duration = DURATION;
    }

    /*public void update() {
        int theSize = AbstractDungeon.player.hand.size();
        AbstractDungeon.actionManager.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
        CardCrawlGame.sound.play("BEE_FADE", 0.1F + (float)Math.random()*0.2F);
        CardCrawlGame.sound.play("BEE_FADE", -0.2F + (float)Math.random()*0.2F);
        CardCrawlGame.sound.play("BEE_FADE", -0.1F + (float)Math.random()*0.2F);
        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(AbstractDungeon.player.drawPile, drawamount, AbstractCard.CardType.ATTACK));
        AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(AbstractDungeon.player.drawPile, drawamount, AbstractCard.CardType.SKILL));

        this.isDone = true;
    }*/

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false, true);
            CardCrawlGame.sound.play("BEE_FADE", 0.1F + (float)Math.random()*0.2F);
            CardCrawlGame.sound.play("BEE_FADE", -0.2F + (float)Math.random()*0.2F);
            CardCrawlGame.sound.play("BEE_FADE", -0.1F + (float)Math.random()*0.2F);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SwarmPower(player, player, amount), amount));
            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard l : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                    System.out.println(player.hand.group.size());
                    player.limbo.group.add(l);
                    player.hand.group.remove(l);
                    System.out.println(player.hand.group.size());
                }
                AbstractDungeon.actionManager.addToBottom(new DiscardAction(player, player, player.hand.size(), true));
                AbstractDungeon.actionManager.addToBottom(new DrawSpecificCardTypeAction(player.hand, drawAmount, StrategicDraw(player)));
                player.hand.group.addAll(player.limbo.group);
                player.limbo.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }


    public AbstractCard.CardType StrategicDraw(AbstractPlayer p){
        int numAttacks = 0;
        int numSkills = 0;
        for (AbstractCard c : p.hand.group) {
            if (!c.equals(this)) {
                switch (c.type) {
                    case ATTACK:
                        numAttacks++;
                        break;
                    case SKILL:
                        numSkills++;
                        break;
                    default:
                }
            }
        }
        if (numAttacks > numSkills){
            return AbstractCard.CardType.SKILL;
        }
        else if (numSkills > numAttacks){
            return AbstractCard.CardType.ATTACK;
        }
        else {
            return AbstractCard.CardType.POWER;
        }
    }
}
