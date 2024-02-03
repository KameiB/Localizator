package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityLockable.class)
public abstract class TileEntityLockableMixin implements ILockableContainer {
    @Inject(
            method = "getDisplayName()Lnet/minecraft/util/text/ITextComponent;",
            at = @At("HEAD"),
            remap = Production.inProduction,
            cancellable = true
    )
    // Localize command-generated container name
    // Return its name as a TextComponentTranslation, regarding if it is a CustomName or the default name
    // Line 47: public ITextComponent getDisplayName()
    private void Minecraft_TileEntityLockable_getLocalizedDisplayName(CallbackInfoReturnable<ITextComponent> cir) {
        cir.setReturnValue(new TextComponentTranslation(getName()));
    }

}
