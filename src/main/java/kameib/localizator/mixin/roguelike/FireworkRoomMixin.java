package kameib.localizator.mixin.roguelike;

import com.github.fnar.minecraft.item.RldItemStack;
import greymerk.roguelike.dungeon.rooms.prototype.FireworkRoom;
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

@Mixin(FireworkRoom.class)
public abstract class FireworkRoomMixin {
    @Inject(
            method = "generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(Lgreymerk/roguelike/worldgen/Coord;Lgreymerk/roguelike/worldgen/Direction;)Lgreymerk/roguelike/dungeon/rooms/prototype/FireworkRoom;",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/dungeon/rooms/prototype/FireworkRoom;launcher(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Direction;Lgreymerk/roguelike/worldgen/Coord;)V",
                    ordinal = 3,
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            remap = false
    )
    // Set a flag, so we only place the sneaky rod inside the desired Dropper
    private void RLD_FireworkRoom_launcher_initFlag(Coord at, Direction entrance, CallbackInfoReturnable<FireworkRoom> cir) {
        localicator$placeDocmRod = true;
    }
    
    @Redirect(
            method = "launcher(Lgreymerk/roguelike/worldgen/WorldEditor;Lgreymerk/roguelike/worldgen/Direction;Lgreymerk/roguelike/worldgen/Coord;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lgreymerk/roguelike/worldgen/WorldEditor;setItem(Lgreymerk/roguelike/worldgen/Coord;ILcom/github/fnar/minecraft/item/RldItemStack;)V",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Replace the Wooden Hoe with a sneaky Rod
    private void RLD_FireworkRoom_launcher_setItem_novelty(WorldEditor editor, Coord cursor, int slot, RldItemStack itemStack) {
        if (localicator$placeDocmRod) {
            // Nobody checks the Firework room anyway. 1/3 chance.
            if (editor.getRandom().nextInt(3) == 0) {
                itemStack = ItemNovelty.docmRodOfCommand();
            }
            localicator$placeDocmRod = false;
        }
        editor.setItem(cursor, slot, itemStack);
    }
    
    @Unique
    private boolean localicator$placeDocmRod;
}
