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
        // Hacer que al ejecutar escribamos directamente en el campo "Nombre"
        nombre.requestFocus();
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

        if (stringNombre.isEmpty()) {
            mensaje.append(getString(R.string.error_nombre)).append("\n");
        }
        if (stringApellidos.isEmpty()) {
            mensaje.append(getString(R.string.error_apellidos)).append("\n");
        }
        if (stringEdad.isEmpty() || Integer.parseInt(stringEdad) > 100) {
            mensaje.append(getString(R.string.error_edad)).append("\n");
        }
        if (!radioHombre.isChecked() && !radioMujer.isChecked()) {
            mensaje.append(getString(R.string.error_sexo)).append("\n");
        }
        estadoCivil = spinnerEstadoCivil.getSelectedItem().toString();
        if (spinnerEstadoCivil.getSelectedItemPosition() == 0) {
            mensaje.append(getString(R.string.error_estado_civil)).append("\n");
        }


        return mensaje.toString();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button2) { //btnEnviar
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

            //Si es menor o mayor de edad.
            int edadInt = Integer.parseInt(stringEdad);
            stringEdad = getString((edadInt < 18) ? R.string.menor_edad : R.string.mayor_edad);


            //RadioButtons
            sexo = radioHombre.isChecked() ? radioHombre.getText().toString() : radioMujer.getText().toString();

            //Switch
            hijos = getString(switchHijos.isChecked() ? R.string.con_hijos : R.string.sin_hijos);


            // Mostrar los resultados en Toast
            if (idioma.equals("es")) {
                Toast.makeText(this, stringApellidos + ", " + stringNombre + ", " + stringEdad + ". " + sexo + " " + estadoCivil + " y " + hijos, Toast.LENGTH_LONG).show();
            }
            else if (idioma.equals("en")) {
                Toast.makeText(this, stringApellidos + ", " + stringNombre + ", " + stringEdad + ", " + estadoCivil + " " + sexo + " " + hijos, Toast.LENGTH_LONG).show();

            }
            else if (idioma.equals("fr")) {
                Toast.makeText(this, stringApellidos + ", " + stringNombre + ", " + stringEdad + ". " + sexo + " " + estadoCivil + " et " + hijos, Toast.LENGTH_LONG).show();
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
            nombre.requestFocus(); // Hacer que al ejecutar escribamos directamente en el campo "Nombre"

            // Mostrar mensaje en Toast
            Toast.makeText(this, R.string.borrar_campos, Toast.LENGTH_SHORT).show();



        }
    }
}

