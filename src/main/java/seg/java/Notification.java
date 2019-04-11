package seg.java;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notification {
    public void makeNotification(String title, String text, Image icon) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(icon))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
}
