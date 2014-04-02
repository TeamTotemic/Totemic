package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.block.BlockTotemSocket;
import totemic_commons.pokefenn.totem.TotemUtil;
import vazkii.botania.api.mana.IManaReceiver;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/02/14
 * Time: 19:51
 */
public class TileTotemMana extends TileTotemic implements IManaReceiver//, IWandHUD
{

    int[] SOCKETS;

    public static String TAG_MANA = "mana";

    public static String TAG_KNOWN_MANA = "knownMana";

    int knownMana = -1;

    public static int SOCKET_NUMBER;

    public static int RANGE_UPGRADES;

    public static int mana = 0;

    public static int maxMana = 3000;

    public TileTotemMana()
    {
        SOCKETS = new int[6];
    }

    public void updateEntity()
    {
        super.updateEntity();

        int currentInput = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);

        if (!this.worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 100L == 0)
            {
                setSocketAmounts();
                scanArea();

            }

            if (!(currentInput >= 1))
            {
                if (SOCKET_NUMBER != 0)
                {
                    if (this.worldObj.getWorldTime() % 5L == 0)
                    {
                        for (int i = 1; i <= SOCKET_NUMBER; i++)
                        {
                            if (canDoEffect(getCurrentMana(), TotemUtil.decrementAmount(SOCKETS[i]) * 10))
                            {
                                //TileTotemIntelligence.doEffects(SOCKETS[i], RANGE_UPGRADES, this, false);
                                decreaseMana(TotemUtil.decrementAmount(SOCKETS[i]) * 40);
                            }
                        }
                    }
                }
            }

            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }


    //Todo correct NBT data stuff

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {

        super.readFromNBT(nbtTagCompound);

        mana = nbtTagCompound.getInteger(TAG_MANA);

        if(nbtTagCompound.hasKey(TAG_KNOWN_MANA))
            knownMana = nbtTagCompound.getInteger(TAG_KNOWN_MANA);

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger(TAG_MANA, mana);

    }

    protected void setSocketAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);

        if (block1 instanceof BlockTotemSocket && block2 == null)
        {
            SOCKET_NUMBER = 1;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 == null)
        {
            SOCKET_NUMBER = 2;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 == null)
        {
            SOCKET_NUMBER = 3;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 == null)
        {
            SOCKET_NUMBER = 4;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 instanceof BlockTotemSocket)
        {
            SOCKET_NUMBER = 5;
        } else
            SOCKET_NUMBER = 0;

    }

    protected int getSocketAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);

        if (block1 instanceof BlockTotemSocket && block2 == null)
        {
            return 1;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 == null)
        {
            return 2;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 == null)
        {
            return 3;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 == null)
        {
            return 4;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 instanceof BlockTotemSocket)
        {
            return 5;
        } else
            return 0;

    }

    protected ItemStack getSocketItemStack(int par1)
    {

        TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        return ((IInventory) tileEntity).getStackInSlot(TileTotemSocket.SLOT_ONE);

    }

    protected void scanArea()
    {
        for (int i = 1; i <= getSocketAmounts(); i++)
        {
            if (getSocketAmounts() <= 5)
            {
                if (getSocketItemStack(i) != null)
                {
                    if (getSocketItemStack(i).getItemDamage() == 12)
                    {
                        SOCKETS[i] = 12;
                        if (RANGE_UPGRADES < 5)
                            RANGE_UPGRADES++;

                    } else
                    {
                        SOCKETS[i] = getSocketItemStack(i).getItemDamage();
                        if (RANGE_UPGRADES > 0)
                            RANGE_UPGRADES--;
                    }
                } else
                    SOCKETS[i] = 0;

            }
        }
    }

    public boolean canDoEffect(int currentMana, int manaDecrease)
    {
        return currentMana - manaDecrease >= 0;
    }

    @Override
    public boolean isFull()
    {
        return mana >= maxMana;
    }

    @Override
    public void recieveMana(int mana)
    {
        if (this.mana + mana >= maxMana)
            this.mana = maxMana;

        else
            this.mana = mana + this.getCurrentMana();

    }

    @Override
    public boolean canRecieveManaFromBursts()
    {
        return true;
    }

    @Override
    public int getCurrentMana()
    {
        return mana;
    }

    public void decreaseMana(int manaValue)
    {
        mana = mana - manaValue;
    }

    /*

    @Override
    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z)
    {
        String name = ModBlocks.totemMana.getLocalizedName();
        int color = 0x660000FF;
        drawSimpleManaHUD(color, mana, maxMana, name, res);

    }

    public static void drawSimpleManaHUD(int color, int mana, int maxMana, String name, ScaledResolution res)
    {

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Minecraft mc = Minecraft.getMinecraft();
        int x = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(name) / 2;
        int y = res.getScaledHeight() / 2 + 10;

        mc.fontRenderer.drawStringWithShadow(name, x, y, color);

        x = res.getScaledWidth() / 2 - 51;
        y += 10;

        renderManaBar(x, y, color, 0.5F, mana, maxMana);

        if (mana < 0)
        {
            String text = StatCollector.translateToLocal("botaniamisc.statusUnknown");
            x = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(text) / 2;
            y -= 1;
            mc.fontRenderer.drawStringWithShadow(text, x, y, color);
        }

        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void renderManaBar(int x, int y, int color, float alpha, int mana, int maxMana)
    {
        Minecraft mc = Minecraft.getMinecraft();

        GL11.glColor4f(1F, 1F, 1F, alpha);

        ResourceLocation manaBar = Textures.MANA_BAR;

        mc.renderEngine.bindTexture(manaBar);
        drawTexturedModalRect(x, y, 0, 0, 0, 102, 5);

        int manaPercentage = Math.max(0, (int) ((double) mana / (double) maxMana * 100));
        drawTexturedModalRect(x + 1 + manaPercentage, y + 1, 0, 0, 5, 100 - manaPercentage, 3);

        Color color_ = new Color(color);
        GL11.glColor4ub((byte) color_.getRed(), (byte) color_.getGreen(), (byte) color_.getBlue(), (byte) (255F * alpha));
        drawTexturedModalRect(x + 1, y + 1, 0, 0, 5, manaPercentage, 3);
    }

    public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(par1 + 0, par2 + par6, z, (par3 + 0) * f, (par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + par6, z, (par3 + par5) * f, (par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + 0, z, (par3 + par5) * f, (par4 + 0) * f1);
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, z, (par3 + 0) * f, (par4 + 0) * f1);
        tessellator.draw();
    }

*/

}
