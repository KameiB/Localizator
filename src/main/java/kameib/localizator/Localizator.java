package kameib.localizator;

import kameib.localizator.handlers.ModRegistry;
import kameib.localizator.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Localizator.MODID, version = Localizator.VERSION, name = Localizator.NAME)
public class Localizator {
    public static final String MODID = "localizator";
    public static final String VERSION = "1.0.13";
    public static final String NAME = "Localizator";
    public static final Logger LOGGER = LogManager.getLogger();

    @SidedProxy(clientSide = "kameib.localizator.proxy.ClientProxy", serverSide = "kameib.localizator.proxy.CommonProxy")
    public static CommonProxy PROXY;
    @Instance(MODID)
    public static Localizator instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        Localizator.PROXY.preInit();
    }
    
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        Localizator.PROXY.onInit(event);
    }
}
