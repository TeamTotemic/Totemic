package pokefenn.totemic.block.totem.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.model.data.ModelData;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.client.model.BakedTotemBaseModel;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;

public class TotemBaseBlockEntity extends BlockEntity {
    private boolean needPoleUpdate = true;

    private TotemWoodType woodType = ModContent.oak;
    private final List<TotemCarving> carvingList = new ArrayList<>(TotemEffectAPI.MAX_POLE_SIZE);
    private Set<TotemCarving> carvingSet = null; //Only needed for Medicine Bags, computed lazily
    private Multiset<TotemEffect> totemEffects = ImmutableMultiset.of();
    private int commonTotemEffectInterval = Integer.MAX_VALUE;

    private TotemState state = new StateTotemEffect(this);

    public TotemBaseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.totem_base.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, TotemBaseBlockEntity tile) {
        if(tile.needPoleUpdate) {
            tile.calculateTotemEffects();
            tile.needPoleUpdate = false;
        }

        tile.state.tick();
    }

    private void calculateTotemEffects() {
        carvingList.clear();
        carvingSet = null;
        var totemEffectsBuilder = ImmutableMultiset.<TotemEffect>builder();

        for(int i = 0; i < TotemEffectAPI.MAX_POLE_SIZE; i++) {
            var blockEntity = level.getBlockEntity(worldPosition.above(i + 1));
            if(blockEntity instanceof TotemPoleBlockEntity pole) {
                var carving = pole.getCarving();
                carvingList.add(carving);
                totemEffectsBuilder.addAll(carving.getEffects());
            }
            else
                break;
        }

        totemEffects = totemEffectsBuilder.build();

        // Calculate the greatest common divisor of all the intervals of the effects
        commonTotemEffectInterval = totemEffects.elementSet().stream()
                .mapToInt(TotemEffect::getInterval)
                .reduce(IntMath::gcd)
                .orElse(Integer.MAX_VALUE); //if there are no effects
    }

    public void onPoleChange() {
        needPoleUpdate = true;
    }

    public TotemWoodType getWoodType() {
        return woodType;
    }

    public void setWoodType(TotemWoodType woodType) {
        this.woodType = Objects.requireNonNull(woodType);
        requestModelDataUpdate();
        setChanged();
    }

    public List<TotemCarving> getCarvingList() {
        return carvingList;
    }

    public boolean hasCarving(TotemCarving carving) {
        if(carvingSet == null)
            carvingSet = Set.copyOf(carvingList);
        return carvingSet.contains(carving);
    }

    public Multiset<TotemEffect> getTotemEffects() {
        return totemEffects;
    }

    public int getPoleSize() {
        return carvingList.size();
    }

    public int getCommonTotemEffectInterval() {
        return commonTotemEffectInterval;
    }

    public TotemState getTotemState() {
        return state;
    }

    void setTotemState(TotemState state) {
        if(state != this.state) {
            this.state = state;
            if(level != null) { //prevent NPE when called during loading
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
                setChanged();
                invalidateCapabilities();
            }
        }
    }

    public void resetTotemState() {
        state.resetTotemState();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Wood", woodType.getRegistryName().toString());
        tag.putByte("State", state.getID());
        state.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        var optWood = TotemicAPI.get().registry().woodTypes().getOptional(ResourceLocation.tryParse(tag.getString("Wood")));
        if(optWood.isEmpty())
            Totemic.logger.error("Unknown Totem Wood Type: '{}'", tag.getString("Wood"));
        woodType = optWood.orElse(ModContent.oak);

        if(tag.contains("State", Tag.TAG_ANY_NUMERIC)) {
            byte id = tag.getByte("State");
            if(id != state.getID())
                state = TotemState.fromID(id, this);
            state.load(tag);
        }
        else
            state = new StateTotemEffect(this);
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

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder().with(BakedTotemBaseModel.WOOD_TYPE_PROPERTY, woodType).build();
    }

    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(TotemicCapabilities.MUSIC_ACCEPTOR, ModBlockEntities.totem_base.get(),
                (totem, context) -> totem.state);
    }
}
