package kameib.localizator.mixin;

import kameib.localizator.data.ConfigToMixin;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import kameib.localizator.Localizator;
import kameib.localizator.handlers.ForgeConfigHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Loads non-vanilla and non-coremod mixins late in order to prevent ClassNotFound exceptions
 * Code based on original MIT Licensed code:
 * https://github.com/DimensionalDevelopment/JustEnoughIDs/blob/master/src/main/java/org/dimdev/jeid/mixin/init/JEIDMixinLoader.java
 */
@Mixin(Loader.class)
public class LocalizatorLoader {
    private static final Map<String, List<ConfigToMixin>> supportedMods = initSupportedModsMap();
    private static Map<String, List<ConfigToMixin>> initSupportedModsMap() {
        Map<String, List<ConfigToMixin>> map = new HashMap<>();
        // Neat
        List<ConfigToMixin> neatList = new ArrayList<>();
        neatList.add(new ConfigToMixin("(Neat) Health Bar Mixin", ForgeConfigHandler.getBoolean("(Neat) Health Bar Mixin"), "mixins.neat.healthbar.json"));
        map.put("neat", neatList);
        // ArmorUnderwear
        List<ConfigToMixin> armorunderList = new ArrayList<>();
        armorunderList.add(new ConfigToMixin("(ArmorUnder) Ozzy Liner text Mixin", ForgeConfigHandler.getBoolean("(ArmorUnder) Ozzy Liner text Mixin"), "mixins.armorunder.ozzyliner.json"));
        map.put("armorunder", armorunderList);
        // SetBonus
        List<ConfigToMixin> setbonusList = new ArrayList<>();
        setbonusList.add(new ConfigToMixin("(SetBonus) Tooltip Mixin", ForgeConfigHandler.getBoolean("(SetBonus) Tooltip Mixin"), "mixins.setbonus.tooltip.json"));
        map.put("setbonus", setbonusList);
        // ForgottenItems
        List<ConfigToMixin> forgottenitemsList = new ArrayList<>();
        forgottenitemsList.add(new ConfigToMixin("(ForgottenItems) Tooltips Mixin", ForgeConfigHandler.getBoolean("(ForgottenItems) Tooltips Mixin"), "mixins.forgottenitems.descriptions.json"));
        map.put("forgottenitems", forgottenitemsList);
        // SRParasites
        List<ConfigToMixin> srparasitesList = new ArrayList<>();
        srparasitesList.add(new ConfigToMixin("(SRParasites) Armor Tooltips Mixin", ForgeConfigHandler.getBoolean("(SRParasites) Armor Tooltips Mixin"), "mixins.srparasites.armortooltip.json"));
        map.put("srparasites", srparasitesList);
        // Better Survival
        List<ConfigToMixin> bettersurvivalList = new ArrayList<>();
        bettersurvivalList.add(new ConfigToMixin("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin", ForgeConfigHandler.getBoolean("(BetterSurvival) Potion-Imbued Weapons Tooltip Mixin"), "mixins.bettersurvival.tooltip.json"));
        map.put("mujmajnkraftsbettersurvival", bettersurvivalList);
        // Bountiful Baubles
        List<ConfigToMixin> bountifulbaublesList = new ArrayList<>();
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Tooltip fixes Mixin", ForgeConfigHandler.getBoolean("(BountifulBaubles) Tooltip fixes Mixin"), "mixins.bountifulbaubles.tooltips.json"));
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Ankh Charm Web Immune Mixin", ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Charm Web Immune Mixin"), "mixins.bountifulbaubles.ankhcharmcobweb.json"));
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) Ankh Shield Web Immune Mixin", ForgeConfigHandler.getBoolean("(BountifulBaubles) Ankh Shield Web Immune Mixin"), "mixins.bountifulbaubles.ankhshieldcobweb.json"));
        map.put("bountifulbaubles", bountifulbaublesList);
        // Rough Tweaks
        List<ConfigToMixin> roughtweaksList = new ArrayList<>();
        roughtweaksList.add(new ConfigToMixin("(RoughTweaks) Localized Names and Better Tooltip Mixin", ForgeConfigHandler.getBoolean("(RoughTweaks) Localized Names and Better Tooltip Mixin"), "mixins.roughtweaks.langkeys.json"));
        map.put("roughtweaks", roughtweaksList);
        // Serene Seasons
        List<ConfigToMixin> sereneseasonsList = new ArrayList<>();
        sereneseasonsList.add(new ConfigToMixin("(SereneSeasons) Localized Crop Fertility Tooltip Mixin", ForgeConfigHandler.getBoolean("(SereneSeasons) Localized Crop Fertility Tooltip Mixin"), "mixins.sereneseasons.tooltip.json"));
        map.put("sereneseasons", sereneseasonsList);
        // Charm
        List<ConfigToMixin> charmList = new ArrayList<>();
        charmList.add(new ConfigToMixin("(Charm) Localized Cake Name Mixin", ForgeConfigHandler.getBoolean("(Charm) Localized Cake Name Mixin"), "mixins.charm.cakename.json"));
        charmList.add(new ConfigToMixin("(Charm) Localized Composter JEI Mixin", ForgeConfigHandler.getBoolean("(Charm) Localized Composter JEI Mixin"), "mixins.charm.compostergui.json"));
        charmList.add(new ConfigToMixin("(Charm) Localized Container Name Mixin", ForgeConfigHandler.getBoolean("(Charm) Localized Container Name Mixin"), "mixins.charm.containername.json"));
        map.put("charm", charmList);
        // Dynamic Trees
        List<ConfigToMixin> dynamictreesList = new ArrayList<>();
        dynamictreesList.add(new ConfigToMixin("(DyamicTrees) Staff Mixin", ForgeConfigHandler.getBoolean("(DyamicTrees) Staff Mixin"), "mixins.dynamictrees.staff.json"));
        map.put("dynamictrees", dynamictreesList);
        // WAILA
        List<ConfigToMixin> wailaList = new ArrayList<>();
        wailaList.add(new ConfigToMixin("(WAILA) Entity Name Mixin", ForgeConfigHandler.getBoolean("(WAILA) Entity Name Mixin"), "mixins.waila.entityname.json"));
        map.put("waila", wailaList);
        // Corpse Complex
        List<ConfigToMixin> corpsecomplexList = new ArrayList<>();
        corpsecomplexList.add(new ConfigToMixin("(CorpseComplex) Grave Scroll Description Mixin", ForgeConfigHandler.getBoolean("(CorpseComplex) Grave Scroll Description Mixin"), "mixins.corpsecomplex.scroll.json"));
        map.put("corpsecomplex", corpsecomplexList);
        // Minecraft Forge FML
        List<ConfigToMixin> forgeList = new ArrayList<>();
        forgeList.add(new ConfigToMixin("(FML) Localized Mod List GUI Mixin", ForgeConfigHandler.getBoolean("(FML) Localized Mod List GUI Mixin"), "mixins.forge.modlist.json"));
        map.put("FML", forgeList);
        // Lycanites
        List<ConfigToMixin> lycanitesList = new ArrayList<>();
        lycanitesList.add(new ConfigToMixin("(Lycanites) Client-side Translated Messages Mixin", ForgeConfigHandler.getBoolean("(Lycanites) Client-side Translated Messages Mixin"), "mixins.lycanites.messages.json"));
        map.put("lycanitesmobs", lycanitesList);
        // Battle Towers
        List<ConfigToMixin> battletowersList = new ArrayList<>();
        battletowersList.add(new ConfigToMixin("(BattleTowers) Golem Defeated Message Mixin", ForgeConfigHandler.getBoolean("(BattleTowers) Golem Defeated Message Mixin"), "mixins.battletowers.messages.json"));
        map.put("battletowers", battletowersList);
        // Callable Horses
        List<ConfigToMixin> callablehorsesList = new ArrayList<>();
        callablehorsesList.add(new ConfigToMixin("(Callable Horses) Horse Messages Mixin", ForgeConfigHandler.getBoolean("(Callable Horses) Horse Messages Mixin"), "mixins.callablehorses.messages.json"));
        map.put("callablehorses", callablehorsesList);
        // Item Physic
        List<ConfigToMixin> itemphysicList = new ArrayList<>();
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) Power: Status Message Mixin", ForgeConfigHandler.getBoolean("(ItemPhysic) Power: Status Message Mixin"), "mixins.itemphysic.message.json"));
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) ERRORED patch Mixin", ForgeConfigHandler.getBoolean("(ItemPhysic) ERRORED patch Mixin"), "mixins.itemphysic.erroredpatch.json"));
        itemphysicList.add(new ConfigToMixin("(ItemPhysic) Reverse Description Mixin", ForgeConfigHandler.getBoolean("(ItemPhysic) Reverse Description Mixin"), "mixins.itemphysic.reversedescription.json"));
        map.put("itemphysic", itemphysicList);
        // Recurrent Complex
        List<ConfigToMixin> reccomplexList = new ArrayList<>();
        reccomplexList.add(new ConfigToMixin("(RecComplex) Artifact Names Mixin", ForgeConfigHandler.getBoolean("(RecComplex) Artifact Names Mixin"), "mixins.reccomplex.artifacts.json"));
        map.put("reccomplex", reccomplexList);
        // iChunUtil
        List<ConfigToMixin> ichunutilList = new ArrayList<>();
        ichunutilList.add(new ConfigToMixin("(iChunUtil) Fix Patrons and Version URL Mixin", ForgeConfigHandler.getBoolean("(iChunUtil) Fix Patrons and Version URL Mixin"), "mixins.ichunutil.urls.json"));
        map.put("ichunutil", ichunutilList);
        // Varied Commodities
        List<ConfigToMixin> variedCommoditiesList = new ArrayList<>();
        variedCommoditiesList.add(new ConfigToMixin("(VariedComm) Localized Book Mixin", ForgeConfigHandler.getBoolean("(VariedComm) Localized Book Mixin"), "mixins.variedcommodities.book.json"));
        map.put("variedcommodities", variedCommoditiesList);
        // Fishing Made Better
        List<ConfigToMixin> FMBList = new ArrayList<>();
        FMBList.add(new ConfigToMixin("(FishingMadeBetter) Localized Fishes Mixin", ForgeConfigHandler.getBoolean("(FishingMadeBetter) Localized Fishes Mixin"), "mixins.fmb.fishes.etc.json"));
        FMBList.add(new ConfigToMixin("(FishingMadeBetter) Instructions on Minigame Mixin", ForgeConfigHandler.getBoolean("(FishingMadeBetter) Instructions on Minigame Mixin"), "mixins.fmb.minigame.json"));
        map.put("fishingmadebetter", FMBList);
        // Fancy Block Particles
        List<ConfigToMixin> FBPList = new ArrayList<>();
        FBPList.add(new ConfigToMixin("(FBP) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(FBP) Localized Keybinds Mixin"), "mixins.fbp.keybinds.json"));
        map.put("fbp", FBPList);
        // LevelUp2
        List<ConfigToMixin> levelUp2List = new ArrayList<>();
        levelUp2List.add(new ConfigToMixin("(LevelUp2) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(LevelUp2) Localized Keybinds Mixin"), "mixins.levelup2.keybinds.json"));
        map.put("levelup2", levelUp2List);
        // Mo'Bends
        List<ConfigToMixin> mobendsList = new ArrayList<>();
        mobendsList.add(new ConfigToMixin("(Mo'Bends) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(Mo'Bends) Localized Keybinds Mixin"), "mixins.mobends.keybinds.json"));
        map.put("mobends", mobendsList);
        // Scaling Health
        List<ConfigToMixin> scalingHealthList = new ArrayList<>();
        scalingHealthList.add(new ConfigToMixin("(ScalingHealth) Localized Keybinds Mixin", ForgeConfigHandler.getBoolean("(ScalingHealth) Localized Keybinds Mixin"), "mixins.scalinghealth.keybinds.json"));
        map.put("scalinghealth", scalingHealthList);

        return Collections.unmodifiableMap(map);
    }
    @Shadow(remap = false)
    private List<ModContainer> mods;
    @Shadow(remap = false)
    private ModClassLoader modClassLoader;

    /**
     * @reason Load all mods now and load mod support mixin configs. This can't be done later
     * since constructing mods loads classes from them.
     */
    @Inject(method = "loadMods", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/LoadController;transition(Lnet/minecraftforge/fml/common/LoaderState;Z)V", ordinal = 1), remap = false)
    private void beforeConstructingMods(List<String> nonMod, CallbackInfo ci) {
        List<String> modIdList = new ArrayList<>();
        // Add all mods to class loader
        for (ModContainer mod : mods) {
            try {
                modClassLoader.addFile(mod.getSource());
                modIdList.add(mod.getModId());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        // Add and reload mixin configs
        for (Map.Entry<String, List<ConfigToMixin>> entry : supportedMods.entrySet()) {
            if (modIdList.contains(entry.getKey())) {
                for (ConfigToMixin config : entry.getValue()) {
                    if (config.isEnabled()) {
                        Localizator.LOGGER.info("[Localizator] Late Loading: " + config.getName());
                        Mixins.addConfiguration(config.getJson());
                    }
                }
            }
        }

        try {
            // This will very likely break on the next major mixin release.
            Class<?> proxyClass = Class.forName("org.spongepowered.asm.mixin.transformer.Proxy");
            Field transformerField = proxyClass.getDeclaredField("transformer");
            transformerField.setAccessible(true);
            Object transformer = transformerField.get(null);

            Class<?> mixinTransformerClass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinTransformer");
            Field processorField = mixinTransformerClass.getDeclaredField("processor");
            processorField.setAccessible(true);
            Object processor = processorField.get(transformer);

            Class<?> mixinProcessorClass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinProcessor");

            Field extensionsField = mixinProcessorClass.getDeclaredField("extensions");
            extensionsField.setAccessible(true);
            Object extensions = extensionsField.get(processor);

            Method selectConfigsMethod = mixinProcessorClass.getDeclaredMethod("selectConfigs", MixinEnvironment.class);
            selectConfigsMethod.setAccessible(true);
            selectConfigsMethod.invoke(processor, MixinEnvironment.getCurrentEnvironment());

            // Mixin 0.8.4+
            try {
                Method prepareConfigs = mixinProcessorClass.getDeclaredMethod("prepareConfigs", MixinEnvironment.class, Extensions.class);
                prepareConfigs.setAccessible(true);
                prepareConfigs.invoke(processor, MixinEnvironment.getCurrentEnvironment(), extensions);
                return;
            } catch (NoSuchMethodException ex) {
                // no-op
            }

            // Mixin 0.8+
            try {
                Method prepareConfigs = mixinProcessorClass.getDeclaredMethod("prepareConfigs", MixinEnvironment.class);
                prepareConfigs.setAccessible(true);
                prepareConfigs.invoke(processor, MixinEnvironment.getCurrentEnvironment());
                return;
            } catch (NoSuchMethodException ex) {
                // no-op
            }

            throw new UnsupportedOperationException("Unsupported Mixin");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

