package kameib.localizator.client.jei.fishingmadebetter;

import kameib.localizator.handlers.ForgeConfigHandler;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.ParametersAreNonnullByDefault;

@JEIPlugin
public class FMBJeiPlugin implements IModPlugin {
    private FishRequirementsRecipeCategory fishReq;
    private static IJeiRuntime runtime;
    
    @Override
    @ParametersAreNonnullByDefault
    public void registerCategories(IRecipeCategoryRegistration registry) {
        if (Loader.isModLoaded("fishingmadebetter")) {
            fishReq = new FishRequirementsRecipeCategory(registry.getJeiHelpers().getGuiHelper());
            registry.addRecipeCategories(fishReq);
        }
    }
    
    @Override
    @ParametersAreNonnullByDefault
    public void register(IModRegistry registry) { 
        if (Loader.isModLoaded("fishingmadebetter")) {
            // Fish Requirements
            registry.addRecipes(FishRequirementsRecipeMaker.getFishRequirementsRecipes(), fishReq.getUid());
            
            // New fish buckets
            if (ForgeConfigHandler.serverConfig.fishingmadebetterLavaFishBucket) {
                registry.addRecipes(LavaFishBucketRecipeMaker.getLavaFishBucketRecipes(), VanillaRecipeCategoryUid.CRAFTING);
            }
            if (ForgeConfigHandler.serverConfig.fishingmadebetterVoidFishBucket) {
                registry.addRecipes(VoidFishBucketRecipeMaker.getVoidFishBucketRecipes(), VanillaRecipeCategoryUid.CRAFTING);
            }
        }
    }

    @ParametersAreNonnullByDefault
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }
    
    public static IJeiRuntime getJeiRuntime() {
        return runtime;
    }
}
