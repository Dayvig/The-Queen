package QueenMod.Patches;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.events.HiveEventBuilding;
import QueenMod.events.HiveEventColony;
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

@SpirePatch(clz = AbstractDungeon.class, method = "initializeCardPools")
public class RemoveHiveEventPatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof TheQueen) {
            dungeon_instance.eventList.remove(HiveEventScouting.ID);
            dungeon_instance.eventList.remove(HiveEventBuilding.ID);
            dungeon_instance.eventList.remove(HiveEventColony.ID);
        }
    }
}
