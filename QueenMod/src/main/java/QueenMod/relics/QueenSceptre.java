package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.Drone;
import QueenMod.powers.Nectar;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class QueenSceptre extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("QueenSceptre");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("sceptre.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("sceptre.png"));
    public boolean isActive = true;

    public QueenSceptre() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart(){
        isActive = true;
        grayscale = false;
        this.pulse = true;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
