package kameib.localizator.client.jei.fishingmadebetter;

import kameib.localizator.common.fishingmadebetter.item.FMBItemManager;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishData;
import net.theawesomegem.fishingmadebetter.common.data.FishData.FishingLiquid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Recipe for the Void Fish Bucket.
 * Code based on original MIT Licensed code:
 * <a href="https://github.com/TheAwesomeGem/fishing-made-better/blob/main/src/main/java/net/theawesomegem/fishingmadebetter/client/jei/FishBucketRecipeWrapper.java">FishBucketRecipeWrapper.java</a>
 **/

public class VoidFishBucketRecipeWrapper implements ICraftingRecipeWrapper {
	private final List<List<ItemStack>> inputs;
	private final ItemStack output;

	public VoidFishBucketRecipeWrapper() {
		ItemStack voidBucket = new ItemStack(FMBItemManager.VOID_BUCKET);
		ItemStack voidFishBucket = new ItemStack(FMBItemManager.VOID_FISH_BUCKET);
		List<ItemStack> fishStackList = new ArrayList<>();
		Item fishItem;
		
		for(FishData fishData : CustomConfigurationHandler.fishDataMap.values()) {
			if(!fishData.liquid.equals(FishingLiquid.VOID)) continue;
			fishItem = Item.getByNameOrId(fishData.itemId);
			if (fishItem == null) continue;
			
			ItemStack fishStack = new ItemStack( fishItem, 1, fishData.itemMetaData);
			fishStackList.add(fishStack);
		}
		

		this.inputs = Arrays.asList(Collections.singletonList(voidBucket), fishStackList);
		this.output = voidFishBucket;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
		ingredients.setOutput(VanillaTypes.ITEM, this.output);
	}
}
