package controller;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import application.Launcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.EmployeesManager;
import model.Key;
import model.KeysManager;
import model.Note;
import model.NoteManager;
import model.implementation.KeyImpl;
import model.implementation.EmployeesManagerImpl;
import model.implementation.KeysManagerImpl;
import model.implementation.NoteImpl;
import model.implementation.NoteManagerImpl;

public class MainPageController implements Initializable {

    @FXML
    private TableView<Employee> employeesTable;
    
    @FXML
    private TableView<Key> keysTable;

    @FXML
    private AnchorPane registro, utente, anchorRemoveUser, keys, note;
    
    @FXML
    private ListView<String> listRemovibleEmployee, keyList;

    @FXML
    private TableColumn<Employee, String> tableCognome, tableLastAccess, tableNome;
    
    @FXML
    private TableColumn<Key, String> tabbleKeyTag, tableKeyHolder, tableKeyLastAccess;
    
    @FXML
    private ChoiceBox<String> noteBox;

    @FXML
    private TextArea textArea;
    
    @FXML
    private TextField txtLastAccess, txtName, txtSurname, txtUsername, newKeyTag, modifyKeyHolder;
        
    private KeysManager keysManager;
    private EmployeesManager employeesManager;
    private NoteManager noteManager;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	this.keysManager = new KeysManagerImpl();
		this.keysManager.getList().forEach(k -> this.keyList.getItems().add(k.getTag()));
    	this.employeesManager = new EmployeesManagerImpl();
    	this.noteManager = new NoteManagerImpl();
    	noteManager.getList().forEach(n -> this.noteBox.getItems().add(n.getCreationDate()));
    	noteBox.setOnAction(this::updateText);
        try {
			userCall(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /*
     *  SEZIONE DEDICATA ALL'UTENTE
     */
    
    @FXML
    void userCall(ActionEvent event) throws SQLException {
    	if(this.registro.isVisible() || this.keys.isVisible() || this.note.isVisible()) {
    		this.registro.setVisible(false);
        	this.keys.setVisible(false);
        	this.note.setVisible(false);
    	}    	
    	Employee user = employeesManager.getList().stream().filter(x -> x.logged()).findFirst().get();
    	this.txtName.setText(user.getName());
    	this.txtSurname.setText(user.getSurname());
    	this.txtUsername.setText(user.getUsername());
    	employeesManager.update(user, setCurrentDateTime());
    	this.txtLastAccess.setText(user.getLastAccess());
    	
    	this.utente.setVisible(true);
    }
    
    @FXML
    void removeUserCall(ActionEvent event) {
    	if (!anchorRemoveUser.isVisible()) 
    		employeesManager.getList().forEach(e -> { if(!e.getUsername().equals(this.txtUsername.getText()))
    				                           		 	  listRemovibleEmployee.getItems().add(e.getUsername()); 
    												});
    	anchorRemoveUser.setVisible(true);
    }
    
    @FXML
    void removeEmployee(ActionEvent event) throws SQLException {
        String selectedEmployee = listRemovibleEmployee.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
        	employeesManager.remove(selectedEmployee);
        	listRemovibleEmployee.getItems().clear();
        }
    	anchorRemoveUser.setVisible(false);
    }
    
    @FXML
    void newUser(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/view/AddEmployee.fxml"));
    	Stage stage = new Stage();
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("Nuovo utente");
		stage.setResizable(false);
    	stage.show();
    }
    
    /*
     * SEZIONE DEDICATA AL REGISTRO
     */

    @FXML
    void registerCall(ActionEvent event) {
    	    if (this.utente.isVisible() || this.keys.isVisible() || this.note.isVisible()) {
    	        this.utente.setVisible(false);
    	        this.keys.setVisible(false);
    	        this.note.setVisible(false);
    	        employeesTable.getItems().clear();
    	        keysTable.getItems().clear();
    	    }
    	    ObservableList<Employee> employeesList = FXCollections.observableArrayList(employeesManager.getList());
    	    tableNome.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
    	    tableCognome.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
    	    tableLastAccess.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastAccess"));
    	    employeesTable.setItems(employeesList);

    	    ObservableList<Key> keysList = FXCollections.observableArrayList(keysManager.getList());
    	    tabbleKeyTag.setCellValueFactory(new PropertyValueFactory<Key, String>("tag"));
    	    tableKeyHolder.setCellValueFactory(new PropertyValueFactory<Key, String>("holder"));
    	    tableKeyLastAccess.setCellValueFactory(new PropertyValueFactory<Key, String>("lastAccess"));
    	    keysTable.setItems(keysList);

    	    registro.setVisible(true);
    	}
    
    /*
     * SEZIONE DEDICATA ALLA GESTIONE DELLE CHIAVI
     */

    @FXML
    void keyCall(ActionEvent event) {
    	if(this.registro.isVisible() || this.utente.isVisible() || this.note.isVisible()) {
    		this.registro.setVisible(false);
    		this.utente.setVisible(false);
    		this.note.setVisible(false);
    	}
    	this.keys.setVisible(true);
    }


    @FXML
    void removeKey(ActionEvent event) throws SQLException {
        String selectedKey = keyList.getSelectionModel().getSelectedItem();
        if (selectedKey != null) {
            keysManager.remove(selectedKey);
            keyList.getItems().removeIf(k -> k.equals(selectedKey));
        }
    }


    @FXML
    void modifyKey(ActionEvent event) throws SQLException {
    	String selectedKey = this.keyList.getSelectionModel().getSelectedItem();
        String newHolder = modifyKeyHolder.getText();
        Iterator<Key> keyIterator = keysManager.getList().iterator();
        while (keyIterator.hasNext()) {
            Key key = keyIterator.next();
            if (key.getTag().equals(selectedKey)) {
            	String whenChange = setCurrentDateTime();
            	keysManager.update(key, whenChange);
                key.setHolder(newHolder);
                key.setLastAccess(whenChange);
                break;
            }
        }
    }
    
    @FXML
    void addKey(ActionEvent event) throws SQLException {
    	if (!newKeyTag.getText().isBlank())
    		keysManager.add(new KeyImpl(newKeyTag.getText(), "Portineria", Optional.of(setCurrentDateTime())));
    		keyList.getItems().add(newKeyTag.getText());
    }
    
    /*
     * SEZIONE DEDICATA ALLE NOTE DEL PERSONALE
     */
   
    @FXML
    void noteCall(ActionEvent event) throws SQLException {
    	if(this.registro.isVisible() || this.utente.isVisible() || this.keys.isVisible()) {
    		this.registro.setVisible(false);
    		this.utente.setVisible(false);
    		this.keys.setVisible(false);
    	}
    	this.textArea.setText(noteManager.getList().stream()
    	        .max(Comparator.comparing(n -> n.getCreationDate()))
    	        .map(Note::getNote)
    	        .orElse(""));
    	
		this.note.setVisible(true);
    }

    @FXML
    void saveTextArea(ActionEvent event) throws SQLException {
    	String note = this.textArea.getText();
    	String date = setCurrentDateTime();
    	this.noteManager.add(new NoteImpl(note, date));
    	this.noteBox.getItems().add(date);
    }
    

   private void updateText(ActionEvent event) {
    	String textDate = this.noteBox.getValue();
    	String note = noteManager.getList().stream()
				   .filter(n -> n.getCreationDate().equals(textDate))
				   .map(n -> n.getNote())
				   .findFirst().orElse(" ");
		this.textArea.setText(note);
    }
    
    /*
     * ALTRO
     */
    
    @FXML
    void logOut(ActionEvent event) throws Exception {
    	keysManager.clear();
    	employeesManager.clear();
    	Launcher launch = new Launcher();
		launch.changeScene("/view/LogIn.fxml");
    }
    
	private static String setCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm:ss");
        Date date = new Date();
		return formatter.format(date);
    }
}
