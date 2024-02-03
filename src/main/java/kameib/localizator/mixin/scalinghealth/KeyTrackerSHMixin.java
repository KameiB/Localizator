package kameib.localizator.mixin.scalinghealth;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.lib.client.key.KeyTrackerSL;
import net.silentchaos512.scalinghealth.client.key.KeyTrackerSH;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@SideOnly(Side.CLIENT)
@Mixin(KeyTrackerSH.class)
public abstract class KeyTrackerSHMixin extends KeyTrackerSL {
    @ModifyArg(
            method = "<init>()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/silentchaos512/scalinghealth/client/key/KeyTrackerSH;createBinding(Ljava/lang/String;Lnet/minecraftforge/client/settings/IKeyConflictContext;Lnet/minecraftforge/client/settings/KeyModifier;I)Lnet/minecraft/client/settings/KeyBinding;",
                    ordinal = 0,
                    remap = false
            ),
            index = 0,
            remap = false
    )
    // Replace the hardcoded "Difficulty Meter - Show" with a lang key
    // Line 24: this.keyShowDifficultyBar = this.createBinding("Difficulty Meter - Show", KeyConflictContext.IN_GAME, KeyModifier.NONE, 49);
    private String ScalingHealth_KeyTrackerSH_init_keyShowDifficultBar(String name) {
        return "key.scalinghealth.difficulty_meter";
    }

    @ModifyArg(
            method = "<init>()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/silentchaos512/scalinghealth/client/key/KeyTrackerSH;createBinding(Ljava/lang/String;Lnet/minecraftforge/client/settings/IKeyConflictContext;Lnet/minecraftforge/client/settings/KeyModifier;I)Lnet/minecraft/client/settings/KeyBinding;",
                    ordinal = 1,
                    remap = false
            ),
            index = 0,
            remap = false
    )
    // Replace the hardcoded "Difficulty Meter - Show" with a lang key
    // Line 24: this.keyShowDifficultyBar = this.createBinding("Difficulty Meter - Show", KeyConflictContext.IN_GAME, KeyModifier.NONE, 49);
    private String ScalingHealth_KeyTrackerSH_init_keyShowDifficultyBarAlways(String name) {
        return "key.scalinghealth.show_always";
    }
    
    public KeyTrackerSHMixin(String modName) {
        super(modName);
    }
}
