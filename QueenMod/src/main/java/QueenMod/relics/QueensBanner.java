package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.Drone;
import QueenMod.events.HiveEventScouting;
import QueenMod.util.TextureLoader;
import basemod.CustomEventRoom;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.StartActSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;

import java.util.ArrayList;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class QueensBanner extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("QueensBanner");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("banner.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("banner.png"));

    public QueensBanner() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
        usedUp = false;
        counter = 0;
    }

    public void refreshForAct(){
        usedUp = false;
        beginPulse();
        pulse = true;
        grayscale = false;
        description = DESCRIPTIONS[0];
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
