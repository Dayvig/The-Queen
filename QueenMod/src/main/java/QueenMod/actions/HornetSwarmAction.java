package QueenMod.actions;

import QueenMod.cards.Hornet;
import QueenMod.cards.HornetCommander;
import QueenMod.cards.RoyalOrder;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class HornetSwarmAction extends AbstractGameAction {
    private CardGroup HornetGroup;
    private boolean asHornet;

    public HornetSwarmAction(AbstractCreature s) {
        this.source = s;
    }

    private void playAllHornets (CardGroup g){
        for (AbstractCard c : g.group) {

            if (c.cardID.equals(Hornet.ID))
            {
                c.costForTurn = 0;
                c.applyPowers();
                AbstractDungeon.player.limbo.group.add(c);
                    Hornet tmp = (Hornet) c.makeStatEquivalentCopy();
                    tmp.playedBySwarm = true;
                    tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, AbstractDungeon.getMonsters().getRandomMonster(true)));
                AbstractDungeon.actionManager.addToTop(new DrawToDiscardAction(c, g));
                AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
            }
        }
    }

    private void discardFromLimbo (){
        for (AbstractCard c : AbstractDungeon.player.limbo.group){
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
        }
    }

        public void update () {
            playAllHornets(AbstractDungeon.player.hand);
            playAllHornets(AbstractDungeon.player.drawPile);
            isDone = true;
        }
    }

