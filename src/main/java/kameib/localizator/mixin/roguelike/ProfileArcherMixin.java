package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileArcher;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.util.RLD_ExtraColor;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileArcher.class)
public abstract class ProfileArcherMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Give Novelty
    private void RLD_ProfileArcher_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        if (level == 3 && random.nextInt(100) == 0) {
            localizator$profileDinnerbone(mob, level, difficulty, random);
        }
    }
    
    @Unique
    private void localizator$profileDinnerbone(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.oldDinnerbone());

        // Skin: https://namemc.com/search?q=Dinnerbone
        // Grey boots (Dove Gray)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.DOVE_GRAY).asStack();
        mob.equip(Slot.FEET, boots);

        // Blue Leggings (Gigas)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.GIGAS).asStack();
        mob.equip(Slot.LEGS, leggings);

        // White lab coat (Gallery)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.GALLERY).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Black hat (Cod Gray)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.COD_GRAY).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
}
