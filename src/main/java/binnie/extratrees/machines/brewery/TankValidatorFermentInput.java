package binnie.extratrees.machines.brewery;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.TankValidator;
import binnie.core.util.I18N;

public class TankValidatorFermentInput extends TankValidator {
	@Override
	public boolean isValid(final FluidStack itemStack) {
		return BreweryRecipes.isValidInputLiquid(itemStack);
	}

	@Override
	public String getTooltip() {
		return I18N.localise("extratrees.machine.machine.brewery.tooltips.tank.fermentable");
	}
}
