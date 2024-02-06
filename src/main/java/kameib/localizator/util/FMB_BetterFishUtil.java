package kameib.localizator.util;

import joptsimple.internal.Strings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishCaughtData;
import net.theawesomegem.fishingmadebetter.common.data.FishData;

import java.util.Locale;
import java.util.Objects;

public class FMB_BetterFishUtil {
    // If itemStack is a BetterFish, then get its custom lang key
    public static String getFishCustomLangKey(ItemStack itemStack){
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("FishId"))  return null;

        return String.format("%s%s:%d%s", "item.fmb.", itemStack.getItem().getRegistryName().toString(), itemStack.getMetadata(), ".name");
    }

    // It looks for a fishId in the fishDataMap. If it exists, return its custom lang key
    public static String fishIdToCustomLangKey(String fishId){
        if(fishId == null) return null;
        if(fishId.isEmpty()) return fishId;

        String unformattedFishId; // For fishes that have their name as TextFormatting.RESET + fishId. Take away the formatting part.
        if(fishId.startsWith(TextFormatting.RESET.toString())) unformattedFishId=fishId.substring(2);
        else unformattedFishId=fishId;

        // Look up in the fishDataMap
        FishData fishData = CustomConfigurationHandler.fishDataMap.get(unformattedFishId);
        if(fishData == null) return unformattedFishId; // If it's not found return the pure fishId, without formatting.

        return String.format("%s%s:%d%s", "item.fmb.", fishData.itemId, fishData.itemMetaData, ".name");
    }
    
    public static String fishIdToRecipe(String fishId) {
        if (Strings.isNullOrEmpty(fishId)) {
            return null;
        }
        FishData fishData = CustomConfigurationHandler.fishDataMap.get(fishId);
        if (fishData == null) {
            return null;
        }
        
        return fishId.toLowerCase(Locale.ENGLISH).replace(" ", "_") + "_requirements";
    }
    
    public static ItemStack fishIdToItemStack(String fishId) {
        if (Strings.isNullOrEmpty(fishId)) {
            return null;
        }
        FishData fishData = CustomConfigurationHandler.fishDataMap.get(fishId);
        if (fishData == null) {
            return null;
        }
        
        return new ItemStack(Objects.requireNonNull(Item.getByNameOrId(fishData.itemId)), 1, fishData.itemMetaData);
    }

    // For bait fishes
    public static boolean isFish(String baitName){
        if(baitName == null) return false;
        return baitName.equals("aquaculture:fish") || baitName.equals("advanced-fishing:fish") || baitName.equals("minecraft:fish");
    }

    public static boolean isFish(ResourceLocation baitResource){
        if(baitResource == null) return false;
        return isFish(baitResource.toString());
    }

    // Just for readability on the rest of the code (Bait Bucket, Bait Box, Baited Fishing Rod)
    public static String getBaitLangKey(String baitId, int baitMetaData){
        if(baitId == null || baitId.isEmpty()) return null;
        Item item = Item.getByNameOrId(baitId);
        if(item == null) return null;

        if(isFish(baitId)) // If bait is a fish, get its custom lang key
            return String.format("%s%s:%d%s", "item.fmb.", baitId, baitMetaData, ".name");
        else { // Get its lang key, because server always sends its English display name
            ItemStack itemStack = new ItemStack(item, 1, baitMetaData);
            if(itemStack.isEmpty()) return null;
            return itemStack.getItem().getUnlocalizedNameInefficiently(itemStack) + ".name";
        }
    }

    public static String getFishDataUnlocalizedName(FishData fishData) {
        return String.format("%s%s:%d%s", "item.fmb.", fishData.itemId, fishData.itemMetaData, ".name");
    }

    public static String getFishDataUnlocalizedDesc(FishData fishData) {
        return String.format("%s%s:%d%s", "item.fmb.", fishData.itemId, fishData.itemMetaData, ".desc");
    }

    public static String getFishCaughtDataUnlocalizedName(FishCaughtData fishData) {
        return String.format("%s%s:%d%s", "item.fmb.", fishData.itemId, fishData.itemMetaData, ".name");
    }
}
