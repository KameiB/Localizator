package kameib.localizator.mixin.mca;

import kameib.localizator.data.Production;
import mca.core.forge.NetMCA;
import mca.core.forge.NetMCA.SetProfessionHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SetProfessionHandler.class)
public abstract class NetMCA_SetProfessionHandlerMixin implements IMessageHandler<NetMCA.SetProfession, IMessage> {
    @ModifyArg(
            method = "onMessage(Lmca/core/forge/NetMCA$SetProfession;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Localize message "Career set to "
    // Line 740: player.sendMessage(new TextComponentString("Career set to " + message.profession));
    private ITextComponent MCA_NetMCA_SetProfessionHandler_onMessage_sendMessage0(ITextComponent par1) {
        String tempProfession = par1.getUnformattedComponentText().replace("Career set to ", "");
        return new TextComponentTranslation("message.mca.professionHandler.careerSet", tempProfession);
    }

    @ModifyArg(
            method = "onMessage(Lmca/core/forge/NetMCA$SetProfession;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Localize message "Career not found: "
    // Line 748: player.sendMessage(new TextComponentString("Career not found: " + message.profession));
    private ITextComponent MCA_NetMCA_SetProfessionHandler_onMessage_sendMessage1(ITextComponent par1) {
        String tempProfession = par1.getUnformattedComponentText().replace("Career not found: ", "");
        return new TextComponentTranslation("message.mca.professionHandler.careerNotFound", tempProfession);
    }
}
