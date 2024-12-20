package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileZombieVillager;
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

@Mixin(ProfileZombieVillager.class)
public abstract class ProfileZombieVillagerMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Give Novelties
    private void RLD_ProfileZombieVillager_apply_equipNovelty(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        // The probability of being a Zombie Villager (from ProfileZombie) is 1/20, so multiplied with this roll (1/20) makes it 1/400. Same as VALANDRAH.
        // If a custom ZOMBIE_VILLAGER spawner is used, it would become a 1/20 chance, so modpack creators better enable that config to decrease it back to 1/400!
        if (!mob.isChild()) {
            // Adult holding White Russian
            if (random.nextDouble() < (ForgeConfigHandler.serverConfig.rldCustomSpawnerZombieVillager ? 0.005 : 0.05)) {
                localizator$profileAvidya(mob, level, difficulty, random);
                return;
            }
            // Give Zombie Villagers a chance to be a child.
            // From Zombie Villager spawner: 1/80. From ProfileZombie: 1/20 * 1/4 = 1/80
            if (random.nextDouble() < (ForgeConfigHandler.serverConfig.rldCustomSpawnerZombieVillager ? 0.0125 : 0.25)) {
                mob.setChild(true);
            }
        }
        
        // From ProfileZombie -> ProfileBaby (1/40) -> Baby Zombie Villager (1/40 * 1/2 = 1/80)
        if (mob.isChild()) {
            if (level == 0) { // 1/80 because very few spawners in level 0
                localizator$profileFourles(mob, level, difficulty, random);
                return;
            }
            if (level == 1 && random.nextInt(2) == 0) { // 2 = 1/160
                localizator$profileGinger(mob, level, difficulty, random);
                return;
            }
            if (level == 2 && random.nextInt(2) == 0) { // 2 = 1/160
                localizator$profileCleo(mob, level, difficulty, random);
                return;
            }
            if (level == 4 && random.nextInt(2) == 0) { // 2 = 1/160
                localizator$profileNotch(mob, level, difficulty, random);
                //return;
            }
        }
    }
    
    @Unique
    private void localizator$profileAvidya(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.avidyasWhiteRussian());
        
        // Skin: https://namemc.com/profile/Avidya.1
        // Pink boots (New York Pink)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.NEW_YORK_PINK).asStack();
        mob.equip(Slot.FEET, boots);

        // Avidya Leggings (Mongoose)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.MONGOOSE).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Avidya chest (Mongoose)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.MONGOOSE).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Brown hair (Old Copper)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.OLD_COPPER).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
    
    @Unique
    private void localizator$profileFourles(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.fourlesDarkroastBeans());

        // Brown boots
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(Color.COFFEE).asStack();
        mob.equip(Slot.FEET, boots);

        // Brown Leggings
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(Color.COFFEE).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Brown Chestplate
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(Color.COFFEE).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Brown Helmet
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(Color.COFFEE).asStack();
        mob.equip(Slot.HEAD, helmet);
    }

    @Unique
    private void localizator$profileGinger(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.gingerSpiceChicken());

        // Dark yellow boots
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(Color.SUNNY_MOOD).asStack();
        mob.equip(Slot.FEET, boots);

        // Dark yellow Leggings
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(Color.SUNNY_MOOD).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Dark yellow Chestplate
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(Color.SUNNY_MOOD).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Dark yellow Helmet
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(Color.SUNNY_MOOD).asStack();
        mob.equip(Slot.HEAD, helmet);
    }

    @Unique
    private void localizator$profileCleo(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.cleophianDiggingFeesh());

        // Skin: https://namemc.com/skin/2f7d1fd8b0185dd3
        // Brown boots (Van Cleef)
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.VAN_CLEEF).asStack();
        mob.equip(Slot.FEET, boots);

        // Black Leggings (Cod Gray)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.COD_GRAY_DARK).asStack();
        mob.equip(Slot.LEGS, leggings);

        // ZombieCleo skin (Cascade)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.CASCADE).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Orange hair (Tia Maria)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.TIA_MARIA).asStack();
        mob.equip(Slot.HEAD, helmet);
    }

    @Unique
    private void localizator$profileNotch(Mob mob, int level, int difficulty, Random random) {
        RLD_MobUtil.supponpon(mob).equipMainhand(ItemNovelty.notchsApple());

        // Skin: https://namemc.com/profile/Notch.1
        // Black boots
        RldItemStack boots = ArmourType.BOOTS.asItem().leather().withColor(RLD_ExtraColor.BLACK).asStack();
        mob.equip(Slot.FEET, boots);

        // Grey leggings (Gray)
        RldItemStack leggings = ArmourType.LEGGINGS.asItem().leather().withColor(RLD_ExtraColor.GRAY).asStack();
        mob.equip(Slot.LEGS, leggings);

        // Brown chestplate (Spice)
        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().leather().withColor(RLD_ExtraColor.SPICE).asStack();
        mob.equip(Slot.CHEST, chestplate);

        // Flesh helmet (My Pink)
        RldItemStack helmet = ArmourType.HELMET.asItem().leather().withColor(RLD_ExtraColor.MY_PINK).asStack();
        mob.equip(Slot.HEAD, helmet);
    }
}
