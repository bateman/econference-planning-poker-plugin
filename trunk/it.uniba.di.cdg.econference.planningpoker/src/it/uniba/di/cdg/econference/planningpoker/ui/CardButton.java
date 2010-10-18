/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.uniba.di.cdg.econference.planningpoker.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;

public class CardButton extends Canvas {
	public static String CARD_BACKGROUND_IMAGE = "/icons/deck/card.png";
	
    private int mouse = 0;
    private Boolean selected = false;
    private String value = "?";
    private Label label;
    
    public CardButton(Composite parent, final String value) {
        super(parent, SWT.NONE);
        this.value = value;

        this.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
            	if (!selected) {
            		// Dotted rectangle containing the card
            		e.gc.setLineWidth(2);
	                e.gc.setLineStyle(SWT.LINE_DASH);
	                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_GRAY));
	                e.gc.drawRoundRectangle(3, 3, e.width-6, e.height-6, 10, 10);
	                
	                // Card border
            		e.gc.setLineWidth(6);
            		e.gc.setLineStyle(SWT.LINE_SOLID);
                    e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
                    e.gc.drawRoundRectangle(12, 12, e.width-24, e.height-24, 5, 5);
	                
	                // Card background pattern
            		Image image = ImageDescriptor.createFromFile(getClass(), CARD_BACKGROUND_IMAGE).createImage();
                	Pattern pattern = new Pattern(e.display, image);
            		e.gc.setBackgroundPattern(pattern);
            		e.gc.fillRectangle(15, 15, e.width-30, e.height-30);
            		
            		// Card value
            		Font font = new Font(e.display, "Arial", 12, SWT.BOLD);
            		e.gc.setFont(font);
            		
            		Point textSize = e.gc.textExtent(value);
            		e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK)); 
    	            e.gc.drawText(value, (e.width-textSize.x)/2, (e.height-textSize.y)/2, true);
            		
    	            /*
    	             * Switch
    	             * 
	                 * 0: Default state
	                 * 1: Mouse over
	                 * 2: Mouse down
	                 * */
	                switch (mouse) {
		                case 0: case 1: case 2:
		            		break;
	                }
	                
	                image.dispose();
		            pattern.dispose();
		            font.dispose();
        		}
        		else {
        			// Dotted rectangle containing the card
            		e.gc.setLineWidth(2);
	                e.gc.setLineStyle(SWT.LINE_DASH);
	                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_GRAY));
	                e.gc.drawRoundRectangle(3, 3, e.width-6, e.height-6, 10, 10);
        		}
            }
        });
        
        this.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				mouse = 0;
                redraw();
			}
			
			@Override
			public void mouseEnter(MouseEvent e) {
				mouse = 1;
                redraw();
			}
		});
        
        this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
                mouse = 1;
                if (e.x < 0 || e.y < 0 || e.x > getBounds().width
                        || e.y > getBounds().height) {
                    mouse = 0;
                }
                redraw();
                if (mouse == 1)
                    notifyListeners(SWT.Selection, new Event());
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
                mouse = 2;
                redraw();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		redraw();
	}
	
	public Label getLabel()
	{
		return label;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
		redraw();
	}

}