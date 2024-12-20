package kameib.localizator.handlers;

import kameib.localizator.Localizator;
import kameib.localizator.data.Production;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = Localizator.MODID)
@SuppressWarnings("unused")
public class ForgeConfigHandler {
	@Config.Comment("Enable/Disable Localization Mixins (Tweaks)")
	@Config.Name("Localizing_Mixins")
	@Config.LangKey("config.localizator.localizingMixins")
	public static final LocalizingMixinsConfig localizingMixinsConfig = new LocalizingMixinsConfig();

	@Config.Comment("Enable/Disable QoL/bug-fix Mixins (Tweaks)")
	@Config.Name("Miscelaneous_Mixins")
	@Config.LangKey("config.localizator.miscelaneousMixins")
	public static final MiscelaneousMixinsConfig miscelaneousMixinsConfig = new MiscelaneousMixinsConfig();

	@Config.Comment("Apply Enabled Server-Side Mixins")
	@Config.Name("Server")
	@Config.LangKey("config.localizator.server")
	public static final ServerConfig serverConfig = new ServerConfig();
	
	@Config.Comment("Apply Enabled Client-side Mixins")
	@Config.Name("Client")
	@Config.LangKey("config.localizator.client")
	public static final ClientConfig clientConfig = new ClientConfig();
	
	private static HashSet<Block> noveltyBlocksWhitelist = null;

	public static class LocalizingMixinsConfig {
		@Config.Comment("Enables the Client config: \n- (Minecraft) Translate Mob Custom Names")
		@Config.Name("(Minecraft) Mob Custom Names Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftMobLocCustomNamesMixin")
		@Config.RequiresMcRestart
		public boolean minecraftMobLocCustomNamesMixin = !Production.inProduction;
		
		@Config.Comment("Enables the Client config: \n- (Minecraft) Translate Boss Custom Names")
		@Config.Name("(Minecraft) Boss Custom Names Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftBossLocCustomNamesMixin")
		@Config.RequiresMcRestart
		public boolean minecraftBossLocCustomNamesMixin = !Production.inProduction;
		
