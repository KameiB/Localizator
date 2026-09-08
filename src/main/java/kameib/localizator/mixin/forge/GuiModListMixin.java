package kameib.localizator.mixin.forge;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GuiModList.class)
public abstract class GuiModListMixin {
    @ModifyConstant(
            method = "initGui()V",
            constant = @Constant(stringValue = "Config"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Localize the hardcoded "Config" text at the moment of this.configModButton initialization
    // Line 101: this.configModButton = new GuiButton(20, 10, this.height - 49, this.listWidth, 20, "Config");
    private static String localizator_FML_GuiModList_initGui_configModButton_constant(String original) {
        return I18n.format("fml.button.config");
    }

    @ModifyConstant(
            method = "initGui()V",
            constant = @Constant(stringValue = "Disable"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Localize the hardcoded "Mod List" text at the moment of this.configModButton initialization
    // Line 102: this.disableModButton = new GuiButton(21, 10, this.height - 27, this.listWidth, 20, "Disable");
    private static String localizator_FML_GuiModList_drawScreen_modList(String original) {
        return I18n.format("fml.button.disable");
    }

    @ModifyConstant(
            method = "drawScreen(IIF)V",
            constant = @Constant(stringValue = "Mod List"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Localize the hardcoded "Mod List" text before passing it to drawCenteredString
    // Line 214: this.drawCenteredString(this.fontRenderer, "Mod List", left, 16, 16777215);
    private static String localizator_FML_GuiModList_initGui_disableModButton_constant(String original) {
        return I18n.format("fml.menu.mods.modlist");
    }
    
    @Shadow(remap = false) private ModContainer selectedMod;    

    @ModifyArg(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0
            ),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Gives a little color to the Mod's name, depending on its lang key value.
    // Line 304: lines.add(selectedMod.getMetadata().name);
    private Object localizator_Forge_GuiModList_updateCache_Name(Object modName) {
        return I18n.format("fml.mod.name.format") + (String)modName;
    }
    
    @Redirect(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Instead of calling String.format, which generates an English string, we'll call I18n.format, using a lang key that receives the same arguments.
    // Plus! Now you can customize the colors to your heart's content, at the lang key :D
    // Line 305: lines.add(String.format("Version: %s (%s)", this.selectedMod.getDisplayVersion(), this.selectedMod.getVersion()));
    private static String localizator_FML_GuiModList_updateCache_Version(String s, Object[] params) {
        return I18n.format("fml.mod.details.version", params);
    }
    
    @Redirect(
            method = "updateCache()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "Mod ID:" text
    // Line 306: lines.add(String.format("Mod ID: '%s' Mod State: %s", selectedMod.getModId(), Loader.instance().getModState(selectedMod)));
    private static String localizator_FML_GuiModList_updateCache_modID(String s, Object[] params) {
        return I18n.format("fml.mod.details.modid", params);
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
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "Credits:" text.
    // Plus, if the mod author created a lang key for the mod's credits, translate it!
    // Line 308: lines.add("Credits: " + selectedMod.getMetadata().credits);
    private Object localizator_Forge_GuiModList_updateCache_credits(Object e) {
        return I18n.format("fml.mod.details.credits",
                  (I18n.hasKey(selectedMod.getMetadata().credits) ? 
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
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "Authors:" text
    // Line 311: lines.add("Authors: " + selectedMod.getMetadata().getAuthorList());
    private Object localizator_Forge_GuiModList_updateCache_authors(Object e) {
        return I18n.format("fml.mod.details.authors", selectedMod.getMetadata().getAuthorList());
    }

    @ModifyConstant(
            method = "updateCache()V",
            constant = @Constant(stringValue = "No child mods for this mod"),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "No child mods for this mod" text
    // Line 436: lines.add("No child mods for this mod");
    private String localizator_Forge_GuiModList_updateCache_noChildMods(String original) {
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
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "Child mods:" text
    // Line 438: lines.add("Child mods: " + selectedMod.getMetadata().getChildModList());
    private Object localizator_Forge_GuiModList_updateCache_childMods(Object e) {
        return I18n.format("fml.mod.details.childmods", 
                 selectedMod.getMetadata().getChildModList());
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
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "Update Available: " text
    // Line 441: lines.add("Update Available: " + (vercheck.url == null ? "" : vercheck.url));
    private Object localizator_Forge_GuiModList_updateCache_updateAvailable(Object e) {
        if (e instanceof String) {
            String vercheckURL = (String)e;
            return I18n.format("fml.mod.details.updateavailable",
                    vercheckURL.replace("Update Available: ", ""));
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
    @SideOnly(Side.CLIENT)
    // In case a mod developer wants their mod description to be translated :D
    // Line 444: lines.add(selectedMod.getMetadata().description);
    private Object localizator_Forge_GuiModList_updateCache_description(Object e) {
        return I18n.format("fml.mod.name.format") + (I18n.hasKey(selectedMod.getMetadata().description) ? 
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
    @SideOnly(Side.CLIENT)
    // If the mod has no mcmod.info file...
    // Line 327: lines.add(TextFormatting.WHITE + "Version: " + this.selectedMod.getVersion());
    private Object localizator_Forge_GuiModList_updateCache_autoGeneratedVersion(Object e) {
        return I18n.format("fml.mod.details.autogenerated.version", 
                  selectedMod.getVersion());
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
    @SideOnly(Side.CLIENT)
    // If the mod has no info file...
    // Line 328: lines.add(TextFormatting.WHITE + "Mod State: " + Loader.instance().getModState(this.selectedMod));
    private Object localizator_Forge_GuiModList_updateCache_autoGeneratedModState(Object e) {
        return I18n.format("fml.mod.details.autogenerated.modstate", 
                Loader.instance().getModState(selectedMod).toString());
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
    @SideOnly(Side.CLIENT)
    // If the mod has no info file...
    // Line 330: lines.add("Update Available: " + (vercheck.url == null ? "" : vercheck.url));
    private Object localizator_Forge_GuiModList_updateCache_updateAvailable2(Object e) {
        if (e instanceof String) {
            String vercheckURL = (String)e;
            return I18n.format("fml.mod.details.updateavailable", 
                    vercheckURL.replace("Update Available: ", ""));
        }
        return e;
    }

    @ModifyConstant(
            method = "updateCache()V",
            constant = @Constant(stringValue = "No mod information found"),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // If the mod has no info file...
    // Line 334: lines.add(RED + "No mod information found");
    private String localizator_Forge_GuiModList_updateCache_autoGeneratedNoModInfo(String original) {
        return I18n.format("fml.mod.details.autogenerated.nomodinfo");
    }
    
    @ModifyConstant(
            method = "updateCache()V",
            constant = @Constant(stringValue = "Ask your mod author to provide a mod mcmod.info file"),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // If the mod has no info file...
    // Line 335: lines.add(RED + "Ask your mod author to provide a mod mcmod.info file");
    private String localizator_Forge_GuiModList_updateCache_autoGeneratedAskAuthor(String original) {
        return I18n.format("fml.mod.details.autogenerated.askauthor");
    }

    @ModifyConstant(
            method = "updateCache()V",
            constant = @Constant(stringValue = "Changes:"),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // If there are changes between one version and the other...
    // Line 340: lines.add("Changes:");
    private String localizator_Forge_GuiModList_updateCache_updateChanges(String original) {
        return I18n.format("fml.mod.details.updatechanges");
    }
}
