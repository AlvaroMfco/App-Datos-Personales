package es.studium.practica2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBorrar;
    Button btnEnviar;
    EditText nombre;
    EditText apellidos;
    EditText edad;
    RadioButton radioHombre;
    RadioButton radioMujer;
    Switch switchHijos;
    Spinner spinnerEstadoCivil;
    TextView tvEtiqueta;

    String stringNombre;
    String stringApellidos;
    String stringEdad;
    String sexo;
    String estadoCivil;
    String hijos;
    String idioma = Locale.getDefault().getLanguage(); // Para saber el idioma del dispositivo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Buttons
        btnBorrar = findViewById(R.id.button);
        btnEnviar = findViewById(R.id.button2);
        btnBorrar.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);

        //EditText
        nombre = findViewById(R.id.editTextText);
        nombre.requestFocus(); // Hacer que al ejecutar escribamos directamente en el campo "Nombre"
        apellidos = findViewById(R.id.editTextText2);
        edad = findViewById(R.id.editTextText3);

        //RadioButtons
        radioHombre = findViewById(R.id.radioButton6);
        radioMujer = findViewById(R.id.radioButton5);

        //Switch
        switchHijos = findViewById(R.id.switch1);

        //TextView
        tvEtiqueta = findViewById(R.id.etiqueta);

        //Spinner
        spinnerEstadoCivil = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estado_civil, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEstadoCivil.setAdapter(adapter);
    }

    //MENSAJES DE ERROR
    public String validarVacios() {
        StringBuilder mensaje = new StringBuilder();
        stringNombre = nombre.getText().toString();
        stringApellidos = apellidos.getText().toString();
        stringEdad = edad.getText().toString();
        if (idioma.equals("es")) { //Si el idioma es español
            if (stringNombre.isEmpty()) {
                mensaje.append("Debe introducir un nombre\n");
            }
            if (stringApellidos.isEmpty()) {
                mensaje.append("Debe introducir un apellido\n");
            }
            if (stringEdad.isEmpty() || Integer.parseInt(stringEdad) > 100) {
                mensaje.append("Debe introducir una edad válida\n");
            }
            if (!radioHombre.isChecked() && !radioMujer.isChecked()) {
                mensaje.append("Debe seleccionar un sexo\n");
            }
            estadoCivil = spinnerEstadoCivil.getSelectedItem().toString();
            if (estadoCivil.equals("Estado Civil")) {
                mensaje.append("Debe seleccionar un estado civil\n");
            }
        }
        else if (idioma.equals("en")) { //Si el idioma es inglés
            if (stringNombre.isEmpty()) {
                mensaje.append("Name Required\n");
            }
            if (stringApellidos.isEmpty()) {
                mensaje.append("Last Name Required\n");
            }
            if (stringEdad.isEmpty() || Integer.parseInt(stringEdad) > 100) {
                mensaje.append("Age Required\n");
            }
            if (!radioHombre.isChecked() && !radioMujer.isChecked()) {
                mensaje.append("Gender Required\n");
            }
            estadoCivil = spinnerEstadoCivil.getSelectedItem().toString();
            if (estadoCivil.equals("Marital Status")) {
                mensaje.append("Marital Status Required\n");
            }
        }
        return mensaje.toString(); // Retornar el mensaje acumulado
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button2) {
            // Limpiar mensaje
            tvEtiqueta.setText("");

            // Validar campos
            String mensajeError = validarVacios();
            if (!mensajeError.isEmpty()) {
                tvEtiqueta.setText(mensajeError); // Mostrar mensaje de error en la Etiqueta
                return; // Salir si hay campos vacíos
            }

            // Asignar valores después de la verificación
            stringEdad = edad.getText().toString();

            int edadInt = Integer.parseInt(stringEdad);
            if (idioma.equals("es")) {
                stringEdad = (edadInt < 18) ? "menor de edad" : "mayor de edad";
            } else if (idioma.equals("en")) {
                stringEdad = (edadInt < 18) ? "Under-age" : "Adult";
            }


            //RadioButtons
            sexo = radioHombre.isChecked() ? radioHombre.getText().toString() : radioMujer.getText().toString();

            //Switch
            if (idioma.equals("es")) {
                hijos = switchHijos.isChecked() ? "con hijos" : "sin hijos";
            } else if (idioma.equals("en")) {
                hijos = switchHijos.isChecked() ? "with children" : "without children";
            }

            // Mostrar los resultados en Toast
            if (idioma.equals("es")) {
                Toast.makeText(this, stringApellidos + ", " + stringNombre + ", " + stringEdad + ", " + sexo + " " + estadoCivil + " y " + hijos, Toast.LENGTH_SHORT).show();
            }
            else if (idioma.equals("en")) {
                Toast.makeText(this, stringApellidos + ", " + stringNombre + ", " + stringEdad + ", " + estadoCivil + " " + sexo + " and " + hijos, Toast.LENGTH_SHORT).show();

            }
        }

        else if (view.getId() == R.id.button) { // btnBorrar
            // Limpiar todos los campos
            nombre.setText("");
            apellidos.setText("");
            edad.setText("");
            radioHombre.setChecked(false);
            radioMujer.setChecked(false);
            switchHijos.setChecked(false);
            spinnerEstadoCivil.setSelection(0); // Spinner en "Estado Civil"
            tvEtiqueta.setText(""); // Limpiar la etiqueta
            Toast.makeText(this, "Campos borrados", Toast.LENGTH_SHORT).show();
        }
    }
}
