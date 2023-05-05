package mx.tecnm.cdhidalgo.smart_route_bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText nombre;
    EditText apellido_pat;
    EditText apellido_mat;
    EditText telefono;
    EditText combi;
    EditText correo;
    EditText edad;
    EditText contraseña;
    Button btn_registrar;
    Button regresar;
    private String name = "";
    private String paterno = "";
    private String materno = "";
    private String cel="";
    private String vehiculo="";
    private String email = "";
    private String  age="";
    private String password = "";
    FirebaseFirestore BaseDeDatos=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        regresar = (Button) findViewById(R.id.btn_regresar);
        nombre = (EditText) findViewById(R.id.txtnombre);
        apellido_pat=(EditText) findViewById(R.id.txtpaterno);
        apellido_mat=(EditText) findViewById(R.id.txtmaterno);
        telefono=(EditText) findViewById(R.id.txtcel);
        combi=(EditText) findViewById(R.id.txtcombi);
        correo=(EditText) findViewById(R.id.txtcorreo);
        edad=(EditText) findViewById(R.id.txtedad);
        contraseña=(EditText) findViewById(R.id.txtcontraseña);
        btn_registrar=(Button) findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                paterno = apellido_pat.getText().toString();
                materno = apellido_mat.getText().toString();
                cel = telefono.getText().toString();
                vehiculo = combi.getText().toString();
                email = correo.getText().toString();
                age = edad.getText().toString();
                password = contraseña.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    if(password.length() >=6){
                        registerUser(email,password);
                    }else{
                        Toast.makeText(Registro.this, "La contraseña debe tener minimo 6 digitos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Registro.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Mapa2.class));
            }
        });
    }
    private void registerUser(String emailUser, String passUser){
        Log.e("error",emailUser);
        Log.e("error",passUser);
        Chofer usuario = new Chofer(name,paterno,materno,cel,vehiculo,email,
                age,password);
        mAuth.createUserWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Registro.this, Mapa2.class));
                        BaseDeDatos.collection("choferes")
                                .document(email.toString())
                                .set(usuario);
                    Toast.makeText(Registro.this, "Chofer Registrado", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Registro.this, "Error al registrar al chofer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

