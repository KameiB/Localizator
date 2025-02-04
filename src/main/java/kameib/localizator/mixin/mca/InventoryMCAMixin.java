package kameib.localizator.mixin.mca;

import kameib.localizator.data.Production;
import mca.entity.EntityVillagerMCA;
import mca.entity.inventory.InventoryMCA;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static mca.entity.EntityVillagerMCA.VILLAGER_NAME;

@Mixin(InventoryMCA.class)
public abstract class InventoryMCAMixin extends InventoryBasic {
    @ModifyArgs(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/inventory/InventoryBasic;<init>(Ljava/lang/String;ZI)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Modify args to make the Inventory title translatable
    private static void MCA_InventoryMCA_init_super(Args args) {
        args.set(0, "gui.mca.villager.inventory");
        args.set(1, false);
    }
    
    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentTranslation(this.getName(), villager.get(VILLAGER_NAME));
    }
    
    @Shadow(remap = false)
    private EntityVillagerMCA villager;

    public InventoryMCAMixin(String title, boolean customName, int slotCount) {
        super(title, customName, slotCount);
    }
}
