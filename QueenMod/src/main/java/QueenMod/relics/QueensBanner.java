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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
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
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final String [] DESCRIPTIONS = relicStrings.DESCRIPTIONS;

    public QueensBanner() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
        usedUp = false;
        counter = 0;
    }

    public void refreshForAct(){
        usedUp = false;
        grayscale = false;
        beginLongPulse();
        pulse = true;
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return AbstractDungeon.player != null ? this.setDescription(AbstractDungeon.player.chosenClass) : this.setDescription((AbstractPlayer.PlayerClass)null);
    }

    @Override
    public Boolean onSave() {
        return usedUp;
    }

    private String setDescription(AbstractPlayer.PlayerClass c) {
        String s = "";
        if (usedUp){
            s = DESCRIPTIONS[1] + DESCRIPTIONS[2] + counter +" / 2"+ DESCRIPTIONS[3];
            s += DESCRIPTIONS[counter + 4];
            return s;
        }
        s = DESCRIPTIONS[0] + DESCRIPTIONS[2] + counter +" / 2"+ DESCRIPTIONS[3];
        s += DESCRIPTIONS[counter + 4];
        return s;
    }

    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onLoad(Boolean aBoolean) {
        usedUp = aBoolean;
        if (usedUp){
            this.grayscale = true;
            this.stopPulse();
            this.pulse = false;
            updateDescription(AbstractDungeon.player.chosenClass);
        }
        else {
            this.grayscale = false;
            this.beginLongPulse();
            this.pulse = true;
            updateDescription(AbstractDungeon.player.chosenClass);
        }
    }
}
