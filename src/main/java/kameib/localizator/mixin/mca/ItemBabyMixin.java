package kameib.localizator.mixin.mca;

import mca.items.ItemBaby;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mixin(ItemBaby.class)
public class ItemBabyMixin extends Item {
    /**
     * @author KameiB
     * @reason Replace custom localizer calls to I18n calls
     */
    @Overwrite(remap = false)
    @SideOnly(Side.CLIENT)
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.hasTagCompound()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            NBTTagCompound nbt = stack.getTagCompound();
            TextFormatting textColor = ((ItemBabyMixin)stack.getItem()).isMale ? TextFormatting.AQUA : TextFormatting.LIGHT_PURPLE;
            int ageInMinutes = nbt.getInteger("age");
            String ownerName = nbt.getUniqueId("ownerUUID").equals(player.getUniqueID()) ? I18n.format("gui.label.you") : nbt.getString("ownerName");
            if (this.getBabyName(stack).equals("")) {
                tooltip.add(textColor + I18n.format("gui.label.name") + " " + TextFormatting.RESET + I18n.format("gui.label.unnamed"));
            } else {
                tooltip.add(textColor + I18n.format("gui.label.name") + " " + TextFormatting.RESET + nbt.getString("name"));
            }

            tooltip.add(textColor + I18n.format("gui.label.age") + " " + TextFormatting.RESET + ageInMinutes + " " + (ageInMinutes == 1 ? I18n.format("gui.label.minute") : I18n.format("gui.label.minutes")));
            tooltip.add(textColor + I18n.format("gui.label.parent") + " " + TextFormatting.RESET + ownerName);
            if (nbt.getBoolean("isInfected")) {
                tooltip.add(TextFormatting.GREEN + I18n.format("gui.label.infected"));
            }

            if (this.isReadyToGrowUp(stack)) {
                tooltip.add(TextFormatting.GREEN + I18n.format("gui.label.readytogrow"));
            }

            if (nbt.getString("name").equals(I18n.format("gui.label.unnamed"))) {
                tooltip.add(TextFormatting.YELLOW + I18n.format("gui.label.rightclicktoname"));
            }
        }
    }
    
    @Shadow(remap = false)
    private String getBabyName(ItemStack stack) {
        return "";
    }
    
    @Shadow(remap = false)
    private boolean isReadyToGrowUp(ItemStack itemStack) {
        return false;
    }
    
    @Shadow(remap = false)
    private boolean isMale;
}
