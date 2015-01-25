package latmod.emcc.client.gui;
import latmod.core.FastList;
import latmod.core.client.LMGuiButtons;
import latmod.core.gui.*;
import latmod.core.mod.LC;
import latmod.emcc.*;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCondenserSettings extends GuiLM
{
	public TileCondenser condenser;
	public ButtonLM buttonBack, buttonSecurity, buttonRedstone, buttonInvMode, buttonRepairItems;
	
	public GuiCondenserSettings(final EntityPlayer ep, IInventory inv)
	{
		super(new ContainerEmpty(ep, inv), EMCC.mod.getLocation("textures/gui/condenserSettings.png"));
		condenser = (TileCondenser)inv;
		xSize = 102;
		ySize = 106;
		
		widgets.add(buttonBack = new ButtonLM(this, 78, 6, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientOpenGui(0);
				playClickSound();
			}
		});
		
		buttonBack.title = LC.mod.translate("button.back");
		
		widgets.add(buttonSecurity = new ButtonLM(this, 78, 25, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LMGuiButtons.SECURITY, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonRedstone = new ButtonLM(this, 78, 44, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LMGuiButtons.REDSTONE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonInvMode = new ButtonLM(this, 78, 63, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LMGuiButtons.INV_MODE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonRepairItems = new ButtonLM(this, 78, 82, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(EMCCGuis.Buttons.REPAIR_TOOLS, b);
				playClickSound();
			}
		});
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		buttonRedstone.render(Icons.redstone[condenser.redstoneMode.ID]);
		buttonSecurity.render(Icons.security[condenser.security.level.ID]);
		buttonInvMode.render(Icons.inv[condenser.invMode.ID]);
		
		if(condenser.repairTools.isOn())
			buttonRepairItems.render(Icons.toggle_on);
		
		buttonBack.render(Icons.back);
	}
	
	public void addMouseText(int mx, int my, FastList<String> l)
	{
		super.addMouseText(mx, my, l);
		
		if(buttonRedstone.mouseOver(mx, my))
			l.add(condenser.redstoneMode.getText());
		
		if(buttonSecurity.mouseOver(mx, my))
			l.add(condenser.security.level.getText());
		
		if(buttonInvMode.mouseOver(mx, my))
			l.add(condenser.invMode.getText());
		
		if(buttonRepairItems.mouseOver(mx, my))
			l.add(condenser.repairTools.getText());
	}
}