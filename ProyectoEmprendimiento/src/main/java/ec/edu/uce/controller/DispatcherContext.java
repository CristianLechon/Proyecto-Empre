package ec.edu.uce.controller;

import ec.edu.uce.model.Dispatcher;

public class DispatcherContext {
    private static Dispatcher currentDispatcher;

    public static Dispatcher getCurrentDispatcher() {
        return currentDispatcher;
    }

    public static void setCurrentDispatcher(Dispatcher dispatcher) {
        currentDispatcher = dispatcher;
    }
}

