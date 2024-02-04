package kameib.localizator.data;

import net.minecraft.util.ResourceLocation;

public class Texture {
    public ResourceLocation texture;
    public int textureWidth, textureHeight;
    
    public Texture(ResourceLocation texture, int textureWidth, int textureHeight) {
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
}
