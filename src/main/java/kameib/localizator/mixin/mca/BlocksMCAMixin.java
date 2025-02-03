package kameib.localizator.mixin.mca;

import mca.core.minecraft.BlocksMCA;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlocksMCA.class)
public abstract class BlocksMCAMixin {
    /**
     * @author KameiB
     * @reason Try to fix blocks' translation keys.
     */
    @Overwrite(remap = false)
    private static void setBlockName(Block block, String blockName) {
        block.setRegistryName("mca", blockName);
        block.setTranslationKey("mca." + blockName);
    }
}
