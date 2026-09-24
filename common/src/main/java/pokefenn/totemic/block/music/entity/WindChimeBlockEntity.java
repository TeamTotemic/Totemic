package pokefenn.totemic.block.music.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.util.BlockUtil;

public class WindChimeBlockEntity extends BlockEntity {
    private static final int CONGESTION_RANGE = 8;
    private static final int MAX_NEARBY_CHIMES = 2;

    private boolean firstServerTick = true;

    private boolean isCongested = false;
    private boolean isPlaying = true;
    private int stateChangeTime = 0;

    public WindChimeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.wind_chime.get(), pPos, pBlockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WindChimeBlockEntity tile) {
        if(tile.firstServerTick) {
            tile.updateCongestionStatus();
            tile.firstServerTick = false;
        }
        if(tile.isCongested)
            return;

        if(tile.stateChangeTime > 0) {
            if(tile.isPlaying && tile.stateChangeTime % 40 == 0)
                tile.playMusic();
            tile.stateChangeTime--;
        }
        else {
            if(tile.isPlaying) {
                tile.isPlaying = false;
                tile.stateChangeTime = (int) (20.0 * (40.0 + 5.0 * level.getRandom().nextGaussian())); //40 ± 5 seconds
            }
            else {
                tile.isPlaying = true;
                tile.stateChangeTime = 8 * 20;
            }
            setChanged(level, pos, state);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, WindChimeBlockEntity tile) {
        if(tile.isCongested && level.getGameTime() % 2 == 0) {
            var rand = level.getRandom();
            level.addAlwaysVisibleParticle(ParticleTypes.CRIT, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0, 0, 0);
        }
    }

    private void playMusic() {
        var pos = worldPosition;
        int baseAmount = ModContent.wind_chime.get().getBaseOutput();
        int bonus = level.getBlockState(pos.above()).is(BlockTags.LEAVES) ? baseAmount / 2 : 0;
        TotemicAPI.get().music().playMusic(level, Vec3.atBottomCenterOf(pos), null, ModContent.wind_chime.get(), MusicAPI.DEFAULT_RANGE, baseAmount + bonus);
    }

    private void updateCongestionStatus() {
        var count = BlockUtil.getBlockEntitiesInRange(ModBlockEntities.wind_chime.get(), level, worldPosition, CONGESTION_RANGE)
                .filter(tile -> tile != this && !tile.isCongested)
                .limit(MAX_NEARBY_CHIMES + 1)
                .count();
        boolean newStatus = count > MAX_NEARBY_CHIMES;
        if(isCongested != newStatus) {
            isCongested = newStatus;
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void setRemoved() {
        if(!level.isClientSide()) {
            BlockUtil.getBlockEntitiesInRange(ModBlockEntities.wind_chime.get(), level, worldPosition, CONGESTION_RANGE)
                    .forEach(WindChimeBlockEntity::updateCongestionStatus);
        }
        super.setRemoved();
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        out.putBoolean("Playing", isPlaying);
        out.putInt("Time", stateChangeTime);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        // This is not nice but this method has to do double duty, both reading from disk on the server side and
        // reading from a packet on the client side.
        if(Totemic.platform().containsKey(in, "Playing")) { // from saveAdditional
            isPlaying = in.getBooleanOr("Playing", true);
            stateChangeTime = in.getIntOr("Time", 0);
        }
        else if(Totemic.platform().containsKey(in, "PlayingTime")) { // legacy from 1.21.1 and before
            isPlaying = true;
            stateChangeTime = in.getIntOr("PlayingTime", 0);
        }
        else if(Totemic.platform().containsKey(in, "Cooldown")) { // legacy from 1.21.1 and before
            isPlaying = false;
            stateChangeTime = in.getIntOr("Cooldown", 0);
        }

        if(Totemic.platform().containsKey(in, "IsCongested")) { // from getUpdateTag
            isCongested = in.getBooleanOr("IsCongested", false);
        }
    }

    @Override
    public CompoundTag getUpdateTag(Provider registries) {
        var tag = new CompoundTag();
        tag.putBoolean("IsCongested", isCongested);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean isCongested() {
        return isCongested;
    }
}
