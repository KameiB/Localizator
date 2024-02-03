package kameib.localizator.client.jei.fishingmadebetter;

import com.google.common.base.Strings;
import com.sun.istack.internal.NotNull;
import kameib.localizator.Localizator;
import kameib.localizator.data.Drawing;
import kameib.localizator.data.fishingmadebetter.FishRequirementData;
import kameib.localizator.data.Texture;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import net.theawesomegem.fishingmadebetter.common.data.FishData;
import net.theawesomegem.fishingmadebetter.common.item.ItemManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;


public class FishRequirementsRecipeCategory extends GuiScreen implements IRecipeCategory<IRecipeWrapper> {
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
    @Nullable
    @Override
    public IDrawable getIcon() {
        return this.guiHelper.createDrawableIngredient(new ItemStack(ItemManager.FISH_TRACKER_DIAMOND));
    }
    @Override
    @Nonnull
    public IDrawable getBackground() {
        return this.guiHelper.createDrawable(new ResourceLocation("fishingmadebetter","textures/gui/fishreq_gui.png"), 7, 5, 163, 120);
    }

    private static final short X_OFFSET = -8;
    private static final short Y_OFFSET = -6;
    private static final int MINIGAME_X_START = 26 - 6;
    private static final int MINIGAME_Y_START = 8 + (Y_OFFSET >>1);
    private static final int YMETER_X_START = 16 + (X_OFFSET);
    private static final int YMETER_Y_START = 8 + (Y_OFFSET >>1);
    private static final short MAX_Y_LEVEL = 28;

    @Override
    public void setRecipe(IRecipeLayout recipeLayout,@Nonnull IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        // Fish
        guiItemStacks.init(SLOT_FISH, true, 23 + X_OFFSET, 55 + Y_OFFSET);
        if (ingredients.getOutputs(VanillaTypes.ITEM).size() > 0) {
            guiItemStacks.set(SLOT_FISH, ingredients.getOutputs(VanillaTypes.ITEM).get(SLOT_FISH));
            
            if(!localInit(ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0))) {
                return; // If the initialization failed, I can't continue.
            }
        }
        else { // No fish? Nothing to do here
            return;
        }
        
