package kameib.localizator.mixin.dynamictrees;

import com.ferreusveritas.dynamictrees.items.Staff;
import kameib.localizator.data.Production;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Staff.class)
public abstract class StaffMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "\u00c2\u00a7a"),
            remap = Production.inProduction
    )
    // Fix the bugged "Â§a" with proper TextFormatting.GREEN
    // Line 271: tooltip.add(getTranslationText("tooltip.woodland_staff.tree") + " " + (species != null ? "Â§a" + species.getLocalizedName() : getTranslationText("tooltip.woodland_staff.tree_not_set")));
    private String localizator_DynamicTrees_Staff_addInformation_fixGreenColor(String original) {
        return "" + TextFormatting.GREEN;
    }

    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "JoCode: \u00c2\u00a76"),
            remap = Production.inProduction
    )
    // Fix JoCode's bugged "Â§6" with proper TextFormatting.GOLD
    // Line 272: tooltip.add("JoCode: Â§6" + this.getCode(stack));
    private String localizator_DynamicTrees_Staff_addInformation_fixJoCodeColor(String original) {
        return "JoCode: " + TextFormatting.GOLD;
    }
}
