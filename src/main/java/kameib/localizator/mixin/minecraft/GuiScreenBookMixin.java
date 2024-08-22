package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(GuiScreenBook.class)
public abstract class GuiScreenBookMixin extends GuiScreen {
    @Inject(
            method = "<init>(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;Z)V",
            at = @At("TAIL"),
            remap = Production.inProduction
    )
    // Adds localized pages support for command-generated Writable Books
    // Line 58: public GuiScreenBook(EntityPlayer player, ItemStack book, boolean isUnsigned)
    private void Minecraft_GuiScreenBook_constructor_readLocPages(EntityPlayer player, ItemStack book, boolean isUnsigned, CallbackInfo ci) {
        if (book.hasTagCompound() && isUnsigned) {
            NBTTagCompound nbtTagCompound = book.getTagCompound();
            if (Constants.NBT.TAG_LIST == nbtTagCompound.getTagId("locPages") &&
                nbtTagCompound.getTagId("pages") == Constants.NBT.TAG_LIST) {
                NBTTagList pagesList = nbtTagCompound.getTagList("pages", Constants.NBT.TAG_STRING);
                NBTTagList locPagesList = nbtTagCompound.getTagList("locPages", Constants.NBT.TAG_STRING).copy();
                if (!locPagesList.isEmpty() && (locPagesList.tagCount() == pagesList.tagCount())) {                    
                    for (int i = 0; i < locPagesList.tagCount() ; i++) {
                        locPagesList.set(i, new NBTTagString(I18n.format(locPagesList.getStringTagAt(i))));
                    }
                    bookPages = locPagesList;
                }
            }
        }
    }
    
    @Shadow(remap = Production.inProduction)
    private NBTTagList bookPages;
}
