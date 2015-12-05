package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 02/02/14
 * Time: 13:04
 */
public class TileTotemPole extends TileTotemic
{
    //compatibility with legacy worlds
    public static final TotemEffect[] legacyIDMapping = {
            null, HandlerInitiation.horseTotem, HandlerInitiation.squidTotem, HandlerInitiation.blazeTotem,
            HandlerInitiation.ocelotTotem, HandlerInitiation.batTotem, HandlerInitiation.spiderTotem, HandlerInitiation.cowTotem
    };

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
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        if(nbtTagCompound.hasKey("effect", Constants.NBT.TAG_STRING))
            effect = Totemic.api.getTotem(nbtTagCompound.getString("effect"));
        else if(nbtTagCompound.hasKey("totemId", Constants.NBT.TAG_INT)) {
            //compatibility for worlds created with a legacy version (<= 0.5.1)
            //TODO: Remove this code later at some point
            effect = legacyIDMapping[nbtTagCompound.getInteger("totemId")];
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        if(effect != null)
            nbtTagCompound.setString("effect", effect.getName());
    }

}
