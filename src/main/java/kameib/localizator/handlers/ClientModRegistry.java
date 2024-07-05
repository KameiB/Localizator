package kameib.localizator.handlers;

import kameib.localizator.Localizator;
import kameib.localizator.common.fishingmadebetter.item.FMBItemManager;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Localizator.MODID, value = Side.CLIENT)
public class ClientModRegistry {

    @SubscribeEvent
    public static void modelRegisterEvent(ModelRegistryEvent event) {
        if (Loader.isModLoaded("fishingmadebetter")) {
            FMBItemManager.registerModels();
        }
    }
}