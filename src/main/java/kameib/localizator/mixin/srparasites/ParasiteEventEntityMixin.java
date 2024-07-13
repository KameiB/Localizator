package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.util.ParasiteEventEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ParasiteEventEntity.class)
public abstract class ParasiteEventEntityMixin {
    @ModifyArg(
            method = "alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;func_145747_a(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Replace TextComponentString with TextComponentTranslation
    // Line 1375: entityPlayer.func_145747_a(new TextComponentString(message));
    private static ITextComponent SRParasites_ParasiteEventEntity_alertAllPlayerDim_translatableMessage(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText());
    }

    @Redirect(
            method = "alertAllPlayerDim(Lnet/minecraft/world/World;Ljava/lang/String;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Replace the hardcoded "Phase decreased" with a lang key
    // Line 1379: if (warning == -7 && message.equals("Phase decreased"))
    private static boolean SRParasites_ParasiteEventEntity_alertAllPlayerDim_phaseDecreased(String message, Object text) {
        return message.contentEquals("Phase decreased") || message.contentEquals("message.srparasites.phasedecreased");
    }

    @ModifyArg(
            method = "alertAllPlayerSer(Ljava/lang/String;Lnet/minecraft/world/World;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayerMP;func_145747_a(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Replace TextComponentString with TextComponentTranslation
    // Line 1399: theEntity.func_145747_a(new TextComponentString(message));
    private static ITextComponent SRParasites_ParasiteEventEntity_alertAllPlayerSer_translatableMessage(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText());
    }
    
    @ModifyArg(
            method = "leaveScent(Lnet/minecraft/world/World;Lnet/minecraft/util/DamageSource;Lcom/dhanantry/scapeandrunparasites/entity/ai/misc/EntityParasiteBase;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/entity/EntityParasiticScent;warnPlayers(Ljava/lang/String;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Replace the hardcoded message with a lang key
    // Line 1695: nut.warnPlayers("A Scent has been deployed in this area");
    private static String SRParasites_ParasiteEventEntity_leaveScent_warnPlayers(String message) {
        return "message.srparasites.scentdeployed";
    }
    
    @ModifyArg(
            method = "leaveScent(Lnet/minecraft/world/World;Lnet/minecraft/util/DamageSource;Lcom/dhanantry/scapeandrunparasites/entity/ai/misc/EntityParasiteBase;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;func_146105_b(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = false
            ),
            index = 0,
            remap = false
    )
    // Replace the hardcoded text with a lang key
    // Line 1710: ((EntityPlayer)cause.func_76346_g()).func_146105_b(new TextComponentString("Closest Scent was notified"), true);
    private static ITextComponent SRParasite_ParasiteEventEntity_leaveScent_sendStatusMessage(ITextComponent message) {
        return new TextComponentTranslation("message.srparasites.scentnotified");
    }
}
