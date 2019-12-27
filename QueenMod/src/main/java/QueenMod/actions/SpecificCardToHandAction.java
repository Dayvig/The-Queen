//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class SpecificCardToHandAction extends AbstractGameAction {
    AbstractPlayer player;
    AbstractCard card;

    public SpecificCardToHandAction(AbstractPlayer p, AbstractCard c) {
        player = p;
        card = c;
    }

    @Override
    public void update() {
        if (player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            player.drawPile.moveToHand(card, player.drawPile);
        }
        isDone = true;
    }
}
