package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.attachment.bobber.ItemBobber;
import net.theawesomegem.fishingmadebetter.common.item.attachment.bobber.ItemBobberBasic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemBobberBasic.class)
public abstract class ItemBobberBasicMixin extends ItemBobber {
    public ItemBobberBasicMixin(String name, int maxDamage, boolean lavaBobber, boolean voidBobber, int varianceModifier, int tensioningModifier) {
        super(name, maxDamage, lavaBobber, voidBobber, varianceModifier, tensioningModifier);
    }

    @SideOnly(Side.CLIENT)
    @Inject(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At("HEAD"),
            remap = Production.inProduction
    )
    // Make it show its details on the tooltip
    // Line 25
    private void FMB_ItemBobberBasic_addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn, CallbackInfo ci) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
