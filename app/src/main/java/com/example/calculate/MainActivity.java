package com.example.calculate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate.R;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonHidden;
    private String input = "";
    private String operator = "";
    private double firstNumber = 0.0;
    private double secondNumber = 0.0;
    private boolean isNewOperation = true;
    private boolean hasOperator = false;
    private boolean isError = false;
    private String resultString = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        buttonHidden = findViewById(R.id.buttonHidden);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonClear = findViewById(R.id.buttonClear);

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                appendNumber(button.getText().toString());
                buttonHidden.setVisibility(View.GONE);
            }
        };

        button0.setOnClickListener(numberClickListener);
        button1.setOnClickListener(numberClickListener);
        button2.setOnClickListener(numberClickListener);
        button3.setOnClickListener(numberClickListener);
        button4.setOnClickListener(numberClickListener);
        button5.setOnClickListener(numberClickListener);
        button6.setOnClickListener(numberClickListener);
        button7.setOnClickListener(numberClickListener);
        button8.setOnClickListener(numberClickListener);
        button9.setOnClickListener(numberClickListener);
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDot();
                buttonHidden.setVisibility(View.GONE);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError) {
                    setOperator("+");
                    buttonHidden.setVisibility(View.GONE);
                }
            }
        });
        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError) {
                    setOperator("-");
                    buttonHidden.setVisibility(View.GONE);
                }
            }
        });
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError) {
                    setOperator("*");
                    buttonHidden.setVisibility(View.GONE);
                }
            }
        });
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError) {
                    setOperator("/");
                    buttonHidden.setVisibility(View.GONE);
                }
            }
        });

        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError) {
                    calculate();
                    buttonHidden.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                buttonHidden.setVisibility(View.GONE);
            }
        });
    }

    public void onNumberClick(){

    }

    private void appendNumber(String number) {
        if (isError) {
            clear();
        }
        if (isNewOperation) {
            input = "";
            isNewOperation = false;
        }
        input += number;
        textView.setText(input);
    }

    private void appendDot() {
        if (isError) {
            clear();
        }
        if (!input.contains(".")) {
            if (isNewOperation) {
                input = "0";
                isNewOperation = false;
            }
            input += ".";
            textView.setText(input);
        }
    }

    private void setOperator(String op) {
        if (!input.isEmpty()) {
            if (!hasOperator) {
                firstNumber = Double.parseDouble(input);
                input = "";
                operator = op;
                isNewOperation = true;
                hasOperator = true;
            } else {
                calculate();
                operator = op;
                hasOperator = true;
            }
        }
    }

    private void calculate() {
        if (input.isEmpty() || !hasOperator) {
            return;
        }
        secondNumber = Double.parseDouble(input);
        double result = 0.0;
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    textView.setText("Error");
                    isError = true;
                    return;
                }
                break;
        }
        resultString = formatResult(result);
        textView.setText(resultString);
        input = String.valueOf(result);
        firstNumber = result;
        isNewOperation = true;
        hasOperator = false;
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%s", result);
        }
    }

    private void calculatePercent() {
        if (!input.isEmpty()) {
            double value = Double.parseDouble(input);
            value = value / 100;
            input = String.valueOf(value);
            textView.setText(formatResult(value));
        }
    }

    private void clear() {
        input = "";
        operator = "";
        firstNumber = 0.0;
        secondNumber = 0.0;
        isNewOperation = true;
        hasOperator = false;
        isError = false;
        textView.setText("0");
    }


    public void onHiddenButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, com.example.calculate.ResultActivity.class);
        intent.putExtra("result", resultString);

        startActivity(intent);
    }
}