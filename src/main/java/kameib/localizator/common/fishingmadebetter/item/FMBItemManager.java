package kameib.localizator.common.fishingmadebetter.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class FMBItemManager {
    public static final ItemLavaFishBucket LAVA_FISH_BUCKET = new ItemLavaFishBucket();
    public static final ItemVoidBucket VOID_BUCKET = new ItemVoidBucket();
    public static final ItemVoidFishBucket VOID_FISH_BUCKET = new ItemVoidFishBucket();
    
    public static void register(IForgeRegistry<Item> registry) {
        registry.register(LAVA_FISH_BUCKET);
        registry.register(VOID_BUCKET);  
        registry.register(VOID_FISH_BUCKET);
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        LAVA_FISH_BUCKET.registerItemModel();
        VOID_BUCKET.registerItemModel();
        VOID_FISH_BUCKET.registerItemModel();
    }
}
