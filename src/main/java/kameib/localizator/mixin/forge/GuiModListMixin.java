package kameib.localizator.mixin.forge;

import kameib.localizator.data.Production;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GuiModList.class)
public abstract class GuiModListMixin {
    @Shadow(remap = false) private ModContainer selectedMod;    
        
    @ModifyArg(
            method = "initGui()V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1),
            remap = Production.inProduction
    )
    // Localize "Config" button text
    // Line 176: this.buttonList.add(configModButton);
    private Object localizator_Forge_GuiModList_initGui_configButton(Object button) {
        ((GuiButton)button).displayString = I18n.format("fml.button.config");
        return button;        
    }
    
    @ModifyArg(
            method = "initGui()V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2),
            remap = Production.inProduction
    )
    // Localize "Disable" button text
    // Line 177: this.buttonList.add(disableModButton);
    private Object localizator_Forge_GuiModList_initGui_disableButton(Object button) {
        ((GuiButton)button).displayString = I18n.format("fml.button.disable");
        return button;
    }
        
    @ModifyArg(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/GuiModList;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V"
            ),
            index = 1,
            remap = Production.inProduction
    )
    // Localize "Mod List" screen text
    // Line 314: this.drawCenteredString(this.fontRenderer, "Mod List", left, 16, 0xFFFFFF);
    private String localizator_Forge_GuiModList_drawScreen_ModList(String modList) {
        return I18n.format("fml.menu.mods.modlist");
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0
            ),
            remap = false
    )
    // Gives a little color to the Mod's name. TODO: Add an option to customize both colors
    // Line 423: lines.add(selectedMod.getMetadata().name);
    private Object localizator_Forge_GuiModList_updateCache_Name(Object e) {
        return TextFormatting.AQUA + (String)e;
    }
    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 1
            ),
            remap = false
    )
    // Localize hardcoded "Version:" text
    // Line 424: lines.add(String.format("Version: %s (%s)", selectedMod.getDisplayVersion(), selectedMod.getVersion()));
    private Object localizator_Forge_GuiModList_updateCache_Version(Object e) {
        return I18n.format("fml.mod.details.version",  TextFormatting.AQUA + selectedMod.getDisplayVersion() + TextFormatting.WHITE, TextFormatting.AQUA + selectedMod.getVersion() + TextFormatting.WHITE);
    }
    
    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 2
            ),
            remap = false
    )
    // Localize hardcoded "Mod ID:" text
    // Line 425: lines.add(String.format("Mod ID: '%s' Mod State: %s", selectedMod.getModId(), Loader.instance().getModState(selectedMod)));
    private Object localizator_Forge_GuiModList_updateCache_modID(Object e) {
        return I18n.format("fml.mod.details.modid", 
                TextFormatting.AQUA + selectedMod.getModId() + TextFormatting.WHITE,
                TextFormatting.AQUA + I18n.format(Loader.instance().getModState(selectedMod).toString()) + TextFormatting.WHITE);
    }    
    
    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 3
            ),
            remap = false
    )
    // Localize hardcoded "Credits:" text
    // Line 429: lines.add("Credits: " + selectedMod.getMetadata().credits);
    private Object localizator_Forge_GuiModList_updateCache_credits(Object e) {
        return I18n.format("fml.mod.details.credits",
                 TextFormatting.AQUA + (I18n.hasKey(selectedMod.getMetadata().credits) ? 
                        I18n.format(selectedMod.getMetadata().credits) : 
                        selectedMod.getMetadata().credits));
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 4
            ),
            remap = false
    )
    // Localize hardcoded "Authors:" text
    // Line 432: lines.add("Authors: " + selectedMod.getMetadata().getAuthorList());
    private Object localizator_Forge_GuiModList_updateCache_authors(Object e) {
        return I18n.format("fml.mod.details.authors", 
                TextFormatting.AQUA + selectedMod.getMetadata().getAuthorList());
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 6
            ),
            remap = false
    )
    // Localize hardcoded "No child mods for this mod" text
    // Line 436: lines.add("No child mods for this mod");
    private Object localizator_Forge_GuiModList_updateCache_noChildmods(Object e) {
        return I18n.format("fml.mod.details.nochildmods");
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 7
            ),
            remap = false
    )
    // Localize hardcoded "Child mods:" text
    // Line 438: lines.add("Child mods: " + selectedMod.getMetadata().getChildModList());
    private Object localizator_Forge_GuiModList_updateCache_childmods(Object e) {
        return I18n.format("fml.mod.details.childmods", 
                TextFormatting.AQUA + selectedMod.getMetadata().getChildModList() + TextFormatting.WHITE);
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 8
            ),
            remap = false
    )
    // Localize hardcoded "Update Available: " text
    // Line 441: lines.add("Update Available: " + (vercheck.url == null ? "" : vercheck.url));
    private Object localizator_Forge_GuiModList_updateCache_updateAvailable(Object e) {
        if (e instanceof String) {
            String vercheckURL = (String)e;
            return I18n.format("fml.mod.details.updateavailable",
                    TextFormatting.AQUA + vercheckURL.replace("Update Available: ", ""));
        }
        return e;
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 10
            ),
            remap = false
    )
    // In case a mod developer wants their mod description to be translated :D
    // Line 444: lines.add(selectedMod.getMetadata().description);
    private Object localizator_Forge_GuiModList_updateCache_description(Object e) {
        return TextFormatting.AQUA + (I18n.hasKey(selectedMod.getMetadata().description) ? 
                I18n.format(selectedMod.getMetadata().description) : 
                selectedMod.getMetadata().description);
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 12
            ),
            remap = false
    )
    // If the mod has no mcmod.info file...
    // Line 449: lines.add(WHITE + "Version: " + selectedMod.getVersion());
    private Object localizator_Forge_GuiModList_updateCache_autoGeneratedVersion(Object e) {
        return I18n.format("fml.mod.details.autogenerated.version", 
                  TextFormatting.AQUA + selectedMod.getVersion());
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 13
            ),
            remap = false
    )
    // If the mod has no info file...
    // Line 450: lines.add(WHITE + "Mod State: " + Loader.instance().getModState(selectedMod));
    private Object localizator_Forge_GuiModList_updateCache_autoGeneratedModState(Object e) {
        return I18n.format("fml.mod.details.autogenerated.modstate", 
                TextFormatting.AQUA + Loader.instance().getModState(selectedMod).toString());
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 14
            ),
            remap = false
    )
    // If the mod has no info file...
    // Line 452: lines.add("Update Available: " + (vercheck.url == null ? "" : vercheck.url));
    private Object localizator_Forge_GuiModList_updateCache_updateAvailable2(Object e) {
        if (e instanceof String) {
            String vercheckURL = (String)e;
            return I18n.format("fml.mod.details.updateavailable", 
                    TextFormatting.AQUA + vercheckURL.replace("Update Available: ", ""));
        }
        return e;
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 16
            ),
            remap = false
    )
    // If the mod has no info file...
    // Line 455: lines.add(RED + "No mod information found");
    private Object localizator_Forge_GuiModList_updateCache_autoGeneratedNoModInfo(Object e) {
        return TextFormatting.RED + I18n.format("fml.mod.details.autogenerated.nomodinfo");
    }
    
    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 17
            ),
            remap = false
    )
    // If the mod has no info file...
    // Line 456: lines.add(RED + "Ask your mod author to provide a mod mcmod.info file");
    private Object localizator_Forge_GuiModList_updateCache_autoGeneratedAskAuthor(Object e) {
        return TextFormatting.RED + I18n.format("fml.mod.details.autogenerated.askauthor");
    }

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 19
            ),
            remap = false
    )
    // If there are changes between one version and the other...
    // Line 462: lines.add("Changes:");
    private Object localizator_Forge_GuiModList_updateCache_updateChanges(Object e) {
        return I18n.format("fml.mod.details.updatechanges");
    }
}
