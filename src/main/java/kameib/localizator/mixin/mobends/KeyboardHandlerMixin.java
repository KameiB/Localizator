package kameib.localizator.mixin.mobends;

import goblinbob.mobends.core.client.event.KeyboardHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Inject(
            method = "initKeyBindings()V",
            at = @At("HEAD"),
            remap = false
    )
    // Modify KEY_MENU & KEY_REFRESH members before getting registered
    // Line 24: public static void initKeyBindings()
    private static void LevelUp2_KeyboardHandler_initKeyBindings_editMembers(CallbackInfo ci) {
        KEY_MENU = new KeyBinding("key.mobends.menu", Keyboard.KEY_G, MAIN_CATEGORY);
        KEY_REFRESH = new KeyBinding("key.mobends.refresh_animations", Keyboard.KEY_F10, MAIN_CATEGORY);
    }
    
    @Final @Mutable
    @Shadow(remap = false)
    private static KeyBinding KEY_MENU;
    @Final @Mutable
    @Shadow(remap = false)
    private static KeyBinding KEY_REFRESH;
    @Final @Mutable
    @Shadow(remap = false)
    private static String MAIN_CATEGORY;
}
