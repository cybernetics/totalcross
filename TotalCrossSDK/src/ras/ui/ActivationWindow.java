/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2012 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *********************************************************************************/

package ras.ui;

import ras.*;

import totalcross.io.*;
import totalcross.sys.*;
import totalcross.ui.*;
import totalcross.ui.dialog.*;
import totalcross.ui.font.*;
import totalcross.ui.gfx.*;
import totalcross.xml.soap.*;

public class ActivationWindow extends MainWindow
{
   static
   {
      Settings.useNewFont = true;
   }
   
   private ActivationClient client;
   private ActivationHtml html;

   public ActivationWindow() // used just to test on JavaSE
   {
      this(null);
   }

   public ActivationWindow(ActivationClient client)
   {
      super("", NO_BORDER);
      setUIStyle(Settings.Android);
      setBackColor(Color.WHITE);

      this.client = client;
   }

   public void initUI()
   {
      int c1 = 0x0A246A;
      Font f = font.adjustedBy(2,true);
      Bar headerBar = new Bar("Activation");
      headerBar.spinner = new Spinner();
      headerBar.spinner.setForeColor(Color.WHITE);
      headerBar.setFont(f);
      headerBar.setBackForeColors(c1,Color.WHITE);
      add(headerBar, LEFT,0,FILL,PREFERRED);

      html = ActivationHtml.getInstance();
      if (html != null)
      {
         if (Settings.keyboardFocusTraversable)
            Settings.geographicalFocus = true;
         int yy = headerBar.getY2();
         html.setRect(0,yy,SCREENSIZE,SCREENSIZE+80);
         html.popup();
         html = null;
      }

      Label l = new Label("The TotalCross virtual machine needs to be activated. This process requires your device's internet connection to be properly set up.");
      l.autoSplit = true;
      l.align = FILL;
      add(l,LEFT+5,AFTER+2,FILL-5,PREFERRED);

      headerBar.startSpinner();
      
      repaintNow();

      try
      {
         if (client == null)
            Vm.sleep(3000);
         else
            client.activate();
         headerBar.stopSpinner();
         MessageBox mb = new MessageBox("Success", "TotalCross is now activated!\nPlease restart your application.");
         mb.setBackColor(0x008800);
         mb.titleColor = Color.WHITE;
         mb.yPosition = BOTTOM;
         mb.popup();
         exit(0);
      }
      catch (ActivationException ex)
      {
         headerBar.stopSpinner();
         Throwable cause = ex.getCause();
         String s = ex.getMessage() + " The activation process cannot continue. The application will be terminated.";

         if (cause instanceof SOAPException || cause instanceof IOException)
            s += " Try again 2 or 3 times if there's really an internet connection.";

         s = s.replace('\n', ' '); // guich@tc115_13
         MessageBox mb = new MessageBox("Failure", s, new String[] { "  Exit  " });
         mb.setTextAlignment(LEFT);
         mb.titleColor = Color.WHITE;
         mb.yPosition = BOTTOM;
         mb.popup();
         exit(1);
      }
   }

   public void screenResized()
   {
      if (html != null && Settings.isLandscape())
      {
         // make sure that the MessageBox takes the whole screen
         MessageBox mb = new MessageBox("Attention","This program must be run in portrait mode.\nPlease rotate back the device.",null)
         {
            public void setRect(int x, int y, int w, int h)
            {
               super.setRect(x,y,Settings.screenWidth,Settings.screenHeight);
            }
         };
         mb.transitionEffect = TRANSITION_NONE;
         mb.popupNonBlocking();
         while (Settings.isLandscape())
            pumpEvents();
         mb.unpop();
      }
      else super.screenResized();
   }
}
