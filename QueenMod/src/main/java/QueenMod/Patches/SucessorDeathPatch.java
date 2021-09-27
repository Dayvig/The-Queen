package QueenMod.Patches;

import QueenMod.characters.TheQueen;
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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DeathScreen;

import java.lang.reflect.Constructor;

public class SucessorDeathPatch {
    @SpirePatch(clz = DeathScreen.class, method = SpirePatch.CONSTRUCTOR)

    public static class SucessorDeath {
        public static void Prefix(DeathScreen __instance) {
            if (AbstractDungeon.player.chosenClass.equals(TheQueen.Enums.THE_QUEEN)){
                System.out.println("Test");
                TheQueen.isSuccessor = false;
            }
        }
    }
}
