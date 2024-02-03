package kameib.localizator.client.jei.fishingmadebetter;

import kameib.localizator.client.jei.fishingmadebetter.FishRequirementsRecipeCategory;
import kameib.localizator.client.jei.fishingmadebetter.FishRequirementsRecipeMaker;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class FMBJeiPlugin implements IModPlugin {
    private FishRequirementsRecipeCategory fishReq;
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        fishReq = new FishRequirementsRecipeCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(fishReq);        
    }
    
    @Override
    public void register(IModRegistry registry) {        
        //registry.addRecipeCatalyst(new ItemStack(ItemManager.FISH_TRACKER_DIAMOND), FishRequirementsRecipeCategory.UID);
        registry.addRecipes(FishRequirementsRecipeMaker.getFishRequirementsRecipes(),fishReq.getUid());
    }
}
