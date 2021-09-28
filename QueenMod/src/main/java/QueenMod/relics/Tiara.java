package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.actions.SuccessionAction;
import QueenMod.cards.Drone;
import QueenMod.powers.CoronationPower;
import QueenMod.powers.Nectar;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class Tiara extends CustomRelic implements OnPlayerDeathRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("Tiara");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Tiara.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("honeyjar.png"));

    public Tiara() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        if (!this.usedUp) {
            AbstractDungeon.actionManager.addToBottom(new SuccessionAction());
            this.usedUp();
            return false;
        }
        else {
            return true;
        }
    }
}
