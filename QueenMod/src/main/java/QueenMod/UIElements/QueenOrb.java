package QueenMod.UIElements;


import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.Hornet;
import QueenMod.cards.HoneyBee;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;


public class QueenOrb extends CustomEnergyOrb implements EnergyOrbInterface {
    public static final String[] orbTextures = {
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",};

    private Hitbox tipHornet = new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);
    private Hitbox tipBumble = new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);
    private Hitbox tipWorker = new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);
    private Hitbox tipDrone= new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);

    private final String hornetLabel = "The number of Hornets in your draw pile.";
    private final String bumbleLabel = "The number of Bumblebees in your draw pile.";
    private final String workerLabel = "The number of Workerbees in your draw pile.";
    private final String droneLabel = "The number of Drones in your draw pile. NL You can turn Drones into other bees by Recruiting.";

    private static final String VFXTexture = ("QueenModResources/images/char/queen/orb/bee.png");

    private static final float[] layerSpeeds = new float[]{10.0F, 30.0F, 15.0F, -20.0F, 0.0F};

    public QueenOrb() {
        super(orbTextures, VFXTexture, layerSpeeds);
    }


    @Override
    public void updateOrb(int energyCount) {
        super.updateOrb(energyCount);
        tipHornet.update();
        tipBumble.update();
        tipDrone.update();
        tipWorker.update();
        if (this.tipHornet.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
        if (this.tipBumble.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }

        if (this.tipWorker.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
        if (this.tipDrone.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean active, float current_x, float current_y) {
        int[] i = {0, 0, 0, 0};
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Hornet.ID)) {
                i[0]++;
            } else if (c.cardID.equals(BumbleBee.ID)) {
                i[1]++;
            } else if (c.cardID.equals(Drone.ID)) {
                i[2]++;
            } else if (c.cardID.equals((HoneyBee.ID))) {
                i[3]++;
            }
        }

        if (this.tipHornet.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, "Hornet Counter", hornetLabel);
        }
        if (this.tipBumble.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, "Bumblebee Counter", bumbleLabel);
        }
        if (this.tipWorker.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, "Workerbee Counter", workerLabel);
        }
        if (this.tipDrone.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, "Drone Counter", droneLabel);
        }

        if (active) {
            sb.draw(energyLayers[0], current_x - 155.0F, current_y - 65.0F);
        } else {
            sb.draw(noEnergyLayers[0], current_x - 155.0F, current_y - 65.0F);
        }

        AbstractDungeon.player.getEnergyNumFont().getData().setScale(1.0F);
        tipHornet.x = current_x-60.0F; tipHornet.y = current_y +40.0F;
        tipBumble.x = current_x-85.0F; tipBumble.y = current_y +8.0F;
        tipWorker.x = current_x-60.0F; tipWorker.y = current_y -32.0F;
        tipDrone.x = current_x-105.0F; tipDrone.y = current_y -32.0F;
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[0]+"", current_x-45.0F, current_y+62.0F);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[1]+"", current_x-70.0F, current_y+20.0F);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[3]+"", current_x-45.0F, current_y-20.0F);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[2]+"", current_x-95.0F, current_y-20.0F);

    }
}