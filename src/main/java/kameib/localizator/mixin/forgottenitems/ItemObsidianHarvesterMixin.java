package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemObsidianHarvester;

@Mixin(ItemObsidianHarvester.class)
public abstract class ItemObsidianHarvesterMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Very useful for mining Obsidian"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Very useful for mining Obsidian" with a translated lang key.
    // Line 45: tooltip.add("Very useful for mining Obsidian");
    private static String localizator_ForgottenItems_ItemObsidianHarvester_addInformation(String original) {
        return I18n.format("item.obsidian_harvester.desc");
    }
}
