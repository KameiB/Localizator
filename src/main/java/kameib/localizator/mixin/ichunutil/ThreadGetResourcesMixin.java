package kameib.localizator.mixin.ichunutil;

import kameib.localizator.Localizator;
import me.ichun.mods.ichunutil.common.thread.ThreadGetResources;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Mixin(ThreadGetResources.class)
public abstract class ThreadGetResourcesMixin {
    @Redirect(
            method = "run()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/net/URL;openStream()Ljava/io/InputStream;",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Replace patrons URL to a valid one
    // Line 34: fileIn = new InputStreamReader((new URL("https://raw.github.com/iChun/iChunUtil/master/src/main/resources/assets/ichunutil/mod/patrons.json")).openStream());
    private InputStream IChunUtil_ThreadGetResources_run_patrons(URL instance) throws IOException {
        Localizator.LOGGER.info("Redirecting patrons call to the valid URL: {}", patronList);
        return (new URL("https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/patrons.json")).openStream();
    }

    @Redirect(
            method = "run()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/net/URL;openStream()Ljava/io/InputStream;",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Replace versions URL to a valid one
    // Line 59: fileIn = new InputStreamReader((new URL("https://raw.github.com/iChun/iChunUtil/master/src/main/resources/assets/ichunutil/mod/versions.json")).openStream());
    private InputStream IChunUtil_ThreadGetResources_run_version(URL instance) throws IOException {
        Localizator.LOGGER.info("Redirecting versions call to the valid URL: {}", versionList);
        return (new URL("https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/versions.json")).openStream();
    }
    
    @Final @Mutable
    @Shadow(remap = false)
    private static String patronList = "https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/patrons.json";
    @Final @Mutable
    @Shadow(remap = false)
    private static String versionList = "https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/versions.json";
}
