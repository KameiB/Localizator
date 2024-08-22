package kameib.localizator.common.fishingmadebetter.recipe;

import kameib.localizator.common.fishingmadebetter.item.ItemVoidBucket;
import kameib.localizator.common.fishingmadebetter.item.ItemVoidFishBucket;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.theawesomegem.fishingmadebetter.BetterFishUtil;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishData;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Recipe for the Void Fish Bucket.
 * Code based on original MIT Licensed code:
 * <a href="https://github.com/TheAwesomeGem/fishing-made-better/blob/main/src/main/java/net/theawesomegem/fishingmadebetter/common/recipe/RecipeFishBucket.java">RecipeFishBucket.java</a>
 **/

public class RecipeVoidFishBucket extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private long worldTime;

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(InventoryCrafting inv, World worldIn) {
    	if(worldIn == null) return false;
    	
    	worldTime = worldIn.getTotalWorldTime();
        return validInput(inv) != null;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack getCraftingResult(InventoryCrafting inv) {
    	Integer[] slots = validInput(inv);
    	if(slots==null) return ItemStack.EMPTY;

        ItemStack itemFish = inv.getStackInSlot(slots[1]).copy();

        return ItemVoidFishBucket.getItemStack(BetterFishUtil.getFishId(itemFish));
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
    
    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }
    
    @Nullable
    private Integer[] validInput(InventoryCrafting inv) {
    	int numStacks = 0;
        int bucketSlot = -1;
        int fishSlot = -1;
        List<Integer> occupiedSlots = new ArrayList<>();
        
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                numStacks++;
                occupiedSlots.add(i);
            }
        }
        if(numStacks != 2) return null;

        for(int i : occupiedSlots) {
            ItemStack itemStack = inv.getStackInSlot(i);
            
            if(itemStack.isEmpty()) {
                return null;
            }
            else {
                if(itemStack.getItem() instanceof ItemVoidBucket) {
                    bucketSlot = i;
                }
                else {
                    if(BetterFishUtil.isBetterFish(itemStack) 
                        && !BetterFishUtil.isDead(itemStack, worldTime) 
                        && CustomConfigurationHandler.fishDataMap.get(BetterFishUtil.getFishId(itemStack)).liquid.equals(FishData.FishingLiquid.VOID)) {
                            fishSlot = i;
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        Integer[] slots = new Integer[2];
        slots[0] = bucketSlot;
        slots[1] = fishSlot;
        return (bucketSlot != -1 && fishSlot != -1) ? slots : null;
    }
}