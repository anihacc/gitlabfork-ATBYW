package net.azagwen.atbyw.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class IronLadderBlockEntity extends BlockEntity {
    private int level = 0;

    public IronLadderBlockEntity(BlockPos pos, BlockState state) {
        super(AtbywBlockEntityTypes.IRON_LADDER_BLOCK_ENTITY, pos, state);
    }

    public IronLadderBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.level = nbt.getInt("level");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("level", this.level);
        return nbt;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.writeNbt(new NbtCompound());
    }

    public void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }

    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 13, this.toInitialChunkDataNbt());
    }
}
