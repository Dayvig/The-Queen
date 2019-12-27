//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import QueenMod.cards.Blitz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FlybyAction extends AbstractGameAction {
    private AbstractPlayer p;
    AbstractCard ret;
    AbstractCard ret2;

    public FlybyAction(AbstractCard f) {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.actionType = ActionType.CARD_MANIPULATION;
        ret = f.makeStatEquivalentCopy();
        ret2 = f;
    }

    public void update() {
        if (AbstractDungeon.player.hand.group.contains(ret2)){
            AbstractDungeon.player.hand.moveToDeck(ret2, true);
        }
        else {
            AbstractDungeon.player.hand.moveToDeck(ret, true);
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}
