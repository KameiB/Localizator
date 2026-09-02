package kameib.localizator.mixin.armorunder;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.jwaresoftware.mcmods.armorunder.runtime.LinedArmorTooltip;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LinedArmorTooltip.class)
public abstract class LinedArmorTooltipMixin {
    @ModifyConstant(
            method = "addPlainLinedArmorTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            constant = @Constant(stringValue = "MILD"),
            remap = false
    )
    // Replace the hardcoded "MILD" with a translated lang key
    // Line 26: String what = defn.value == 0 ? "MILD" : (defn.value < 0 ? "COOL" : "WARM");
    @SideOnly(Side.CLIENT)
    private String localizator_ArmorUnder_LinedArmorTooltip_addPlainLinedArmorTooltip_replaceMILD(String constant) {
        return I18n.format("tooltip.auw.xlining.ozzy_mild");
    }

    @ModifyConstant(
            method = "addPlainLinedArmorTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            constant = @Constant(stringValue = "COOL"),
            remap = false
    )
    // Replace the hardcoded "COOL" with a translated lang key
    // Line 26: String what = defn.value == 0 ? "MILD" : (defn.value < 0 ? "COOL" : "WARM");
    @SideOnly(Side.CLIENT)
    private String localizator_ArmorUnder_LinedArmorTooltip_addPlainLinedArmorTooltip_replaceCOOL(String constant) {
        return I18n.format("tooltip.auw.xlining.ozzy_cool");
    }

    @ModifyConstant(
            method = "addPlainLinedArmorTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            constant = @Constant(stringValue = "WARM"),
            remap = false
    )
    // Replace the hardcoded "WARM" with a translated lang key
    // Line 26: String what = defn.value == 0 ? "MILD" : (defn.value < 0 ? "COOL" : "WARM");
    @SideOnly(Side.CLIENT)
    private String localizator_ArmorUnder_LinedArmorTooltip_addPlainLinedArmorTooltip_replaceWARM(String constant) {
        return I18n.format("tooltip.auw.xlining.ozzy_warm");
    }
}
