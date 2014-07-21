package latmod.emcc.api;
import net.minecraft.item.*;

public interface IEmcStorageItem
{
	public boolean canChargeEmc(ItemStack is);
	public boolean canDischargeEmc(ItemStack is);
	public double getStoredEmc(ItemStack is);
	public void setStoredEmc(ItemStack is, double emc);
	public double getMaxEmcChargeFromBattery(ItemStack is);
	public double getEmcTrasferLimit(ItemStack is);
}