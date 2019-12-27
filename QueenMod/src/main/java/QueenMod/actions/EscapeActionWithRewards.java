//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EscapeActionWithRewards extends AbstractGameAction {

    boolean rewards = false;

    public EscapeActionWithRewards(AbstractMonster source) {
        this.setValues(source, source);
        this.duration = 0.5F;
        this.actionType = ActionType.TEXT;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractMonster m = (AbstractMonster) this.source;
            m.die();
        }
        this.tickDuration();
    }
}
/*
@SpirePatch(
        clz=AbstractMonster.class,
        method="updateEscapeAnimation",
        paramtypez = {}
)
public static class swarmPatchEscaping  {
    @SpireInsertPatch
            (
                    locator=Locator.class,
                    localvars={}
            )
    public static void updateEscapeAnimation (AbstractMonster es) {
            es.escaped = false;
            StatsScreen.incrementEnemySlain();
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    AbstractRoom.class, "endBattle");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
}


private void updateEscapeAnimation() {

        if (this.escapeTimer != 0.0F) {
            this.flipHorizontal = true;
            this.escapeTimer -= Gdx.graphics.getDeltaTime();
            this.drawX += Gdx.graphics.getDeltaTime() * 400.0F * Settings.scale;
        }

        if (this.escapeTimer < 0.0F) {
            this.escaped = true;
            if (AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.getCurrRoom().isBattleOver && !AbstractDungeon.getCurrRoom().cannotLose) {
                AbstractDungeon.getCurrRoom().endBattle();
            }
        }

    }

 */
