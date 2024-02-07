package kameib.localizator.common.networking;

import kameib.localizator.Localizator;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PrimaryPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Localizator.MODID);
    
    public static void registerPackets() {
        //INSTANCE.registerMessage(FishRequirementsClickEventPacket.class, );
    }
}
