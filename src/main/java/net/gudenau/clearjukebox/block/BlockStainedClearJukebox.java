package net.gudenau.clearjukebox.block;

import net.minecraft.block.BlockBeacon;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraft.block.BlockStainedGlass.COLOR;

/**
 * Created by gudenau on 1/21/2017.
 * <p>
 * ClearJukebox
 */
public class BlockStainedClearJukebox extends BlockClearJukebox{
    public BlockStainedClearJukebox(){
        super();

        setDefaultState(blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
    }

    @Override
    public int damageDropped(IBlockState state){
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tabs, NonNullList<ItemStack> items){
        for(EnumDyeColor enumdyecolor : EnumDyeColor.values()){
            items.add(new ItemStack(this, 1, enumdyecolor.getMetadata()));
        }
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        return MapColor.getBlockColor(state.getValue(COLOR));
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        if(!worldIn.isRemote){
            BlockBeacon.updateColorAsync(worldIn, pos);
        }
    }

    @Override
    public float[] getBeaconColorMultiplier(IBlockState state, World world, BlockPos pos, BlockPos beaconPos){
        return state.getValue(COLOR).getColorComponentValues();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        if(!worldIn.isRemote){
            BlockBeacon.updateColorAsync(worldIn, pos);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.TRANSLUCENT;
    }

    /**
     * Get the unlocalised name suffix for the specified {@link ItemStack}.
     *
     * @param stack The ItemStack
     * @return The unlocalised name suffix
     */
    public String getName(final ItemStack stack) {
        final int metadata = stack.getMetadata();

        return EnumDyeColor.byMetadata(metadata).getName();
    }
}
