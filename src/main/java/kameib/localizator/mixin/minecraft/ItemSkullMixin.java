package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.ItemStackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSkull.class)
public abstract class ItemSkullMixin {
    @Inject(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z",
                    remap = Production.inProduction
            ),
            cancellable = true,
            remap = Production.inProduction
    )
    // Prevent the placement of a custom Skull
    // Before Line 75: worldIn.setBlockState(pos, Blocks.SKULL.getDefaultState().withProperty(BlockSkull.FACING, facing), 11);
    private void Minecraft_ItemSkull_onItemUse_setBlockState_cancelSkullPlacement(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir) {
        if (!ForgeConfigHandler.serverConfig.minecraftPreventNoveltySkullsPlacement) {
            return;
        }
        ItemStack stack = player.getHeldItem(hand);
        
        if (ItemStackUtil.isNovelty(stack)) {
            player.sendStatusMessage(new TextComponentTranslation("message.localizator.dont_place_novelty.skull"),true);
            cir.setReturnValue(EnumActionResult.FAIL);
        }
    }
}
