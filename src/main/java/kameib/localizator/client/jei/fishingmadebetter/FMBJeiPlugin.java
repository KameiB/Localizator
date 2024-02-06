package kameib.localizator.client.jei.fishingmadebetter;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import javax.annotation.Nonnull;

@JEIPlugin
public class FMBJeiPlugin implements IModPlugin {
    private FishRequirementsRecipeCategory fishReq;
    private static IJeiRuntime runtime;
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        fishReq = new FishRequirementsRecipeCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(fishReq);        
    }
    
    @Override
    public void register(IModRegistry registry) {        
        registry.addRecipes(FishRequirementsRecipeMaker.getFishRequirementsRecipes(),fishReq.getUid());
    }

    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }
    
    public static IJeiRuntime getJeiRuntime() {
        return runtime;
    }
}
