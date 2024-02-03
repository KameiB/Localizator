package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.item.special.ItemSoulstone;
import com.lycanitesmobs.core.pets.PetEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ItemSoulstone.class)
public abstract class ItemSoulstoneMixin {
    /**
     * @author KameiB
     * @reason Translate messages on client side, not server side
     */
    @Overwrite(remap = false)
    public boolean onItemRightClickOnEntity(EntityPlayer player, Entity entity, ItemStack itemStack) {
        ExtendedPlayer playerExt = ExtendedPlayer.getForPlayer(player);
        if(playerExt == null)
            return false;
        if(!(entity instanceof TameableCreatureEntity)) {
            if(!player.getEntityWorld().isRemote)
                player.sendMessage(new TextComponentTranslation("message.soulstone.invalid"));
            return false;
        }

        TameableCreatureEntity entityTameable = (TameableCreatureEntity)entity;
        CreatureInfo creatureInfo = entityTameable.creatureInfo;
        if(!creatureInfo.isTameable() || entityTameable.getOwner() != player) {
            if(!player.getEntityWorld().isRemote)
                player.sendMessage(new TextComponentTranslation("message.soulstone.untamed"));
            return false;
        }
        if(entityTameable.getPetEntry() != null) {
            if(!player.getEntityWorld().isRemote)
                player.sendMessage(new TextComponentTranslation("message.soulstone.exists"));
            return false;
        }

        // Particle Effect:
        if(player.getEntityWorld().isRemote) {
            for(int i = 0; i < 32; ++i) {
                entity.getEntityWorld().spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                        entity.posX + (4.0F * player.getRNG().nextFloat()) - 2.0F,
                        entity.posY + (4.0F * player.getRNG().nextFloat()) - 2.0F,
                        entity.posZ + (4.0F * player.getRNG().nextFloat()) - 2.0F,
                        0.0D, 0.0D, 0.0D);
            }
        }

        // Store Pet:
        if(!player.getEntityWorld().isRemote) {
            String petType = "pet";
            if(entityTameable.creatureInfo.isMountable()) {
                petType = "mount";
            }

            String message = "message.soulstone." + petType + ".added_";
            //message = message.replace("%creature%", creatureInfo.getTitle());
            player.sendMessage(new TextComponentTranslation(message, new TextComponentTranslation("entity." + creatureInfo.getLocalisationKey() + ".name")));
            //player.addStat(ObjectManager.getStat("soulstone"), 1);

            // Add Pet Entry:
            PetEntry petEntry = PetEntry.createFromEntity(player, entityTameable, petType);
            playerExt.petManager.addEntry(petEntry);
            playerExt.sendPetEntriesToPlayer(petType);
            petEntry.assignEntity(entity);
            entityTameable.setPetEntry(petEntry);

            // Consume Soulstone:
            if (!player.capabilities.isCreativeMode)
                itemStack.setCount(Math.max(0, itemStack.getCount() - 1));
        }

        return true;
    }
}
