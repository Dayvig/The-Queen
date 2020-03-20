package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.actions.MedalAction;
import QueenMod.cards.BumbleBee;
import QueenMod.cards.Drone;
import QueenMod.cards.Hornet;
import QueenMod.interfaces.IsHive;
import QueenMod.powers.Nectar;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class Medal extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("Medal");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("medal.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("medal.png"));

    public Medal() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void atTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new MedalAction());
        this.flash();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
