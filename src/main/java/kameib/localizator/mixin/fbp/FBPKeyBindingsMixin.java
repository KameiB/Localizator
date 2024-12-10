package kameib.localizator.mixin.fbp;

import com.TominoCZ.FBP.keys.FBPKeyBindings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(FBPKeyBindings.class)
public abstract class FBPKeyBindingsMixin {
    @Inject(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/registry/ClientRegistry;registerKeyBinding(Lnet/minecraft/client/settings/KeyBinding;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // IDK why FBPMenu is still hardcoded...
    private static void Localizator_FBP_FBPKeyBindings_init_beforeRegisterKeyBindings(CallbackInfo ci) {
        
        FBPFreeze = new KeyBinding("key.fbp.toggle_freeze", Keyboard.KEY_R, "Fancy Block Particles");
        FBPToggle = new KeyBinding("key.fbp.enable_disable", Keyboard.KEY_NONE, "Fancy Block Particles");
        FBPSweep = new KeyBinding("key.fbp.kill_particles", Keyboard.KEY_NONE, "Fancy Block Particles");
        FBPFastAdd = new KeyBinding("key.fbp.blacklist_block", Keyboard.KEY_X, "Fancy Block Particles");
        FBPMenu = new KeyBinding("key.fbp.open_menu", Keyboard.KEY_P, "Fancy Block Particles");
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
