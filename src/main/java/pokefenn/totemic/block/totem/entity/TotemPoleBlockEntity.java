package pokefenn.totemic.block.totem.entity;

import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
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
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.neoforge.client.TotemPoleModelData;

public class TotemPoleBlockEntity extends BlockEntity {
    //Remember the values as read from NBT to avoid permanently replacing them with the defaults in case entries are removed from the registry
    //(e.g. when the config gets changed or corrupted)
    private ResourceLocation woodTypeLoc = ModContent.oak.get().getRegistryName();
    private ResourceLocation carvingLoc = ModContent.none.get().getRegistryName();

    private TotemWoodType woodType = ModContent.oak.get();
    private TotemCarving carving = ModContent.none.get();

    public TotemPoleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.totem_pole.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("Wood", woodTypeLoc.toString());
        tag.putString("Carving", carvingLoc.toString());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, Provider registries) {
        super.loadAdditional(tag, registries);
        woodTypeLoc = Objects.requireNonNullElseGet(ResourceLocation.tryParse(tag.getString("Wood")), () -> ModContent.oak.get().getRegistryName());
        var optWood = TotemicAPI.get().registry().woodTypes().getOptional(woodTypeLoc);
        if(optWood.isEmpty())
            Totemic.logger.warn("Unknown Totem Wood Type: '{}'", woodTypeLoc);
        woodType = optWood.orElseGet(ModContent.oak);

        carvingLoc = Objects.requireNonNullElseGet(ResourceLocation.tryParse(tag.getString("Carving")), () -> ModContent.none.get().getRegistryName());
        var optCarving = TotemicAPI.get().registry().totemCarvings().getOptional(carvingLoc);
        if(optCarving.isEmpty())
            Totemic.logger.warn("Unknown Totem Carving: '{}'", carvingLoc);
        carving = optCarving.orElseGet(ModContent.none);
        Totemic.platform().requestModelDataUpdate(this);
    }

    @Override
    public CompoundTag getUpdateTag(Provider registries) {
        return saveWithoutMetadata(registries);
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
        Totemic.platform().requestModelDataUpdate(this);
        setChanged();
    }

    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(TotemPoleModelData.DATA_PROPERTY, new TotemPoleModelData(woodType, carving)).build();
    }
}
