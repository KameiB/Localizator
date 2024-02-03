package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import kameib.localizator.data.Production;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TameableCreatureEntity.class)
public abstract class TameableCreatureEntityMixin extends AgeableCreatureEntity implements IEntityOwnable {
    public TameableCreatureEntityMixin(World world) {
        super(world);
    }

    @ModifyArg(
            method = "getInteractCommands(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;Lnet/minecraft/item/ItemStack;)Ljava/util/HashMap;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 318: player.sendMessage(new TextComponentString(LanguageManager.translate("item.lycanitesmobs.charge.creature.invalid")));
    private ITextComponent localizator_Lycanites_TameableCreatureEntity_getInteractCommands_creatureInvalid(ITextComponent message) {
        return new TextComponentTranslation("item.lycanitesmobs.charge.creature.invalid");
    }
    @ModifyArg(
            method = "tame(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate message on client side
    // Line 875: player.sendMessage(new TextComponentString(tameMessage));
    private ITextComponent localizator_Lycanites_TameableCreatureEntity_tame_tamed(ITextComponent message) {
        return new TextComponentTranslation("message.pet.tamed_", this.getSpeciesName());
    }
}
