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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.Preferences;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements IStartup {
	public static final String ID = "com.tlcsdm.eclipse.fullscreen"; //$NON-NLS-1$
	public static final String HIDE_MENU_BAR = ID + ".hide_menu_bar"; //$NON-NLS-1$
	public static final String HIDE_STATUS_BAR = ID + ".hide_status_bar"; //$NON-NLS-1$
	public static final String FULLSCREEN_STARTUP = ID + ".fullscreen_startup"; //$NON-NLS-1$
	public static final String DISABLE_ECLIPSE_FULLSCREEN = ID + ".disable_eclipse_fullscreen"; //$NON-NLS-1$

	private static Activator INSTANCE;
	private Map<Shell, List<Control>> controlLists;
	private Map<Shell, Menu> menuBars;

	public static Activator getDefault() {
		return INSTANCE;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		controlLists = new HashMap<>();
		menuBars = new HashMap<>();
		INSTANCE = this;
	}

	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		controlLists.clear();
		controlLists = null;
		menuBars.clear();
		menuBars = null;
		// Restore on uninstall
		if (getDisableEclipseFullscreen()) {
			FullscreenHandlerService.enableFullscreenHandler();
		}
		super.stop(context);
	}

	/**
	 * Set the workbench window full screen state.
	 * 
	 * @param window     the workbench window
	 * @param fullScreen new full screen state
	 */
	public void setFullScreen(Shell mainShell, boolean fullScreen) {
		if (mainShell == null || mainShell.isDisposed())
			return;

		if (fullScreen) {
			List<Control> controls = hideTrimControls(mainShell);
			controlLists.put(mainShell, controls);
			if (getHideMenuBar()) {
				Menu menuBar = mainShell.getMenuBar();
				mainShell.setMenuBar(null);
				menuBars.put(mainShell, menuBar);
			}

		} else {
			showTrimControls(mainShell);
			controlLists.remove(mainShell);
			Menu menuBar = mainShell.getMenuBar();
			if (menuBar == null) {
				menuBar = (Menu) menuBars.get(mainShell);
				mainShell.setMenuBar(menuBar);
				menuBars.remove(mainShell);
			}
		}

		mainShell.setFullScreen(fullScreen);
		mainShell.layout();
	}

	private void showTrimControls(Shell mainShell) {
		List<Control> controls = controlLists.get(mainShell);
		if (controls != null) {
			for (int i = 0; i < controls.size(); i++) {
				Control control = (Control) controls.get(i);
				control.setVisible(true);
			}
		}
	}

	private List<Control> hideTrimControls(Shell mainShell) {
		List<Control> controls = new ArrayList<>();
		Control[] children = mainShell.getChildren();
		for (int i = 0; i < children.length; i++) {
			Control child = children[i];
			if (child.isDisposed() || !child.isVisible()) {
				continue;
			}
			if (child.getClass().equals(Canvas.class)) {
				continue;
			}

			if (child.getClass().equals(Composite.class)) {
				boolean hasStatusLine = false;
				Composite composite = (Composite) child;
				Control[] childChildren = composite.getChildren();
				for (Control ch : childChildren) {
					if (ch.getClass().toString().contains("StatusLine")) { //$NON-NLS-1$
						hasStatusLine = true;
					}
				}
				if (hasStatusLine) {
					child.setVisible(!getHideStatusBar());
					controls.add(child);
				}
				continue;
			}

			// org.eclipse.jface.action.StatusLine is an internal class
			// the only way to hide it is by getting its name in string form
			// TODO: find a more elegant way to do fetch the status line
			if (!getHideStatusBar() && child.getClass().toString().contains("StatusLine")) { //$NON-NLS-1$
				child.setVisible(true);
			} else {
				child.setVisible(false);
			}
			controls.add(child);
		}
		return controls;
	}

	private boolean getHideMenuBar() {
		return preferences().getBoolean(HIDE_MENU_BAR, true);
	}

	private boolean getHideStatusBar() {
		return preferences().getBoolean(HIDE_STATUS_BAR, true);
	}

	private boolean getFullscreenStartup() {
		return preferences().getBoolean(FULLSCREEN_STARTUP, false);
	}

	private boolean getDisableEclipseFullscreen() {
		return preferences().getBoolean(DISABLE_ECLIPSE_FULLSCREEN, true);
	}

	private Preferences preferences() {
		Preferences preferences = Platform.getPreferencesService().getRootNode().node(InstanceScope.SCOPE).node(ID);
		return preferences;
	}

	public void earlyStartup() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String changedProperty = event.getProperty();
				if (DISABLE_ECLIPSE_FULLSCREEN.equals(changedProperty)) {
					boolean disabled = store.getBoolean(DISABLE_ECLIPSE_FULLSCREEN);
					if (disabled) {
						FullscreenHandlerService.disableFullscreenHandler();
					} else {
						FullscreenHandlerService.enableFullscreenHandler();
					}
				}
			}
		});
		if (getDisableEclipseFullscreen()) {
			FullscreenHandlerService.disableFullscreenHandler();
		}

		if (!getFullscreenStartup()) {
			return;
		}

		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					Shell mainShell = window.getShell();
					getDefault().setFullScreen(mainShell, true);
				}
			}
		});
	}

}
