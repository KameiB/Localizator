package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tschipp.forgottenitems.items.ItemBoundShovel;

import java.util.List;

@Mixin(ItemBoundShovel.class)
public abstract class ItemBoundShovelMixin {   
    /**
     * @author KameiB
     * @reason Localize bounding texts
     */
    @Inject(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 100
    private void ForgottenItems_ItemBoundShovel_addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag, CallbackInfo ci)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("owner")) {
            tooltip.add(I18n.format("tooltip.forgottenitems.bound_tools.bound_to") + " " + stack.getTagCompound().getString("owner"));
        } else {
            tooltip.add(I18n.format("tooltip.forgottenitems.bound_tools.unbound"));
        }

        tooltip.add(I18n.format("tooltip.forgottenitems.bound_tools.desc"));
        ci.cancel();
    }
}
