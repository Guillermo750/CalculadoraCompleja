package dad.calculadora.compleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {
	
	private Label signo1Label, i1Label, signo2Label, i2Label, signo3Label, i3Label;
	private TextField real1Text,imagi1Text,real2Text,imagi2Text,realText,imaginarioText;
	private HBox numero1Box,numero2Box,numero3Box;
	private VBox realBox,comboBox;
	private ComboBox<String> miCombo;
	private Separator separador = new Separator();
	private Complejo primercomp = new Complejo();
	private Complejo segundocomp = new Complejo();
	private Complejo resultadocomp = new Complejo();
	private StringProperty operador = new SimpleStringProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		signo1Label = new Label("+");
		i1Label = new Label("i");
		signo2Label = new Label("+");
		i2Label = new Label("i");
		signo3Label = new Label("+");
		i3Label = new Label("i");
		
		real1Text = new TextField("0");
		real1Text.setMaxWidth(50);
		
		imagi1Text = new TextField("0");
		imagi1Text.setMaxWidth(50);
		
		real2Text = new TextField("0");
		real2Text.setMaxWidth(50);
		
		imagi2Text = new TextField("0");
		imagi2Text.setMaxWidth(50);
		
		realText = new TextField("0");
		realText.setMaxWidth(50);
		realText.setDisable(true);
		
		imaginarioText = new TextField("0");
		imaginarioText.setMaxWidth(50);
		imaginarioText.setDisable(true);
		
		miCombo = new ComboBox<String>();
		miCombo.getItems().addAll("+", "-", "*", "/");
		miCombo.getSelectionModel().selectFirst();
		miCombo.setMaxWidth(60);
		
		numero1Box = new HBox(4,real1Text,signo1Label,imagi1Text,i1Label);
		numero1Box.setAlignment(Pos.CENTER);
		
		numero2Box = new HBox(4,real2Text,signo2Label,imagi2Text,i2Label);
		numero2Box.setAlignment(Pos.CENTER);
		
		numero3Box = new HBox(4,realText,signo3Label,imaginarioText,i3Label);
		numero3Box.setAlignment(Pos.CENTER);
		
		comboBox = new VBox(5,miCombo);
		comboBox.setAlignment(Pos.CENTER);
		
		realBox = new VBox(5,numero1Box,numero2Box,separador,numero3Box);
		realBox.setAlignment(Pos.CENTER);
		
		HBox root = new HBox(5,realBox,comboBox);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("CalculadoraView.fxml");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Bindings.bindBidirectional(real1Text.textProperty(), primercomp.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(real2Text.textProperty(), segundocomp.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(realText.textProperty(), resultadocomp.realProperty(), new NumberStringConverter());

		Bindings.bindBidirectional(imagi1Text.textProperty(), primercomp.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(imagi2Text.textProperty(), segundocomp.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(imaginarioText.textProperty(), resultadocomp.imaginarioProperty(), new NumberStringConverter());
		
		operador.bind(miCombo.getSelectionModel().selectedItemProperty());
		
		operador.addListener((o, ov, nv) -> onOperadorChanged(nv));

	}

	private void onOperadorChanged(String nv) {
		
		switch (nv) {
		
			case "+":
				resultadocomp.realProperty().bind(primercomp.realProperty().add(segundocomp.realProperty()));
				resultadocomp.imaginarioProperty().bind(primercomp.imaginarioProperty().add(segundocomp.imaginarioProperty()));
			break;
		
			case "-":
				resultadocomp.realProperty().bind(primercomp.realProperty().subtract(segundocomp.realProperty()));
				resultadocomp.imaginarioProperty().bind(primercomp.imaginarioProperty().subtract(segundocomp.imaginarioProperty()));
			break;
			
			case "*":
				resultadocomp.realProperty().bind(primercomp.realProperty().multiply(segundocomp.realProperty()
					.subtract(primercomp.imaginarioProperty().multiply(segundocomp.imaginarioProperty()))));
				resultadocomp.imaginarioProperty().bind(primercomp.realProperty().multiply(segundocomp.imaginarioProperty())
					.add(primercomp.imaginarioProperty().multiply(segundocomp.realProperty())));
			break;
			
			case "/":
				resultadocomp.realProperty()
					.bind((primercomp.realProperty()
							.multiply(segundocomp.realProperty()
									.add(primercomp.imaginarioProperty().multiply(segundocomp.imaginarioProperty())))
							.divide(segundocomp.realProperty().multiply(segundocomp.realProperty())
									.add(segundocomp.imaginarioProperty().multiply(segundocomp.imaginarioProperty())))));
				resultadocomp.imaginarioProperty()
					.bind((primercomp.imaginarioProperty().multiply(segundocomp.realProperty())
							.subtract(primercomp.realProperty().multiply(segundocomp.imaginarioProperty())))
									.divide(segundocomp.realProperty().multiply(segundocomp.realProperty()).add(
											segundocomp.imaginarioProperty().multiply(segundocomp.imaginarioProperty()))));
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
