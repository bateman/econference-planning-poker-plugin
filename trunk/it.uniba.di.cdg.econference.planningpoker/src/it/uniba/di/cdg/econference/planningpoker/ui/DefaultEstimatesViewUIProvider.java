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


import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.utils.AutoResizeTableLayout;
import it.uniba.di.cdg.xcore.m2m.model.IParticipant;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

public class DefaultEstimatesViewUIProvider implements IEstimatesViewUIProvider {

	public DefaultEstimatesViewUIProvider() {

	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String label = "";
		//String story = (String)element;

		if(element instanceof IParticipant){
			IParticipant participant = (IParticipant) element;
			return  participant.getNickName();
		}else{
			IPokerCard card = (IPokerCard) element;
			if(card!=null)
				label = card.getStringValue();
			else //votes are hidden
				label = "Hidden";
			return label;
		}
		
//		IPokerCard card = (IPokerCard) story[1];
//		switch(columnIndex){
//		case 0: // Name 
//			//label = participant.getNickName();
//			label = story;
//		break;
//		case 1: {
//			if(card!=null)
//				label = card.getStringValue();
//			else //votes are hidden
//				label = "Hidden";
//			label = story;
//		}
//		break;
//		}
//		return label;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void createColumns(TableViewer viewer) {
		String[] titles = new String[] {"Participant","Estimate",};
		int[] bounds = new int[] {200,80};
		AutoResizeTableLayout layout = (AutoResizeTableLayout) viewer.getTable().getLayout();
		for (int i = 0; i < titles.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(titles[i]);
			//column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			layout.addColumnData(new ColumnWeightData(bounds[i]));
			column.setLabelProvider(new CellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {	
					Object[] element =  (Object[]) cell.getElement();
					if(element!=null){
						int index = cell.getColumnIndex();
						switch(index){
						case 0:
							if(element[0] instanceof IParticipant){
								IParticipant participant = (IParticipant) element[0];
								cell.setText(participant.getNickName());
							}
							break;
						case 1:
							if(element[1] instanceof IPokerCard){
								IPokerCard card = (IPokerCard) element[1];
								if(card!=null)
									cell.setText(card.getStringValue());
								else //votes are hidden
									cell.setText("Hidden");
							}
							break;
						}
					}
				}
			});
		}
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
	}
	

}
