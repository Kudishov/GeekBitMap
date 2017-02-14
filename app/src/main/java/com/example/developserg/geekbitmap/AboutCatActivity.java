package com.example.developserg.geekbitmap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutCatActivity extends Activity {
    private TextView tvCatName;
    private TextView tvCatDescription;
    private TextView tvCatType;
    private ImageView ivCatPhoto;
    private Button btnCall;
    private Cat cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cat);
        initView();

        if (getIntent().getParcelableExtra(MapsActivity.CAT_EXTRA) != null) {
            cat = getIntent().getParcelableExtra(MapsActivity.CAT_EXTRA);
        }

        tvCatDescription.setText(getString(R.string.description) + ": " + cat.getDescription());
        tvCatName.setText(getString(R.string.name) + ": " + cat.getName());
        tvCatType.setText(getString(R.string.type) + ": " + cat.getType());
        ivCatPhoto.setImageResource(cat.getFoto());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:000000000";
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                startActivity(callIntent);
            }
        });
    }

    private void initView() {
        tvCatDescription = (TextView) findViewById(R.id.tvDescriptions);
        tvCatType = (TextView) findViewById(R.id.tvCatType);
        tvCatName = (TextView) findViewById(R.id.tvCatName);
        ivCatPhoto = (ImageView) findViewById(R.id.ivCatPhoto);
        btnCall = (Button) findViewById(R.id.btnCall);
    }
}
