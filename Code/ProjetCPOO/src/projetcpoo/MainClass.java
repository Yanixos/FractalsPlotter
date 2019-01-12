package projetcpoo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainClass extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Parameters param = getParameters();
        ArgumentParser arg_prs = new ArgumentParser(param.getRaw().toArray(new String[0]));
        
        EnsembleFractal e;
        Complexe c = new Complexe(arg_prs.x, arg_prs.y);
        if(arg_prs.s.equals("julia")){
            if(arg_prs.f.equals("polynomial")){
                e = new EnsembleDeJuliaPolynomial((int)arg_prs.d, c);
            }
            else{
                e = new EnsembleDeJuliaExponentiel(c);
            }
        }
        else{
            FonctionRecursive f;
            if(arg_prs.f.equals("polynomial")){
                f = (x0, cc) -> { return cc.plus(x0.times(x0)); };
            }
            else{
                f = (x0, c2) -> {
                    Complexe xn = x0;
                    xn = xn.exp();
                    return c2.plus(xn);
                };
            }
            e = new EnsembleDeMandelbrot(f);
        }
        
        EnsembleFractalTraceur t = new EnsembleFractalTraceur(e, arg_prs.z, Complexe.zero(),
                null , arg_prs.m);
        t.changerPalette(arg_prs.p - 1);
        
        if(arg_prs.i.equals("off")){
            t.exporterPng(arg_prs.o, arg_prs.rx, arg_prs.ry);
            System.exit(1);
        }
        
        
        final ImageView iv = new ImageView(new WritableImage(500, 500));
        Refresh(arg_prs, t, iv);
        
        //global container
        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setPrefWidth(700);
        
        //lower part container
        HBox hb = new HBox(5);
        hb.setAlignment(Pos.CENTER);
        hb.setPrefHeight(100);
        hb.setMaxHeight(100);
        
        //left container
        VBox vb1 = new VBox(10);
        vb1.setPadding(new Insets(10, 10, 10, 10));
        HBox.setHgrow(vb1, Priority.ALWAYS);
        vb1.setAlignment(Pos.TOP_CENTER);
        vb1.setMaxWidth(Double.MAX_VALUE);
        
        //Ensemble + Julia/Mandelbrot
        HBox hb11 = new HBox(5);
        hb11.setAlignment(Pos.BASELINE_CENTER);
        
        //Ensemble
        Label lb11 = new Label("Ensemble :");
        HBox.setHgrow(lb11, Priority.ALWAYS);
        lb11.setMaxWidth(Double.MAX_VALUE);
        
        //Julia/Mandelbrot
        ChoiceBox<String> cb11 = new ChoiceBox<String>(FXCollections.observableArrayList(
            "Julia", "Mandelbrot")
        );
        cb11.setMaxWidth(100);
        cb11.setMinWidth(100);
        HBox.setHgrow(cb11, Priority.ALWAYS);
        if(arg_prs.s.equals("julia")){
            cb11.getSelectionModel().selectFirst();
        }
        else{
            cb11.getSelectionModel().selectLast();
        }
        
        //Fonction + x^n/e^x + Degree
        HBox hb12 = new HBox(5);
        hb12.setAlignment(Pos.BASELINE_CENTER);
        
        //Fonction
        Label lb12 = new Label("Fonction :");
        HBox.setHgrow(lb12, Priority.ALWAYS);
        lb12.setMaxWidth(Double.MAX_VALUE);
        
        //x^n/e^x
        ChoiceBox<String> cb2 = new ChoiceBox<String>(FXCollections.observableArrayList(
            "X^n", "e^X")
        );
        cb2.setMaxWidth(50);
        HBox.setHgrow(cb2, Priority.ALWAYS);
        cb2.setMinWidth(50);
        cb2.getSelectionModel().selectFirst();
        
        //Degree
        TextField tf12 = new TextField();
        tf12.setPromptText("Degree");
        HBox.setHgrow(tf12, Priority.ALWAYS);
        tf12.setPrefWidth(50);
        tf12.setMaxWidth(Double.MAX_VALUE);
        tf12.setText(Integer.toString((int)arg_prs.d));
        
        //Palette + 1/2/3
        HBox hb13 = new HBox(5);
        hb13.setAlignment(Pos.BASELINE_CENTER);
        
        //Palette
        Label lb13 = new Label("Palette :");
        HBox.setHgrow(lb13, Priority.ALWAYS);
        lb13.setMaxWidth(Double.MAX_VALUE);
        
        //1/2/3
        ChoiceBox<String> cb3 = new ChoiceBox<String>(FXCollections.observableArrayList(
            "1", "2", "3")
        );
        cb3.setMaxWidth(70);
        HBox.setHgrow(cb3, Priority.ALWAYS);
        cb3.setMinWidth(70);
        cb3.getSelectionModel().select(Integer.toString(arg_prs.p));
        
        //middle containter
        VBox vb2 = new VBox(10);
        vb2.setPadding(new Insets(10, 10, 10, 10));
        HBox.setHgrow(vb2, Priority.ALWAYS);
        vb2.setAlignment(Pos.TOP_CENTER);
        vb2.setMaxWidth(Double.MAX_VALUE);
        
        //C + Re + Im
        HBox hb21 = new HBox(5);
        hb21.setAlignment(Pos.BASELINE_CENTER);
        
        //C
        Label lb21 = new Label("C :");
        lb21.setPrefWidth(30);
        
        //Re
        TextField tf21 = new TextField();
        tf21.setPromptText("Re");
        tf21.setPrefWidth(60);
        tf21.setText(Double.toString(arg_prs.x));
        
        //Im
        TextField tf22 = new TextField();
        tf22.setPromptText("Im");
        tf22.setPrefWidth(60);
        tf22.setText(Double.toString(arg_prs.y));
        
        //Iterations + text field
        HBox hb22 = new HBox(5);
        hb22.setAlignment(Pos.BASELINE_CENTER);
        
        //Iterations
        Label lb22 = new Label("Itérations :");
        lb22.setPrefWidth(100);
        HBox.setHgrow(lb22, Priority.ALWAYS);
        lb22.setMaxWidth(Double.MAX_VALUE);
        
        //max iter text field
        TextField tf221 = new TextField();
        HBox.setHgrow(tf221, Priority.ALWAYS);
        tf221.setPrefWidth(100);
        tf221.setMaxWidth(Double.MAX_VALUE);
        tf221.setText(Integer.toString(arg_prs.m));
        
        //raifraichir
        Button b22 = new Button("Rafraîchir");
        
        //right container
        VBox vb3 = new VBox(5);
        vb3.setPadding(new Insets(10, 10, 10, 10));
        HBox.setHgrow(vb3, Priority.ALWAYS);
        vb3.setAlignment(Pos.TOP_CENTER);
        vb3.setMaxWidth(Double.MAX_VALUE);
        
        //Zoom + "+" + "-"
        HBox hb31 = new HBox(5);
        hb31.setAlignment(Pos.BASELINE_CENTER);
        
        //Zoom
        Label lb31 = new Label("Zoom :");
        HBox.setHgrow(lb31, Priority.ALWAYS);
        
        //"+"
        Button b311 = new Button("+");
        b311.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                t.zoomer(true);
                Refresh(arg_prs, t, iv);
            }
        });
        
        //"-"
        Button b312 = new Button("-");
        b312.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                t.zoomer(false);
                Refresh(arg_prs, t, iv);
            }
        });
        
        //Move up
        Button b32 = new Button("Haut");
        b32.setPrefWidth(70);
        b32.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                t.decaler(0, -1);
                Refresh(arg_prs, t, iv);
            }
        });
        
        //Move left + Depl + Move right
        HBox hb32 = new HBox(5);
        hb32.setAlignment(Pos.BASELINE_CENTER);
        
        //Move left
        Button b321 = new Button("Gauche");
        b321.setPrefWidth(70);
        b321.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                t.decaler(-1, 0);
                Refresh(arg_prs, t, iv);
            }
        });
        
        //Depl
        Label lb321 = new Label("Depl.");
        
        //Move right
        Button b322 = new Button("Droite");
        b322.setPrefWidth(70);
        b322.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                t.decaler(1, 0);
                Refresh(arg_prs, t, iv);
            }
        });
        
        //Move down
        Button b33 = new Button("Bas");
        b33.setPrefWidth(70);
        b33.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                t.decaler(0, 1);
                Refresh(arg_prs, t, iv);
            }
        });
        
        b22.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int funcId = 0;//x^n
                if(cb2.getSelectionModel().isSelected(1)){
                    funcId = 1;//e^x
                }
                int degree = tryParseInt(tf12.getText());
                double re = tryParseDouble(tf21.getText());
                double im = tryParseDouble(tf22.getText());
                int iter = tryParseInt(tf221.getText());
                int paletteID = cb3.getSelectionModel().
                        selectedIndexProperty().get();
                if(paletteID < 0){ paletteID = 0; }
                
                EnsembleFractal ens;
                if(!cb11.getSelectionModel().isSelected(1)){//Julia
                    if(funcId == 0){
                        ens = new EnsembleDeJuliaPolynomial(
                                degree, 
                                new Complexe(re, im));
                    }
                    else{
                        ens = new EnsembleDeJuliaExponentiel
                            (new Complexe(re, im));
                    }
                }
                else{//Mandelbrot
                    FonctionRecursive fh;
                    if(funcId == 0){
                        fh = (x0, c2) -> {
                            Complexe xn = x0;
                            for(int i = 1;i < degree;i++) {
                                xn = xn.times(x0);
                            }
                            return c2.plus(xn);
                        };
                    }
                    else{
                        fh = (x0, c2) -> {
                            Complexe xn = x0;
                            xn = xn.exp();
                            return c2.plus(xn);
                        };
                    }
                    ens = new EnsembleDeMandelbrot(fh);
                }
                t.changerMaxIter(iter);
                t.changerPalette(paletteID);
                t.changerEnsembleFractal(ens);
                Refresh(arg_prs, t, iv);
            }
        });
        
        vb.getChildren().addAll(iv, hb);
        hb.getChildren().addAll(vb1, vb2, vb3);
        vb1.getChildren().addAll(hb11, hb12, hb13);
        hb11.getChildren().addAll(lb11, cb11);
        hb12.getChildren().addAll(lb12, cb2, tf12);
        hb13.getChildren().addAll(lb13, cb3);
        vb2.getChildren().addAll(hb21, hb22, b22);
        hb21.getChildren().addAll(lb21, tf21, tf22);
        hb22.getChildren().addAll(lb22, tf221);
        vb3.getChildren().addAll(hb31, b32, hb32, b33);
        hb31.getChildren().addAll(lb31, b311, b312);
        hb32.getChildren().addAll(b321, lb321, b322);
        
        //root.getChildren().add(vb);
        Scene scene = new Scene(vb);
        
        primaryStage.setTitle("Ensemble De Julia!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private static int tryParseInt(String text){
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }
    
    private static double tryParseDouble(String text){
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return 0;
        }
    }

    private static void Refresh(ArgumentParser arg_prs,
            EnsembleFractalTraceur t, ImageView iv){
        if(arg_prs.t <= 1){
            if(arg_prs.prg.equals("on")){
                t.afficherProgressivementSurEcran(iv, 500, 500);
            }
            else{
                t.afficherSurEcran(iv, 500, 500);
            }
        }
        else{
            t.afficherSurEcranMultithread(iv, 500, 500, arg_prs.t);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
