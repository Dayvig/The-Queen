package QueenMod.relics;

import QueenMod.QueenMod;
import QueenMod.actions.MakeTempCardInDrawPileActionFast;
import QueenMod.cards.Drone;
import QueenMod.powers.Nectar;
import QueenMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static QueenMod.QueenMod.makeRelicOutlinePath;
import static QueenMod.QueenMod.makeRelicPath;

public class HoneyJar extends CustomRelic {

    // ID, images, text.
    public static final String ID = QueenMod.makeID("HoneyJar");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("honeyjar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("honeyjar.png"));

    public HoneyJar() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
        counter = 0;
        this.grayscale = true;
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Nectar(AbstractDungeon.player, AbstractDungeon.player, counter),counter));
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.player.hasPower(Nectar.POWER_ID)){
            this.counter = (int) (AbstractDungeon.player.getPower(Nectar.POWER_ID).amount * 0.2);
            if (this.counter > 10){ this.counter = 10; }
            this.grayscale = false;
            this.flash();
        }
        else {
            this.counter = 0;
            this.grayscale = true;
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
