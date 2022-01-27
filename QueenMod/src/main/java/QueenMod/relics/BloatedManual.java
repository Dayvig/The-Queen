package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.actions.ManualAction;
import QueenMod.actions.StrategizeAction;
import QueenMod.cards.Drone;
import QueenMod.powers.Nectar;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class BloatedManual extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("BloatedManual");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("manual.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("manual.png"));

    public BloatedManual() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public void atTurnStartPostDraw() {
        if (AbstractDungeon.player.drawPile.size() >= 8) {
            AbstractDungeon.actionManager.addToBottom(new StrategizeAction());
            this.flash();
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
