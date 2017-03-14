package com.example.ctn.caculator;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

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

    // encode cac toan tu trong html
    private static final String V_ADDITION = "&#43;";
    private static final String V_SUBTRACTION = "&#8722;";
    private static final String V_MULTIPLICATION = "&#215;";
    private static final String V_DIVISION = "&#247;";

    private String input;
    private String output;
    private TextView mainTextView;
    private HorizontalScrollView scrollView;

    private ArrayList<String> values;   // cac gia tri nhap vao
    private ArrayList<String> types;    // kieu cua cac gia tri nhap vao
    private int size;                   // kich thuoc 2 arraylist

    private int openingBraces;          // so ngoac mo chua dong

    private boolean ansAvailable;       // da co ket qua tu phep tinh truoc
    private double ansValue;            // ket qua tu phep tinh truoc
    private String ansType;             // kieu cua gia tri tu phep tinh truoc (int/double)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // khoi tao cac gia tri
        input = "";
        output = "";
        values = new ArrayList<>();
        types = new ArrayList<>();
        size = 0;
        openingBraces = 0;
        ansAvailable = false ;
        ansValue = 0;

        mainTextView = (TextView) findViewById(R.id.mainTextView);
        mainTextView.setMinWidth(Resources.getSystem().getDisplayMetrics().widthPixels);    // minWidth texview = chieu rong man hinh
        mainTextView.setText("");

        scrollView = (HorizontalScrollView) findViewById(R.id.mainScrollView);

        initButtons();
    }

    private String formatNumber(double d){
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern("###,###.###");
        return df.format(d);
    }

    private double formatDouble(String string){
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        Number number;
        try {
            number = format.parse(string);
            return number.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // ham tinh toan chinh
    private void caculate() {
        String result = ""; // ket qua tinh toan
        String baiToan = input.trim();
        if (baiToan.trim().length() > 0) {
            Calulation c = new Calulation(baiToan);
            if (c.isError()) {
                //phep tinh loi
                result = c.getError();
                clearAns(); // khong luu gia tri phep tinh
            } else {
                result = formatNumber(c.getResult());  // lam tron 4 chu so thap phan
                setAns(formatDouble(result)); // luu gia tri phep tinh
            }
        }

        // thay the input bang cac ki tu de hien thi
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
            output += "<font color=\"#3da23e\">" + "<br>= "+ result + "</font>";
        }
        // hien thi
        mainTextView.setText(Html.fromHtml(output),TextView.BufferType.SPANNABLE);

        // xem texview tu ben phai
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
            }
        });
    }

    // thay doi hien thi khi input thay doi
    private void inputChange() {
        input = "";
        output = "";
        if (!values.isEmpty()) {
            // thay the cac toan tu bang ki hieu trong html
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
        //  hien thi
        mainTextView.setText(Html.fromHtml(output),TextView.BufferType.SPANNABLE);

        // xem texview tu ben phai
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
            }
        });
    }

    // gan su kien cho cac nut
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

        Button btn_dot = (Button) findViewById(R.id.buttonDot);
        btn_dot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // xoa phep tinh truoc
                if (ansAvailable) {
                    clearFields();
                    clearAns();
                }

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
                // xoa phep tinh truoc
                if (ansAvailable) {
                    clearFields();
                    clearAns();
                }

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
                clearFields();
                clearAns();
            }
        });

        Button btn_mul = (Button) findViewById(R.id.buttonMul);
        btn_mul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ansAvailable) {
                    // neu co phep tinh truoc
                    // xoa man hinh
                    clearFields();
                    // lay ket qua tu phep tinh truoc
                    if (ansValue<0) {
                        // neu am -> them (-
                        addValue("(", TYPE_OPENBRACE);
                        openingBraces++;
                        addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                        ansValue = 0 - ansValue;
                    }
                    addValue(String.valueOf(ansValue),ansType);
                    clearAns();
                }
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
                if (ansAvailable) {
                    // neu co phep tinh truoc
                    // xoa man hinh
                    clearFields();
                    // lay ket qua tu phep tinh truoc
                    if (ansValue<0) {
                        // neu am -> them (-
                        addValue("(", TYPE_OPENBRACE);
                        openingBraces++;
                        addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                        ansValue = 0 - ansValue;
                    }
                    addValue(String.valueOf(ansValue),ansType);
                    clearAns();
                }
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
                if (ansAvailable) {
                    // neu co phep tinh truoc
                    // xoa man hinh
                    clearFields();
                    // lay ket qua tu phep tinh truoc
                    if (ansValue<0) {
                        // neu am -> them (-
                        addValue("(", TYPE_OPENBRACE);
                        openingBraces++;
                        addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                        ansValue = 0 - ansValue;
                    }
                    addValue(String.valueOf(ansValue),ansType);
                    clearAns();
                }
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
                if (ansAvailable) {
                    // neu co phep tinh truoc
                    // xoa man hinh
                    clearFields();
                    // lay ket qua tu phep tinh truoc
                    if (ansValue<0) {
                        // neu am -> them (-
                        addValue("(", TYPE_OPENBRACE);
                        openingBraces++;
                        addValue(String.valueOf(OP_SUBTRACTION), TYPE_OPERATOR_L);
                        ansValue = 0 - ansValue;
                    }
                    addValue(String.valueOf(ansValue),ansType);
                    clearAns();
                }
                if (size > 0) {
                    if (isNumber(size-1) || values.get(size-1).equals(")")) {
                        // truoc do la so hoac ")"
                        addValue(String.valueOf(OP_SUBTRACTION),TYPE_OPERATOR_L);
                    }
                }
            }
        });

        ImageButton btn_del = (ImageButton) findViewById(R.id.buttonDel);
        btn_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearAns();
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
                clearAns();
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
                if (!ansAvailable) {
                    caculate();
                }
            }
        });

        ImageButton btn_history = (ImageButton) findViewById(R.id.buttonHistory);
        btn_history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // history view
            }
        });
    }


    // gan su kien cho cac nut so
    private void numberButtonClickHandler(int btnNumber) {
        clearAns();
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



    // set gia tri truoc
    private void setAns(double value){
        if (value%1 != 0)
            ansType = TYPE_FLOAT;
        else
            ansType = TYPE_INT;
        ansValue = value;
        ansAvailable = true;
    }

    // xoa gia tri tuoc
    private void clearAns() {
        ansAvailable = false;
        ansValue = 0;
        ansType = null;
    }

    // reset tat ca cac gia tri
    private void clearFields(){
        input = "";
        output = "";
        values.clear();
        types.clear();
        size = 0;
        openingBraces = 0;
        inputChange();
    }

    // them doi tuong vao arraylist sau khi nhap
    private void addValue (String value, String type) {
        values.add(value);
        types.add(type);
        size ++;
        inputChange();
    }

    // chen doi tuong vao arraylist
    private void insertValue (String value, String type, int index) {
        values.add(index, value);
        types.add(index, type);
        size ++;
        inputChange();
    }

    // xoa doi tuong khoi arraylist
    private void deleteValue (int index) {
        values.remove(index);
        types.remove(index);
        size --;
        inputChange();
    }

    // them so
    private void updateNumber_addNumber (int index, String number){
        values.set(index, values.get(index)+number);
        inputChange();
    }

    //
    private void updateNumber_addDot(int index) {
        // them dau '.', chuyen int thanh float
        values.set(index, values.get(index)+".");
        types.set(index,TYPE_FLOAT);
        inputChange();
    }

    // kiem tra gia tri trong arraylist co phai la so
    private boolean isNumber (int index){
        return (types.get(index).equals(TYPE_INT) || types.get(index).equals(TYPE_FLOAT));
    }

}
