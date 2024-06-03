package org.example.idproject.view.infoPanes;

import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.ViewModel;

public abstract class AbstractInfoController {
    protected final ViewModel viewModel;
    protected final ScreenManager screenManager;

    AbstractInfoController(ViewModel viewModel, ScreenManager screenManager) {
        this.viewModel = viewModel;
        this.screenManager = screenManager;
    }

    public abstract void update(int id);
}
