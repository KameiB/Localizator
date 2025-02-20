package kameib.localizator.mixin.fbp;

import com.TominoCZ.FBP.keys.FBPKeyBindings;
import kameib.localizator.data.Production;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin(FBPKeyBindings.class)
public abstract class FBPKeyBindingsMixin {
    @ModifyArg(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/settings/KeyBinding;<init>(Ljava/lang/String;ILjava/lang/String;)V",
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // This should correctly localize the descriptions and keep the R keybind working
    private static String Localizator_FBP_FBPKeyBindings_init_mapKeyDescriptions(String description) {
        switch (description) {
            case "Open Menu":
                return "key.fbp.open_menu";
            case "Toggle Freeze Effect":
                return "key.fbp.toggle_freeze";
            case "Enable/Disable":
                return "key.fbp.enable_disable";
            case "Kill Particles":
                return "key.fbp.kill_particles";
            case "Blacklist Block":
                return "key.fbp.blacklist_block";
        }
        return description;
    }
}
