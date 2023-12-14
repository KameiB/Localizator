package localizator.mixin.minecraft;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class BossEntityInfo {
    protected UUID bossUUID;
    protected EntityLiving bossEntity;
    /*public BossEntityInfo() {
        new BossEntityInfo(new UUID(0,0), null);
    }*/
    
    public BossEntityInfo(UUID uuid, EntityLiving entity) {
        bossUUID = uuid;
        bossEntity = entity;
    }
}
