package view;

import DAO.HouseDAO;
import DAO.WandDAO;
import DAO.WizardDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.House;
import model.Wand;
import model.Wizard;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WizardViewFX {

    //Elementos visuales
    private final BorderPane root = new BorderPane();
    private VBox panelDerecho;

    //Tabla y datos de producto
    private final TableView<Wizard> tabla = new TableView<>();
    private final ObservableList<Wizard> datos = FXCollections.observableArrayList();

    //Formulario (Wizard)
    private final TextField txtId = new TextField();
    private final TextField txtNombre = new TextField();
    private final TextField txtEdad = new TextField();
    private final TextField txtWood = new TextField();
    private final TextField txtCore = new TextField();
    private final TextField txtLength = new TextField();

    //ComboHouse / Founder
    private final Label lblFounder = new Label("---");
    private final ComboBox<House> comboHouse = new ComboBox<>();


    // Caché en memoria: id_wand -> wand / id_house -> house
    private final Map<Integer, Wand> cacheWand = new HashMap<>();
    private final Map<Integer, House> cacheHouse = new HashMap<>();

    // Botones CRUD
    private final Button btnNuevo    = new Button("Nuevo");
    private final Button btnGuardar  = new Button("Guardar");
    private final Button btnBorrar   = new Button("Borrar");
    private final Button btnRecargar = new Button("Recargar");

    // Búsqueda
    private final TextField txtBuscar          = new TextField();
    private final Button    btnBuscar          = new Button("Buscar");
    private final Button    btnLimpiarBusqueda = new Button("Limpiar");

    // DAO (acceso a BD)
    private WizardDAO wizardDAO;
    private WandDAO wandDAO;
    private HouseDAO houseDAO;

    private List<Wizard> wizards;
    private List<Wand> wands;
    private List<House> houses;

    Integer indexHouse;
    Integer indexWand;

    public WizardViewFX() {
        try{
            this.wizardDAO = new WizardDAO();
            this.wandDAO = new WandDAO();
            this.houseDAO = new HouseDAO();
        }catch (Exception e){
            mostrarError("Error haciendo DAOs",e);
        }
        configurarTabla();
        configurarFormulario();
        configurarEventos();
        SplitPane splitPane = new SplitPane();

        // Añadimos: Izquierda (Tabla), Derecha (Panel Formulario)
        splitPane.getItems().addAll(tabla, panelDerecho);

        // Establecemos la posición inicial del divisor (65% tabla, 35% form)
        splitPane.setDividerPositions(0.65);

        // FIX: Fija el panel derecho (el formulario) para que no sea redimensionable por el usuario
        SplitPane.setResizableWithParent(panelDerecho, false);

        // Añadimos el SplitPane al centro del BorderPane (root) de la ventana principal
        root.setCenter(splitPane);

        recargarDatos();
    }

    public Parent getRoot() {
        return root;
    }

    private void configurarTabla() {

        TableColumn<Wizard, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(w ->
                new javafx.beans.property.SimpleIntegerProperty(w.getValue().getId()));

        TableColumn<Wizard, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(w ->
                new javafx.beans.property.SimpleStringProperty(w.getValue().getName()));

        TableColumn<Wizard, String> colEdad = new TableColumn<>("Edad");
        colEdad.setCellValueFactory(w ->
                new javafx.beans.property.SimpleStringProperty(w.getValue().getAge()+"")); //entero a string

        // ===== Columnas “placeholder” para House =====
        TableColumn<Wizard, String> colHouse= new TableColumn<>("House");
        colHouse.setCellValueFactory(w -> {
            House house = cacheHouse.get(w.getValue().getHouseId());
            String valor = (house != null) ? house.getName() : "";
            return new javafx.beans.property.SimpleStringProperty(valor);
        });

        // ===== Columnas “placeholder” para Wand =====
        TableColumn<Wizard, String> colWood = new TableColumn<>("Madera");
        colWood.setCellValueFactory(w -> {
            Wand wand = cacheWand.get(w.getValue().getWandId());
            String valor = (wand != null) ? wand.getWood() : "";
            return new javafx.beans.property.SimpleStringProperty(valor);
        });

        TableColumn<Wizard, String> colNucleo = new TableColumn<>("Nucleo");
        colNucleo.setCellValueFactory(w -> {
            Wand wand = cacheWand.get(w.getValue().getWandId());
            String valor = (wand != null) ? wand.getCore() : "";
            return new javafx.beans.property.SimpleStringProperty(valor);
        });

        TableColumn<Wizard, String> colLong = new TableColumn<>("Longitud");
        colLong.setCellValueFactory(w -> {
            Wand wand = cacheWand.get(w.getValue().getWandId());
            String valor = (wand != null) ? (wand.getLength()+"") : "";
            return new javafx.beans.property.SimpleStringProperty(valor);
        });
        tabla.getColumns().addAll(colId, colNombre, colEdad, colHouse, colWood, colNucleo, colLong);
        tabla.setItems(datos);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //root.setCenter(tabla);
    }

    private void configurarFormulario() {
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(15);
        form.setVgap(15);

        // ----- Wizard -----
        txtId.setPromptText("ID (entero)");
        txtNombre.setPromptText("Nombre");
        txtEdad.setPromptText("Edad");

        form.add(new Label("ID:"), 0, 0);
        form.add(txtId, 1, 0);
        form.add(new Label("Nombre:"), 0, 1);
        form.add(txtNombre, 1, 1);
        form.add(new Label("Edad:"), 0, 2);
        form.add(txtEdad, 1, 2);

        // ----- DetalleWizard (solo UI, sin BD de momento) -----
        txtWood.setPromptText("Madera varita");
        txtCore.setPromptText("Nucleo varita");
        txtLength.setPromptText("Longitud varita");

        //ComboHouse
        comboHouse.setConverter(new javafx.util.StringConverter<House>() {
            @Override
            public String toString(House house) {
                return (house != null) ? house.getName() : "";
            }

            @Override
            public House fromString(String string) {
                return null; // No necesitamos convertir de texto a objeto si no es editable
            }
        });

        form.add(new Label("House:"), 0, 3);
        form.add(comboHouse, 1, 3);
        form.add(new Label("Founder:"), 0, 4);
        form.add(lblFounder, 1, 4);
        form.add(new Label("Madera varita:"), 0, 5);
        form.add(txtWood, 1, 5);
        form.add(new Label("Núcleo varita:"), 0, 6);
        form.add(txtCore, 1, 6);
        form.add(new Label("Longitud:"), 0, 7);
        form.add(txtLength, 1, 7);


        // Zona botones CRUD aplicamos class para dar estilos
        btnNuevo.getStyleClass().add("btn-info");
        btnGuardar.getStyleClass().add("btn-success");
        btnBorrar.getStyleClass().add("btn-danger");
        btnRecargar.getStyleClass().add("btn-warning");

        HBox botonesCrud = new HBox(10, btnNuevo, btnGuardar, btnBorrar, btnRecargar);
        botonesCrud.setAlignment(javafx.geometry.Pos.CENTER);
        botonesCrud.setPadding(new Insets(20, 0, 0, 0));


        // Zona de búsqueda
        HBox zonaBusqueda = new HBox(10, new Label("🔍"), txtBuscar, btnBuscar, btnLimpiarBusqueda);
        zonaBusqueda.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        zonaBusqueda.setPadding(new Insets(0, 0, 20, 0));

        // --- ENSAMBLAJE DEL PANEL DERECHO (Formulario completo) ---
        panelDerecho = new VBox();
        panelDerecho.setPadding(new Insets(20));
        panelDerecho.getChildren().addAll(zonaBusqueda, new Separator(), form, botonesCrud);

        // Hacemos que el formulario ocupe el espacio necesario pero no se estire feo
        panelDerecho.setMinWidth(350);

    }

    private void configurarEventos() {
        // Cuando seleccionamos una fila en la tabla, pasamos los datos al formulario
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                // Cliente
                txtId.setText(String.valueOf(newSel.getId()));
                txtNombre.setText(newSel.getName());
                txtEdad.setText(newSel.getAge()+"");
                txtId.setDisable(true); // al editar, de momento, no dejamos cambiar el ID

                //DetalleHouse
                House casaDelMago = cacheHouse.get(newSel.getHouseId());

                if (casaDelMago != null) {
                    comboHouse.getSelectionModel().select(casaDelMago);
                    indexHouse = casaDelMago.getId();
                } else {
                    comboHouse.getSelectionModel().clearSelection();
                    indexHouse = null;
                }

                //DetalleWand
                try {
                    Wand newDetalle = wandDAO.findById(newSel.getWandId());
                    if (newDetalle != null) {
                        txtWood.setText(newDetalle.getWood());
                        txtCore.setText(newDetalle.getCore());
                        txtLength.setText(newDetalle.getLength()+"");
                    }   else{
                        txtWood.clear();
                        txtCore.clear();
                        txtLength.clear();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // EVENTO DEL COMBO
        comboHouse.setOnAction(e -> {
            House seleccionada = comboHouse.getValue();
            if (seleccionada != null) {
                lblFounder.setText(seleccionada.getFounder());
                indexHouse = seleccionada.getId(); // <--- Aquí guardamos el 50 por defecto
            } else {
                lblFounder.setText("");
                indexHouse = null;
            }
        });

        btnNuevo.setOnAction(e -> limpiarFormulario());

        btnGuardar.setOnAction(e -> guardarMago());

        btnBorrar.setOnAction(e -> borrarMagoSeleccionado());

        btnRecargar.setOnAction(e -> {
            txtBuscar.clear();
            recargarDatos();
        });

        //btnBuscar.setOnAction(e -> buscarClientesEnBBDD());

        btnLimpiarBusqueda.setOnAction(e -> {
            txtBuscar.clear();
            recargarDatos();
        });
    }

    private void guardarMago() {
        // Con ID manual, vuelve a ser obligatorio
        if (txtId.getText().isBlank() ||
                txtNombre.getText().isBlank() ||
                txtEdad.getText().isBlank()) {

            mostrarAlerta("Campos obligatorios",
                    "Debes rellenar ID, nombre y edad.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException ex) {
            mostrarAlerta("ID inválido", "El ID debe ser un número entero.");
            return;
        }

        //LOGICA PARA AGREGAR MAGO
        /*
         * seleccion por defecto de NO HOUSE / NO FOUNDER
         * indexwand en null, salvo que se pongan los campos
         */

        // HOUSE si no se selecciona ninguno a 50 por defecto
        int houseIdSeguro = (indexHouse != null) ? indexHouse : 50;

        //WAND por defecto null TODO
        String wood = txtWood.getText().trim();
        String core = txtCore.getText().trim();
        String lengthText = txtLength.getText().trim();
        double length=0;

        boolean tieneDatosVarita = !wood.isBlank() || !core.isBlank() || !lengthText.isBlank();
        if (tieneDatosVarita) {
            if (wood.isBlank() || core.isBlank() || lengthText.isBlank()) {
                mostrarAlerta("Varita Incompleta", "Si rellenas la varita, todos sus campos son obligatorios.");
                return;
            }
            try {
                length = Double.parseDouble(lengthText);
            } catch (NumberFormatException ex) {
                mostrarAlerta("Longitud inválida", "Revisa el formato de la longitud.");
                return;
            }
        }
        try {
            // Primero comprobamos si el mago existe
            Wizard existente = wizardDAO.findById(id);

            if (existente == null) {
                // ==========================================
                // CASO 1: Mago NUEVO (INSERT)
                // ==========================================

                Integer newWandId = null;
                if (tieneDatosVarita) {
                    //creamos la varita si no existe
                    newWandId = wandDAO.insert(new Wand(wood, core, length));
                }

                Wizard nuevoMago = new Wizard(id, txtNombre.getText(), Integer.parseInt(txtEdad.getText()), houseIdSeguro, newWandId);
                wizardDAO.create(nuevoMago);

                mostrarInfo("Éxito", "Mago creado correctamente.");

            } else {
                // ==========================================
                // CASO 2: Mago EXISTENTE (UPDATE)
                // ==========================================

                Integer wandIdFinal = existente.getWandId(); // Por defecto, mantenemos su varita actual

                if (tieneDatosVarita) {
                    if (existente.getWandId() != null && existente.getWandId() >0) {
                        // Ya tenía varita -> LA ACTUALIZAMOS
                        Wand wandAEditar = new Wand(existente.getWandId(), wood, core, length);
                        wandDAO.update(wandAEditar);
                    } else {
                        // No tenía varita -> CREAMOS UNA NUEVA Y SE LA ASIGNAMOS
                        wandIdFinal = wandDAO.insert(new Wand(wood, core, length));
                    }
                } else {
                    // Borró los campos de la varita -> se la ponemos a null
                    wandIdFinal = null;
                }

                // Actualizamos el objeto Mago
                Wizard wUpdate = new Wizard(
                        id,
                        txtNombre.getText(),
                        Integer.parseInt(txtEdad.getText()),
                        houseIdSeguro,
                        wandIdFinal
                );
                wizardDAO.update(wUpdate);

                mostrarInfo("Éxito", "Mago actualizado correctamente.");
            }

            recargarDatos();
            limpiarFormulario();

        } catch (SQLException e) {
            mostrarError("Error al guardar mago", e);
        }
    }

    private void recargarDatos() {
        try {
            // 1) Cargar todos los clientes
            List<Wizard> wizards = wizardDAO.getAll();

            // 2) Cargar todos los detalles
            List<Wand> wands = wandDAO.getAll();
            List<House> houses = houseDAO.getAll();

            //actualizar lista de combo
            comboHouse.setItems(FXCollections.observableArrayList(houses));
            House casaDefault = houses.stream()
                    .filter(h -> h.getId() == 50)
                    .findFirst()
                    .orElse(null);
            if (casaDefault != null) {
                comboHouse.getSelectionModel().select(casaDefault);
            }

            // 3) Rellenar la caché id -> detalle
            cacheHouse.clear();
            for (House h : houses) {
                cacheHouse.put(h.getId(), h);
            }
            cacheWand.clear();
            for (Wand w : wands) {
                cacheWand.put(w.getId(), w);
            }

            // 4) Refrescar la tabla  👈 AHORA SÍ
            datos.setAll(wizards);

        } catch (SQLException e) {
            mostrarError("Error al recargar datos", e);
        }
    }

    private void borrarMagoSeleccionado() {
        Wizard sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Sin selección", "Selecciona un mago en la tabla.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar borrado");
        confirm.setHeaderText("¿Eliminar mago de la tabla?");
        confirm.setContentText("Se borrará el mago con ID " + sel.getId());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }
        try {
            wizardDAO.delete(sel.getId());
            //si no existe la varita no pasa nada, porque la consulta de eliminar no devuelve error
            wandDAO.delete(sel.getWandId());
            mostrarInfo("Eliminar", "Mago y varita eliminados correctamente.");

        } catch (SQLException e) {
            mostrarError("Error al borrar el mago y varita", e);
        }

        limpiarFormulario();
        recargarDatos();

    }

    private void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        txtEdad.clear();
        txtWood.clear();
        txtCore.clear();
        txtLength.clear();
        txtId.setDisable(false);
        tabla.getSelectionModel().clearSelection();

        House defaultHouse = comboHouse.getItems().stream()
                .filter(h -> h.getId() == 50)  // Buscamos el ID 50
                .findFirst()
                .orElse(null);

        if (defaultHouse != null) {
            // y se pondrá indexHouse = 50 y el label = "No Founder"
            comboHouse.getSelectionModel().select(defaultHouse);
        } else {
            // Si no existe la 50, limpiamos todo
            comboHouse.getSelectionModel().clearSelection();
            lblFounder.setText("---");
            indexHouse = null;
        }
    }

    /* =========================================================
       DIÁLOGOS AUXILIARES
       ========================================================= */

    private void mostrarError(String titulo, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}