package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    Button btDividir, btMultiplicar, btSomar, btSubtrair;
    Button btLimpar, btIgual;
    TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResultado = (TextView) findViewById(R.id.tvResultado);

        // Botões Numericos
        bt0 = (Button) findViewById(R.id.bt0);
        bt0.setOnClickListener(onClick_bt0);

        bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(onClick_bt1);

        bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(onClick_bt2);

        bt3 = (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(onClick_bt3);

        bt4 = (Button) findViewById(R.id.bt4);
        bt4.setOnClickListener(onClick_bt4);

        bt5 = (Button) findViewById(R.id.bt5);
        bt5.setOnClickListener(onClick_bt5);

        bt6 = (Button) findViewById(R.id.bt6);
        bt6.setOnClickListener(onClick_bt6);

        bt7 = (Button) findViewById(R.id.bt7);
        bt7.setOnClickListener(onClick_bt7);

        bt8 = (Button) findViewById(R.id.bt8);
        bt8.setOnClickListener(onClick_bt8);

        bt9 = (Button) findViewById(R.id.bt9);
        bt9.setOnClickListener(onClick_bt9);

        // Botões Operação
        btLimpar = (Button) findViewById(R.id.btLimpar);
        btLimpar.setOnClickListener(onClick_btLimpar);

        btIgual = (Button) findViewById(R.id.btIgual);
        btIgual.setOnClickListener(onClick_btIgual);

        btDividir = (Button) findViewById(R.id.btDividir);
        btDividir.setOnClickListener(onClick_btDividir);

        btMultiplicar = (Button) findViewById(R.id.btMultiplicar);
        btMultiplicar.setOnClickListener(onClick_btMultiplicar);

        btSomar = (Button) findViewById(R.id.btSomar);
        btSomar.setOnClickListener(onClick_btSomar);

        btSubtrair = (Button) findViewById(R.id.btSubtrair);
        btSubtrair.setOnClickListener(onClick_btSubtrair);
    }

    @Override
    public void onClick(View v) {}

    OnClickListener onClick_bt0 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "0");
        }
    };

    OnClickListener onClick_bt1 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "1");
        }
    };

    OnClickListener onClick_bt2 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "2");
        }
    };

    OnClickListener onClick_bt3 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "3");
        }
    };

    OnClickListener onClick_bt4 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "4");
        }
    };

    OnClickListener onClick_bt5 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "5");
        }
    };

    OnClickListener onClick_bt6 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "6");
        }
    };

    OnClickListener onClick_bt7 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "7");
        }
    };

    OnClickListener onClick_bt8 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "8");
        }
    };

    OnClickListener onClick_bt9 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "9");
        }
    };

    OnClickListener onClick_btLimpar = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText("");
        }
    };

    OnClickListener onClick_btSomar = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "+");
        }
    };

    OnClickListener onClick_btSubtrair = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "-");
        }
    };

    OnClickListener onClick_btMultiplicar = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "*");
        }
    };

    OnClickListener onClick_btDividir = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tvResultado.setText(tvResultado.getText() + "/");
        }
    };

    OnClickListener onClick_btIgual = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String expressao = tvResultado.getText().toString();
            expressao = expressao.replace("=", "");

            try{
                double resultadoExpressao  = eval(expressao);
                tvResultado.setText(String.valueOf(resultadoExpressao));
            }
            catch (Exception ex){
                tvResultado.setText("Operação inválida");
            }
        }
    };

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}