package localizator.mixin;

import localizator.data.ConfigToMixin;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import localizator.Localizator;
import localizator.handlers.ForgeConfigHandler;

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
        // ItemPhysic
        //List<ConfigToMixin> itemphysicList = new ArrayList<>();
        //itemphysicList.add(new ConfigToMixin("(ItemPhysic) Throw Power Mixin", ForgeConfigHandler.getBoolean("(ItemPhysic) Throw Power Mixin"), "mixins.itemphysic.throwpower.json"));
        //map.put("creativecore", itemphysicList);
        // Bountiful Baubles
        List<ConfigToMixin> bountifulbaublesList = new ArrayList<>();
        bountifulbaublesList.add(new ConfigToMixin("(BountifulBaubles) No Modifier at Name Mixin", ForgeConfigHandler.getBoolean("(BountifulBaubles) No Modifier at Name Mixin"), "mixins.bountifulbaubles.attributeatname.json"));
        map.put("bountifulbaubles", bountifulbaublesList);
        // Rough Tweaks
        List<ConfigToMixin> roughtweaksList = new ArrayList<>();
        roughtweaksList.add(new ConfigToMixin("(RoughTweaks) Localized Names and Better Tooltip Mixin", ForgeConfigHandler.getBoolean("(RoughTweaks) Localized Names and Better Tooltip Mixin"), "mixins.roughtweaks.tooltip.json"));
        map.put("roughtweaks", roughtweaksList);
        // Serene Seasons
        List<ConfigToMixin> sereneseasonsList = new ArrayList<>();
        sereneseasonsList.add(new ConfigToMixin("(SereneSeasons) Localized Crop Fertility Tooltip Mixin", ForgeConfigHandler.getBoolean("(SereneSeasons) Localized Crop Fertility Tooltip Mixin"), "mixins.sereneseasons.tooltip.json"));
        map.put("sereneseasons", sereneseasonsList);

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
                        Localizator.LOGGER.log(Level.INFO, "[Localizator] Late Loading: " + config.getName());
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