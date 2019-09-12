package com.mala.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_operation;
    private TextView tv_result;
    private TextView tv_ac;
    private TextView tv_one,tv_two, tv_three, tv_four, tv_five, tv_six, tv_seven, tv_eight, tv_nine,tv_zero;
    private TextView tv_plus, tv_minus, tv_multiply, tv_divide, tv_percent;
    private TextView tv_equals;

    private Integer num1, num2, num1Length;
    private boolean isOperatorReady;
    private String lastOperator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_operation=(TextView) findViewById(R.id.tv_operation);
        tv_result = (TextView) findViewById(R.id.tv_result);

        tv_ac = (TextView) findViewById(R.id.tv_ac);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        tv_four = (TextView) findViewById(R.id.tv_four);
        tv_five = (TextView) findViewById(R.id.tv_five);
        tv_six = (TextView) findViewById(R.id.tv_six);
        tv_seven = (TextView) findViewById(R.id.tv_seven);
        tv_eight = (TextView) findViewById(R.id.tv_eight);
        tv_nine = (TextView) findViewById(R.id.tv_nine);
        tv_zero = (TextView) findViewById(R.id.tv_zero);

        tv_plus = (TextView) findViewById(R.id.tv_plus);
        tv_minus = (TextView) findViewById(R.id.tv_minus);
        tv_multiply = (TextView) findViewById(R.id.tv_multiply);
        tv_divide = (TextView) findViewById(R.id.tv_divide);
        tv_percent = (TextView) findViewById(R.id.tv_percent);
        tv_equals=(TextView) findViewById(R.id.tv_equals);

        //initiation
        tv_operation.setText("");
        tv_result.setText("");
        isOperatorReady=false;
        num1=0;
        num2=0;
        num1Length=0;
        lastOperator="";
        operatorPosition=-100;

    }

    public void onClickAC(View view){
        tv_operation.setText("");
        tv_result.setText("");
        isOperatorReady=false;
    }

    public void onClickDel(View view){
        String operation = tv_operation.getText().toString();
        if(operation.length()>0) {
            String result = operation.substring(0, operation.length() - 1);
            tv_operation.setText(result);

            if(num1Length>result.length()){
                isOperatorReady=false;
                lastOperator="";
            }
        }
    }

    public void onClickNumber(View view){
        String number=checkNumber(view.getId());
        if(tv_result.getText().toString().equalsIgnoreCase("error")){
            tv_result.setText("");
        }
        else{
            String operation = tv_operation.getText().toString();
            tv_operation.setText(operation + number);
        }
    }

    public void onClickOperator(View view){
        String operation = tv_operation.getText().toString();
        String operator = checkOperator(view.getId());
        //
        if(operation.length()==0){
            if(view.getId()!=tv_equals.getId())tv_result.setText("Error");
        }
        else if(operation.length()==num1Length+1 && num1Length!=0){
            String result = operation.substring(0, operation.length() - 1);
            tv_operation.setText(result + operator);
            lastOperator=operator;
        }
        else{
             if(isOperatorReady){
                num2=Integer.parseInt(operation.substring(num1Length+1,operation.length()));
                boolean isError=false;

                if(lastOperator=="+") num1=num1+num2;
                else if(lastOperator=="-")num1=num1-num2;
                else if(lastOperator=="*") num1=num1*num2;
                else if(lastOperator==":"){
                    if(num2==0) isError=true;
                    else num1=num1/num2;
                }

                if(operator=="="){
                    isOperatorReady=false;
                    tv_result.setText("");
                    tv_operation.setText(num1.toString() );
                }
                else {
                    tv_result.setText(num1.toString());
                    tv_operation.setText(num1.toString()+ operator);
                    lastOperator=operator;
                    operatorPosition=num1.toString().length()+1;
                }

                if(isError) tv_operation.setText("Error");

                num1Length=num1.toString().length();

            }
            else {
                if(operator!="="){
                    num1=Integer.parseInt(operation);
                    num1Length = operation.length();
                    tv_operation.setText(operation + operator);

                    isOperatorReady=true;
                    lastOperator=operator;
                }
            }
        }
        tv_debugger.setText("panjang operasi"+ operation.length()+ "nuumlength" + num1Length);

    }

    /*public void negateNumber(View view){
        String operation=tv_operation.getText().toString();
        Integer number;
        if(!isOperatorReady && operation!=""){

            if(operation.charAt(0)=='(') number=Integer.parseInt(operation.substring(1,operation.length()-1));
            else number=Integer.parseInt(operation);

            if(number!=0){
                number*=-1;
                if(number<0){
                    tv_operation.setText("("+number.toString()+")");
                }
                else tv_operation.setText(number.toString());
            }
        }
        else if(isOperatorReady &&  num1Length+1<operation.length()){
            if(operation.charAt(0)=='(') number=Integer.parseInt(operation.substring(num1Length+1,operation.length()-1));
            else number=Integer.parseInt(operation);

            if(number!=0){
                number*=-1;
                if(number<0){
                    tv_operation.setText(operation.substring(0,num1Length+1)+"("+number.toString()+")");
                }
                else tv_operation.setText(number.toString());
            }
        }
    }*/

    private String  checkNumber(int viewId){
        if(viewId==tv_one.getId()) return "1";
        else if(viewId==tv_two.getId()) return "2";
        else if(viewId==tv_three.getId()) return "3";
        else if(viewId==tv_four.getId()) return "4";
        else if(viewId==tv_five.getId()) return "5";
        else if(viewId==tv_six.getId()) return "6";
        else if(viewId==tv_seven.getId()) return "7";
        else if(viewId==tv_eight.getId()) return "8";
        else if(viewId==tv_nine.getId()) return "9";
        else if(viewId==tv_zero.getId()) return "0";

        return "-1";
    }

    private String checkOperator(int viewId){
        if(viewId==tv_plus.getId()) return "+";
        else if(viewId==tv_minus.getId()) return "-";
        else if(viewId==tv_multiply.getId()) return "*";
        else if(viewId==tv_divide.getId()) return ":";
        else if(viewId==tv_percent.getId()) return "%";
        else if(viewId==tv_equals.getId()) return "=";

        return "-1";
    }

}
