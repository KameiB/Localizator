package kameib.localizator.mixin.charm;

import kameib.localizator.data.Production;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import svenhjol.charm.Charm;
import svenhjol.charm.brewing.item.ItemFlavoredCake;
import svenhjol.meson.iface.IMesonBlock;

@Mixin(ItemFlavoredCake.class)
public abstract class ItemFlavoredCakeMixin extends ItemBlock {    
    public ItemFlavoredCakeMixin(Block block) {
        super(block);
    }

    /**
     * @author KameiB
     * @reason Cake variation name were hardcoded texts
     */
    @Inject(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 22
    private void Charm_ItemFlavoredCake_getItemStackDisplayName(ItemStack stack, CallbackInfoReturnable<String> cir) {
        String name = ((IMesonBlock)this.block).getName();
        if (name == null) {
            cir.setReturnValue(super.getItemStackDisplayName(stack));
        } else {
            cir.setReturnValue(I18n.format("tile." + Charm.MOD_ID + "." + name + ".name"));
        }
    }
}
