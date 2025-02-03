package kameib.localizator.mixin.mca;
import kameib.localizator.data.Production;
import mca.client.gui.GuiVillagerEditor;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SideOnly(Side.CLIENT)
@Mixin(GuiVillagerEditor.class)
public abstract class GuiVillagerEditorMixin extends GuiScreen {
    @Redirect(
            method = "actionPerformed(Lnet/minecraft/client/gui/GuiButton;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/entity/EntityPlayerSP;sendChatMessage(Ljava/lang/String;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Localize chat message: "Villager UUID copied to clipboard."
    // Line 75: Minecraft.getMinecraft().player.sendChatMessage("Villager UUID copied to clipboard.");
    private void MCA_GuiVillagerEditor_actionPerformed_sendMessage(EntityPlayerSP instance, String message) {
        instance.sendMessage(new TextComponentTranslation("message.mca.copyuuid"));
    }

    /**
     * @author KameiB
     * @reason Use I18n instead of custom Localizer
     */
    @Overwrite(remap = false)
    public void drawScreen(int sizeX, int sizeY, float offset) {
        this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
        this.drawString(this.fontRenderer, I18n.format("gui.mca.villager.name"), this.width / 2 - 205, this.height / 2 - 110, 16777215);
        this.drawCenteredString(this.fontRenderer, I18n.format("gui.title.editor"), this.width / 2, this.height / 2 - 110, 16777215);
        this.nameTextField.drawTextBox();
        this.professionTextField.drawTextBox();
        this.textureTextField.drawTextBox();
        super.drawScreen(sizeX, sizeY, offset);
    }
    
    @Shadow(remap = false)
    private GuiTextField nameTextField;
    @Shadow(remap = false)
    private GuiTextField professionTextField;
    @Shadow(remap = false)
    private GuiTextField textureTextField;
}
