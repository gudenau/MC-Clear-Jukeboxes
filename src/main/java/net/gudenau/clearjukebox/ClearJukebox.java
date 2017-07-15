package net.gudenau.clearjukebox;

import net.gudenau.clearjukebox.block.BlockClearJukebox;
import net.gudenau.clearjukebox.block.BlockStainedClearJukebox;
import net.gudenau.clearjukebox.proxy.Proxy;
import net.gudenau.clearjukebox.renderer.TileEntityJukeboxRenderer;
import net.gudenau.clearjukebox.tile.TileEntityClearJukebox;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by gudenau on 1/20/2017.
 * <p>
 * ClearJukebox
 */
@Mod(modid = "gud_clear_jukebox")
public class ClearJukebox {
    private Block jukebox;
    public Item jukeboxItem;

    private BlockStainedClearJukebox jukeboxStained;
    public Item jukeboxStainedItem;

    @SuppressWarnings("WeakerAccess")
    @SidedProxy(serverSide = "net.gudenau.clearjukebox.proxy.Proxy", clientSide = "net.gudenau.clearjukebox.proxy.ClientProxy")
    public static Proxy proxy;
    @Mod.Instance("gud_clear_jukebox")
    public static ClearJukebox instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBlockRegisterEvent(RegistryEvent.Register<Block> itemRegister){
        jukebox = new BlockClearJukebox();
        jukebox.setRegistryName(new ResourceLocation("gud_clear_jukebox", "clear_jukebox"));
        jukebox.setUnlocalizedName("gud_clear_jukebox");

        jukeboxStained = new BlockStainedClearJukebox();
        jukeboxStained.setRegistryName(new ResourceLocation("gud_clear_jukebox", "clear_jukebox_stained"));
        jukeboxStained.setUnlocalizedName("gud_clear_stained_jukebox");


        IForgeRegistry<Block> registry = itemRegister.getRegistry();
        registry.register(jukebox);
        registry.register(jukeboxStained);

        GameRegistry.registerTileEntity(TileEntityClearJukebox.class, "gud_clear_jukebox:clear_jukebox");
    }

    @SubscribeEvent
    public void onItemRegisterEvent(RegistryEvent.Register<Item> itemRegister){
        jukeboxItem = new ItemBlock(jukebox).setUnlocalizedName("gud_clear_jukebox");
        jukeboxItem.setRegistryName(jukebox.getRegistryName());

        jukeboxStainedItem = new ItemMultiTexture(jukeboxStained, jukeboxStained, jukeboxStained::getName).setUnlocalizedName("gud_clear_jukebox_stained");
        jukeboxStainedItem.setRegistryName(jukeboxStained.getRegistryName());

        IForgeRegistry<Item> registry = itemRegister.getRegistry();
        registry.register(jukeboxItem);
        registry.register(jukeboxStainedItem);
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init();
    }

    @SubscribeEvent
    public void registerBlockModels(ModelRegistryEvent event){
        ModelLoader.setCustomModelResourceLocation(jukeboxItem, 0, new ModelResourceLocation(jukeboxItem.getRegistryName(), "inventory"));
        for(EnumDyeColor enumdyecolor : EnumDyeColor.values()){
            ModelLoader.setCustomModelResourceLocation(jukeboxStainedItem, enumdyecolor.getMetadata(), new ModelResourceLocation(jukeboxStainedItem.getRegistryName(), "color=" + enumdyecolor.getName()));
        }
    }
}
