package localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.consumable.ItemHalloweenTreat;
import localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
    // Line 107: entityCreature.setCustomNameTag("Twisted Ent");
    private String lycanites_ItemHalloweenTreat_openBad_twistedEnt(String name) {
        return "entity.twistedent.name";
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
    // Line 109: entityCreature.setCustomNameTag("Wicked Treant");
    private String lycanites_ItemHalloweenTreat_openBad_wickedTreant(String name) {
        return "entity.wickedtreant.name";
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
    // Line 111: entityCreature.setCustomNameTag("Vampire Bat");
    private String lycanites_ItemHalloweenTreat_openBad_vampireBat(String name) {
        return "entity.vampirebat.name";
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
    // Line 113: entityCreature.setCustomNameTag("Shadow Clown");
    private String lycanites_ItemHalloweenTreat_openBad_shadowClown(String name) {
        return "entity.shadowclown.name";
    }
}
