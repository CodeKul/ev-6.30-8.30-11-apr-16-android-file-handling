package com.codekul.filehandaling;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText edtSomeData;
    private Button btnOkay;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSomeData = (EditText) findViewById(R.id.edtSomeData);

        btnOkay = (Button) findViewById(R.id.btnOkay);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                externalStorage();
            }
        });
        textResult = (TextView) findViewById(R.id.textResult);
    }

    private void fileBasics() {

        File fileInternal = new File(getFilesDir(), "Downloads");
        fileInternal.mkdir();
        fileInternal.delete();

       fileInternal = new File("/");
        File[] rootFiles = fileInternal.listFiles();

        for (File file : rootFiles) {
            textResult.append("\n" + (file.isDirectory() ? " * " + file.getName() : " - " + file.getName()));
        }

        textResult.append("getFilesDir -> " + fileInternal.getPath());
        textResult.append("Exists -> " + fileInternal.exists());

        textResult.append("getFilesDir -> " + fileInternal.getPath());
    }

    private void internalStorage(){
        try {

            FileOutputStream fos = openFileOutput("my.txt", MODE_APPEND);
            byte[] byteData = "Hello World".getBytes();
            fos.write(byteData, 0, byteData.length);
            fos.close();

            FileInputStream fis = openFileInput("my.txt");
            StringBuilder builder = new StringBuilder();
            while (true) {

                int ch = fis.read();
                if (ch == -1) break;
                else {
                    builder.append("" + (char) ch);
                }
                textResult.setText(builder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void externalStorage(){

        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            textResult.setText("Bad Media");
            return;
        }

        textResult.append("\n Public Path - "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
        textResult.append("\n External Path - "+getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }
}
