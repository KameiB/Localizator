package localizator.mixin.minecraft;

import localizator.Localizator;
import net.minecraft.util.text.translation.LanguageMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;

@Mixin(LanguageMap.class)
public abstract class LanguageMapMixin {
    /**
     * @author KameiB
     * @reason Charm's stupid "item.charm:record_creative6" lang key at es_MX and de_DE lang files.
     * Trust me, I didn't want to.
     */
    @ModifyArg(
            method = "inject(Lnet/minecraft/util/text/translation/LanguageMap;Ljava/io/InputStream;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;putAll(Ljava/util/Map;)V"
            ),
            remap = false
    )
    private static Map<String, String> localizator_charm_LanguageMap_inject_curateMap(Map<String, String> map) {
        for (String key : map.keySet()) {
            Localizator.LOGGER.info("[Localizator] Found lang key: " + key);
        }
        
        String translation = map.remove("item.charm:record_creative6");
        if (translation != null) {
            Localizator.LOGGER.info("[Localizator -> Charm] item.charm:record_creative6 lang key found!");
            if (!map.containsKey("item.charm:record_creative6.name")) {
                Localizator.LOGGER.info("[Localizator -> Charm] Replacing item.charm:record_creative6 lang key...");
                map.put("item.charm:record_creative6.name", translation);
            }
        }
        return map;
    }
    
}
