package binnie.genetics.machine.acclimatiser;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PackageAcclimatiser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageAcclimatiser() {
		super("acclimatiser", GeneticsTexture.Acclimatiser, 9857609, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.Acclimatiser);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(4, "process");
		inventory.getSlot(4).setValidator(new SlotValidator.Individual());
		inventory.getSlot(4).setReadOnly();
		inventory.getSlot(4).forbidExtraction();
		for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_RESERVE, "input")) {
			slot.forbidExtraction();
			slot.setValidator(new SlotValidator.Individual());
		}
		for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_DRONE, "output")) {
			slot.setReadOnly();
			slot.setValidator(new SlotValidator.Individual());
		}
		for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_ACCLIMATISER, "acclimatiser")) {
			slot.setValidator(new ValidatorAcclimatiserItem());
		}
		final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Acclimatiser.SLOT_RESERVE, 4, 1);
		transfer.addStorage(4, Acclimatiser.SLOT_DRONE, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(final ItemStack stack) {
				return !Acclimatiser.canAcclimatise(stack, machine.getMachineUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER));
			}
		});
		new ComponentPowerReceptor(machine, 5000);
		new AcclimatiserLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}