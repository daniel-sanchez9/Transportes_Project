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


public class RecuperarPassword extends AppCompatActivity {

    EditText editTextEmail, editTextUsername, editTextNewPassword;
    Button buttonResetPassword;
    DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_password);
        getSupportActionBar().setTitle("Recuperar password");
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        database = new DatabaseHelper(this);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_RecuperarPassword(v);
            }
        });

    }
    public void btn_RecuperarPassword(View view){
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();

        if (email.isEmpty() || username.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Complete todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (database.verificarUsuario(email, username)) {
            if (database.updatePassword(email, newPassword)) {
                Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RecuperarPassword.this, LoginForm.class));
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Usuario o correo electrónico no encontrados", Toast.LENGTH_SHORT).show();
 }
}
}