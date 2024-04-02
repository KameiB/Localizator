package kameib.localizator.mixin.scalinghealth;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.silentchaos512.lib.util.ChatHelper;
import net.silentchaos512.scalinghealth.event.ScalingHealthCommonEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ScalingHealthCommonEvents.class)
public abstract class ScalingHealthCommonEventsMixin {
    @Redirect(
            method = "onPlayerJoinedServer(Lnet/minecraftforge/fml/common/gameevent/PlayerEvent$PlayerLoggedInEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/silentchaos512/lib/util/ChatHelper;sendMessage(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Use the ITextComponent overload instead of the String overload
    // sendMessage(EntityPlayer player, ITextComponent component)
    // Line 186: ChatHelper.sendMessage(player, "[Scaling Health] Your difficulty has been reset.");
    private void ScalingHealth_ScalingHealthCommonEvents_onPlayerJoinedServer_message0(EntityPlayer player, String message) {
        ChatHelper.sendMessage(player, new TextComponentTranslation("misc.scalinghealth.difficultyReset"));
    }

    @Redirect(
            method = "onPlayerJoinedServer(Lnet/minecraftforge/fml/common/gameevent/PlayerEvent$PlayerLoggedInEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/silentchaos512/lib/util/ChatHelper;sendMessage(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;)V",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Use the ITextComponent overload instead of the String overload
    // sendMessage(EntityPlayer player, ITextComponent component)
    // Line 193: ChatHelper.sendMessage(player, "[Scaling Health] Your health has been reset.");
    private void ScalingHealth_ScalingHealthCommonEvents_onPlayerJoinedServer_message1(EntityPlayer player, String message) {
        ChatHelper.sendMessage(player, new TextComponentTranslation("misc.scalinghealth.healthReset"));
    }
    @Redirect(
            method = "onPlayerJoinedServer(Lnet/minecraftforge/fml/common/gameevent/PlayerEvent$PlayerLoggedInEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/silentchaos512/lib/util/ChatHelper;sendMessage(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;)V",
                    ordinal = 2,
                    remap = false
            ),
            remap = false
    )
    // Use the ITextComponent overload instead of the String overload
    // sendMessage(EntityPlayer player, ITextComponent component)
    // Line 204: ChatHelper.sendMessage(event.player, TextFormatting.RED + "[Scaling Health] It's April Fool's time... hehehe.");
    private void ScalingHealth_ScalingHealthCommonEvents_onPlayerJoinedServer_message2(EntityPlayer player, String message) {
        ChatHelper.sendMessage(player, new TextComponentTranslation("misc.scalinghealth.AprilFoolTime")
                                        .setStyle(new Style().setColor(TextFormatting.RED)));
    }
}
