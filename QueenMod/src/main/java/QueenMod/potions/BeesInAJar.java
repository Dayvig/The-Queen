package QueenMod.potions;

import QueenMod.QueenMod;
import QueenMod.powers.Nectar;
import QueenMod.powers.SwarmPowerEnemy;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import javax.smartcardio.Card;

public class BeesInAJar extends AbstractPotion {


    public static final String POTION_ID = QueenMod.makeID("BeesInAJar");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BeesInAJar() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main QueenMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.JAR, PotionEffect.NONE, Color.CLEAR, Color.CLEAR, Color.BROWN);
        
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        isThrown = true;
        this.targetRequired = true;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new SwarmPowerEnemy(target, target, potency), potency));
            CardCrawlGame.sound.playA("BEE_ATTACK2", 0.6F);
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new BeesInAJar();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 7;
    }

    public void upgradePotion()
    {
      potency += 1;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}
