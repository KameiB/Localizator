package kameib.localizator.common.fishingmadebetter.item;

import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.ModInfo;
import net.theawesomegem.fishingmadebetter.common.capability.world.ChunkCapabilityProvider;
import net.theawesomegem.fishingmadebetter.common.capability.world.IChunkFishingData;
import net.theawesomegem.fishingmadebetter.common.capability.world.PopulationData;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishData;
import net.theawesomegem.fishingmadebetter.common.registry.FMBCreativeTab;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Item for holding void fishes.
 * Code based on original MIT Licensed code:
 * <a href="https://github.com/TheAwesomeGem/fishing-made-better/blob/main/src/main/java/net/theawesomegem/fishingmadebetter/common/item/ItemFishBucket.java">ItemFishBucket.java</a>
 **/

public class ItemVoidFishBucket extends Item {
    public ItemVoidFishBucket(){
        super();

        this.maxStackSize=1;
        this.setCreativeTab(FMBCreativeTab.instance);
        this.setRegistryName(ModInfo.MOD_ID, "void_fish_bucket");
        this.setTranslationKey(ModInfo.MOD_ID + ".void_fish_bucket");
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) return new ActionResult<>(EnumActionResult.PASS, itemstack);

        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, itemstack);

        // Trying to avoid placing a void fish in a dimension different from The End,
        // because the Void Tracker doesn't detect fishes on other dimensions.
        // We can place the void fish in the Overworld and fish it, but the Tracker doesn't detect it.
        if(playerIn.dimension != 1) // 1 = The End
        {
            playerIn.sendMessage(new TextComponentTranslation("notif.fishingmadebetter.void_fish_bucket.only_void"));
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }

        String fishId = getFishId(itemstack);
        if(fishId == null) return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        FishData fishData = CustomConfigurationHandler.fishDataMap.get(fishId);
        if(fishData == null) return new ActionResult<>(EnumActionResult.FAIL, itemstack);

        BlockPos blockpos = raytraceresult.getBlockPos();
        IChunkFishingData chunkFishingData = getChunkFishingData(worldIn.getChunk(blockpos));
        if(chunkFishingData == null) return new ActionResult<>(EnumActionResult.FAIL, itemstack);

        //IBlockState blockState = worldIn.getBlockState(blockpos);
        //Material material = blockState.getMaterial();

        //if(material != MaterialLiquid.STRUCTURE_VOID) {
        /* if(blockpos.getY() > 3) {
            playerIn.sendMessage(new TextComponentTranslation("notif.fishingmadebetter.void_fish_bucket.only_void"));
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }*/

        /* int voidCount = 0;
        for(BlockPos pos : BlockPos.getAllInBox(blockpos.getX()-2, blockpos.getY()-3, blockpos.getZ()-2, blockpos.getX()+2, blockpos.getY(), blockpos.getZ()+2)) {
            Material mat = worldIn.getBlockState(pos).getMaterial();
            if(mat == MaterialLiquid.STRUCTURE_VOID) voidCount++;
            if(voidCount >= 25) break;
        }
        if(voidCount < 25) {
            playerIn.sendMessage(new TextComponentTranslation("notif.fishingmadebetter.void_fish_bucket.small_void"));
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }*/

        if(fishData.minYLevel > blockpos.getY() || fishData.maxYLevel < blockpos.getY()) {
            playerIn.sendMessage(new TextComponentTranslation("notif.fishingmadebetter.fish_bucket.wrong_altitude"));
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }

        Map<String, PopulationData> fishMap = chunkFishingData.getRawFishMap();
        PopulationData populationData;
        if(!fishMap.containsKey(fishId)) {
            populationData = new PopulationData(fishId, 1, 0, worldIn.getTotalWorldTime());
            fishMap.put(fishId, populationData);
        }
        else {
            populationData = fishMap.get(fishId);
            populationData.setQuantity(populationData.getQuantity() + 1);
        }
        chunkFishingData.setPopulationData(fishId, populationData, true);

        worldIn.playSound(playerIn, blockpos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        playerIn.setHeldItem(handIn, new ItemStack(Items.BUCKET));

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Objects.requireNonNull(this.getRegistryName()), "inventory"));
    }

    public static ItemStack getItemStack(String fishId) {
        ItemStack itemStack = new ItemStack(FMBItemManager.VOID_FISH_BUCKET, 1);

        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("FishId", fishId);
        itemStack.setTagCompound(tagCompound);

        return itemStack;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        String fishId = getFishId(itemStack);
        //String fishId = getFishDisplayName(itemStack);
        if(fishId==null) return;

        fishId = FMB_BetterFishUtil.fishIdToCustomLangKey(fishId);
        if (fishId == null) {
            fishId = "item.fmb.missingno.name";
        }

        tooltip.add(TextFormatting.BLUE + I18n.format("item.fishingmadebetter.fish_bucket.tooltip") + ": " + TextFormatting.BOLD + I18n.format(fishId) + TextFormatting.RESET);
    }
    
    @Nullable
    public static String getFishId(ItemStack itemStack) {
        if(!itemStack.hasTagCompound()) return null;

        NBTTagCompound tagCompound = itemStack.getTagCompound();

        assert tagCompound != null;
        return tagCompound.hasKey("FishId") ? tagCompound.getString("FishId") : null;
    }
    
    @Nullable
    private IChunkFishingData getChunkFishingData(Chunk chunk) {
        return chunk.getCapability(ChunkCapabilityProvider.CHUNK_FISHING_DATA_CAP, null);
    }
}

