package kameib.localizator.mixin.bhmenu;

import com.bisecthosting.mods.bhmenu.modules.servercreatorbanner.screens.steps.RecommendJarStep;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RecommendJarStep.class)
public abstract class RecommendJarStepMixin {
    // Replace the hardcoded "Select" text with its intended lang key
    // Line 31: new PressableButton(1, screen.width / 2 - 104, screen.height / 2 + 20, 100, 20, "Select", (b)
    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "init(Lcom/bisecthosting/mods/bhmenu/modules/servercreatorbanner/screens/BHOrderScreen;Ljava/util/function/Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "com/bisecthosting/mods/bhmenu/client/screen/PressableButton.<init> (IIIIILjava/lang/String;Ljava/util/function/Consumer;)V",
                    ordinal = 0,
                    remap = false
            ),
            index = 5,
            remap = false
    )
    private String BHMenu_RecommendJarStep_init_newPressableButton_Select(String buttonText) {
        if (buttonText.equals("Select")) {
            return I18n.format("step.recommended_jar.select");
        }
        return buttonText;
    }

    // Replace the hardcoded "Change" text with its intended lang key
    // Line 35: new PressableButton(2, screen.width / 2 + 4, screen.height / 2 + 20, 100, 20, "Change", (b) -> screen.setStep(new SelectJarStep(this.orderData))));
    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "init(Lcom/bisecthosting/mods/bhmenu/modules/servercreatorbanner/screens/BHOrderScreen;Ljava/util/function/Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "com/bisecthosting/mods/bhmenu/client/screen/PressableButton.<init> (IIIIILjava/lang/String;Ljava/util/function/Consumer;)V",
                    ordinal = 1,
                    remap = false
            ),
            index = 5,
            remap = false
    )
    private String BHMenu_RecommendJarStep_init_newPressableButton_Change(String buttonText) {
        if (buttonText.equals("Change")) {
            return I18n.format("step.recommended_jar.change");
        }
        return buttonText;
    }
}
