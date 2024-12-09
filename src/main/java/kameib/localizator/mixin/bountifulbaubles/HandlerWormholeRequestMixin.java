package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.network.NBTPacket;
import cursedflames.bountifulbaubles.network.wormhole.HandlerWormholeRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(HandlerWormholeRequest.class)
public abstract class HandlerWormholeRequestMixin {
    
    /**
     * @author KameiB
     * @reason Replace hardcoded texts with lang keys. Made and Overwrite to avoid several injections.
     */
    @Overwrite(remap = false)
    public static void handleWormholeRequest(NBTPacket message, MessageContext ctx) {
        NBTTagCompound tag = message.getTag();
        if (tag.hasUniqueId("sender") && tag.hasKey("name")) {
            ITextComponent name = new TextComponentString(tag.getString("name")).setStyle(new Style().setColor(TextFormatting.YELLOW));
            ITextComponent acc = new TextComponentTranslation("message.bountifulbaubles.wormhole.acceptButton")
                            .setStyle(new Style().setColor(TextFormatting.GREEN)
                                .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole acc " + name.getUnformattedComponentText())));
            ITextComponent rej = new TextComponentTranslation("message.bountifulbaubles.wormhole.rejectButton")
                            .setStyle(new Style().setColor(TextFormatting.RED)
                                .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole deny " + name.getUnformattedComponentText())));
            
            ITextComponent base = new TextComponentTranslation("message.bountifulbaubles.wormhole.receivedReq", name, acc, rej);
            Minecraft.getMinecraft().player.sendMessage(base);
        }
    }
}
