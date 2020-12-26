package view;


import java.io.IOException;
import java.sql.SQLException;

public interface Menu {
    void execute() throws IOException, SQLException;
}
