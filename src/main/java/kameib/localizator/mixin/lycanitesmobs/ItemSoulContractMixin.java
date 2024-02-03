package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.special.ItemSoulContract;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemSoulContract.class)
public abstract class ItemSoulContractMixin extends ItemBase {
    @ModifyArg(
            method = "onItemRightClickOnEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 77: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.invalid")));
    private ITextComponent lycanites_ItemSoulContract_onItemRightClickOnEntity_message(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.invalid");
    }
    @ModifyArg(
            method = "bindSoul(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 105: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.not_owner")));
    private ITextComponent lycanites_ItemSoulContract_bindSoul_message_not_owner(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.not_owner");
    }
    @ModifyArg(
            method = "bindSoul(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 116: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.bound")));
    private ITextComponent lycanites_ItemSoulContract_bindSoul_message_bound(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.bound");
    }
    @ModifyArg(
            method = "transferSoul(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;Ljava/util/UUID;Ljava/util/UUID;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 145: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.unbound")));
    private ITextComponent lycanites_ItemSoulContract_bindSoul_message_unbound(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.unbound");
    }
    @ModifyArg(
            method = "transferSoul(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;Ljava/util/UUID;Ljava/util/UUID;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 157: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.wrong_target")));
    private ITextComponent lycanites_ItemSoulContract_bindSoul_message_wrong_target(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.wrong_target");
    }
    @ModifyArg(
            method = "transferSoul(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;Ljava/util/UUID;Ljava/util/UUID;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 167: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.owner_missing")));
    private ITextComponent lycanites_ItemSoulContract_bindSoul_message_owner_missing(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.owner_missing");
    }
    @ModifyArg(
            method = "transferSoul(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;Ljava/util/UUID;Ljava/util/UUID;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 185: owner.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.transferred")));
    private ITextComponent lycanites_ItemSoulContract_bindSoul_message_owner_transferred(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.transferred");
    }
    @ModifyArg(
            method = "transferSoul(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;Ljava/util/UUID;Ljava/util/UUID;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 186: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soul_contract.transferred")));
    private ITextComponent lycanites_ItemSoulContract_bindSoul_message_player_transferred(ITextComponent message) {
        return new TextComponentTranslation("message.soul_contract.transferred");
    }
}
