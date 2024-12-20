package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.block.decorative.BrewingStand;
import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.dungeon.rooms.prototype.LabRoom;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LabRoom.class)
public abstract class LabRoomMixin {
    @Redirect(
            method = "southWest(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/theme/Theme;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/worldgen/WorldEditor;setItem(Lgreymerk/roguelike/worldgen/Coord;ILcom/github/fnar/minecraft/item/RldItemStack;)V",
                    remap = false
            ),
            remap = false
    )
    // Place the default Blaze Powder and a some sneaky beans
    // The reason is, maybe getting the beans from level 0 might be a bit difficult for the lack of spawners there
    // For this to work, will need to update RLD where setItem works on BrewingStands and Furnaces
    // Line 267: editor.setItem(bs, BrewingStand.Slot.FUEL, Ingredient.Type.BLAZE_POWDER.asItemStack());
    private void RLD_LabRoom_southWest_setItem_placeNovelty(WorldEditor instance, Coord bs, int slot, RldItemStack blazePowder) {
        instance.setItem(bs, slot, blazePowder);
        if (instance.getRandom().nextInt(7) == 0) {
            instance.setItem(bs, BrewingStand.Slot.INGREDIENT, ItemNovelty.fourlesDarkroastBeans());
        }
    }
}
