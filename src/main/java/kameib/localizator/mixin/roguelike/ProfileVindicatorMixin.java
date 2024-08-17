package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfileType;
import greymerk.roguelike.monster.profiles.ProfileVindicator;
import kameib.localizator.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileVindicator.class)
public abstract class ProfileVindicatorMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    // Enable Johnny spawn chance
    private void RLD_ProfileVindicator_apply_JohnnySpawnChance(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        if (ForgeConfigHandler.serverConfig.rldJohnnySpawnChance > 0.0001) { // Not willing to fight against some stupid Java "a zero is not really a zero" nonsense
            if (random.nextDouble() <= ForgeConfigHandler.serverConfig.rldJohnnySpawnChance) {
                cir.setReturnValue(MonsterProfileType.JOHNNY.apply(mob, level, difficulty, random));
            }
        }
    }
}
