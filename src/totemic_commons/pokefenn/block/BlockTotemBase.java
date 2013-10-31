package totemic_commons.pokefenn.block;


import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
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
	
	
	

}
