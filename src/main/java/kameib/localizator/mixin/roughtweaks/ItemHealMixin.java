package kameib.localizator.mixin.roughtweaks;

import lellson.roughTweaks.RoughTweaks;
import lellson.roughTweaks.items.ItemHeal;
import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemHeal.class)
public abstract class ItemHealMixin extends Item {
    /**
     * @author KameiB
     * @reason RoughTweaks item Translation Keys collide with FirstAid ones.
     */
    @Inject(
            method = "<init>(Ljava/lang/String;IIFLnet/minecraft/potion/PotionEffect;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "TAIL"),
            remap = false
    )
    // Line 38: this.setTranslationKey(name);
    private void localizator_RoughTweaks_ItemHeal_setTranslationKey(String name, int useCount, int healRate, float healAmount, PotionEffect effect, ItemStack returnStack, CallbackInfo ci) {
        this.setTranslationKey(RoughTweaks.MODID + "." + name);
    }

    @Mutable
    @Final
    @Shadow(remap = false) private float healAmount;
    /**
     * @author KameiB
     * @reason Localize item Heal amount. 
     * Optional: Remove the need of pressing Shift to show the item's Heal amount.
     */
    @Inject(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 91
    private void RoughTweaks_ItemHeal_addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag, CallbackInfo ci) {
        if (GuiScreen.isShiftKeyDown() || ForgeConfigHandler.clientConfig.roughtweaksTooltip) {
            float hearts = this.healAmount / 2.0F;
            if ((double)hearts % 1.0 == 0.0) {
                tooltip.add(TextFormatting.BLUE + I18n.format("tooltip.roughtweaks.itemheal.heal_amount", (int)hearts));
            } else {
                tooltip.add(TextFormatting.BLUE + I18n.format("tooltip.roughtweaks.itemheal.heal_amount", hearts));
            }
        }
        ci.cancel();
    }
}
