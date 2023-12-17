package localizator.mixin.itemphysic;

import com.creativemd.itemphysic.EventHandler;
import localizator.data.Production;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EventHandler.class)
public abstract class ErroredPatch {
    @Unique
    private String localizator$itemName = "ERRORED";
    @Redirect(
            method = "renderTickFull()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/item/EntityItem;getItem()Lnet/minecraft/item/ItemStack;",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Attempt to work around the Ozzy Liner's "ERRORED". This also happens with all ArmorUnder items that show a text in their description.
    // Step 1: Try to cheese the item's name 
    // Line 266: entity.getItem().getItem().addInformation(entity.getItem(), mc.player.world, list, TooltipFlags.NORMAL);
    private ItemStack ItemPhysic_EventHandler_renderTickFull_getItemName(EntityItem entity) {
        localizator$itemName = "ERRORED";
        if (entity.getItem() != null) { // Just in case. I'm not THAT reckless
            //if (!(entity.getItem().isEmpty())) { // Code doesn't enter here
            //if (entity.getItem().hasDisplayName()) { // Neither here
            localizator$itemName = entity.getItem().getDisplayName(); // Let's just take it by force. The exception will be caught anyway.
            //}
            //}
        }
        //Localizator.LOGGER.info("Item: " + entity.getItem().getDisplayName()); // Used for testing how viable this approach was.

        // Continue with normal behaviour
        return entity.getItem();
    }

    @ModifyArg(
            method = "renderTickFull()V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Attempt to work around the Ozzy Liner's "ERRORED".
    // Step 2: At least show the item name
    // Line 270: list.add("ERRORED");
    private Object ArmorUnder_EventHandler_renderTickFull_showItemNameIfPossible(Object text) {
        if (localizator$itemName != null && !localizator$itemName.isEmpty()) { // Extra precautions just in case...
            return localizator$itemName;
        }
        return "ERRORED";
    }
}
