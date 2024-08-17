package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileSwordsman;
import kameib.localizator.util.RLD_ExtraColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileSwordsman.class)
public abstract class ProfileSwordsmanMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/monster/Mob;equipMainhand(Lcom/github/fnar/minecraft/item/RldItemStack;)V",
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            cancellable = true,
            remap = false
    )
    // Dress up this swordsman as Valandrah :D. The Sword has already been equipped
    private void RLD_ProfileSwordsman_apply_equipMainhand_ValandrahClothes(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        // Skin: https://namemc.com/profile/Valandrah.1
        // Black boots
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.BLACK).asStack();
        mob.equip(Slot.FEET, boots);

        // Pink Leggings (Jazzberry Jam)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.JAZZBERRY_JAM).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Black chestplate
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.BLACK).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Purple hair (Seance)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.SEANCE).asStack();
        mob.equip(Slot.HEAD, helmet);
        
        cir.setReturnValue(mob);
    }
}
