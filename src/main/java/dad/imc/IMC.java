package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
	// peso
	private Label pesoLabel;
	private TextField pesoText;
	private Label kgLabel;
	// altura
	private Label alturaLabel;
	private TextField alturaText;
	private Label cmLabel;
	// formula
	private Label formulaLabel;
	// opciones
	private Label opcionesLabel;
	// propiedades
	private StringProperty pesoStringProperty = new SimpleStringProperty();
	private DoubleProperty pesoProperty = new SimpleDoubleProperty();
	private StringProperty alturaStringProperty = new SimpleStringProperty();
	private DoubleProperty alturaProperty = new SimpleDoubleProperty();
	private StringProperty formulaStringProperty = new SimpleStringProperty();
	private DoubleProperty formulaProperty = new SimpleDoubleProperty();
	private StringProperty opcionesProperty = new SimpleStringProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// etiquetas
		pesoLabel = new Label("Peso:");
		alturaLabel = new Label("Altura:");
		kgLabel = new Label("kg");
		cmLabel = new Label("cm");
		formulaLabel = new Label("IMC: (peso * altura^ 2)");
		opcionesLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
		// cuadros texto
		pesoText = new TextField();
		pesoText.setPrefColumnCount(5);
		pesoText.setMaxWidth(130);
		pesoText.setAlignment(Pos.CENTER);
		alturaText = new TextField();
		alturaText.setPrefColumnCount(5);
		alturaText.setMaxWidth(130);
		alturaText.setAlignment(Pos.CENTER);
		// cajas
		HBox pesoBox = new HBox();
		pesoBox.setSpacing(5);
		pesoBox.setAlignment(Pos.CENTER);
		pesoBox.getChildren().addAll(pesoLabel, pesoText, kgLabel);
		HBox alturaBox = new HBox();
		alturaBox.setSpacing(5);
		alturaBox.setAlignment(Pos.CENTER);
		alturaBox.getChildren().addAll(alturaLabel, alturaText, cmLabel);
		HBox imcBox = new HBox();
		imcBox.setSpacing(1);
		imcBox.setAlignment(Pos.CENTER);
		VBox root = new VBox(5, pesoBox, alturaBox, formulaLabel, opcionesLabel);
		root.setAlignment(Pos.CENTER);
		//escena
		Scene escena = new Scene(root, 300, 200);
		primaryStage.setScene(escena);
		primaryStage.setTitle("IMC");
		primaryStage.show();
		// bindeos
		pesoStringProperty.bindBidirectional(pesoText.textProperty());
		Bindings.bindBidirectional(pesoStringProperty, pesoProperty, new NumberStringConverter());
		pesoProperty.addListener((o, ov, nv) -> onCalculoOpciones());
		alturaStringProperty.bindBidirectional(alturaText.textProperty());
		Bindings.bindBidirectional(alturaStringProperty, alturaProperty, new NumberStringConverter());
		alturaProperty.addListener((o, ov, nv) -> onCalculoOpciones());
		formulaStringProperty.bindBidirectional(formulaLabel.textProperty());
		opcionesProperty.bindBidirectional(opcionesLabel.textProperty());
		formulaProperty.addListener((o, ov, nv) -> onCalculoIMC());
	}
	private void onCalculoOpciones() {
		if ((pesoProperty.get() == 0) || (alturaProperty.get() == 0))
			formulaProperty.set(0);
		else {
			Double imc = (pesoProperty.get() / (Math.pow(alturaProperty.get(), 2)) * 10000);
			formulaProperty.set(Math.round(imc * 100.0) / 100.0);
		}
	}
	public void onCalculoIMC() {
		if (formulaProperty.get() == 0) {
			formulaStringProperty.set("IMC: (peso * altura^ 2)");
			opcionesProperty.set("Bajo peso | Normal | Sobrepeso | Obeso");
		} else {
			formulaStringProperty.set("IMC: " + formulaProperty.get());
			if (formulaProperty.get() < 18.5)
				opcionesProperty.set("Bajo peso");
			else if (formulaProperty.get() >= 18.5 && formulaProperty.get() < 25.0)
				opcionesProperty.set("Normal");
			else if (formulaProperty.get() >= 25.0 && formulaProperty.get() < 30.0)
				opcionesProperty.set("Sobrepeso");
			else
				opcionesProperty.set("Obeso");
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}

