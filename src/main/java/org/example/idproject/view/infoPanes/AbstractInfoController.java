package org.example.idproject.view.infoPanes;

import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DataProvider;

public abstract class AbstractInfoController {
    protected final DataProvider dataProvider;
    protected final ScreenManager screenManager;

    AbstractInfoController(DataProvider dataProvider, ScreenManager screenManager) {
        this.dataProvider = dataProvider;
        this.screenManager = screenManager;
    }

    public abstract void update(int id);
}
