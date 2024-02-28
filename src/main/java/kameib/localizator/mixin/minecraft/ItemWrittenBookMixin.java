package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemWrittenBook.class)
@SuppressWarnings("deprecation")
public abstract class ItemWrittenBookMixin
implements net.minecraftforge.common.capabilities.ICapabilitySerializable<NBTTagCompound> {
    
    @Inject(
            method = "validBookTagContents(Lnet/minecraft/nbt/NBTTagCompound;)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    // Add "locTitle" and "locAuthor" support for Written Books.
    // Line 34: public static boolean validBookTagContents(NBTTagCompound nbt)
    private static void Minecraft_ItemWrittenBook_validBookTagContents_locTitle(NBTTagCompound nbt, CallbackInfoReturnable<Boolean> cir) {
        if (!ItemWritableBook.isNBTValid(nbt)) {
            cir.setReturnValue(false);
        }
        else {
            if (nbt.hasKey("locTitle", Constants.NBT.TAG_STRING)) { // 8
                String s = nbt.getString("locTitle");
                if (I18n.canTranslate(s)) {
                    cir.setReturnValue(I18n.translateToLocal(s).length() <= 32 ?
                            (nbt.hasKey("author", Constants.NBT.TAG_STRING) || nbt.hasKey("locAuthor", Constants.NBT.TAG_STRING)) // 8, 8
                            : false);
                }
                else {
                    cir.setReturnValue(s.length() <= 32 ?
                            (nbt.hasKey("author", Constants.NBT.TAG_STRING) || nbt.hasKey("locAuthor", Constants.NBT.TAG_STRING)) // 8, 8
                            : false);
                }
            }
            else
            {
                if (!nbt.hasKey("title", Constants.NBT.TAG_STRING)) {// 8
                    cir.setReturnValue(false);
                }
                else {
                    String s = nbt.getString("title");
                    cir.setReturnValue(s != null && s.length() <= 32 ?
                            (nbt.hasKey("author", Constants.NBT.TAG_STRING) || nbt.hasKey("locAuthor", Constants.NBT.TAG_STRING)) // 8, 8
                            : false);
                }
            }
        }        
    }
    
    @Inject(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    // Line 57
    private void localizator_Minecraft_ItemWrittenBook_getItemStackDisplayName(ItemStack stack, CallbackInfoReturnable<String> cir) {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            String s = nbttagcompound.getString("locTitle");

            if (!StringUtils.isNullOrEmpty(s))
            {
                cir.setReturnValue(I18n.translateToLocal(s));
            }
        }
    }
    
    @Inject(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 74
    private void localizator_Minecraft_ItemWrittenBook_addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn, CallbackInfo ci) {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            String s = nbttagcompound.getString("locAuthor");

            if (!StringUtils.isNullOrEmpty(s))
            {
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("book.byAuthor", I18n.translateToLocal(s)));
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
                ci.cancel();
            }
        }
    }
}
