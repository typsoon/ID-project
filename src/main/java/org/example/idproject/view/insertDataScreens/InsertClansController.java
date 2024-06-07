package org.example.idproject.view.insertDataScreens;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.idproject.App;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class InsertClansController extends AbstractInsertDataController {
    private final Map<String, String> nameToPathMapping = new HashMap<>();

    public InsertClansController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);

        URL folderUrl = App.class.getResource("clanLogos");

        Path folderPath;
        try {
            assert folderUrl != null;
            folderPath = Paths.get(folderUrl.toURI());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
            // Iterate over each file or subfolder in the folder
            for (Path file : stream) {
                int lastIndex = file.getFileName().toString().lastIndexOf(".");
                String fileNameNoExtension = file.getFileName().toString().substring(0, lastIndex);
                nameToPathMapping.put(fileNameNoExtension, "clanLogos/"+file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
//        System.out.println(nameToPathMapping);
    }


    @FXML TextField leaderID;
    @FXML DatePicker dateFrom;
    @FXML TextField clanName;
    @FXML ChoiceBox<String> clanLogos;

    @Override
    void initialize() {
        for (String fileName : nameToPathMapping.keySet()) {
            clanLogos.getItems().add(fileName);
        }

        addSimpleDataButton.setOnAction(event -> insertSimpleData());
    }

    @Override
    void insertSimpleData() {
        try {
            databaseService.insertClan(Integer.parseInt(leaderID.getText()), dateFrom.getValue(),
                    clanName.getText(),
                    nameToPathMapping.get(clanLogos.getValue())
            );
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
    }
}