        // Fishing rods
        guiItemStacks.init(SLOT_FISHING_ROD, true, 14 + X_OFFSET, 83 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 0) {
            guiItemStacks.set(SLOT_FISHING_ROD, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_FISHING_ROD-1));
        }

        // Reels
        guiItemStacks.init(SLOT_REEL, true, 32 + X_OFFSET, 83 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 1) {
            guiItemStacks.set(SLOT_REEL, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_REEL-1));
        }

        // Bobber
        guiItemStacks.init(SLOT_BOBBER, true, 14 + X_OFFSET, 101 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 2) {
            guiItemStacks.set(SLOT_BOBBER, ingredients.getInputs(VanillaTypes.ITEM).get(SLOT_BOBBER-1));
        }

        // Bait Bucket
        guiItemStacks.init(SLOT_BAIT_BUCKET, true, 64 + X_OFFSET, 101 + Y_OFFSET);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 3) {
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
    @Override
    @Nonnull
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = new ArrayList<>();
        if ((mouseX >= YMETER_X_START && mouseX < YMETER_X_START + DRAWING_YMETER_BACKGROUND.width)
                && (mouseY >= YMETER_Y_START && mouseY < YMETER_Y_START + DRAWING_YMETER_BACKGROUND.height)) {
            // Detailed Y range
            tooltip.add(TextFormatting.GRAY + "Y: " + TextFormatting.WHITE + fishRequirementData.minYLevel + TextFormatting.GRAY + " - " + TextFormatting.WHITE + fishRequirementData.maxYLevel);
            return tooltip;
        }
        
        if ((mouseX >= MINIGAME_X_START && mouseX < MINIGAME_X_START + DRAWING_OUTLINE.width)
                && (mouseY >= MINIGAME_Y_START && mouseY < MINIGAME_Y_START + DRAWING_OUTLINE.height)) {
            // Time to fish
            tooltip.add(TextFormatting.GRAY + I18n.format("jei.fishingmadebetter.category.fish_requirements.minigame.time.tooltip",
                    TextFormatting.WHITE + I18n.format("notif.fishingmadebetter.fish_tracker.creative.time." + fishRequirementData.timeToFish)));
            // Requires rain?
            tooltip.add(TextFormatting.GRAY + I18n.format("jei.fishingmadebetter.category.fish_requirements.minigame.rain.tooltip",
                    TextFormatting.WHITE + I18n.format("notif.fishingmadebetter.fish_tracker.creative.rain." + fishRequirementData.rainRequired)));
            // Requires thunderstorm?
            tooltip.add(TextFormatting.GRAY + I18n.format("jei.fishingmadebetter.category.fish_requirements.minigame.thunder.tooltip",
                     TextFormatting.WHITE + I18n.format("notif.fishingmadebetter.fish_tracker.creative.thunder." + fishRequirementData.thunderRequired)));
            return tooltip;
        }

        return Collections.emptyList();
    }
    
    private FishRequirementData fishRequirementData = null;
    public List<BiomeDimensionOverlay> overlayList = new ArrayList<>();
    
    // Initialize data based on the target fish
    public boolean localInit(ItemStack fishStack) {
        boolean success = false;
        fishRequirementData = null;
        overlayList.clear();
        String fishString = Objects.requireNonNull(Item.REGISTRY.getNameForObject(fishStack.getItem())).toString();
        if (Strings.isNullOrEmpty(fishString)) {
            return false;
        }

        for (FishData currFish : CustomConfigurationHandler.getAllFishData()) {
            if (currFish.itemId.equals(fishString)) {
                if (currFish.itemMetaData == fishStack.getMetadata()) {
                    fishRequirementData = new FishRequirementData(currFish);
                    success = true;
                    break;
                }
            }
        }
        
        if (!success) {
            return false;
        }

        // --------------- Minigame Image ------------------
        if (fishRequirementData.dimensionList.isEmpty()) {
            return false;
        }
        for (Integer dimension : fishRequirementData.dimensionList) {
            if (dimension == -1) {
                overlayList.add(new BiomeDimensionOverlay(dimension, null, fishRequirementData.liquid, fishRequirementData.timeToFish, fishRequirementData.rainRequired, fishRequirementData.thunderRequired, fishRequirementData.minYLevel, fishRequirementData.maxYLevel));
            } else if (dimension == 1) {
                overlayList.add(new BiomeDimensionOverlay(dimension, null, fishRequirementData.liquid, fishRequirementData.timeToFish, fishRequirementData.rainRequired, fishRequirementData.thunderRequired, fishRequirementData.minYLevel, fishRequirementData.maxYLevel));
            } else {
                for (String biome : fishRequirementData.biomeTagList) {
                    overlayList.add(new BiomeDimensionOverlay(dimension, biome, fishRequirementData.liquid, fishRequirementData.timeToFish, fishRequirementData.rainRequired, fishRequirementData.thunderRequired, fishRequirementData.minYLevel, fishRequirementData.maxYLevel));
                }
            }
        }
        
        return true;
    }
    
    // Now, draw the minigame interface and the Y meter
    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        this.mc=minecraft;
        //GL11.glPushMatrix();
        //GL11.glScalef(1F, 1F, 1F);
        
        beginRenderingTransparency();
        {
            // First draw the lowermost overlay, then the uppermost one
            /*
            // ---------- Y METER OVERLAY ----------
            mc.renderEngine.bindTexture(TEXTURE_OVERLAYS.texture);
            // Y Meter Background
            drawModalRectWithCustomSizedTexture(YMETER_X_START, YMETER_Y_START, DRAWING_YMETER_BACKGROUND.u, DRAWING_YMETER_BACKGROUND.v, DRAWING_YMETER_BACKGROUND.width, DRAWING_YMETER_BACKGROUND.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            // Y range
            drawModalRectWithCustomSizedTexture(YMETER_X_START + 1, YMETER_Y_START + 1 + (MAX_Y_LEVEL - (fishRequirementData.maxYLevel / 5)), DRAWING_YRANGE.u, DRAWING_YRANGE.v + (MAX_Y_LEVEL - ((float) fishRequirementData.maxYLevel / 5)), DRAWING_YRANGE.width, fishRequirementData.maxYLevel / 5 - Math.max(fishRequirementData.minYLevel / 5, 0) + 1, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);

            // Roll between dimensions
            int dimensionCurrent = mc.player.ticksExisted % (fishRequirementData.dimensionList.size() * 20);
            if (fishRequirementData.dimensionList.get(dimensionCurrent / 20) == DimensionType.NETHER.getId()) {
                // Lava level
                drawModalRectWithCustomSizedTexture(YMETER_X_START + 1, YMETER_Y_START + 1 + (MAX_Y_LEVEL - (31 / 5)), DRAWING_LAVA_LEVEL.u, DRAWING_LAVA_LEVEL.v, DRAWING_LAVA_LEVEL.width, DRAWING_LAVA_LEVEL.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            } else if (fishRequirementData.dimensionList.get(dimensionCurrent / 20) == DimensionType.THE_END.getId()) {
                // Void level
                drawModalRectWithCustomSizedTexture(YMETER_X_START + 1, YMETER_Y_START + 1 + (MAX_Y_LEVEL - (5 / 5)), DRAWING_VOID_LEVEL.u, DRAWING_VOID_LEVEL.v, DRAWING_VOID_LEVEL.width, DRAWING_VOID_LEVEL.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            } else {
                // Sea level
                drawModalRectWithCustomSizedTexture(YMETER_X_START + 1, YMETER_Y_START + 1 + (MAX_Y_LEVEL - (62 / 5)), DRAWING_SEA_LEVEL.u, DRAWING_SEA_LEVEL.v, DRAWING_SEA_LEVEL.width, DRAWING_SEA_LEVEL.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            }
            // Y Meter Foreground
            drawModalRectWithCustomSizedTexture(YMETER_X_START, YMETER_Y_START, DRAWING_YMETER_FOREGROUND.u, DRAWING_YMETER_FOREGROUND.v, DRAWING_YMETER_FOREGROUND.width, DRAWING_YMETER_FOREGROUND.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            */

            // ---------- MINIGAME OVERLAY ----------
            int biomeDisplay = mc.player.ticksExisted % (overlayList.size() * 20);
            overlayList.get(biomeDisplay / 20).drawMe(mc, MINIGAME_X_START + 3, MINIGAME_Y_START + 3);

            // Outline
            drawImage(TEXTURE_OUTLINE, MINIGAME_X_START, MINIGAME_Y_START, DRAWING_OUTLINE);

        }
        finishRenderingTransparency();

        //GL11.glPopMatrix();
    }

    public void drawImage(Texture texture, int x, int y, Drawing drawing) {
        this.mc.renderEngine.bindTexture(texture.texture);
        drawModalRectWithCustomSizedTexture(x, y, drawing.u, drawing.v, drawing.width, drawing.height, texture.textureWidth, texture.textureHeight);
    }

    private void beginRenderingTransparency() {
        GlStateManager.pushMatrix();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
    }

    private void finishRenderingTransparency() {
        GlStateManager.popMatrix();
    }
    
    public static class BiomeDimensionOverlay {
        public Texture m_textureLiquid, m_textureTime, m_textureSurroundings, m_textureRain, m_textureThunder;
        public Drawing m_drawingLiquid, m_drawingTime, m_drawingSurroundings, m_drawingRain, m_drawingThunder, m_drawingYmeterLiquidLevel;
        int m_minYLevel, m_maxYLevel, m_dimension;

        public BiomeDimensionOverlay(int dimension, String biomeTag, FishData.FishingLiquid fishingLiquid, FishData.TimeToFish timeToFish, boolean rainRequired, boolean thunderRequired, int minYLevel, int maxYLevel) {
            m_minYLevel = minYLevel;
            m_maxYLevel = maxYLevel;
            m_dimension = dimension;
            
            switch (dimension) {
                case -1: // Nether
                    m_textureLiquid = TEXTURE_LIQUID_SKY;           m_drawingLiquid = DRAWING_LIQUID_LAVA;
                    m_textureTime = TEXTURE_LIQUID_SKY;             m_drawingTime = DRAWING_TIMELESS;
                    m_textureSurroundings = TEXTURE_DIMENSION_CAVE; m_drawingSurroundings = DRAWING_NETHER;
                    m_textureRain = TEXTURE_LIQUID_SKY;             m_drawingRain = DRAWING_TIMELESS;
                    m_textureThunder = TEXTURE_LIQUID_SKY;          m_drawingThunder = DRAWING_TIMELESS; 
                    m_drawingYmeterLiquidLevel = DRAWING_LAVA_LEVEL;
                    break;
                case 1: // The End
                    m_textureLiquid = TEXTURE_LIQUID_SKY;           m_drawingLiquid = DRAWING_LIQUID_VOID;
                    m_textureTime = TEXTURE_LIQUID_SKY;             m_drawingTime = DRAWING_TIMELESS;
                    m_textureSurroundings = TEXTURE_DIMENSION_CAVE; m_drawingSurroundings = DRAWING_THE_END;
                    m_textureRain = TEXTURE_LIQUID_SKY;             m_drawingRain = DRAWING_TIMELESS;
                    m_textureThunder = TEXTURE_LIQUID_SKY;          m_drawingThunder = DRAWING_TIMELESS;
                    m_drawingYmeterLiquidLevel = DRAWING_VOID_LEVEL;
                    break;
                default: // Overworld
                    switch (fishingLiquid) {
                        case LAVA:
                            m_textureLiquid = TEXTURE_LIQUID_SKY;   m_drawingLiquid = DRAWING_LIQUID_LAVA;
                            break;
                        case VOID:
                            m_textureLiquid = TEXTURE_LIQUID_SKY;   m_drawingLiquid = DRAWING_LIQUID_VOID;
                            break;
                        case ANY:
                            m_textureLiquid = TEXTURE_OVERLAYS;     m_drawingLiquid = DRAWING_LIQUID_ANY;
                            break;
                        default:
                            m_textureLiquid = TEXTURE_LIQUID_SKY;   m_drawingLiquid = DRAWING_BIOME_LIQUIDS.get(FishRequirementData.texturePosFromBiomeTag(biomeTag));
                    }
                    switch (timeToFish) {
                        case NIGHT:
                            m_textureTime = TEXTURE_LIQUID_SKY;     m_drawingTime = DRAWING_NIGHT;
                            break;
                        case ANY:
                            m_textureTime = TEXTURE_OVERLAYS;       m_drawingTime = DRAWING_DAY_NIGHT;
                            break;
                        default:
                            m_textureTime = TEXTURE_LIQUID_SKY;     m_drawingTime = DRAWING_DAY;
                    }
                    m_textureSurroundings = TEXTURE_BIOME;          m_drawingSurroundings = DRAWING_BIOME_SURROUNDINGS.get(FishRequirementData.texturePosFromBiomeTag(biomeTag));
                    if (rainRequired) {
                        m_textureRain = TEXTURE_OVERLAYS;           m_drawingRain = DRAWING_RAIN;
                    }
                    else {
                        m_textureRain = TEXTURE_LIQUID_SKY;         m_drawingRain = DRAWING_TIMELESS;
                    }
                    if (thunderRequired) {
                        m_textureThunder = TEXTURE_OVERLAYS;        m_drawingThunder = DRAWING_THUNDERSTORM;
                    }
                    else {
                        m_textureThunder = TEXTURE_LIQUID_SKY;      m_drawingThunder = DRAWING_TIMELESS;
                    }
                    m_drawingYmeterLiquidLevel = DRAWING_SEA_LEVEL;
            }
        }
        
        public void drawMe(@NotNull Minecraft minecraft, int x, int y) {
            // Draw liquid
            minecraft.renderEngine.bindTexture(m_textureLiquid.texture);
            drawModalRectWithCustomSizedTexture(x, y, m_drawingLiquid.u, m_drawingLiquid.v, m_drawingLiquid.width, m_drawingLiquid.height, m_textureLiquid.textureWidth, m_textureLiquid.textureHeight);

            // Draw sky
            minecraft.renderEngine.bindTexture(m_textureTime.texture);
            drawModalRectWithCustomSizedTexture(x, y, m_drawingTime.u, m_drawingTime.v, m_drawingTime.width, m_drawingTime.height, m_textureTime.textureWidth, m_textureTime.textureHeight);
            
            // Draw Surroundings
            minecraft.renderEngine.bindTexture(m_textureSurroundings.texture);
            drawModalRectWithCustomSizedTexture(x, y, m_drawingSurroundings.u, m_drawingSurroundings.v, m_drawingSurroundings.width, m_drawingSurroundings.height, m_textureSurroundings.textureWidth, m_textureSurroundings.textureHeight);
            
            // Draw Rain
            minecraft.renderEngine.bindTexture(m_textureRain.texture);
            drawModalRectWithCustomSizedTexture(x, y, m_drawingRain.u, m_drawingRain.v, m_drawingRain.width, m_drawingRain.height, m_textureRain.textureWidth, m_textureRain.textureHeight);

            // Draw Thunderstorm
            minecraft.renderEngine.bindTexture(m_textureThunder.texture);
            drawModalRectWithCustomSizedTexture(x, y, m_drawingThunder.u, m_drawingThunder.v, m_drawingThunder.width, m_drawingThunder.height, m_textureThunder.textureWidth, m_textureThunder.textureHeight);


            // ---------- Y METER OVERLAY ----------
            minecraft.renderEngine.bindTexture(TEXTURE_OVERLAYS.texture);
            // Y Meter Background
            drawModalRectWithCustomSizedTexture(YMETER_X_START, YMETER_Y_START, DRAWING_YMETER_BACKGROUND.u, DRAWING_YMETER_BACKGROUND.v, DRAWING_YMETER_BACKGROUND.width, DRAWING_YMETER_BACKGROUND.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            // Y range
            minecraft.renderEngine.bindTexture(TEXTURE_OVERLAYS.texture);
            drawModalRectWithCustomSizedTexture(YMETER_X_START + 1, YMETER_Y_START + 1 + (MAX_Y_LEVEL - (m_maxYLevel / 5)), DRAWING_YRANGE.u, DRAWING_YRANGE.v + (MAX_Y_LEVEL - ((float) m_maxYLevel / 5)), DRAWING_YRANGE.width, m_maxYLevel / 5 - Math.max(m_minYLevel / 5, 0) + 1, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            // Y Meter Foreground
            minecraft.renderEngine.bindTexture(TEXTURE_OVERLAYS.texture);
            drawModalRectWithCustomSizedTexture(YMETER_X_START, YMETER_Y_START, DRAWING_YMETER_FOREGROUND.u, DRAWING_YMETER_FOREGROUND.v, DRAWING_YMETER_FOREGROUND.width, DRAWING_YMETER_FOREGROUND.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
            // Sea/Lava ocean/Void level line
            minecraft.renderEngine.bindTexture(TEXTURE_OVERLAYS.texture);
            drawModalRectWithCustomSizedTexture(YMETER_X_START -1, YMETER_Y_START + 1 + (MAX_Y_LEVEL - ((m_dimension == DimensionType.NETHER.getId() ? 31 : m_dimension == DimensionType.THE_END.getId() ? 5 : 62) / 5)), m_drawingYmeterLiquidLevel.u, m_drawingYmeterLiquidLevel.v, m_drawingYmeterLiquidLevel.width, m_drawingYmeterLiquidLevel.height, TEXTURE_OVERLAYS.textureWidth, TEXTURE_OVERLAYS.textureHeight);
        }
        
        
    }
    
    
    private static final short minigameBackgroundBarWidth = 128;
    private static final short minigameBackgroundBarHeight = 24;
    

    private static final short SLOT_FISH = 0;
    private static final short SLOT_FISHING_ROD = 1;
    private static final short SLOT_REEL = 2;
    private static final short SLOT_BOBBER = 3;
    private static final short SLOT_BAIT_BUCKET = 4;
    private static final short SLOT_BAITS_START = 5;
    
    public static final Texture TEXTURE_LIQUID_SKY = new Texture(new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_underoverlay.png"), 256, 256);
    public static final Texture TEXTURE_BIOME = new Texture(new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_biome.png"), 256, 256);
    public static final Texture TEXTURE_DIMENSION_CAVE = new Texture(new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_fullsize.png"), 256, 256);
    public static final Texture TEXTURE_OUTLINE = new Texture(new ResourceLocation("fishingmadebetter", "textures/gui/reeling_hud_outline.png"), 256, 256);
    public static final Texture TEXTURE_OVERLAYS = new Texture(new ResourceLocation("fishingmadebetter", "textures/gui/fish_data_overlays.png"), 256, 256);
    public static final Drawing DRAWING_OUTLINE = new Drawing(0, 0, 133 + 1, 29 + 1);
    public static final Drawing DRAWING_THE_END = new Drawing(0, 0, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_NETHER = new Drawing(0, 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final List<Drawing> DRAWING_BIOME_LIQUIDS = Arrays.asList(
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("WATER") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("JUNGLE") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("SANDY") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("COLD") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("MOUNTAIN") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("FOREST") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("PLAINS") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("SWAMP") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("MUSHROOM") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(128, FishRequirementData.texturePosFromBiomeTag("DEAD") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight));
    public static final List<Drawing> DRAWING_BIOME_SURROUNDINGS = Arrays.asList(
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("WATER") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("JUNGLE") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("SANDY") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("COLD") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("MOUNTAIN") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("FOREST") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("PLAINS") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("SWAMP") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("MUSHROOM") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight),
            new Drawing(0, FishRequirementData.texturePosFromBiomeTag("DEAD") * 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight));
    public static final Drawing DRAWING_LIQUID_LAVA = new Drawing(0, 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_LIQUID_VOID = new Drawing(0, 48, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_LIQUID_ANY = new Drawing(128, 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_DAY = new Drawing(0, 72, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_NIGHT = new Drawing(0, 96, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_DAY_NIGHT = new Drawing(128, 0, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_TIMELESS = new Drawing(0, 120, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_RAIN = new Drawing(0, 0, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_THUNDERSTORM = new Drawing(0, 24, minigameBackgroundBarWidth, minigameBackgroundBarHeight);
    public static final Drawing DRAWING_YMETER_BACKGROUND = new Drawing(0, 49, 6 + 1, 29 + 1);
    public static final Drawing DRAWING_YMETER_FOREGROUND = new Drawing(8, 49, 6 + 1, 29 + 1);
    public static final Drawing DRAWING_YRANGE = new Drawing(16, 49, 4 + 1, 27 + 1);
    public static final Drawing DRAWING_SEA_LEVEL = new Drawing(22, 49, 8 + 1, 1);
    public static final Drawing DRAWING_LAVA_LEVEL = new Drawing(22, 51, 8 + 1, 1);
    public static final Drawing DRAWING_VOID_LEVEL = new Drawing(22, 53, 8 + 1, 1);
    

}
