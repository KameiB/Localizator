package kameib.localizator.mixin.mca;
import mca.client.gui.component.GuiButtonEx;
import mca.core.Localizer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SideOnly(Side.CLIENT)
@Mixin(GuiButtonEx.class)
public abstract class GuiButtonExMixin {
    @Redirect(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/core/Localizer;localize(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Localize using I18n instead of custom localizer
    // Line 17: super(apiButton.getId(), gui.width / 2 + apiButton.getX(), gui.height / 2 + apiButton.getY(), apiButton.getWidth(), apiButton.getHeight(), MCA.getLocalizer().localize(apiButton.getIdentifier(), new String[0]));
    private static String MCA_GuiButtonEx_init_buttonIdentifier_localize(Localizer instance, String key, String[] vars) {
        return I18n.format(key);
    }
}
