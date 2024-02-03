package kameib.localizator.client.jei.fishingmadebetter;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.theawesomegem.fishingmadebetter.common.data.FishData;
import net.theawesomegem.fishingmadebetter.common.item.ItemManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FishRequirementsRecipeWrapper implements ICraftingRecipeWrapper {
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;
    
    private int minDeepLevel;
    private FishData.FishingLiquid fishingLiquid;
    private List<ItemStack> fishingRodStackList = new ArrayList<>();
    private List<ItemStack> reelStackList = new ArrayList<>();
    private List<ItemStack> bobberStackList = new ArrayList<>();
    
    private static final short REEL_BASIC_RANGE = 40;
    private static final short REEL_FAST_RANGE = 75;
    private static final short REEL_LONG_RANGE = 150;
    
    public FishRequirementsRecipeWrapper(FishData fishData) {
        // ---------------  Fishing Rods  ------------------
        fishingRodStackList.add(new ItemStack(ItemManager.FISHING_ROD_WOOD));
        fishingRodStackList.add(new ItemStack(ItemManager.FISHING_ROD_IRON));
        fishingRodStackList.add(new ItemStack(ItemManager.FISHING_ROD_DIAMOND));
        
        // ---------------      Reels     ------------------
        minDeepLevel = fishData.minDeepLevel;
        if (minDeepLevel < REEL_BASIC_RANGE) {
            reelStackList.add(new ItemStack(ItemManager.REEL_BASIC));
        }
        if (minDeepLevel < REEL_FAST_RANGE) {
            reelStackList.add(new ItemStack(ItemManager.REEL_FAST));
        }
        if (minDeepLevel < REEL_LONG_RANGE) {
            reelStackList.add(new ItemStack(ItemManager.REEL_LONG));
        }

        // ---------------     Bobbers    ------------------
        fishingLiquid = fishData.liquid;
        if (fishingLiquid == FishData.FishingLiquid.LAVA) {
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_OBSIDIAN));
        } else if (fishingLiquid == FishData.FishingLiquid.VOID) {
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_VOID));
        } else if (fishingLiquid == FishData.FishingLiquid.ANY) {
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_BASIC));
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_HEAVY));
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_LIGHTWEIGHT));
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_OBSIDIAN));
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_VOID));
        } else {
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_BASIC));
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_HEAVY));
            bobberStackList.add(new ItemStack(ItemManager.BOBBER_LIGHTWEIGHT));
        }

        this.inputs = new ArrayList<>();
        this.inputs.add(fishingRodStackList);
        this.inputs.add(reelStackList);
        this.inputs.add(bobberStackList);
        this.inputs.add(Collections.singletonList(new ItemStack(Item.getByNameOrId("fishingmadebetter:bait_bucket"))));
        
        // ---------------      Baits     ------------------
        Item bait;
        for (String baitName : fishData.baitItemMap.keySet()) {
            for (int baitMetadata : fishData.baitItemMap.get(baitName)) {
                bait = Item.getByNameOrId(baitName);
                if (bait != null) {
                    this.inputs.add(Collections.singletonList(new ItemStack(bait, 1, baitMetadata)));
                }
            }
        }
        
        this.output = new ItemStack(Item.getByNameOrId(fishData.itemId), 1, fishData.itemMetaData);
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
        ingredients.setOutput(VanillaTypes.ITEM, this.output);
    }
}
