package kameib.localizator.mixin.mca;
import kameib.localizator.data.Production;
import mca.client.gui.GuiWhistle;
import mca.core.Localizer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@SideOnly(Side.CLIENT)
@Mixin(GuiWhistle.class)
public abstract class GuiWhistleMixin extends GuiScreen {
    @Redirect(
            method = "initGui()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/core/Localizer;localize(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Use I18n instead of custom localizer
    // Line 54: this.buttonList.add(this.callButton = new GuiButton(4, this.width / 2 - 100, this.height / 2 + 90, 60, 20, MCA.getLocalizer().localize("gui.button.call", new String[0])));
    // Line 55: this.buttonList.add(this.exitButton = new GuiButton(6, this.width / 2 + 40, this.height / 2 + 90, 60, 20, MCA.getLocalizer().localize("gui.button.exit", new String[0])));
    private String MCA_GuiWhistle_initGui_buttons_localize(Localizer instance, String key, String[] vars) {
        return I18n.format(key);
    }
    
    @Redirect(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/core/Localizer;localize(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Replace custom localize method with I18n
    // Line 95: this.drawCenteredString(this.fontRenderer, MCA.getLocalizer().localize("gui.title.whistle", new String[0]), this.width / 2, this.height / 2 - 110, 16777215);
    private String MCA_GuiWhistle_drawScreen_whistle_localize(Localizer instance, String key, String[] vars) {
        return I18n.format(key);
    }
    
    @ModifyArg(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/client/gui/GuiWhistle;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 1,
            remap = false
            
    )
    // Localize "Loading"
    // Line 97: this.drawString(this.fontRenderer, "Loading" + StringUtils.repeat(".", this.loadingAnimationTicks % 10), this.width / 2 - 20, this.height / 2 - 10, 16777215);
    private String MCA_GuiWhistle_drawScreen_drawString_loading(String par2) {
        return par2.replace("Loading", I18n.format("gui.mca.whistle.loading"));
    }
    
    @ModifyArg(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/client/gui/GuiWhistle;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            index = 1,
            remap = false
    )
    // Localize "No family members could be found in the area."
    // Line 99: this.drawCenteredString(this.fontRenderer, "No family members could be found in the area.", this.width / 2, this.height / 2 + 50, 16777215);
    private String MCA_GuiWhistle_drawScreen_drawCenteredString1_noFamilyMembers(String par2) {
        return I18n.format("gui.mca.whistle.noFamilyFound");
    }
}
