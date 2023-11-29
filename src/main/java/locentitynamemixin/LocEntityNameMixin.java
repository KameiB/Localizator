package locentitynamemixin;

import locentitynamemixin.handlers.ModRegistry;
import locentitynamemixin.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = LocEntityNameMixin.MODID, version = LocEntityNameMixin.VERSION, name = LocEntityNameMixin.NAME)
public class LocEntityNameMixin {
    public static final String MODID = "locentitynamemixin";
    public static final String VERSION = "1.0.1";
    public static final String NAME = "Localized Entity Name";
    public static final Logger LOGGER = LogManager.getLogger();

    @SidedProxy(clientSide = "locentitynamemixin.proxy.ClientProxy", serverSide = "locentitynamemixin.proxy.CommonProxy")
    public static CommonProxy PROXY;
    @Instance(MODID)
    public static LocEntityNameMixin instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        LocEntityNameMixin.PROXY.preInit();
    }
}
