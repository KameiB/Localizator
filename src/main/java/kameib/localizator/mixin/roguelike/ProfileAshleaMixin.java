package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileAshlea;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ProfileAshlea.class)
public abstract class ProfileAshleaMixin {
    @Redirect(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/monster/Mob;equipArmor(Ljava/util/Random;ILcom/github/fnar/util/Color;I)V",
                    remap = false
            ),
            remap = false
    )
    // Make little guy always wear his pink leather armor, instead of it being sometimes replaced with other materials.
    private void RLD_ProfileAshlea_apply_equipArmor_allLeatherArmor(Mob mob, Random random, int level, Color color, int difficulty) {
        // Pink flamingo boots
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(Color.PINK_FLAMINGO).asStack();
        mob.equip(Slot.FEET, boots);

        // Pink flamingo Leggings
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(Color.PINK_FLAMINGO).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Pink flamingo chestplate
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(Color.PINK_FLAMINGO).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Pink flamingo helmet
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(Color.PINK_FLAMINGO).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
}
