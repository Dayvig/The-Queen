package QueenMod.Patches;

import QueenMod.powers.BusyBeesPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import QueenMod.interfaces.CardAddedToDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BusybeesPatch {
    @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = "update")

    public static class BusybeesAdd {
        public static void Prefix(MakeTempCardInDrawPileAction __instance) {
            if (AbstractDungeon.player.hasPower(BusyBeesPower.POWER_ID)){
                __instance.amount += AbstractDungeon.player.getPower(BusyBeesPower.POWER_ID).amount;
            }
        }
    }
}