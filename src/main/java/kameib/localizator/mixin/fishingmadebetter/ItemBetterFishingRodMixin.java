package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.ItemManager;
import net.theawesomegem.fishingmadebetter.common.item.attachment.bobber.ItemBobber;
import net.theawesomegem.fishingmadebetter.common.item.attachment.hook.ItemHook;
import net.theawesomegem.fishingmadebetter.common.item.attachment.reel.ItemReel;
import net.theawesomegem.fishingmadebetter.common.item.fishingrod.ItemBetterFishingRod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mixin(ItemBetterFishingRod.class)
public abstract class ItemBetterFishingRodMixin extends ItemFishingRod  {

    /**
     * @author KameiB
     * @reason Make Hook descriptions consistent when attached to the rod,
     * Show attached bait name properly.
     */
    @SideOnly(Side.CLIENT)
    @Overwrite(remap = Production.inProduction)
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        ItemReel reel = getReelItem(stack);
        ItemBobber bobber = getBobberItem(stack);
        ItemHook hook = getHookItem(stack);
        String baitDisplayName;
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.reel.name") + ": " + TextFormatting.RESET + TextFormatting.GRAY + I18n.format("item.fishingmadebetter." + reel.getRegistryName().getPath() + ".name") + TextFormatting.RESET);
            tooltip.add(reel.getMaxDamage() != 0 ? "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": " + (reel.getMaxDamage() - getReelDamage(stack)) + "/" + reel.getMaxDamage() : "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": -/-");
            tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.reel.range") + ": " + reel.getReelRange() + "m");
            tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.reel.speed") + ": " + reel.getReelSpeed() + "m/s");
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.bobber") + ": " + TextFormatting.RESET + "" + TextFormatting.GRAY + I18n.format("item.fishingmadebetter." + bobber.getRegistryName().getPath() + ".name") + TextFormatting.RESET);
            tooltip.add(bobber.getMaxDamage() != 0 ? "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": " + (bobber.getMaxDamage() - getBobberDamage(stack)) + "/" + bobber.getMaxDamage() : "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": -/-");
            tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.bobber.can_fish_in",
                    I18n.format("tooltip.fishingmadebetter.bobber." + (bobber.isLavaBobber() ? "obsidian" : bobber.isVoidBobber() ? "void" : "water"))));

            if (bobber.getVarianceModifier() != 0) {
                tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.bobber.tension_size") + ": +" + bobber.getVarianceModifier());
            }

            if (bobber.getTensioningModifier() != 0) {
                tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.bobber.tension_speed") + ": +" + bobber.getTensioningModifier());
            }

            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook") + ": " + TextFormatting.RESET + "" + TextFormatting.GRAY + I18n.format("item.fishingmadebetter." + hook.getRegistryName().getPath() + ".name") + TextFormatting.RESET);
            tooltip.add(hook.getMaxDamage() != 0 ? "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": " + (hook.getMaxDamage() - getHookDamage(stack)) + "/" + hook.getMaxDamage() : "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": -/-");
            if (hook.getTuggingReduction() != 0) {
                tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook.tugging") + ": -" + hook.getTuggingReduction());
            }

            if (hook.getTreasureModifier() != 0) {
                tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook.treasure_chance") + ": +" + hook.getTreasureModifier() + "%");
            }

            if (hook.getBiteRateModifier() != 0) {
                tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook.bite_rate") + ": +" + hook.getBiteRateModifier() + "%");
            }

            if (hook.getWeightModifier() != 0) {
                tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook.weight") + ": +" + hook.getWeightModifier() + "%");
            }

            baitDisplayName = FMB_BetterFishUtil.getBaitLangKey(getBaitItem(stack), getBaitMetadata(stack));
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.bait") 
                    + ": " + TextFormatting.RESET + TextFormatting.GRAY + (baitDisplayName != null && !baitDisplayName.isEmpty() ? 
                    I18n.format(baitDisplayName) : 
                    I18n.format("tooltip.fishingmadebetter.fishing_rod.bait_none")) + TextFormatting.RESET);
            tooltip.add("");
        } else {
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.reel.name") + ": " + TextFormatting.RESET + TextFormatting.GRAY + I18n.format("item.fishingmadebetter." + reel.getRegistryName().getPath() + ".name") + TextFormatting.RESET);
            tooltip.add(reel.getMaxDamage() != 0 ? "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": " + (reel.getMaxDamage() - getReelDamage(stack)) + "/" + reel.getMaxDamage() : "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": -/-");
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.bobber") + ": " + TextFormatting.RESET + TextFormatting.GRAY + I18n.format("item.fishingmadebetter." + bobber.getRegistryName().getPath() + ".name") + TextFormatting.RESET);
            tooltip.add(bobber.getMaxDamage() != 0 ? "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": " + (bobber.getMaxDamage() - getBobberDamage(stack)) + "/" + bobber.getMaxDamage() : "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": -/-");
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook") + ": " + TextFormatting.RESET + TextFormatting.GRAY + I18n.format("item.fishingmadebetter." + hook.getRegistryName().getPath() + ".name") + TextFormatting.RESET);
            tooltip.add(hook.getMaxDamage() != 0 ? "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": " + (hook.getMaxDamage() - getHookDamage(stack)) + "/" + hook.getMaxDamage() : "  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.durability.title") + ": -/-");
            baitDisplayName = FMB_BetterFishUtil.getBaitLangKey(getBaitItem(stack), getBaitMetadata(stack));
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.bait") 
                    + ": " + TextFormatting.RESET + TextFormatting.GRAY + (baitDisplayName != null && !baitDisplayName.isEmpty() ? 
                    I18n.format(baitDisplayName) :
                    I18n.format("tooltip.fishingmadebetter.fishing_rod.bait_none")) + TextFormatting.RESET);
            tooltip.add("");
            tooltip.add(I18n.format("tooltip.fishingmadebetter.fishing_rod.shift.1") + " " 
                    + TextFormatting.GOLD + "Shift" + TextFormatting.RESET + TextFormatting.GRAY + " " 
                    + I18n.format("tooltip.fishingmadebetter.fishing_rod.shift.2") + TextFormatting.RESET);
        }
    }
    
    @Shadow(remap = false)
    public static String getBaitItem(ItemStack itemStack) {
        return hasBait(itemStack) ? itemStack.getTagCompound().getString("BaitItem") : null;
    }
    @Shadow(remap = false)
    public static boolean hasBait(ItemStack itemStack) {
        return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("BaitItem");
    }
    @Shadow(remap = false)
    public static ItemReel getReelItem(ItemStack itemStack) {
        return hasReelItem(itemStack) ? (ItemReel) Item.getByNameOrId(itemStack.getTagCompound().getString("ReelItem")) : ItemManager.REEL_BASIC;
    }
    @Shadow(remap = false)
    public static boolean hasReelItem(ItemStack itemStack) {
        return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("ReelItem");
    }
    @Shadow(remap = false)
    public static ItemBobber getBobberItem(ItemStack itemStack) {
        return hasBobberItem(itemStack) ? (ItemBobber)Item.getByNameOrId(itemStack.getTagCompound().getString("BobberItem")) : ItemManager.BOBBER_BASIC;
    }
    @Shadow(remap = false)
    public static boolean hasBobberItem(ItemStack itemStack) {
        return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("BobberItem");
    }
    @Shadow(remap = false)
    public static ItemHook getHookItem(ItemStack itemStack) {
        return hasHookItem(itemStack) ? (ItemHook)Item.getByNameOrId(itemStack.getTagCompound().getString("HookItem")) : ItemManager.HOOK_BASIC;
    }
    @Shadow(remap = false)
    public static boolean hasHookItem(ItemStack itemStack) {
        return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("HookItem");
    }
    @Shadow(remap = false)
    public static int getReelDamage(ItemStack itemStack) {
        return hasReelItem(itemStack) ? itemStack.getTagCompound().getInteger("ReelDamage") : 0;
    }
    @Shadow(remap = false)
    public static int getBobberDamage(ItemStack itemStack) {
        return hasBobberItem(itemStack) ? itemStack.getTagCompound().getInteger("BobberDamage") : 0;
    }
    @Shadow(remap = false)
    public static int getHookDamage(ItemStack itemStack) {
        return hasHookItem(itemStack) ? itemStack.getTagCompound().getInteger("HookDamage") : 0;
    }
    @Shadow(remap = false)
    public static int getBaitMetadata(ItemStack itemStack) {
        return hasBait(itemStack) ? itemStack.getTagCompound().getInteger("BaitMetadata") : 0;
    }
}
