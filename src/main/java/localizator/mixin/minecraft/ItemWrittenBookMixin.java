package localizator.mixin.minecraft;

import localizator.data.Production;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemWrittenBook.class)
public abstract class ItemWrittenBookMixin
implements net.minecraftforge.common.capabilities.ICapabilitySerializable<NBTTagCompound> {
    /**
     * @author KameiB
     * @reason Adds "locTitle" and "locAuthor" support for Written Books.
     */
    @Overwrite(remap = Production.inProduction) // FALSE ONLY WHEN DEBUGGING!
    // Line 34
    public static boolean validBookTagContents(NBTTagCompound nbt) {
        if (!ItemWritableBook.isNBTValid(nbt))
        {
            return false;
        }
        if (nbt.hasKey("locTitle", 8)) {
            String s = nbt.getString("locTitle");
            if (s == null) return false;
            if (I18n.canTranslate(s)) {
                return I18n.translateToLocal(s).length() <= 32 ?
                    (nbt.hasKey("author", 8) || nbt.hasKey("locAuthor", 8))
                        : false;
            }
            else {
                return s.length() <= 32 ?
                    (nbt.hasKey("author", 8) || nbt.hasKey("locAuthor", 8))
                        : false;
            }
        }
        else if (!nbt.hasKey("title", 8))
        {
            return false;
        }
        else
        {
            String s = nbt.getString("title");
            return s != null && s.length() <= 32 ?
                    (nbt.hasKey("author", 8) || nbt.hasKey("locAuthor", 8))
                    : false;
        }
    }

    @Inject(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true
    )
    // Line 57
    private void localizator_minecraftItemWrittenBook_getItemStackDisplayName(ItemStack stack, CallbackInfoReturnable<String> cir) {
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
            cancellable = true
    )
    @SideOnly(Side.CLIENT)
    // Line 74
    private void localizator_minecraftItemWrittenBook_addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn, CallbackInfo ci) {
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
