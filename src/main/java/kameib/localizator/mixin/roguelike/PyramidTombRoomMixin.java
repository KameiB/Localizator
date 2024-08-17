package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.dungeon.rooms.prototype.PyramidTombRoom;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(PyramidTombRoom.class)
public abstract class PyramidTombRoomMixin {
    @Redirect(
            method = "sarcophagus(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Direction;Lgreymerk/roguelike/worldgen/Coord;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/treasure/TreasureChest;stroke(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Coord;)Ljava/util/Optional;",
                    remap = false
            ),
            remap = false
    )
    // Place a sneaky rotten flesh
    private Optional<TreasureChest> RLD_PyramidTombRoom_sarcophagus_stroke_addNovelty(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);

        // Turns out this room is not that rare or even secret. I've found it in Floor 0, right in front the stairs.
        if (worldEditor.getRandom().nextBoolean()) {
            myChest.ifPresent(grim -> {
                grim.setRandomEmptySlot(ItemNovelty.grimChewToy());
            });
        }
        return myChest;
    }
}
