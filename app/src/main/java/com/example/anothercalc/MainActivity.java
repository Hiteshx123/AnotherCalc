package com.example.anothercalc;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    StringBuilder currentCalcText;

    MathematicsParser parser;

    ArrayList<String> historyData;

    TextView Display;
    TextView Display2;
    TextView Display3;
    TextView Display4;

    Button CE;

    //Numerals
    Button[] numerals;

    Button powerOf;
    Button percent;
    Button decimal;
    Button changeSign;

    String CopyMemory;

    //Operations
    Button equals;
    Button minus;
    Button add;
    Button divide;
    Button multiply;

    //Top Bar
    Button delete;
    Button history;
    Button unit;

    //landscapeButtons

    Button secondFunctions;
    boolean secondToggle = false;

    Button radical;
    Button sin;
    Button cos;
    Button tan;
    Button naturalLog;
    Button baseTenLog;
    Button fractional;
    Button naturalToPowerOf;
    Button toThePowerOf;
    Button pi;
    Button E;
    Button sqrt;
    Button absolute;

    Button copyPaste;

    boolean radianToggle = false;
    Button radian;

    Context CalcContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentCalcText = new StringBuilder();
        initializeButton();
        CalcContext = this.getBaseContext();
        historyData = new ArrayList<>();
        if (getIntent().getStringArrayListExtra("History") != null && getIntent().getStringExtra("CurrentData") != null) {
            historyData = getIntent().getStringArrayListExtra("History");
            currentCalcText.append(getIntent().getStringExtra("CurrentData"));
            refreshDisplay();
        }
    }

    private String convertStandardToParsable(String expression) {
        String[] splitExpression = expression.split("\n");

        return splitExpression[splitExpression.length - 1];
    }


    private void refreshDisplay() {
        StringBuilder currentString = new StringBuilder(currentCalcText.toString().replaceAll(" ", "").replaceAll("S", "").replaceAll("A", ""));
        for (int i = 0; i < currentString.length(); i++) {
            char token = currentString.charAt(i);
            if (token >= 'a' && token <= 'z')
                if (currentString.charAt(i - 1) == '0')
                    currentString.deleteCharAt(i - 1);
            if (token == '!') {
                if (currentString.charAt(i + 1) == '0')
                    currentString.deleteCharAt(i + 1);
            }
        }
        System.out.println(currentString.toString());
        CharSequence displayText = currentString.toString();
        CharSequence error = "Maximum characters reached";
        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            if (displayText.length() > 18) {
                toast.show();
            } else
                Display.setText(displayText);
        } else {
            if (displayText.length() > 50) {
                toast.show();
            } else
                Display.setText(displayText);
        }
    }

    public static String superscript(String str) {
        switch (str) {
            case "0":
                return "⁰";
            case "1":
                return "¹";
            case "2":
                return "²";
            case "3":
                return "³";
            case "4":
                return "⁴";
            case "5":
                return "⁵";
            case "6":
                return "⁶";
            case "7":
                return "⁷";
            case "8":
                return "⁸";
            case "9":
                return "⁹";
            case "-1":
                return "⁻¹";
            case "-2":
                return "⁻²";
            case "-3":
                return "⁻³";
            case "-4":
                return "⁻⁴";
            case "-5":
                return "⁻⁵";
            case "-6":
                return "⁻⁶";
            case "-7":
                return "⁻⁷";
            case "-8":
                return "⁻⁸";
            case "-9":
                return "⁻⁹";
        }
        return "";
    }


    private void initializeButton() {
        Display = findViewById(R.id.textView10);
        Display2 = findViewById(R.id.textView9);
        Display3 = findViewById(R.id.textView8);
        Display4 = findViewById(R.id.textView);

        numerals = new Button[10];
        numerals[0] = findViewById(R.id.button10);
        numerals[1] = findViewById(R.id.button4);
        numerals[2] = findViewById(R.id.button9);
        numerals[3] = findViewById(R.id.button11);
        numerals[4] = findViewById(R.id.button3);
        numerals[5] = findViewById(R.id.button8);
        numerals[6] = findViewById(R.id.button12);
        numerals[7] = findViewById(R.id.button6);
        numerals[8] = findViewById(R.id.button7);
        numerals[9] = findViewById(R.id.button2);

        CE = findViewById(R.id.button19);

        powerOf = findViewById(R.id.button18);
        percent = findViewById(R.id.button20);
        decimal = findViewById(R.id.button5);
        changeSign = findViewById(R.id.button);

        equals = findViewById(R.id.button17);
        minus = findViewById(R.id.button15);
        add = findViewById(R.id.button16);
        multiply = findViewById(R.id.button14);
        divide = findViewById(R.id.button13);

        history = findViewById(R.id.button21);
        delete = findViewById(R.id.button24);
        unit = findViewById(R.id.button22);

        sin = findViewById(R.id.button31);
        tan = findViewById(R.id.button39);
        cos = findViewById(R.id.button36);

        baseTenLog = findViewById(R.id.button35);
        naturalLog = findViewById(R.id.button30);

        fractional = findViewById(R.id.button40);

        radical = findViewById(R.id.button38);

        pi = findViewById(R.id.button33);
        E = findViewById(R.id.button42);

        naturalToPowerOf = findViewById(R.id.button25);
        toThePowerOf = findViewById(R.id.button34);

        radian = findViewById(R.id.button37);

        sqrt = findViewById(R.id.button41);

        secondFunctions = findViewById(R.id.button32);

        copyPaste = findViewById(R.id.button29);
        absolute = findViewById(R.id.button26);
        numerals[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(0);
                refreshDisplay();
            }
        });

        numerals[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(1);
                refreshDisplay();
            }
        });

        numerals[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(2);
                refreshDisplay();
            }
        });

        numerals[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(3);
                refreshDisplay();
            }
        });

        numerals[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(4);
                refreshDisplay();
            }
        });

        numerals[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(5);
                refreshDisplay();
            }
        });

        numerals[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(6);
                refreshDisplay();
            }
        });

        numerals[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(7);
                refreshDisplay();
            }
        });

        numerals[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(8);
                refreshDisplay();
            }
        });

        numerals[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(9);
                refreshDisplay();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(" ");
                currentCalcText.append("+");
                currentCalcText.append(" ");
                refreshDisplay();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(" ");
                currentCalcText.append("-");
                currentCalcText.append(" ");
                refreshDisplay();
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(" ");
                currentCalcText.append("*");
                currentCalcText.append(" ");
                refreshDisplay();
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(" ");
                currentCalcText.append("/");
                currentCalcText.append(" ");
                refreshDisplay();
            }
        });

        CE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText = new StringBuilder();
                refreshDisplay();
            }
        });

        CE.setLongClickable(true);

        CE.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Display2.setText("");
                Display3.setText("");
                Display4.setText("");
                currentCalcText = new StringBuilder();
                refreshDisplay();
                return true;
            }
        });

        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parser = new MathematicsParser(currentCalcText.toString());
                System.out.println("CURRENT CALC = " + currentCalcText.toString());
                double answer = parser.evaluate();
                StringBuilder savedData = new StringBuilder();
                savedData.append(Display.getText());
                savedData.append("\n");
                Display4.setText(Display3.getText());
                Display3.setText(Display2.getText());
                Display2.setText(Display.getText());
                currentCalcText = new StringBuilder();
                if (parser.error == null) {
                    savedData.append(answer);
                    currentCalcText.append(answer);
                } else {
                    savedData.append(parser.error);
                    currentCalcText.append(parser.error);
                }
                System.out.println(savedData.toString());
                historyData.add(savedData.toString());
                System.out.println(historyData.get(historyData.size() - 1));
                refreshDisplay();
            }
        });

        equals.setLongClickable(true);

        equals.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(CalcContext, GraphActivity.class);
                intent.putExtra("radians", radianToggle);
                String[] Equations;
                if(currentCalcText.toString().contains(",")) {
                    Equations = currentCalcText.toString().split(",");
                    intent.putExtra("Equations", Equations);
                } else {
                    intent.putExtra("Equation", currentCalcText.toString());
                }
                startActivity(intent);
                return true;

            }
        });

        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(".");
                refreshDisplay();
            }
        });

        changeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int lastSpace = currentCalcText.lastIndexOf(" ");
                    if (currentCalcText.charAt(lastSpace + 1) != '-') {
                        currentCalcText.insert(lastSpace + 1, '-');
                    } else {
                        currentCalcText.deleteCharAt(lastSpace + 1);
                    }
                    refreshDisplay();
                } catch (StringIndexOutOfBoundsException E) {
                    refreshDisplay();
                }
            }
        });

        powerOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalcText.append(" ");
                currentCalcText.append("^");
                currentCalcText.append(" ");
                refreshDisplay();
            }
        });

        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCalcText.append(" ");
                currentCalcText.append("(");
                currentCalcText.append(" ");
                refreshDisplay();
            }
        });

        percent.setLongClickable(true);

        percent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalcText.append(" ");
                currentCalcText.append(")");
                currentCalcText.append(" ");
                refreshDisplay();
                return true;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (currentCalcText.charAt(currentCalcText.length() - 1) == ' ') {
                        currentCalcText.delete(currentCalcText.length() - 3, currentCalcText.length() - 1);
                    } else {
                        currentCalcText.deleteCharAt(currentCalcText.length() - 1);
                    }
                    refreshDisplay();
                } catch (StringIndexOutOfBoundsException E) {
                    E.printStackTrace();
                }
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(CalcContext, HistoryActivity.class);
                historyIntent.putExtra("History", historyData);
                startActivity(historyIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unitIntent = new Intent(CalcContext, UnitActivity.class);
                startActivity(unitIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            copyPaste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentCalcText.append(CopyMemory);
                    refreshDisplay();
                }
            });

            copyPaste.setLongClickable(true);

            copyPaste.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CopyMemory = currentCalcText.toString();
                    refreshDisplay();
                    return true;
                }
            });

            sin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("0 sin ");
                    } else
                        currentCalcText.append("0 sin-1 ");
                    refreshDisplay();
                }
            });

            tan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("0 tan ");
                    } else
                        currentCalcText.append("0 tan-1 ");
                    refreshDisplay();
                }
            });

            cos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("0 cos ");
                    } else
                        currentCalcText.append("0 cos-1 ");
                    refreshDisplay();
                }
            });

            baseTenLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("0 log ");
                    } else
                        currentCalcText.append("0 cosh ");
                    refreshDisplay();
                }
            });

            naturalLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("0 ln ");
                    } else
                        currentCalcText.append("0 sinh ");
                    refreshDisplay();
                    ;
                }
            });

            fractional.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("( 1 / ");
                    } else
                        currentCalcText.append("0 tanh ");
                    refreshDisplay();
                }
            });

            radical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append(" ^√ ");
                    } else
                        currentCalcText.append("3 ^√ ");
                    refreshDisplay();
                }
            });

            pi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("π");
                    } else
                        currentCalcText.append(" ^ 3");
                    refreshDisplay();
                }
            });

            E.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("E");
                    } else
                        currentCalcText.append(" ! 0");
                    refreshDisplay();
                }
            });

            naturalToPowerOf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("E ^");
                    } else
                        currentCalcText.append("10 ^ ");
                    refreshDisplay();
                }
            });

            toThePowerOf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append(" ^ 2");
                    } else
                        currentCalcText.append("X");
                    refreshDisplay();
                }
            });

            radian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!radianToggle) {
                        radianToggle = true;
                        radian.setText("DEG");
                    } else {
                        radianToggle = false;
                        radian.setText("RAD");
                    }
                }
            });

            sqrt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        currentCalcText.append("2 ^√ ");
                    } else
                        currentCalcText.append(" EE ");
                    refreshDisplay();
                }
            });

            absolute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!secondToggle) {
                        currentCalcText.append(" A| ");
                        refreshDisplay();
                    } else {
                        currentCalcText.append(",");
                        refreshDisplay();
                    }
                }
            });

            absolute.setLongClickable(true);

            absolute.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    currentCalcText.append(" S| ");
                    refreshDisplay();
                    return true;
                }
            });

            secondFunctions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!secondToggle) {
                        secondToggle = true;
                        secondFunctions.setText("<- 1st");
                        sin.setText("sin⁻¹");
                        tan.setText("tan⁻¹");
                        cos.setText("cos⁻¹");
                        baseTenLog.setText("cosh");
                        naturalLog.setText("sinh");
                        fractional.setText("tanh");
                        radical.setText("3^√");
                        pi.setText("^3");
                        E.setText("!");
                        sqrt.setText("EE");
                        naturalToPowerOf.setText("10^");
                        toThePowerOf.setText("X");
                        absolute.setText(",");
                    } else {
                        secondFunctions.setText("-> 2nd");
                        secondToggle = false;
                        sin.setText("sin");
                        tan.setText("tan");
                        cos.setText("cos");
                        baseTenLog.setText("log");
                        naturalLog.setText("ln");
                        fractional.setText("1/x");
                        radical.setText("^√");
                        pi.setText("π");
                        E.setText("E");
                        sqrt.setText("2^√");
                        naturalToPowerOf.setText("E^");
                        toThePowerOf.setText("x^2");
                        absolute.setText("|X|");
                    }
                }
            });

        }
    }


    public class MathematicsParser extends StringTokenizer {

        public String error;

        private double E = 2.71828;
        private double PI = 3.14159;

        public MathematicsParser(String expression) {
            super(expression, " ");
        }

        public double evaluate() {
            try {
                Stack<Double> values = new Stack<>();

                Stack<String> ops = new Stack<>();

                while (this.hasMoreElements()) {
                    String token = this.nextToken();
                    if (token.equalsIgnoreCase("E"))
                        token = E + "";
                    else if (token.equalsIgnoreCase("π"))
                        token = PI + "";

                    System.out.println(token);
                    if (token.equalsIgnoreCase(" "))
                        continue;

                    if (isNumeral(token)) {
                        if (token.startsWith("0") && !token.contains(".") && token.length() > 1)
                            throw new Exception();
                        else
                            values.push(Double.parseDouble(token));
                    } else if (token.equalsIgnoreCase("(") || token.equalsIgnoreCase("A|"))
                        ops.push(token);
                    else if (token.equalsIgnoreCase("S|")) {
                        while (!ops.peek().equalsIgnoreCase("A|")) {
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                        }
                        double answer = values.pop();
                        if (answer < 0) {
                            answer *= -1;
                            values.push(answer);
                        } else {
                            values.push(answer);
                        }
                        ops.pop();
                    } else if (token.equalsIgnoreCase(")")) {
                        while (!ops.peek().equalsIgnoreCase("("))
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                        ops.pop();
                    } else if (isFunction(token)) {
                        while (!ops.empty() && hasPrecedence(token, ops.peek()))
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                        ops.push(token);
                    } else if (isOperator(token)) {
                        while (!ops.empty() && hasPrecedence(token, ops.peek()))
                            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                        ops.push(token);
                    }


                }

                while (!ops.empty())
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                return values.pop();
            } catch (Exception E) {
                E.printStackTrace();
                error = "Invalid Expression";
            }
            return 0;
        }

        private boolean isNumeral(String numeral) {
            try {
                Double.parseDouble(numeral);
                return true;
            } catch (NumberFormatException E) {
                E.printStackTrace();
                return false;
            }
        }

        private boolean isOperator(String token) {
            if (token.equalsIgnoreCase("^") ||
                    token.equalsIgnoreCase("^√") ||
                    token.equalsIgnoreCase("+") ||
                    token.equalsIgnoreCase("-") ||
                    token.equalsIgnoreCase("*") ||
                    token.equalsIgnoreCase("/")) {
                return true;
            }
            return false;
        }

        public Double calculate(Double base, Double n) {
            return Math.pow(Math.E, Math.log(base) / n);
        }

        public int factorial(int number) {
            int fact = 1;
            for (int i = 1; i <= number; i++) {
                fact = fact * i;
            }
            return fact;
        }

        private boolean isFunction(String token) {
            if (token.equalsIgnoreCase("sin") ||
                    token.equalsIgnoreCase("tan") ||
                    token.equalsIgnoreCase("cos") ||
                    token.equalsIgnoreCase("log") ||
                    token.equalsIgnoreCase("ln") ||
                    token.equalsIgnoreCase("^√") ||
                    token.equalsIgnoreCase("sinh") ||
                    token.equalsIgnoreCase("tanh") ||
                    token.equalsIgnoreCase("cosh") ||
                    token.equalsIgnoreCase("sin-1") ||
                    token.equalsIgnoreCase("tan-1") ||
                    token.equalsIgnoreCase("cos-1") ||
                    token.equalsIgnoreCase("sinh-1") ||
                    token.equalsIgnoreCase("tanh-1") ||
                    token.equalsIgnoreCase("cosh-1") ||
                    token.equalsIgnoreCase("!") ||
                    token.equalsIgnoreCase("EE")) {
                return true;
            }
            return false;
        }

        public boolean hasPrecedence(String op1, String op2) {
            if (op2.equalsIgnoreCase("(") || op2.equalsIgnoreCase(")") || op2.equalsIgnoreCase("A|") || op2.equalsIgnoreCase("E|"))
                return false;
            if (op1.equalsIgnoreCase("sin") ||
                    op1.equalsIgnoreCase("tan") ||
                    op1.equalsIgnoreCase("cos") ||
                    op1.equalsIgnoreCase("log") ||
                    op1.equalsIgnoreCase("ln") ||
                    op1.equalsIgnoreCase("^√") ||
                    op1.equalsIgnoreCase("sinh") ||
                    op1.equalsIgnoreCase("tanh") ||
                    op1.equalsIgnoreCase("cosh") ||
                    op1.equalsIgnoreCase("sin-1") ||
                    op1.equalsIgnoreCase("tan-1") ||
                    op1.equalsIgnoreCase("cos-1") ||
                    op1.equalsIgnoreCase("sinh-1") ||
                    op1.equalsIgnoreCase("tanh-1") ||
                    op1.equalsIgnoreCase("cosh-1") ||
                    op1.equalsIgnoreCase("!") ||
                    op1.equalsIgnoreCase("EE"))
                return false;
            if (op1.equalsIgnoreCase("^") && !op2.equalsIgnoreCase("^"))
                return false;
            if ((op1.equalsIgnoreCase("*") || op1.equalsIgnoreCase("/")) && (op2.equalsIgnoreCase("+") || op2.equalsIgnoreCase("-")))
                return false;
            else
                return true;
        }

        public double applyOp(String op, double b, double a) {
            try {
                switch (op) {
                    case "+":
                        return a + b;
                    case "-":
                        return a - b;
                    case "*":
                        double answer = a * b;
                        if (answer == Double.NEGATIVE_INFINITY || answer == Double.POSITIVE_INFINITY)
                            throw new ArithmeticException();
                        else return answer;
                    case "/":
                        double answer1 = a / b;
                        if (answer1 == Double.NEGATIVE_INFINITY || answer1 == Double.POSITIVE_INFINITY)
                            throw new ArithmeticException();
                        else return answer1;
                    case "^":
                        return Math.pow(a, b);
                    case "sin":
                        if (!radianToggle)
                            return Math.sin(Math.toRadians(b)) + a;
                        else
                            return Math.sin(Math.toDegrees(b)) + a;
                    case "tan":
                        if (!radianToggle)
                            return Math.tan(Math.toRadians(b)) + a;
                        else
                            return Math.tan(Math.toDegrees(b)) + a;
                    case "cos":
                        if (!radianToggle)
                            return Math.cos(Math.toRadians(b)) + a;
                        else
                            return Math.cos(Math.toDegrees(b)) + a;
                    case "sin-1":
                        if (!radianToggle)
                            return Math.asin(Math.toRadians(b)) + a;
                        else
                            return Math.asin(Math.toDegrees(b)) + a;
                    case "tan-1":
                        if (!radianToggle)
                            return Math.atan(Math.toRadians(b)) + a;
                        else
                            return Math.atan(Math.toDegrees(b)) + a;
                    case "cos-1":
                        if (!radianToggle)
                            return Math.acos(Math.toRadians(b)) + a;
                        else
                            return Math.acos(Math.toDegrees(b)) + a;
                    case "sinh":
                        if (!radianToggle)
                            return Math.sinh(Math.toRadians(b)) + a;
                        else
                            return Math.sinh(Math.toDegrees(b)) + a;
                    case "tanh":
                        if (!radianToggle)
                            return Math.tanh(Math.toRadians(b)) + a;
                        else
                            return Math.tanh(Math.toDegrees(b)) + a;
                    case "cosh":
                        if (!radianToggle)
                            return Math.cosh(Math.toRadians(b)) + a;
                        else
                            return Math.cosh(Math.toDegrees(b)) + a;
                    case "log":
                        return Math.log10(b) + a;
                    case "ln":
                        return Math.log(b) + a;
                    case "^√":
                        return calculate(b, a);
                    case "!":
                        System.out.println("Fact  " + factorial((int) a));
                        return factorial((int) a) + b;
                    case "EE":
                        return a * (Math.pow(10, b));
                }
                return 0;
            } catch (ArithmeticException E) {
                E.printStackTrace();
                error = "Divide By Zero Error";
                return 0;
            }
        }

    }

}

