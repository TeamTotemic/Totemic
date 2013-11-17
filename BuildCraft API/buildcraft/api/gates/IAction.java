package buildcraft.api.gates;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IAction {

	/**
	 * Return your ID from the old API here, this is only used to convert old
	 * saves to the new format.
	 */
	int getLegacyId();

	String getUniqueTag();

	@SideOnly(Side.CLIENT)
	Icon getIcon();

	@SideOnly(Side.CLIENT)
	void registerIcons(IconRegister iconRegister);

	boolean hasParameter();

	String getDescription();
}
