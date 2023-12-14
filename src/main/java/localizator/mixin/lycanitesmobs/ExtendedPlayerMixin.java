package localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.VersionChecker;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.Beastiary;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import localizator.data.Production;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ExtendedPlayer.class)
public abstract class ExtendedPlayerMixin {
    @ModifyArg(
            method = "onUpdate()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 222: this.player.sendMessage(new TextComponentString(LanguageManager.translate("lyc.version.newer").replace("{current}", LycanitesMobs.versionNumber).replace("{latest}", latestVersion.versionNumber)));
    private ITextComponent localizator_Lycanites_ExtendedPlayer_onUpdate_versions(ITextComponent message) {
        return new TextComponentTranslation("lyc.version.newer_", 
                LycanitesMobs.versionNumber, VersionChecker.INSTANCE.getLatestVersion().versionNumber);
    }
    
    @Shadow(remap = false)
    public EntityPlayer player;
    @Shadow(remap = false)
    public Beastiary beastiary;
    @Shadow(remap = false)
    public int creatureStudyCooldown;
    @Shadow(remap = false)
    public int creatureStudyCooldownMax;
    /**
     * @author KameiB
     * @reason Translate Status Messages on client side
     */
    @Overwrite(remap = false)
    public boolean studyCreature(Entity entity, int experience, boolean useCooldown, boolean alwaysShowMessage) {
        if (!(entity instanceof BaseCreatureEntity)) {
            if (useCooldown && !this.player.getEntityWorld().isRemote) {
                this.player.sendStatusMessage(new TextComponentTranslation("message.beastiary.unknown"), true);
            }
            return false;
        }

        if (useCooldown && this.creatureStudyCooldown > 0) {
            if (!this.player.getEntityWorld().isRemote) {
                this.player.sendStatusMessage(new TextComponentTranslation("message.beastiary.study.recharging"), true);
            }
            return false;
        }

        BaseCreatureEntity creature = (BaseCreatureEntity)entity;
        if (creature.isTamed()) {
            return false;
        }
        experience = creature.scaleKnowledgeExperience(experience);
        CreatureKnowledge newKnowledge = this.beastiary.addCreatureKnowledge(creature, experience);
        if (newKnowledge != null) {
            if (useCooldown) {
                this.creatureStudyCooldown = this.creatureStudyCooldownMax;
            }
            if (!player.getEntityWorld().isRemote) {
                if (newKnowledge.getMaxExperience() == 0) {
                    player.sendStatusMessage(new TextComponentTranslation("message.beastiary.study.full")
                            .appendText(" ")
                            .appendSibling(new TextComponentTranslation("entity." + creature.creatureInfo.getLocalisationKey() + ".name")), true);
                }
                else if (experience > 0) {
                    boolean showMessage = alwaysShowMessage;
                    if (!showMessage) {
                        float messageThreshold = ((float) newKnowledge.getMaxExperience() * 0.25F);
                        int fromExperience = Math.max(0, newKnowledge.experience - experience);
                        int wrappedExperience = fromExperience % (int)messageThreshold;
                        showMessage = wrappedExperience + experience >= messageThreshold;
                    }
                    if (showMessage) {
                        player.sendStatusMessage(new TextComponentString(" ")
                                .appendSibling(new TextComponentTranslation("message.beastiary.study"))
                                .appendText(" ")
                                .appendSibling(new TextComponentTranslation("entity." + newKnowledge.getCreatureInfo().getLocalisationKey() + ".name"))
                                .appendText(String.format(" %d/%d (+%d)", newKnowledge.experience, newKnowledge.getMaxExperience(), experience))
                                , true);
                    }
                }
            }
            return true;
        }

        if (useCooldown && !player.getEntityWorld().isRemote) {
            player.sendStatusMessage(new TextComponentTranslation("message.beastiary.study.full")
                    .appendText(" ")
                    .appendSibling(new TextComponentTranslation("entity." + creature.creatureInfo.getLocalisationKey() + ".name")), true);
        }
        return false;
    }
}
