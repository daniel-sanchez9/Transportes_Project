package co.unipiloto.transportes_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import co.unipiloto.transportes_project.database.DatabaseHelper;

public class SignupForm extends AppCompatActivity {
    EditText txtName,txtUser,txtMail,txtPassword,txtConfirmPassword,txtFecha,txtGenero;
    Spinner spinner;
    Button btnRegistar;
    DatabaseHelper database;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_form);
        getSupportActionBar().setTitle("Signup Form");
        txtName= findViewById(R.id.editTextFullName);
        txtUser = findViewById(R.id.editTextUserName);
        txtMail = findViewById(R.id.editTextEmail);
        txtPassword = findViewById(R.id.editTextPassword);
        txtConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        txtFecha = findViewById(R.id.editTextDateOfBirth);
        txtGenero = findViewById(R.id.editTextGenero);
        spinner = findViewById(R.id.spinnerRole);
        btnRegistar = findViewById(R.id.buttonRegister);

        database = new DatabaseHelper(this);

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
   }
   private void insertData(){
        String name = txtName.getText().toString().trim();
        String user = txtUser.getText().toString().trim();
        String email = txtMail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();
        String fecha = txtFecha.getText().toString().trim();
        String genero = txtGenero.getText().toString().trim();
        String tipoUsuario = spinner.getSelectedItem().toString();

        if(name.isEmpty()){
            txtName.setError("Complete los campos");
            return;
        }else if(user.isEmpty()){
            txtUser.setError("complete los compos");
            return;
        }else if(!isValidEmail(email)){
            Toast.makeText(getApplicationContext(), "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }else if(password.isEmpty()){
            txtPassword.setError("complete los compos");
            return;
        }else if(!isvalidDate(fecha)){
            txtFecha.setError("complete los compos");
            return;
        }else if(genero.isEmpty()){
            txtGenero.setError("complete los campos");
            return;
        }

       if (!password.equals(confirmPassword)) {
           txtConfirmPassword.setError("Las contraseñas no coinciden");
           return;
       }

        if(database.verificarUsuario(email,user)){
            Toast.makeText(getApplicationContext(),"El usuario ya existe",Toast.LENGTH_SHORT).show();
            return;
        }

        database.insertarUsuario(database.getWritableDatabase(),name,user,email,password,fecha,genero,tipoUsuario);

       Toast.makeText(this,"Usuario registrado", Toast.LENGTH_SHORT).show();

       Intent intent = new Intent(SignupForm.this,LoginForm.class);
       startActivity(intent);
       finish();
   }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private boolean isvalidDate(String fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        sdf.setLenient(false);
        try {
            sdf.parse(fecha);
            return true;
        }catch (ParseException e){
            return false;
        }
    }
}