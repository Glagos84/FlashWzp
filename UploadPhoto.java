package cl.mind.flashwzp.views.main.drawer;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.mind.flashwzp.data.CurrentUSer;
import cl.mind.flashwzp.data.EmailProcessor;
import cl.mind.flashwzp.data.Nodes;
import cl.mind.flashwzp.data.PhotoPreference;
import cl.mind.flashwzp.models.LocalUser;

/**
 * Created by Gabriel on 30-08-2017.
 */

public class UploadPhoto {

    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void toFireBase(String path){

        final CurrentUSer currentUSer = new CurrentUSer();
        String folder = new EmailProcessor().sanitizedEmail(currentUSer.email() + "/");
        String photoName = "avatar.jpeg";
        String baseUrl = "gs://flashwzp.appspot.com/avatars/";
        String refUrl = baseUrl + folder + photoName;
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];
                new PhotoPreference(context).photoSave(url);
                LocalUser user = new LocalUser();
                user.setEmail(currentUSer.email());
                user.setName(currentUSer.getCurrentUser().getDisplayName());
                user.setPhoto(url);
                user.setUid(currentUSer.uid());
                String key = new EmailProcessor().sanitizedEmail(currentUSer.email());
                new Nodes().users().child(key).setValue(user);

            }
        });

        //ADD: photo upload, ADD: user sent to db

    }
}
