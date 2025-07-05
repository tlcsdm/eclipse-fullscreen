/*******************************************************************************
 * Copyright (c) 2010 Ugo Sangiorgi and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Ugo Sangiorgi <ugo.sangiorgi@gmail.com> - Initial contribution
 *  Daoen Pan <http://code.google.com/u/gr8vyguy/> - Original code
 *******************************************************************************/
package com.tlcsdm.eclipse.fullscreen;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class FullScreenPreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		ScopedPreferenceStore preferences = new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.ID);
		preferences.setDefault(Activator.HIDE_MENU_BAR, true);
		preferences.setDefault(Activator.HIDE_STATUS_BAR, true);
		preferences.setDefault(Activator.FULLSCREEN_STARTUP, false);
		preferences.setDefault(Activator.DISABLE_ECLIPSE_FULLSCREEN, true);
	}

}
