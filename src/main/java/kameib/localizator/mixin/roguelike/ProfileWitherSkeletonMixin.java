package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileWitherSkeleton;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.util.RLD_ExtraColor;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileWitherSkeleton.class)
public abstract class ProfileWitherSkeletonMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Give Novelties
    private void RLD_ProfileWitherSkeleton_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        // This profile is called in a 1/10 chance. If we multiply it by this 1/20 chance, it becomes a 1/200 chance.
        if (level == 3 && random.nextDouble() < 0.05) {
            localizator$profileEnikoEarring(mob, level, difficulty, random);
        }
        else {
            // 1/200 chance, spawned from the Blaze Room spawner (Level 4).
            if (level == 4 && random.nextDouble() < 0.005) {
                mob.equipMainhand(ItemNovelty.nullPointer());
                mob.equipOffhand(BlockType.AIR.asItem().asStack());
            }
        }
    }

    @Unique
    private void localizator$profileEnikoEarring(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.enikosEarring());

        // Skin: https://namemc.com/profile/Eniko.1
        // Brown boots (English Walnut)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.ENGLISH_WALNUT).asStack();
        mob.equip(Slot.FEET, boots);

        // Dark brown Leggings (Zeus)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.ZEUS).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Light brown chestplate (Armadillo)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.ARMADILLO).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Dark red hair (Temptress)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.TEMPTRESS).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
}
