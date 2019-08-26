package rpl2016_17.example.com.salesmanmake2.Notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
<<<<<<< HEAD

public class MyFirebaseMessagingService extends FirebaseMessagingService {

}
=======
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        if(remoteMessage.getData().size() > 0){

        }

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

    }
}
>>>>>>> c2ffdfba5805c0da5c08292ad2cc73af38797533
