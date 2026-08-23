package kameib.localizator.mixin.campfire;

import git.jbredwards.campfire.common.item.ItemCampfire;
import kameib.localizator.data.Production;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemCampfire.class)
public abstract class ItemCampfireMixin {
    @Redirect(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getDisplayName()Ljava/lang/String;",
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Use translateToLocalFormatted and pass the type lang key as a parameter instead of hardcoding "<type> Campfire"
    // Line 26: return I18n.canTranslate(specialCase) ? I18n.translateToLocal(specialCase) : type.getDisplayName().replaceFirst(I18n.translateToLocal(this.getRegexTarget()), I18n.translateToLocal(this.getRegexReplacement()));
    @SuppressWarnings("deprecation")
    private String Campfire_ItemCampfire_getItemStackDisplayName_translatedName(ItemStack itemStack) {
        String typeTranslated = I18n.translateToLocal(itemStack.getTranslationKey() + ".name");
        
        return I18n.translateToLocalFormatted("tile.campfire.campfire_of.name", typeTranslated);
    }
    
    @Redirect(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Cancel any regex stuff and just return what I already prepared
    // Line 26: return I18n.canTranslate(specialCase) ? I18n.translateToLocal(specialCase) : type.getDisplayName().replaceFirst(I18n.translateToLocal(this.getRegexTarget()), I18n.translateToLocal(this.getRegexReplacement()));
    private String Campfire_ItemCampfire_getItemStackDisplayName_replaceFirst_cancel(String translatedName, String regexTarget, String regexReplacement) {
        return translatedName;
    }
}
