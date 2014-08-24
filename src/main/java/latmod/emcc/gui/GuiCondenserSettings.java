package latmod.emcc.gui;
import latmod.core.LatCoreMC;
import latmod.core.mod.*;
import latmod.core.mod.gui.*;
import latmod.core.util.FastList;
import latmod.emcc.*;
import latmod.emcc.tile.TileCondenser;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCondenserSettings extends GuiLM
{
	public TileCondenser condenser;
	public ButtonLM buttonSettings, buttonSecurity, buttonRedstone, buttonInvMode, buttonRepairItems;
	
	public GuiCondenserSettings(ContainerLM c)
	{
		super(c, LatCoreMC.getLocation(EMCC.MOD_ID, "textures/gui/condenserSettings.png"));
		condenser = (TileCondenser)c.inv;
		xSize = 102;
		ySize = 106;
		textureWidth = 128;
		textureHeight = 128;
		
		widgets.add(buttonSettings = new ButtonLM(this, 78, 6, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientOpenGui(EMCCGuis.CONDENSER);
				playClickSound();
			}
		});
		
		widgets.add(buttonSecurity = new ButtonLM(this, 78, 25, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LCGuis.Buttons.SECURITY, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonRedstone = new ButtonLM(this, 78, 44, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LCGuis.Buttons.REDSTONE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonInvMode = new ButtonLM(this, 78, 63, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LCGuis.Buttons.INV_MODE, b);
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
		textureWidth = textureHeight = 128;
		
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		textureWidth = textureHeight = 256;
		
		buttonRedstone.render(button_redstone[condenser.redstoneMode.ID]);
		buttonSecurity.render(button_security[condenser.security.level.ID]);
		buttonInvMode.render(button_inv[condenser.invMode.ID]);
		
		if(condenser.repairTools.isOn())
			buttonRepairItems.render(button_inner_pressed);
		
		buttonSettings.render(button_back);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		FastList<String> al = new FastList<String>();
		
		if(buttonSettings.mouseOver(mx, my))
			al.add(LC.mod.translate("back"));
		
		if(buttonRedstone.mouseOver(mx, my))
			al.add(condenser.redstoneMode.getText());
		
		if(buttonSecurity.mouseOver(mx, my))
			al.add(condenser.security.level.getText());
		
		if(buttonInvMode.mouseOver(mx, my))
			al.add(condenser.invMode.getText());
		
		if(buttonRepairItems.mouseOver(mx, my))
			al.add(condenser.repairTools.getText());
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
}