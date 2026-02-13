package kameib.localizator.handlers.checker;

import kameib.localizator.Localizator;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber(modid = Localizator.MODID)
public class McaVersionChecker {

    private static final String MCA_MOD_ID = "mca";
    private static final String TARGET_VERSION = "6.2.0";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded(MCA_MOD_ID)) {
            String mcaVersion = Loader.instance().getIndexedModList().get(MCA_MOD_ID).getVersion();
            if (isVersionHigher(mcaVersion, TARGET_VERSION)) {
                disableMcaConfigs();
            }
        }
    }

    private static boolean isVersionHigher(String currentVersion, String targetVersion) {
        String[] currentParts = currentVersion.split("\\.");
        String[] targetParts = targetVersion.split("\\.");
        int length = Math.max(currentParts.length, targetParts.length);

        for (int i = 0; i < length; i++) {
            int currentPart = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int targetPart = i < targetParts.length ? Integer.parseInt(targetParts[i]) : 0;
            if (currentPart > targetPart) {
                return true;
            } else if (currentPart < targetPart) {
                return false;
            }
        }
        return false;
    }

    private static void disableMcaConfigs() {
        Localizator.LOGGER.info("[Localizator] Detected MCA version higher than 6.2.0. Disabling MCA-related configurations.");
        ForgeConfigHandler.localizingMixinsConfig.minecraftLocNameMixin = false;
        ForgeConfigHandler.localizingMixinsConfig.minecraftLocLoreMixin = false;
        ForgeConfigHandler.localizingMixinsConfig.minecraftBiomeMixin = false;
        // Add other MCA-related configurations here if needed
    }
}