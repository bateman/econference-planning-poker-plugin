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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class CardTarget extends Canvas {
	private String estimate = "";
    
    public CardTarget(Composite parent) {
        super(parent, SWT.NONE);

        this.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {	
            	if (isEnabled()) {
		            if (estimate.isEmpty()) {
		            	Font font = new Font(e.display, "Arial", 10, SWT.NONE);
		            	e.gc.setFont(font);
		            	e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_DARK_GRAY));
		            	
		                String text = "Drag";
			            Point textSize = e.gc.textExtent(text);
			            e.gc.drawText(text, (e.width-textSize.x)/2, (e.height-textSize.y)/2-20, true);
			            
			            text = "your card";
			            textSize = e.gc.textExtent(text);
			            e.gc.drawText(text, (e.width-textSize.x)/2, (e.height-textSize.y)/2, true);
			            
			            text = "here";
			            textSize = e.gc.textExtent(text);
			            e.gc.drawText(text, (e.width-textSize.x)/2, (e.height-textSize.y)/2+20, true);
		            
			            // Dotted rectangle containing the card
	            		e.gc.setLineWidth(2);
		                e.gc.setLineStyle(SWT.LINE_DASH);
		                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
		                e.gc.drawRoundRectangle(3, 3, e.width-6, e.height-6, 10, 10);
		                
			            font.dispose();
		            }
		            else
		            {
		            	// Dotted rectangle containing the card
	            		e.gc.setLineWidth(2);
		                e.gc.setLineStyle(SWT.LINE_DASH);
		                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
		                e.gc.drawRoundRectangle(3, 3, e.width-6, e.height-6, 10, 10);
		                
		                // Card background pattern
	            		Image image = ImageDescriptor.createFromFile(getClass(), CardButton.CARD_BACKGROUND_IMAGE).createImage();
	                	Pattern pattern = new Pattern(e.display, image);
	            		e.gc.setBackgroundPattern(pattern);
	            		e.gc.fillRectangle(8, 8, e.width-16, e.height-16);
	            		
	            		// Card border
	            		e.gc.setLineWidth(2);
	            		e.gc.setLineStyle(SWT.LINE_SOLID);
	                    e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
	                    e.gc.drawRoundRectangle(7, 7, e.width-14, e.height-14, 5, 5);
	            		
	            		// Card value
	            		Font font = new Font(e.display, "Arial", 12, SWT.BOLD);
	            		e.gc.setFont(font);
	            		
	            		Point textSize = e.gc.textExtent(estimate);
	            		e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK)); 
	    	            e.gc.drawText(estimate, (e.width-textSize.x)/2, (e.height-textSize.y)/2, true);
	    	            
	    	            font.dispose();
	    	            image.dispose();
	    	            pattern.dispose();
		            }
            	}
            	else {
            		Font font = new Font(e.display, "Arial", 10, SWT.NONE);
	            	e.gc.setFont(font);
	            	e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_DARK_GRAY));
	            	
	                String text = "You";
		            Point textSize = e.gc.textExtent(text);
		            e.gc.drawText(text, (e.width-textSize.x)/2, (e.height-textSize.y)/2-20, true);
		            
		            text = "can't";
		            textSize = e.gc.textExtent(text);
		            e.gc.drawText(text, (e.width-textSize.x)/2, (e.height-textSize.y)/2, true);
		            
		            text = "vote";
		            textSize = e.gc.textExtent(text);
		            e.gc.drawText(text, (e.width-textSize.x)/2, (e.height-textSize.y)/2+20, true);
	            
		            // Dotted rectangle containing the card
            		e.gc.setLineWidth(2);
	                e.gc.setLineStyle(SWT.LINE_DASH);
	                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
	                e.gc.drawRoundRectangle(3, 3, e.width-6, e.height-6, 10, 10);
	                
		            font.dispose();
            	}

            }
        });
    }
    
    public void setEstimate(String estimate)
    {
    	this.estimate = estimate;
    	redraw();
    }
    
    public void setEnabled (boolean enabled) {
    	super.setEnabled(enabled);
    	redraw();
    }

}