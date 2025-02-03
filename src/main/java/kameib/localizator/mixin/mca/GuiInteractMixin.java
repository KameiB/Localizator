package kameib.localizator.mixin.mca;

import mca.client.gui.GuiInteract;
import mca.entity.EntityVillagerMCA;
import mca.entity.data.ParentData;
import mca.enums.EnumMarriageState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.*;

@Mixin(GuiInteract.class)
@SideOnly(Side.CLIENT)
public abstract class GuiInteractMixin extends GuiScreen {
    /**
     * @author KameiB
     * @reason Use standard localization methods instead of the custom ones.
     */
    @Overwrite(remap = false)
    private void drawTextPopups() {
        EnumMarriageState marriageState = EnumMarriageState.byId(this.villager.get(EntityVillagerMCA.MARRIAGE_STATE));
        if (this.hoveringOverHeartsIcon()) {
            int hearts = this.villager.getPlayerHistoryFor(this.player.getUniqueID()).getHearts();
            //this.drawHoveringText(hearts + " hearts", 35, 55);
            this.drawHoveringText(I18n.format("gui.mca.interact.label.hearts", hearts), 35, 55);
        }

        if (this.hoveringOverMarriageIcon()) {
            String spouseName = this.villager.get(EntityVillagerMCA.SPOUSE_NAME);
            String marriageInfo;
            if (marriageState == EnumMarriageState.MARRIED) {
                marriageInfo = I18n.format("gui.interact.label.married_", spouseName);
            } else if (marriageState == EnumMarriageState.ENGAGED) {
                marriageInfo = I18n.format("gui.interact.label.engaged_", spouseName);
            } else {
                marriageInfo = I18n.format("gui.interact.label.notmarried");
            }

            this.drawHoveringText(marriageInfo, 35, 85);
        }

        if (this.canDrawParentsIcon() && this.hoveringOverParentsIcon()) {
            ParentData data = ParentData.fromNBT(this.villager.get(EntityVillagerMCA.PARENTS));
            this.drawHoveringText(I18n.format("gui.interact.label.parents_", data.getParent1Name(), data.getParent2Name()), 35, 115);
        }

        if (this.canDrawGiftIcon() && this.hoveringOverGiftIcon()) {
            this.drawHoveringText(I18n.format("gui.interact.label.gift"), 35, 145);
        }

    }

    protected GuiInteractMixin(EntityVillagerMCA villager, EntityPlayer player) {
        this.villager = villager;
        this.player = player;
    }
    
    @Mutable
    @Final
    @Shadow(remap = false)
    private final EntityVillagerMCA villager;
    
    @Shadow(remap = false)
    private boolean hoveringOverHeartsIcon() { return false; }
    
    @Mutable
    @Final
    @Shadow(remap = false)
    private final EntityPlayer player;
    
    @Shadow(remap = false)
    private boolean hoveringOverMarriageIcon() { return false; }
    
    @Shadow(remap = false)
    private boolean canDrawParentsIcon() { return false; }
    
    @Shadow(remap = false)
    private boolean hoveringOverParentsIcon() { return false; }
    
    @Shadow(remap = false)
    private boolean canDrawGiftIcon() { return false; }
    
    @Shadow(remap = false)
    private boolean hoveringOverGiftIcon() { return false; }
}
