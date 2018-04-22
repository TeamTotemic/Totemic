package pokefenn.totemic.tileentity.totem;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.TileTotemic;

public class TileTotemPole extends TileTotemic
{
    private WoodVariant woodType = WoodVariant.OAK;
    private TotemEffect effect = null;

    @Nullable
    public TotemEffect getEffect()
    {
        return effect;
    }

    public void setEffect(TotemEffect effect)
    {
        this.effect = effect;
    }

    public WoodVariant getWoodType()
    {
        return woodType;
    }

    public void setWoodType(WoodVariant woodType)
    {
        this.woodType = woodType;
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
        woodType = WoodVariant.fromID(tag.getByte("wood"));
        if(tag.hasKey("effect", Constants.NBT.TAG_STRING))
            effect = TotemicRegistries.totemEffects().getValue(new ResourceLocation(tag.getString("effect")));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        tag.setByte("wood", (byte) woodType.getID());
        if(effect != null)
            tag.setString("effect", effect.getRegistryName().toString());
        return tag;
    }

}
