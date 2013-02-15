/**
   Copyright 2012 John Cummens (aka Shadowmage, Shadowmage4513)
   This software is distributed under the terms of the GNU General Public Licence.
   Please see COPYING for precise license information.

   This file is part of Ancient Warfare.

   Ancient Warfare is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Ancient Warfare is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Ancient Warfare.  If not, see <http://www.gnu.org/licenses/>.
 */
package shadowmage.ancient_warfare.client.gui.structure;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import shadowmage.ancient_warfare.client.gui.GuiContainerAdvanced;
import shadowmage.ancient_warfare.common.container.ContainerEditor;

public class GuiEditor extends GuiContainerAdvanced
{

private ContainerEditor cont;

private GuiTextBox editor;

private String errorMsg;
private int errorDisplayCount = 0;

/**
 * might be null, if force-opened from scanner....
 */
private GuiEditorSelect parentScreen;

/**
 * @param container
 */
public GuiEditor(ContainerEditor cont, GuiEditorSelect parent)
  {
  super(cont);
  this.cont = cont; 
  this.parentScreen = parent;
  }

@Override
public int getXSize()
  {
  return 256;
  }

@Override
public int getYSize()
  {
  return 240;
  }

@Override
public String getGuiBackGroundTexture()
  {
  return "/shadowmage/ancient_warfare/resources/gui/guiBackgroundLarge.png";
  }

@Override
public void renderExtraBackGround(int mouseX, int mouseY, float partialTime)
  { 
  if(this.editor!=null)
    {
    this.editor.drawTextBox(fontRenderer, guiLeft+4, guiTop+4);
    }
  this.fontRenderer.drawString(errorMsg, guiLeft+10, guiTop+240-4-18-1-7, 0xffff0000, false);
  //this.drawString(fontRenderer, errorMsg, guiLeft+10, guiTop+240-4-18-1-10, 0xffff0000);
  }

@Override
public void setupGui()
  {
  this.controlList.clear();
  this.addGuiButton(0, 128-50-2, 240-18-4, 50, 18, "Discard");
  this.addGuiButton(1, 128 + 2, 240-18-4, 50, 18, "Save");
  if(editor!=null)    
    {
    editor.updateDrawPos(guiLeft+4, guiTop+4);
    }  
  this.errorMsg = "";
  }

@Override
public void updateScreenContents()
  {
  if(this.editor==null && this.cont.clientLines!=null)
    {
    this.editor = new GuiTextBox(248, 240-18-4-2-10, 20, 32, 0xffffffff, 0x00000000, cont.clientLines);
    this.editor.activated = true;
    this.editor.updateDrawPos(guiLeft+4, guiTop+4);
    }
  if(this.errorDisplayCount>0)
    {
    this.errorDisplayCount--;
    }
  if(this.errorDisplayCount<=0)
    {
    this.errorMsg = "";
    }
  }

@Override
public void handleDataFromContainer(NBTTagCompound tag)
  {
  if(tag.hasKey("badSave"))
    {
    this.errorMsg = "Invalid template, could not be saved to server.";
    this.errorDisplayCount = 200;//ten seconds...
    }
  if(tag.hasKey("goodSave"))
    {
    if(this.editor!=null)
      {
      this.editor.setFileClean();
      }
    }
  }

@Override
public void buttonClicked(GuiButton button)
  {
  switch(button.id)
  {
  case 0:
  this.closeGUI();
  break;
  
  case 1:
  if(this.editor.isFileDirty())
    {
    this.cont.saveTemplate();
    }
  //this.closeGUI();
  break;
  
  default:
  break;
  }
  // TODO Auto-generated method stub  
  }


@Override
protected void keyTyped(char par1, int par2)
  {
  if (par2 == 1)
    {
    super.keyTyped(par1, par2);
    }
  if(this.editor!=null)
    {
    this.editor.onKeyTyped(par1, par2);
    }
  
  }

@Override
protected void mouseClicked(int mouseX, int mouseY, int buttonNum)
  {
  if(editor!=null && editor.isMouseOver(mouseX, mouseY))
    {
    editor.onMouseClicked(buttonNum, mouseX, mouseY);
    }
  else
    {
    super.mouseClicked(mouseX, mouseY, buttonNum);
    }
  }



}
