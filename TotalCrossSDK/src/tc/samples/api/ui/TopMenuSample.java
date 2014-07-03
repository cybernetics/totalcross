/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2014 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *  This file is covered by the GNU LESSER GENERAL PUBLIC LICENSE VERSION 3.0    *
 *  A copy of this license is located in file license.txt at the root of this    *
 *  SDK or can be downloaded here:                                               *
 *  http://www.gnu.org/licenses/lgpl-3.0.txt                                     *
 *                                                                               *
 *********************************************************************************/

package tc.samples.api.ui;

import tc.samples.api.*;

import totalcross.res.*;
import totalcross.ui.*;
import totalcross.ui.dialog.*;
import totalcross.ui.event.*;

public class TopMenuSample extends BaseContainer
{
   ScrollContainer sc;
   
   public void initUI()
   {
      try
      {
         super.initUI();
         Control []items = 
         {
            new TopMenu.Item("Videocalls",            Resources.warning),
            new TopMenu.Item("Insert emoticon",       Resources.exit),
            new ComboBox(new String[]{"Smile","Sad","Laugh"}),
            new TopMenu.Item("Add text",              Resources.back),
            new TopMenu.Item("See contact",           Resources.menu),
            new TopMenu.Item("Add slide",             Resources.warning),
            new TopMenu.Item("Add subject",           Resources.exit),
            new TopMenu.Item("Add persons",           Resources.back),
            new TopMenu.Item("Programmed messages",   Resources.menu),
            new TopMenu.Item("Add to the phone book", Resources.warning),
         };
         show(new TopMenu(items,CENTER),"CENTER");
         show(new TopMenu(items,BOTTOM),"BOTTOM");
         show(new TopMenu(items,TOP),"TOP");
         show(new TopMenu(items,LEFT),"LEFT");
         show(new TopMenu(items,RIGHT),"RIGHT");
         back();
      }
      catch (Exception e)
      {
         MessageBox.showException(e,true);
         back();
      }
   }

   private void show(final TopMenu t, String dir)
   {
      setInfo("Showing at "+dir+". Click outside to close");
      t.addPressListener(new PressListener()
      {
         public void controlPressed(ControlEvent e)
         {
            setInfo("Selected index: "+t.getSelectedIndex());
         }
      });
      t.popup();
   }
}