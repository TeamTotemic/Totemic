package totemic_commons.pokefenn.block;


import rukalib_commons.pokefenn.block.BlockTile;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.GuiIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemBase;
import totemic_commons.pokefenn.tileentity.TileTotemic;

public class BlockTotemBase extends BlockTile {

    public BlockTotemBase(int id)
    {

        super(id, Material.wood);
        this.setUnlocalizedName(Strings.TOTEM_BASE_NAME);
        this.setHardness(1F);
        this.setCreativeTab(Totemic.tabsTotem);


    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {

        return new TileTotemBase();
    }

	/*
    @Override
	public boolean renderAsNormalBlock(){
		
		return false;
		
	}
	
	public boolean isOpaqueCube(){
	    
        return false;
        
    }
	
	@Override
    public int getRenderType() {
            return -1;
    }
	
	*/

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        if (player.isSneaking())
            return false;
        else
        {
            if (!world.isRemote)
            {
                TileTotemBase tileTotemBase = (TileTotemBase) world.getBlockTileEntity(x, y, z);

                if (tileTotemBase != null)
                {


                    player.openGui(Totemic.instance, GuiIds.TOTEM_BASE, world, x, y, z);
                }
            }

            return true;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {

        int direction = 0;
        int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        switch (facing)
        {

            case 0:
                direction = ForgeDirection.NORTH.ordinal();
                break;

            case 1:
                direction = ForgeDirection.EAST.ordinal();
                break;

            case 2:
                direction = ForgeDirection.SOUTH.ordinal();
                break;

            case 3:
                direction = ForgeDirection.WEST.ordinal();
                break;

        }

        world.setBlockMetadataWithNotify(x, y, z, direction, 3);

        if (itemStack.hasDisplayName())
        {
            ((TileTotemic) world.getBlockTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
        }

        ((TileTotemic) world.getBlockTileEntity(x, y, z)).setOrientation(direction);
    }


}
