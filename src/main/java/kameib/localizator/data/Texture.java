package kameib.localizator.data;

import net.minecraft.util.ResourceLocation;

public class Texture {
    public ResourceLocation texture;
    public float textureWidth, textureHeight;
    
    public Texture(ResourceLocation texture, float textureWidth, float textureHeight) {
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
}
