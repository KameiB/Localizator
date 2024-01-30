package localizator.client.jei.fishingmadebetter;

import com.google.common.base.Strings;
import localizator.Localizator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishData;

import java.util.ArrayList;
import java.util.List;

public class FishRequirementsRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    // First, do default stuff
    private final IGuiHelper guiHelper;
    public static final String UID = "FMB_FISH_REQUIREMENTS";
    public FishRequirementsRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }
    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("jei.fishingmadebetter.category.fish_requirements");
    }

    @Override
    public String getModName() {
        return Localizator.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.guiHelper.createDrawable(GuiBackground, 7, 5, 163, 120);
    }

    private static final short xOffset = -8;
    private static final short yOffset = -6;
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        //List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);

        // Fish
        guiItemStacks.init(SLOT_FISH, true, 23 + xOffset, 55 + yOffset);
        if (ingredients.getOutputs(VanillaTypes.ITEM).size() > 0) {
            guiItemStacks.set(SLOT_FISH, ingredients.getOutputs(VanillaTypes.ITEM).get(SLOT_FISH));
            //Localizator.LOGGER.info("[Localizator] FishGui ------- " + ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0).getDisplayName() + " ------");
        }
        
        // Fishing rods
        guiItemStacks.init(SLOT_FISHING_ROD, true, 14 + xOffset, 83 + yOffset);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 0) {
            guiItemStacks.set(SLOT_FISHING_ROD, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_FISHING_ROD-1));
        }

        // Reels
        guiItemStacks.init(SLOT_REEL, true, 32 + xOffset, 83 + yOffset);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 1) {
            guiItemStacks.set(SLOT_REEL, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_REEL-1));
        }

        // Bobber
        guiItemStacks.init(SLOT_BOBBER, true, 14 + xOffset, 101 + yOffset);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 2) {
            guiItemStacks.set(SLOT_BOBBER, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_BOBBER-1));
        }

        // Bait Bucket
        guiItemStacks.init(SLOT_BAIT_BUCKET, true, 64 + xOffset, 101 + yOffset);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 3) {
            guiItemStacks.set(SLOT_BAIT_BUCKET, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_BAIT_BUCKET-1));
        }

        // Baits
        for (short i = 0, slot = SLOT_BAITS_START, x = 96 + xOffset, y = 51 + yOffset; (slot - 1) < ingredients.getInputs(VanillaTypes.ITEM).size() && i < 16 ; slot++, i++) {
            guiItemStacks.init(slot, true, x + (i/4 * 18), y + ((i%4) * 18));
            if (ingredients.getInputs(VanillaTypes.ITEM).size() > ((slot - 1))) {
                guiItemStacks.set(slot, ingredients.getInputs(VanillaTypes.ITEM).get((slot - 1)));
            }
        }
        //Localizator.LOGGER.info("[Localizator] FishGui ----------------------");
    }

    private static final ResourceLocation GuiBackground = new ResourceLocation("fishingmadebetter","textures/gui/fishreq_gui.png");
    private static final ResourceLocation RainOverlay = new ResourceLocation("fishingmadebetter", "textures/gui/rain_overlay.png");
    private static final ResourceLocation ThunderstormOverlay = new ResourceLocation("fishingmadebetter", "textures/gui/thunderstorm_overlay.png");
    private static final ResourceLocation YaxisOverlay = new ResourceLocation("fishingmadebetter", "textures/gui/Y_axis_overlay.png");
    private static final ResourceLocation ReelHudBiome = new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_biome.png");
    private static final ResourceLocation ReelHudFullsize = new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_fullsize.png");
    private static final ResourceLocation ReelHudOutline = new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_outline.png");
    private static final ResourceLocation ReelHudUnderOverlay = new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_underoverlay.png");
    private static final short minigameBackgroundBarWidth = 128;
    private static final short minigameBackgroundBarHeight = 24;
    private static final short REEL_BASIC_RANGE = 40;
    private static final short REEL_FAST_RANGE = 75;
    private static final short REEL_LONG_RANGE = 150;
    private final int minigameOutlineBarWidth = minigameBackgroundBarWidth + 6;
    private final int minigameOutlineBarHeight = minigameBackgroundBarHeight + 6;
    private static List<Integer> defaultDimensions = new ArrayList<>();
    private ItemStack fishStack;
    /** YaxisOverlay */
    private int minYLevel, maxYLevel;
    /** ReelHudLiquid and Bobber */
    private FishData.FishingLiquid fishingLiquid;
    /** ReelHudFullsize */
    private List<Integer> dimensionList = new ArrayList<>();
    /** ReelHudBiome */
    private List<String> biomeTagList = new ArrayList<>();
    /** ReelHudBiome */
    private FishData.TimeToFish timeToFish;
    /** RainOverlay & ThunderstormOverlay */
    private boolean rainRequired, thunderRequired;
    /** Reel */
    private int minDeepLevel, maxDeepLevel;
    private List<ItemStack> reelStackList = new ArrayList<>();
    private List<ItemStack> baitStackList = new ArrayList<>();
    private List<ItemStack> bobberStackList = new ArrayList<>();
    private List<ItemStack> fishingRodStackList = new ArrayList<>();
    public boolean shouldShow;


    private static final short SLOT_FISH = 0;
    private static final short SLOT_FISHING_ROD = 1;
    private static final short SLOT_REEL = 2;
    private static final short SLOT_BOBBER = 3;
    private static final short SLOT_BAIT_BUCKET = 4;
    private static final short SLOT_BAITS_START = 5;
    
    
    
    public void InitializeGuiData(FishData fishData) {
        // ---------------     Y Meter    ------------------
        minYLevel = fishData.minYLevel;
        maxYLevel = fishData.maxYLevel;
        
        // --------------- Minigame Image ------------------
        fishingLiquid = fishData.liquid;
        if (fishData.dimensionBlacklist) {
            dimensionList.addAll(defaultDimensions);
            dimensionList.removeAll(fishData.dimensionList);
        } else {
            List<Integer> nonVanillaDimensions = new ArrayList<>(fishData.dimensionList);
            nonVanillaDimensions.removeAll(defaultDimensions);
            dimensionList.addAll(fishData.dimensionList);
            dimensionList.removeAll(nonVanillaDimensions);
        }
        biomeTagList.addAll(fishData.biomeTagList);
        timeToFish = fishData.time;
        rainRequired = fishData.rainRequired;
        thunderRequired = fishData.thunderRequired;
        
        // ---------------  Fishing Rods  ------------------
        Item fishingrod;
        fishingrod = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "fishing_rod_wood"));
        fishingRodStackList.add(new ItemStack(fishingrod != null ? fishingrod : Items.AIR));
        fishingrod = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "fishing_rod_iron"));
        fishingRodStackList.add(new ItemStack(fishingrod != null ? fishingrod : Items.AIR));
        fishingrod = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "fishing_rod_diamond"));
        fishingRodStackList.add(new ItemStack(fishingrod != null ? fishingrod : Items.AIR));
        
        // ---------------      Reels     ------------------
        minDeepLevel = fishData.minDeepLevel;
        maxDeepLevel = fishData.maxDeepLevel;
        Item reel;
        if (minDeepLevel < REEL_BASIC_RANGE) {
            reelStackList.add(new ItemStack(Items.AIR));
        }
        if (minDeepLevel < REEL_FAST_RANGE) {
            reel = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "reel_fast"));
            reelStackList.add(new ItemStack(reel != null ? reel : Items.AIR));
        }
        if (minDeepLevel < REEL_LONG_RANGE) {
            reel = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "reel_long"));
            reelStackList.add(new ItemStack(reel != null ? reel : Items.AIR));
        }
        
        // ---------------     Bobbers    ------------------
        Item bobber;
        if (fishingLiquid == FishData.FishingLiquid.LAVA) {
            bobber = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "bobber_obsidian"));
            bobberStackList.add(new ItemStack(bobber != null ? bobber : Items.AIR));
        } else if (fishingLiquid == FishData.FishingLiquid.VOID) {
            bobber = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "bobber_void"));
            bobberStackList.add(new ItemStack(bobber != null ? bobber : Items.AIR));
        } else {
            bobberStackList.add(new ItemStack(Items.AIR));
            bobber = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "bobber_heavy"));
            bobberStackList.add(new ItemStack(bobber != null ? bobber : Items.AIR));
            bobber = Item.REGISTRY.getObject(new ResourceLocation("fishingmadebetter", "bobber_lightweight"));
            bobberStackList.add(new ItemStack(bobber != null ? bobber : Items.AIR));
        }
        // ---------------      Baits     ------------------
        ResourceLocation rl;
        for (String baitName : fishData.baitItemMap.keySet()) {
            for (int baitMetadata : fishData.baitItemMap.get(baitName)) {
                rl = new ResourceLocation(baitName);

                if (Item.REGISTRY.containsKey(rl)) {
                    Item item = Item.REGISTRY.getObject(rl);
                    if (item != null) {
                        baitStackList.add(new ItemStack(item, 1, baitMetadata));
                    }
                }
            }
        }
    }
    
    public void FishRequirementsWrapper(ItemStack fish) {
        this.fishStack = fish;
        biomeTagList = new ArrayList<>();        
        dimensionList = new ArrayList<>();
        reelStackList = new ArrayList<>();
        baitStackList = new ArrayList<>();
        bobberStackList = new ArrayList<>();
        fishingRodStackList = new ArrayList<>();
        defaultDimensions.add(-1);
        defaultDimensions.add(0);
        defaultDimensions.add(1);
        
        FishDataFromItemStack(this.fishStack);
    }
    
    
    
    
    
    
    public void FishDataFromItemStack(ItemStack itemStack) {
        String fishString = Item.REGISTRY.getNameForObject(itemStack.getItem()).toString();
        if (Strings.isNullOrEmpty(fishString)) {
            shouldShow = false;
            return;
        }
        
        for (FishData currFish : CustomConfigurationHandler.getAllFishData()) {
            if (currFish.itemId.equals(fishString)) {
                if (currFish.itemMetaData == itemStack.getMetadata()) {
                    shouldShow = true;
                    return;
                }
            }
        }
        shouldShow = false;
    }
    
}
