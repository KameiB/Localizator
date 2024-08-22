package kameib.localizator.mixin.variedcommodities;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.vc.client.gui.GuiBook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(GuiBook.class)
public abstract class GuiBookMixin {
    @Inject(
            method = "<init>(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;III)V",
            at = @At("TAIL"),
            remap = false
    )
    // Adds localized pages support for command-generated Writable Books
    // Line 59: public GuiBook(EntityPlayer par1EntityPlayer, ItemStack item, int x, int y, int z)
    private void VariedCommodities_GuiBook_constructor_readLocPages(EntityPlayer par1EntityPlayer, ItemStack item, int x, int y, int z, CallbackInfo ci) {
        if (bookObj.hasTagCompound() && bookIsUnsigned) {
            NBTTagCompound nbtTagCompound = bookObj.getTagCompound();
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
    
    @Shadow(remap = false)
    private ItemStack bookObj;
    @Shadow(remap = false)
    private NBTTagList bookPages;
    @Final
    @Shadow(remap = false)
    private boolean bookIsUnsigned;
}
