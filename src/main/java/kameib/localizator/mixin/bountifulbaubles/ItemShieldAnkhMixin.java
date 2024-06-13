package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.ItemShieldAnkh;
import cursedflames.bountifulbaubles.item.ItemShieldObsidian;
import kameib.localizator.mixin.minecraft.EntityAccessor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.api.item.IPhantomInkable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemShieldAnkh.class)
public abstract class ItemShieldAnkhMixin extends ItemShieldObsidian {
    public ItemShieldAnkhMixin(String name) {
        super(name);
    }

    @Inject(
            method = "onWornTick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;)V",
            at = @At("HEAD"),
            remap = false
    )
    // Adds Cobweb immunity because it makes sense to me >:c
    // Line 25: public void onWornTick(ItemStack stack, EntityLivingBase player)
    private void BountifulBaubles_ItemShieldAnkh_cobwebImmunity(ItemStack stack, EntityLivingBase player, CallbackInfo ci) {
        if (player instanceof EntityPlayer) {
            ((EntityAccessor)(player)).setPlayerInCobWeb(false);
        }
    }
    
    // Had to override this method just to add the added cobweb immunity in the place I wanted
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        if (!stack.getTagCompound().hasKey("HideFlags")) {
            stack.getTagCompound().setInteger("HideFlags", 2);
        }

        if (stack.getItemDamage() >= stack.getMaxDamage()) {
            tooltip.add(I18n.format("bountifulbaubles.broken"));
        }

        tooltip.add(I18n.format(this.getTranslationKey() + ".tooltip.0_cobweb"));
        
        
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(I18n.format(this.getTranslationKey() + ".tooltip.1"));
            tooltip.add(I18n.format(this.getTranslationKey() + ".tooltip.2"));
            if (stack.getItem() instanceof IPhantomInkable && ((IPhantomInkable)stack.getItem()).hasPhantomInk(stack)) {
                tooltip.add(BountifulBaubles.proxy.translate("bountifulbaubles.misc.hasPhantomInk"));
            }
        } else {
            tooltip.add(I18n.format("bountifulbaubles.moreinfo"));
        }
    }
}
