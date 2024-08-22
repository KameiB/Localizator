package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponToolArmorBase;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(WeaponToolArmorBase.class)
public abstract class WeaponToolArmorBaseMixin {
    @Redirect(
            method = "func_77624_a(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At( value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Make "Current Adaptation:" text translatable
    // Line 105: tooltip.add(TextFormatting.DARK_PURPLE + "Current Adaptation:");
    private boolean localizator_SRParasites_WeaponToolArmorBase_addInformation_tooltipAdaptation(List<String> tooltip, Object e) {
        return tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tooltip.srparasites.armor.adaptation"));
    }

    @ModifyArg(
            method = "func_77624_a(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Make resistances texts translatable
    // Line 113: tooltip.add(TextFormatting.YELLOW + "-> " + (String)resistanceS.get(i));
    private Object localizator_SRParasites_WeaponToolArmorBase_addInformation_tooltipResistance(Object resistanceObj) {
        if (ForgeConfigHandler.clientConfig.srparasitesResistances) {
            String resistanceFull = ((String)resistanceObj);
            String resistanceRaw = resistanceFull.substring(resistanceFull.indexOf("-> ") + 3);            
            String resistanceTranslated;
            if (I18n.hasKey(resistanceRaw)) { // Non-entity damage
                resistanceTranslated = I18n.format(resistanceRaw);
            }
            else { // Entity damage OR damage not included in Lang file
                resistanceTranslated = EntityList.getTranslationName(new ResourceLocation(resistanceRaw));
                if (resistanceTranslated == null) {
                    // Non registered entity? or Damage not included in Lang file. Show it as-is.
                    // Include it in your Lang file, so it shows translated.
                    resistanceTranslated = resistanceRaw;
                }
                else {
                    resistanceTranslated = I18n.format( "entity." + resistanceTranslated + ".name");
                }
            }            
            resistanceObj = TextFormatting.YELLOW + "-> " + resistanceTranslated;
        }
        return resistanceObj;
    }
    
    @ModifyArg(
            method = "func_77624_a(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2),            
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Make " reduction: " text translatable
    // Line 114: tooltip.add(TextFormatting.YELLOW + " reduction: " + formatResult + "%");
    private Object localizator_SRParasites_WeaponToolArmorBase_addInformation_tooltipReduction(Object reductionObj) {
        return ((String)reductionObj).replace(" reduction:", TextFormatting.GREEN + I18n.format("tooltip.srparasites.armor.reduction"));        
    }
}
