package kameib.localizator.proxy;

import kameib.localizator.client.event.FishRequirementsOnClickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
    }

    public void onInit(FMLInitializationEvent event) {
        registerEvents();
    }
    
    protected void registerEvents() {
        if (Loader.isModLoaded("fishingmadebetter")) {
            MinecraftForge.EVENT_BUS.register(new FishRequirementsOnClickEvent());
        }
    }
}