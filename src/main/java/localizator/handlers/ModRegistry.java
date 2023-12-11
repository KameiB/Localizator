package localizator.handlers;

import localizator.Localizator;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Localizator.MODID)
public class ModRegistry {
        public static void init() {
                ConfigManager.sync(Localizator.MODID, Config.Type.INSTANCE);
        }
}