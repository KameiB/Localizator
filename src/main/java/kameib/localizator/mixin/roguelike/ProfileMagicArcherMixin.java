package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileMagicArcher;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.util.RLD_ExtraColor;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileMagicArcher.class)
public abstract class ProfileMagicArcherMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Give Novelty
    private void RLD_ProfileMagicArcher_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        // This profile is called in a 1/50 chance. Multiply that by 1/4 chance becomes 1/200.
        if (level == 3 && random.nextDouble() < 0.25) {
            localizator$profileKurt(mob, level, difficulty, random);
        }
    }
    
    @Unique
    private void localizator$profileKurt(Mob mob, int level, int difficulty, Random random) {
        RldItemStack mainHand = mob.getMainhand().asStack();
        RldItemStack offHand = mob.getOffhand().asStack();
        
        RLD_MobUtil.supponpon(mob).equipMainhand(mainHand);
        mob.equipOffhand(offHand);

        // Nice boots
        mob.equip(Slot.FEET, ItemNovelty.farlandTravellers());
        
        // Blue Pants, maybe? (Blue Zodiac)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.BLUE_ZODIAC).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Nice light brown coat (Driftwood)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.DRIFTWOOD).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Brown hair (Deep Bronze)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.DEEP_BRONZE).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
}
