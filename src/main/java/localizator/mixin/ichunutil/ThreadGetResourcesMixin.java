package localizator.mixin.ichunutil;

import com.google.gson.Gson;
import localizator.Localizator;
import me.ichun.mods.ichunutil.common.iChunUtil;
import me.ichun.mods.ichunutil.common.module.update.UpdateChecker;
import me.ichun.mods.ichunutil.common.thread.ThreadGetResources;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

@Mixin(ThreadGetResources.class)
public abstract class ThreadGetResourcesMixin {
    /**
     * @author KameiB
     * @reason Point the Patron and Version calls to a valid URL, because the branch "master" no longer exists.
     */
    @Overwrite(remap = false)
    public void run()
    {
        //Check to see if the current client is a patron.
        if(side.isClient())
        {
            try
            {
                Gson gson = new Gson();
                Localizator.LOGGER.info("[Localizator->iChunUtil] Redirecting Patron call to the valid URL: " + patronList);
                Reader fileIn = new InputStreamReader(new URL(patronList).openStream());
                String[] json = gson.fromJson(fileIn, String[].class);
                fileIn.close();

                if(json != null)
                {
                    for(String s : json)
                    {
                        if(s.replaceAll("-", "").equalsIgnoreCase(iChunUtil.proxy.getPlayerId()))
                        {
                            iChunUtil.userIsPatron = true;
                            iChunUtil.config.reveal("showPatronReward", "patronRewardType");
                        }
                    }
                }
            }
            catch(UnknownHostException e)
            {
                iChunUtil.LOGGER.warn("Error retrieving iChunUtil patron list: UnknownHostException. Is your internet connection working?");
            }
            catch(Exception e)
            {
                iChunUtil.LOGGER.warn("Error retrieving iChunUtil patron list.");
                e.printStackTrace();
            }
        }
        try
        {
            Gson gson = new Gson();
            Localizator.LOGGER.info("[Localizator->iChunUtil] Redirecting Version call to the valid URL: " + versionList);
            Reader fileIn = new InputStreamReader(new URL(versionList).openStream());
            UpdateChecker.processModsList(gson.fromJson(fileIn, Map.class));
        }
        catch(UnknownHostException e)
        {
            iChunUtil.LOGGER.warn("Error retrieving mods versions list: UnknownHostException. Is your internet connection working?");
        }
        catch(Exception e)
        {
            iChunUtil.LOGGER.warn("Error retrieving mods versions list.");
            e.printStackTrace();
        }
    }
    
    @Final @Mutable
    @Shadow(remap = false)
    private static String patronList = "https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/patrons.json";
    @Final @Mutable
    @Shadow(remap = false)
    private static String versionList = "https://raw.github.com/iChun/iChunUtil/1.7.10_legacy/src/main/resources/assets/ichunutil/mod/versions.json";
    @Final
    @Shadow(remap = false)
    private Side side;

}
