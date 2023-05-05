package mx.tecnm.cdhidalgo.smart_route_bus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firestore.v1.WriteResult



private lateinit var nombre:EditText
private lateinit var apellido_pat:EditText
private lateinit var apellido_mat:EditText
private lateinit var telefono:EditText
private lateinit var combi:EditText
private lateinit var correo:EditText
private lateinit var edad:EditText
private lateinit var contraseña:EditText

private lateinit var btn_regresar:Button
private lateinit var btn_buscar:Button
private lateinit var btn_actualizar:Button
private lateinit var btn_eliminar:Button

private lateinit var doc:QueryDocumentSnapshot
private  var name = ""
private  var paterno = ""
private  var materno = ""
private  var cel = ""
private  var vehiculo = ""
private  var email = ""
private  var age = ""
private  var password = ""
var BaseDeDatos = FirebaseFirestore.getInstance()
private lateinit var auth:FirebaseAuth
class Choferk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choferk)
        nombre = findViewById(R.id.txtnom)
        apellido_pat = findViewById(R.id.txtpat)
        apellido_mat = findViewById(R.id.txtmat)
        telefono = findViewById(R.id.txtce)
        combi = findViewById(R.id.txtcomb)
        correo = findViewById(R.id.txtemail)
        edad = findViewById(R.id.txteda)
        contraseña = findViewById(R.id.txtcontra)
        btn_buscar = findViewById(R.id.btn_buscar)
        btn_actualizar = findViewById(R.id.btn_actualizar)
        btn_eliminar = findViewById(R.id.btn_eliminar)
        btn_regresar = findViewById(R.id.btn_regresar)
        auth = FirebaseAuth.getInstance()
        btn_regresar.setOnClickListener {
            startActivity(Intent(this@Choferk, Mapa2::class.java))
        }
        btn_buscar.setOnClickListener {
            BaseDeDatos.collection("choferes").whereEqualTo("email",correo.text.toString())
                .get().addOnSuccessListener { documents ->
                    doc = documents.first()
                    nombre.setText(doc.data.get("nombre").toString())
                    apellido_pat.setText(doc.data.get("apellido_pat").toString())
                    apellido_mat.setText(doc.data.get("apellido_mat").toString())
                    telefono.setText(doc.data.get("telefono").toString())
                    combi.setText(doc.data.get("combi").toString())
                    edad.setText(doc.data.get("edad").toString())
                    contraseña.setText(doc.data.get("contraseña").toString())
                    Toast.makeText(this@Choferk, "Mostrando registro", Toast.LENGTH_SHORT).show()
                }
        }
        btn_actualizar.setOnClickListener {
            email = correo.text.toString()
            name = nombre.text.toString()
            paterno = apellido_pat.text.toString()
            materno = apellido_mat.text.toString()
            cel = telefono.text.toString()
            vehiculo = combi.text.toString()
            age = edad.text.toString()
            password = contraseña.text.toString()
            // Update an existing document
            // Update an existing document
            val docRef: Task<DocumentSnapshot> = BaseDeDatos.collection("choferes").document(email)
                .get().addOnSuccessListener{ documents ->
                    doc.reference.update("email", email)
                    doc.reference.update("nombre", name)
                    doc.reference.update("apellido_pat", paterno)
                    doc.reference.update("apellido_mat", materno)
                    doc.reference.update("telefono", cel)
                    doc.reference.update("combi", vehiculo)
                    doc.reference.update("contraseña", password)
                    doc.reference.update("edad", age)
                    Toast.makeText(this@Choferk, "Registro actualizado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Choferk, Choferk::class.java))

                }
        }

        btn_eliminar.setOnClickListener {
            BaseDeDatos.collection("choferes").whereEqualTo("email", correo.text.toString())
                .get().addOnSuccessListener { documents ->
                    doc.reference.delete()
                    Toast.makeText(this@Choferk, "Registro Eliminado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Choferk, Choferk::class.java))
                }
        }

    }

}