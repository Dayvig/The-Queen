package QueenMod.UIElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;

import static QueenMod.QueenMod.QUEEN_YELLOW;

public class HiveCounterPanel extends AbstractPanel {

    public static float fontScale;
    public static final float FONT_POP_SCALE = 2.0F;
    public static int totalCount;
    private Hitbox tipHitbox;
    private Texture gainEnergyImg;
    private float energyVfxAngle;
    private float energyVfxScale;
    private Color energyVfxColor;
    public static float energyVfxTimer;
    private static final Color ENERGY_TEXT_COLOR = QUEEN_YELLOW;
    public static  String MSG;
    public static String LABEL;
    AbstractCard cardToDisplay;




    public HiveCounterPanel(AbstractCard card, float realxpos, float realypos){
        super(realxpos * Settings.scale, realypos * Settings.scale, -480.0F * Settings.scale, 200.0F * Settings.scale, 12.0F * Settings.scale, -12.0F * Settings.scale, (Texture)null, true);
        this.tipHitbox = new Hitbox(0.0F, 0.0F, 120.0F * Settings.scale, 120.0F * Settings.scale);
        this.energyVfxAngle = 0.0F;
        this.energyVfxScale = Settings.scale;
        this.energyVfxColor = Color.WHITE.cpy();
        MSG = "The number of "+card.cardID + " in your draw pile.";
        LABEL = card.cardID + "counter.";
        cardToDisplay = card;
        fontScale = 2.0F;
    }

    public static void setEnergy(int energy) {
        totalCount = energy;
        energyVfxTimer = 2.0F;
        fontScale = 2.0F;
    }

    public void update() {
        this.updateVfx();

        if (fontScale != 1.0F) {
            fontScale = MathHelper.scaleLerpSnap(fontScale, 1.0F);
        }

        this.tipHitbox.update();
        if (this.tipHitbox.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
    }

    private void updateVfx() {
        if (energyVfxTimer != 0.0F) {
            this.energyVfxColor.a = Interpolation.exp10In.apply(0.5F, 0.0F, 1.0F - energyVfxTimer / 2.0F);
            this.energyVfxAngle += Gdx.graphics.getDeltaTime() * -30.0F;
            this.energyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0F, 0.1F, 1.0F - energyVfxTimer / 2.0F);
            energyVfxTimer -= Gdx.graphics.getDeltaTime();
            if (energyVfxTimer < 0.0F) {
                energyVfxTimer = 0.0F;
                this.energyVfxColor.a = 0.0F;
            }
        }

    }

    public void render(SpriteBatch sb) {

        System.out.println("Card: "+cardToDisplay.cardID);
        System.out.println("Current X: "+this.current_x);
        System.out.println("Current Y: "+this.current_x);
        System.out.println("Total Counter: "+totalCount);
        System.out.println("==============");

        this.tipHitbox.move(this.current_x, this.current_y);
        String energyMsg = totalCount+"";
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(fontScale);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), energyMsg, this.current_x, this.current_y, ENERGY_TEXT_COLOR);
        this.tipHitbox.render(sb);
        if (this.tipHitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, LABEL, MSG);
        }

    }






}
