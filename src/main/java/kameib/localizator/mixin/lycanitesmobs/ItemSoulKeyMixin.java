package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.special.ItemSoulkey;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemSoulkey.class)
public abstract class ItemSoulKeyMixin {
    @ModifyArg(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V", ordinal = 0),
            remap = Production.inProduction
    )
    // Translate message on client side
    // Line 51: player.sendMessage(new TextComponentString(message));
    private ITextComponent localizator_Lycanites_ItemSoulKey_onItemUse_disabled(ITextComponent message) {
        return new TextComponentTranslation("message.soulkey.disabled");
    }
    @ModifyArg(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V", ordinal = 1),
            remap = Production.inProduction
    )
    // Translate message on client side
    // Line 65: player.sendMessage(new TextComponentString(message));
    private ITextComponent localizator_Lycanites_ItemSoulKey_onItemUse_none(ITextComponent message) {
        return new TextComponentTranslation("message.soulkey.none");
    }
    @ModifyArg(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V", ordinal = 2),
            remap = Production.inProduction
    )
    // Translate message on client side
    // Line 78: player.sendMessage(new TextComponentString(message));
    private ITextComponent localizator_Lycanites_ItemSoulKey_onItemUse_badlocation(ITextComponent message) {
        return new TextComponentTranslation("message.soulkey.badlocation");
    }
    @ModifyArg(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V", ordinal = 3),
            remap = Production.inProduction
    )
    // Translate message on client side
    // Line 84: player.sendMessage(new TextComponentString(message));
    private ITextComponent localizator_Lycanites_ItemSoulKey_onItemUse_active(ITextComponent message) {
        return new TextComponentTranslation("message.soulkey.active");
    }
    @ModifyArg(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V", ordinal = 4),
            remap = Production.inProduction
    )
    // Translate message on client side
    // Line 92: player.sendMessage(new TextComponentString(message));
    private ITextComponent localizator_Lycanites_ItemSoulKey_onItemUse_invalid(ITextComponent message) {
        return new TextComponentTranslation("message.soulkey.invalid");
    }
}
