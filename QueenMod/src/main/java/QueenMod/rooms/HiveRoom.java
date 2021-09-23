package QueenMod.rooms;

import QueenMod.QueenMod;
import QueenMod.events.HiveEventBuilding;
import QueenMod.events.HiveEventColony;
import QueenMod.events.HiveEventScouting;
import QueenMod.relics.QueensBanner;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.EventRoom;

public class HiveRoom extends EventRoom {

    public HiveRoom() {
        this.phase = RoomPhase.EVENT;
        this.mapSymbol = "?";
        this.mapImg = ImageMaster.MAP_NODE_EVENT;
        this.mapImgOutline = ImageMaster.MAP_NODE_EVENT_OUTLINE;
    }

    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
//&& !AbstractDungeon.player.getRelic(QueensBanner.ID).usedUp
        if (AbstractDungeon.player.hasRelic(QueensBanner.ID)) {
            if (!AbstractDungeon.player.getRelic(QueensBanner.ID).usedUp) {
                if (AbstractDungeon.player.getRelic(QueensBanner.ID).counter < 0) {
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter = 0;
                }
                if (AbstractDungeon.player.getRelic(QueensBanner.ID).counter > 2) {
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter = 2;
                }
                switch (AbstractDungeon.player.getRelic(QueensBanner.ID).counter) {
                    case 0:
                        this.event = new HiveEventScouting();
                        break;
                    case 1:
                        this.event = new HiveEventBuilding();
                        break;
                    case 2:
                        this.event = new HiveEventColony();
                        break;
                    default:
                        this.event = new HiveEventScouting();
                        break;
                }
                AbstractDungeon.player.getRelic(QueensBanner.ID).usedUp();
                AbstractDungeon.player.getRelic(QueensBanner.ID).stopPulse();
                AbstractDungeon.player.getRelic(QueensBanner.ID).updateDescription(AbstractDungeon.player.chosenClass);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp) {
            this.event.update();
        }

        if (this.event.waitTimer == 0.0F && !this.event.hasFocus && this.phase != RoomPhase.COMBAT) {
            this.phase = RoomPhase.COMPLETE;
            this.event.reopen();
        }

    }

    public void render(SpriteBatch sb) {
        if (this.event != null) {
            this.event.render(sb);
        }

        super.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb) {
        super.renderAboveTopPanel(sb);
        if (this.event != null) {
            this.event.renderAboveTopPanel(sb);
        }

    }
}
