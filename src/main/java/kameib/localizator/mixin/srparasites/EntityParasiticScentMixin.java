package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.entity.EntityParasiticScent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityParasiticScent.class)
public abstract class EntityParasiticScentMixin {
    @ModifyArg(
            method = "scentListener()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/entity/EntityParasiticScent;warnPlayers(Ljava/lang/String;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Replace the hardcoded message with a lang key
    // Line 157: this.warnPlayers("Scent is active");
    private String SRParasites_EntityParasiticScent_scentListener_warnPlayers(String message) {
        return "message.srparasites.scentactive";
    }

    @ModifyArg(
            method = "warnPlayers(Ljava/lang/String;)V",
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
    // Line 647: mob.func_146105_b(new TextComponentString(in), true);
    private ITextComponent SRParasite_EntityParasiticScent_warnPlayers_sendStatusMessage(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText());
    }
}
