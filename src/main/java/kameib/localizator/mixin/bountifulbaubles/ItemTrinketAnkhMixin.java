package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.PotionNegation;
import cursedflames.bountifulbaubles.item.ItemTrinketPotionCharm;
import kameib.localizator.mixin.minecraft.EntityAccessor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.botania.api.item.IPhantomInkable;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({"MixinSuperClass"})
@Mixin(targets = "cursedflames.bountifulbaubles.item.ModItems$1ItemTrinketAnkh")
public abstract class ItemTrinketAnkhMixin extends ItemTrinketPotionCharm implements PotionNegation.IPotionNegateItem {
    public ItemTrinketAnkhMixin(String id, List<String> potionsIn) {
        super(id, potionsIn);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        PotionNegation.negatePotion(player, potions);
        if (player instanceof EntityPlayer) {
            ((EntityAccessor)player).setPlayerInCobWeb(false);
        }
    }

    // Had to override this method just to add the added cobweb immunity in the place I wanted
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        // From GenericItemBB
        boolean isShifting = GuiScreen.isShiftKeyDown();
        String base = this.getTranslationKey() + ".tooltip.";
        String shift = "";
        if (I18n.hasKey(base + "0")) {
            // Add cobweb immunity to its tooltip.
            if (I18n.hasKey(base + "0_cobweb")) {
                shift = "_cobweb";
            }
            if (isShifting && I18n.hasKey(base + "0s")) {
                shift += "s";
            }

            for(int i = 0; I18n.hasKey(base + i + shift) && i < 100; ++i) {
                tooltip.add(I18n.format(base + i + shift));
            }
        }
        
        // From AGenericItemBauble
        if (stack.getItem() instanceof IPhantomInkable && ((IPhantomInkable)stack.getItem()).hasPhantomInk(stack)) {
            tooltip.add(BountifulBaubles.proxy.translate("bountifulbaubles.misc.hasPhantomInk"));
        }
        
    }
}
