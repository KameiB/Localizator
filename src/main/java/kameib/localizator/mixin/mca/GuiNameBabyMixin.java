package kameib.localizator.mixin.mca;

import mca.client.gui.GuiNameBaby;
import mca.items.ItemBaby;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiNameBaby.class)
@SideOnly(Side.CLIENT)
public abstract class GuiNameBabyMixin extends GuiScreen {
    /**
     * @author KameiB
     * @reason Use I18n instead of custom localizer
     */
    @Overwrite(remap = false)
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.doneButton = new GuiButton(1, this.width / 2 - 40, this.height / 2 - 10, 80, 20, I18n.format("gui.button.done")));
        this.buttonList.add(this.randomButton = new GuiButton(2, this.width / 2 + 105, this.height / 2 - 60, 60, 20, I18n.format("gui.button.random")));
        this.babyNameTextField = new GuiTextField(3, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 60, 200, 20);
        this.babyNameTextField.setMaxStringLength(32);
        if (this.baby == null) {
            this.mc.displayGuiScreen(null);
        }
    }
    
    /**
     * @author KameiB
     * @reason Use I18n instead of custom localizer
     */
    @Overwrite(remap = false)
    public void drawScreen(int sizeX, int sizeY, float offset) {
        this.drawDefaultBackground();
        this.drawString(this.fontRenderer, I18n.format("gui.title.namebaby"), this.width / 2 - 100, this.height / 2 - 70, 10526880);
        this.babyNameTextField.drawTextBox();
        super.drawScreen(sizeX, sizeY, offset);
    }
    
    @Shadow(remap = false)
    private GuiTextField babyNameTextField;
    @Shadow(remap = false)
    private GuiButton doneButton;
    @Shadow(remap = false)
    private GuiButton randomButton;
    @Shadow(remap = false)
    private ItemBaby baby;
}
