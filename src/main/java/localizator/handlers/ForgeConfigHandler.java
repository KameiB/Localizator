package localizator.handlers;

import localizator.Localizator;
import localizator.data.Production;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = Localizator.MODID)
public class ForgeConfigHandler {
	@Config.Comment("Enable/Disable Tweaks")
	@Config.Name("Mixins")
	@Config.LangKey("config.localizator.mixins")
	public static final MixinConfig mixinConfig = new MixinConfig();

	@Config.Comment("Client-Side Options")
	@Config.Name("Client")
	@Config.LangKey("config.localizator.client")
	public static final ClientConfig clientConfig = new ClientConfig();

	public static class MixinConfig {
		@Config.Comment("Enables Client - \"(Minecraft) Translate Mob Custom Names\" config option.")
		@Config.Name("(Minecraft) Mob Custom Names Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftMobLocCustomNamesMixin")
		@Config.RequiresMcRestart
		public boolean minecraftMobLocCustomNamesMixin = !Production.inProduction;
		@Config.Comment("Enables Client - \"(Minecraft) Translate Boss Custom Names\" config option.")
		@Config.Name("(Minecraft) Boss Custom Names Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftBossLocCustomNamesMixin")
		@Config.RequiresMcRestart
		public boolean minecraftBossLocCustomNamesMixin = !Production.inProduction;
		@Config.Comment("Enables Localized Lore \"LocLore\" NBT tag support for items.")
		@Config.Name("(Minecraft) Localized Lore Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocLoreMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocLoreMixin = !Production.inProduction;
		@Config.Comment("Enables Localized Title \"locTitle\" and Author \"locAuthor\" NBT tags support for Written Books.")
		@Config.Name("(Minecraft) Localized Written Book Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocWrittenBookMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocWrittenBookMixin = !Production.inProduction;
		@Config.Comment("Enables Client - \"(Neat) Translate Names In Health Bar\" config option.")
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
		@Config.Comment("Enables support for Language keys in all Forgotten Items' items descriptions.")
		@Config.Name("(ForgottenItems) Tooltips Mixin")
		@Config.LangKey("config.localizator.mixins.forgottenitemsTooltipMixin")
		@Config.RequiresMcRestart
		public boolean forgottenitemsTooltipMixin = !Production.inProduction;
		@Config.Comment("Localizes the \"Current Adaptation:\" and \"reduction:\" hardcoded texts at armor tooltip.")
		@Config.Name("(SRParasites) Armor Tooltips Mixin")
		@Config.LangKey("config.localizator.mixins.srparasitesArmorTooltipMixin")
		@Config.RequiresMcRestart
		public boolean srparasitesArmorTooltipMixin = !Production.inProduction;
		@Config.Comment("Localizes the \"hits remaining\" hardcoded text \nadded to a weapon's tooltip when it has been imbued with a potion in a cauldron.")
		@Config.Name("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.bettersurvivalTooltipMixin")
		@Config.RequiresMcRestart
		public boolean bettersurvivalTooltipMixin = !Production.inProduction;
		@Config.Comment("Enables Client - \"(BountifulBaubles) Remove Modifier from Name\" config option.")
		@Config.Name("(BountifulBaubles) No Modifier at Name Mixin")
		@Config.LangKey("config.localizator.mixins.bountifulbaublesNoModifierAtNameMixin")
		@Config.RequiresMcRestart
		public boolean bountifulbaublesNoModifierAtNameMixin = !Production.inProduction;
		@Config.Comment("Modifies the lang keys of Rough Tweaks items, so they don't collide with FirstAid. \nAlso, Enables the Client config: \n- \"(RoughTweaks) Show Heal Amount\".")
		@Config.Name("(RoughTweaks) Localized Names and Better Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.roughtweaksNamesAndTooltipMixin")
		@Config.RequiresMcRestart
		public boolean roughtweaksNamesAndTooltipMixin = !Production.inProduction;
		@Config.Comment("Localizes the \"Fertile Seasons:\", and seasons hardcoded texts at crop tooltip. \nAlso adds support to Rustic's Tomato Seeds and Chili seeds.")
		@Config.Name("(SereneSeasons) Localized Crop Fertility Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.sereneseasonsTooltipMixin")
		@Config.RequiresMcRestart
		public boolean sereneseasonsTooltipMixin = !Production.inProduction;
		@Config.Comment("Localizes Special Cakes Hardcoded Names.")
		@Config.Name("(Charm) Localized Cake Name Mixin")
		@Config.LangKey("config.localizator.mixins.charmCakeNameMixin")
		@Config.RequiresMcRestart
		public boolean charmCakeNameMixin = !Production.inProduction;
		@Config.Comment("Localizes the Composter JEI Recipe Integration GUI.")
		@Config.Name("(Charm) Localized Composter JEI Mixin")
		@Config.LangKey("config.localizator.mixins.charmComposterRecipeMixin")
		@Config.RequiresMcRestart
		public boolean charmComposterRecipeMixin = !Production.inProduction;
		@Config.Comment("If you hate those weird \"Â\" symbols in the Staff tooltip as much as me, enable this Mixin.")
		@Config.Name("(DyamicTrees) Staff Mixin")
		@Config.LangKey("config.localizator.mixins.dynamictreesStaffMixin")
		@Config.RequiresMcRestart
		public boolean dynamictreesStaffMixin = !Production.inProduction;
		@Config.Comment("Enables support for Language Keys in Entity's CustomName tag.")
		@Config.Name("(WAILA) Entity Name Mixin")
		@Config.LangKey("config.localizator.mixins.wailaEntityNameMixin")
		@Config.RequiresMcRestart
		public boolean wailaEntityNameMixin = !Production.inProduction;
		@Config.Comment("Localizes the Grave Scroll description.")
		@Config.Name("(CorpseComplex) Grave Scroll Description Mixin")
		@Config.LangKey("config.localizator.mixins.corpsecomplexScrollMixin")
		@Config.RequiresMcRestart
		public boolean corpsecomplexScrollMixin = !Production.inProduction;		
		@Config.Comment("Localizes few texts in the Mod List screen.")
		@Config.Name("(FML) Localized Mod List GUI Mixin")
		@Config.LangKey("config.localizator.mixins.forgeModListMixin")
		@Config.RequiresMcRestart
		public boolean forgeModListMixin = !Production.inProduction;
		@Config.Comment("The messages received from the server will be translated on client side to match their locale.")
		@Config.Name("(Lycanites) Client-side Translated Messages Mixin")
		@Config.LangKey("config.localizator.mixins.lycanitesMessagesMixin")
		@Config.RequiresMcRestart
		public boolean lycanitesMessagesMixin = !Production.inProduction;
		@Config.Comment("Localize message sent from the server when the Battle Tower Golem is defeated.")
		@Config.Name("(BattleTowers) Golem Defeated Message Mixin")
		@Config.LangKey("config.localizator.mixins.battletowersMessagesMixin")
		@Config.RequiresMcRestart
		public boolean battletowersMessagesMixin = !Production.inProduction;
		@Config.Comment("Localize all messages sent from the server.")
		@Config.Name("(Callable Horses) Horse Messages Mixin")
		@Config.LangKey("config.localizator.mixins.callableHorsesMessagesMixin")
		@Config.RequiresMcRestart
		public boolean callableHorsesMessagesMixin = !Production.inProduction;
	}

	public static class ClientConfig {
		@Config.Comment("Applies Mixins - \"(Minecraft) Mob Custom Names Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Minecraft) Translate Mob Custom Names")
		@Config.LangKey("config.localizator.client.minecraftMobLocCustomNames")
		public boolean minecraftMobLocCustomNames = !Production.inProduction;
		@Config.Comment("Applies Mixins - \"(Minecraft) Boss Custom Names Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Minecraft) Translate Boss Custom Names")
		@Config.LangKey("config.localizator.client.minecraftBossLocCustomNames")
		public boolean minecraftBossLocCustomNames = !Production.inProduction;
		@Config.Comment("If an item has both \"Lore\" and \"LocLore\" NBT tags, LocLore contents will override Lore contents. \nIf it has only one of the lore tags, it will be displayed normally. \nRequired Mixin: \n- \"(Minecraft) Localized Lore Mixin\".")
		@Config.Name("(Minecraft) Hide Lore if LocLore exists")
		@Config.LangKey("config.localizator.client.minecraftHideLore")
		public boolean minecraftHideLore = !Production.inProduction;
		@Config.Comment("Applies Mixins - \"(Neat) Health Bar Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Neat) Translate Names In Health Bar")
		@Config.LangKey("config.localizator.client.neatLocHealthBar")
		public boolean neatLocHealthBar = !Production.inProduction;
		@Config.Comment("In SRP armor tooltip, translate resisted damage sources names. \nEntity names are taken from the registry. \nNon-entity names are taken from this mod's lang file. \n§cDisable this if it impacts performance.")
		@Config.Name("(SRParasites) Translate Resisted Damage Sources")
		@Config.LangKey("config.localizator.client.srparasitesResistances")
		public boolean srparasitesResistances = !Production.inProduction;
		@Config.Comment("Prevents a Bauble's Modifier from displaying at its Name")
		@Config.Name("(BountifulBaubles) Remove Modifier from Bauble Name")
		@Config.LangKey("config.localizator.client.bountifulbaublesRemoveModifierFromName")
		public boolean bountifulbaublesRemoveModifierFromName = !Production.inProduction;
		@Config.Comment("Removes the need of Shift on RoughTweaks Items Tooltip to show the Heal Amount. \n- \"(RoughTweaks) Localized Names and Better Tooltip Mixin\" needs to be enabled.")
		@Config.Name("(RoughTweaks) Show Heal Amount")
		@Config.LangKey("config.localizator.client.roughtweaksTooltip")		
		public boolean roughtweaksTooltip = !Production.inProduction;
		
	}

	@Mod.EventBusSubscriber(modid = Localizator.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(Localizator.MODID)) {
				ConfigManager.sync(Localizator.MODID, Config.Type.INSTANCE);
			}
		}
	}

