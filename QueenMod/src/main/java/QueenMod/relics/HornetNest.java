package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.Drone;
import QueenMod.cards.Hornet;
import QueenMod.powers.Nectar;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CentennialPuzzle;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class HornetNest extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("HornetNest");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("honeyjar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("honeyjar.png"));

    private boolean usedThisCombat;

    public HornetNest() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    public void atPreBattle() {
        usedThisCombat = false;
        this.pulse = true;
        this.beginPulse();
    }

    public void wasHPLost(int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !usedThisCombat) {
            this.flash();
            this.pulse = false;
            this.addToTop(new MakeTempCardInHandAction(new Hornet(), 3));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            usedThisCombat = true;
            this.grayscale = true;
        }

    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void onVictory() {
        this.pulse = false;
    }

    public AbstractRelic makeCopy() {
        return new HornetNest();
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
