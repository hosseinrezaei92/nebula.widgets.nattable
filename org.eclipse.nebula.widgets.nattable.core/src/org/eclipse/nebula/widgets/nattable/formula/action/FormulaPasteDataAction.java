/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.nebula.widgets.nattable.formula.action;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.formula.command.FormulaPasteDataCommand;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;

/**
 * {@link IKeyAction} that triggers the {@link FormulaPasteDataCommand}.
 *
 * @since 1.4
 */
public class FormulaPasteDataAction implements IKeyAction {

    @Override
    public void run(NatTable natTable, KeyEvent event) {
        natTable.doCommand(new FormulaPasteDataCommand(natTable.getConfigRegistry()));
    }

}
