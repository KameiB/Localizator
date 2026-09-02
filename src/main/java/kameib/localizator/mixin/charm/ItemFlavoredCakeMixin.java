package kameib.localizator.mixin.charm;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import kameib.localizator.data.Production;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import svenhjol.charm.brewing.item.ItemFlavoredCake;
import svenhjol.meson.iface.IMesonBlock;

@Mixin(ItemFlavoredCake.class)
public abstract class ItemFlavoredCakeMixin {
    @ModifyReturnValue(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            ),
            remap = Production.inProduction
    )
    // Modify the second return value to use an independent lang key instead of a generic lang key + a hardcoded text
    // Line 34: return I18n.format("charm.cake_of", new Object[0]) + newName;
    @SideOnly(Side.CLIENT)
    private String localizator_Charm_ItemFlavoredCake_getItemStackDisplayName_return1(String original) {
        // Replicate the original "name" local variable initialization
        String name = ((IMesonBlock)localizator$block).getName();
        // Use a lang key that depends on the current flavor.
        return I18n.format("tile.charm." + name + ".name");
    }

    @Unique
    @Mutable
    protected Block localizator$block;
    
    @Inject(
            method = "<init>(Lnet/minecraft/block/Block;)V",
            at = @At("TAIL"),
            remap = false
    )
    private void localizator_Charm_ItemFlavoredCake_init_captureBlock(Block block, CallbackInfo ci) {
        localizator$block=block;
    }
}
