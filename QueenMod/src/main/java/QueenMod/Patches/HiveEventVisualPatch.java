package QueenMod.Patches;

import QueenMod.events.HiveEventScouting;
import QueenMod.powers.BusyBeesPower;
import QueenMod.relics.QueensBanner;
import QueenMod.rooms.HiveRoom;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import QueenMod.interfaces.CardAddedToDeck;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.vfx.FlameAnimationEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class HiveEventVisualPatch {
    @SpirePatch(clz = MapRoomNode.class, method = "render", paramtypez = SpriteBatch.class)
    public static class HiveEventVisualPatchAdd {

        private static float hiveVfxTimer = 0.0F;
        private static final Texture beemap = ImageMaster.loadImage("QueenModResources/images/char/queen/map_bee.png");

        public static void Prefix(MapRoomNode __instance, SpriteBatch sb) {
            float offsetX = (float)ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_X");
            float offsetY = (float)ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_Y");
            float spacingX = (float)ReflectionHacks.getPrivateStatic(MapRoomNode.class, "SPACING_X");
            float scale = (float) ReflectionHacks.getPrivate(__instance, MapRoomNode.class, "scale");
            if (AbstractDungeon.player.hasRelic(QueensBanner.ID)) {
                if (!AbstractDungeon.player.getRelic(QueensBanner.ID).usedUp && __instance.room instanceof EventRoom) {

                    sb.draw(beemap,
                            __instance.x * spacingX + offsetX - 64.0f + __instance.offsetX,
                            __instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 50.0f + __instance.offsetY,
                            64.0f,
                            50.0f,
                            128.0f,
                            100.0f,
                            (scale * 0.65f + 0.2f) * Settings.scale,
                            (scale * 0.65f + 0.2f) * Settings.scale,
                            0,
                            0,
                            0,
                            128, 100,
                                false, false);
                    /*
                    sb.draw(beemap,
                            (__instance.x * spacingX + offsetX - 64.0f + __instance.offsetX),
                            (__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 56.0f + __instance.offsetY),
                            64.0F, 64.0F, beemap.getWidth()*Settings.scale, beemap.getHeight()*Settings.scale, (scale * 0.65f + 0.2f) * Settings.scale,
                            (scale * 0.65f + 0.2f) * Settings.scale, 0, 0, 0, (int)(beemap.getWidth()*Settings.scale), (int)(beemap.getHeight()*Settings.scale),
                            false, false);*/
                }
            }
        }
    }
}

