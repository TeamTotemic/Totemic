package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 02/02/14
 * Time: 13:04
 */
public class TileTotemPole extends TileTotemic
{
    public TotemEffect effect = null;

    public TotemEffect getTotemEffect()
    {
        return effect;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(pos, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
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
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        if(effect != null)
            nbtTagCompound.setString("effect", effect.getName());
    }

}
