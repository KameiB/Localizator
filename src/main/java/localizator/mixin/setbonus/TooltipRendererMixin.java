package localizator.mixin.setbonus;

import com.fantasticsource.mctools.items.ItemFilter;
import com.fantasticsource.setbonus.client.ClientBonus;
import com.fantasticsource.setbonus.client.ClientData;
import com.fantasticsource.setbonus.client.TooltipRenderer;
import com.fantasticsource.setbonus.common.bonusrequirements.ABonusRequirement;
import com.fantasticsource.setbonus.common.bonusrequirements.setrequirement.Set;
import com.fantasticsource.setbonus.common.bonusrequirements.setrequirement.SetRequirement;
import com.fantasticsource.setbonus.config.SetBonusConfig;
import com.fantasticsource.tools.Tools;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(TooltipRenderer.class)
public abstract class TooltipRendererMixin {
    /**
     * @author KameiB
     * @reason Add support to lang keys in config file's set name and bonus name
     * Code based on original MIT Licensed code:
     * https://github.com/Laike-Endaril/Set-Bonus/blob/1.12.2/src/main/java/com/fantasticsource/setbonus/client/TooltipRenderer.java
     */
    @Overwrite(remap = false)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT) // Just to remind myself that this is running on client side
    public static void tooltips(ItemTooltipEvent event) {    
        if (!SetBonusConfig.clientSettings.enableTooltips) return;

        EntityPlayer player = event.getEntityPlayer();
        if (player == null) return;

        List<String> tooltip = event.getToolTip();

        boolean edited = false;
        for (Set set : ClientData.sets.values())
        {
            for (ItemFilter filter : set.involvedEquips.values())
            {
                if (filter.matches(event.getItemStack()))
                {
                    if (!edited)
                    {
                        edited = true;
                        tooltip.add("");
//                        tooltip.add("" + LIGHT_PURPLE + UNDERLINE + I18n.translateToLocalFormatted(SetBonus.MODID + ".tooltip.pressDetailKey"));
//                        tooltip.add("");
                    }
                    int count = set.getNumberEquipped(player);
                    int max = set.getMaxNumber();
                    String color = "" + (count == 0 ? TextFormatting.RED : count == max ? TextFormatting.GREEN : TextFormatting.YELLOW);
                    //tooltip.add(color + TextFormatting.BOLD + "=== " + set.name + " (" + count + "/" + max + ") ===");
                    tooltip.add(color + TextFormatting.BOLD + "=== " + (I18n.hasKey(set.name) ? I18n.format(set.name) : set.name) + " (" + count + "/" + max + ") ===");
                    for (ClientBonus bonus : ClientData.bonuses.values())
                    {
                        int req = 0;
                        boolean otherReqs = false;

                        for (ABonusRequirement requirement : bonus.bonusRequirements)
                        {
                            if (requirement instanceof SetRequirement)
                            {
                                SetRequirement setRequirement = ((SetRequirement) requirement);
                                if (setRequirement.set.id.equals(set.id))
                                {
                                    req = Tools.max(req, setRequirement.num);
                                }
                                else otherReqs = true;
                            }
                            else otherReqs = true;
                        }

                        if (req > 0)
                        {
                            ClientBonus.BonusInstance bonusInstance = bonus.getBonusInstance(player);

                            color = "";
                            int active = set.getNumberEquipped(player);

                            if (bonusInstance.active) color += TextFormatting.GREEN; //All requirements met
                            else
                            {
                                if (active >= req) color += TextFormatting.DARK_PURPLE; //Set requirements are met, but non-set requirements are not met
                                else if (active == 0) color += TextFormatting.RED; //No set requirements met
                                else color += TextFormatting.YELLOW; //Some set requirements met
                            }

                            //tooltip.add(color + " (" + active + "/" + req + ")" + (otherReqs ? "*" : "") + " " + bonus.name);
                            tooltip.add(color + " (" + active + "/" + req + ")" + (otherReqs ? "*" : "") + " " + (I18n.hasKey(bonus.name) ? I18n.format(bonus.name) : bonus.name));
                        }
                    }
                    tooltip.add("");
                }
            }
        }
    }
}
