package QueenMod.actions;

        import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.common.DrawCardAction;
        import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChainStrikeAction extends AbstractGameAction {
    AbstractMonster monster;
    int numCards;
    public ChainStrikeAction(int n){
        numCards = n;

    }

    public void update(){
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1 >= numCards) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,1));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        isDone = true;
    }
}