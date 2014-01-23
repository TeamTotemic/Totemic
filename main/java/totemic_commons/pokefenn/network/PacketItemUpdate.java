package totemic_commons.pokefenn.network;

import com.pahimar.ee3.lib.ItemUpdateTypes;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketItemUpdate extends PacketTotemic {
	
	
	public byte slot;
    public byte updateType;

    public PacketItemUpdate() {

        super(PacketTypeHandler.ITEM_UPDATE, false);
    }

    public PacketItemUpdate(byte slot, byte updateType) {

        super(PacketTypeHandler.ITEM_UPDATE, false);
        this.slot = slot;
        this.updateType = updateType;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {

        data.writeByte(slot);
        data.writeByte(updateType);
    }

    @Override
    public void readData(DataInputStream data) throws IOException {

        slot = data.readByte();
        updateType = data.readByte();
    }

    @Override
    public void execute(INetworkManager manager, Player player) {

        EntityPlayer thePlayer = (EntityPlayer) player;
        ItemStack destroyedStack = thePlayer.inventory.getStackInSlot(slot);

        if (updateType == ItemUpdateTypes.DESTROYED) {
            thePlayer.renderBrokenItemStack(destroyedStack);
        }
    }

}
