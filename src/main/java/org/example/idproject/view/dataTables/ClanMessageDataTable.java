package org.example.idproject.view.dataTables;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.example.idproject.common.ClanMessage;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class ClanMessageDataTable extends CustomDataTable<ClanMessage> {
    public ClanMessageDataTable() throws IOException {
        super(FXMLAddresses.CLAN_MESSAGE_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("Sent date", "sentDate");
        addDataColumn("Sender Nickname", "senderNickname");
        var messageColumn = addDataColumn("Message Text", "msgText");

        messageColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ClanMessage, String> call(TableColumn<ClanMessage, String> param) {
                return new TableCell<ClanMessage, String>() {
                    private final Text text = new Text();

                    {
                        // Enable wrapping by binding the wrapping width to the column width
                        text.wrappingWidthProperty().bind(param.widthProperty());
                        text.setStyle("-fx-fill: black;"); // Example of text styling
                        setGraphic(text);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            text.setText(null);
                        } else {
                            text.setText(item);
                        }
                    }
                };
            }
        });
    }
}
