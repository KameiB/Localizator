package localizator;

import localizator.data.ConfigToMixin;
import localizator.handlers.ForgeConfigHandler;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.*;
import javax.annotation.Nullable;


@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
public class LocalizatorPlugin implements IFMLLoadingPlugin {
    public static final Map<String, List<ConfigToMixin>> vanillaMixins = initCoreConfigMap();
    private static Map<String, List<ConfigToMixin>> initCoreConfigMap() {
        Map<String, List<ConfigToMixin>> map = new HashMap<>();
        List<ConfigToMixin> list = new ArrayList<>();
        list.add(new ConfigToMixin("(Minecraft) Mob Custom Names Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Mob Custom Names Mixin"), "mixins.core.entityname.json"));
        list.add(new ConfigToMixin("(Minecraft) Boss Custom Names Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Boss Custom Names Mixin"), "mixins.core.bossoverlay.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Lore Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Localized Lore Mixin"), "mixins.core.loclore.json"));
        list.add(new ConfigToMixin("(Minecraft) Localized Written Book Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Localized Written Book Mixin"), "mixins.core.writtenbooks.json"));
        list.add(new ConfigToMixin("(Minecraft/Charm) Creative Disc 6 Mixin", ForgeConfigHandler.getBoolean("(Minecraft/Charm) Creative Disc 6 Mixin"), "mixins.core.charmcreative6.json"));
        map.put("minecraft", list);

        return Collections.unmodifiableMap(map);
    }

    public LocalizatorPlugin(){
        MixinBootstrap.init();
        Localizator.LOGGER.log(Level.INFO, "[Localizator] Loading initialization mixin");
        Mixins.addConfiguration("mixins.localizator.init.json");
        
        for (Map.Entry<String, List<ConfigToMixin>> entry : vanillaMixins.entrySet()) {
            for (ConfigToMixin config : entry.getValue()) {
                if (config.isEnabled()) {
                    Localizator.LOGGER.log(Level.INFO, "[Localizator] Early loading: " + config.getName());
                    Mixins.addConfiguration(config.getJson());
                }
            }
        }        
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
