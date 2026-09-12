package kameib.localizator.mixin.ichunutil;

import kameib.localizator.Localizator;
import me.ichun.mods.ichunutil.common.thread.ThreadGetResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ThreadGetResources.class)
public abstract class ThreadGetResourcesMixin {
    @Unique
    private static final String localizator$patronsNewURL = "https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/patrons.json";
    @Unique
    private static final String localizator$versionsNewURL = "https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/versions.json";
    
    @ModifyConstant(
            method = "run()V",
            constant = @Constant(stringValue = "https://raw.github.com/iChun/iChunUtil/master/src/main/resources/assets/ichunutil/mod/patrons.json"),
            remap = false
    )
    // Replace old broken URL with valid URL.
    // Line 33: Reader fileIn = new InputStreamReader((new URL("https://raw.github.com/iChun/iChunUtil/master/src/main/resources/assets/ichunutil/mod/patrons.json")).openStream());
    private static String localizator_ichunUtil_ThreadGetResources_run_patrons(String original) {
        Localizator.LOGGER.info("Localizator -> Redirecting iChunUtils Patrons list to the valid URL: {}",  localizator$patronsNewURL);
        return localizator$patronsNewURL;
    }
    
    @ModifyConstant(
            method = "run()V",
            constant = @Constant(stringValue = "https://raw.github.com/iChun/iChunUtil/master/src/main/resources/assets/ichunutil/mod/versions.json"),
            remap = false
    )
    // Replace old broken URL with valid URL.
    // Line 54: Reader fileIn = new InputStreamReader((new URL("https://raw.github.com/iChun/iChunUtil/master/src/main/resources/assets/ichunutil/mod/versions.json")).openStream());
    private static String localizator_ichunUtil_ThreadGetResources_run_versions(String original) {
        Localizator.LOGGER.info("Localizator -> Redirecting iChunUtils Versions list to the valid URL: {}",  localizator$versionsNewURL);
        return localizator$versionsNewURL;
    }
}
