package kameib.localizator.mixin.waila;

import com.google.common.base.Strings;
import mcp.mobius.waila.addons.core.HUDHandlerEntities;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.config.FormattingConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nonnull;
import java.util.List;

@Mixin(HUDHandlerEntities.class)
public abstract class HUDHandlerEntitiesMixin {
    
    /**
     * @author KameiB
     * @reason If entity.getName is a lang key (from CustomName NBT tag), translate it
     */
    @Nonnull
    @Overwrite(remap = false)
    // Line 33
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        if (!Strings.isNullOrEmpty(FormattingConfig.entityFormat)) {
            try {
                if (entity instanceof EntityPlayer) {
                    currenttip.add(TextFormatting.RESET + String.format(FormattingConfig.entityFormat, entity.getName()));
                }
                else {
                    currenttip.add(TextFormatting.RESET + String.format(FormattingConfig.entityFormat,
                            I18n.hasKey(entity.getName()) ? I18n.format(entity.getName()) : entity.getName()));
                }
                
            } catch (Exception e) {
                currenttip.add(TextFormatting.RESET + String.format(FormattingConfig.entityFormat, I18n.format("entity.waila.unknown")));
            }
        } else currenttip.add(I18n.format("entity.waila.unknown"));

        return currenttip;
    }
}
