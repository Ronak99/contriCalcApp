package com.contricalcapp.www.contricalc;


import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorFragment extends Fragment {

    private TextView result;

    private String operand;

    private String operator;

    private Set<String> numbers;

    private Set<String> operators;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonClear;
    Button buttonDivide;
    Button buttonMinus;
    Button buttonPlus;
    Button buttonResult;
    Button buttonMultiply;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator,container,false);
        result = view.findViewById(R.id.result);

         button0        = view.findViewById(R.id.button0);
         button1        = view.findViewById(R.id.button1);
         button2        = view.findViewById(R.id.button2);
         button3        = view.findViewById(R.id.button3);
         button4        = view.findViewById(R.id.button4);
         button5        = view.findViewById(R.id.button5);
         button6        = view.findViewById(R.id.button6);
         button7        = view.findViewById(R.id.button7);
         button8        = view.findViewById(R.id.button8);
         button9        = view.findViewById(R.id.button9);
         buttonClear    = view.findViewById(R.id.buttonClear);
         buttonDivide   = view.findViewById(R.id.buttonDivide);
         buttonMinus    = view.findViewById(R.id.buttonMinus);
         buttonPlus     = view.findViewById(R.id.buttonPlus);
         buttonResult   = view.findViewById(R.id.buttonResult);
         buttonMultiply = view.findViewById(R.id.buttonMultiply);

        initListeners();

        return view;
    }


    public void initListeners(){

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        });


    }

    /**
     * Initialization of set of number values.
     */
    private void initNumbers() {
        numbers = new HashSet<String>();
        for (int i = 0; i < 10; i++) {
            numbers.add(Integer.toString(i));
        }
    }

    /**
     * Initialization of set of operators.
     */
    private void initOperators() {
        operators = new HashSet<String>();
        String[] ops = { "+", "-", "*", "/" };
        for (String operator : ops) {
            operators.add(operator);
        }
    }

    /**
     * Button click event handler.
     *
     * @param view
     *            clicked button
     * @return void
     */
    public void handleClick(View view) {
        Button clicked = (Button) view;
        String value = clicked.getText().toString();

        if (isNumerical(value)) {
            if (!isDefaultResult(result.getText().toString())) {
                value = result.getText().toString() + value;
            }
        } else if (isOperator(value)) {
            operand = result.getText().toString();
            operator = value;
        } else if (isClear(value)) {
            value = getString(R.string.result_default);
        } else {
            double a = Double.parseDouble(operand), b = Double
                    .parseDouble(result.getText().toString());

            if (operator.equals("+")) {
                value = Double.toString(a + b);
            }
            if (operator.equals("-")) {
                value = Double.toString(a - b);
            }
            if (operator.equals("*")) {
                Toast.makeText(getActivity(),"in multipply", Toast.LENGTH_SHORT).show();
            }
            if (operator.equals("/")) {
                value = Double.toString(a / b);
            }

            // Reset values.
            operator = null;
            operand = null;
        }

        result.setText(value);
    }

    /**
     * Test if value is the same as clear button's.
     *
     * @param value
     *            button value
     * @return true if button is clear button
     */
    private boolean isClear(String value) {
        return value.equals(getString(R.string.buttonClear));
    }

    /**
     * Test if value is operator.
     *
     * @param value
     *            button value
     * @return true if value is operator
     */
    private boolean isOperator(String value) {
        if (operators == null) {
            initOperators();
        }
        return operators.contains(value);
    }

    /**
     * Test if result was modified.
     *
     * @param value
     *            result value
     * @return true if result is default
     */
    private boolean isDefaultResult(String value) {
        return value.equals(getString(R.string.result_default));
    }

    /**
     * Test if value is numerical.
     *
     * @param value
     *            button's value
     * @return true if value is numerical
     */
    private boolean isNumerical(String value) {
        if (numbers == null) {
            initNumbers();
        }
        return numbers.contains(value);
    }

}