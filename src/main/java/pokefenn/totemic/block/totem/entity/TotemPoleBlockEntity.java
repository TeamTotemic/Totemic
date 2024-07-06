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
import net.neoforged.neoforge.client.model.data.ModelData;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.client.model.totem.TotemPoleModelData;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;

public class TotemPoleBlockEntity extends BlockEntity {
    //Remember the values as read from NBT to avoid permanently replacing them with the defaults in case entries are removed from the registry
    //(e.g. when the config gets changed or corrupted)
    private ResourceLocation woodTypeLoc = ModContent.oak.getId();
    private ResourceLocation carvingLoc = ModContent.none.getId();

    //Fields need to be volatile since getModelData() is called from chunk render threads
    private volatile TotemWoodType woodType = ModContent.oak.get();
    private volatile TotemCarving carving = ModContent.none.get();

    public TotemPoleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.totem_pole.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Wood", woodTypeLoc.toString());
        tag.putString("Carving", carvingLoc.toString());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        woodTypeLoc = Objects.requireNonNullElse(ResourceLocation.tryParse(tag.getString("Wood")), ModContent.oak.getId());
        var optWood = TotemicAPI.get().registry().woodTypes().getOptional(woodTypeLoc);
        if(optWood.isEmpty())
            Totemic.logger.error("Unknown Totem Wood Type: '{}'", woodTypeLoc);
        woodType = optWood.orElseGet(ModContent.oak);

        carvingLoc = Objects.requireNonNullElse(ResourceLocation.tryParse(tag.getString("Carving")), ModContent.none.getId());
        var optCarving = TotemicAPI.get().registry().totemCarvings().getOptional(carvingLoc );
        if(optCarving.isEmpty())
            Totemic.logger.error("Unknown Totem Carving: '{}'", carvingLoc );
        carving = optCarving.orElseGet(ModContent.none);
        requestModelDataUpdate();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
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
        this.woodTypeLoc = woodType.getRegistryName();
        this.carving = Objects.requireNonNull(carving);
        this.carvingLoc = carving.getRegistryName();
        requestModelDataUpdate();
        setChanged();
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder().with(TotemPoleModelData.DATA_PROPERTY, new TotemPoleModelData(woodType, carving)).build();
    }
}
