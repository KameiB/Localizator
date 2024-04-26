package kameib.localizator.mixin.setbonus;

import com.fantasticsource.setbonus.client.ClientBonus;
import com.fantasticsource.setbonus.client.TooltipRenderer;
import com.fantasticsource.setbonus.common.bonusrequirements.setrequirement.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TooltipRenderer.class)
public abstract class TooltipRendererMixin {
    @SideOnly(Side.CLIENT)
    @Redirect(
            method = "tooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/fantasticsource/setbonus/common/bonusrequirements/setrequirement/Set;getMaxNumber()I",
                    remap = false
            ),
            remap = false
    )
    // Make set.name translatable
    // Line 58: int max = set.getMaxNumber();
    private static int SetBonus_TooltipRenderer_tooltips_set_assignName(Set set) {
        if (I18n.hasKey(set.name)) {
            set.name = I18n.format(set.name);
        }
        return set.getMaxNumber();
    }

    @SideOnly(Side.CLIENT)
    @Redirect(
            method = "tooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/fantasticsource/setbonus/client/ClientBonus;getBonusInstance(Lnet/minecraft/entity/player/EntityPlayer;)Lcom/fantasticsource/setbonus/client/ClientBonus$BonusInstance;",
                    remap = false
            ),
            remap = false
    )
    // Make bonus.name translatable
    // Line 84: ClientBonus.BonusInstance bonusInstance = bonus.getBonusInstance(player);
    private static ClientBonus.BonusInstance SetBonus_TooltipRenderer_tooltips_bonus_assignName(ClientBonus bonus, EntityPlayer player) {
        if (I18n.hasKey(bonus.name)) {
            bonus.name = I18n.format(bonus.name);
        }
        return bonus.getBonusInstance(player);
    }
}
