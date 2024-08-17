package kameib.localizator.mixin.roguelike;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsCreeperDen;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
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

@Mixin(DungeonsCreeperDen.class)
public abstract class DungeonsCreeperDenMixin extends BaseRoom{
    @Unique
    private boolean localizator$addVechs = false;

    public DungeonsCreeperDenMixin(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
        super(roomSetting, levelSettings, worldEditor);
    }

    @Inject(
            method = "generate(Lgreymerk/roguelike/worldgen/Coord;Ljava/util/List;)Lgreymerk/roguelike/dungeon/base/BaseRoom;",
            at = @At("HEAD"),
            remap = false
    )
    // This room can generare up to 3 chests. This flag ensures the novelty will only be added in 1 of them.
    private void RLD_DungeonsCreeperDen_generate_initFlag(Coord at, List<Direction> entrances, CallbackInfoReturnable<BaseRoom> cir) {
        localizator$addVechs = true;
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
    // Add a sneaky stick
    private Optional<TreasureChest> RLD_DungeonsCreeperDen_generate_stroke_addNovelty(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);
        
        if (localizator$addVechs) {
            // If a dungeon can generate up to 3 creeper rooms, it becomes a 1/3 (dungeons) chance... Would it be rare enough?
            if (worldEditor.getRandom().nextInt(10) == 0) {
                myChest.ifPresent(mmillss -> {
                    // Empty the chest for additional Emotional Damage
                    mmillss.withChestType(getChestTypeOrUse(ChestType.EMPTY))
                    .setSlot(worldEditor.getCapacity(mmillss) / 2, ItemNovelty.vechsLegendaryStick());
                });
            }
            localizator$addVechs = false;
        }

        return myChest;
    }
}
