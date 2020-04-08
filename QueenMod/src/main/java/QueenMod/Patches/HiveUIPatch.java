/*package QueenMod.Patches;

import QueenMod.UIElements.HiveCounterPanel;
import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.Hornet;
import QueenMod.cards.WorkerBee;
import QueenMod.characters.TheQueen;
import QueenMod.util.TextureLoader;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import QueenMod.interfaces.CardAddedToDeck;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.ui.buttons.Button;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.overlayMenu;

public class HiveUIPatch {
    public HiveCounterPanel hornetCounter = new HiveCounterPanel(new Hornet(), 168.0F, 190.0F);
    public HiveCounterPanel bumbleCounter = new HiveCounterPanel(new BumbleBee(), 148.0F, 190.0F);
    public HiveCounterPanel workerCounter = new HiveCounterPanel(new WorkerBee(), 128.0F, 190.0F);
    public HiveCounterPanel droneCounter = new HiveCounterPanel(new Drone(), 108.0F, 190.0F);



    @SpirePatch(clz = OverlayMenu.class, method = "showCombatPanels")

    public class UIShowPatch{
        public void Postfix (OverlayMenu _instance) {
            hornetCounter.show();
            bumbleCounter.show();
            workerCounter.show();
            droneCounter.show();
        }
    }

    @SpirePatch(clz = OverlayMenu.class, method = "hideCombatPanels")

    public static class UIHidePatch{
        public static void Postfix (OverlayMenu _instance) {
            hornetCounter.hide();
            bumbleCounter.hide();
            workerCounter.hide();
            droneCounter.hide();
        }
    }

    @SpirePatch(clz = OverlayMenu.class, method = "update")

    public static class UIUpdatePatch{
        public static void Postfix (OverlayMenu _instance) {
            hornetCounter.update();
            bumbleCounter.update();
            workerCounter.update();
            droneCounter.update();
        }
    }

    @SpirePatch(clz = OverlayMenu.class, method = "render")

    public static class UIRenderPatch{
        public static void Postfix (OverlayMenu _instance, SpriteBatch sb) {
            hornetCounter.render(sb);
            bumbleCounter.render(sb);
            workerCounter.render(sb);
            droneCounter.render(sb);
        }
    }


    @SpirePatch(clz = CardGroup.class, method = "refreshHandLayout")

    public static class UICountPatch{
        public static void Postfix (CardGroup _instance) {
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                if (c.cardID.equals(Hornet.ID)){
                    i1++;
                }
                if (c.cardID.equals(BumbleBee.ID)){
                    i2++;
                }
                if (c.cardID.equals(WorkerBee.ID)){
                    i3++;
                }
                if (c.cardID.equals(Drone.ID)){
                    i4++;
                }
                }
            hornetCounter.setEnergy(i1);
            bumbleCounter.setEnergy(i2);
            workerCounter.setEnergy(i3);
            droneCounter.setEnergy(i4);
            }
        }
    }*/