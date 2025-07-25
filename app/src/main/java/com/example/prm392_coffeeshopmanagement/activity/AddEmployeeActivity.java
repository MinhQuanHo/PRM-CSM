package com.example.prm392_coffeeshopmanagement.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_coffeeshopmanagement.R;
import com.example.prm392_coffeeshopmanagement.entity.User;
import com.example.prm392_coffeeshopmanagement.viewmodel.EmployeeViewModel;

public class AddEmployeeActivity extends BaseActivity {

    private EditText edtFullName, edtUserName, edtEmail, edtPhoneNumber, edtPassword, edtConfirmPassword;
    private Spinner spPosition;
    private Button btnSaveEmployee;
    private EmployeeViewModel employeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_employee);

        edtFullName = findViewById(R.id.edtFullName);
        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        spPosition = findViewById(R.id.spPosition);
        btnSaveEmployee = findViewById(R.id.btnSaveEmployee);

        employeeViewModel = new EmployeeViewModel(getApplication());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_array_employee, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPosition.setAdapter(adapter);

        btnSaveEmployee.setOnClickListener(v -> onSaveEmployeeClicked());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_employee;
    }

    private void onSaveEmployeeClicked() {
        if (!validateRequiredFields() || !validatePasswordMatch() || !validateEmailFormat()
                || !validatePhoneFormat()) {
            return;
        }

        String userName = edtUserName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();

        if (employeeViewModel.isUsernameExists(userName)) {
            edtUserName.setError(getString(R.string.employees_validation_username_exists));
            return;
        }

        if (employeeViewModel.isEmailExists(email)) {
            edtEmail.setError(getString(R.string.employees_validation_email_exists));
            return;
        }

        if (employeeViewModel.isPhoneExists(phoneNumber)) {
            edtPhoneNumber.setError(getString(R.string.employees_validation_phone_exists));
            return;
        }

        String fullName = edtFullName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String position = spPosition.getSelectedItem().toString();
        User newEmployee = new User();
        newEmployee.setFullName(fullName);
        newEmployee.setUserName(userName);
        newEmployee.setEmail(email);
        newEmployee.setPhoneNumber(phoneNumber);
        newEmployee.setPassword(password);
        newEmployee.setPosition(position);
        employeeViewModel.addEmployee(newEmployee);
        Toast.makeText(this, "Employee added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateRequiredFields() {
        if (TextUtils.isEmpty(edtFullName.getText())) {
            edtFullName.setError(getString(R.string.employees_validation_empty_fields));
            return false;
        }
        if (TextUtils.isEmpty(edtUserName.getText())) {
            edtUserName.setError(getString(R.string.employees_validation_empty_fields));
            return false;
        }
        if (TextUtils.isEmpty(edtEmail.getText())) {
            edtEmail.setError(getString(R.string.employees_validation_empty_fields));
            return false;
        }
        if (TextUtils.isEmpty(edtPhoneNumber.getText())) {
            edtPhoneNumber.setError(getString(R.string.employees_validation_empty_fields));
            return false;
        }
        if (TextUtils.isEmpty(edtPassword.getText())) {
            edtPassword.setError(getString(R.string.employees_validation_empty_fields));
            return false;
        }
        if (TextUtils.isEmpty(edtConfirmPassword.getText())) {
            edtConfirmPassword.setError(getString(R.string.employees_validation_empty_fields));
            return false;
        }
        return true;
    }

    private boolean validatePasswordMatch() {
        if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.employees_validation_password_mismatch));
            return false;
        }
        return true;
    }

    private boolean validateEmailFormat() {
        String email = edtEmail.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError(getString(R.string.employees_validation_invalid_email));
            return false;
        }
        return true;
    }

    private boolean validatePhoneFormat() {
        String phone = edtPhoneNumber.getText().toString().trim();
        if (!phone.matches("\\d{10}")) {
            edtPhoneNumber.setError(getString(R.string.employees_validation_invalid_phone));
            return false;
        }
        return true;
    }
}
