package kameib.localizator.mixin.levelup2;

import levelup2.event.KeybindEventHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@SideOnly(Side.CLIENT)
@Mixin(KeybindEventHandler.class)
public abstract class KeyBindEventHandler {
    @ModifyArg(
            method = "<init>()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/registry/ClientRegistry;registerKeyBinding(Lnet/minecraft/client/settings/KeyBinding;)V"
            ),
            remap = false
    )
    // Reinitialize keyBind so it has a lang key instead of a hardcoded text
    // Line 23: private KeybindEventHandler()
    private net.minecraft.client.settings.KeyBinding LevelUp2_KeybindEventHandler_init_keyBind(KeyBinding key) {
        keybind = new KeyBinding("key.levelup2.gui", Keyboard.KEY_L, "key.categories.gui");
        return keybind;
    }
    
    @Final @Mutable
    @Shadow(remap = false)
    private KeyBinding keybind;
}
