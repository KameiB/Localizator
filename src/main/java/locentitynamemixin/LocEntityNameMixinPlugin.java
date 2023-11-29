package locentitynamemixin;

import locentitynamemixin.data.ConfigToMixin;
import locentitynamemixin.handlers.ForgeConfigHandler;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.*;
import javax.annotation.Nullable;


@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
public class LocEntityNameMixinPlugin implements IFMLLoadingPlugin {
    public static final Map<String, List<ConfigToMixin>> vanillaMixins = initCoreConfigMap();
    private static Map<String, List<ConfigToMixin>> initCoreConfigMap() {
        Map<String, List<ConfigToMixin>> map = new HashMap<>();
        List<ConfigToMixin> list = new ArrayList<>();
        list.add(new ConfigToMixin("(Minecraft) Mob Custom Names Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Mob Custom Names Mixin"), "mixins.core.entityname.json"));
        list.add(new ConfigToMixin("(Minecraft) Boss Custom Names Mixin", ForgeConfigHandler.getBoolean("(Minecraft) Boss Custom Names Mixin"), "mixins.core.bossoverlay.json"));
        map.put("minecraft", list);

        return Collections.unmodifiableMap(map);
    }

    public LocEntityNameMixinPlugin(){
        MixinBootstrap.init();
        // First load core (Vanilla) ones...
        for (Map.Entry<String, List<ConfigToMixin>> entry : vanillaMixins.entrySet()) {
            for (ConfigToMixin config : entry.getValue()) {
                if (config.isEnabled()) {
                    LocEntityNameMixin.LOGGER.log(Level.INFO, "LocEntityNameMixin early loading: " + config.getName());
                    Mixins.addConfiguration(config.getJson());
                }
            }
        }

        // ... then the init one
        LocEntityNameMixin.LOGGER.log(Level.INFO, "Initializing LocEntityNameMixin initialization mixin");
        Mixins.addConfiguration("mixins.locentityname.init.json");
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
