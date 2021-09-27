package QueenMod.actions;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.interfaces.IsHive;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SuccessionAction
        extends AbstractGameAction
{

    private static final int NEWMAXHP = 20;
    private static final int NEWDECKSIZE = 5;


    public SuccessionAction()
    {

    }

    private void exhaustAllNonHive(){
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (!(c instanceof IsHive)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardFasterAction(c, AbstractDungeon.player.drawPile, true));
            }
        }
        for (AbstractCard c2 : AbstractDungeon.player.hand.group){
            if (!(c2 instanceof IsHive)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardFasterAction(c2, AbstractDungeon.player.hand, true));
            }
        }
        for (AbstractCard c3 : AbstractDungeon.player.discardPile.group){
            if (!(c3 instanceof IsHive)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardFasterAction(c3, AbstractDungeon.player.discardPile, true));
            }
        }
    }

    @Override
    public void update() {
        AbstractDungeon.effectsQueue.add(new GrandFinalEffect());
        AbstractDungeon.player.decreaseMaxHealth(AbstractDungeon.player.maxHealth/2);
        int healAmount = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, healAmount));
        exhaustAllNonHive();
        ArrayList<AbstractCard> toRemove = new ArrayList<>();
        for (AbstractCard c2 : AbstractDungeon.player.masterDeck.group){
            if (!(c2 instanceof IsHive)){
                toRemove.add(c2);
            }
        }
        for (AbstractCard c3 : toRemove){
            AbstractDungeon.player.masterDeck.removeCard(c3);
        }
        toRemove.clear();
        for (int i = 0; i<10; i++) {
            AbstractDungeon.player.masterDeck.addToBottom(AbstractDungeon.returnTrulyRandomCard().makeCopy());
        }
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileActionFast(c, 1, false, false, false));
        }
        AbstractDungeon.player.img = ImageMaster.loadImage(QueenMod.QUEEN_SUCCESSOR_WITH_CORPSE);
        TheQueen p = (TheQueen)AbstractDungeon.player;
        p.setSuccessorStatus(true);
        tickDuration();
    }
}