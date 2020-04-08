package QueenMod.UIElements;


import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.Hornet;
import QueenMod.cards.WorkerBee;
import QueenMod.characters.TheQueen;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.awt.*;
import java.util.ArrayList;

import static QueenMod.QueenMod.QUEEN_YELLOW;


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


    private static final String VFXTexture = ("QueenModResources/images/char/queen/orb/bee.png");

    private static final float[] layerSpeeds = new float[]{10.0F, 30.0F, 15.0F, -20.0F, 0.0F};

    public QueenOrb() {
        super(orbTextures, VFXTexture, layerSpeeds);
    }


    @Override
    public void updateOrb(int energyCount) {
        super.updateOrb(energyCount);
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
            } else if (c.cardID.equals((WorkerBee.ID))) {
                i[3]++;
            }
        }


        if (active) {
            sb.draw(energyLayers[0], current_x - 155.0F, current_y - 65.0F);
        } else {
            sb.draw(noEnergyLayers[0], current_x - 155.0F, current_y - 65.0F);
        }

        AbstractDungeon.player.getEnergyNumFont().getData().setScale(1.0F);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[0]+"", current_x-45.0F, current_y+62.0F);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[1]+"", current_x-70.0F, current_y+20.0F);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[3]+"", current_x-45.0F, current_y-20.0F);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[2]+"", current_x-95.0F, current_y-20.0F);

    }
}