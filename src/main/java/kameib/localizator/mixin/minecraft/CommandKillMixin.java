package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import net.minecraft.command.CommandKill;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandKill.class)
public abstract class CommandKillMixin {
    @Redirect(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getDisplayName()Lnet/minecraft/util/text/ITextComponent;",
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // When killing item entities, replace "item.item...." with "item....name" so their names are shown
    private ITextComponent Minecraft_CommandKill_execute_notifyCommandListener_reformatItemLangKey(Entity entity) {
        if (entity.getDisplayName().getUnformattedComponentText().startsWith("item.tile.") || 
             entity.getDisplayName().getUnformattedComponentText().startsWith("item.item."))
        {
            String name = entity.getDisplayName().getUnformattedComponentText().replaceFirst("item.", "").concat(".name");
            if (name.contentEquals("item.monsterPlacer.name")) 
            {
                name = name.replaceFirst("item.", "entity.");
            }
            return new TextComponentTranslation(name).setStyle(new Style().setColor(TextFormatting.GRAY));
        }
        else {
            return entity.getDisplayName();
        }
    }

}
