package kameib.localizator.client.jei.fishingmadebetter;

import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishData;

import java.util.ArrayList;
import java.util.List;

public class FishRequirementsRecipeMaker {
    public static List<FishRequirementsRecipeWrapper> getFishRequirementsRecipes() {
        List<FishRequirementsRecipeWrapper> recipes = new ArrayList<>();
        
        for (FishData fishData : CustomConfigurationHandler.getAllFishData()) {
            recipes.add(new FishRequirementsRecipeWrapper(fishData));
        }
        
        return recipes;
    }
}
