package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfilePigZombie;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.util.RLD_ExtraColor;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfilePigZombie.class)
public abstract class ProfilePigZombieMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/monster/Mob;setMobType(Lcom/github/fnar/minecraft/block/spawner/MobType;)V",
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            remap = false
    )
    // Prepare special baby
    private void RLD_ProfilePigZombie_apply_setMobType_rollBaby(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        localizator$babyZisteau = false;
        // 1/200 chance if it spawned from the Blaze Room spawner (Level 4). 
        // Else, this may be a custom Nether dungeon. Then make it 1/400 chance.
        if (random.nextDouble() < (level == 4 ? 0.005 : 0.0025)) {
            if (!mob.isChild()) {
                mob.setChild(true);
                localizator$babyZisteau = true;
            }
        }
    }
    
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Give Novelties
    private void RLD_ProfilePigZombie_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        if (mob.isChild()) {
            if (localizator$babyZisteau) {
                localizator$profileZisteauSign(mob, level, difficulty, random);
            }
        }
        else {
            // 1/200 chance if it spawned from the Blaze Room spawner (Level 4). 
            // Else, this may be a custom Nether dungeon. Then make it 1/400 chance
            if (random.nextDouble() < (level == 4 ? 0.005 : 0.0025)) {
                localizator$profileManPants(mob, level, difficulty, random);
            }
        }
    }
    
    @Unique
    private void localizator$profileZisteauSign(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.zisteauSign());

        // Skin: https://namemc.com/profile/Zisteau.1
        // Light brown boots (Fuscous Gray)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.FUSCOUS_GRAY).asStack();
        mob.equip(Slot.FEET, boots);

        // Blue Leggings (River Bed)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.RIVER_BED).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Light brown skin (Heathered Gray)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.HEATHERED_GRAY).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Light brown hat (Heathered Gray)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.HEATHERED_GRAY).asStack();
        mob.equip(Slot.HEAD, helmet);
    }

    @Unique
    private void localizator$profileManPants(Mob mob, int level, int difficulty, Random random) {
        RldItemStack mainHand = mob.getMainhand().asStack();
        RldItemStack offHand = mob.getOffhand().asStack();
        
        RLD_MobUtil.supponpon(mob).equipMainhand(mainHand);
        mob.equipOffhand(offHand);

        // Skin: https://namemc.com/profile/Zisteau.1
        // Light brown boots (Fuscous Gray)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.FUSCOUS_GRAY).asStack();
        mob.equip(Slot.FEET, boots);

        // Blue Leggings
        mob.equip(Slot.LEGS, ItemNovelty.manPants());

        // Light brown hat (Heathered Gray)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.HEATHERED_GRAY).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
    
    @Unique
    private boolean localizator$babyZisteau;
}
