package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.client.KeybindManager;
import net.theawesomegem.fishingmadebetter.client.gui.GuiReelingOverlay;
import net.theawesomegem.fishingmadebetter.common.capability.fishing.IFishingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@SideOnly(Side.CLIENT)
@Mixin(GuiReelingOverlay.class)
public abstract class GuiReelingOverlayMixin extends Gui {
    /**
     * Capture 'fishingData' for later use
     */
    @Redirect(
            method = "onRenderGameOverlay(Lnet/minecraftforge/client/event/RenderGameOverlayEvent$Post;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/common/capability/fishing/IFishingData;getLineBreak()I",
                    remap = false
            ),
            remap = false
    ) 
    // Line 53: this.fontColor = this.getIntFromColor(fishingData.getLineBreak());
    private int FMB_GuiReelingOverlay_getFishingData(IFishingData instance) {
        localizator$myFishingData = instance;
        return instance.getLineBreak();
    }

    /**
     * Center the "Distance: Xm" text based on its width. It was drawn at a hardcoded location.
     * <br />Capture X and Y for later use.
     */
    @ModifyArgs(
            method = "onRenderGameOverlay(Lnet/minecraftforge/client/event/RenderGameOverlayEvent$Post;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Arguments: 0 = String text, 1 = float x, 2 = float y, 3 = int color 
    // Line 54:  this.fontRenderer.drawStringWithShadow(I18n.format("fishingmadebetter.reelingoverlay.distance", new Object[0]) + String.format(": %sm", distance), (float)this.getBarPosX(scaledWidth) + 67.0F - 30.0F, (float)(this.getBarPosY(scaledHeight) + 30 + 2), this.fontColor);
    private void FMB_GuiReelingOverlay_onRenderOverlay_drawDistanceCentered(Args args) {
        // Received x: getBarPosX(scaledWidth) + (outlineBarWidth * 0.5f) - 30
        localizator$myX = ((float)args.get(1)) + 30; // Get rid of the hardcoded "centering" and capture this position for later use
        args.set(1, localizator$myX - (fontRenderer.getStringWidth(args.get(0)) * 0.5f)); // Apply a true centering, based on the text width
        
        // Received y: getBarPosY(scaledHeight) + outlineBarHeight + 2
        localizator$myY = ((float)args.get(2)) - 2; // Capture this 'y' for later use
    }

    /**
     * After rendering "Distance: Xm", render a visual guide for players that don't know how to fish.
     * <br />(Show "Press [LEFT] / [RIGHT]" when fish is out of reel rectangle and the reel is on its break point, so experienced players don't see this message too often)
     */
    @Inject(
            method = "onRenderGameOverlay(Lnet/minecraftforge/client/event/RenderGameOverlayEvent$Post;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I",
                    shift = At.Shift.AFTER,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Before Line 55: int posX = this.getBarPosX(scaledWidth);
    private void FMB_GuiReelingOverlay_onRenderOverlay_drawPressLeftRight(RenderGameOverlayEvent.Post e, CallbackInfo ci) {
        if (ForgeConfigHandler.clientConfig.fishingmadebetterMinigameHelpText) {
            // Trying to emulate the situation when a player doesn't know how to play and just stare at the fish swimming further in the distance.
            if ((Math.abs(localizator$myFishingData.getReelTarget() - localizator$myFishingData.getReelAmount()) > (localizator$myFishingData.getErrorVariance() + 10)) && localizator$myFishingData.getLineBreak() > 0) {
                String txtInstructions = I18n.format("fishingmadebetter.reelingoverlay.move_with", KeybindManager.reelIn.getDisplayName(), KeybindManager.reelOut.getDisplayName());
                fontRenderer.drawStringWithShadow(txtInstructions, localizator$myX - (fontRenderer.getStringWidth(txtInstructions) * 0.5f), localizator$myY + 12, fontColor);
            }
        }
    }

    @Unique
    IFishingData localizator$myFishingData;
    /**
     * getBarPosX(scaledWidth) + (outlineBarWidth * 0.5f)
     */
    @Unique    
    float localizator$myX;
    /**
     * getBarPosY(scaledHeight) + outlineBarHeight
     */
    @Unique
    float localizator$myY;
    @Shadow(remap = false)
    private int fontColor;
    @Shadow(remap = false)
    private FontRenderer fontRenderer;
}
