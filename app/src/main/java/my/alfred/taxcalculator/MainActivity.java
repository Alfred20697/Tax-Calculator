//MainActivity.java
//Calculate bill with 6% GST or custom tax percentage

package my.alfred.taxcalculator;

import java.text.NumberFormat; //Currency formatting
import android.support.v7.app.AppCompatActivity; //base class for activity
import android.os.Bundle; //for saving state instance
import android.widget.EditText; //for bill amount input
import android.widget.SeekBar; //for changing custom tax percentage
import android.widget.TextView; ////for displaying text
import android.text.TextWatcher; //EditText Listener
import android.text.Editable; //for EditText event handling

//MainActivity Class for Tax Calculator application
public class MainActivity extends AppCompatActivity {
    //currency and percentage formatting
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0; //bill amount entered by user
    private double customPercent = 0.10; //initial custom tax percentage
    private TextView amountDisplayTextView; //show formatted bill amount
    private TextView percentCustomTextView; //show custom tax percentage
    private TextView tax6TextView; //show 6% tax
    private TextView total6TextView; //show total with 6% tax
    private TextView taxCustomTextView; //show custom tax amount
    private TextView totalCustomTextView; //show total with custom tax amount

    //call when activity first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //call superclass
        setContentView(R.layout.activity_main); //call the GUI

        //get reference to text views
        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tax6TextView = (TextView) findViewById(R.id.tax6TextView);
        total6TextView = (TextView) findViewById(R.id.total6TextView);
        taxCustomTextView = (TextView) findViewById(R.id.taxCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        //update GUI based on bill amount and custom tax amount
        amountDisplayTextView.setText(currencyFormat.format(billAmount));
        updateStandard(); //update 6% tax text view
        updateCustom(); //update custom tax percentage text view

        //set amountEditText text watcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        //set customSeekBar listener
        SeekBar customTaxSeekBar = (SeekBar) findViewById(R.id.customTaxSeekBar);
        customTaxSeekBar.setOnSeekBarChangeListener(customeTaxSeekBarListener);
    } //end method onCreate

    //update 6% tax text view
    private void updateStandard(){
        //calculate 6& tax and total
        double sixPercentTax = billAmount * 0.06;
        double sixPercentTotal = billAmount + sixPercentTax;

        //display 6% tax and total formatted as currency
        tax6TextView.setText(currencyFormat.format(sixPercentTax));
        total6TextView.setText(currencyFormat.format(sixPercentTotal));
    } //end method updateStandard

    //update custom tax percentage textview
    private void updateCustom(){

        //show custom percent in percentCustomTextView formatted as percentage
        percentCustomTextView.setText(percentFormat.format(customPercent));

        //calculate custom tax percentage and total
        double customTax = billAmount * customPercent;
        double customTotal = billAmount + customTax;

        //display custom tax percentage and total formatted as currency
        taxCustomTextView.setText(currencyFormat.format(customTax));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    } //end method updateCustom

    //invoked when user changes the position of seek bar
    private SeekBar.OnSeekBarChangeListener customeTaxSeekBarListener = new SeekBar.OnSeekBarChangeListener(){

        //update customPercent, then call updateCustom function
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //set customPercent to position of seek bar's thumb
            customPercent = progress/100.0;
            updateCustom(); //update custom tax percentage text view
        } //end method onProgressChanged

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //not implemented
        } //end method onStartTrackingTouch

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //not implemented
        } //end method onStopTrackingTouch
    }; //end OnSeekBarChangeListener

    //event handling object that responds to amountEditText's events
    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int count) {
            //not implemented
        } //end method beforeTextChanged

        //invoked when user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //convert amountEditText to a double
            //maximum digit receive is 6. Eg. 799999 will be shown as $7999.99
            try{
                billAmount = Double.parseDouble(s.toString())/100.0;
            } //end try
            catch (NumberFormatException e){
                billAmount = 0.0; //default if an exception occurs
            } //end catch

            //update GUI based on bill amount and custom tax amount
            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard(); //update 6% tax text view
            updateCustom(); //update custom tax percentage text view
        } //end method onTextChanged

        @Override
        public void afterTextChanged(Editable editable) {
            //not implemented
        } //end method afterTextChanged
    }; //end amountEditTextWatcher
} //end class MainActivity

//Future Improvement: GUI design, change dollar sign to MYR, implement more element