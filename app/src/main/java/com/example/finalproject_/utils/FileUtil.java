package com.example.finalproject_.utils;


import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class FileUtil {

    public static File copyFileFromUri(Context context, Uri uri) throws IOException {
        File outputFile = createOutputFile(context, uri);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4 * 1024]; // גודל הבאפר לקריאה/כתיבה
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();

            return outputFile;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static File createOutputFile(Context context, Uri uri) throws IOException {
        String extension = getFileExtension(context, uri);

        // Log.d("MyTag", extension);

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File outputFile = File.createTempFile("temp_", extension, storageDir);
        return outputFile;
    }

    private static String getFileExtension(Context context, Uri uri) {
        String extension = "";
        String mimeType = context.getContentResolver().getType(uri);
        if (mimeType != null) {
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        }
        if (extension == null || extension.isEmpty()) {
            extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        }
        if (extension == null) {
            extension = "";
        }
        return extension;
    }

    public static File copyFile(Context context, Uri uri) {
        File copiedFile = null;
        if (Objects.nonNull(uri)) {
            try {
                copiedFile = FileUtil.copyFileFromUri(context, uri);
                if (copiedFile.exists()) {
                    long fileSize = copiedFile.length(); // גודל הקובץ בבתים
                    Toast.makeText(MyApplication.getInstance(), "" + fileSize, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getInstance(), "הקובץ לא קיים", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MyApplication.getInstance(), "קובץ לא חוקי", Toast.LENGTH_SHORT).show();
            }
        }
        return copiedFile;
    }
}
