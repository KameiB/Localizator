package localizator.mixin.waila;

import mcp.mobius.waila.addons.core.HUDHandlerEntities;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HUDHandlerEntities.class)
public abstract class HUDHandlerEntitiesMixin {
    @ModifyArg(
            method = "getWailaHead(Lnet/minecraft/entity/Entity;Ljava/util/List;Lmcp/mobius/waila/api/IWailaEntityAccessor;Lmcp/mobius/waila/api/IWailaConfigHandler;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0
            ),
            index = 1,
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Line 33: currenttip.add("\u00a7r" + String.format(FormattingConfig.entityFormat, entity.getName()));
    private Object[] localizator_Waila_HUDHandlerEntities_getWailaHead_entityName(Object[] args) {
        if (args[0] instanceof String) {
            String name = (String)args[0];
            if (I18n.hasKey(name)) {
                args[0] = I18n.format(name);
            }
        }
        return args;
    }
}
