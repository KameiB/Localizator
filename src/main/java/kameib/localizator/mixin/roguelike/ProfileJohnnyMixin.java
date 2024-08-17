package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.entity.Slot;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.profiles.ProfileJohnny;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import kameib.localizator.handlers.ForgeConfigHandler;
import kameib.localizator.util.RLD_MobUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProfileJohnny.class)
public abstract class ProfileJohnnyMixin {
    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("HEAD"),
            remap = false
    )
    private void RLD_ProfileJohnny_apply_getRandom(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        localizator$myRandom = random;
    }
    
    @ModifyArg(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/monster/Mob;equipMainhand(Lcom/github/fnar/minecraft/item/RldItemStack;)V",
                    remap = false
            ),
            remap = false
    )
    // Equip Johnny with Greymerk's Hatchet.
    // Since he will relentlessly attack everything, it would be better to enable the Eternal Novelties Mixin.
    private RldItemStack RLD_ProfileJohnny_apply_equipMainHand_equipNovelty(RldItemStack itemStack) {
        // The premise is 1/200 chance of getting Greymerk's Hatchet.
        localizator$ourJohnny = true;
        
        // If player enables Johnnys spawning from Vindicators spawners...
        if (ForgeConfigHandler.miscelaneousMixinsConfig.rldSpawnJohnny) {
            // The rarer the Johnny, the more common the Hatchet (the more common the Johnny, the rarer the Hatchet)
            // Also, if the player forgot to set up the spawn chance (it's 0.0), then this is our Johnny.
            if (localizator$myRandom.nextDouble() <= (0.005 / Math.max(0.005,ForgeConfigHandler.serverConfig.rldJohnnySpawnChance))) {
                return ItemNovelty.greymerksHatchet();
            } else {
                // Bad luck! No Hatchet!
                localizator$ourJohnny = false;
                return itemStack;
            }
        }
        // The standard (secret) Johnny spawns in a 1/200 chance in only 1 floor! I think it's fair to always give him a Novelty.
        return ItemNovelty.greymerksHatchet();
    }

    @Inject(
            method = "apply(Lgreymerk/roguelike/monster/Mob;IILjava/util/Random;)Lgreymerk/roguelike/monster/Mob;",
            at = @At("TAIL"),
            remap = false
    )
    // Any zombie with an Axe killed him in 2 hits, so better protect him!
    private void RLD_ProfileJohnny_apply_equipBetterArmor(Mob mob, int level, int difficulty, Random random, CallbackInfoReturnable<Mob> cir) {
        if (!localizator$ourJohnny) {
            // Oh, because I, Klaus Kickenklober, master choreographer, am not good enough for Johnny
            // Yes, I'm irrelevant to him!
            // I'm just a stupid, fat, old monkey!
            return;
        }
        
        int protLevel = 4;
        int unbrLevel = 2;

        RLD_MobUtil.supponpon(mob);
        // Enchant with Curse of Vanishing
        RldItemStack helmet = ArmourType.HELMET.asItem().withQuality(Quality.DIAMOND)
                .withEnchantment(Enchantment.Effect.PROTECTION.atLevel(Math.max(protLevel - 1, 1)))
                .withEnchantment(Enchantment.Effect.VANISHING_CURSE)
                .asStack();
        mob.equip(Slot.HEAD, helmet);

        RldItemStack chestplate = ArmourType.CHESTPLATE.asItem().withQuality(Quality.IRON)
                .withEnchantment(Enchantment.Effect.PROTECTION.atLevel(protLevel))
                .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(unbrLevel))
                .withEnchantment(Enchantment.Effect.VANISHING_CURSE)
                .asStack();
        mob.equip(Slot.CHEST, chestplate);

        RldItemStack leggings = ArmourType.LEGGINGS.asItem().withQuality(Quality.IRON)
                .withEnchantment(Enchantment.Effect.PROTECTION.atLevel(protLevel))
                .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(unbrLevel))
                .withEnchantment(Enchantment.Effect.VANISHING_CURSE)
                .asStack();
        mob.equip(Slot.LEGS, leggings);
        
        RldItemStack boots = ArmourType.BOOTS.asItem().withQuality(Quality.IRON)
                .withEnchantment(Enchantment.Effect.PROTECTION.atLevel(protLevel))
                .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(unbrLevel))
                .withEnchantment(Enchantment.Effect.VANISHING_CURSE)
                .asStack();
        mob.equip(Slot.FEET, boots);
    }
    
    @Unique
    private boolean localizator$ourJohnny;
    @Unique
    private Random localizator$myRandom;
}
