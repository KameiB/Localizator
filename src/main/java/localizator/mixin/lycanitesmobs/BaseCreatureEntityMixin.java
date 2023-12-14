package localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.Subspecies;
import com.lycanitesmobs.core.info.Variant;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfoServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMixin {
    @ModifyArg(
            method = "isDamageEntityApplicable(Lnet/minecraft/entity/Entity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    remap = true
            ),
            index = 0,
            remap = false
    )
    // Translate Status Message on client side
    // Line 4772: ((EntityPlayer)entity).sendStatusMessage(new TextComponentString(LanguageManager.translate("boss.damage.protection.range")), true);
    private ITextComponent lycanites_BaseCreatureEntity_isDamageEntityApplicable_protection(ITextComponent message) {
        return new TextComponentTranslation("boss.damage.protection.range");
    }
    
    @Shadow(remap = false)
    public BossInfoServer bossInfo;
    @Shadow(remap = false)
    public String getFullName() { return ""; }
    @Shadow(remap = false)
    public boolean isBossAlways() { return false; }
    @Shadow(remap = false)
    public int getBattlePhase() { return 0; }
    
    /**
     * @author KameiB
     * @reason Translate boss name on client side
     */
    @Overwrite(remap = false)
    public void refreshBossHealthName() {
        if(this.bossInfo != null) {
            //String name = this.getFullName();
            TextComponentString Name = new TextComponentString("");
            Name.appendSibling(this.getFullNameLangKey());
            if(this.isBossAlways()) {
                //name += " (" + LanguageManager.translate("entity.phase") + " " + (this.getBattlePhase() + 1) + ")";
                Name.appendText(" (")
                        .appendSibling(new TextComponentTranslation("entity.phase"))
                        .appendText(String.format(" %d)", this.getBattlePhase() + 1));                
            }
            //this.bossInfo.setName(new TextComponentString(name));
            this.bossInfo.setName(Name);
        }
    }
    
    // Had to redo EVERYTHING just because KameiB wanted localized boss names...
    @Unique
    private ITextComponent getFullNameLangKey() {
        String nameFormatting = LanguageManager.translate("entity.lycanitesmobs.creature.name.format");
        String[] nameParts = nameFormatting.split("\\|");
        if (nameParts.length < 4 || nameFormatting.equals("entity.lycanitesmobs.creature.name.format")) {
            nameParts = new String[]{"age", "variant", "subspecies", "species", "level"};
        }

        //StringBuilder name = new StringBuilder();
        TextComponentString txsName = new TextComponentString("");        
        List<ITextComponent> nameComponents = new ArrayList<>();
        for (String namePart : nameParts) {
            switch (namePart) {
                case "age":
                    nameComponents.add(this.getAgeNameLangKey());
                    break;
                case "variant":
                    nameComponents.add(this.getVariantNameLangKey());
                    break;
                case "subspecies":
                    nameComponents.add(this.getSubspeciesNameLangKey());
                    break;
                case "species":
                    nameComponents.add(this.getSpeciesNameLangKey());
                    break;
                case "level":
                    nameComponents.add(this.getLevelNameLangKey());
                    break;
            }
        }

        boolean first = true;
        for (ITextComponent nameComponent : nameComponents) {
            if (nameComponent.getUnformattedComponentText().isEmpty()) {
                continue;
            }
            if (!first) {
                //name.append(" ");
                txsName.appendText(" ");
            }
            first = false;
            //name.append(nameComponent);
            txsName.appendSibling(nameComponent);
        }

        //return name.toString();
        return txsName;
    }
    @Unique
    private ITextComponent getAgeNameLangKey() {
        return new TextComponentString("");
    }
    @Shadow(remap = false)
    public Variant variant;
    @Shadow(remap = false)
    public Variant getVariant() {
        return this.variant;
    }
    @Unique
    private ITextComponent getVariantNameLangKey() {
        if(this.getVariant() != null) {
            //return this.getVariant().getTitle();
            return new TextComponentTranslation("subspecies." + this.getVariant().color + ".name");
        }
        //return "";
        return new TextComponentString("");
    }
    @Shadow(remap = false)
    public Subspecies getSubspecies() {
        if(this.subspecies == null) {
            this.subspecies = this.creatureInfo.getSubspecies(0);
        }
        return this.subspecies;
    }
    @Unique
    private ITextComponent getSubspeciesNameLangKey() {
        if(this.getSubspecies() != null) {
            //return this.getSubspecies().getTitle();
            if (this.getSubspecies().name != null) {
                return new TextComponentTranslation("subspecies." + this.getSubspecies().name + ".name");
            }
        }
        //return "";
        return new TextComponentString("");
    }
    @Unique
    private ITextComponent getSpeciesNameLangKey() {
        return new TextComponentTranslation("entity." + this.creatureInfo.getLocalisationKey() + ".name");
    }
    @Shadow(remap = false)
    public int getLevel() { return 0; }

    @Shadow(remap = false)
    public CreatureInfo creatureInfo;

    @Shadow(remap = false)
    public Subspecies subspecies;

    @Unique
    private ITextComponent getLevelNameLangKey() {
        if(this.getLevel() < 2) {
            //return "";
            return new TextComponentString("");            
        }
        //return " " + LanguageManager.translate("entity.level") + " " + this.getLevel();
        return new TextComponentString(" ").appendSibling(new TextComponentTranslation("entity.level")).appendText(String.format(" %d", this.getLevel()));
    }
}
