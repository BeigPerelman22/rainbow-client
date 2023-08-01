package com.example.finalproject_.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.finalproject_.R;

public class LoaderUtils {

    private static View overlay;
    private static ProgressBar loader;

    public static void showLoader(Activity activity) {
        if (overlay == null) {
            View loaderLayout = activity.getLayoutInflater().inflate(R.layout.loader, null);

            overlay = loaderLayout.findViewById(R.id.overlay);
            loader = loaderLayout.findViewById(R.id.loader);

            ViewGroup content = activity.findViewById(android.R.id.content);
            content.addView(loaderLayout);
        }

        overlay.setVisibility(View.VISIBLE);
        loader.setVisibility(View.VISIBLE);
    }

    public static void hideLoader() {
        overlay.setVisibility(View.INVISIBLE);
        loader.setVisibility(View.INVISIBLE);
    }

}

