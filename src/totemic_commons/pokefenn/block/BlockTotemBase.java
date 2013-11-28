package totemic_commons.pokefenn.block;


import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.GuiIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemBase;

public class BlockTotemBase extends BlockTotemic {
	
	public BlockTotemBase(int id){
		
		super(id, Material.wood);
		this.setUnlocalizedName(Strings.TOTEM_BASE_NAME);
		this.setHardness(1F);
		this.setCreativeTab(Totemic.tabsTotem);
		
		
		
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		
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

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){

        if (player.isSneaking())
            return false;
        else {
            if (!world.isRemote) {
                TileTotemBase tileTotemBase = (TileTotemBase) world.getBlockTileEntity(x, y, z);

                if (tileTotemBase != null) {
                    System.out.println("trying to open gui");
                    player.openGui(Totemic.instance, GuiIds.TOTEM_BASE, world, x, y, z);
                }
            }

            return true;
        }
    }



}
