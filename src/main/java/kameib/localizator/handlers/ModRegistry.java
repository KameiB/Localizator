package kameib.localizator.handlers;

import kameib.localizator.Localizator;
import kameib.localizator.common.fishingmadebetter.item.FMBItemManager;
import kameib.localizator.common.fishingmadebetter.recipe.RecipeLavaFishBucket;
import kameib.localizator.common.fishingmadebetter.recipe.RecipeVoidFishBucket;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.theawesomegem.fishingmadebetter.ModInfo;
import net.theawesomegem.fishingmadebetter.util.RebornCraftingHelper;

@Mod.EventBusSubscriber(modid = Localizator.MODID)
public class ModRegistry {
        
        public static void init() {
                
        }
        
        @SubscribeEvent
        public static void registerItemEvent(RegistryEvent.Register<Item> event) {
                if (Loader.isModLoaded("fishingmadebetter")) {
                        FMBItemManager.register(event.getRegistry());
                }
        }
        
        @SubscribeEvent
        public static void registerRecipeEvent(RegistryEvent.Register<IRecipe> event) {
                if (Loader.isModLoaded("fishingmadebetter")) {
                        // LAVA FISH BUCKET
                        if (ForgeConfigHandler.serverConfig.fishingmadebetterLavaFishBucket) {
                                event.getRegistry().register(new RecipeLavaFishBucket().setRegistryName(new ResourceLocation(ModInfo.MOD_ID, "lava_fish_bucket")));
                                RebornCraftingHelper.addShapelessRecipe(new ItemStack(Items.BUCKET), FMBItemManager.LAVA_FISH_BUCKET);
                        }
                                                
                        // VOID FISH BUCKET
                        if (ForgeConfigHandler.serverConfig.fishingmadebetterVoidFishBucket) {
                                // VOID BUCKET
                                RebornCraftingHelper.addShapedRecipe(new ItemStack(FMBItemManager.VOID_BUCKET), 
                                        " P ", "PBP", " P ", 'P', Items.ENDER_PEARL, 'B', Items.BUCKET);
                                RebornCraftingHelper.addShapelessRecipe(new ItemStack(Items.BUCKET), 
                                        FMBItemManager.VOID_BUCKET);
                                if (Loader.isModLoaded("advanced-fishing")) {
                                        RebornCraftingHelper.addShapelessRecipe(
                                                new ItemStack(FMBItemManager.VOID_BUCKET),
                                                new ItemStack(Item.getByNameOrId("advanced-fishing:fish"), 1, 14), // Pearlfish
                                                Items.BUCKET);
                                }

                                // VOID FISH BUCKET
                                event.getRegistry().register(new RecipeVoidFishBucket().setRegistryName(new ResourceLocation(ModInfo.MOD_ID, "void_fish_bucket")));
                                RebornCraftingHelper.addShapelessRecipe(new ItemStack(Items.BUCKET), FMBItemManager.VOID_FISH_BUCKET);
                        }
                }
        }
}