/*******************************************************************************
 * Copyright (c) 2012 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.painter.cell.decorator;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.LayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.CellPainterWrapper;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.CellStyleUtil;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;


public class PaddingDecorator extends CellPainterWrapper {
	
	private final int topPadding;
	private final int rightPadding;
	private final int bottomPadding;
	private final int leftPadding;

	public PaddingDecorator(ICellPainter interiorPainter) {
		this(interiorPainter, 2);
	}
	
	public PaddingDecorator(ICellPainter interiorPainter, int padding) {
		this(interiorPainter, padding, padding, padding, padding);
	}
	
	public PaddingDecorator(ICellPainter interiorPainter, int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
		super(interiorPainter);
		this.topPadding = topPadding;
		this.rightPadding = rightPadding;
		this.bottomPadding = bottomPadding;
		this.leftPadding = leftPadding;
	}

	public int getPreferredWidth(LayerCell cell, GC gc, IConfigRegistry configRegistry) {
		return leftPadding + super.getPreferredWidth(cell, gc, configRegistry) + rightPadding;
	}
	
	public int getPreferredHeight(LayerCell cell, GC gc, IConfigRegistry configRegistry) {
		return topPadding + super.getPreferredHeight(cell, gc, configRegistry) + bottomPadding;
	}

	public void paintCell(LayerCell cell, GC gc, Rectangle adjustedCellBounds, IConfigRegistry configRegistry) {
		Rectangle interiorBounds = getInteriorBounds(adjustedCellBounds);
		
		Color originalBg = gc.getBackground();
		Color cellStyleBackground = getBackgroundColor(cell, configRegistry);
        gc.setBackground(cellStyleBackground != null ? cellStyleBackground : originalBg);
		gc.fillRectangle(adjustedCellBounds);
		gc.setBackground(originalBg);
		
		if (interiorBounds.width > 0 && interiorBounds.height > 0) {
			super.paintCell(cell, gc, interiorBounds, configRegistry);
		}
	}
	
	public Rectangle getInteriorBounds(Rectangle adjustedCellBounds) {
		return new Rectangle(
				adjustedCellBounds.x + leftPadding,
				adjustedCellBounds.y + topPadding,
				adjustedCellBounds.width - leftPadding - rightPadding,
				adjustedCellBounds.height - topPadding - bottomPadding
		);
	}
	
	protected Color getBackgroundColor(LayerCell cell, IConfigRegistry configRegistry) {
		return CellStyleUtil.getCellStyle(cell, configRegistry).getAttributeValue(CellStyleAttributes.BACKGROUND_COLOR);		
	}
	
}
