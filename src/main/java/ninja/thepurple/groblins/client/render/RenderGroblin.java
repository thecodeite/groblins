package ninja.thepurple.groblins.client.render;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import ninja.thepurple.groblins.client.model.ModelGroblin;
import ninja.thepurple.groblins.common.entity.groblin.EntityGroblin;

import javax.annotation.Nonnull;

//public class RenderGroblin extends RenderLiving<EntityGroblin> {
//
//    private ResourceLocation mobTexture = new ResourceLocation("groblins:textures/entity/groblin.png");
//
//    public static final Factory FACTORY = new Factory();
//
//    public RenderGroblin(RenderManager rendermanagerIn) {
//        // We use the vanilla zombie model here and we simply
//        // retexture it. Of course you can make your own model
//        super(rendermanagerIn, new ModelGroblin(1.0F, true), 0.7F);
//    }
//
//    @Override
//    @Nonnull
//    protected ResourceLocation getEntityTexture(@Nonnull EntityGroblin entity) {
//        return mobTexture;
//    }
//
//    public static class Factory implements IRenderFactory<EntityGroblin> {
//
//        @Override
//        public Render<? super EntityGroblin> createRenderFor(RenderManager manager) {
//            return new RenderGroblin(manager);
//        }
//
//    }
//
//}
public class RenderGroblin extends RenderLivingBase<EntityGroblin> {
    public static final Factory FACTORY = new Factory();
    public static class Factory implements IRenderFactory<EntityGroblin> {
        @Override
        public Render<? super EntityGroblin> createRenderFor(RenderManager manager) {
            return new RenderGroblin(manager);
        }

    }

    private ResourceLocation mobTexture = new ResourceLocation("groblins:textures/entity/groblin.png");

    public RenderGroblin(RenderManager renderManager) {
        super(renderManager, new ModelGroblin(0.0F), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
    }

    @Override
    public ModelGroblin getMainModel() {
        return (ModelGroblin) super.getMainModel();
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityGroblin entity, double x, double y, double z, float entityYaw, float partialTicks) {
        double d0 = y;

        if (entity.isSneaking()) {
            d0 = y - 0.125D;
        }

        this.setModelVisibilities(entity);
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        super.doRender(entity, x, d0, z, entityYaw, partialTicks);
        GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
    }

    private void setModelVisibilities(EntityGroblin groblin) {
        ModelGroblin modelplayer = this.getMainModel();

        ItemStack itemstack = groblin.getHeldItemMainhand();
        ItemStack itemstack1 = groblin.getHeldItemOffhand();
        modelplayer.setInvisible(true);
        modelplayer.isSneak = groblin.isSneaking();
        ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
        ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

        if (itemstack != null) {
            modelbiped$armpose = ModelBiped.ArmPose.ITEM;

            if (groblin.getItemInUseCount() > 0) {
                EnumAction enumaction = itemstack.getItemUseAction();

                if (enumaction == EnumAction.BLOCK) {
                    modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                } else if (enumaction == EnumAction.BOW) {
                    modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }
        }

        if (itemstack1 != null) {
            modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

            if (groblin.getItemInUseCount() > 0) {
                EnumAction enumaction1 = itemstack1.getItemUseAction();

                if (enumaction1 == EnumAction.BLOCK) {
                    modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                }
                // FORGE: fix MC-88356 allow offhand to use bow and arrow animation
                else if (enumaction1 == EnumAction.BOW) {
                    modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }
        }

        modelplayer.rightArmPose = modelbiped$armpose;
        modelplayer.leftArmPose = modelbiped$armpose1;

    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityGroblin entity) {
        return mobTexture;
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityGroblin entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }

    protected void renderEntityName(EntityGroblin entityGroblin, double x, double y, double z, String name, double p_188296_9_) {
//        if (p_188296_9_ < 100.0D) {
//            this.renderLivingLabel(entityGroblin, entityGroblin.getCustomNameTag(), x, y, z, 64);
//            y += (double) ((float) this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * 0.025F);
//
//        }

        super.renderEntityName(entityGroblin, x, y, z, name, p_188296_9_);
    }

    public void renderRightArm(EntityGroblin clientPlayer) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        ModelGroblin modelplayer = this.getMainModel();
        this.setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.swingProgress = 0.0F;
        modelplayer.isSneak = false;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
        modelplayer.bipedRightArm.render(0.0625F);
        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedRightArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }

    public void renderLeftArm(EntityGroblin clientPlayer) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        ModelGroblin modelplayer = this.getMainModel();
        this.setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.isSneak = false;
        modelplayer.swingProgress = 0.0F;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArm.render(0.0625F);
        modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }

}
