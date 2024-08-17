package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.dungeon.rooms.prototype.DungeonsEnchant;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(DungeonsEnchant.class)
public abstract class DungeonsEnchantMixin {
    @Redirect(
            method = "generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(Lgreymerk/roguelike/worldgen/Coord;Ljava/util/List;Lgreymerk/roguelike/worldgen/Direction;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/treasure/TreasureChest;stroke(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Coord;)Ljava/util/Optional;",
                    remap = false
            ),
            remap = false
    )
    // Place sneaky cheese
    private Optional<TreasureChest> RLD_DungeonsEnchant_generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter_stroke_addNovelty(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);

        // This room is already secret and rare enough. But extra 1/2 chance for the funsies.
        if (worldEditor.getRandom().nextBoolean()) {
            myChest.ifPresent(cheese -> {
                cheese.setRandomEmptySlot(ItemNovelty.quantumleapsSwissCheese());
            });
        }
        return myChest;
    }
}
