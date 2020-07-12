package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.Drone;
import QueenMod.cards.HiveDefenses;
import QueenMod.events.HiveEventScouting;
import QueenMod.rooms.HiveRoom;
import QueenMod.util.TextureLoader;
import basemod.CustomEventRoom;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
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

public class QueensBanner extends CustomRelic implements CustomSavable<Boolean> {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("QueensBanner");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("banner.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("banner.png"));
    private static String BaseFlavor;
    boolean realUsedUp;

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
        this.description = DESCRIPTIONS[0];
        this.description += "Current Hive Presence: "+(counter+1)+"/3";
        initializeTips();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEnterRoom(AbstractRoom room){
        super.onEnterRoom(room);
        if (room instanceof HiveRoom){
            this.description = DESCRIPTIONS[0];
            this.description += "Current Hive Presence: "+(counter+1)+"/3";
            initializeTips();
        }
    }

    @Override
    public Boolean onSave() {
        return usedUp;
    }

    @Override
    public void onLoad(Boolean aBoolean) {
        usedUp = aBoolean;
        if (usedUp){
            this.grayscale = true;
        }
        this.description = DESCRIPTIONS[0];
        this.description += "Current Hive Presence: "+(counter+1)+"/3";
        initializeTips();
    }
}
