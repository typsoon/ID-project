package org.example.idproject.view.infoPanes;

import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

public abstract class AbstractInfoController {
    protected final DatabaseService databaseService;
    protected final ScreenManager screenManager;

    AbstractInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        this.databaseService = databaseService;
        this.screenManager = screenManager;
    }

    public abstract void update(int id);
}
