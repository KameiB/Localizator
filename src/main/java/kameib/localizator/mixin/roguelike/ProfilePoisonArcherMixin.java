package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfilePoisonArcher;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.util.RLD_ExtraColor;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfilePoisonArcher.class)
public abstract class ProfilePoisonArcherMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Give Novelties
    private void RLD_ProfilePoisonArcher_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        // This profile is called only in 1 floor, in a 1/40 chance. If we multiply that by 1/5 chance, it becomes a 1/200 chance.
        if (random.nextDouble() < 0.20) {
            localizator$profileEnikoBow(mob, level, difficulty, random);
        }
    }
    
    @Unique
    private void localizator$profileEnikoBow(Mob mob, int level, int difficulty, Random random) {
        RldItemStack offHand = mob.getOffhand().asStack();
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.enikosStringTheory());
        mob.equipOffhand(offHand);

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
