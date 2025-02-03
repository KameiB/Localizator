package kameib.localizator.mixin.mca;

import mca.core.MCA;
import mca.core.forge.EventHooks;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EventHooks.class)
public abstract class EventHooksMixin {
    /**
     * @author KameiB
     * @reason Replace TextComponentString with TextComponentTranslation
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (MCA.updateAvailable) {
            ITextComponent updateMessage = new TextComponentTranslation("message.mca.updateAvailable").appendText(" v" + MCA.latestVersion);
            ITextComponent chatComponentUpdate = new TextComponentTranslation("message.mca.clickToUpdate");
            chatComponentUpdate.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://minecraftcomesalive.com/download"));
            chatComponentUpdate.getStyle().setUnderlined(true);
            event.player.sendMessage(updateMessage);
            event.player.sendMessage(chatComponentUpdate);
            MCA.updateAvailable = false;
        }
    }
}
