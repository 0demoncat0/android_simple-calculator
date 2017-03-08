package com.example.ctn.caculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // def types
    private static String TYPE_INT = "1";
    private static String TYPE_FLOAT = "2";
    private static String TYPE_OPERATOR_L = "3";
    private static String TYPE_OPERATOR_H = "4";
    private static String TYPE_OPENBRACE = "5";
    private static String TYPE_CLOSEBRACE = "6";

    private static final char OP_ADDITION = '+';
    private static final char OP_SUBTRACTION = '-';
    private static final char OP_MULTIPLICATION = '*';
    private static final char OP_DIVISION = '/';

    private static final String V_ADDITION = "&#43;";
    private static final String V_SUBTRACTION = "&#8722;";
    private static final String V_MULTIPLICATION = "&#215;";
    private static final String V_DIVISION = "&#247;";

    private String input;
    private String output;
    private TextView mainTextView;

    private ArrayList<String> values;
    private ArrayList<String> types;
    private int size;
    private int openingBraces = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init variables
        input = "";
        output = "";
        values = new ArrayList<>();
        types = new ArrayList<>();
        size = 0;
        mainTextView = (TextView) findViewById(R.id.mainTextView);
        mainTextView.setMovementMethod(new ScrollingMovementMethod());
        initButtons();

        mainTextView.setText("");
    }

    private void caculate() {
        String result = "";
        String baiToan = input.trim();
        if (baiToan.trim().length() > 0) {
            Calulate c = new Calulate(baiToan);
            if (c.isError()) {
                result = c.getError();
            } else {
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);
                result = df.format(c.getResult());
            }
        }
        input = "";
        output = "";
        if (!values.isEmpty() && !result.isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (values.get(i).equals(String.valueOf(OP_ADDITION))) {
                    output += "<font color=\"#1393b6\">"+V_ADDITION+"</font>";
                }
                else if (values.get(i).equals(String.valueOf(OP_SUBTRACTION))) {
                    output += "<font color=\"#1393b6\">"+V_SUBTRACTION+"</font>";
                }
                else if (values.get(i).equals(String.valueOf(OP_MULTIPLICATION))) {
                    output += "<font color=\"#1393b6\">"+V_MULTIPLICATION+"</font>";
                }
                else if (values.get(i).equals(String.valueOf(OP_DIVISION))) {
                    output += "<font color=\"#1393b6\">"+V_DIVISION+"</font>";
                }
                else {
                    output+= values.get(i);
                }
            }
            //output+= "\n= "+kq;
            output += "<font color=\"#3da23e\">" + "<br>= "+ result + "</font>";
        }
        mainTextView.setText(Html.fromHtml(output),TextView.BufferType.SPANNABLE);
    }

    private void inputChange() {
        input = "";
        output = "";
        if (!values.isEmpty()) {
            for (int i = 0; i < size; i++) {
                input += values.get(i);
                if (values.get(i).equals(String.valueOf(OP_ADDITION))) {
                    output += "<font color=\"#1393b6\">"+V_ADDITION+"</font>";
                }
                else if (values.get(i).equals(String.valueOf(OP_SUBTRACTION))) {
                    output += "<font color=\"#1393b6\">"+V_SUBTRACTION+"</font>";
                }
                else if (values.get(i).equals(String.valueOf(OP_MULTIPLICATION))) {
                    output += "<font color=\"#1393b6\">"+V_MULTIPLICATION+"</font>";
                }
                else if (values.get(i).equals(String.valueOf(OP_DIVISION))) {
                    output += "<font color=\"#1393b6\">"+V_DIVISION+"</font>";
                }
                else {
                    output+= values.get(i);
                }
            }
        }
        mainTextView.setText(Html.fromHtml(output),TextView.BufferType.SPANNABLE);
    }



    private void initButtons() {
        Button btn_0 = (Button) findViewById(R.id.buttonZero);
        btn_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(0);
            }
        });

        Button btn_1 = (Button) findViewById(R.id.buttonOne);
        btn_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(1);
            }
        });

        Button btn_2 = (Button) findViewById(R.id.buttonTwo);
        btn_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(2);
            }
        });

        Button btn_3 = (Button) findViewById(R.id.buttonThree);
        btn_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(3);
            }
        });

        Button btn_4 = (Button) findViewById(R.id.buttonFour);
        btn_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(4);
            }
        });

        Button btn_5 = (Button) findViewById(R.id.buttonFive);
        btn_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(5);
            }
        });

        Button btn_6 = (Button) findViewById(R.id.buttonSix);
        btn_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(6);
            }
        });

        Button btn_7 = (Button) findViewById(R.id.buttonSeven);
        btn_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(7);
            }
        });

        Button btn_8 = (Button) findViewById(R.id.buttonEight);
        btn_8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(8);
            }
        });

        Button btn_9 = (Button) findViewById(R.id.buttonNine);
        btn_9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberButtonClickHandler(9);
            }
        });


        // === SPEC BUTTONS ===

        Button btn_dot = (Button) findViewById(R.id.buttonDot);
        btn_dot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (size > 0) {
                    if (types.get(size - 1).equals(TYPE_INT)) {
                        //phia truoc la so nguyen -> float
                        updateNumber_addDot(size - 1);
                    }
                    /*
                    else if (types.get(size - 1).equals(TYPE_FLOAT)) {
                        //phia truoc la so thap phan -> huy
                    }
                    */
                    else if (types.get(size - 1).equals(TYPE_OPENBRACE)) {
                        //phia truoc la mo ngoac -> 0.
                        addValue("0", TYPE_INT);
                        updateNumber_addDot(size - 1);
                    } else if (types.get(size - 1).equals(TYPE_CLOSEBRACE)) {
                        //phia truoc la dong ngoac -> *0.
                        addValue(String.valueOf(OP_MULTIPLICATION), TYPE_OPERATOR_H);
                        addValue("0", TYPE_INT);
                        updateNumber_addDot(size - 1);
                    } else if (types.get(size - 1).equals(TYPE_OPERATOR_H) || types.get(size - 1).equals(TYPE_OPERATOR_L)) {
                        // phia truoc la toan tu -> 0.
                        addValue("0", TYPE_INT);
                        updateNumber_addDot(size - 1);
                    }
                } else {
                    addValue("0", TYPE_INT);
                    updateNumber_addDot(size - 1);
                }
            }
        });


        Button btn_sign = (Button) findViewById(R.id.buttonSign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (size == 0) {
                    addValue("(", TYPE_OPENBRACE);
                    openingBraces++;
                    addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                } else {
                    if (isNumber(size - 1)) {
                        //phia truoc la so
                        if (size <= 2) {
                            // them (-
                            insertValue("(", TYPE_OPENBRACE, size - 1);
                            openingBraces++;
                            insertValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L, size - 1);
                        } else {
                            // size >= 3
                            if (values.get(size-3).equals("(") && values.get(size-2).equals(String.valueOf(OP_SUBTRACTION))) {
                                // truong hop so da co dau am "(-" -> bo "(-"
                                deleteValue(size-2);
                                deleteValue(size-2);
                                openingBraces--;
                            } else {
                                insertValue("(", TYPE_OPENBRACE, size - 1);
                                openingBraces++;
                                insertValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L, size - 1);
                            }
                        }
                    }
                    else if (types.get(size - 1).equals(TYPE_OPENBRACE)) {
                        //phia truoc la mo ngoac -> them (-
                        addValue("(", TYPE_OPENBRACE);
                        openingBraces++;
                        addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                    }
                    else if (types.get(size - 1).equals(TYPE_CLOSEBRACE)) {
                        //phia truoc la dong ngoac -> *(-
                        addValue(String.valueOf(OP_MULTIPLICATION), TYPE_OPERATOR_H);
                        addValue("(", TYPE_OPENBRACE);
                        openingBraces++;
                        addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                    }
                    else if (types.get(size - 1).equals(TYPE_OPERATOR_H) || types.get(size - 1).equals(TYPE_OPERATOR_L)) {
                        if (size >= 2) {
                            if (values.get(size - 1).equals(String.valueOf(OP_SUBTRACTION)) && values.get(size - 2).equals("(")) {
                                // truoc do la "(-" -> xoa "(-"
                                deleteValue(size - 1);
                                deleteValue(size - 1);
                                openingBraces--;
                            } else {
                                addValue("(", TYPE_OPENBRACE);
                                openingBraces++;
                                addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                            }
                        } else {
                            // phia truoc la toan tu -> them (-
                            addValue("(", TYPE_OPENBRACE);
                            openingBraces++;
                            addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                        }
                    }
                }
            }
        });


        Button btn_clear = (Button) findViewById(R.id.buttonClear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                input = "";
                output = "";
                values.clear();
                types.clear();
                size = 0;
                openingBraces = 0;
                inputChange();
            }
        });

        Button btn_mul = (Button) findViewById(R.id.buttonMul);
        btn_mul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (size > 0) {
                    if (isNumber(size-1) || values.get(size-1).equals(")")) {
                        // truoc do la so hoac ")"
                        addValue(String.valueOf(OP_MULTIPLICATION),TYPE_OPERATOR_H);
                    }
                }
            }
        });

        Button btn_div = (Button) findViewById(R.id.buttonDiv);
        btn_div.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (size > 0) {
                    if (isNumber(size-1) || values.get(size-1).equals(")")) {
                        // truoc do la so hoac ")"
                        addValue(String.valueOf(OP_DIVISION),TYPE_OPERATOR_H);
                    }
                }
            }
        });

        Button btn_add = (Button) findViewById(R.id.buttonAdd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (size > 0) {
                    if (isNumber(size-1) || values.get(size-1).equals(")")) {
                        // truoc do la so hoac ")"
                        addValue(String.valueOf(OP_ADDITION),TYPE_OPERATOR_L);
                    }
                }
            }
        });

        Button btn_sub = (Button) findViewById(R.id.buttonSub);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (size > 0) {
                    if (isNumber(size-1) || values.get(size-1).equals(")")) {
                        // truoc do la so hoac ")"
                        addValue(String.valueOf(OP_SUBTRACTION),TYPE_OPERATOR_L);
                    }
                }
            }
        });

        Button btn_del = (Button) findViewById(R.id.buttonDel);
        btn_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (size>0){
                    if (isNumber(size-1)) {
                        // truoc do la so
                        String n = values.get(size-1);
                        if(n.length()<=1) {
                            // con 1 ki tu -> xoa luon so
                            deleteValue(size-1);
                        }
                        else {
                            if (n.charAt(n.length()-1) == '.') {
                                // xoa dau thap phan, chuyen float ve int
                                values.set(size-1,n.substring(0,n.length()-1));
                                types.set(size-1,TYPE_INT);
                                inputChange();
                            }
                            else {
                                // xoa chu so binh thuong
                                values.set(size-1,n.substring(0,n.length()-1));
                                inputChange();
                            }
                        }
                    }
                    else if (types.get(size-1).equals(TYPE_OPENBRACE)) {
                        // xoa dau mo ngoac
                        deleteValue(size-1);
                        openingBraces--;
                    }
                    else if (types.get(size-1).equals(TYPE_CLOSEBRACE)) {
                        // xoa dau dong ngoac
                        deleteValue(size-1);
                        openingBraces++;
                    }
                    else {
                        // xoa +-*/
                        deleteValue(size-1);
                    }
                }
            }
        });

        Button btn_brace = (Button) findViewById(R.id.buttonBrace);
        btn_brace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(size == 0) {
                    // chua nhap gi -> (
                    addValue("(", TYPE_OPENBRACE);
                    openingBraces++;
                }
                else {
                    if (isNumber(size-1)) {
                        if (openingBraces > 0) {
                            addValue(")",TYPE_CLOSEBRACE);
                            openingBraces--;
                        }
                        else {
                            addValue(String.valueOf(OP_MULTIPLICATION),TYPE_OPERATOR_H);
                            addValue("(",TYPE_OPENBRACE);
                            openingBraces++;
                        }
                    }
                    else if (types.get(size-1).equals(TYPE_OPENBRACE)) {
                        addValue("(",TYPE_OPENBRACE);
                        openingBraces++;
                    }
                    else if (types.get(size-1).equals(TYPE_CLOSEBRACE)) {
                        if (openingBraces > 0) {
                            addValue(")",TYPE_CLOSEBRACE);
                            openingBraces--;
                        }
                        else {
                            addValue(String.valueOf(OP_MULTIPLICATION),TYPE_OPERATOR_H);
                            addValue("(",TYPE_OPENBRACE);
                            openingBraces++;
                        }
                    }
                    else {
                        addValue("(",TYPE_OPENBRACE);
                        openingBraces++;
                    }
                }
            }
        });

        Button btn_equal = (Button) findViewById(R.id.buttonEqual);
        btn_equal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                caculate();
            }
        });

        ImageButton btn_history = (ImageButton) findViewById(R.id.buttonHistory);
        btn_history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // history view
            }
        });
    }


    // ============================

    private void addValue (String value, String type) {
        values.add(value);
        types.add(type);
        size ++;
        inputChange();
    }

    private void insertValue (String value, String type, int index) {
        values.add(index, value);
        types.add(index, type);
        size ++;
        inputChange();
    }

    private void deleteValue (int index) {
        values.remove(index);
        types.remove(index);
        size --;
        inputChange();
    }

    private void updateNumber_addNumber (int index, String number){
        values.set(index, values.get(index)+number);
        inputChange();
    }

    private void updateNumber_addDot(int index) {
        // them dau '.', chuyen int thanh float
        values.set(index, values.get(index)+".");
        types.set(index,TYPE_FLOAT);
        inputChange();
    }

    private boolean isNumber (int index){
        return (types.get(index).equals(TYPE_INT) || types.get(index).equals(TYPE_FLOAT));
    }

    private void numberButtonClickHandler(int btnNumber) {
        if (size > 0) {
            if (types.get(size-1).equals(TYPE_CLOSEBRACE)) {
                // truoc do la ngoac dong
                addValue(String.valueOf(OP_MULTIPLICATION),TYPE_OPERATOR_H);
                addValue(""+btnNumber,TYPE_INT);
            }
            else if (isNumber(size-1)) {
                // neu dang tiep tuc nhap so
                updateNumber_addNumber(size - 1, ""+btnNumber);
            }
            else {
                // truoc do la toan tu
                addValue(""+btnNumber,TYPE_INT);
            }
        }
        else {
            addValue(""+btnNumber,TYPE_INT);
        }
    }


}
