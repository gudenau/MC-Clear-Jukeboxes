package net.gudenau.clearjukebox.block;

import net.gudenau.clearjukebox.tile.TileEntityClearJukebox;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by gudenau on 1/20/2017.
 * <p>
 * ClearJukebox
 */
public class BlockClearJukebox extends Block {
    public BlockClearJukebox() {
        super(Material.WOOD, MapColor.DIRT);
        setCreativeTab(CreativeTabs.DECORATIONS);

        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){return false;}

    @Override
    public boolean isFullCube(IBlockState state){return false;}

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){return BlockRenderLayer.CUTOUT;}

    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state){
        return new TileEntityClearJukebox();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state){return true;}

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos){
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityClearJukebox){
            ItemStack itemstack = ((TileEntityClearJukebox)tileentity).getRecord();

            if (!itemstack.isEmpty()){
                return Item.getIdFromItem(itemstack.getItem()) + 1 - Item.getIdFromItem(Items.RECORD_13);
            }
        }

        return 0;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        dropRecord(worldIn, pos, state);
        super.breakBlock(worldIn, pos, state);
    }

    private void dropRecord(World worldIn, BlockPos pos, IBlockState state){
        if (!worldIn.isRemote){
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityClearJukebox){
                TileEntityClearJukebox jukebox = (TileEntityClearJukebox)tileentity;
                ItemStack itemstack = jukebox.getRecord();

                if (!itemstack.isEmpty()){
                    worldIn.playEvent(1010, pos, 0);
                    worldIn.playRecord(pos, null);
                    jukebox.setRecord(ItemStack.EMPTY);
                    float f = 0.7F;
                    double d0 = (double)(worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
                    double d1 = (double)(worldIn.rand.nextFloat() * 0.7F) + 0.66000000238418579D;
                    double d2 = (double)(worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
                    EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, itemstack.copy());
                    entityitem.setDefaultPickupDelay();
                    worldIn.spawnEntity(entityitem);
                }
            }
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(!(tileEntity instanceof TileEntityClearJukebox)){
            return false;
        }

        TileEntityClearJukebox jukebox = (TileEntityClearJukebox) tileEntity;
        ItemStack stack = jukebox.getRecord();

        if (!stack.isEmpty()){
            dropRecord(worldIn, pos, state);
            jukebox.setRecord(ItemStack.EMPTY);
            worldIn.setBlockState(pos, state, 3);
            return true;
        }else{
            ItemStack newStack = playerIn.getHeldItem(hand);
            if(newStack.getItem() instanceof ItemRecord){
                ItemStack itemstack = playerIn.getHeldItem(hand);

                jukebox.setRecord(itemstack.copy());
                worldIn.setBlockState(pos, state, 3);

                worldIn.playEvent(null, 1010, pos, Item.getIdFromItem(newStack.getItem()));
                itemstack.shrink(1);
                playerIn.addStat(StatList.RECORD_PLAYED);

                return true;
            }

            return false;
        }
    }
}
