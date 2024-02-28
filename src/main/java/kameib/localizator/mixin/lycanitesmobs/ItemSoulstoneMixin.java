package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.item.special.ItemSoulstone;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemSoulstone.class)
public abstract class ItemSoulstoneMixin {
    @Redirect(
            method = "onItemRightClickOnEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/client/localisation/LanguageManager;translate(Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Don't translate here
    // All ocurrences
    private String Lycanites_ItemSoulstone_dontTranslate(String key) {
        return key;
    }
    
    @ModifyArg(
            method = "onItemRightClickOnEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace the TextcomponentString with a TextComponentTranslation
    // Line 39: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soulstone.invalid")));
    private ITextComponent Lycanites_ItemSoulstone_onItemRightClickOnEntity_message1(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }

    @ModifyArg(
            method = "onItemRightClickOnEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace the TextcomponentString with a TextComponentTranslation
    // Line 83: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soulstone.untamed")));
    private ITextComponent Lycanites_ItemSoulstone_onItemRightClickOnEntity_message2(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }

    @ModifyArg(
            method = "onItemRightClickOnEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace the TextcomponentString with a TextComponentTranslation
    // Line 49: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soulstone.exists")));
    private ITextComponent Lycanites_ItemSoulstone_onItemRightClickOnEntity_message3(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }
    
    @Redirect(
            method = "onItemRightClickOnEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/info/CreatureInfo;getTitle()Ljava/lang/String;",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Capture CreatureInfo name
    // Line 67: message = message.replace("%creature%", creatureInfo.getTitle());
    private String Lycanites_ItemSoulstone_onItemRightClickOnEntity_captureCreatureName(CreatureInfo instance) {
        localizator$myPetName = "entity." + instance.getLocalisationKey() + ".name";
        return instance.getTitle();
    }
    @ModifyArg(
            method = "onItemRightClickOnEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace the TextcomponentString with a TextComponentTranslation
    // Line 49: player.sendMessage(new TextComponentString(LanguageManager.translate("message.soulstone.exists")));
    private ITextComponent Lycanites_ItemSoulstone_onItemRightClickOnEntity_message4(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText() + "_",
                new TextComponentTranslation(localizator$myPetName));
    }
    
    @Unique
    private String localizator$myPetName;
}
