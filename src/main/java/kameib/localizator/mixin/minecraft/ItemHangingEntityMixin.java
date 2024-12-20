package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.ItemStackUtil;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemHangingEntity.class)
public abstract class ItemHangingEntityMixin {
    protected ItemHangingEntityMixin(Class<? extends EntityHanging> hangingEntityClass) {
        this.hangingEntityClass = hangingEntityClass;
    }

    @Inject(
            method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemHangingEntity;createEntity(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/entity/EntityHanging;",
                    remap = Production.inProduction
            ),
            cancellable = true,
            remap = Production.inProduction
    )
    // Cancel the placement of a custom Painting on a wall
    // Before Line 32: EntityHanging entityhanging = this.createEntity(worldIn, blockpos, facing);
    private void Minecraft_ItemHangingEntity_onItemUse_cancelPaintingPlacement(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir) {
        if (!ForgeConfigHandler.serverConfig.minecraftPreventNoveltyPaintingsPlacement) {
            return;
        }
        if (hangingEntityClass != EntityPainting.class) {
            return;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        if (ItemStackUtil.isNovelty(stack)) {
            player.sendStatusMessage(new TextComponentTranslation("message.localizator.dont_place_novelty.painting"),true);
            cir.setReturnValue(EnumActionResult.FAIL);
        }
    }
    
    @Mutable
    @Final
    @Shadow(remap = Production.inProduction)
    private final Class <? extends EntityHanging> hangingEntityClass;
}
