package com.jakebrooker.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.function.DoubleSupplier;

public class CalculateLoan extends AppCompatActivity {

    TextView resultMonthlyText;
    TextView resultInterestText;
    TextView resultTotalText;

    EditText initAmountText;
    EditText interestRateText;
    EditText lengthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_loan);

        resultMonthlyText = (TextView) findViewById(R.id.resultMonthly);
        resultInterestText = (TextView) findViewById(R.id.resultInterest);
        resultTotalText = (TextView) findViewById(R.id.resultTotal);

        initAmountText = (EditText) findViewById(R.id.inputInitAmount);
        interestRateText = (EditText) findViewById(R.id.inputInterestRate);
        lengthText = (EditText) findViewById(R.id.inputLength);

    }

    public void calculateResults(View view)
    {
        double amount = 0, rate = 0;
        int years = 0;

        try {
            years = Integer.parseInt(lengthText.getText().toString());
            amount = Double.parseDouble(initAmountText.getText().toString());
            rate = Double.parseDouble(interestRateText.getText().toString());

            resultMonthlyText.setText("Monthly payment: " + getMonthlyPayment(amount, rate, years));
            resultInterestText.setText("Total interest: " + getTotalInterest(amount, rate, years));
            resultTotalText.setText("Total payment: " + getTotalPayment(amount, rate, years));
        }
        catch(Exception e) {
            resultMonthlyText.setText("ERROR! One or more of your inputed values are not numbers.");
        }
    }

    private double getTotalPayment(double loanAmount, double yearlyInterestRate, int numberOfYears)
    {
        return getMonthlyPayment(loanAmount, yearlyInterestRate, numberOfYears)*numberOfYears*12;
    }

    private double getMonthlyPayment (double loanAmount, double yearlyInterestRate, int numberOfYears)
    {
        double monthlyPayment;
        double monthlyInterestRate;
        int numberOfPayments;
        if (numberOfYears != 0 && yearlyInterestRate != 0)
        {
            //calculate the monthly payment
            monthlyInterestRate = yearlyInterestRate / 1200;
            numberOfPayments = numberOfYears * 12;

            monthlyPayment =
                    (loanAmount * monthlyInterestRate) /
                            (1 - (1 / Math.pow ((1 + monthlyInterestRate), numberOfPayments)));

            monthlyPayment = Math.round (monthlyPayment * 100) / 100.0;
        }
        else
            monthlyPayment = 0;
        return monthlyPayment;
    }

    private double getTotalInterest(double loanAmount, double yearlyInterestRate, int numberOfYears)
    {
        return getTotalPayment(loanAmount, yearlyInterestRate, numberOfYears) - loanAmount;
    }

    public void clearPage(View view)
    {
        //OUTPUT
        resultMonthlyText.setText("");
        resultInterestText.setText("");
        resultTotalText.setText("");

        //INPUT
        initAmountText.setText("");
        interestRateText.setText("");
        lengthText.setText("");

    }

}
