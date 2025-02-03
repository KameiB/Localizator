package kameib.localizator.mixin.mca;

import mca.core.Localizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(Localizer.class)
public abstract class LocalizerMixin {
    /**
     * @author KameiB
     * @reason Prevent translating on server side.
     * Return the selected lang key and update the arguments list if the original lang key contains %Supporter%
     */
    @Overwrite(remap = false)
    public String localize(String key, ArrayList<String> vars) {
        String resultText = localizerMap.getOrDefault(key, key);
        String resultLangKey = resultText;
        int randomIndex;
        List <String> possibleLangKeys; 
        
        if (resultText.equals(key)) {
            possibleLangKeys = localizerMap.keySet().stream().filter(s -> s.contains(key)).collect(Collectors.toList());
            List<String> responses = localizerMap.entrySet().stream().filter(entry -> entry.getKey().contains(key)).map(Map.Entry::getValue).collect(Collectors.toList());
            if (!responses.isEmpty()) {
                randomIndex = new Random().nextInt(responses.size());
                //resultText = responses.get(randomIndex);
                resultLangKey = possibleLangKeys.get(randomIndex);
            }
            
        }

        return resultLangKey;
        //return parseVars(resultText, vars).replaceAll("\\\\", "");
    }
    
    @Shadow(remap = false)
    private Map<String, String> localizerMap = new HashMap<>();
   
}
