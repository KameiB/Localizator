package kameib.localizator.client.jei.fishingmadebetter;

import kameib.localizator.Localizator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.theawesomegem.fishingmadebetter.common.item.ItemManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class FishRequirementsRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    private final IGuiHelper guiHelper;
    
    public FishRequirementsRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }
    @Override
    @Nonnull
    public String getUid() {
        return "FMB_FISH_REQUIREMENTS";
    }
    @Override
    @Nonnull
    public String getTitle() {
        return I18n.format("jei.fishingmadebetter.category.fish_requirements");
    }
    @Override
    @Nonnull
    public String getModName() {
        return Localizator.NAME;
    }
    @Override
    @Nonnull
    public IDrawable getBackground() {
        return this.guiHelper.createDrawable(new ResourceLocation("fishingmadebetter","textures/gui/fishreq_gui.png"), 7, 5, 163, 120);
    }
    @Nullable
    @Override
    public IDrawable getIcon() {
        return this.guiHelper.createDrawableIngredient(new ItemStack(ItemManager.FISH_TRACKER_DIAMOND));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout,@Nonnull IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        // Fish
        guiItemStacks.init(SLOT_FISH, true, 23 + X_OFFSET, 55 + Y_OFFSET);
        if (ingredients.getOutputs(VanillaTypes.ITEM).size() > 0) {
            guiItemStacks.set(SLOT_FISH, ingredients.getOutputs(VanillaTypes.ITEM).get(SLOT_FISH));
        }
        else { // No fish? Nothing to do here
            return;
        }
        
        // Fishing rods
        guiItemStacks.init(SLOT_FISHING_ROD, true, 14 + X_OFFSET, 83 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() >= SLOT_FISHING_ROD) {
            guiItemStacks.set(SLOT_FISHING_ROD, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_FISHING_ROD-1));
        }

        // Reels
        guiItemStacks.init(SLOT_REEL, true, 32 + X_OFFSET, 83 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() >= SLOT_REEL) {
            guiItemStacks.set(SLOT_REEL, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_REEL-1));
        }

        // Bobber
        guiItemStacks.init(SLOT_BOBBER, true, 14 + X_OFFSET, 101 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() >= SLOT_BOBBER) {
            guiItemStacks.set(SLOT_BOBBER, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_BOBBER-1));
        }

        // Bait Bucket
        guiItemStacks.init(SLOT_BAIT_BUCKET, true, 64 + X_OFFSET, 101 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() >= SLOT_BAIT_BUCKET) {
            guiItemStacks.set(SLOT_BAIT_BUCKET, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_BAIT_BUCKET-1));
        }

        // Baits
        for (short i = 0, slot = SLOT_BAITS_START, x = 96 + X_OFFSET, y = 51 + Y_OFFSET; (slot - 1) < ingredients.getInputs(VanillaTypes.ITEM).size() && i < 16 ; slot++, i++) {
            guiItemStacks.init(slot, true, x + (i/4 * 18), y + ((i%4) * 18));
            if (ingredients.getInputs(VanillaTypes.ITEM).size() > ((slot - 1))) {
                guiItemStacks.set(slot, ingredients.getInputs(VanillaTypes.ITEM).get((slot - 1)));
            }
        }
        
    }

    private static final short SLOT_FISH = 0;
    private static final short SLOT_FISHING_ROD = 1;
    private static final short SLOT_REEL = 2;
    private static final short SLOT_BOBBER = 3;
    private static final short SLOT_BAIT_BUCKET = 4;
    private static final short SLOT_BAITS_START = 5;
    private static final short X_OFFSET = -8;
    private static final short Y_OFFSET = -6;    
}
