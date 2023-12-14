package localizator.mixin.waila;

import com.google.common.base.Strings;
import mcp.mobius.waila.addons.core.HUDHandlerEntities;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.config.FormattingConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(HUDHandlerEntities.class)
public abstract class HUDHandlerEntitiesMixin {
    
    /**
     * @author KameiB
     * @reason If entity.getName is a lang key (from CustomName NBT tag), translate it
     */
    @Overwrite(remap = false)
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        if (!Strings.isNullOrEmpty(FormattingConfig.entityFormat)) {
            try {
                currenttip.add("\u00a7r" + String.format(FormattingConfig.entityFormat, 
                        I18n.hasKey(entity.getName()) ? I18n.format(entity.getName()) : entity.getName()));
            } catch (Exception e) {
                currenttip.add("\u00a7r" + String.format(FormattingConfig.entityFormat, "Unknown"));
            }
        } else currenttip.add("Unknown");

        return currenttip;
    }
}
