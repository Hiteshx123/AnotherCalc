package com.example.anothercalc;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class GraphActivity extends AppCompatActivity {

    GraphView graphView;

    boolean radianToggle;

    Context app;

    ListView listView;

    public class startGraphs extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            listView.setAdapter(new ArrayAdapter<>(app, android.R.layout.simple_list_item_1, strings));

            for(int i = 0; i < strings.length; i++){
                String Equation = strings[i];

                DataPoint[] dataPoints = new DataPoint[10000];
                int k = 0;
                for (double l = dataPoints.length * 0.25 * -1; l < dataPoints.length * 0.25; l+= 0.5) {
                    Log.d("TAG", "i" + l);
                    String secondEquation = Equation.replaceAll("X", l + "");
                    dataPoints[k] = new DataPoint(l, new MathematicsParser(secondEquation).evaluate());
                    k++;
                }

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

                Log.d("TAG", series.toString());
                Log.d("TAG", graphView.toString());
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(app, "Datapoint X : " + dataPoint.getX() + " Y : " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });

                graphView.addSeries(series);
            }
            return null;
        }
    }

    public  class startGraph extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            listView.setAdapter(new ArrayAdapter<>(app, android.R.layout.simple_list_item_1, strings));

            DataPoint[] dataPoints = new DataPoint[1000];
            int k = 0;
            for (double i = dataPoints.length * 0.5 * -1; i < dataPoints.length * 0.5; i++) {
                Log.d("TAG", "i" + i);
                String secondEquation = strings[0].replaceAll("X", i + "");
                dataPoints[k] = new DataPoint(i, new MathematicsParser(secondEquation).evaluate());
                k++;
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

            Log.d("TAG", series.toString());
            Log.d("TAG", graphView.toString());
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(app, "Datapoint X : " + dataPoint.getX() + " Y : " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }
            });

            graphView.addSeries(series);
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        try {
            listView = findViewById(R.id.listViewEquations);
            app = this.getApplicationContext();
            graphView = findViewById(R.id.graph);
            graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setMinY(-150);
            graphView.getViewport().setMaxY(150);

            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getViewport().setMinX(4);
            graphView.getViewport().setMaxX(80);

            graphView.getViewport().setScalable(true);
            graphView.getViewport().setScalableY(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                }
                                            });
                    radianToggle = getIntent().getBooleanExtra("radians", false);
            if(getIntent().getStringExtra("Equation") == null){
                String[] Equations = getIntent().getStringArrayExtra("Equations");
                startGraphs startGraphs = new startGraphs();
                startGraphs.execute(Equations);
            } else {
                String Equation = getIntent().getStringExtra("Equation");
                startGraph startGraph = new startGraph();
                startGraph.execute(Equation);

            }
        } catch (Exception E){
            E.printStackTrace();
            finish();
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
                error = "Invalid Expression";
                finish();
            }
            return 0;
        }

        private boolean isNumeral(String numeral) {
            try {
                Double.parseDouble(numeral);
                return true;
            } catch (NumberFormatException E) {
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
                        return factorial((int) a) + b;
                    case "EE":
                        return a * (Math.pow(10, b));
                }
                return 0;
            } catch (ArithmeticException E) {
                error = "Divide By Zero Error";
                finish();
                return 0;
            }
        }

    }

}