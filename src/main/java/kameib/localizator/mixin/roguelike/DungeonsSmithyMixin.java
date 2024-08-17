package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.Tool;
import com.github.fnar.minecraft.item.ToolType;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSmithy;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.Random;

@Mixin(DungeonsSmithy.class)
public abstract class DungeonsSmithyMixin {
    @Inject(
            method = "smelterSide(Lgreymerk/roguelike/worldgen/Direction;Lgreymerk/roguelike/worldgen/Coord;)V",
            at = @At("HEAD"),
            remap = false
    )
    // Flag to act only on the desired furnace
    private void RLD_DungeonsSmithy_smelterSide_initFlag(Direction entranceDirection, Coord origin, CallbackInfo ci) {
        localizator$addEtho = false;
    }
    
    @Redirect(
            method = "smelter(Lgreymerk/roguelike/worldgen/Direction;Lgreymerk/roguelike/worldgen/Coord;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/treasure/TreasureChest;stroke(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Coord;)Ljava/util/Optional;",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Put in a countdown and a sneaky pick
    private Optional<TreasureChest> RLD_DungeonsSmithy_smelter_stroke_fuelChest(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);
        if (localizator$addEtho){
            myChest.ifPresent(etho -> {
                Random random = worldEditor.getRandom();
                short slot = 0;
                etho.setSlot(slot, BlockType.COAL_BLOCK.asItem().asStack().withCount(1)); // 800 seconds (13.33 minutes)
                slot++;
                short dummies = (short)(random.nextInt(10)); // 0 - 9 coals
                if (dummies > 0) {
                    etho.setSlot(slot, Material.Type.COAL.asItem().asStack().withCount(dummies)); // 80 - 720 seconds (12 minutes)
                    slot++;
                }
                
                dummies = (short)(1 + random.nextInt(7));
                for (; slot < dummies; slot++) { // Up to 6
                    etho.setSlot(slot, new Tool(ToolType.PICKAXE).wooden().asStack()); // 10 - 60 seconds
                }
                // Time range: 13.33 min - 26.16 min
                // Keep in mind furnaces will start smelting when the chunk is loaded.
                // Player loads the chunk + finds the dungeon entrance + finds the room
                etho.setSlot(slot, ItemNovelty.ethosYourMomJoke());
            });
        }
        
        return myChest;
    }

    @Redirect(
            method = "smelter(Lgreymerk/roguelike/worldgen/Direction;Lgreymerk/roguelike/worldgen/Coord;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/treasure/TreasureChest;stroke(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Coord;)Ljava/util/Optional;",
                    ordinal = 2,
                    remap = false
            ),
            remap = false
    )
    // Put in enough logs
    private Optional<TreasureChest> RLD_DungeonsSmithy_smelter_stroke_smeltChest(TreasureChest instance, WorldEditor worldEditor, Coord coord) {
        Optional<TreasureChest> myChest = instance.stroke(worldEditor, coord);
        if (!localizator$addEtho) {
            localizator$addEtho = true;
        }
        else {
            myChest.ifPresent(logs -> {
                logs.setSlot(0, BlockType.OAK_LOG.asItem().asStack().withCount(64));
                logs.setSlot(1, BlockType.OAK_LOG.asItem().asStack().withCount(64));
                logs.setSlot(2, BlockType.OAK_LOG.asItem().asStack().withCount(32)); // 1,600 seconds
            });
            localizator$addEtho = false;
        }

        return myChest;
    }
    
    @Unique
    private boolean localizator$addEtho = false;
}
