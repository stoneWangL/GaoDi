package com.example.stonewang.gaodi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by stoneWang on 2017/3/3.
 */

public class TestGson extends AppCompatActivity {

    private String jsonData = "[{\"name\":\"Micharel\",\"age\":20},{\"name\":\"Mike\",\"age\":21}]";
    private String jsonDataTwo = "{\"name\":\"Micharel\",\"age\":20}";
    private Button button, button2, button3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testgson);

        button = (Button) findViewById(R.id.buttonid);
        button.setOnClickListener(new ButtonListener());

        button2 = (Button) findViewById(R.id.button_google_json_one);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonUtilsTwo jsonUtilsTwo = new JsonUtilsTwo();
                jsonUtilsTwo.parseUserFromJson(jsonDataTwo);
            }
        });

        button3 = (Button) findViewById(R.id.button_google_json_two);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonUtilsThree jsonUtilsThree = new JsonUtilsThree();
                jsonUtilsThree.parseUserFromJson(jsonData);
            }
        });
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            JsonUtils jsonUtils = new JsonUtils();
            jsonUtils.parseJson(jsonData);
        }
    }
}
