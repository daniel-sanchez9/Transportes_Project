package co.unipiloto.transportes_project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.database.DatabaseHelper;

public class LoginForm extends AppCompatActivity {
    EditText txtEmail , txtPassword;
    DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login Form");

        txtEmail = findViewById(R.id.etEmail);
        txtPassword = findViewById(R.id.etPassword);

        databaseHelper = new DatabaseHelper(this);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login(v);
            }
        });
    }

    public void btn_login(View view) {
        String strEmail = txtEmail.getText().toString().trim();
        String strPassword = txtPassword.getText().toString().trim();

        if (strEmail.isEmpty()){
            Toast.makeText(this,"Ingrese Email",Toast.LENGTH_SHORT).show();
        }else if (strPassword.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }else {
            if (databaseHelper.checkUser(strEmail,strPassword)){
                String userType = databaseHelper.getUserType(strEmail);
                txtEmail.setText("");
                txtPassword.setText("");
                Toast.makeText(this,"login ejecutado",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), co.unipiloto.transportes_project.main.MainPropietarioCarga.class);
                intent.putExtra("email", strEmail);


                ejecutarMain(userType, intent);
            }else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void ejecutarMain(String userType, Intent intent){
        // Determinar qué actividad iniciar en función del tipo de usuario
        switch (userType) {
            case "Propietario de carga":
                intent.setClass(getApplicationContext(), co.unipiloto.transportes_project.main.MainPropietarioCarga.class);
                break;
            case "Propietario de vehiculo":
                intent.setClass(getApplicationContext(), co.unipiloto.transportes_project.main.mainPropietarioVehiculo.class);
                break;
            case "Conductor":
                intent.setClass(getApplicationContext(), co.unipiloto.transportes_project.main.MainConductor.class);
                break;
            default:
                // En caso de un tipo de usuario desconocido, iniciar la actividad predeterminada
                intent.setClass(getApplicationContext(), LoginForm.class);
                break;
        }

        startActivity(intent);
    }
    public void btn_signupForm(View view){
        startActivity(new Intent(getApplicationContext(),SignupForm.class));
    }
    public void btnRecuperarContraseña(View view){
        startActivity(new Intent(getApplicationContext(),RecuperarPassword.class));
    }
}