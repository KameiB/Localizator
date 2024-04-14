package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.LocNameArguments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackLocNameMixin
        implements net.minecraftforge.common.capabilities.ICapabilitySerializable<NBTTagCompound>{
    @Shadow(remap = Production.inProduction)
    private NBTTagCompound stackTagCompound;

    @Shadow(remap = Production.inProduction)
    public NBTTagCompound getSubCompound(String key) {
        return stackTagCompound != null && stackTagCompound.hasKey(key, 10) ? stackTagCompound.getCompoundTag(key) : null;
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
    @SuppressWarnings("deprecation")
    private void Minecraft_ItemStack_getDisplayName_LocNameWithArgs(CallbackInfoReturnable<String> cir) {
        if (ForgeConfigHandler.serverConfig.minecraftLocNameOverName) {
            NBTTagCompound nbtTagCompound = getSubCompound("display");
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("LocName", Constants.NBT.TAG_STRING)) {
                    List<String> argsList = LocNameArguments.getLocNameArgs((ItemStack) ((Object) this));
                    if (argsList.isEmpty()) {
                        cir.setReturnValue(net.minecraft.util.text.translation.I18n.translateToLocal(nbtTagCompound.getString("LocName")));
                    } else {
                        cir.setReturnValue(net.minecraft.util.text.translation.I18n.translateToLocalFormatted(
                                nbtTagCompound.getString("LocName"),
                                argsList.toArray()));
                    }
                }
            }
        }
    }

    /**
     * <br>Return a translated name with fixed arguments (Optional).</br>
     * <br>This feature was added to support Recurrent Complex's chaotic names (up to 2 chaotic names per Artifact name) on custom loot.</br>
     * <br>Example of LocName lang key: lang.key=%s the legendary Staff</br>
     * <br>LocNameArgs tag must be a list of strings</br> 
     * <br>(it can be a number in the form of a String. Its contents can be set in code or config file)</br>
     */
    @Inject(
            method = "getDisplayName()Ljava/lang/String;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/text/translation/I18n;translateToLocal(Ljava/lang/String;)Ljava/lang/String;"
            ),
            cancellable = true,
            remap = Production.inProduction
    )
    // Line 613: return I18n.translateToLocal(nbttagcompound.getString("LocName"));
    @SuppressWarnings("deprecation")
    private void Minecraft_ItemStack_LocNameWithArgs(CallbackInfoReturnable<String> cir) {
        // All the safety checks must've been run earlier
        List<String> argsList = LocNameArguments.getLocNameArgs((ItemStack)((Object)this));
        if (!argsList.isEmpty()) {
            NBTTagCompound nbtTagCompound = getSubCompound("display");
            cir.setReturnValue(net.minecraft.util.text.translation.I18n.translateToLocalFormatted(
                    nbtTagCompound.getString("LocName"),
                    argsList.toArray()));
        }
    }
        
    /**
     * <br>Just after return, clear the LocName and LocNameArgs tags (if present).</br>
     * <br>This is meant to be called when the player renames an item that also has the LocName tag.</br>
     * <br>If we don't remove it now, the player won't be able to see the name they assigned.</br>
     */
    @Inject(
            method = "setStackDisplayName(Ljava/lang/String;)Lnet/minecraft/item/ItemStack;",
            at = @At("TAIL"),
            cancellable = false,
            remap = Production.inProduction
    )
    // Inject just before returning
    // Line 629: return this;
    private void Minecraft_ItemStack_setStackDisplayName_clearLocName(String displayName, CallbackInfoReturnable<ItemStack> cir) {
        NBTTagCompound nbtTagCompound = getSubCompound("display");
        if (nbtTagCompound != null) {
            nbtTagCompound.removeTag("LocNameArgs");
            nbtTagCompound.removeTag("LocName");
        }
    }
    
    @Inject(
            method = "clearCustomName()V",
            at = @At("HEAD"),
            cancellable = false,
            remap = Production.inProduction
    )
    // When the player clears an item's name who has LocName and Name NBT tags, clear both, as intended.
    // Line 632
    private void Minecraft_ItemStack_clearCustomName_clearLocName(CallbackInfo ci) {
        NBTTagCompound nbtTagCompound = getSubCompound("display");
        if (nbtTagCompound != null) {
            nbtTagCompound.removeTag("LocNameArgs");
            nbtTagCompound.removeTag("LocName");
        }
    }
    
    @ModifyVariable(
            method = "getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;",
            name = "s",
            at = @At(
                    value = "STORE",
                    ordinal = 1
            ),
            remap = Production.inProduction
    )
    // Only apply TextFormatting.ITALIC if the item has no LocName NBT tag
    // Line 666: s = TextFormatting.ITALIC + s;
    private String Minecraft_getTooltip_handleItalicName(String s) {
        if (localizator$hasTranslatableName() && ForgeConfigHandler.serverConfig.minecraftLocNameOverName) {
            if (s.startsWith(String.valueOf(TextFormatting.ITALIC))) {
                return s.replaceFirst(String.valueOf(TextFormatting.ITALIC), "");
            }
        }
        return s;
    }
    
    @Unique
    public boolean localizator$hasTranslatableName() {
        NBTTagCompound nbttagcompound = this.getSubCompound("display");
        return nbttagcompound != null && nbttagcompound.hasKey("LocName", Constants.NBT.TAG_STRING);
    } 
}