package QueenMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EnemyData;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DiplomacyAction extends AbstractGameAction {

    boolean p;

    public DiplomacyAction(boolean isPlayer){
        p = isPlayer;
    }

    public void update(){
        if (p) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.type.equals(EnemyData.MonsterType.BOSS)) {
                    if (m.currentHealth < m.maxHealth / 2) {
                        AbstractDungeon.actionManager.addToBottom(new EscapeActionWithRewards(m));
                    }
                }
            }
        }
        isDone = true;
    }
}