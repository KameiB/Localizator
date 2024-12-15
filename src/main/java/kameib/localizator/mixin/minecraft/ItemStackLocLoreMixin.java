package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.LocLoreUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.Constants;
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
public abstract class ItemStackLocLoreMixin
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
    @SideOnly(Side.CLIENT)
    // Get a copy of the local variable "list"
    // Line 697: list.add(s);
    private boolean Minecraft_ItemStack_captureTooltipList(List<String> list, Object text) {
        localizator$tooltip = list;
        return list.add((String)text);
    }

    /**
     * <br>Inject just before checking if the item has the "Lore" NBT tag.</br>
     * <br>Add LocLore contents to the tooltip before Lore contents get added.</br>
     * <br>(Optional) You can also send 1 string argument to each LocLore line!</br>
     * <br>Note: The LocLoreArg list must be the same size as LocLore list!</br>
     * <br>Unused LocLoreArg elements must be empty Strings, NOT null!</br>
     */
    @Inject(
            method = "getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NBTTagCompound;getTagId(Ljava/lang/String;)B"
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 746: if (nbttagcompound1.getTagId("Lore") == 9)
    private void Minecraft_ItemStack_beforeLore(EntityPlayer playerIn, ITooltipFlag advanced, CallbackInfoReturnable<List<String>> cir) {
        localizator$hasLocLore = false;
        NBTTagCompound displayTag = stackTagCompound.getCompoundTag("display");
        if (displayTag.getTagId("LocLore") == Constants.NBT.TAG_LIST) { // 9
            NBTTagList locloreList = displayTag.getTagList("LocLore", Constants.NBT.TAG_STRING); // 8
            if (!locloreList.isEmpty()) {
                localizator$hasLocLore = true;
                // New capability: send 1 argument to each LocLore line!
                if (displayTag.getTagId("LocLoreArg") == Constants.NBT.TAG_LIST) { // 9
                    NBTTagList locloreArgList = displayTag.getTagList("LocLoreArg", Constants.NBT.TAG_STRING); // 8
                    // The "LocLoreArg" list needs to be the same size as the "LocLore" list
                    if (!locloreArgList.isEmpty() && (locloreArgList.tagCount() == locloreList.tagCount())) {
                        for (int l1 = 0; l1 < locloreList.tagCount(); ++l1) {
                            // Add the "LocLore" with the corresponding argument (String) for the lang key.
                            // Lang key needs to have 1 and only 1 %s in it to show as expected! 
                            localizator$tooltip.add(LocLoreUtil.getCustomColor() + LocLoreUtil.getCustomFormat() + I18n.format(locloreList.getStringTagAt(l1),locloreArgList.getStringTagAt(l1)));
                        }
                    }
                    else {
                        for (int l1 = 0; l1 < locloreList.tagCount(); ++l1) {
                            // Add the "LocLore" translated contents to the tooltip's current position
                            localizator$tooltip.add(LocLoreUtil.getCustomColor() + LocLoreUtil.getCustomFormat() + I18n.format(locloreList.getStringTagAt(l1)));
                        }
                    }
                }
                else {
                    for (int l1 = 0; l1 < locloreList.tagCount(); ++l1) {
                        // Add the "LocLore" translated contents to the tooltip's current position
                        localizator$tooltip.add(LocLoreUtil.getCustomColor() + LocLoreUtil.getCustomFormat() + I18n.format(locloreList.getStringTagAt(l1)));
                    }
                }
            }
        }
    }

    /**
     * <br>If item has the "LocLore" NBT tag and "Hide Lore" option is enabled, don't add the "Lore" contents.</br>
     * <br>If item has the "LocLore" NBT tag but "Hide Lore" option is disabled, add the "Lore" contents (show both lore strings).</br>
     * <br>If item doesn't have the "LocLore" NBT tag, add the "Lore" contents regardless of the "Hide Lore" config option. (Vanilla behaviour)</br>
     */
    @Redirect(
            method = "getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 4
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 754: list.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC + nbttaglist3.getStringTagAt(l1));
    private boolean Minecraft_ItemStack_insteadOfLore(List<String> list, Object text) {
        if (localizator$hasLocLore && ForgeConfigHandler.clientConfig.minecraftHideLore) {
            return false;
        }
        else {
            return list.add((String) text);
        }
    }
}