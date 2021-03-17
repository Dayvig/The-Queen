package QueenMod.events;

import QueenMod.QueenMod;
import QueenMod.relics.QueensBanner;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static QueenMod.QueenMod.makeEventPath;

public class HiveEventScouting extends AbstractImageEvent {


    public static final String ID = QueenMod.makeID("HiveEventScouting");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("scouts.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private boolean hasBasic = false;
    private boolean hasNonBasic = false;
    private boolean cardSelect;

    public HiveEventScouting() {
        super(NAME, DESCRIPTIONS[0], IMG);

        boolean hasBasic = false;
        boolean hasNonBasic = false;
        for (AbstractCard c: CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).group){
            if (c.rarity.equals(AbstractCard.CardRarity.BASIC)){
                hasBasic = true;
            }
            else {
                hasNonBasic = true;
            }
        }
        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Recall - Gain a yellow card and 100 gold.
        imageEventText.setDialogOption(OPTIONS[1], !hasBasic); // Delegate - Remove a basic card from your deck.
        imageEventText.setDialogOption(OPTIONS[2], !hasNonBasic); // Invest - [Remove a non-basic card from your deck]: Improve future Hive Events.
        imageEventText.setDialogOption(OPTIONS[3]); // Leave

    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1;
                        this.reward();

                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Deinal
                        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            CardGroup g = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards());
                            for (AbstractCard c : g.group){
                                if (c.rarity.equals(AbstractCard.CardRarity.BASIC)){
                                    tmp.addToBottom(c);
                                }
                            }
                            AbstractDungeon.gridSelectScreen.open(tmp, 1, OPTIONS[1], false, false, false, true);
                            this.cardSelect = true;
                        }

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        // Same as before. A note here is that you can also do
                        // imageEventText.clearAllDialogs();
                        // imageEventText.setDialogOption(OPTIONS[1]);
                        // imageEventText.setDialogOption(OPTIONS[4]);
                        // (etc.)
                        // And that would also just set them into slot 0, 1, 2... in order, just like what we do in the very beginning

                        break;
                    case 2:
                        CardGroup tmp2 = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            CardGroup g = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards());
                            for (AbstractCard c : g.group){
                                if (!c.rarity.equals(AbstractCard.CardRarity.BASIC)){
                                    tmp2.addToBottom(c);
                                }
                            }
                            AbstractDungeon.gridSelectScreen.open(tmp2, 1, OPTIONS[2], false, false, false, true);
                            this.cardSelect = true;
                        }

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        // Same as before. A note here is that you can also do
                        // imageEventText.clearAllDialogs();
                        // imageEventText.setDialogOption(OPTIONS[1]);
                        // imageEventText.setDialogOption(OPTIONS[4]);
                        // (etc.)
                        // And that would also just set them into slot 0, 1, 2... in order, just like what we do in the very beginning

                        break;
                    case 3:
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
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
        //AbstractDungeon.getCurrRoom().addCardReward(new RewardItem());
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
                    this.screenNum = 1;
                case BASIC:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.screenNum = 1;
                    break;
                case COMMON:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter++;
                    this.screenNum = 1;
                    break;
                case UNCOMMON:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter++;
                    this.screenNum = 1;
                    break;
                case RARE:
                    this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                    AbstractDungeon.player.getRelic(QueensBanner.ID).counter += 2;
                    this.screenNum = 1;
                default:
                    this.screenNum = 1;
            }
            // if you want to continue using the other selected cards for something
        }

    }

}
