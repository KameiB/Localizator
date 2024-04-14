package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.ItemStackUtil;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SideOnly(Side.CLIENT)
@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin {
    @ModifyVariable(
            method = "renderSelectedItem(Lnet/minecraft/client/gui/ScaledResolution;)V",
            name = "s",
            at = @At(
                    value = "STORE",
                    ordinal = 1
            ),
            remap = Production.inProduction
    )
    // Only apply TextFormatting.ITALIC if the item has no LocName NBT tag
    // Line 656: s = TextFormatting.ITALIC + s;
    private String Minecraft_GuiIngame_renderSelectedItem_handleItalicName(String s) {
        if (ItemStackUtil.hasTranslatableName(highlightingItemStack) && ForgeConfigHandler.serverConfig.minecraftLocNameOverName) {
            if (s.startsWith(String.valueOf(TextFormatting.ITALIC))) {
                return s.replaceFirst(String.valueOf(TextFormatting.ITALIC), "");
            }
        }
        return s;
    }
        
    @Shadow
    protected ItemStack highlightingItemStack;
}
