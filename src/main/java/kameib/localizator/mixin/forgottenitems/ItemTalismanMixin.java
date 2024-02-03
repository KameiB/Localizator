package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tschipp.forgottenitems.items.ItemTalisman;

import java.util.List;

@Mixin(ItemTalisman.class)
public abstract class ItemTalismanMixin {
    @Shadow(remap = false)
    private String lore;
    /**
     * @author KameiB
     * @reason Localize item description
     */
    @Inject(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 44
    private void ForgottenItems_ItemTalisman_addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag, CallbackInfo ci)
    {        
        tooltip.add(I18n.format(this.lore));
        ci.cancel();
    }
}
