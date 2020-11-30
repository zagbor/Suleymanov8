package view;


import java.io.IOException;

public class MenuSelect {
    Menu menu;

    public MenuSelect(Menu menu) {
        this.menu = menu;
    }

    public void executePlay() throws IOException {
        menu.execute();
    }
}
