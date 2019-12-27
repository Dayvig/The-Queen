package QueenMod.actions;

import QueenMod.cards.Hornet;
import QueenMod.cards.HornetCommander;
import QueenMod.cards.WASP;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HornetSwarmAction extends AbstractGameAction {
    private CardGroup HornetGroup;
    private boolean asHornet;

    public HornetSwarmAction(AbstractCreature s) {
        this.source = s;
        asHornet = false;
    }

    private void playAllHornets (CardGroup g){
        for (AbstractCard c : g.group) {
            if (c.cardID.equals(WASP.ID)){
                asHornet = ((WASP) c).isHornet;
            }
            if (c.cardID.equals(Hornet.ID) || c.cardID.equals(HornetCommander.ID) || asHornet)
            {
                c.costForTurn = 0;
                c.applyPowers();
                AbstractDungeon.player.limbo.group.add(c);
                if (c.cardID.equals(Hornet.ID)){
                    Hornet tmp = (Hornet) c;
                    tmp.playedBySwarm = true;
                }
                else if (c.cardID.equals(HornetCommander.ID)){
                    HornetCommander tmp = (HornetCommander) c;
                    tmp.playedBySwarm = true;
                }
                else {
                    WASP tmp = (WASP) c;
                    tmp.playedBySwarm = true;
                }
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c, AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng)));
                if (g.equals(AbstractDungeon.player.drawPile)) {
                    AbstractDungeon.actionManager.addToBottom(new DrawToHandAction(c));
                }
            }
        }
    }

    private void discardFromLimbo (){
        for (AbstractCard c : AbstractDungeon.player.limbo.group){
            System.out.println("Trace");
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
        }
    }

        public void update () {
            playAllHornets(AbstractDungeon.player.hand);
            playAllHornets(AbstractDungeon.player.drawPile);
            discardFromLimbo();
            isDone = true;
        }
    }

