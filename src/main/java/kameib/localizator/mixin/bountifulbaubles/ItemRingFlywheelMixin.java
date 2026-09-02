package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemRingFlywheel;
import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemRingFlywheel.class)
public abstract class ItemRingFlywheelMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "\ufffd4"),
            remap = Production.inProduction
    )
    // Replace the bugged "�4" in the tooltip with the proper color code.
    // Line 69: String color = energy == 0 ? "�4" : (energy < max / 4 ? "�c" : (energy < max / 2 ? "�e" : "�a"));
    @SideOnly(Side.CLIENT)
    private String localizator_BountifulBaubles_ItemRingFlywheel_addInformation_darkRed(String bad) {
        return "" + TextFormatting.DARK_RED;
    }

    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "\ufffdc"),
            remap = Production.inProduction
    )
    // Replace the bugged "�c" in the tooltip with the proper color code.
    // Line 69: String color = energy == 0 ? "�4" : (energy < max / 4 ? "�c" : (energy < max / 2 ? "�e" : "�a"));
    @SideOnly(Side.CLIENT)
    private String localizator_BountifulBaubles_ItemRingFlywheel_addInformation_Red(String bad) {
        return "" + TextFormatting.RED;
    }

    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "\ufffde"),
            remap = Production.inProduction
    )
    // Replace the bugged "�e" in the tooltip with the proper color code.
    // Line 69: String color = energy == 0 ? "�4" : (energy < max / 4 ? "�c" : (energy < max / 2 ? "�e" : "�a"));
    @SideOnly(Side.CLIENT)
    private String localizator_BountifulBaubles_ItemRingFlywheel_addInformation_Yellow(String bad) {
        return "" + TextFormatting.YELLOW;
    }

    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "\ufffda"),
            remap = Production.inProduction
    )
    // Replace the bugged "�a" in the tooltip with the proper color code.
    // Line 69: String color = energy == 0 ? "�4" : (energy < max / 4 ? "�c" : (energy < max / 2 ? "�e" : "�a"));
    @SideOnly(Side.CLIENT)
    private String localizator_BountifulBaubles_ItemRingFlywheel_addInformation_Green(String bad) {
        return "" + TextFormatting.GREEN;
    }
    
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "RF"),
            remap = Production.inProduction
    )
    // Replace the hardcoded "RF" (Revolution Force?) unit with a translated lang key for reasons
    // Line 70: tooltip.add(color + String.valueOf(energy) + "/" + max + "RF");
    @SideOnly(Side.CLIENT)
    private String localizator_BountifulBaubles_ItemRingFlywheel_addInformation_RF(String rf) {
        return I18n.format("item.bountifulbaubles.ringFlywheel.RF");
    }
}
