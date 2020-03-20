package QueenMod.actions;

import QueenMod.cards.BumbleBee;
import QueenMod.cards.Hornet;
import QueenMod.powers.HeartOfTheSwarm;
import QueenMod.powers.SwarmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Iterator;
import java.util.UUID;

public class MedalAction extends AbstractGameAction {
    private int increaseAmount;
    private DamageInfo info;
    private UUID uuid;
    private int amoun;

    public MedalAction() {

    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.cardID.equals(Hornet.ID) || c.cardID.equals(BumbleBee.ID)) {
                c.freeToPlayOnce = true;
            }
        }
        isDone = true;
    }
}
