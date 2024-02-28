package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.core.VersionChecker;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.Beastiary;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import kameib.localizator.data.Production;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

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
                LycanitesMobs.versionNumber, 
                VersionChecker.INSTANCE.getLatestVersion().versionNumber);
    }
    @ModifyArg(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Replace TextcomponentString with TextComponentTranslation
    // Line 288: this.player.sendStatusMessage(new TextComponentString(LanguageManager.translate("message.beastiary.unknown")), true);
    private ITextComponent Lycanites_ExtendedPlayer_studyCreature_Message0(ITextComponent chatComponent) {
        return new TextComponentTranslation("message.beastiary.unknown");
    }
    @ModifyArg(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Replace TextcomponentString with TextComponentTranslation
    // Line 294: this.player.sendStatusMessage(new TextComponentString(LanguageManager.translate("message.beastiary.study.recharging")), true);
    private ITextComponent Lycanites_ExtendedPlayer_studyCreature_Message1(ITextComponent chatComponent) {
        return new TextComponentTranslation("message.beastiary.study.recharging");
    }
    @Redirect(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;isTamed()Z",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Capture creature name (lang key)
    // Line 312: this.player.sendStatusMessage(new TextComponentString(LanguageManager.translate("message.beastiary.study.full") + " " + creature.creatureInfo.getTitle()), true);
    private boolean Lycanites_ExtendedPlayer_studyCreature_getCreatureTitle(BaseCreatureEntity instance) {
        localizator$myCreatureTitle = "entity." + instance.creatureInfo.getLocalisationKey() + ".name";
        return instance.isTamed();
    }
    @ModifyArg(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Replace TextcomponentString with TextComponentTranslation
    // Line 312: this.player.sendStatusMessage(new TextComponentString(LanguageManager.translate("message.beastiary.study.full") + " " + creature.creatureInfo.getTitle()), true);
    private ITextComponent Lycanites_ExtendedPlayer_studyCreature_Message2(ITextComponent chatComponent) {
        return new TextComponentTranslation("message.beastiary.study.full")
                .appendText(" ")
                .appendSibling(new TextComponentTranslation(localizator$myCreatureTitle));
    }
    @Redirect(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;scaleKnowledgeExperience(I)I",
                    remap = false
            ),
            remap = false
    )
    // Capture local variable "experience"
    // Line 303: experience = creature.scaleKnowledgeExperience(experience);
    private int Lycanites_ExtendedPlayer_studyCreature_captureExperience(BaseCreatureEntity instance, int knowledgeExperience) {
        localizator$myExperience = instance.scaleKnowledgeExperience(knowledgeExperience);
        return localizator$myExperience;
    }
    @Redirect(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/info/Beastiary;addCreatureKnowledge(Lnet/minecraft/entity/Entity;I)Lcom/lycanitesmobs/core/info/CreatureKnowledge;",
                    remap = false
            ),
            remap = false
    )
    // Capture newKnowledge
    // Line 304: CreatureKnowledge newKnowledge = this.beastiary.addCreatureKnowledge(creature, experience);
    private CreatureKnowledge Lycanites_ExtendedPlayer_studyCreature_getMaxExperience(Beastiary instance, Entity creature, int experience) {
        localizator$myCreatureKnowledge = instance.addCreatureKnowledge(creature, experience);
        return localizator$myCreatureKnowledge;
    }
    @ModifyArg(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Replace TextcomponentString with TextComponentTranslation
    // Line 323: this.player.sendStatusMessage(new TextComponentString(" " + LanguageManager.translate("message.beastiary.study") + newKnowledge.getCreatureInfo().getTitle() + " " + newKnowledge.experience + "/" + newKnowledge.getMaxExperience() + " (+" + experience + ")"), true);
    private ITextComponent Lycanites_ExtendedPlayer_studyCreature_Message3(ITextComponent chatComponent) {
        return new TextComponentString(" ")
                .appendSibling(new TextComponentTranslation("message.beastiary.study"))
                .appendText(" ")
                .appendSibling(new TextComponentTranslation(localizator$myCreatureTitle))
                .appendText(String.format(" %d/%d (+%d)", localizator$myCreatureKnowledge.experience, localizator$myCreatureKnowledge.getMaxExperience(), localizator$myExperience));
    }
    @ModifyArg(
            method = "studyCreature(Lnet/minecraft/entity/Entity;IZZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Replace TextcomponentString with TextComponentTranslation
    // Line 331: this.player.sendStatusMessage(new TextComponentString(LanguageManager.translate("message.beastiary.study.full") + " " + creature.creatureInfo.getTitle()), true);
    private ITextComponent Lycanites_ExtendedPlayer_studyCreature_Message4(ITextComponent chatComponent) {
        return new TextComponentTranslation("message.beastiary.study.full")
                .appendText(" ")
                .appendSibling(new TextComponentTranslation(localizator$myCreatureTitle));
    }
    
    @Unique
    private String localizator$myCreatureTitle;
    @Unique
    private CreatureKnowledge localizator$myCreatureKnowledge;
    @Unique
    private int localizator$myExperience;
}
