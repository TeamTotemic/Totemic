package pokefenn.totemic.block.totem.entity;

import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;

public class TotemPoleBlockEntity extends BlockEntity {
    private TotemWoodType woodType = TotemWoodType.CEDAR;
    private TotemCarving carving = ModContent.none;

    public TotemPoleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.totem_pole.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Wood", "totemic:" + woodType.getName());
        tag.putString("Carving", carving.getRegistryName().toString());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        var woodKey = ResourceLocation.tryParse(tag.getString("Wood"));
        var wood = TotemWoodType.getWoodTypes().stream()
                .filter(wt -> wt.getName().equals(woodKey.getPath()))
                .findAny();
        if(wood.isEmpty())
            Totemic.logger.error("Unknown Totem Wood Type: '{}'", tag.getString("Wood"));
        woodType = wood.orElse(TotemWoodType.CEDAR);
        var carvingKey = ResourceLocation.tryParse(tag.getString("Carving"));
        if(!TotemicAPI.get().registry().totemCarvings().containsKey(carvingKey))
            Totemic.logger.error("Unknown Totem Carving: '{}'", tag.getString("Carving"));
        carving = TotemicAPI.get().registry().totemCarvings().getValue(carvingKey);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public TotemWoodType getWoodType() {
        return woodType;
    }

    public TotemCarving getCarving() {
        return carving;
    }

    public void setCarving(TotemCarving carving) {
        this.carving = Objects.requireNonNull(carving);
        setChanged();
    }
}
