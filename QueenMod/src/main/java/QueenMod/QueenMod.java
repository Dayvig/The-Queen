package QueenMod;

import QueenMod.cards.*;
import QueenMod.characters.TheQueen;
import QueenMod.events.IdentityCrisisEvent;
import QueenMod.potions.PlaceholderPotion;
import QueenMod.relics.BottledPlaceholderRelic;
import QueenMod.relics.DefaultClickableRelic;
import QueenMod.relics.QueensBanner;
import QueenMod.relics.PlaceholderRelic2;
import QueenMod.util.IDCheckDontTouchPls;
import QueenMod.util.TextureLoader;
import QueenMod.variables.DefaultCustomVariable;
import QueenMod.variables.DefaultSecondMagicNumber;
import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 */

@SpireInitializer
public class QueenMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber
{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(QueenMod.class.getName());
    private static String modID;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "QueenMod";
    private static final String AUTHOR = "Dayvigilante"; // And pretty soon - You!
    private static final String DESCRIPTION = "A deposed queen bee, seeking to conquer the spire and claim it as her new hive. Modify your deck with powerful Hive cards, and recruit and manage a deadly swarm.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color QUEEN_YELLOW = CardHelper.getColor(247.0f, 219.0f, 93.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_QUEEN = "QueenModResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_QUEEN = "QueenModResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_QUEEN = "QueenModResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_QUEEN = "QueenModResources/images/512/card_queen_orb.png";
    private static final String ENERGY_ORB_QUEEN_SMALL = "QueenModResources/images/512/card_queen_small_orb.png";

    private static final String ATTACK_QUEEN_PORTRAIT = "QueenModResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_QUEEN_PORTRAIT = "QueenModResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_QUEEN_PORTRAIT = "QueenModResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_QUEEN_PORTRAIT = "QueenModResources/images/1024/card_default_gray_orb.png";

    // Character assets
    private static final String QUEEN_BUTTON = "QueenModResources/images/charSelect/DefaultCharacterButton.png";
    private static final String QUEEN_PORTRAIT = "QueenModResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String QUEEN_SHOULDER_1 = "QueenModResources/images/char/defaultCharacter/shoulder.png";
    public static final String QUEEN_SHOULDER_2 = "QueenModResources/images/char/defaultCharacter/shoulder2.png";
    public static final String QUEEN_CORPSE = "QueenModResources/images/char/defaultCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "QueenModResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String QUEEN_SKELETON_ATLAS = "QueenModResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String QUEEN_SKELETON_JSON = "QueenModResources/images/char/defaultCharacter/skeleton.json";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_YELLOW, INITIALIZE =================

    public QueenMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CHANGE YOUR MOD ID HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        setModID("QueenMod");
        // Now go to your resources folder in the project panel, and refactor> rename QueenModResources to
        // yourModIDResources.
        // Also click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        // FINALLY and most importnatly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        logger.info("Done subscribing");

        logger.info("Creating the color " + TheQueen.Enums.COLOR_YELLOW.toString());

        BaseMod.addColor(TheQueen.Enums.COLOR_YELLOW, QUEEN_YELLOW, QUEEN_YELLOW, QUEEN_YELLOW,
                QUEEN_YELLOW, QUEEN_YELLOW, QUEEN_YELLOW, QUEEN_YELLOW,
                ATTACK_QUEEN, SKILL_QUEEN, POWER_QUEEN, ENERGY_ORB_QUEEN,
                ATTACK_QUEEN_PORTRAIT, SKILL_QUEEN_PORTRAIT, POWER_QUEEN_PORTRAIT,
                ENERGY_ORB_QUEEN_PORTRAIT, ENERGY_ORB_QUEEN_SMALL);

