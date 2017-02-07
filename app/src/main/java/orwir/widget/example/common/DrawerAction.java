package orwir.widget.example.common;


public class DrawerAction {

    final String title;
    final Class<? extends BaseActivity> clazz;

    public DrawerAction(String title, Class<? extends BaseActivity> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return title;
    }
}
