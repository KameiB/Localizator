package kameib.localizator.mixin.mca;

import mca.client.gui.GuiStaffOfLife;
import mca.core.forge.NetMCA;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@SideOnly(Side.CLIENT)
@Mixin(GuiStaffOfLife.class)
public abstract class GuiStaffOfLifeMixin extends GuiScreen {
    /**
     * @author KameiB
     * @reason Use I18n instead of custom Localizer
     */
    @Overwrite(remap = false)
    public void initGui() {
        NetMCA.INSTANCE.sendToServer(new NetMCA.SavedVillagersRequest());
        this.buttonList.clear();
        this.buttonList.add(this.backButton = new GuiButton(1, this.width / 2 - 123, this.height / 2 + 65, 20, 20, "<<"));
        this.buttonList.add(this.nextButton = new GuiButton(2, this.width / 2 + 103, this.height / 2 + 65, 20, 20, ">>"));
        this.buttonList.add(this.nameButton = new GuiButton(3, this.width / 2 - 100, this.height / 2 + 65, 200, 20, ""));
        this.buttonList.add(this.reviveButton = new GuiButton(4, this.width / 2 - 100, this.height / 2 + 90, 60, 20, I18n.format("gui.button.revive")));
        this.buttonList.add(this.closeButton = new GuiButton(5, this.width / 2 + 40, this.height / 2 + 90, 60, 20, I18n.format("gui.button.exit")));
    }

    /**
     * @author KameiB
     * @reason Use I18n instead of custom Localizer
     */
    @Overwrite(remap = false)
    public void drawScreen(int sizeX, int sizeY, float offset) {
        this.drawDefaultBackground();
        this.drawDummy();
        this.drawCenteredString(this.fontRenderer, I18n.format("gui.title.staffoflife"), this.width / 2, this.height / 2 - 110, 16777215);
        super.drawScreen(sizeX, sizeY, offset);
    }
    
    @Inject(
            method = "setVillagerData(Ljava/util/Map;)V",
            at = @At("TAIL"),
            remap = false
    )
    // Lovcalize the "No villagers found." button text.
    // Line 82: this.nameButton.displayString = "No villagers found.";
    private void MCA_GuiStaffOfLife_setVillagerData_nameButton_noVillagers(Map<String, NBTTagCompound> data, CallbackInfo ci) {
        if (data.isEmpty()) {
            nameButton.displayString = I18n.format("gui.staffoflife.noVillagersFound");
        }
    }

    @Shadow(remap = false)
    private GuiButton nameButton;
    @Shadow(remap = false)
    private GuiButton reviveButton;
    @Shadow(remap = false)
    private GuiButton backButton;
    @Shadow(remap = false)
    private GuiButton nextButton;
    @Shadow(remap = false)
    private GuiButton closeButton;
    @Shadow(remap = false)
    private void drawDummy() {}
}
