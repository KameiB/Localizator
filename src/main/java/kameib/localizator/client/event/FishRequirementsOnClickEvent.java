package kameib.localizator.client.event;

import kameib.localizator.Localizator;
import kameib.localizator.client.jei.fishingmadebetter.FMBJeiPlugin;
import kameib.localizator.util.FMB_BetterFishUtil;
import kameib.localizator.util.text.event.FishRequirementsClickEvent;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IRecipesGui;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.configuration.CustomConfigurationHandler;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import java.util.Objects;

@Mod.EventBusSubscriber
public class FishRequirementsOnClickEvent {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @Optional.Method(modid = "fishingmadebetter")
    public void onChatGuiClick(GuiScreenEvent.MouseInputEvent event) {
        if(!(event.getGui() instanceof GuiChat)) {
            return;
        }
        if(!Mouse.isButtonDown(0)) {
            return;
        }

        GuiNewChat guiChatGUI = event.getGui().mc.ingameGUI.getChatGUI();
        ITextComponent chatMessage = guiChatGUI.getChatComponent(Mouse.getEventX(), Mouse.getEventY());
        if (chatMessage == null) {
            return;
        }
        
        Style style = chatMessage.getStyle();
        Localizator.LOGGER.info("Chat message: " + chatMessage.getUnformattedComponentText() + " with ClickEvent: " + style);
        if (!(style.getClickEvent() instanceof FishRequirementsClickEvent)) {
            Localizator.LOGGER.warn("ERROR! ClickEvent details: " + style.getClickEvent() + ". Value: " + style.getClickEvent().getValue());
            return;
        }
        Localizator.LOGGER.info("The chat message " + chatMessage.getUnformattedText() + " contains a valid ClickEvent!");
        FishRequirementsClickEvent clickEvent = (FishRequirementsClickEvent) style.getClickEvent();
        // Check if the click event is of type FISH_REQUIREMENTS
        if (clickEvent.getFishRequirementsAction() != FishRequirementsClickEvent.FishRequirementsAction.FISH_REQUIREMENTS) {
            return;
        }
        String fishId = clickEvent.getFishId();
        if (!CustomConfigurationHandler.fishDataMap.containsKey(fishId)) {
            return;
        }
        ItemStack fishStack = FMB_BetterFishUtil.fishIdToItemStack(fishId);
        if (fishStack != null && !fishStack.isEmpty()) {
            if (Loader.isModLoaded("jei")) {
                openJEIRecipesGUI(fishStack);
            }
        }
        
    }

    @Optional.Method(modid = "jei")
    private void openJEIRecipesGUI(ItemStack fishStack) {
        // Get the JEI runtime
        IJeiRuntime jeiRuntime = FMBJeiPlugin.getJeiRuntime();
        if (jeiRuntime == null) {
            return;
        }

        // Get the recipes GUI from JEI runtime
        IRecipesGui recipesGui = jeiRuntime.getRecipesGui();
        IFocus<ItemStack> focus = new IFocus<ItemStack>() {
            @Override
            @Nonnull
            public ItemStack getValue() {
                return fishStack;
            }

            @Override
            @Nonnull
            public Mode getMode() {
                return Mode.OUTPUT;
            }
        };
        try {
            // Trigger the show recipes method for the specified fish
            Localizator.LOGGER.info("Showing the JEI recipe for " + fishStack.getDisplayName() + "...");
            recipesGui.show(focus);
        } catch (Exception e) {
            Localizator.LOGGER.error("Error showing " + fishStack.getDisplayName() + " recipe in JEI", e);
        }
    }
}
