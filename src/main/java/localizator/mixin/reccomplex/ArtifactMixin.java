package localizator.mixin.reccomplex;

import ivorius.reccomplex.random.Artifact;
import ivorius.reccomplex.random.Person;
import localizator.wrapper.IChaoticName;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(Artifact.class)
public abstract class ArtifactMixin implements IChaoticName {
    @Shadow(remap = false)
    private static List<String> objectTypes = Arrays.asList("tool", "eye", "board", "weapon", "toy", "memory", "relic", "orb", "block", "killer", "diminisher", "eater", "devourer", "helper", "teacher", "remedy", "prophet", "stone", "artifact", "scroll", "amulet", "ring", "tablet");
    @Shadow(remap = false)
    private static List<String> traits = Arrays.asList("ominous", "odd", "uncontrollable", "catastrophical", "silent", "furious", "banned", "secret", "unknown", "popular", "forgotten", "lost", "heroic", "famous", "colossal", "mad", "wise", "uncontrollable", "glorious", "unprecedented", "unbelievable", "incredible", "lesser", "greater", "striking", "red", "gold", "silver", "blue", "marine", "glowing", "crimson", "violet", "white", "black", "sinister");
    @Shadow(remap = false)
    private static List<String> powers = Arrays.asList("tears", "darkness", "gloom", "twilight", "light", "fire", "gold", "luck", "cold", "wealth", "fury", "magic", "nature", "beasts", "rapture", "salvation", "destruction", "perdition");
    @Shadow(remap = false)
    private String objectType;
    @Shadow(remap = false)
    private String trait;
    @Shadow(remap = false)
    private String power;
    @Shadow(remap = false)
    private String uniqueName;
    // Additional members for LocName support
    @Unique
    private String localizator$preName;
    @Unique
    private String localizator$postName;
    
    /**
     * @author KameiB
     * @reason Modify the logic to support localized name (LocName) and LocNameArgs
     */
    @Overwrite(remap = false)
    public static Artifact randomArtifact(Random random, String objectType) {
        String trait = random.nextFloat() < 0.2f ? getRandomElementFrom(traits, random) : null;
        // if "power" is set as a chaoticName, save that into its corresponding string.
        // String power = random.nextFloat() < 0.1F ? Person.chaoticName(random, random.nextBoolean()) : 
        //      (random.nextFloat() < 0.8F ? (String)getRandomElementFrom(powers, random) : null);
        String power;
        String name2 = "";
        if (random.nextFloat() < 0.1f) {
            power = "chaotic";
            name2 = Person.chaoticName(random, random.nextBoolean());
        }
        else {
            power = (random.nextFloat() < 0.8F ? (String)getRandomElementFrom(powers, random) : null);            
        }

        String uniqueName;
        String name1 = "";
        // if "uniqueName" is set as a chaoticName, save that into its corresponding string.
        // String uniqueName = random.nextFloat() < 0.2F ? Person.chaoticName(random, random.nextBoolean()) : null;
        if (random.nextFloat() < 0.2f) {
            name1 = Person.chaoticName(random, random.nextBoolean());
            uniqueName = "chaotic";
        }
        else  {
            uniqueName = null;
        }
        
        Artifact artifact = new Artifact(objectType, trait, power, uniqueName);
        IChaoticName chaoticArtifact = (IChaoticName) artifact;
        chaoticArtifact.setPreName(name1);
        chaoticArtifact.setPostName(name2);
        
        return (Artifact)chaoticArtifact;
    }
    
    /**
     * @author KameiB
     * @return Return full name in the form of a lang key
     */
    @Override
    public String getFullLocName() {
        StringBuilder builder = new StringBuilder();
        builder.append("item.reccomplex.");
        if (this.uniqueName != null) {
            builder.append(this.uniqueName).append(".");
        }

        if (this.trait != null) {
            builder.append(this.trait).append(".");
        }

        builder.append(this.objectType);
        if (this.power != null) {
            builder.append(".").append(this.power);
        }

        builder.append(".name");

        return builder.toString();
    }
    
    @Shadow(remap = false)
    private static <O> O getRandomElementFrom(List<O> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }

    @Override
    public String getPreName() {
        return localizator$preName;
    }
    @Override
    public String getPostName() {
        return localizator$postName;
    }
    @Override
    public void setPreName(String preName) {
        this.localizator$preName = preName;
    }
    @Override
    public void setPostName(String postName) {
        this.localizator$postName = postName;
    }
}
