package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.common.fishingmadebetter.item.ItemLavaFishBucket;
import kameib.localizator.common.fishingmadebetter.item.ItemVoidBucket;
import kameib.localizator.common.fishingmadebetter.item.ItemVoidFishBucket;
import kameib.localizator.util.LocLoreUtil;
import net.minecraft.item.ItemStack;
import net.theawesomegem.fishingmadebetter.BetterFishUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BetterFishUtil.class)
public abstract class BetterFishUtilMixin {
    @ModifyArg(
            method = "setFishHasScale(Lnet/minecraft/item/ItemStack;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/util/ItemStackUtil;appendToolTip(Lnet/minecraft/item/ItemStack;Ljava/lang/Iterable;)Lnet/minecraft/item/ItemStack;",                    
                    remap = false
            ),
            index = 0,
            remap = false
    )
    // Append LocLore and LocLoreArg NBT tags to the fish
    // Line 66: itemStack = ItemStackUtil.appendToolTip(itemStack, tooltipList);
    private static ItemStack FishingMadeBetter_BetterFishUtil_appendLocLore(ItemStack itemStack) {
        List<String> locLoreList = new ArrayList<>();
        locLoreList.add("tooltip.fishingmadebetter.fish.weight");
        locLoreList.add("tooltip.fishingmadebetter.fish.scale_detached");
        locLoreList.add("tooltip.fishingmadebetter.fish.dead");
        
        List<String> argList = new ArrayList<>();
        argList.add(String.valueOf(getFishWeight(itemStack)));
        argList.add("");
        argList.add("");

        return LocLoreUtil.appendLocLore(itemStack, locLoreList, argList);        
    }    
    
    @Shadow(remap = false)
    public static int getFishWeight(ItemStack itemStack) {
        return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("FishWeight") ? itemStack.getTagCompound().getInteger("FishWeight") : 1;
    }

    @Inject(
            method = "isBetterFish(Lnet/minecraft/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    // Add compatiblity for the new Fish Buckets
    private static void FMB_BetterFishUtil_isBetterFish(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (itemStack.getItem() instanceof ItemLavaFishBucket || itemStack.getItem() instanceof ItemVoidBucket || itemStack.getItem() instanceof ItemVoidFishBucket) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
    
}
