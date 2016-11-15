package ninja.thepurple.groblins.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import ninja.thepurple.groblins.client.model.ModelGroblin;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

import javax.annotation.Nonnull;

public class RenderGroblin extends RenderLiving<EntityGroblin> {

    private ResourceLocation mobTexture = new ResourceLocation("groblins:textures/entity/groblin.png");

    public static final Factory FACTORY = new Factory();

    public RenderGroblin(RenderManager rendermanagerIn) {
        // We use the vanilla zombie model here and we simply
        // retexture it. Of course you can make your own model
        super(rendermanagerIn, new ModelGroblin(.5F), 0.7F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityGroblin entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityGroblin> {

        @Override
        public Render<? super EntityGroblin> createRenderFor(RenderManager manager) {
            return new RenderGroblin(manager);
        }

    }

}