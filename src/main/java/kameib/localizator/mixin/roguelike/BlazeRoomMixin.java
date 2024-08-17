package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.dungeon.rooms.prototype.BlazeRoom;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(BlazeRoom.class)
public abstract class BlazeRoomMixin {
    @Redirect(
            method = "generateLiquidPitChest(Lgreymerk/roguelike/worldgen/Coord;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/treasure/TreasureChest;stroke(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Coord;)Ljava/util/Optional;",
                    remap = false
            ),
            remap = false
    )
    // Place sneaky shears
    private Optional<TreasureChest> RLD_DungeonsMusic_generate_stroke_addNovelty(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);

        // This chest has a 1/10 chance of being generated. Then 1/2 chance to add this item.
        if (worldEditor.getRandom().nextBoolean()) {
            myChest.ifPresent(amlp -> {
                amlp.setRandomEmptySlot(ItemNovelty.lascerator());
            });
        }
        
        return myChest;
    }
}
