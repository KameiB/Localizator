package locentitynamemixin.handlers;

import locentitynamemixin.LocEntityNameMixin;
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

@Config(modid = LocEntityNameMixin.MODID)
public class ForgeConfigHandler {
	@Config.Comment("Enable/Disable Tweaks")
	@Config.Name("Mixins")
	@Config.LangKey("config.locentityname.mixins")
	public static final MixinConfig mixinConfig = new MixinConfig();

	@Config.Comment("Client-Side Options")
	@Config.Name("Client")
	@Config.LangKey("config.locentityname.client")
	public static final ClientConfig clientConfig = new ClientConfig();

	public static class MixinConfig {
		@Config.Comment("Enables Client - \"(Minecraft) Translate Mob Custom Names\" config option.")
		@Config.Name("(Minecraft) Mob Custom Names Mixin")
		@Config.LangKey("config.locentityname.mixins.vanillaMobLocCustomNamesMixin")
		@Config.RequiresMcRestart
		public boolean vanillaMobLocCustomNamesMixin = true;
		@Config.Comment("Enables Client - \"(Minecraft) Translate Boss Custom Names\" config option.")
		@Config.Name("(Minecraft) Boss Custom Names Mixin")
		@Config.LangKey("config.locentityname.mixins.vanillaBossLocCustomNamesMixin")
		@Config.RequiresMcRestart
		public boolean vanillaBossLocCustomNamesMixin = true;
		@Config.Comment("Enables Client - \"(Neat) Translate Names In Health Bar\" config option.")
		@Config.Name("(Neat) Health Bar Mixin")
		@Config.LangKey("config.locentityname.mixins.neatLocHealthBarMixin")
		@Config.RequiresMcRestart
		public boolean neatLocHealthBarMixin = true;
	}

	public static class ClientConfig {
		@Config.Comment("Applies Mixins - \"(Minecraft) Mob Custom Names Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Minecraft) Translate Mob Custom Names")
		@Config.LangKey("config.locentityname.client.vanillaMobLocCustomNames")
		public boolean vanillaMobLocCustomNames = true;
		@Config.Comment("Applies Mixins - \"(Minecraft) Boss Custom Names Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Minecraft) Translate Boss Custom Names")
		@Config.LangKey("config.locentityname.client.vanillaBossLocCustomNames")
		public boolean vanillaBossLocCustomNames = true;
		@Config.Comment("Applies Mixins - \"(Neat) Health Bar Mixin\". This mixin needs to be enabled.")
		@Config.Name("(Neat) Translate Names In Health Bar")
		@Config.LangKey("config.locentityname.client.neatLocHealthBar")
		public boolean neatLocHealthBar = true;
	}

	@Mod.EventBusSubscriber(modid = LocEntityNameMixin.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(LocEntityNameMixin.MODID)) {
				ConfigManager.sync(LocEntityNameMixin.MODID, Config.Type.INSTANCE);
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
			configFile = new File("config", LocEntityNameMixin.MODID + ".cfg");
			if (configFile.exists() && configFile.isFile()) {
				try (Stream<String> stream = Files.lines(configFile.toPath())) {
					configBooleanString = stream.filter(s -> s.trim().startsWith("B:")).collect(Collectors.joining());
				} catch (Exception ex) {
					LocEntityNameMixin.LOGGER.log(Level.ERROR, "Failed to parse LocEntityNameMixin config: " + ex);
				}
			} else {
				isFirstBoot = true; // If the config file doesn't exist yet, assume all mixins are enabled.
			}
		}

		//If entry is not in the config file, don't enable injection on first run
		return isFirstBoot || configBooleanString.contains("B:\"" + name + "\"=true");
	}
}