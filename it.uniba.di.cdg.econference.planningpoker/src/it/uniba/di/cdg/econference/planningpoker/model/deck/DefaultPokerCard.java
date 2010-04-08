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
package it.uniba.di.cdg.econference.planningpoker.model.deck;


public class DefaultPokerCard implements IPokerCard {
	
	public static final String UNKNOWN ="?";
	public static final String ZERO ="0";
	public static final String ONE ="1";
	public static final String TWO ="2";
	public static final String THREE ="3";
	public static final String FIVE ="5";
	public static final String EIGHT ="8";
	public static final String TWENTY ="20";
	public static final String FOURTY ="40";
	public static final String HUNDRED ="100";
	
	public static final String IMAGE_ONE = "icons/deck/1.ico";
	public static final String IMAGE_TWO = "icons/deck/2.ico";
	public static final String IMAGE_THREE = "icons/deck/3.ico";
	public static final String IMAGE_FIVE = "icons/deck/5.ico";
	public static final String IMAGE_EIGHT = "icons/deck/8.ico";	
	public static final String IMAGE_UNKNOWN = "icons/deck/0.ico";
	
	private String value;
	private String imagePath;
	
	public DefaultPokerCard(String value){
		this.value = value;		
	}
	
	public DefaultPokerCard(String value, String imagePath){
		this.value = value;		
		this.imagePath = imagePath;
	}
	
	@Override
	public String getStringValue() {
		return value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (String) value;
	}

	@Override
	public String getImagePath() {		
		return imagePath;
	}

	@Override
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
		
	}

	@Override
	public boolean hasImagePath() {
		return imagePath!=null;
	}

}
