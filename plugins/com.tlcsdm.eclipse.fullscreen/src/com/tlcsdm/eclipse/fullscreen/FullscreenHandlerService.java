package com.tlcsdm.eclipse.fullscreen;

import org.eclipse.core.commands.Command;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.handlers.FullScreenHandler;

public class FullscreenHandlerService {

	@SuppressWarnings("restriction")
	public static void enableFullscreenHandler() {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		if (commandService == null) {
			return;
		}
		Command command = commandService.getCommand("org.eclipse.ui.window.fullscreenmode");
		command.setHandler(new FullScreenHandler());
	}

	public static void disableFullscreenHandler() {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		if (commandService == null) {
			return;
		}
		Command command = commandService.getCommand("org.eclipse.ui.window.fullscreenmode");
		command.setHandler(null);
	}

}
