package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.theawesomegem.fishingmadebetter.common.command.FishingReloadCommand;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingReloadCommand.class)
public abstract class FishingReloadCommandMixin {
    
    @Redirect(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/common/configuration/CustomConfigurationHandler;loadFishes()Z",
                    ordinal = 0,
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Capture the result of loadFishes
    // Line 31: boolean loaded = CustomConfigurationHandler.loadFishes();
    private boolean FMB_FishingReloadCommand_execute_getLoadFishes() {
        localizator$fishesLoaded = CustomConfigurationHandler.loadFishes();
        return localizator$fishesLoaded;
    }
    
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Send a TextComponentTranslation instead of a TextComponentString
    // Line 39: sender.sendMessage(new TextComponentString(message));
    private ITextComponent FMB_FishingReloadCommand_execute_sendMessage(ITextComponent component) {
        return localizator$fishesLoaded ? 
                new TextComponentTranslation("notif.fishingmadebetter.command.reload_success") :
                new TextComponentTranslation("notif.fishingmadebetter.command.reload_fail");
    }
    
    @Unique
    boolean localizator$fishesLoaded;
}
