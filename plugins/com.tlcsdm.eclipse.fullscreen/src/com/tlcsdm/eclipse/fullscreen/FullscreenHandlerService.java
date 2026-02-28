package com.tlcsdm.eclipse.fullscreen;

import org.eclipse.core.commands.Command;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.handlers.FullScreenHandler;

@SuppressWarnings("restriction")
public class FullscreenHandlerService {

	private static final String FULLSCREEN_COMMAND_ID = "org.eclipse.ui.window.fullscreenmode";

	public static void enableFullscreenHandler() {
		Command command = getFullscreenCommand();
		if (command != null) {
			command.setHandler(new FullScreenHandler());
		}
	}

	public static void disableFullscreenHandler() {
		Command command = getFullscreenCommand();
		if (command != null) {
			command.setHandler(null);
		}
	}

	private static Command getFullscreenCommand() {
		ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);
		if (commandService == null) {
			return null;
		}
		return commandService.getCommand(FULLSCREEN_COMMAND_ID);
	}

}
