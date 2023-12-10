package localizator.handlers;

import localizator.Localizator;
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
		public boolean minecraftMobLocCustomNamesMixin = true;
		@Config.Comment("Enables Client - \"(Minecraft) Translate Boss Custom Names\" config option.")
		@Config.Name("(Minecraft) Boss Custom Names Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftBossLocCustomNamesMixin")
		@Config.RequiresMcRestart
		public boolean minecraftBossLocCustomNamesMixin = true;
		@Config.Comment("Enables Localized Lore \"LocLore\" NBT tag support for items.")
		@Config.Name("(Minecraft) Localized Lore Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocLoreMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocLoreMixin = true;
		@Config.Comment("Enables Localized Title \"locTitle\" and Author \"locAuthor\" NBT tags support for Written Books.")
		@Config.Name("(Minecraft) Localized Written Book Mixin")
		@Config.LangKey("config.localizator.mixins.minecraftLocWrittenBookMixin")
		@Config.RequiresMcRestart
		public boolean minecraftLocWrittenBookMixin = true;
		@Config.Comment("Enables Client - \"(Neat) Translate Names In Health Bar\" config option.")
		@Config.Name("(Neat) Health Bar Mixin")
		@Config.LangKey("config.localizator.mixins.neatLocHealthBarMixin")
		@Config.RequiresMcRestart
		public boolean neatLocHealthBarMixin = true;
		@Config.Comment("Localizes the MILD/COOL/WARM hardcoded texts when applying an Ozzy liner to a piece of armor.")
		@Config.Name("(ArmorUnder) Ozzy Liner text Mixin")
		@Config.LangKey("config.localizator.mixins.armorunderOzzyLinerMixin")
		@Config.RequiresMcRestart
		public boolean armorunderOzzyLinerMixin = true;
		@Config.Comment("Enables support for Language keys in Set and Bonus names (config file).")
		@Config.Name("(SetBonus) Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.setbonusTooltipMixin")
		@Config.RequiresMcRestart
		public boolean setbonusTooltipMixin = true;
		@Config.Comment("Enables support for Language keys in all Forgotten Items' items descriptions.")
		@Config.Name("(ForgottenItems) Tooltips Mixin")
		@Config.LangKey("config.localizator.mixins.forgottenitemsTooltipMixin")
		@Config.RequiresMcRestart
		public boolean forgottenitemsTooltipMixin = true;
		@Config.Comment("Localizes the \"Current Adaptation:\" and \"reduction:\" hardcoded texts at armor tooltip.")
		@Config.Name("(SRParasites) Armor Tooltips Mixin")
		@Config.LangKey("config.localizator.mixins.srparasitesArmorTooltipMixin")
		@Config.RequiresMcRestart
		public boolean srparasitesArmorTooltipMixin = true;
		@Config.Comment("Localizes the \"hits remaining\" hardcoded text \nadded to a weapon's tooltip when it has been imbued with a potion in a cauldron.")
		@Config.Name("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.bettersurvivalTooltipMixin")
		@Config.RequiresMcRestart
		public boolean bettersurvivalTooltipMixin = true;
		
		//@Config.Comment("Localizes the \"Power:\" hardcoded text \nshown when the custom item throwing physic is enabled.")
		//@Config.Name("(ItemPhysic) Throw Power Mixin")
		//@Config.LangKey("config.localizator.mixins.itemphysicThrowPowerMixin")
		//@Config.RequiresMcRestart
		//public boolean itemphysicThrowPowerMixin = true;

		@Config.Comment("Enables Client - \"(BountifulBaubles) Remove Modifier from Name\" config option.")
		@Config.Name("(BountifulBaubles) No Modifier at Name Mixin")
		@Config.LangKey("config.localizator.mixins.bountifulbaublesNoModifierAtNameMixin")
		@Config.RequiresMcRestart
		public boolean bountifulbaublesNoModifierAtNameMixin = true;
		@Config.Comment("Modifies the lang keys of Rough Tweaks items, so they don't collide with FirstAid. \nAlso, Enables the Client config: \n- \"(RoughTweaks) Show Heal Amount\".")
		@Config.Name("(RoughTweaks) Localized Names and Better Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.roughtweaksNamesAndTooltipMixin")
		@Config.RequiresMcRestart
		public boolean roughtweaksNamesAndTooltipMixin = true;
		@Config.Comment("Localizes the \"Fertile Seasons:\", and seasons hardcoded texts at crop tooltip.")
		@Config.Name("(SereneSeasons) Localized Crop Fertility Tooltip Mixin")
		@Config.LangKey("config.localizator.mixins.sereneseasonsTooltipMixin")
		@Config.RequiresMcRestart
		public boolean sereneseasonsTooltipMixin = true;
		@Config.Comment("Localizes Special Cakes Hardcoded Names.")
		@Config.Name("(Charm) Localized Cake Name Mixin")
		@Config.LangKey("config.localizator.mixins.charmCakeNameMixin")
		@Config.RequiresMcRestart
		public boolean charmCakeNameMixin = true;
		@Config.Comment("Localizes the Composter JEI Recipe Integration GUI.")
		@Config.Name("(Charm) Localized Composter JEI Mixin")
		@Config.LangKey("config.localizator.mixins.charmComposterRecipeMixin")
		@Config.RequiresMcRestart
		public boolean charmComposterRecipeMixin = true;
		@Config.Comment("If you hate those weird \"Â\" symbols in the Staff tooltip as much as me, enable this Mixin.")
		@Config.Name("(DyamicTrees) Staff Mixin")
		@Config.LangKey("config.localizator.mixins.dynamictreesStaffMixin")
		@Config.RequiresMcRestart
		public boolean dynamictreesStaffMixin = true;
		@Config.Comment("Enables support for Language Keys in Entity's CustomName tag.")
		@Config.Name("(WAILA) Entity Name Mixin")
		@Config.LangKey("config.localizator.mixins.wailaEntityNameMixin")
		@Config.RequiresMcRestart
		public boolean wailaEntityNameMixin = true;
		@Config.Comment("Localizes the Grave Scroll description.")
		@Config.Name("(CorpseComplex) Grave Scroll Description Mixin")
		@Config.LangKey("config.localizator.mixins.corpsecomplexScrollMixin")
		@Config.RequiresMcRestart
		public boolean corpsecomplexScrollMixin = true;
		@Config.Comment("In Charm's [de_DE, es_MX] lang files, the Creative Music Disc 6 lang key \nis registered as \"item.charm:record_creative6\", resulting in \nMinecraft adding \".name\" at the end of its name. \nMay cause lag when loading languages.")
		@Config.Name("(Minecraft/Charm) Creative Disc 6 Mixin")
		@Config.LangKey("config.localizator.mixins.charmCreative6Mixin")
		@Config.RequiresMcRestart
		public boolean charmCreative6Mixin = true;
		//@Config.Comment("Localizes few texts in the Mod List screen.")
		//@Config.Name("(FML) Localized Mod List GUI Mixin")
		//@Config.LangKey("config.localizator.mixins.forgeModListMixin")
		//@Config.RequiresMcRestart
		//public boolean forgeModListMixin = true;
	}

	public static class ClientConfig {
		@Config.Comment("Applies Mixins - \"(Minecraft) Mob Custom Names Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Minecraft) Translate Mob Custom Names")
		@Config.LangKey("config.localizator.client.minecraftMobLocCustomNames")
		public boolean minecraftMobLocCustomNames = true;
		@Config.Comment("Applies Mixins - \"(Minecraft) Boss Custom Names Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Minecraft) Translate Boss Custom Names")
		@Config.LangKey("config.localizator.client.minecraftBossLocCustomNames")
		public boolean minecraftBossLocCustomNames = true;
		@Config.Comment("Applies Mixins - \"(Neat) Health Bar Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Neat) Translate Names In Health Bar")
		@Config.LangKey("config.localizator.client.neatLocHealthBar")
		public boolean neatLocHealthBar = true;
		@Config.Comment("In SRP armor tooltip, translate resisted damage sources names. \nEntity names are taken from the registry. \nNon-entity names are taken from this mod's lang file. \n§cDisable this if it impacts performance.")
		@Config.Name("(SRParasites) Translate Resisted Damage Sources")
		@Config.LangKey("config.localizator.client.srparasitesResistances")
		public boolean srparasitesResistances = true;
		@Config.Comment("Prevents a Bauble's Modifier from displaying at its Name")
		@Config.Name("(BountifulBaubles) Remove Modifier from Bauble Name")
		@Config.LangKey("config.localizator.client.bountifulbaublesRemoveModifierFromName")
		public boolean bountifulbaublesRemoveModifierFromName = true;
		@Config.Comment("Removes the need of Shift on RoughTweaks Items Tooltip to show the Heal Amount. \n- \"(RoughTweaks) Localized Names and Better Tooltip Mixin\" needs to be enabled.")
		@Config.Name("(RoughTweaks) Show Heal Amount")
		@Config.LangKey("config.localizator.client.roughtweaksTooltip")		
		public boolean roughtweaksTooltip = true;
		
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
				isFirstBoot = true; // If the config file doesn't exist yet, assume all mixins are enabled.
			}
		}

		//If entry is not in the config file, don't enable injection on first run
		return isFirstBoot || configBooleanString.contains("B:\"" + name + "\"=true");
	}
}