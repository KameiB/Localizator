package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.*;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileBaby;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.util.RLD_ExtraColor;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static greymerk.roguelike.treasure.loot.PotionMixture.getTequila;

@Mixin(ProfileBaby.class)
public abstract class ProfileBabyMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Chance to spawn a Baby Zombie with a sneaky shell
    private void RLD_ProfileBaby_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        // From ProfileZombie: 1/40 * this roll 1/4 = 1/160
        if (random.nextDouble() < 0.25) {
            localizator$profileKameiB(mob, level, difficulty, random);
        }
    }
    
    @Unique
    private void localizator$profileKameiB(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob);
        if (random.nextBoolean()) {
            RldItemStack rod = ToolType.FISHING_ROD.asItem().plzEnchantAtLevel(level).asStack();
            mob.equipMainhand(rod);
        }
        else {
            mob.equipMainhand(getTequila(random));
        }

        // Skin: https://namemc.com/profile/KameiB.1
        // Green boots (Fern Green)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.FERN_GREEN).asStack();
        mob.equip(Slot.FEET, boots);

        // White Leggings (Mercury)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.MERCURY).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Green chest (Fruit Salad)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.FRUIT_SALAD).asStack();
        mob.equip(Slot.CHEST, chestplate);

        mob.equip(Slot.HEAD, ItemNovelty.kameibShell());
    }
}
