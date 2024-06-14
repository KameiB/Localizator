package kameib.localizator.data.fishingmadebetter;

import com.google.common.base.Strings;
import net.theawesomegem.fishingmadebetter.common.data.FishData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FishRequirementData {
    public int minYLevel, maxYLevel;
    public FishData.FishingLiquid liquid;
    public List<Integer> dimensionList;
    public boolean rainRequired, thunderRequired;
    public List<String> biomeTagList;
    public FishData.TimeToFish timeToFish;
    public String fishId;
    public int maxLightLevel;
    
    public FishRequirementData(FishData fishData) {
        fishId = fishData.fishId;
        minYLevel = fishData.minYLevel;
        maxYLevel = fishData.maxYLevel;
        liquid = fishData.liquid;
        timeToFish = fishData.time;
        rainRequired = fishData.rainRequired;
        thunderRequired = fishData.thunderRequired;
        maxLightLevel = fishData.maxLightLevel;
        
        dimensionList = new ArrayList<>();
        if (!fishData.dimensionBlacklist) {
            dimensionList.addAll(fishData.dimensionList);
        }
        else {
            dimensionList.addAll(DefaultDimensions.getDefaultDimensions());
            dimensionList.removeAll(fishData.dimensionList);
        }
        
        biomeTagList = new ArrayList<>();
        if (!fishData.biomeBlacklist) {
            biomeTagList.addAll(fishData.biomeTagList);
        }
        else {
            biomeTagList.addAll(getAllBiomeTypeNames());
            biomeTagList.removeAll(fishData.biomeTagList);
        }
    }

    public static List<String> getAllBiomeTypeNames() {
        return Arrays.asList("MUSHROOM", "DEAD", "SPOOKY", "JUNGLE", "SWAMP", "HOT",
                "SANDY", "COLD", "MOUNTAIN", "FOREST", "PLAINS", "RIVER", "WATER");
    }

    public static int texturePosFromBiomeTag(String tag) {
        int index = 0;
        if (Strings.isNullOrEmpty(tag)) return index;
        switch(tag) {
            case "MUSHROOM":
                index = 8;
                break;
            case "DEAD":
            case "SPOOKY":
                index = 9;
                break;
            case "JUNGLE":
                index = 1;
                break;
            case "SWAMP":
                index = 7;
                break;
            case "HOT":
            case "SANDY":
                index = 2;
                break;
            case "COLD":
                index = 3;
                break;
            case "MOUNTAIN":
                index = 4;
                break;
            case "FOREST":
                index = 5;
                break;
            case "PLAINS":
            case "RIVER":
                index = 6;
                break;
        }
        return index;
    }
    
    public enum DefaultDimensions {
        NETHER(-1),
        OVERWORLD(0),
        THE_END(1);
        private final Integer dimensionCode;
        
        DefaultDimensions(Integer dimension) {
            dimensionCode = dimension;
        }
        
        public static List<Integer> getDefaultDimensions() {
            return Arrays.asList(NETHER.dimensionCode, OVERWORLD.dimensionCode, THE_END.dimensionCode);
        }
    }
    
}
