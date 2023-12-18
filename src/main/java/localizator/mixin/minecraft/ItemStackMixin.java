package localizator.mixin.minecraft;

import localizator.data.Production;
import localizator.handlers.ForgeConfigHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
        implements net.minecraftforge.common.capabilities.ICapabilitySerializable<NBTTagCompound>{
    @Shadow(remap = Production.inProduction)
    private NBTTagCompound stackTagCompound;    
    @Unique
    private boolean localizator$hasLocLore;
    @Unique
    private List<String> localizator$tooltip ;
    
    @Redirect(
            method = "getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;",
            at = @At( value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0),
            remap = Production.inProduction
    )
    // Get a copy of the local variable "list"
    // Line 697: list.add(s);
    private boolean Minecraft_ItemStack_captureTooltipList(List<String> list, Object text) {
        localizator$tooltip = list;
        return list.add((String)text);
    }
    
    @Inject(
            method = "getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NBTTagCompound;getTagId(Ljava/lang/String;)B"
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Inject just before checking if item has the "Lore" NBT tag
    // Line 746: if (nbttagcompound1.getTagId("Lore") == 9)
    private void Minecraft_ItemStack_beforeLore(EntityPlayer playerIn, ITooltipFlag advanced, CallbackInfoReturnable<List<String>> cir) {
        localizator$hasLocLore = false;
        NBTTagCompound locloreTag = this.stackTagCompound.getCompoundTag("display");
        if (locloreTag.getTagId("LocLore") == 9) {
            NBTTagList nbttagList4 = locloreTag.getTagList("LocLore", 8);
            if (!nbttagList4.isEmpty()) {
                localizator$hasLocLore = true;                
                for (int l1 = 0; l1 < nbttagList4.tagCount(); ++l1) {
                    // Add the "LocLore" contents to the tooltip's current position
                    localizator$tooltip.add(TextFormatting.WHITE + I18n.format(nbttagList4.getStringTagAt(l1)));
                }
            }
        }
    }
    
    @Redirect(
            method = "getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 4
            ),
            remap = Production.inProduction
    )
    // If item has the "LocLore" NBT tag and "Hide Lore" option is enabled, don't add the "Lore" contents.
    // If item has the "LocLore" NBT tag but "Hide Lore" option is disabled, add the "Lore" contents (show both lore strings).
    // If item doesn't have the "LocLore" NBT tag, add the "Lore" contents regardless of the "Hide Lore" config option. (Vanilla behaviour)
    // Line 754: list.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC + nbttaglist3.getStringTagAt(l1));
    private boolean Minecraft_ItemStack_insteadOfLore(List<String> list, Object text) {
        if (!(localizator$hasLocLore && ForgeConfigHandler.clientConfig.minecraftHideLore)) {
            return list.add((String)text);
        }
        return false;
    }
}