	/**
	 * Reads the config file before loading my mixins, because ConfigManager won't do it until later.
	 * Code based on original MIT Licensed code:
	 * https://github.com/fonnymunkey/RLMixins/blob/main/src/main/java/rlmixins/handlers/ForgeConfigHandler.java
	 * TODO: Try to implement a Custom Config Manager.
	*/
	//This is jank, but easier than setting up a whole custom GUI config
	private static File configFile;
	private static String configBooleanString = "";
	private static boolean isFirstBoot = false;

	public static boolean getBoolean(String name) {
		if(configFile==null) {
			configFile = new File("config", Localizator.MODID + ".cfg");
			if (configFile.exists() && configFile.isFile()) {
				try (Stream<String> stream = Files.lines(configFile.toPath())) {
					configBooleanString = stream.filter(s -> s.trim().startsWith("B:")).collect(Collectors.joining());
				} catch (Exception ex) {
					Localizator.LOGGER.log(Level.ERROR, "Failed to parse LocEntityNameMixin config: " + ex);
				}
			} else {
				isFirstBoot = !Production.inProduction; // (For DEBUG purposes) If the config file doesn't exist yet, assume all mixins are enabled.
			}
		}

		//If entry is not in the config file, don't enable injection on first run
		return isFirstBoot || configBooleanString.contains("B:\"" + name + "\"=true");		
	}
}