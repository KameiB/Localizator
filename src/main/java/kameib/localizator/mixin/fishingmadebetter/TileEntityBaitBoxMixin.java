package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.theawesomegem.fishingmadebetter.common.block.tileentity.TileEntityBaitBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityBaitBox.class)
public abstract class TileEntityBaitBoxMixin extends TileEntity implements ITickable {
    @Redirect(
            method = "handleRightClick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getDisplayName()Ljava/lang/String;",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Save a lang key instead of a hardcoded English (in server case) name
    // Line 101: String name = stack.getDisplayName();
    private String FMB_TileEntityBaitBox_handleRightClick_getBaitUnlocalizedName(ItemStack stack) {
        String baitLangKey = FMB_BetterFishUtil.getBaitLangKey(stack.getItem().getRegistryName().toString(),
                stack.getMetadata());
        
        return baitLangKey != null ? baitLangKey : stack.getDisplayName();
    }
        
    @ModifyArg(
            method = "handleRightClick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Send the localized bait names
    // Line 117: player.sendMessage(new TextComponentString(String.format("%s: %d", baitEntry.getKey(), baitEntry.getValue())));
    private ITextComponent FMB_TileEntityBaitBox_handleRightClick_sendLocalizedMessage(ITextComponent text) {
        // parts[0] = Bait Lang Key.  parts[1] = ": <Qty>"        
        String[] parts = text.getUnformattedComponentText().split("(?=: )");
        return new TextComponentTranslation((parts[0])).setStyle(new Style().setBold(true))
                .appendText(parts[1]);
    }
    
}
