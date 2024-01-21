package localizator.mixin.fishingmadebetter;

import localizator.data.Production;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.theawesomegem.fishingmadebetter.common.command.FishingReloadCommand;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FishingReloadCommand.class)
public abstract class FishingReloadCommandMixin extends CommandBase {
    /**
     * @author KameiB
     * @reason Send localized messages
     */
    @Overwrite(remap = Production.inProduction)
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        boolean loaded = CustomConfigurationHandler.loadFishes();
        String message;

        if (loaded) {
            message = "notif.fishingmadebetter.command.reload_success";
        } else {
            message = "notif.fishingmadebetter.command.reload_fail";
        }

        sender.sendMessage(new TextComponentTranslation(message));
    }
}