		@Config.Comment("Enables Localized Lore \"LocLore\" NBT tag support.\nFor items with translatable \"lore\".")
		@Config.Name("(Minecraft) Localized Lore Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocLoreMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocLoreMixin = !Production.inProduction;
		
		@Config.Comment("Makes the \"LocName\" NBT tag, support language keys that accept parameters.\nAlso, enables the Server config: \n- (Minecraft) Show LocName instead of Name")
		@Config.Name("(Minecraft) Better Localized Name Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocNameMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocNameMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Biome Names. \nFor modded biomes, please create and include their respective lang keys in your modpack")
		@Config.Name("(Minecraft) Localized Biome Name Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftBiomeMixin")
		@Config.RequiresMcRestart
		public boolean minecraftBiomeMixin = !Production.inProduction;
		
		@Config.Comment("Enables \"locTitle\" and \"locAuthor\" NBT tags support.\nFor Written Books with localized author and title.")
		@Config.Name("(Minecraft) Localized Written Book Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocWrittenBookMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocWrittenBookMixin = !Production.inProduction;
		
		@Config.Comment("Enables \"locPages\" NBT List tag support.\nFor Command-Generated Writable Books with localized pages.")
		@Config.Name("(Minecraft) Localized Writable Book Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocWritableBookMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocWritableBookMixin = !Production.inProduction;
		
		@Config.Comment("Localizes a Container (TileEntityLockable) CustomName.")
		@Config.Name("(Minecraft) Localized Container Name Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocContainerNameMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocContainerNameMixin = !Production.inProduction;
		
		@Config.Comment("Enables the Client config: \n- (Neat) Translate Names In Health Bar")
		@Config.Name("(Neat) Health Bar Mixin")
		@Config.LangKey("config.localizator.mixins.neatLocHealthBarMixin")
		@Config.RequiresMcRestart
		public boolean neatLocHealthBarMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the MILD/COOL/WARM hardcoded texts when applying an Ozzy liner to a piece of armor.")
		@Config.Name("(ArmorUnder) Ozzy Liner text Mixin")
		@Config.LangKey("config.localizator.mixins.armorunderOzzyLinerMixin")
		@Config.RequiresMcRestart
		public boolean armorunderOzzyLinerMixin = !Production.inProduction;
		
		@Config.Comment("Enables support for Language keys in Set and Bonus names (config file).")
		@Config.Name("(SetBonus) Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.setbonusTooltipMixin")
		@Config.RequiresMcRestart
		public boolean setbonusTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Localizes all Forgotten Items' item descriptions.")
		@Config.Name("(ForgottenItems) Tooltips Mixin")
		@Config.LangKey("config.localizator.mixins.forgottenitemsTooltipMixin")
		@Config.RequiresMcRestart
		public boolean forgottenitemsTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the \"Current Adaptation:\" and \"reduction:\" hardcoded texts on parasite armor tooltip. \nAlso the damage source!\nFor damage sources, enable the Client config: \n- (SRParasites) Translate Resisted Damage Sources")
		@Config.Name("(SRParasites) Armor Tooltips Mixin")
		@Config.LangKey("config.localizator.mixins.srparasitesArmorTooltipMixin")
		@Config.RequiresMcRestart
		public boolean srparasitesArmorTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the \"hits remaining\" hardcoded text added to a weapon's tooltip when it has been imbued with a potion in a cauldron.")
		@Config.Name("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.bettersurvivalTooltipMixin")
		@Config.RequiresMcRestart
		public boolean bettersurvivalTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Modifies the lang keys of Rough Tweaks items, so they don't collide with FirstAid. \nAlso, Enables the Client config: \n- (RoughTweaks) Show Heal Amount")
		@Config.Name("(RoughTweaks) Localized Names and Better Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.roughtweaksNamesAndTooltipMixin")
		@Config.RequiresMcRestart
		public boolean roughtweaksNamesAndTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the \"Fertile Seasons:\", and seasons hardcoded texts at crop tooltip. \nAlso adds support to the following Rustic seeds: \n- Tomato Seeds \n- Chili seeds.")
		@Config.Name("(SereneSeasons) Localized Crop Fertility Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.sereneseasonsTooltipMixin")
		@Config.RequiresMcRestart
		public boolean sereneseasonsTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Localizes special Cakes hardcoded Names.")
		@Config.Name("(Charm) Localized Cake Name Mixin")
		@Config.LangKey("config.localizator.mixins.charmCakeNameMixin")
		@Config.RequiresMcRestart
		public boolean charmCakeNameMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the Composter JEI Recipe Integration GUI.")
		@Config.Name("(Charm) Localized Composter JEI Mixin")
		@Config.LangKey("config.localizator.mixins.charmComposterRecipeMixin")
		@Config.RequiresMcRestart
		public boolean charmComposterRecipeMixin = !Production.inProduction;
		
		@Config.Comment("Localizes all Charm Containers Custom Name on GUI.")
		@Config.Name("(Charm) Localized Container Name Mixin")
		@Config.LangKey("config.localizator.mixins.charmContainerNameMixin")
		@Config.RequiresMcRestart
		public boolean charmContainerNameMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the names of Charm Crates generated in the world.")
		@Config.Name("(Charm) Localized World Crates Name Mixin")
		@Config.LangKey("config.localizator.mixins.charmWorldCratesNameMixin")
		@Config.RequiresMcRestart
		public boolean charmWorldCratesNameMixin = !Production.inProduction;
		
		@Config.Comment("Enables support for Language Keys in Entity's CustomName tag.")
		@Config.Name("(WAILA) Entity Name Mixin")
		@Config.LangKey("config.localizator.mixins.wailaEntityNameMixin")
		@Config.RequiresMcRestart
		public boolean wailaEntityNameMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the Grave Scroll description hardcoded text.")
		@Config.Name("(CorpseComplex) Grave Scroll Description Mixin")
		@Config.LangKey("config.localizator.mixins.corpsecomplexScrollMixin")
		@Config.RequiresMcRestart
		public boolean corpsecomplexScrollMixin = !Production.inProduction;
		
		@Config.Comment("Localizes hardcoded texts in Forge's Mod List screen.\nAlso highlights the important information at the Mod details screen.")
		@Config.Name("(FML) Localized Mod List GUI Mixin")
		@Config.LangKey("config.localizator.mixins.forgeModListMixin")
		@Config.RequiresMcRestart
		public boolean forgeModListMixin = !Production.inProduction;
		
		@Config.Comment("The messages received from the server will be translated on client side to match their locale.")
		@Config.Name("(Lycanites) Client-side Translated Messages Mixin")
		@Config.LangKey("config.localizator.mixins.lycanitesMessagesMixin")
		@Config.RequiresMcRestart
		public boolean lycanitesMessagesMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the message sent when the Battle Tower Golem is defeated.")
		@Config.Name("(BattleTowers) Golem Defeated Message Mixin")
		@Config.LangKey("config.localizator.mixins.battletowersMessagesMixin")
		@Config.RequiresMcRestart
		public boolean battletowersMessagesMixin = !Production.inProduction;
		
		@Config.Comment("Localizes all horse-related messages sent from the server.")
		@Config.Name("(Callable Horses) Horse Messages Mixin")
		@Config.LangKey("config.localizator.mixins.callableHorsesMessagesMixin")
		@Config.RequiresMcRestart
		public boolean callableHorsesMessagesMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the \"Power: \" status message when throwing an item.")
		@Config.Name("(ItemPhysic) Power: Status Message Mixin")
		@Config.LangKey("config.localizator.mixins.itemphysicMessagesMixin")
		@Config.RequiresMcRestart
		public boolean itemphysicMessagesMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Artifacts custom names.")
		@Config.Name("(RecComplex) Artifact Names Mixin")
		@Config.LangKey("config.localizator.mixins.reccomplexLocArtifacts")
		@Config.RequiresMcRestart
		public boolean reccomplexLocArtifacts = !Production.inProduction;
		
