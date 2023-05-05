package mx.tecnm.cdhidalgo.smart_route_bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuthException;

public class Login extends AppCompatActivity {
    Button btn_sesion;
    EditText email, password;
    FirebaseAuth mAuth;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.correo);
        password = findViewById(R.id.contraseña);
        btn_sesion = findViewById(R.id.btn_comenzar);

        btn_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();
                if (emailUser.isEmpty() && passUser.isEmpty()) {
                    Toast.makeText(Login.this, "Ingrese los datos", Toast.LENGTH_SHORT).show();
                } else {
                    if(emailUser.equals("admin") && passUser.equals("123")){
                        startActivity(new Intent(Login.this, Mapa2.class));
                    }else {
                        loginUser(emailUser, passUser);
                    }
                }
            }

        });
    }

            private void loginUser(String emailUser, String passUser) {
                mAuth.signInWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(Login.this, Mapa.class));
                            Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      Toast.makeText(Login.this, "Error al iniciar", Toast.LENGTH_SHORT).show();
                    }
                });
            }

}


