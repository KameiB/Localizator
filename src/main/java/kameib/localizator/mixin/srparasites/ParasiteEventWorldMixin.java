package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.util.ParasiteEventWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ParasiteEventWorld.class)
public abstract class ParasiteEventWorldMixin {
    @ModifyArg(
            method = "placeHeartInWorld(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 0,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Node Warning Message" with a lang key
    // Line 183: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigWorld.nodeWarning, 100);
    private static String SRParasites_ParasiteEventWorld_placeHeartInWorld_alertAllPlayerDim(String message) {
        return "message.srparasites.node_warning";
    }

    @ModifyArg(
            method = "placeColonyInWorld(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/util/ParasiteEventEntity;alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
                    ordinal = 0,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace config "Colonies Warning Message" with a lang key
    // Line 705: ParasiteEventEntity.alertAllPlayerDim(worldIn, SRPConfigWorld.colonyWarning, 101);
    private static String SRParasites_ParasiteEventWorld_placeColonyInWorld_alertAllPlayerDim(String message) {
        return "message.srparasites.colony_warning";
    }
}