        logger.info("Done creating the color");
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStrings.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = QueenMod.class.getResourceAsStream("/IDCheckStrings.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT

        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStrings.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = QueenMod.class.getResourceAsStream("/IDCheckStrings.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT

        String packageName = QueenMod.class.getPackage().getName(); // STILL NOT EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    // ====== YOU CAN EDIT AGAIN ======


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        QueenMod defaultmod = new QueenMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_YELLOW, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheQueen.Enums.THE_QUEEN.toString());
        BaseMod.addCharacter(new TheQueen("The Queen", TheQueen.Enums.THE_QUEEN),
                QUEEN_BUTTON, QUEEN_PORTRAIT, TheQueen.Enums.THE_QUEEN);
        receiveEditPotions();
        logger.info("Added " + TheQueen.Enums.THE_QUEEN.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================


    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("QueenMod doesn't have any settings! An example of those may come later.", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        // =============== EVENTS =================

        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");

    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD POTIONS ===================


    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "Enum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheQueen.Enums.THE_QUEEN);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new QueensBanner(), TheQueen.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheQueen.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheQueen.Enums.COLOR_YELLOW);

        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    // ================ ADD AUDIO ====================


    @Override
    public void receiveAddAudio(){
        BaseMod.addAudio("BEE_SLOW", "QueenModResources/sound/bee_slow.ogg");
        BaseMod.addAudio("BEE_ATTACK1", "QueenModResources/sound/bee_attack1.ogg");
        BaseMod.addAudio("BEE_ATTACK2", "QueenModResources/sound/bee_attack2.ogg");
    }


    // ================ /ADD AUDIO/ ====================

    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of e0ach type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        BaseMod.addCard(new Garrison());
        BaseMod.addCard(new IAmTheSwarm());
        BaseMod.addCard(new Feint());
        BaseMod.addCard(new HoldPosition());
        BaseMod.addCard(new Fortify());
        BaseMod.addCard(new Drain());
        BaseMod.addCard(new Leadership());
        BaseMod.addCard(new TeaTime());
        BaseMod.addCard(new CallToArms());
        BaseMod.addCard(new Frenzy());
        BaseMod.addCard(new Recruit());
        BaseMod.addCard(new SupplyLines());
        BaseMod.addCard(new OverwhelmingPower());
        BaseMod.addCard(new MatingDance());
        BaseMod.addCard(new AggressionPolicy());
        BaseMod.addCard(new Multiply());
        BaseMod.addCard(new Fleche());
        BaseMod.addCard(new FullRetreat());
        BaseMod.addCard(new Reposition());
        BaseMod.addCard(new HoneyShield());
        BaseMod.addCard(new Synthesize());
        BaseMod.addCard(new HonorGuard());
        BaseMod.addCard(new Gather());
        BaseMod.addCard(new WarTrumpet());
        BaseMod.addCard(new Regroup());
        BaseMod.addCard(new Reinforcements());
        BaseMod.addCard(new FlankingStrike());
        BaseMod.addCard(new KillerBee());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Incubate());
        BaseMod.addCard(new Conscripts());
        BaseMod.addCard(new Spearhead());
        BaseMod.addCard(new Charge());
        BaseMod.addCard(new FinishingBlow());
        BaseMod.addCard(new Ambush());
        BaseMod.addCard(new Riposte());
        BaseMod.addCard(new Parry());
        BaseMod.addCard(new Lunge());
        BaseMod.addCard(new Knighting());
        BaseMod.addCard(new EliteSoldiers());
        BaseMod.addCard(new InspiringStrike());
        BaseMod.addCard(new GeneralForm());
        BaseMod.addCard(new SecretWeapon());
        BaseMod.addCard(new BlindingSwarm());
        BaseMod.addCard(new PollenBlast());
        BaseMod.addCard(new MilitaryHandbook());
        BaseMod.addCard(new HoneyCorps());
        BaseMod.addCard(new Anticipate());
        BaseMod.addCard(new CrossGuard());
        BaseMod.addCard(new DroneSpawner());
        BaseMod.addCard(new Feast());
        BaseMod.addCard(new SplitStrike());
        BaseMod.addCard(new PerfectLanding());
        BaseMod.addCard(new Flyby());
        BaseMod.addCard(new OverwhelmingForce());
        BaseMod.addCard(new StrategicGenius());
        BaseMod.addCard(new SwarmTactics());
        BaseMod.addCard(new Mark());
        BaseMod.addCard(new EnergyReserves());
        BaseMod.addCard(new SharpStingers());
        BaseMod.addCard(new ProtectQueen());
        BaseMod.addCard(new ChainStrike());
        BaseMod.addCard(new WarBuzz());
        BaseMod.addCard(new HornetSwarm());
        BaseMod.addCard(new Nourish());
        BaseMod.addCard(new Metamorphosis());
        BaseMod.addCard(new Beekeeping());
        BaseMod.addCard(new BusyBees());
        BaseMod.addCard(new HiveNetwork());
        BaseMod.addCard(new KillerQueen());
        BaseMod.addCard(new Assimilate());
        BaseMod.addCard(new WorkerBee());
        BaseMod.addCard(new BuildOrder());
        BaseMod.addCard(new Drone());
        BaseMod.addCard(new Draft());
        BaseMod.addCard(new HoneycombSmash());
        BaseMod.addCard(new HexGuard());
        BaseMod.addCard(new GatheringSwarm());
        BaseMod.addCard(new BumbleBee());
        BaseMod.addCard(new DefendOrder());
        BaseMod.addCard(new Hornet());
        BaseMod.addCard(new AttackOrder());
        BaseMod.addCard(new GnatSquadron());
        BaseMod.addCard(new DignifiedSlap());

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.

        UnlockTracker.unlockCard(Garrison.ID);
        UnlockTracker.unlockCard(IAmTheSwarm.ID);
        UnlockTracker.unlockCard(Feint.ID);
        UnlockTracker.unlockCard(HoldPosition.ID);
        UnlockTracker.unlockCard(Fortify.ID);
        UnlockTracker.unlockCard(Drain.ID);
        UnlockTracker.unlockCard(Leadership.ID);
        UnlockTracker.unlockCard(TeaTime.ID);
        UnlockTracker.unlockCard(Frenzy.ID);
        UnlockTracker.unlockCard(Recruit.ID);
        UnlockTracker.unlockCard(SupplyLines.ID);
        UnlockTracker.unlockCard(MatingDance.ID);
        UnlockTracker.unlockCard(OverwhelmingPower.ID);
        UnlockTracker.unlockCard(AggressionPolicy.ID);
        UnlockTracker.unlockCard(Multiply.ID);
        UnlockTracker.unlockCard(Fleche.ID);
        UnlockTracker.unlockCard(HoneyShield.ID);
        UnlockTracker.unlockCard(FullRetreat.ID);
        UnlockTracker.unlockCard(Reposition.ID);
        UnlockTracker.unlockCard(Synthesize.ID);
        UnlockTracker.unlockCard(HonorGuard.ID);
        UnlockTracker.unlockCard(Gather.ID);
        UnlockTracker.unlockCard(WarTrumpet.ID);
        UnlockTracker.unlockCard(Regroup.ID);
        UnlockTracker.unlockCard(Reinforcements.ID);
        UnlockTracker.unlockCard(FlankingStrike.ID);
        UnlockTracker.unlockCard(KillerBee.ID);
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(Incubate.ID);
        UnlockTracker.unlockCard(Conscripts.ID);
        UnlockTracker.unlockCard(SharpStingers.ID);
        UnlockTracker.unlockCard(EnergyReserves.ID);
        UnlockTracker.unlockCard(Mark.ID);
        UnlockTracker.unlockCard(SwarmTactics.ID);
        UnlockTracker.unlockCard(StrategicGenius.ID);
        UnlockTracker.unlockCard(OverwhelmingForce.ID);
        UnlockTracker.unlockCard(Flyby.ID);
        UnlockTracker.unlockCard(PerfectLanding.ID);
        UnlockTracker.unlockCard(SplitStrike.ID);
        UnlockTracker.unlockCard(Feast.ID);
        UnlockTracker.unlockCard(DroneSpawner.ID);
        UnlockTracker.unlockCard(CrossGuard.ID);
        UnlockTracker.unlockCard(Anticipate.ID);
        UnlockTracker.unlockCard(HoneyCorps.ID);
        UnlockTracker.unlockCard(MilitaryHandbook.ID);
        UnlockTracker.unlockCard(PollenBlast.ID);
        UnlockTracker.unlockCard(BlindingSwarm.ID);
        UnlockTracker.unlockCard(SecretWeapon.ID);
        UnlockTracker.unlockCard(GeneralForm.ID);
        UnlockTracker.unlockCard(InspiringStrike.ID);
        UnlockTracker.unlockCard(EliteSoldiers.ID);
        UnlockTracker.unlockCard(DroneCommander.ID);
        UnlockTracker.unlockCard(WorkerBeeCommander.ID);
        UnlockTracker.unlockCard(BumbleBeeCommander.ID);
        UnlockTracker.unlockCard(HornetCommander.ID);
        UnlockTracker.unlockCard(Knighting.ID);
        UnlockTracker.unlockCard(Lunge.ID);
        UnlockTracker.unlockCard(Parry.ID);
        UnlockTracker.unlockCard(Riposte.ID);
        UnlockTracker.unlockCard(Ambush.ID);
        UnlockTracker.unlockCard(FinishingBlow.ID);
        UnlockTracker.unlockCard(Charge.ID);
        UnlockTracker.unlockCard(Spearhead.ID);
        UnlockTracker.unlockCard(Draft.ID);
        UnlockTracker.unlockCard(Drone.ID);
        UnlockTracker.unlockCard(BuildOrder.ID);
        UnlockTracker.unlockCard(WorkerBee.ID);
        UnlockTracker.unlockCard(Assimilate.ID);
        UnlockTracker.unlockCard(KillerQueen.ID);
        UnlockTracker.unlockCard(HiveNetwork.ID);
        UnlockTracker.unlockCard(BusyBees.ID);
        UnlockTracker.unlockCard(Beekeeping.ID);
        UnlockTracker.unlockCard(Metamorphosis.ID);
        UnlockTracker.unlockCard(Nourish.ID);
        UnlockTracker.unlockCard(HornetSwarm.ID);
        UnlockTracker.unlockCard(WarBuzz.ID);
        UnlockTracker.unlockCard(ChainStrike.ID);
        UnlockTracker.unlockCard(HoneycombSmash.ID);
        UnlockTracker.unlockCard(HexGuard.ID);
        UnlockTracker.unlockCard(GatheringSwarm.ID);

        logger.info("Done adding cards!");
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/QueenMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/QueenMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/QueenMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/QueenMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/QueenMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/QueenMod-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/QueenMod-Orb-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/QueenMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

}
