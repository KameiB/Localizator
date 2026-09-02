package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.attachment.bobber.ItemBobber;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemBobber.class)
public abstract class ItemBobberMixin {
    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/I18n;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // I just wanted the "Lava" text to show in Red, ok? >:( It's called cOnSiStEnCy
    // Line 61: tooltip.add(I18n.format("item.fishingmadebetter.bobber_obsidian.tooltip", new Object[0]));
    private String localizator_FMB_ItemBobber_addInformation_formatLavaBobber (String originalText, Object[] originalArgs) {
        String redLava = I18n.format("tooltip.fishingmadebetter.bobber.obsidian");
        return I18n.format("tooltip.fishingmadebetter.bobber.can_fish_in", redLava);
    }

    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/I18n;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // I just wanted the "Void" text to show in Light Purple, ok? >:( It's called cOnSiStEnCy
    // Line 65: tooltip.add(I18n.format("item.fishingmadebetter.bobber_void.tooltip", new Object[0]));
    private String localizator_FMB_ItemBobber_addInformation_formatVoidBobber (String originalText, Object[] originalArgs) {
        String lightPurpleVoid = I18n.format("tooltip.fishingmadebetter.bobber.void");
        return I18n.format("tooltip.fishingmadebetter.bobber.can_fish_in", lightPurpleVoid);
    }

    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/I18n;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // I just wanted the "Water" text to show in Aqua, ok? >:( It's called cOnSiStEnCy
    // Line 69: tooltip.add(I18n.format("item.fishingmadebetter.bobber_water.tooltip", new Object[0]));
    private String localizator_FMB_ItemBobber_addInformation_formatWaterBobber (String originalText, Object[] originalArgs) {
        String lightPurpleVoid = I18n.format("tooltip.fishingmadebetter.bobber.water");
        return I18n.format("tooltip.fishingmadebetter.bobber.can_fish_in", lightPurpleVoid);
    }
}
