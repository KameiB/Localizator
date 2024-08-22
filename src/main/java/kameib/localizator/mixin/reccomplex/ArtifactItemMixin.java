package kameib.localizator.mixin.reccomplex;

import ivorius.reccomplex.random.Artifact;
import ivorius.reccomplex.random.item.ArtifactItem;
import kameib.localizator.data.Production;
import kameib.localizator.wrapper.IArtifactMixin;
import kameib.localizator.util.LocNameArguments;
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
    // Snitch a copy of the Artifact
    // Line 85: artifactStack.setStackDisplayName(artifact.getFullName());
    private static String RecComplex_ArtifactItem_any_getFullLocName(Artifact artifact) {
        localizator$tempArtifact = artifact;
        return artifact.getFullName();
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
    // Add LocName and LocNameArgs
    // Line 85: artifactStack.setStackDisplayName(artifact.getFullName());
    private static ItemStack RecComplex_ArtifactItem_any_setLocNameAndLocNameArgs(ItemStack artifactStack, String name) {        
        // First, set the DisplayName (because this method will clear the LocName and LocNameArgs NBT tags)
        artifactStack.setStackDisplayName(name);
        // Then, set the LocName. Localizator shows LocName (if exists) instead of DisplayName.
        // I use both DisplayName and LocName, so if the player removes Localizator, Minecraft shows DisplayName instead of LocName.
        artifactStack.setTranslatableName(((IArtifactMixin)localizator$tempArtifact).localizator$getFullLocName());
        
        // Finally, append LocNameArgs.
        // This method creates a copy of the ItemStack it receives and returns the copy, instead of modifying the original ItemStack.
        return LocNameArguments.appendLocNameArgs(artifactStack,
                Arrays.asList(((IArtifactMixin) localizator$tempArtifact).localizator$getPreName(), 
                                     ((IArtifactMixin) localizator$tempArtifact).localizator$getPostName()));
    }
}
