package net.gudenau.clearjukebox.renderer;

import net.gudenau.clearjukebox.tile.TileEntityClearJukebox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

/**
 * Created by gudenau on 1/21/2017.
 * <p>
 * ClearJukebox
 */
public class TileEntityJukeboxRenderer extends TileEntitySpecialRenderer<TileEntityClearJukebox> {
    @Override
    public void renderTileEntityFast(TileEntityClearJukebox tile, double x, double y, double z, float partialTicks, int destroyStage, float partial, net.minecraft.client.renderer.BufferBuilder buffer) {
        if(tile == null){
            return;
        }

        ItemStack stack = tile.getRecord();

        if(!stack.isEmpty()) {
            RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

            GlStateManager.pushMatrix();

            GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);

            float tick = Minecraft.getMinecraft().world.getTotalWorldTime();
            tick = (tick - 1) + (tick - (tick - 1)) * partialTicks;
            GlStateManager.rotate(tick * 3, 0, 1, 0);
            GlStateManager.rotate((float) Math.sin(tick / 4) / 2, 1, 0, 1);

            GlStateManager.rotate(90, 0, 1, 0);
            GlStateManager.rotate(90, 1, 0, 0);

            GlStateManager.scale(0.9, 0.9, 0.9);

            itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.NONE);
            GlStateManager.popMatrix();
        }
    }
}
