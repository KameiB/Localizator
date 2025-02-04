package kameib.localizator.mixin.mca;

import mca.client.gui.GuiWhistle;
import mca.core.forge.NetMCA;
import mca.entity.EntityVillagerMCA;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@SideOnly(Side.CLIENT)
@Mixin(GuiWhistle.class)
public abstract class GuiWhistleMixin extends GuiScreen {
    /**
     * @author KameiB
     * @reason Use I18n instead of custom localizer
     */
    @Overwrite(remap = false)
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(this.selectionLeftButton = new GuiButton(1, this.width / 2 - 123, this.height / 2 + 65, 20, 20, "<<"));
        this.buttonList.add(this.selectionRightButton = new GuiButton(2, this.width / 2 + 103, this.height / 2 + 65, 20, 20, ">>"));
        this.buttonList.add(this.villagerNameButton = new GuiButton(3, this.width / 2 - 100, this.height / 2 + 65, 200, 20, ""));
        this.buttonList.add(this.callButton = new GuiButton(4, this.width / 2 - 100, this.height / 2 + 90, 60, 20, I18n.format("gui.button.call")));
        this.buttonList.add(this.exitButton = new GuiButton(6, this.width / 2 + 40, this.height / 2 + 90, 60, 20, I18n.format("gui.button.exit")));
        NetMCA.INSTANCE.sendToServer(new NetMCA.GetFamily());
    }
    
    /**
     * @author KameiB
     * @reason Use I18n instead of custom localizer
     */
    @Overwrite(remap = false)
    public void drawScreen(int sizeX, int sizeY, float offset) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("gui.title.whistle"), this.width / 2, this.height / 2 - 120, 16777215);
        if (this.loadingAnimationTicks != -1) {
            this.drawString(this.fontRenderer, I18n.format("gui.mca.whistle.loading") + StringUtils.repeat(".", this.loadingAnimationTicks % 10), this.width / 2 - 20, this.height / 2 - 10, 16777215);
        } else if (this.villagerDataList.isEmpty()) {
            this.drawCenteredString(this.fontRenderer, I18n.format("gui.mca.whistle.noFamilyFound"), this.width / 2, this.height / 2 + 50, 16777215);
        } else {
            this.drawCenteredString(this.fontRenderer, this.selectedIndex + " / " + this.villagerDataList.size(), this.width / 2, this.height / 2 + 50, 16777215);
        }

        if (this.dummyHuman != null) {
            this.drawDummyVillager();
        }

        super.drawScreen(sizeX, sizeY, offset);
    }
    
    
    @Shadow(remap = false)
    private EntityVillagerMCA dummyHuman;
    @Shadow(remap = false)
    private List<NBTTagCompound> villagerDataList;
    @Shadow(remap = false)
    private GuiButton selectionLeftButton;
    @Shadow(remap = false)
    private GuiButton selectionRightButton;
    @Shadow(remap = false)
    private GuiButton villagerNameButton;
    @Shadow(remap = false)
    private GuiButton callButton;
    @Shadow(remap = false)
    private GuiButton exitButton;
    @Shadow(remap = false)
    private int loadingAnimationTicks;
    @Shadow(remap = false)
    private int selectedIndex;
    
    @Shadow(remap = false)
    private void drawDummyVillager() {}
}
