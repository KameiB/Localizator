package localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.consumable.ItemWinterGift;
import localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemWinterGift.class)
public abstract class ItemWinterGiftMixin extends ItemBase {
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
    private ITextComponent lycanites_ItemWinterGift_openGood(ITextComponent message) {
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
    // Line 85: player.sendMessage(new TextComponentString(message));
    private ITextComponent lycanites_ItemWinterGift_openBad(ITextComponent message) {
        return new TextComponentTranslation("item." + this.itemName + ".bad");
    }
    @ModifyArg(
            method = "openBad(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;setCustomNameTag(Ljava/lang/String;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Set a localizable name, so it can be translated by my other Mixin
    // Line 105: entityCreature.setCustomNameTag("Gooderness");
    private String lycanites_ItemWinterGift_open_gooderness(String name) {
        return "entity.gooderness.name";
    }
    @ModifyArg(
            method = "openBad(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;setCustomNameTag(Ljava/lang/String;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Set a localizable name, so it can be translated by my other Mixin
    // Line 107: entityCreature.setCustomNameTag("Rudolph");
    private String lycanites_ItemWinterGift_open_rudolph(String name) {
        return "entity.rufolph.name";
    }
    @ModifyArg(
            method = "openBad(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;setCustomNameTag(Ljava/lang/String;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Set a localizable name, so it can be translated by my other Mixin
    // Line 109: entityCreature.setCustomNameTag("Salty Tree");
    private String lycanites_ItemWinterGift_open_entSaltyTree(String name) {
        return "entity.saltytree.name";
    }
    @ModifyArg(
            method = "openBad(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;setCustomNameTag(Ljava/lang/String;)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Set a localizable name, so it can be translated by my other Mixin
    // Line 111: entityCreature.setCustomNameTag("Salty Tree");
    private String lycanites_ItemWinterGift_open_treantSaltyTree(String name) {
        return "entity.saltytree.name";
    }
    @ModifyArg(
            method = "openBad(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;setCustomNameTag(Ljava/lang/String;)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Set a localizable name, so it can be translated by my other Mixin
    // Line 113: entityCreature.setCustomNameTag("Satan Claws");
    private String lycanites_ItemWinterGift_open_satanClaws(String name) {
        return "entity.satanclaws.name";
    }
    @ModifyArg(
            method = "openBad(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;setCustomNameTag(Ljava/lang/String;)V",
                    ordinal = 5,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Set a localizable name, so it can be translated by my other Mixin
    // Line 115: entityCreature.setCustomNameTag("Krampus");
    private String lycanites_ItemWinterGift_open_krampus(String name) {
        return "entity.krampus.name";
    }
}
