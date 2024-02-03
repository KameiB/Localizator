package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.consumable.ItemHalloweenTreat;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemHalloweenTreat.class)
public abstract class ItemHalloweenTreatMixin extends ItemBase {    
    @ModifyArg(
            method = "openGood(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 64: player.sendMessage(new TextComponentString(message));
    private ITextComponent localizator_Lycanites_ItemHalloweenTreat_openGood(ITextComponent message) {
        return new TextComponentTranslation("item." + this.itemName + ".good");
    }
    @ModifyArg(
            method = "openBad(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 64: player.sendMessage(new TextComponentString(message));
    private ITextComponent localizator_Lycanites_ItemHalloweenTreat_openBad(ITextComponent message) {
        return new TextComponentTranslation("item." + this.itemName + ".bad");
    }
}
