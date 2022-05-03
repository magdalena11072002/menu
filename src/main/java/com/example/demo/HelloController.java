package com.example.demo;

import Enums.Ksztalty;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class HelloController {
    @FXML
    private Pane panelRysowania;

    private Ksztalty wybranyKsztalt = Ksztalty.TROJKAT;

    private ArrayList<Circle> punktyNaPlanszy = new ArrayList<>();
    private ArrayList<Rectangle> prostokaty = new ArrayList<>();
    private ArrayList<Polygon> trojkaty = new ArrayList<>();
    private double x,y;
    private double promien;

    @FXML
    protected void rysujTrojkat() {
        wybranyKsztalt = Ksztalty.TROJKAT;
        wyczyscPunktyNaPlanszy();
    }

    @FXML
    protected void rysujProstokat() {
        wybranyKsztalt = Ksztalty.PROSTOKAT;
        wyczyscPunktyNaPlanszy();
    }

    @FXML
    protected void rysujKolo() {
        wybranyKsztalt=Ksztalty.KOLO;
        wyczyscPunktyNaPlanszy();
    }

    @FXML
    protected void addRectangle(MouseEvent event) {
        Circle punktNaPlanszy = new Circle();
        punktNaPlanszy.setLayoutX(event.getX());
        punktNaPlanszy.setLayoutY(event.getY());
        punktNaPlanszy.setRadius(1);
        punktNaPlanszy.setFill(Color.BLACK);
        panelRysowania.getChildren().add(punktNaPlanszy);
        punktyNaPlanszy.add(punktNaPlanszy);


        if (punktyNaPlanszy.toArray().length == 2 && wybranyKsztalt == Ksztalty.PROSTOKAT) {

            Rectangle rectangle = new Rectangle();

            double height, width;
            if (punktyNaPlanszy.get(0).getLayoutX() < punktyNaPlanszy.get(1).getLayoutX()) {
                x = punktyNaPlanszy.get(0).getLayoutX();
                width = punktyNaPlanszy.get(1).getLayoutX() - punktyNaPlanszy.get(0).getLayoutX();

            } else {
                x = punktyNaPlanszy.get(1).getLayoutX();
                width = punktyNaPlanszy.get(0).getLayoutX() - punktyNaPlanszy.get(1).getLayoutX();
            }

            if (punktyNaPlanszy.get(0).getLayoutY() < punktyNaPlanszy.get(1).getLayoutY()) {
                y = punktyNaPlanszy.get(0).getLayoutY();
                height = punktyNaPlanszy.get(1).getLayoutY() - punktyNaPlanszy.get(0).getLayoutY();

            } else {
                y = punktyNaPlanszy.get(1).getLayoutY();
                height = punktyNaPlanszy.get(0).getLayoutY() - punktyNaPlanszy.get(1).getLayoutY();
            }

            rectangle.setX(x);
            rectangle.setY(y);
            rectangle.setWidth(width);
            rectangle.setHeight(height);
            prostokaty.add(rectangle);
            panelRysowania.getChildren().add(rectangle);

            rectangle.setOnMousePressed(e->{
                x=e.getSceneX()-rectangle.getTranslateX();
                y=e.getSceneY()-rectangle.getTranslateY();
            });

            rectangle.setOnMouseDragged(e ->{
                rectangle.setTranslateX(e.getSceneX()-x);
                rectangle.setTranslateY(e.getSceneY()-y);
            });

            wyczyscPunktyNaPlanszy();
        }

        if (punktyNaPlanszy.toArray().length == 2 && wybranyKsztalt == Ksztalty.KOLO) {
            Circle kolo = new Circle();
            promien = Math.sqrt(Math.pow((punktyNaPlanszy.get(0).getLayoutX() - punktyNaPlanszy.get(1).getLayoutX()), 2) + Math.pow((punktyNaPlanszy.get(0).getLayoutY() - punktyNaPlanszy.get(1).getLayoutY()), 2));
            kolo.setRadius(promien);
            kolo.setLayoutX(punktyNaPlanszy.get(0).getLayoutX());
            kolo.setLayoutY(punktyNaPlanszy.get(0).getLayoutY());

            panelRysowania.getChildren().add(kolo);

            kolo.setOnMousePressed(e->{
                promien=e.getSceneX()-kolo.getTranslateX();
                promien=e.getSceneY()-kolo.getTranslateY();
            });

            kolo.setOnMouseDragged(e ->{
                kolo.setTranslateX(e.getSceneX()-promien);
                kolo.setTranslateY(e.getSceneY()-promien);

            });

            wyczyscPunktyNaPlanszy();
        }

        if (punktyNaPlanszy.toArray().length == 3 && wybranyKsztalt == Ksztalty.TROJKAT) {
            Polygon trojkat = new Polygon();
            trojkat.getPoints().addAll(punktyNaPlanszy.get(0).getLayoutX(), punktyNaPlanszy.get(0).getLayoutY(), punktyNaPlanszy.get(1).getLayoutX(), punktyNaPlanszy.get(1).getLayoutY(), punktyNaPlanszy.get(2).getLayoutX(), punktyNaPlanszy.get(2).getLayoutY());

            panelRysowania.getChildren().add(trojkat);
            wyczyscPunktyNaPlanszy();

//            trojkat.setOnMousePressed(e->{
//                trojkat.getPoints()=e.getSceneX()-trojkat;
//            });
        }

    }
    private void wyczyscPunktyNaPlanszy() {
        panelRysowania.getChildren().removeAll(punktyNaPlanszy);
        punktyNaPlanszy.removeAll(punktyNaPlanszy);
    }

}

