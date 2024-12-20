package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.ItemStackUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlock.class)
public abstract class ItemBlockMixin {
    @Inject(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemBlock;getMetadata(I)I",
                    remap = Production.inProduction
            ),
            remap = Production.inProduction,
            cancellable = true
    )
    // Prevent the placement of a custom Block
    // Line 51: int i = this.getMetadata(itemstack.getMetadata());
    private void Minecraft_ItemBlock_onItemUse_beforeGetMetadata_cancelBlockPlacement(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir) {
        if (!ForgeConfigHandler.serverConfig.minecraftPreventNoveltyBlocksPlacement) {
            return;
        }
        ItemStack stack = player.getHeldItem(hand);
        
        if (ItemStackUtil.isNovelty(stack)) {
            Block block = ForgeRegistries.BLOCKS.getValue(stack.getItem().getRegistryName());
            if (ForgeConfigHandler.getNoveltyBlocksWhitelist().contains(block)) {
                return;
            }
            player.sendStatusMessage(new TextComponentTranslation("message.localizator.dont_place_novelty.block"),true);
            cir.setReturnValue(EnumActionResult.FAIL);
        }
    }
}
