package totemic_commons.pokefenn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityCupidsArrow extends EntityArrow
{
    public EntityCupidsArrow(World par1World)
    {
        super(par1World);
        setDamage(0);
    }

    public EntityCupidsArrow(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        setDamage(0);
    }

    public EntityCupidsArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3)
    {
        super(par1World, par2EntityLivingBase, par3);
        setDamage(0);
    }

    public EntityCupidsArrow(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
    {
        super(par1World, par2EntityLivingBase, par3EntityLivingBase, par4, par5);
        setDamage(0);
    }

}
