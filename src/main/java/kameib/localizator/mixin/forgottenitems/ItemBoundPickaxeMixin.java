package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import tschipp.forgottenitems.items.ItemBoundPickaxe;

@Mixin(ItemBoundPickaxe.class)
public abstract class ItemBoundPickaxeMixin {
    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            // Slice just in case someone wants to inject a tooltip.add at the head of addInformation. Am I overthinking?
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/item/ItemStack;hasTagCompound()Z",
                            remap = Production.inProduction
                    )
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Bound to " with a lang key that takes the owner name as an argument.
    // This gives translators full control over the word order, and gives modpack devs full control over the formatting.
    // Line 102: tooltip.add("Bound to " + stack.getTagCompound().getString("owner"));
    private Object localizator_ForgottenItems_ItemBoundPickaxe_addInformation_boundTo(Object original) {
        String owner = ((String)original).replace("Bound to ", "");
        return I18n.format("tooltip.forgottenitems.bound_tools.bound_to",owner);
    }

    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Unbound"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Unbound" with a lang key.
    // Line 104: tooltip.add("Unbound");
    private String localizator_ForgottenItems_ItemBoundPickaxe_addInformation_unbound(String original) {
        return I18n.format("tooltip.forgottenitems.bound_tools.unbound");
    }

    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Stays in the inventory on death."),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Stays in the inventory on death." with a lang key.
    // Line 107: tooltip.add("Stays in the inventory on death.");
    private String localizator_ForgottenItems_ItemBoundPickaxe_addInformation_description(String original) {
        return I18n.format("tooltip.forgottenitems.bound_tools.desc");
    }
}
