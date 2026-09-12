package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tschipp.forgottenitems.items.ItemTalisman;

@Mixin(ItemTalisman.class)
public abstract class ItemTalismanMixin {
    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Translate the item's lore.
    // Line 45: tooltip.add(this.lore);
    private Object localizator_ForgottenItems_ItemTalisman_addInformation(Object lore) {
        return I18n.format((String)lore);
    }
}