		@Config.Comment("Enables \"locPages\" NBT List tag support.\nFor Command-Generated Writable Books with localized pages.")
		@Config.Name("(VariedCommodities) Localized Book Mixin")
		@Config.LangKey("config.localizator.mixins.variedCommoditiesLocBookMixin")
		@Config.RequiresMcRestart
		public boolean variedcommoditiesLocBookMixin = !Production.inProduction;
		
		@Config.Comment("Localizes caught fishes information (Name, Weight, Scale, Alive)\nAlso localizes:\n- Fish Bucket\n- Fish Slice\n- Bobber\n- Command and Bait Bucket messages messages.")
		@Config.Name("(FishingMadeBetter) Localized Fishes Mixin")
		@Config.LangKey("config.localizator.mixins.fishingmadebetterLocFishesEtcMixin")
		@Config.RequiresMcRestart
		public boolean fishingmadebetterLocFishesEtcMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Fancy Block Particles Keybinds texts on Settings screen.")
		@Config.Name("(FBP) Localized Keybinds Mixin")
		@Config.LangKey("config.localizator.mixins.fbpKeyBindsMixin")
		@Config.RequiresMcRestart
		public boolean fbpKeybindsMixin = !Production.inProduction;
		
		@Config.Comment("Localizes LevelUp Reloaded Keybinds texts on Settings screen.")
		@Config.Name("(LevelUp2) Localized Keybinds Mixin")
		@Config.LangKey("config.localizator.mixins.levelup2KeybindsMixin")
		@Config.RequiresMcRestart
		public boolean levelup2KeybindsMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Mo'Bends Keybinds texts on Settings screen.")
		@Config.Name("(Mo'Bends) Localized Keybinds Mixin")
		@Config.LangKey("config.localizator.mixins.mobendsKeybindsMixin")
		@Config.RequiresMcRestart
		public boolean mobendsKeybindsMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Scaling Health Keybinds texts on Settings screen.")
		@Config.Name("(ScalingHealth) Localized Keybinds Mixin")
		@Config.LangKey("config.localizator.mixins.scalingHealthKeybindsMixin")
		@Config.RequiresMcRestart
		public boolean scalingHealthKeybindsMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Swords tooltip \"Material Bonus:\" text.")
		@Config.Name("(SpartanWeaponry) Localized Material Bonus Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.spartanWeaponrySwordTooltipMixin")
		@Config.RequiresMcRestart
		public boolean spartanWeaponrySwordTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Ender Queen's chat messages when hurt.")
		@Config.Name("(T&B) Localized Ender Queen messages Mixin")
		@Config.LangKey("config.localizator.mixins.xatEnderQueenMessagesMixin")
		@Config.RequiresMcRestart
		public boolean xatEnderQueenMessagesMixin = !Production.inProduction;
		
		@Config.Comment("Localizes the armor weight tooltip text when transformed into Faelis race.")
		@Config.Name("(T&B) Localized Armor Weight Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.xatArmorWeightTooltipMixin")
		@Config.RequiresMcRestart
		public boolean xatArmorWeightTooltipMixin = !Production.inProduction;
		
		@Config.Comment("Localizes Scaling Health messages related to the date.")
		@Config.Name("(ScalingHealth) Localized Messages Mixin")
		@Config.LangKey("config.localizator.mixins.scalingHealthMessagesMixin")
		@Config.RequiresMcRestart
		public boolean scalingHealthMessagesMixin = !Production.inProduction;
		
		@Config.Comment("Localizes SRParasites messages.")
		@Config.Name("(SRParasites) Localized Messages Mixin")
		@Config.LangKey("config.localizator.mixins.srparasitesMessagesMixin")
		@Config.RequiresMcRestart
		public boolean srparasitesMessagesMixin = !Production.inProduction;

		@Config.Comment("Localizes Raw ores names to behave correctly on languages other than English.\nIt took the ore ingot name and stripped \"Ingot\" from it, which works only for English.")
		@Config.Name("(DynaOres) Correctly Localized Ore Names")
		@Config.LangKey("config.localizator.mixins.dynaoresNameMixin")
		@Config.RequiresMcRestart
		public boolean dynaoresNameMixin = !Production.inProduction;

		@Config.Comment("Messages will be taken from lang keys and translated client-side instead of being hardcoded from the config file.")
		@Config.Name("(Morpheus) Localized Messages Mixin")
		@Config.LangKey("config.localizator.mixins.morpheusMessagesMixin")
		@Config.RequiresMcRestart
		public boolean morpheusMessages = !Production.inProduction;

