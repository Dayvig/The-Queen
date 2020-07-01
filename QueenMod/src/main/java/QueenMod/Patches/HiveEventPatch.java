package QueenMod.Patches;

import QueenMod.events.HiveEventScouting;
import QueenMod.powers.BusyBeesPower;
import QueenMod.relics.QueensBanner;
import QueenMod.rooms.HiveRoom;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import QueenMod.interfaces.CardAddedToDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class HiveEventPatch {
    @SpirePatch(clz = AbstractDungeon.class, method = "generateRoom")

    public static class HiveEventAdd {
        public static SpireReturn Prefix(AbstractDungeon __instance) {
            if (AbstractDungeon.player.hasRelic(QueensBanner.ID) && !AbstractDungeon.player.getRelic(QueensBanner.ID).usedUp){

                AbstractDungeon.player.getRelic(QueensBanner.ID).usedUp = true;
                AbstractDungeon.player.getRelic(QueensBanner.ID).stopPulse();

                return SpireReturn.Return(new HiveRoom());

            }
            else {
                return SpireReturn.Continue();
            }
        }
    }
}