package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.block.BlockTotemSocket;
import totemic_commons.pokefenn.block.BlockTotemSupport;
import totemic_commons.pokefenn.lib.Totems;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemIntelligence extends TileTotemic
{

    public void updateEntity()
    {
        super.updateEntity();

        if (!this.worldObj.isRemote)
        {
            if (scanAbove() && scanBelow())
            {
                for (int i = 0; i < 5; i++)
                {
                    if (getTotemSocket(i) != 0)
                    {
                        doEffects(getTotemSocket(i));
                    }
                }
            }

        }

    }

    public boolean canUpdate()
    {
        return true;
    }

    protected boolean scanAbove()
    {

        Block blockQuery = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord)];

        return blockQuery instanceof BlockTotemSocket;
    }

    protected boolean scanBelow()
    {
        Block blockQuery = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord)];

        return blockQuery instanceof BlockTotemSupport;
    }

    protected void doEffects(int metadata)
    {
        switch (metadata)
        {
            //Todo i need to add a item into Totems with a metadata of 0, this needs to do nothing and be hidden, this is so that

            case 0:
                System.out.println("meta 0");
                break;

            case 1:
                System.out.println("meta 1");
                break;

            case 2:
                System.out.println("meta 2");
                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;

            case 8:

                break;

            case 9:

                break;

            default:
                break;

        }

    }

    protected void decreaseChlorophyll(int par1, int subtraction)
    {

        Block support = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord - par1, this.zCoord)];

        TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord - par1, this.zCoord);

        if (support instanceof BlockTotemSupport)
        {

            if (tileEntity instanceof IInventory)
            {
                if (((IInventory) tileEntity).getStackInSlot(TileTotemSupport.SLOT_ONE) != null)
                {
                    ((IInventory) tileEntity).setInventorySlotContents(TileTotemSupport.SLOT_ONE, new ItemStack(ModItems.chlorophyllCrystal, 1, ((IInventory) tileEntity).getStackInSlot(TileTotemSupport.SLOT_ONE).getItemDamage() + subtraction));
                }

            }

        }

    }


    protected int getTotemSocket(int par1)
    {
        Block socket = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + par1, this.zCoord)];

        TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        if (socket instanceof BlockTotemSocket)
        {

            if (tileEntity instanceof IInventory)
            {
                if (((IInventory) tileEntity).getStackInSlot(TileTotemSupport.SLOT_ONE) != null)
                {

                    return ((IInventory) tileEntity).getStackInSlot(TileTotemSocket.SLOT_ONE).getItemDamage();

                }

            }

        }

        return 0;
    }

    protected int getChlorophyll(int par1)
    {

        Block support = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord - par1, this.zCoord)];

        TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord - par1, this.zCoord);

        if (support instanceof BlockTotemSupport)
        {

            if (tileEntity instanceof IInventory)
            {
                return ((IInventory) tileEntity).getStackInSlot(TileTotemSocket.SLOT_ONE).getItemDamage();
            } else
                return 0;

        } else
            return 0;

    }

    protected int decrementAmount(String par1)
    {
        if (par1.equals("cactus"))
        {
            return Totems.DECREMENT_CACTUS;
        } else if (par1.equals("horse"))
        {
            return Totems.DECREMENT_HORSE;
        } else if (par1.equals("hopper"))
        {
            return Totems.DECREMENT_HOPPER;
        } else if (par1.equals("bat"))
        {
            return Totems.DECREMENT_BAT;
        } else if (par1.equals("sun"))
        {
            return Totems.DECREMENT_SUN;
        } else if (par1.equals("blaze"))
        {
            return Totems.DECREMENT_BLAZE;
        } else if (par1.equals("ocelot"))
        {
            return Totems.DECREMENT_OCELOT;
        } else if (par1.equals("squid"))
        {
            return Totems.DECREMENT_SQUID;
        } else if (par1.equals("food"))
        {
            return Totems.DECREMENT_FOOD;
        } else
            return 0;

    }

    protected String getStringFromInt(int par1)
    {
        if (par1 == 1)
        {
            return "cactus";
        } else if (par1 == 2)
        {
            return "horse";
        } else if (par1 == 3)
        {
            return "hopper";
        } else if (par1 == 4)
        {
            return "bat";
        } else if (par1 == 5)
        {
            return "sun";
        } else if (par1 == 6)
        {
            return "blaze";
        } else if (par1 == 7)
        {
            return "ocelot";
        } else if (par1 == 8)
        {
            return "squid";
        } else if (par1 == 9)
        {
            return "food";
        }

        return "swag";
    }
}
