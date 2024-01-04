package localizator.mixin.minecraft;

import localizator.data.Production;
import localizator.handlers.ForgeConfigHandler;
import localizator.util.LocNameArguments;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

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
public abstract class ItemStackMixin
        implements net.minecraftforge.common.capabilities.ICapabilitySerializable<NBTTagCompound>{
    @Shadow(remap = Production.inProduction)
    private NBTTagCompound stackTagCompound;

    @Shadow(remap = Production.inProduction)
    public NBTTagCompound getSubCompound(String key) {
        return stackTagCompound != null && stackTagCompound.hasKey(key, 10) ? stackTagCompound.getCompoundTag(key) : null;
    }

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
                            localizator$tooltip.add(TextFormatting.WHITE + I18n.format(locloreList.getStringTagAt(l1),locloreArgList.getStringTagAt(l1)));
                        }
                    }
                    else {
                        for (int l1 = 0; l1 < locloreList.tagCount(); ++l1) {
                            // Add the "LocLore" translated contents to the tooltip's current position
                            localizator$tooltip.add(TextFormatting.WHITE + I18n.format(locloreList.getStringTagAt(l1)));
                        }
                    }
                }
                else {
                    for (int l1 = 0; l1 < locloreList.tagCount(); ++l1) {
                        // Add the "LocLore" translated contents to the tooltip's current position
                        localizator$tooltip.add(TextFormatting.WHITE + I18n.format(locloreList.getStringTagAt(l1)));
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
    // Line 754: list.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC + nbttaglist3.getStringTagAt(l1));
    private boolean Minecraft_ItemStack_insteadOfLore(List<String> list, Object text) {
        if (localizator$hasLocLore && ForgeConfigHandler.clientConfig.minecraftHideLore) {
            return false;
        }
        else {
            return list.add((String) text);
        }
    }

    /**
     * <br>Return a translated name with fixed arguments (Optional) before checking for the Name tag, so an item can have both (when created).</br>  
     * <br>Why both? so if the player stops using Localizator, they will still see the name contained in the Name tag before the LocName.</br>
     * <br>This feature was added to support Recurrent Complex's chaotic names (up to 2 chaotic names per Artifact name) on custom loot.</br> 
     * <br>Example of LocName lang key: lang.key=%s the legendary Staff</br>
     * <br>LocNameArgs tag must be a list of strings 
     * (it can be a number in the form of a String. Its contents can be set in code or config file)
     * </br>
     **/
    @Inject(
            method = "getDisplayName()Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    // Inject just before checking if item has the "Name" NBT tag
    // Line 606: if (nbttagcompound.hasKey("Name", 8))
    private void Minecraft_ItemStack_getDisplayName_LocNameWithArgs(CallbackInfoReturnable<String> cir) {
        NBTTagCompound nbtTagCompound = getSubCompound("display");
        if (nbtTagCompound != null) {
            if (nbtTagCompound.hasKey("LocName", Constants.NBT.TAG_STRING)) {
                List<String> argsList = LocNameArguments.getLocNameArgs((ItemStack) ((Object) this));
                if (argsList.isEmpty()) {
                    cir.setReturnValue(net.minecraft.util.text.translation.I18n.translateToLocal(nbtTagCompound.getString("LocName")));                    
                }
                else {
                    cir.setReturnValue(net.minecraft.util.text.translation.I18n.translateToLocalFormatted(
                            nbtTagCompound.getString("LocName"),
                            argsList.toArray()));
                }
            }
        }
    }

    /**
     * <br>Just after return, clear the LocName and LocNameArgs tags (if present).</br>
     * <br>This is meant to be called when the player renames an item that has the LocName tag.</br>
     * <br>If we don't remove it now, the player won't be able to see the name they assigned.</br>
     * <p>Yes, if a player renames an item and then renames it again with an empty name, the item will be left with its original name.</p>
     * <b>It's a feature, not a bug ok? xc</b>
     */
    @Inject(
            method = "setStackDisplayName(Ljava/lang/String;)Lnet/minecraft/item/ItemStack;",
            at = @At("TAIL"),
            cancellable = false,
            remap = Production.inProduction
    )
    // Line 629: return this;
    private void Minecraft_ItemStack_setStackDisplayName_clearLocName(String displayName, CallbackInfoReturnable<ItemStack> cir) {
        NBTTagCompound nbtTagCompound = getSubCompound("display");
        if (nbtTagCompound != null) {
            nbtTagCompound.removeTag("LocNameArgs");
            nbtTagCompound.removeTag("LocName");
        }
    }
}