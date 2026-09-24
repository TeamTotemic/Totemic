package pokefenn.totemic.block.totem.entity;

import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;

public class TotemPoleBlockEntity extends BlockEntity {
    //Remember the values as read from NBT to avoid permanently replacing them with the defaults in case entries are removed from the registry
    //(e.g. when the config gets changed or corrupted)
    private Identifier woodTypeLoc = ModContent.oak.get().getRegistryName();
    private Identifier carvingLoc = ModContent.none.get().getRegistryName();

    private TotemWoodType woodType = ModContent.oak.get();
    private TotemCarving carving = ModContent.none.get();

    public TotemPoleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.totem_pole.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        out.store("Wood", Identifier.CODEC, woodTypeLoc);
        out.store("Carving", Identifier.CODEC, carvingLoc);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        // TODO: Might want to do the reading with a Codec instead, so that we can report unknown keys to the ProblemReporter rather than logging a warning
        woodTypeLoc = in.read("Wood", Identifier.CODEC).orElseGet(ModContent.oak.get()::getRegistryName);
        var optWood = TotemicAPI.get().registry().woodTypes().getOptional(woodTypeLoc);
        if(optWood.isEmpty())
            Totemic.logger.warn("Totem Pole at {} has an unknown Wood Type saved: '{}'", worldPosition, woodTypeLoc);
        woodType = optWood.orElseGet(ModContent.oak);

        carvingLoc = in.read("Carving", Identifier.CODEC).orElseGet(ModContent.none.get()::getRegistryName);
        var optCarving = TotemicAPI.get().registry().totemCarvings().getOptional(carvingLoc);
        if(optCarving.isEmpty())
            Totemic.logger.warn("Totem Pole at {} has an unknown Carving saved: '{}'", worldPosition, carvingLoc);
        carving = optCarving.orElseGet(ModContent.none);
        Totemic.platform().requestModelDataUpdate(this);
    }

    @Override
    public CompoundTag getUpdateTag(Provider registries) {
        return saveCustomOnly(registries);
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
}