		@Config.Comment("Localizes messages related to Wormhole teleport items.")
		@Config.Name("(BountifulBaubles) Localized Wormhole Messages Mixin")
		@Config.LangKey("config.localizator.mixins.bountifulbaublesWormholeMessagesMixin")
		@Config.RequiresMcRestart
		public boolean bountifulbaublesWormholeMessages = !Production.inProduction;
	}
	
	public static class MiscelaneousMixinsConfig {
		@Config.Comment("Applies some QoL tooltip fixes. \n* Fixes Flywheel Ring's energy tooltip string not rendering its color properly. \n* Enables the Client config: \n- (BountifulBaubles) Remove Modifier from Bauble Name")
		@Config.Name("(BountifulBaubles) Tooltip fixes Mixin")
		@Config.LangKey("config.localizator.mixins.bountifulbaublesTooltipFixesMixin")
		@Config.RequiresMcRestart
		public boolean bountifulbaublesTooltipFixesMixin = !Production.inProduction;
		
		@Config.Comment("If you hate those weird \"Â\" symbols in the Staff tooltip as much as me, enable this Mixin.")
		@Config.Name("(DyamicTrees) Staff Mixin")
		@Config.LangKey("config.localizator.mixins.dynamictreesStaffMixin")
		@Config.RequiresMcRestart
		public boolean dynamictreesStaffMixin = !Production.inProduction;
		
		@Config.Comment("If an item is not THAT errored, retrieve its name and display it. \nWorks with ArmorUnderwear's Ozzy Liners and other items!")
		@Config.Name("(ItemPhysic) ERRORED patch Mixin")
		@Config.LangKey("config.localizator.mixins.itemphysicErroredMixin")
		@Config.RequiresMcRestart
		public boolean itemphysicErroredMixin = !Production.inProduction;
		
		@Config.Comment("Reverse the order of a dropped item description, so it shows the same as on its tooltip.")
		@Config.Name("(ItemPhysic) Reverse Description Mixin")
		@Config.LangKey("config.localizator.mixins.itemphysicReverseDescriptionMixin")
		@Config.RequiresMcRestart
		public boolean itemphysicReverseDescriptionMixin = !Production.inProduction;
		
		@Config.Comment("Point Patrons and Version calls to valid repo URLs, to prevent java.io.FileNotFoundException.")
		@Config.Name("(iChunUtil) Fix Patrons and Version URL Mixin")
		@Config.LangKey("config.localizator.mixins.ichunutilFixURLs")
		@Config.RequiresMcRestart
		public boolean ichunutilFixURLs = !Production.inProduction;
		
		@Config.Comment("Adds cobweb immunity to the Ankh Charm. \nIf it makes sense to you as well c:")
		@Config.Name("(BountifulBaubles) Ankh Charm Web Immune Mixin")
		@Config.LangKey("config.localizator.mixins.bountifulbaublesAnkhCharmWebImmunity")
		@Config.RequiresMcRestart
		public boolean bountifulbaublesAnkhCharmWebImmunity = !Production.inProduction;
		
		@Config.Comment("Adds cobweb immunity to the Ankh Shield. \nIf it makes sense to you as well c:")
		@Config.Name("(BountifulBaubles) Ankh Shield Web Immune Mixin")
		@Config.LangKey("config.localizator.mixins.bountifulbaublesAnkhShieldmWebImmunity")
		@Config.RequiresMcRestart
		public boolean bountifulbaublesAnkhShieldmWebImmunity = !Production.inProduction;
		
		@Config.Comment("Enables the Client config:\n- (FishingMadeBetter) Instructions on Minigame")
		@Config.Name("(FishingMadeBetter) Instructions on Minigame Mixin")
		@Config.LangKey("config.localizator.mixins.fishingmadebetterMinigameHelpTextMixin")
		@Config.RequiresMcRestart
		public boolean fishingmadebetterMinigameHelpTextMixin = !Production.inProduction;
		
		@Config.Comment("Enables the user-defined Name list for the parasite Adventurers/Thralls.\nRequires \"Mod Easter Eggs\" option in SRParasites cfg to be enabled.")
		@Config.Name("(SRParasites) Custom Adventurer Names Mixin")
		@Config.LangKey("config.localizator.mixins.srparasitesCustomAdventurerNames")
		@Config.RequiresMcRestart
		public boolean srparasitesCustomAdventurerNames = !Production.inProduction;

		@Config.Comment("Makes some Roguelike Dungeons Novelties generate with the \"Unbreakable\" NBT tag.\nIndividual Novelty items can be configured in the Server config section.")
		@Config.Name("(RLD) Unbreakable Novelties Mixin")
		@Config.LangKey("config.localizator.mixins.rldEternalNovelties")
		@Config.RequiresMcRestart
		public boolean rldEternalNovelties = !Production.inProduction;

		@Config.Comment("Originally, some zombies and skeletons rarely spawned with 1 of these 3 Novelty items:\n- Ashlea's Otameal Cookie\n- Rleahian battle sub\n- Valandrah's Kiss\nThis Mixin will enable more mob types to spawn holding or wearing other novelties.\nAlso, room-specific novelties will have a chance to generate in containers, making all novelties obtainable!")
		@Config.Name("(RLD) Extended Novelty Pool Mixin")
		@Config.LangKey("config.localizator.mixins.rldExtendedNovelties")
		@Config.RequiresMcRestart
		public boolean rldExtendedNovelties = !Production.inProduction;

		@Config.Comment("Enables the Server config:\n- (RLD) Johnny spawning chance")
		@Config.Name("(RLD) Spawn Johnny Mixin")
		@Config.LangKey("config.localizator.mixins.rldSpawnJohnny")
		@Config.RequiresMcRestart
		public boolean rldSpawnJohnny = !Production.inProduction;

		@Config.Comment("When using the /kill command, item entities will show their correct name instead of item.item.the_item.")
		@Config.Name("(Minecraft) Item Names on Kill Command Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocItemEntityNameMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocItemEntityNameMixin = !Production.inProduction;

		@Config.Comment("Prevents players from placing Novelty Items in the world by mistake.\nItems with Name and Lore (or their localized versions) NBT tags are considered as Novelties.\nThis applies to:\n- Paintings\n- Signs\n- Skulls\n- Any other Block Item that has no custom action when placing it.")
		@Config.Name("(Minecraft) Prevent Novelties Placement Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftPreventNoveltyPlacementMixin")
		@Config.RequiresMcRestart
		public boolean minecraftPreventNoveltyPlacementMixin = !Production.inProduction;
	}

	public static class ClientConfig {
		@Config.Comment("If an entity has a custom name (CustomName tag with a language key in it), translate it. \nRequired Mixin:\n- (Minecraft) Mob Custom Names Mixin")
		@Config.Name("(Minecraft) Translate Mob Custom Names")
		@Config.LangKey("config.localizator.client.minecraftMobLocCustomNames")
		public boolean minecraftMobLocCustomNames = true;
		
		@Config.Comment("If a Boss has a custom Name (CustomName tag with a language key in it), translate it on client side. \nThe corresponding language key must exist! \nRequired Mixin:\n- (Minecraft) Boss Custom Names Mixin")
		@Config.Name("(Minecraft) Translate Boss Custom Names")
		@Config.LangKey("config.localizator.client.minecraftBossLocCustomNames")
		public boolean minecraftBossLocCustomNames = true;
		
		@Config.Comment("If an item has both \"Lore\" and \"LocLore\" NBT tags, LocLore contents will override Lore contents. \nIf it has only one of the lore tags, it will be displayed normally. \nRequired Mixin: \n- (Minecraft) Localized Lore Mixin")
		@Config.Name("(Minecraft) Hide Lore if LocLore exists")
		@Config.LangKey("config.localizator.client.minecraftHideLore")
		public boolean minecraftHideLore = true;
		
		@Config.Comment("If an entity has a custom name (CustomName tag with a language key), translate it. nRequired Mixin:\n- (Neat) Health Bar Mixin")
		@Config.Name("(Neat) Translate Names In Health Bar")
		@Config.LangKey("config.localizator.client.neatLocHealthBar")
		public boolean neatLocHealthBar = true;
		
		@Config.Comment("In SRP armor tooltip, translate resisted damage sources names. \nEntity names are taken from Forge's registry (automatic). \nNon-entity names are taken from a lang file. \nRequired Mixin: \n- (SRParasites) Armor Tooltips Mixin \nDisable this option if it impacts performance.")
		@Config.Name("(SRParasites) Translate Resisted Damage Sources")
		@Config.LangKey("config.localizator.client.srparasitesResistances")
		public boolean srparasitesResistances = true;
		
		@Config.Comment("Prevents a Bauble's Modifier from displaying at Bauble Name\nRequired Mixin: \n- (BountifulBaubles) No Modifier at Name Mixin")
		@Config.Name("(BountifulBaubles) Remove Modifier from Bauble Name")
		@Config.LangKey("config.localizator.client.bountifulbaublesRemoveModifierFromName")
		public boolean bountifulbaublesRemoveModifierFromName = true;
		
		@Config.Comment("Removes the need of pressing Shift on RoughTweaks Items Tooltip to show the Heal Amount. \nRequired Mixin: \n- (RoughTweaks) Localized Names and Better Tooltip Mixin")
		@Config.Name("(RoughTweaks) Show Heal Amount")
		@Config.LangKey("config.localizator.client.roughtweaksTooltip")		
		public boolean roughtweaksTooltip = true;
		
		@Config.Comment("Adds a little help for players that don't know how to fish. \n \"Press [LEFT] / [RIGHT]\". \nRequired Mixin: \n- (FishingMadeBetter) Instructions on Minigame Mixin")
		@Config.Name("(FishingMadeBetter) Instructions on Minigame")
		@Config.LangKey("config.localizator.client.fishingmadebetterMinigameHelpText")
		public boolean fishingmadebetterMinigameHelpText = true;
		
		@Config.Comment("The Overworld Sea Level line shown over the Fish Requirements JEI Plugin")
		@Config.Name("(FMB/JEI) Y Meter. Overworld Sea Level")
		@Config.RangeInt(min = 0, max = 140)
		@Config.LangKey("config.localizator.client.fishingmadebetterYmeterSeaLevelText")
		@Config.RequiresMcRestart
		public int fishingmadebetterYmeterSeaLevel = 62;
		
		@Config.Comment("The Nether Lava Ocean Level line shown over the Fish Requirements JEI Plugin")
		@Config.Name("(FMB/JEI) Y Meter. Nether Lava Ocean Level")
		@Config.RangeInt(min = 0, max = 140)
		@Config.LangKey("config.localizator.client.fishingmadebetterYmeterLavaLevelText")
		@Config.RequiresMcRestart
		public int fishingmadebetterYmeterLavaLevel = 31;
		
		@Config.Comment("The End Void Level line shown over the Fish Requirements JEI Plugin.\nIt's set to 5 just for visuals.")
		@Config.Name("(FMB/JEI) Y Meter. The End Void Level")
		@Config.RangeInt(min = 0, max = 140)
		@Config.LangKey("config.localizator.client.fishingmadebetterYmeterVoidLevelText")
		@Config.RequiresMcRestart
		public int fishingmadebetterYmeterVoidLevel = 5;
		
		@Config.Comment("Represent fish light levels with the Photometer icon (if Inspirations mod is present)\nIf Inspirations is not present or this option is false, the Minecraft light icon is used.")
		@Config.Name("(FMB/JEI) Use Photometer icon for light levels")
		@Config.LangKey("config.localizator.client.fishingmadebetterPhotometer")
		public boolean fishingmadebetterPhotometer = true;

		@Config.Comment("Minecraft shows an item's Lore with dark_purple color.\nLocalizator shows LocLore with white color.\nYou can choose the default text color for LocLore texts here.\nIf your lang key doesn't have any formatting codes, its text will be shown with this color setting.\n\nValid colors:\n black, dark_blue, dark_green, dark_aqua, dark_red, dark_purple, gold, gray, dark_gray, blue, green, aqua, red, light_purple, yellow, white")
		@Config.Name("(Minecraft) LocLore Default Text Color")
		@Config.LangKey("config.localizator.client.minecraftDefaultLocLoreColor")
		public String minecraftDefaultLocLoreColor = "white";
		
		@Config.Comment("Minecraft shows an item's Lore with italic format.\nLocalizator shows LocLore with no formatting code.\nYou can choose the default text formatting for LocLore texts here.\nIf your lang key doesn't have any formatting codes, its text will be shown with this formatting setting.\n\nValid formats:\n obfuscated, bold, strikethrough, underline, italic, none")
		@Config.Name("(Minecraft) LocLore Default Text Format")
		@Config.LangKey("config.localizator.client.minecraftDefaultLocLoreFormat")
		public String minecraftDefaultLocLoreFormat = "none";
	}
	
	public static class ServerConfig {
		@Config.Comment("If an item has both LocName and Name display NBT tags, show LocName instead of Name. \nThis is useful for Mixins that localize mods like Recurrent Complex, \nwhich assign custom hardcoded names to items on generation. \nRequired Mixin: \n- (Minecraft) Better Localized Name Mixin")
		@Config.Name("(Minecraft) Show LocName instead of Name")
		@Config.LangKey("config.localizator.server.minecraftLocNameOverName")
		public boolean minecraftLocNameOverName = true;
		
		@Config.Comment("This list is added to the original SRParasites Name lists. \nRequired Mixin: \n- (SRParasites) Custom Adventurer Names Mixin")
		@Config.Name("(SRParasites) Custom Parasite Name list")
		@Config.LangKey("config.localizator.server.srparasitesCustomNamesList")
		public String[] srparasitesCustomNamesList = {"Collin"};

		@Config.Comment("Enable the Lava Fish Bucket recipe?\nThis item will be registered only if Fishing Made Better is loaded")
		@Config.Name("(FishingMadeBetter) Lava Fish Bucket")
		@Config.LangKey("config.localizator.server.fishingmadebetterLavaFishBucket")
		@Config.RequiresMcRestart
		public boolean fishingmadebetterLavaFishBucket = true;

		@Config.Comment("Enable the Void Bucket and Void Fish Bucket recipes?\nThese items will be registered only if Fishing Made Better is loaded")
		@Config.Name("(FishingMadeBetter) Void Fish Bucket")
		@Config.LangKey("config.localizator.server.fishingmadebetterVoidFishBucket")
		@Config.RequiresMcRestart
		public boolean fishingmadebetterVoidFishBucket = true;

		@Config.Comment("Should the \"Nebris Crown\" (Golden Helmet) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Nebris Crown")
		@Config.LangKey("config.localizator.server.rldEternalNebrisCrown")
		public boolean rldEternalNebrisCrown = true;
		
		@Config.Comment("Should the \"Null Pointer\" (Diamond Sword) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Null Pointer")
		@Config.LangKey("config.localizator.server.rldEternalNullPointer")
		public boolean rldEternalNullPointer = true;

		@Config.Comment("Should the \"Man Pants\" (Leather Leggings) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Man Pants")
		@Config.LangKey("config.localizator.server.rldEternalManPants")
		public boolean rldEternalManPants = true;

		@Config.Comment("Should the \"Farland Travelers\" (Leather Boots) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Farland Travelers")
		@Config.LangKey("config.localizator.server.rldEternalFarlandTravelers")
		public boolean rldEternalFarlandTravelers = true;

		@Config.Comment("Should the \"Lascerator\" (Shears) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Lascerator")
		@Config.LangKey("config.localizator.server.rldEternalLascerator")
		public boolean rldEternalLascerator = true;

		@Config.Comment("Should the \"Pink Sweater\" (Leather Chestplate) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Pink Sweater")
		@Config.LangKey("config.localizator.server.rldEternalPinkSweater")
		public boolean rldEternalPinkSweater = true;

		@Config.Comment("Should the \"Dig Job\" (Diamond Shovel) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Dig Job")
		@Config.LangKey("config.localizator.server.rldEternalDigJob")
		public boolean rldEternalDigJob = true;

		@Config.Comment("Should the \"Your Mum\" (Wooden Pickaxe) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Your Mum")
		@Config.LangKey("config.localizator.server.rldEternalYourMum")
		public boolean rldEternalYourMum = true;
		
		@Config.Comment("Should the \"Enikos String Theory\" (Bow) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Enikos String Theory")
		@Config.LangKey("config.localizator.server.rldEternalEnikoStringTheory")
		public boolean rldEternalEnikoStringTheory = true;

		@Config.Comment("Should the \"Enikos Earring\" (Diamond Sword) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Enikos Earring")
		@Config.LangKey("config.localizator.server.rldEternalEnikoEarring")
		public boolean rldEternalEnikoEarring = true;

		@Config.Comment("Should the \"Bajs Last Resort\" (Golden Hoe) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Bajs Last Resort")
		@Config.LangKey("config.localizator.server.rldEternalBajLastResort")
		public boolean rldEternalBajLastResort = true;

		@Config.Comment("Should the \"Rod of Command\" (Fishing Rod) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Rod of Command")
		@Config.LangKey("config.localizator.server.rldEternalRodOfCommand")
		public boolean rldEternalRodOfCommand = true;

		@Config.Comment("Should the \"Valandrahs Kiss\" (Iron Sword) Novelty generate with the \"Unbreakable\" NBT tag?\nRequired Mixin: \n- (RLD) Unbreakable Novelties Mixin")
		@Config.Name("(RLD) Eternal Valandrahs Kiss")
		@Config.LangKey("config.localizator.server.rldEternalValandrahKiss")
		public boolean rldEternalValandrahKiss = true;

		@Config.Comment("Do you have any custom dungeon with Husk Spawners?\nThis option will balance this mobs Novelty generation chance acordingly.\nRequired Mixin: \n- (RLD) Extended Novelty Pool Mixin")
		@Config.Name("(RLD) Custom Spawner - Husk")
		@Config.LangKey("config.localizator.server.rldCustomSpawnerHusk")
		public boolean rldCustomSpawnerHusk = false;
		
		@Config.Comment("Do you have any custom dungeon with Zombie Villager Spawners?\nThis option will balance this mobs Novelty generation chance acordingly.\nRequired Mixin: \n- (RLD) Extended Novelty Pool Mixin")
		@Config.Name("(RLD) Custom Spawner - Zombie Villager")
		@Config.LangKey("config.localizator.server.rldCustomSpawnerZombieVillager")
		public boolean rldCustomSpawnerZombieVillager = false;

		@Config.Comment("If you have any custom dungeon with Vindicator Spawners, Johnny will have a chance to spawn from them.\n0.00 = 0% Spawn chance\n1.0 = 100% Spawn chance\nRequired Mixin: \n- (RLD) Spawn Johnny Mixin")
		@Config.Name("(RLD) Johnny spawning chance")
		@Config.RangeDouble(min = 0.00, max = 1.0)
		@Config.LangKey("config.localizator.server.rldJohnnySpawnChance")
		public double rldJohnnySpawnChance = 0.00;

		@Config.Comment("Prevent players from placing Novelty Paintings in the world by mistake, losing them.\nThe player will receive a message explaining why it can't be placed.\nRequired Mixin: \n- (Minecraft) Prevent Novelties Placement Mixin")
		@Config.Name("(Minecraft) Prevent Novelty Paintings Placement")
		@Config.LangKey("config.localizator.server.minecraftPreventNoveltyPaintingsPlacement")
		public boolean minecraftPreventNoveltyPaintingsPlacement = true;

		@Config.Comment("Prevent players from placing Novelty Signs in the world by mistake, losing them.\nThe player will receive a message explaining why it can't be placed.\nRequired Mixin: \n- (Minecraft) Prevent Novelties Placement Mixin")
		@Config.Name("(Minecraft) Prevent Novelty Signs Placement")
		@Config.LangKey("config.localizator.server.minecraftPreventNoveltySignsPlacement")
		public boolean minecraftPreventNoveltySignsPlacement = true;

		@Config.Comment("Prevent players from placing Novelty Skulls in the world by mistake, losing them.\nThe player will receive a message explaining why it can't be placed.\nRequired Mixin: \n- (Minecraft) Prevent Novelties Placement Mixin")
		@Config.Name("(Minecraft) Prevent Novelty Skulls Placement")
		@Config.LangKey("config.localizator.server.minecraftPreventNoveltySkullsPlacement")
		public boolean minecraftPreventNoveltySkullsPlacement = true;

		@Config.Comment("Prevent players from placing Novelty Blocks in the world by mistake, losing them.\nThe player will receive a message explaining why it can't be placed.\nRequired Mixin: \n- (Minecraft) Prevent Novelties Placement Mixin")
		@Config.Name("(Minecraft) Prevent Novelty Blocks Placement")
		@Config.LangKey("config.localizator.server.minecraftPreventNoveltyBlocksPlacement")
		public boolean minecraftPreventNoveltyBlocksPlacement = true;

		@Config.Comment("Block ID Whitelist to enable players to place the designated Novelty Blocks in the world.\nExample ID: minecraft:dirt\n\nKeep in mind, when a Block is placed in the world, its NBT tags are deleted, turning a Novelty into a normal Block! (Vanilla behaviour)\nRequired Server Config: \n- (Minecraft) Prevent Novelty Blocks Placement")
		@Config.Name("(Minecraft) Prevent Novelty Blocks Placement Whitelist")
		@Config.LangKey("config.localizator.server.minecraftPreventNoveltyBlocksPlacementWhitelist")
		public String[] minecraftPreventNoveltyBlocksPlacementWhitelist = {""};
	}
	
	// ====================== Hash stuff initializing ==================================================================================
	
	public static HashSet<Block> getNoveltyBlocksWhitelist() {
		if (ForgeConfigHandler.noveltyBlocksWhitelist == null) {
			ForgeConfigHandler.noveltyBlocksWhitelist = new HashSet<>();
			Block block;
			if (ForgeConfigHandler.serverConfig.minecraftPreventNoveltyBlocksPlacementWhitelist == null) {
				ForgeConfigHandler.serverConfig.minecraftPreventNoveltyBlocksPlacementWhitelist = new String[]{""};
			}
			for (String name : ForgeConfigHandler.serverConfig.minecraftPreventNoveltyBlocksPlacementWhitelist) {
				block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
				if (block == null || block == Blocks.AIR) {
					Localizator.LOGGER.warn("Localizator Novelty Blocks Whitelist. Invalid block: {}, ignoring...", name);
					continue;
				}
				ForgeConfigHandler.noveltyBlocksWhitelist.add(block);
			}
		}
		return ForgeConfigHandler.noveltyBlocksWhitelist;
	}
	
	// ====================== Config file handling =====================================================================================
	@Mod.EventBusSubscriber(modid = Localizator.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(Localizator.MODID)) {
				ForgeConfigHandler.noveltyBlocksWhitelist = null;
				
				ConfigManager.sync(Localizator.MODID, Config.Type.INSTANCE);
			}
		}
	}

	/**
     * Reads the config file before loading my mixins, because ConfigManager won't do it until later.
     * Code based on original MIT Licensed code:
     * <a href="https://github.com/fonnymunkey/RLMixins/blob/main/src/main/java/rlmixins/handlers/ForgeConfigHandler.java">ForgeConfigHandler.java</a>
     */
	//This is jank, but easier than setting up a whole custom GUI config
	private static File configFile;
	private static String configBooleanString = "";
	private static boolean isFirstBoot = false;

	public static boolean getBoolean(String name) {
		if(configFile == null) {
			configFile = new File("config", Localizator.MODID + ".cfg");
			if (configFile.exists() && configFile.isFile()) {				
				String configCurrentVersion = "";
				try	(Stream<String> streamOld = Files.lines(configFile.toPath())) {
					configCurrentVersion = streamOld.filter(s -> s.trim().contains("Enable/Disable Localization Mixins (Tweaks)")).collect(Collectors.joining());
				} catch (Exception e) {
                    Localizator.LOGGER.error("Failed to open LocEntityNameMixin config: {}", String.valueOf(e));
				}
				Production.migratedCfg = !(configCurrentVersion.contains("Enable/Disable Localization Mixins (Tweaks)"));
				if (Production.migratedCfg) {
					String oldCfgName = Localizator.MODID + "_old.cfg";
                    Localizator.LOGGER.warn("[Localizator] Found an old-version config file. Renaming it to \"{}\" and creating a new one...", oldCfgName);
					// Migrate from old cfg version to current one
					try {
						Files.deleteIfExists(new File("config", oldCfgName).toPath());
					} catch (Exception e) {
                        Localizator.LOGGER.error("Failed to delete old config file: {}. {}", oldCfgName, e);
					}
					if (!(configFile.renameTo(new File("config", oldCfgName)))) {
                        Localizator.LOGGER.error("Failed to rename config file to: {}", oldCfgName);
					}
					Localizator.LOGGER.info("[Localizator] Please re-enable all your desired Mixins and configurations. Sorry for the inconvenience!");
					configFile = null;
					configBooleanString = "";
					return false;
				}
				
				
				try (Stream<String> stream = Files.lines(configFile.toPath())) {
					configBooleanString = stream.filter(s -> s.trim().startsWith("B:")).collect(Collectors.joining());
				} catch (Exception ex) {
                    Localizator.LOGGER.error("Failed to parse LocEntityNameMixin config: {}", String.valueOf(ex));
				}
			} else {
				isFirstBoot = !Production.inProduction; // (For DEBUG purposes) If the config file doesn't exist yet, assume all mixins are enabled.
			}
		}

		//If entry is not in the config file, don't enable injection on first run
		return isFirstBoot || configBooleanString.contains("B:\"" + name + "\"=true");		
	}
}