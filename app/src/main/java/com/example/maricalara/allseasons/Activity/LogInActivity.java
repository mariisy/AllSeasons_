package com.example.maricalara.allseasons.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import com.example.maricalara.allseasons.R;

public class LogInActivity extends AppCompatActivity {

    private Button btnlogin;
    private Intent intent;
    private EditText txtUsername, txtPass;
    private TextInputLayout inputLayoutUname, inputLayoutPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputLayoutUname = (TextInputLayout) findViewById(R.id.input_layout_uName);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_pass);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPass = (EditText) findViewById(R.id.txtPass);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                submitForm();


            }
        });
    }

        private void submitForm() {
            if (!validateUsername()) {
                return;
            }

            if (!validatePassword()) {
                return;
            }

            intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
         }


        private boolean validateUsername(){
            if (txtUsername.getText().toString().trim().isEmpty()){
                inputLayoutUname.setError("Enter Username");
                inputLayoutPass.setError("Enter Password");
                requestFocus(txtUsername);

                return false;
            } else {
                inputLayoutUname.setErrorEnabled(false);
            }
            return true;
        }

        private boolean validatePassword(){
            if (txtPass.getText().toString().trim().isEmpty()){
                inputLayoutPass.setError("Enter Password");
                requestFocus(txtPass);
                return false;
            } else {
                inputLayoutPass.setErrorEnabled(false);
            }
            return true;
        }

        private void requestFocus(View view) {
            if (view.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

        private class MyTextWatcher implements TextWatcher {

            private View view;

            private MyTextWatcher(View view) {
                this.view = view;
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                switch (view.getId()) {
                    case R.id.txtUsername:
                        validateUsername();
                        break;
                    case R.id.txtPass:
                        validatePassword();
                        break;
                }
            }
        }
}
