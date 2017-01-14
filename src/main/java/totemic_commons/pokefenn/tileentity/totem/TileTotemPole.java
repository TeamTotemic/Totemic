package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemic;

public class TileTotemPole extends TileTotemic
{
    private TotemEffect effect = null;

    public TotemEffect getEffect()
    {
        return effect;
    }

    public void setEffect(TotemEffect effect)
    {
        this.effect = effect;
        markDirty();
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        if(tag.hasKey("effect", Constants.NBT.TAG_STRING))
            effect = Totemic.api.registry().getTotem(tag.getString("effect"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        if(effect != null)
            tag.setString("effect", effect.getName());
        return tag;
    }

}
