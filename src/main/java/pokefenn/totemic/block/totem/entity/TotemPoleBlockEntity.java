package pokefenn.totemic.block.totem.entity;

import java.util.Objects;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.client.model.BakedTotemPoleModel;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;

public class TotemPoleBlockEntity extends BlockEntity {
    private TotemWoodType woodType = ModContent.oak;
    private TotemCarving carving = ModContent.none;

    public TotemPoleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.totem_pole.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Wood", woodType.getRegistryName().toString());
        tag.putString("Carving", carving.getRegistryName().toString());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        var woodKey = ResourceLocation.tryParse(tag.getString("Wood"));
        if(!TotemicAPI.get().registry().woodTypes().containsKey(woodKey))
            Totemic.logger.error("Unknown Totem Wood Type: '{}'", tag.getString("Wood"));
        woodType = TotemicAPI.get().registry().woodTypes().getValue(woodKey);
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

    public void setAppearance(TotemWoodType woodType, TotemCarving carving) {
        this.woodType = Objects.requireNonNull(woodType);
        this.carving = Objects.requireNonNull(carving);
        requestModelDataUpdate();
        setChanged();
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder().with(BakedTotemPoleModel.DATA_PROPERTY, new BakedTotemPoleModel.Data(woodType, carving)).build();
    }
}
