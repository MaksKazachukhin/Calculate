package com.example.calculate;

import static com.example.calculate.R.drawable.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate.R;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button gradientButton;
    private Spinner typeSpinner;
    private ImageButton likeButton;
    private boolean isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTextView = findViewById(R.id.resultTextView);
        gradientButton = findViewById(R.id.gradientButton);
        typeSpinner = findViewById(R.id.typeSpinner);
        likeButton = findViewById(R.id.likeButton);

        String result = getIntent().getStringExtra("result");
        resultTextView.setText(result);

        gradientButton.setOnClickListener(v -> {
            finishAffinity();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = (String) parent.getItemAtPosition(position);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.black));
                ((TextView) parent.getChildAt(0)).setText(selectedType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        likeButton.setOnClickListener(v -> {
            isLiked = !isLiked;
            if (isLiked) {
                likeButton.setBackgroundResource(R.drawable.pink2);
                likeButton.setImageResource(R.drawable.heart);
            } else {
                likeButton.setBackgroundResource(R.drawable.black);
                likeButton.setImageResource(R.drawable.heart2);
            }
        });
    }
}