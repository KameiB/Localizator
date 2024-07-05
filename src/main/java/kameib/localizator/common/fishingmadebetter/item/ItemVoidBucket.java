package kameib.localizator.common.fishingmadebetter.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.ModInfo;
import net.theawesomegem.fishingmadebetter.common.registry.FMBCreativeTab;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * The equivalent of a Water Bucket or a Lava Bucket, to put void fishes in, to get a Void Fish Bucket.
 **/

public class ItemVoidBucket extends Item {

    public ItemVoidBucket() {
        super();

        this.setCreativeTab(FMBCreativeTab.instance);
        this.setRegistryName(ModInfo.MOD_ID,"void_bucket");
        this.setTranslationKey(ModInfo.MOD_ID + ".void_bucket");
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.fishingmadebetter.void_bucket.tooltip.1"));
        tooltip.add(TextFormatting.DARK_RED + I18n.format("item.fishingmadebetter.void_bucket.tooltip.2") + TextFormatting.RESET);
    }

}