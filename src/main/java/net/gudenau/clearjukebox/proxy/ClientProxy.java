package net.gudenau.clearjukebox.proxy;

import net.gudenau.clearjukebox.ClearJukebox;
import net.gudenau.clearjukebox.renderer.TileEntityJukeboxRenderer;
import net.gudenau.clearjukebox.tile.TileEntityClearJukebox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

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

    @Override
    public void init(){
        if(false){
            Item jukeboxItem = ClearJukebox.instance.jukeboxItem;
            Item jukeboxStainedItem = ClearJukebox.instance.jukeboxStainedItem;

            ModelLoader.setCustomModelResourceLocation(jukeboxItem, 0, new ModelResourceLocation(jukeboxItem.getRegistryName(), "inventory"));
            ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
            //mesher.register(jukeboxItem, 0, new ModelResourceLocation(jukeboxItem.getRegistryName(), "inventory"));

            for(EnumDyeColor enumdyecolor : EnumDyeColor.values()){
                //list.add(new ItemStack(itemIn, 1, enumdyecolor.getMetadata()));
                ModelLoader.setCustomModelResourceLocation(jukeboxStainedItem, enumdyecolor.getMetadata(), new ModelResourceLocation(jukeboxStainedItem.getRegistryName(), enumdyecolor.getName()));
                //mesher.register(jukeboxStainedItem, enumdyecolor.getMetadata(), new ModelResourceLocation("gud_clear_jukebox:clear_jukebox_" + enumdyecolor.getName(), "inventory"));
            }
        }
    }
}
