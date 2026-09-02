package kameib.localizator.mixin.fbp;

import com.TominoCZ.FBP.keys.FBPKeyBindings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin(FBPKeyBindings.class)
public abstract class FBPKeyBindingsMixin {
    @ModifyConstant(
            method = "init()V",
            constant = @Constant(stringValue = "Open Menu"),
            remap = false
    )
    // Replace hardcoded "Open Menu" with a lang key
    // Line 19: FBPMenu = new KeyBinding("Open Menu", 25, "Fancy Block Particles");
    private static String localizator_FBP_FBPKeyBindings_init_openMenu(String original) {
        return "key.fbp.open_menu";
    }

    @ModifyConstant(
            method = "init()V",
            constant = @Constant(stringValue = "Toggle Freeze Effect"),
            remap = false
    )
    // Replace hardcoded "Toggle Freeze Effect" with a lang key
    // Line 20: FBPFreeze = new KeyBinding("Toggle Freeze Effect", 19, "Fancy Block Particles");
    private static String localizator_FBP_FBPKeyBindings_init_toggleFreeze(String original) {
        return "key.fbp.toggle_freeze";
    }

    @ModifyConstant(
            method = "init()V",
            constant = @Constant(stringValue = "Enable/Disable"),
            remap = false
    )
    // Replace hardcoded "Enable/Disable" with a lang key
    // Line 21: FBPToggle = new KeyBinding("Enable/Disable", 0, "Fancy Block Particles");
    private static String localizator_FBP_FBPKeyBindings_init_enableDisable(String original) {
        return "key.fbp.enable_disable";
    }

    @ModifyConstant(
            method = "init()V",
            constant = @Constant(stringValue = "Kill Particles"),
            remap = false
    )
    // Replace hardcoded "Kill Particles" with a lang key
    // Line 22: FBPSweep = new KeyBinding("Kill Particles", 0, "Fancy Block Particles");
    private static String localizator_FBP_FBPKeyBindings_init_killParticles(String original) {
        return "key.fbp.kill_particles";
    }

    @ModifyConstant(
            method = "init()V",
            constant = @Constant(stringValue = "Blacklist Block"),
            remap = false
    )
    // Replace hardcoded "Blacklist Block" with a lang key
    // Line 23: FBPFastAdd = new KeyBinding("Blacklist Block", 45, "Fancy Block Particles");
    private static String localizator_FBP_FBPKeyBindings_init_blacklistBlock(String original) {
        return "key.fbp.blacklist_block";
    }
}
