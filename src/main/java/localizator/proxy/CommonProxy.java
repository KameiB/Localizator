package localizator.proxy;


import localizator.client.event.NewConfigEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class CommonProxy {

    public void preInit() { }
    
    public void onInit(FMLInitializationEvent event) {
        registerEvents();
    }
    
    protected void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new NewConfigEvent());
    }

}