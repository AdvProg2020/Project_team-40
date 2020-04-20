package View.AccountMenus;

import View.Menu;

public abstract class PeopleAccountMenu extends Menu {

    public PeopleAccountMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    public abstract void getShowInfo();

    public abstract void getEditInfo();
}
