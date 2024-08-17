package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.dungeon.rooms.prototype.DungeonsMusic;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(DungeonsMusic.class)
public abstract class DungeonsMusicMixin {
    @Redirect(
            method = "generate(Lgreymerk/roguelike/worldgen/Coord;Ljava/util/List;)Lgreymerk/roguelike/dungeon/base/BaseRoom;",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/treasure/TreasureChest;stroke(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Coord;)Ljava/util/Optional;",
                    remap = false
            ),
            remap = false
    )
    // Place a sneaky disc
    private Optional<TreasureChest> RLD_DungeonsMusic_generate_stroke_addNovelty(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);
        
        // To honour his 290 episodes :)
        if (worldEditor.getRandom().nextInt(29) == 0) { // 3.4% chance D:
            myChest.ifPresent(guude -> {
                guude.setSlot(worldEditor.getCapacity(guude) / 2, ItemNovelty.boulderfistianGoldenRecord());
            });
        }
        return myChest;
    }
}
