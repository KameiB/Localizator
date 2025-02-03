package kameib.localizator.mixin.mca;
import mca.core.minecraft.ItemsMCA;
import mca.items.ItemGuideBook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemsMCA.class)
public class ItemsMCAMixin {
    /**
     * @author KameiB
     * @reason Localize books contents
     */
    @Overwrite(remap = false)
    public static void setBookNBT(ItemStack stack) {
        Item book = stack.getItem();
        NBTTagCompound nbt = new NBTTagCompound();
        if (book == BOOK_DEATH) {
            nbt.setString("title", "Death, and How to Cure It!");
            nbt.setString("locTitle", "item.mca.book.book_death.title");
            nbt.setString("author", "Ozzie the Warrior");
            nbt.setString("locAuthor", "item.mca.book.book_death.author");
            nbt.setBoolean("resolved", true);
            NBTTagList pages = new NBTTagList();
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.1\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.2\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.3\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.4\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.5\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.6\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.7\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.8\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_death.page.9\"}"));
            nbt.setTag("pages", pages);
        } else if (book == BOOK_ROMANCE) {
            nbt.setString("title", "Relationships and You");
            nbt.setString("locTitle", "item.mca.book.book_romance.title");
            nbt.setString("author", "Gerry the Librarian");
            nbt.setString("locAuthor", "item.mca.book.book_romance.author");
            nbt.setBoolean("resolved", true);
            NBTTagList pages = new NBTTagList();
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_romance.page.1\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_romance.page.2\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_romance.page.3\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_romance.page.4\"}"));
            nbt.setTag("pages", pages);
        } else if (book == BOOK_FAMILY) {
            nbt.setString("title", "Managing Your Family Vol. XI");
            nbt.setString("locTitle", "item.mca.book.book_family.title");
            nbt.setString("author", "Leanne the Cleric");
            nbt.setString("locAuthor", "item.mca.book.book_family.author");
            nbt.setBoolean("resolved", true);
            NBTTagList pages = new NBTTagList();
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_family.page.1\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_family.page.2\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_family.page.3\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_family.page.4\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_family.page.5\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_family.page.6\"}"));
            nbt.setTag("pages", pages);
        } else if (book == BOOK_ROSE_GOLD) {
            nbt.setString("title", "On Rose Gold");
            nbt.setString("locTitle", "item.mca.book.book_rose_gold.title");
            nbt.setString("author", "William the Miner");
            nbt.setString("locAuthor", "item.mca.book.book_rose_gold.author");
            nbt.setBoolean("resolved", true);
            NBTTagList pages = new NBTTagList();
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_rose_gold.page.1\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_rose_gold.page.2\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_rose_gold.page.3\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_rose_gold.page.4\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_rose_gold.page.5\"}"));
            nbt.setTag("pages", pages);
        } else if (book == BOOK_INFECTION) {
            nbt.setString("title", "Beware the Infection!");
            nbt.setString("locTitle", "item.mca.book.book_infection.title");
            nbt.setString("author", "Richard the Zombie");
            nbt.setString("locAuthor", "item.mca.book.book_infection.author");
            nbt.setBoolean("resolved", true);
            NBTTagList pages = new NBTTagList();
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_infection.page.1\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_infection.page.2\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_infection.page.3\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_infection.page.4\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_infection.page.5\"}"));
            pages.appendTag(new NBTTagString("{\"translate\":\"item.mca.book.book_infection.page.6\"}"));
            nbt.setTag("pages", pages);
        }

        stack.setTagCompound(nbt);
    }
    
    /**
     * @author KameiB
     * @reason Fix TranslationKeys to current standards
     */
    @Overwrite(remap = false)
    private static void setItemName(Item item, String itemName) {
        item.setTranslationKey("mca." + itemName);
        item.setRegistryName(new ResourceLocation("mca:" + itemName));
    }
    
    @Shadow(remap = false)
    public static final ItemGuideBook BOOK_DEATH = new ItemGuideBook();
    @Shadow(remap = false)
    public static final ItemGuideBook BOOK_ROMANCE = new ItemGuideBook();
    @Shadow(remap = false)
    public static final ItemGuideBook BOOK_FAMILY = new ItemGuideBook();
    @Shadow(remap = false)
    public static final ItemGuideBook BOOK_ROSE_GOLD = new ItemGuideBook();
    @Shadow(remap = false)
    public static final ItemGuideBook BOOK_INFECTION = new ItemGuideBook();
}
