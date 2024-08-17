package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileRleahy;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileRleahy.class)
public abstract class ProfileRleahyMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("HEAD"),
            remap = false
    )
    // Take away his armor so he can be freshly equipped
    private void RLD_ProfileRleahy_apply_freshForArmor(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        RLD_MobUtil.supponpon(mob);
    }
}
