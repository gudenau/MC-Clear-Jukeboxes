package net.gudenau.clearjukebox.proxy;

import net.gudenau.clearjukebox.ClearJukebox;
import net.gudenau.clearjukebox.renderer.TileEntityJukeboxRenderer;
import net.gudenau.clearjukebox.tile.TileEntityClearJukebox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by gudenau on 1/22/2017.
 * <p>
 * ClearJukebox
 */
@SuppressWarnings("unused")
public class ClientProxy extends Proxy {
    @Override
    public void preInit(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityClearJukebox.class, new TileEntityJukeboxRenderer());
    }

    @SubscribeEvent
    public void registerBlockModels(ModelRegistryEvent event){
        ModelLoader.setCustomModelResourceLocation(ClearJukebox.instance.jukeboxItem, 0, new ModelResourceLocation(ClearJukebox.instance.jukeboxItem.getRegistryName(), "inventory"));
        for(EnumDyeColor enumdyecolor : EnumDyeColor.values()){
            ModelLoader.setCustomModelResourceLocation(ClearJukebox.instance.jukeboxStainedItem, enumdyecolor.getMetadata(), new ModelResourceLocation(ClearJukebox.instance.jukeboxStainedItem.getRegistryName(), "color=" + enumdyecolor.getName()));
        }
    }
}
