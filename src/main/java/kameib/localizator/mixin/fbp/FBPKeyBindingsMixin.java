package kameib.localizator.mixin.fbp;

import com.TominoCZ.FBP.keys.FBPKeyBindings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@SideOnly(Side.CLIENT)
@Mixin(FBPKeyBindings.class)
public abstract class FBPKeyBindingsMixin {
    @ModifyArg(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/registry/ClientRegistry;registerKeyBinding(Lnet/minecraft/client/settings/KeyBinding;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Localize "Open Menu"
    // Line 27: ClientRegistry.registerKeyBinding(FBPMenu);
    private static KeyBinding FBP_FBPKeyBindings_init_locFBPMenu(KeyBinding key) {
        FBPMenu = new KeyBinding("key.fbp.open_menu", Keyboard.KEY_P, "Fancy Block Particles");
        return FBPMenu;
    }
    @ModifyArg(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/registry/ClientRegistry;registerKeyBinding(Lnet/minecraft/client/settings/KeyBinding;)V",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Localize "Toggle Freeze Effect"
    // Line 28: ClientRegistry.registerKeyBinding(FBPFreeze);
    private static KeyBinding FBP_FBPKeyBindings_init_locFBPFreeze(KeyBinding key) {
        FBPFreeze = new KeyBinding("key.fbp.toggle_freeze", Keyboard.KEY_R, "Fancy Block Particles");
        return FBPFreeze;
    }
    @ModifyArg(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/registry/ClientRegistry;registerKeyBinding(Lnet/minecraft/client/settings/KeyBinding;)V",
                    ordinal = 2,
                    remap = false
            ),
            remap = false
    )
    // Localize "Enable/Disable"
    // Line 29: ClientRegistry.registerKeyBinding(FBPToggle);
    private static KeyBinding FBP_FBPKeyBindings_init_locFBPToggle(KeyBinding key) {
        FBPToggle = new KeyBinding("key.fbp.enable_disable", Keyboard.KEY_NONE, "Fancy Block Particles");
        return FBPToggle;
    }
    @ModifyArg(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/registry/ClientRegistry;registerKeyBinding(Lnet/minecraft/client/settings/KeyBinding;)V",
                    ordinal = 3,
                    remap = false
            ),
            remap = false
    )
    // Localize "Kill Particles"
    // Line 30: ClientRegistry.registerKeyBinding(FBPSweep);
    private static KeyBinding FBP_FBPKeyBindings_init_locFBPSweep(KeyBinding key) {
        FBPSweep = new KeyBinding("key.fbp.kill_particles", Keyboard.KEY_NONE, "Fancy Block Particles");
        return FBPSweep;
    }
    @ModifyArg(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/registry/ClientRegistry;registerKeyBinding(Lnet/minecraft/client/settings/KeyBinding;)V",
                    ordinal = 4,
                    remap = false
            ),
            remap = false
    )
    // Localize "Blacklist Block"
    // Line 31: ClientRegistry.registerKeyBinding(FBPFastAdd);
    private static KeyBinding FBP_FBPKeyBindings_init_locFBPFastAdd(KeyBinding key) {
        FBPFastAdd = new KeyBinding("key.fbp.blacklist_block", Keyboard.KEY_X, "Fancy Block Particles");
        return FBPFastAdd;
    }
    
    @Shadow(remap = false)
    public static KeyBinding FBPMenu;
    @Shadow(remap = false)
    public static KeyBinding FBPFreeze;
    @Shadow(remap = false)
    public static KeyBinding FBPToggle;
    @Shadow(remap = false)
    public static KeyBinding FBPSweep;
    @Shadow(remap = false)
    public static KeyBinding FBPFastAdd;
}
