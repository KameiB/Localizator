package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.info.Beastiary;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Beastiary.class)
public abstract class BeastiaryMixin {
    // **************** sendAddedMessage *****************
    @Inject(
            method = "sendAddedMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/info/CreatureKnowledge;getCreatureInfo()Lcom/lycanitesmobs/core/info/CreatureInfo;",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Capture Creature name and rank from creatureKnowledge
    // Line 85: CreatureInfo creatureInfo = creatureKnowledge.getCreatureInfo();
    private void Lycanites_Beastiary_sendAddedMessage_getCreatureInfo(CreatureKnowledge creatureKnowledge, CallbackInfo ci) {
        localizator$myCreature = "entity." + creatureKnowledge.getCreatureInfo().getLocalisationKey() + ".name";
        localizator$myRank = String.valueOf(creatureKnowledge.rank);
    }
    @Redirect(
            method = "sendAddedMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/client/localisation/LanguageManager;translate(Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Don't translate lang keys on server-side
    // All invokes
    private String Lycanites_Beastiary_sendAddedMessage_dontTranslate(String key) {
        return key;
    }
    @ModifyArg(
            method = "sendAddedMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Send TextComponentTranslation instead of TextComponentString
    // Line 93: this.extendedPlayer.player.sendMessage(new TextComponentString(message));
    private ITextComponent Lycanites_Beastiary_sendAddedMessage_Message0(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText() + "_",
                new TextComponentTranslation(localizator$myCreature),
                localizator$myRank);
    }

    @ModifyArg(
            method = "sendAddedMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Send TextComponentTranslation instead of TextComponentString
    // Line 98: this.extendedPlayer.player.sendMessage(new TextComponentString(tameMessage));
    private ITextComponent Lycanites_Beastiary_sendAddedMessage_Message1(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText() + "_",
                new TextComponentTranslation(localizator$myCreature));
    }

    @ModifyArg(
            method = "sendAddedMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Send TextComponentTranslation instead of TextComponentString
    // Line 104: this.extendedPlayer.player.sendMessage(new TextComponentString(tameMessage));
    private ITextComponent Lycanites_Beastiary_sendAddedMessage_Message2(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText() + "_",
                new TextComponentTranslation(localizator$myCreature));
    }

    // **************** sendKnownMessage *****************
    @Redirect(
            method = "sendKnownMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/client/localisation/LanguageManager;translate(Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Don't translate lang keys on server-side
    // All invokes
    private String Lycanites_Beastiary_sendKnownMessage_dontTranslate(String key) {
        return key;
    }
    @Redirect(
            method = "sendKnownMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/core/info/CreatureInfo;getTitle()Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Capture creature name
    // Line 115: message = message.replace("%creature%", creatureInfo.getTitle());
    private String Lycanites_Beastiary_sendKnownMessage_getCreatureTitle(CreatureInfo instance) {
        localizator$myCreature = "entity." + instance.getLocalisationKey() + ".name";
        return instance.getTitle();
    }
    @Redirect(
            method = "sendKnownMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Capture creature rank
    // Line 116: message = message.replace("%rank%", "" + currentKnowledge.rank);
    private String Lycanites_Beastiary_sendKnownMessage_getCreatureRank(String instance, CharSequence rank, CharSequence currentRank) {
        localizator$myRank = (String) currentRank;
        return instance;
    }

    @ModifyArg(
            method = "sendKnownMessage(Lcom/lycanitesmobs/core/info/CreatureKnowledge;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Send TextComponentTranslation instead of TextComponentString
    // Line 117: this.extendedPlayer.player.sendMessage(new TextComponentString(message));
    private ITextComponent Lycanites_Beastiary_sendKnownMessage_Message0(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText() + "_",
                localizator$myRank,
                new TextComponentTranslation(localizator$myCreature));
    }
    
    @Unique
    private String localizator$myCreature, localizator$myRank;
}
