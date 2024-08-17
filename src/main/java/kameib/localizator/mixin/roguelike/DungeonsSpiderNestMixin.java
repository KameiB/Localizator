package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSpiderNest;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(DungeonsSpiderNest.class)
public abstract class DungeonsSpiderNestMixin {
    @Unique
    private boolean localizator$addMmillss = false;
    
    @Inject(
            method = "generate(Lgreymerk/roguelike/worldgen/Coord;Ljava/util/List;)Lgreymerk/roguelike/dungeon/base/BaseRoom;",
            at = @At("HEAD"),
            remap = false
    )
    // This room can generare 1-3 chests. This flag ensures the novelty will only be added in 1 of them.
    private void RLD_DungeonSpiderNest_generate_initFlag(Coord at, List<Direction> entrances, CallbackInfoReturnable<BaseRoom> cir) {
        localizator$addMmillss = true;
    }
    
    @Redirect(
            method = "lambda$generate$0(Ljava/util/List;Lgreymerk/roguelike/worldgen/Coord;)V",            
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/treasure/TreasureChest;stroke(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Coord;)Ljava/util/Optional;",
                    remap = false
            ),
            remap = false
    )
    // Add a sneaky cactus
    private Optional<TreasureChest> RLD_DungeonSpiderNest_generate_stroke_addNovelty(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);
        
        if (localizator$addMmillss) {
            // If a dungeon can generate up to 3 spider rooms, it becomes a 1/7 (dungeons) chance... Would it be rare enough?
            if (worldEditor.getRandom().nextInt(20) == 0) {
                myChest.ifPresent(mmillss -> {
                    mmillss.setSlot(worldEditor.getCapacity(mmillss) / 2, ItemNovelty.mmillssSpiderBane());
                });
            }
            localizator$addMmillss = false;
        }
        
        return myChest;
    }
}
