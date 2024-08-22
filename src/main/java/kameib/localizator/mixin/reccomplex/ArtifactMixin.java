package kameib.localizator.mixin.reccomplex;

import ivorius.reccomplex.random.Artifact;
import ivorius.reccomplex.random.Person;
import kameib.localizator.wrapper.IArtifactMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Random;

@Mixin(Artifact.class)
public abstract class ArtifactMixin implements IArtifactMixin {
    // Additional fields for LocName support
    @Unique 
    private String localizator$preName;
    @Unique 
    private String localizator$postName;
    
    /**
     * @author KameiB
     * @reason Modify the logic to support localized name (LocName) and LocNameArgs
     */
    @Overwrite(remap = false)
    public static Artifact randomArtifact(Random random, String objectType_) {
        String trait_ = random.nextFloat() < 0.2f ? getRandomElementFrom(traits, random) : null;
        // if "power" is set as a chaoticName, save that into its corresponding string.
        String power_;
        String name2 = "";
        if (random.nextFloat() < 0.1f) {
            power_ = "chaotic";
            name2 = Person.chaoticName(random, random.nextBoolean());
        }
        else {
            power_ = (random.nextFloat() < 0.8F ? getRandomElementFrom(powers, random) : null);            
        }

        String uniqueName_;
        String name1 = "";
        // if "uniqueName" is set as a chaoticName, save that into its corresponding string.
        // String uniqueName = random.nextFloat() < 0.2F ? Person.chaoticName(random, random.nextBoolean()) : null;
        if (random.nextFloat() < 0.2f) {
            name1 = Person.chaoticName(random, random.nextBoolean());
            uniqueName_ = "chaotic";
        }
        else  {
            uniqueName_ = null;
        }
        
        Artifact artifact = new Artifact(objectType_, trait_, power_, uniqueName_);        
        ((IArtifactMixin) artifact).localizator$setPreName(name1);
        ((IArtifactMixin) artifact).localizator$setPostName(name2);
        
        return artifact;
    }
    
    /**
     * @author KameiB
     * @return Return full name in the form of a lang key
     */
    @Override
    public String localizator$getFullLocName() {
        StringBuilder builder = new StringBuilder();
        builder.append("item.reccomplex.");
        if (uniqueName != null) {
            builder.append(uniqueName).append(".");
        }

        if (trait != null) {
            builder.append(trait).append(".");
        }

        builder.append(objectType);
        if (power != null) {
            builder.append(".").append(power);
        }

        builder.append(".name");

        return builder.toString();
    }

    @Override
    public String localizator$getPreName() {
        return localizator$preName;
    }
    @Override
    public String localizator$getPostName() {
        return localizator$postName;
    }
    @Override
    public void localizator$setPreName(String preName) {
        this.localizator$preName = preName;
    }
    @Override
    public void localizator$setPostName(String postName) {
        this.localizator$postName = postName;
    }
    
    @Shadow(remap = false)
    private static <O> O getRandomElementFrom(List<O> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }

    @Shadow(remap = false) private static List<String> traits;
    @Shadow(remap = false) private static List<String> powers;
    @Shadow(remap = false) private String objectType;
    @Shadow(remap = false) private String trait;
    @Shadow(remap = false) private String power;
    @Shadow(remap = false) private String uniqueName;
}
