package QueenMod.events;

import QueenMod.QueenMod;
import QueenMod.relics.QueensBanner;
import basemod.devcommands.gold.Gold;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import static QueenMod.QueenMod.makeEventPath;

public class HiveEventBuilding extends AbstractImageEvent {


    public static final String ID = QueenMod.makeID("HiveEventBuilding");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("builders.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private boolean cardSelect = false;

    public HiveEventBuilding() {
        super(NAME, DESCRIPTIONS[0], IMG);
        noCardsInRewards = true;
        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Collect - Gain an uncommon relic and 100 gold.

    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        System.out.println("asf");

                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.updateDialogOption(1, OPTIONS[2]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event

                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
                        AbstractDungeon.combatRewardScreen.setupItemReward();
                        AbstractDungeon.combatRewardScreen.open();
                        screenNum = 1;
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 2;
                        this.reward();

                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Deinal

                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[1], false, false, false, true);
                            this.cardSelect = true;
                        }

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;

                        // Same as before. A note here is that you can also do
                        // imageEventText.clearAllDialogs();
                        // imageEventText.setDialogOption(OPTIONS[1]);
                        // imageEventText.setDialogOption(OPTIONS[4]);
                        // (etc.)
                        // And that would also just set them into slot 0, 1, 2... in order, just like what we do in the very beginning

                        break; // Onto screen 1 we go.
                }
                break;
            case 2:
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

    private void reward() {
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().addCardReward(new RewardItem());
        AbstractDungeon.getCurrRoom().addCardReward(new RewardItem());
        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0); // Get the card
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2))); // Create the card removal effect
            AbstractDungeon.player.masterDeck.removeCard(c); // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Or you can .remove(c) instead of clear,
            switch (c.rarity){
                case CURSE:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                case BASIC:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    break;
                case COMMON:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter++;
                    break;
                case UNCOMMON:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter++;
                    break;
                case RARE:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter += 2;
                default:
            }
            // if you want to continue using the other selected cards for something
        }

    }

}
