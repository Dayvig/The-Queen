package QueenMod.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class LoadCardImageAction extends AbstractGameAction {
    private CustomCard c;
    private String imgPath;
    private boolean withFlash;

    public LoadCardImageAction(AbstractCard card, String imgPath, boolean withFlash) {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_MED;
        c = (CustomCard)card;
        this.imgPath = imgPath;
        this.withFlash = withFlash;
    }


    @Override
    public void update() {
        c.loadCardImage(imgPath);
        if (withFlash) {
            c.flash();
        }
        isDone = true;
        System.out.println("Card Image loaded!");
    }
}