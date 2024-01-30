package localizator.client.jei.fishingmadebetter;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.theawesomegem.fishingmadebetter.common.item.ItemManager;

@JEIPlugin
public class FMBJeiPlugin implements IModPlugin {
    private FishRequirementsRecipeCategory fishReq;
    public FMBJeiPlugin() {
        
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        fishReq = new FishRequirementsRecipeCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(fishReq);
    }
    
    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ItemManager.FISH_TRACKER_DIAMOND), FishRequirementsRecipeCategory.UID);
        registry.addRecipes(FishRequirementsRecipeMaker.getFishRequirementsRecipes(),
                FishRequirementsRecipeCategory.UID);
    }
}
