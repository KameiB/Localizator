package kameib.localizator.common.networking;

import io.netty.buffer.ByteBuf;
import kameib.localizator.util.text.event.FishRequirementsClickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FishRequirementsClickEventPacket implements IMessage {
    private FishRequirementsClickEvent clickEvent;

    public FishRequirementsClickEventPacket() {
        // Required empty constructor for Forge
    }

    public FishRequirementsClickEventPacket(FishRequirementsClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Deserialize data from the ByteBuf
        //this.clickEvent = FishRequirementsClickEvent.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Serialize data into the ByteBuf
        // Example: this.clickEvent.toBytes(buf);
    }
    
    public static class ClickEventHandler implements IMessageHandler<FishRequirementsClickEventPacket, IMessage> {

        /**
         * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
         * is needed.
         *
         * @param message The message
         * @param ctx
         * @return an optional return message
         */
        @Override
        public IMessage onMessage(FishRequirementsClickEventPacket message, MessageContext ctx) {
            return null;
        }
    }
}
