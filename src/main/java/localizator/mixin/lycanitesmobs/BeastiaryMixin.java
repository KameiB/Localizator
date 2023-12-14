package localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.Beastiary;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import com.lycanitesmobs.core.info.CreatureManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Beastiary.class)
public abstract class BeastiaryMixin {
    @Shadow(remap = false)
    public ExtendedPlayer extendedPlayer;
    
    /**
     * @author KameiB
     * @reason Translate messages on client side. 
     */
    @Overwrite(remap = false)
    public void sendAddedMessage(CreatureKnowledge creatureKnowledge) {
        if(this.extendedPlayer.player.getEntityWorld().isRemote || !CreatureManager.getInstance().config.beastiaryKnowledgeMessages) {
            return;
        }
        CreatureInfo creatureInfo = creatureKnowledge.getCreatureInfo();
        String message = "message.beastiary.rank_";
        if (creatureKnowledge.rank == 1) {
            message = "message.beastiary.new_";
        }
        //message = message.replace("%creature%", creatureInfo.getTitle());
        //message = message.replace("%rank%", "" + creatureKnowledge.rank);
        this.extendedPlayer.player.sendMessage(new TextComponentTranslation(message,
                 new TextComponentTranslation("entity." + creatureInfo.getLocalisationKey() + ".name"),
                new TextComponentString(String.valueOf(creatureKnowledge.rank))));

        if(creatureInfo.isSummonable() && creatureKnowledge.rank == 2) {
            String summonMessage = "message.beastiary.summonable_";
            //summonMessage = summonMessage.replace("%creature%", creatureInfo.getTitle());
            this.extendedPlayer.player.sendMessage(new TextComponentTranslation(summonMessage,
                    new TextComponentTranslation("entity." + creatureInfo.getLocalisationKey() + ".name")));
        }

        if(creatureInfo.isTameable() && creatureKnowledge.rank == 2) {
            String tameMessage = "message.beastiary.tameable_";
            //tameMessage = tameMessage.replace("%creature%", creatureInfo.getTitle());
            this.extendedPlayer.player.sendMessage(new TextComponentTranslation(tameMessage,
                    new TextComponentTranslation("entity." + creatureInfo.getLocalisationKey() + ".name")));
        }
    }
}
