package localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.Subspecies;
import com.lycanitesmobs.core.info.Variant;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfoServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMixin {
    @ModifyArg(
            method = "isDamageEntityApplicable(Lnet/minecraft/entity/Entity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    remap = true
            ),
            index = 0,
            remap = false
    )
    // Translate Status Message on client side
    // Line 4772: ((EntityPlayer)entity).sendStatusMessage(new TextComponentString(LanguageManager.translate("boss.damage.protection.range")), true);
    private ITextComponent lycanites_BaseCreatureEntity_isDamageEntityApplicable_protection(ITextComponent message) {
        return new TextComponentTranslation("boss.damage.protection.range");
    }
}
