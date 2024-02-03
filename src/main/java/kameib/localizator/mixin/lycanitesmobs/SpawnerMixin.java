package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.spawner.Spawner;
import com.lycanitesmobs.core.spawner.trigger.SpawnTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(Spawner.class)
public abstract class SpawnerMixin {
    @Shadow(remap = false)
    public boolean isEnabled(World world, EntityPlayer player) { return false; }
    @Shadow(remap = false)
    public boolean canSpawn(World world, EntityPlayer player, BlockPos triggerPos) { return false; }
    @Shadow(remap = false)
    public int triggersRequired;
    @Shadow(remap = false)
    public boolean doSpawn(World world, EntityPlayer player, SpawnTrigger spawnTrigger, BlockPos triggerPos, int level, int chain) { return false; }
    @Shadow(remap = false)
    protected Map<EntityPlayer, Integer> triggerCounts;
    @Shadow(remap = false)
    public String name;
    @Shadow(remap = false)
    public Map<Integer, String> triggerCountMessages;
    
    /**
     * @author KameiB
     * @reason I just wanted to translate 1 Status Message... :c
     * Line 373: player.sendStatusMessage(new TextComponentString(message), true);
     */
    @Overwrite(remap = false)
    public boolean trigger(World world, EntityPlayer player, SpawnTrigger spawnTrigger, BlockPos triggerPos, int level, int countAmount, int chain) {
        if(!this.isEnabled(world, player)) {
            return false;
        }

        LycanitesMobs.logDebug("JSONSpawner", "~O==================== Spawner Triggered: " + this.name + " ====================O~");
        if(!this.canSpawn(world, player, triggerPos)) {
            LycanitesMobs.logDebug("JSONSpawner", "This Spawner Cannot Spawn");
            return false;
        }

        // Only One Trigger Required:
        if(this.triggersRequired <= 1 || player == null) {
            LycanitesMobs.logDebug("JSONSpawner", "Only one trigger required.");
            return this.doSpawn(world, player, spawnTrigger, triggerPos, level, chain);
        }

        // Get Current Count:
        if(!this.triggerCounts.containsKey(player)) {
            this.triggerCounts.put(player, Math.max(countAmount, 0));
        }
        int currentCount = this.triggerCounts.get(player);
        int lastCount = currentCount;

        // Change Count:
        if(countAmount == 0) {
            currentCount = 0;
        }
        else {
            currentCount += countAmount;
        }
        if(currentCount != lastCount) {
            if(this.triggerCountMessages.containsKey(currentCount)) {                
                player.sendStatusMessage(new TextComponentTranslation(this.triggerCountMessages.get(currentCount)), true);
            }
        }

        // Required Count Met:
        LycanitesMobs.logDebug("JSONSpawner", "Trigger " + currentCount + "/" + this.triggersRequired);
        if(currentCount >= this.triggersRequired) {
            this.triggerCounts.put(player, 0);
            return this.doSpawn(world, player, spawnTrigger, triggerPos, level, chain);
        }

        this.triggerCounts.put(player, currentCount);
        return false;
    }
}
