package localizator.mixin.reccomplex;

import ivorius.reccomplex.random.Artifact;
import ivorius.reccomplex.random.item.ArtifactItem;
import localizator.data.Production;
import localizator.wrapper.IArtifactMixin;
import localizator.util.LocNameArguments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.*;

@Mixin(ArtifactItem.class)
public abstract class ArtifactItemMixin {
    @Unique
    private static Artifact localizator$tempArtifact;
    
    @Redirect(
            method = "any(Ljava/util/Random;)Lnet/minecraft/item/ItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Livorius/reccomplex/random/Artifact;getFullName()Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Try to send a lang key...
    // Line 85: artifactStack.setStackDisplayName(artifact.getFullName());
    private static String RecComplex_ArtifactItem_any_getFullLocName(Artifact artifact) {
        localizator$tempArtifact = artifact;
        return ((IArtifactMixin)artifact).getFullLocName();
    }
    
    @Redirect(
            method = "any(Ljava/util/Random;)Lnet/minecraft/item/ItemStack;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;setStackDisplayName(Ljava/lang/String;)Lnet/minecraft/item/ItemStack;",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // ... to setTranslatableName
    // Line 85: artifactStack.setStackDisplayName(artifact.getFullName());
    private static ItemStack RecComplex_ArtifactItem_any_setLocNameAndLocNameArgs(ItemStack artifactStack, String locName) {        
        LocNameArguments.appendLocNameArgs(artifactStack, Arrays.asList(((IArtifactMixin) localizator$tempArtifact).getPreName(), ((IArtifactMixin) localizator$tempArtifact).getPostName()));
        
        return artifactStack.setTranslatableName(locName);
    }
}
