package kameib.localizator.mixin.minecraft;

import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.ItemStackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SideOnly(Side.CLIENT)
@Mixin(GuiIngameForge.class)
public abstract class GuiIngameForgeMixin extends GuiIngame {
    public GuiIngameForgeMixin(Minecraft mcIn) {
        super(mcIn);
    }

    @ModifyVariable(
            method = "renderToolHighlight(Lnet/minecraft/client/gui/ScaledResolution;)V",
            name = "name",
            at = @At(
                    value = "STORE",
                    ordinal = 1
            ),
            remap = false
    )
    // Only apply TextFormatting.ITALIC if the item has no LocName NBT tag
    // Line 650: name = TextFormatting.ITALIC + name;
    private String MinecraftForge_GuiIngameForge_renderTooltipHighlight_handleItalicName(String name) {
        if (ItemStackUtil.hasTranslatableName(this.highlightingItemStack) && ForgeConfigHandler.serverConfig.minecraftLocNameOverName) {
            if (name.startsWith(String.valueOf(TextFormatting.ITALIC))) {
                return name.replaceFirst(String.valueOf(TextFormatting.ITALIC), "");
            }
        }
        return name;
    }
}
