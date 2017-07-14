package net.gudenau.clearjukebox.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by gudenau on 1/20/2017.
 * <p>
 * ClearJukebox
 */
public class TileEntityClearJukebox extends TileEntity {
    private ItemStack record = ItemStack.EMPTY;

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);

        if (compound.hasKey("RecordItem", 10)){
            setRecord(new ItemStack(compound.getCompoundTag("RecordItem")));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound = super.writeToNBT(compound);

        if (!getRecord().isEmpty()){
            compound.setTag("RecordItem", getRecord().writeToNBT(new NBTTagCompound()));
        }

        return compound;
    }

    public ItemStack getRecord(){return record;}

    public void setRecord(ItemStack recordStack){
        record = recordStack;
        markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag(){
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
