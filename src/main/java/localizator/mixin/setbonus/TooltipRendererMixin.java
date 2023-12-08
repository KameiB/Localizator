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
import java.util.Iterator;
import java.util.List;

@Mixin(TooltipRenderer.class)
public class TooltipRendererMixin {
    /**
     * @author KameiB
     * @reason Add support to lang keys in config file's set name and bonus name
     */
    @Overwrite
    @SubscribeEvent(priority = EventPriority.LOWEST)
    // Line 29
    public static void tooltips(ItemTooltipEvent event) {
        if (SetBonusConfig.clientSettings.enableTooltips) {
            EntityPlayer player = event.getEntityPlayer();
            if (player != null) {
                List<String> tooltip = event.getToolTip();
                boolean edited = false;
                Iterator var4 = ClientData.sets.values().iterator();

                label89:
                while(var4.hasNext()) {
                    Set set = (Set)var4.next();
                    Iterator var6 = set.involvedEquips.values().iterator();

                    while(true) {
                        ItemFilter filter;
                        do {
                            if (!var6.hasNext()) {
                                continue label89;
                            }

                            filter = (ItemFilter)var6.next();
                        } while(!filter.matches(event.getItemStack()));

                        if (!edited) {
                            edited = true;
                            tooltip.add("");
                        }

                        int count = set.getNumberEquipped(player);
                        int max = set.getMaxNumber();
                        String color = "" + (count == 0 ? TextFormatting.RED : (count == max ? TextFormatting.GREEN : TextFormatting.YELLOW));
                        //tooltip.add(color + TextFormatting.BOLD + "=== " + set.name + " (" + count + "/" + max + ") ===");
                        tooltip.add(color + TextFormatting.BOLD + "=== " + (I18n.hasKey(set.name) ? I18n.format(set.name) : set.name) + " (" + count + "/" + max + ") ===");
                        Iterator var11 = ClientData.bonuses.values().iterator();

                        while(var11.hasNext()) {
                            ClientBonus bonus = (ClientBonus)var11.next();
                            int req = 0;
                            boolean otherReqs = false;
                            Iterator var15 = bonus.bonusRequirements.iterator();

                            while(var15.hasNext()) {
                                ABonusRequirement requirement = (ABonusRequirement)var15.next();
                                if (requirement instanceof SetRequirement) {
                                    SetRequirement setRequirement = (SetRequirement)requirement;
                                    if (setRequirement.set.id.equals(set.id)) {
                                        req = Tools.max(new int[]{req, setRequirement.num});
                                    } else {
                                        otherReqs = true;
                                    }
                                } else {
                                    otherReqs = true;
                                }
                            }

                            if (req > 0) {
                                ClientBonus.BonusInstance bonusInstance = bonus.getBonusInstance(player);
                                color = "";
                                int active = set.getNumberEquipped(player);
                                if (bonusInstance.active) {
                                    color = color + TextFormatting.GREEN;
                                } else if (active >= req) {
                                    color = color + TextFormatting.DARK_PURPLE;
                                } else if (active == 0) {
                                    color = color + TextFormatting.RED;
                                } else {
                                    color = color + TextFormatting.YELLOW;
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
}
