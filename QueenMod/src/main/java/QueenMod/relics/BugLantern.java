package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.Drone;
import QueenMod.powers.Nectar;
import QueenMod.powers.SwarmPower;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class BugLantern extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("BugLantern");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("honeyjar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("honeyjar.png"));
    private static final int AMOUNT = 3;
    public int amount;

    public BugLantern() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
        amount = AMOUNT;
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SwarmPower(AbstractDungeon.player, AbstractDungeon.player, amount),amount));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
