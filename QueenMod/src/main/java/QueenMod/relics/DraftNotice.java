package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.HoneyBee;
import QueenMod.cards.Hornet;
import QueenMod.powers.Nectar;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class DraftNotice extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("DraftNotice");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("draftnotice.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("draftnotice.png"));

    public DraftNotice() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileActionFast(new Drone(), 3, true, false));
        this.flash();
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
