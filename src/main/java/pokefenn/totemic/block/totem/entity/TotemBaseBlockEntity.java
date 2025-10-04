package pokefenn.totemic.block.totem.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
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
import pokefenn.totemic.client.model.totem.TotemPoleModelData;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;

public class TotemBaseBlockEntity extends BlockEntity {
    private boolean needPoleUpdate = true;

    //see also TotemPoleBlockEntity
    private ResourceLocation woodTypeLoc = ModContent.oak.getId();
    private volatile TotemWoodType woodType = ModContent.oak.get();

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
                totemEffectsBuilder.addAll(carving.getTotemEffects());
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
        this.woodTypeLoc = woodType.getRegistryName();
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
    protected void saveAdditional(CompoundTag tag, Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("Wood", woodTypeLoc.toString());
        tag.putByte("State", state.getID());
        state.save(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, Provider registries) {
        super.loadAdditional(tag, registries);
        woodTypeLoc = Objects.requireNonNullElse(ResourceLocation.tryParse(tag.getString("Wood")), ModContent.oak.getId());
        var optWood = TotemicAPI.get().registry().woodTypes().getOptional(woodTypeLoc);
        if(optWood.isEmpty())
            Totemic.logger.warn("Unknown Totem Wood Type: '{}'", woodTypeLoc);
        woodType = optWood.orElseGet(ModContent.oak);
        requestModelDataUpdate();

        if(tag.contains("State", Tag.TAG_BYTE)) {
            byte id = tag.getByte("State");
            if(id != state.getID())
                state = TotemState.fromID(id, this);
            state.load(tag, registries);
        }
        else
            state = new StateTotemEffect(this);
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

    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(TotemPoleModelData.WOOD_TYPE_PROPERTY, woodType).build();
    }

    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(TotemicCapabilities.MUSIC_ACCEPTOR, ModBlockEntities.totem_base.get(),
                (totem, context) -> totem.state);
    }
}
