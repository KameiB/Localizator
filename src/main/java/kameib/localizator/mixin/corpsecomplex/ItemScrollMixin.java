package kameib.localizator.mixin.corpsecomplex;

import c4.corpsecomplex.common.modules.spawning.ItemScroll;
import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemScroll.class)
public abstract class ItemScrollMixin {
    /**
     * @author KameiB
     * @reason Grave scroll description was hardcoded
     */
    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    remap = false
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 103: tooltip.add("Return to death location");
    private Object CorpseComplex_ItemScroll_addInformation(Object desc) {
        return I18n.format("item.corpsecomplex.scroll.desc");
    }
}
