package cl.mind.flashwzp.views.main.drawer;

import android.content.Context;

import cl.mind.flashwzp.data.PhotoPreference;

import static android.os.Build.VERSION_CODES.O;

/**
 * Created by Gabriel on 30-08-2017.
 */

public class PhotoValidation {

    private Context context;
    private PhotoCallback callback;

    public PhotoValidation(Context context, PhotoCallback photoCallback) {
        this.context = context;
        this.callback = photoCallback;
    }

    public void validate(){

        String url = new PhotoPreference(context).getPhoto();
        if (url != null){

            callback.photoAvailable(url);

        }else {

            callback.empyPhoto();

        }



    }
}
