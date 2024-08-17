package kameib.localizator.util;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.entity.Slot;
import greymerk.roguelike.monster.Mob;

public class RLD_MobUtil {
    public static Mob supponpon(Mob mob) {
        mob.equipMainhand(BlockType.AIR.asItem().asStack());
        mob.equipOffhand(BlockType.AIR.asItem().asStack());

        mob.equip(Slot.FEET, BlockType.AIR.asItem().asStack());
        mob.equip(Slot.LEGS, BlockType.AIR.asItem().asStack());
        mob.equip(Slot.CHEST, BlockType.AIR.asItem().asStack());
        mob.equip(Slot.HEAD, BlockType.AIR.asItem().asStack());
        
        return mob;
    }
}
