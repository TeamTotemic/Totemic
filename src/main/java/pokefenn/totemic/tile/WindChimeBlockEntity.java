package pokefenn.totemic.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModTileEntities;

public class WindChimeBlockEntity extends BlockEntity {
    private int playingTimeLeft = 0;
    private int cooldown = 0; //Only used on the server side
    private boolean isCongested = false;

    private static final int PLAYING_TIME = 8 * 20;

    public WindChimeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.wind_chime.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WindChimeBlockEntity tile) {
        if(tile.isPlaying()) {
            if(tile.playingTimeLeft % 40 == 0)
                TotemicAPI.get().music().playMusic(level, pos, null, ModContent.windChime);

            tile.playingTimeLeft--;
            if(tile.playingTimeLeft <= 0)
                tile.setNotPlaying();
        }
        else {
            if(!level.isClientSide) {
                tile.cooldown--;
                if(tile.cooldown <= 0)
                    tile.setPlaying(PLAYING_TIME);
            }
        }
    }

    public boolean isPlaying() {
        return playingTimeLeft > 0;
    }

    public void setPlaying(int time) {
        playingTimeLeft = time;
        if(!level.isClientSide)
            level.blockEvent(getBlockPos(), getBlockState().getBlock(), 0, time); //Notify the client that the chime is playing
        setChanged();
    }

    public void setNotPlaying() {
        playingTimeLeft = 0;
        if(!level.isClientSide)
            cooldown = getRandomCooldown(level.random);
        setChanged();
    }

    @Override
    public boolean triggerEvent(int id, int param) {
        if(!level.isClientSide)
            return true;
        else {
            setPlaying(param);
            return true;
        }
    }

    private int getRandomCooldown(RandomSource rand) {
        return (int) (20.0 * (40.0 + 5.0 * rand.nextGaussian())); //40 ± 5 seconds
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(isPlaying())
            tag.putInt("PlayingTime", playingTimeLeft);
        else
            tag.putInt("Cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if(tag.contains("PlayingTime"))
            playingTimeLeft = tag.getInt("PlayingTime");
        else {
            playingTimeLeft = 0;
            cooldown = tag.getInt("Cooldown");
        }
    }
}
