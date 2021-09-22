package QueenMod.Patches;

import QueenMod.powers.BusyBeesPower;
import QueenMod.relics.QueenSceptre;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import QueenMod.interfaces.CardAddedToDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BusybeesPatch {
    @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = "update")

    public static class BusybeesAdd {
    public static void Prefix(MakeTempCardInDrawPileAction __instance, AbstractCard ___cardToMake) {
        if (AbstractDungeon.player.hasPower(BusyBeesPower.POWER_ID)){
            __instance.amount += AbstractDungeon.player.getPower(BusyBeesPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasRelic(QueenSceptre.ID)){
            QueenSceptre q = (QueenSceptre)AbstractDungeon.player.getRelic(QueenSceptre.ID);
            if (q.isActive) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(___cardToMake, 1));
                __instance.amount--;
                q.isActive = false;
                q.grayscale = true;
                q.stopPulse();
                if (__instance.amount <= 0) {
                    __instance.isDone = true;
                }
            }
        }
    }
}
}