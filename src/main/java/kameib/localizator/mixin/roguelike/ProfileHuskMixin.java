package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.Difficulty;
import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileHusk;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.RLD_ExtraColor;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileHusk.class)
public abstract class ProfileHuskMixin {
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
    private void RLD_ProfileHusk_apply_setMobType_rollBaby(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        localizator$specialChild = false;
        // The probability of being a Husk (from ProfileZombie) is 1/20, so multiplied with this roll (1/20) makes it 1/400. Same as VALANDRAH.
        // If a custom HUSK spawner is used, it becomes a 1/20 chance, so modpack creators better enable that config to decrease it back to 1/400!
        if (random.nextDouble() < (ForgeConfigHandler.serverConfig.rldCustomSpawnerHusk ? 0.0025 : 0.05)) {
            if (!mob.isChild()) {
                mob.setChild(true);
                localizator$specialChild = true;
            }
        }
    }

    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Give Novelties
    private void RLD_ProfileHusk_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        if (mob.isChild()) {
            if (localizator$specialChild) {
                localizator$profileNebris(mob, level, difficulty, random);
            }
        }
        else {
            // The probability of being a Husk (from ProfileZombie) is 1/20, so multiplied with this roll (1/20) makes it 1/400. Same as VALANDRAH.
            // If a custom HUSK spawner is used, it becomes a 1/20 chance, so modpack creators better enable that config to decrease it back to 1/400!
            if (random.nextDouble() < (ForgeConfigHandler.serverConfig.rldCustomSpawnerHusk ? 0.0025 : 0.05)) {
                localizator$profileBaj(mob, level, difficulty, random);
            }
        }
    }

    @Unique
    private void localizator$profileNebris(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipSword(random, level, Difficulty.fromInt(difficulty));

        // Skin: https://namemc.com/profile/Nebris.1
        // White boots (Alto)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.ALTO).asStack();
        mob.equip(Slot.FEET, boots);

        // Grey Leggings (Cod Gray)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.COD_GRAY_2).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Light grey shirt (Mine Shaft)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.MINE_SHAFT).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Brown hair (Brown Pod)
        //RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.BROWN_POD).asStack();
        mob.equip(Slot.HEAD, ItemNovelty.nebrisCrown());
    }
    
    @Unique
    private void localizator$profileBaj(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.bajsLastResort());

        // Skin: https://namemc.com/profile/W92Baj.1
        // Black boots
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.BLACK).asStack();
        mob.equip(Slot.FEET, boots);

        // Military Leggings (Verdun Green)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.VERDUN_GREEN).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Military shirt (Verdun Green)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.VERDUN_GREEN).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Red hat (Tawny Port)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.TAWNY_PORT  ).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
    
    @Unique
    private boolean localizator$specialChild;
}
