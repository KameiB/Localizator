package locentitynamemixin;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import java.util.Map;
import javax.annotation.Nullable;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
public class LocEntityNameMixinPlugin implements IFMLLoadingPlugin {
    public LocEntityNameMixinPlugin(){
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.locentityname.json");
        Mixins.addConfiguration("mixins.bossoverlay.json");
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
