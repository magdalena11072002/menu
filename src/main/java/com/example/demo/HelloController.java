package com.example.demo;

import Enums.Ksztalty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class HelloController {
    @FXML
    private Pane panelRysowania;

    private Ksztalty wybranyKsztalt = Ksztalty.TROJKAT;

    private ArrayList<Circle> punktyNaPlanszy = new ArrayList<>();
    private ArrayList<Rectangle> prostokaty = new ArrayList<>();
    private ArrayList<Polygon> trojkaty = new ArrayList<>();
    private double x, y, x1, y1;
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
        wybranyKsztalt = Ksztalty.KOLO;
        wyczyscPunktyNaPlanszy();
    }

    @FXML
    protected void addRectangle(MouseEvent event) {

        czyDodacPunkt(event, true);


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


            rectangle.setOnMousePressed(e -> {
                x = e.getSceneX() - rectangle.getTranslateX();
                y = e.getSceneY() - rectangle.getTranslateY();
            });

            rectangle.setOnMouseDragged(e -> {
                rectangle.setTranslateX(e.getSceneX() - x);
                rectangle.setTranslateY(e.getSceneY() - y);
            });

            rectangle.setOnScroll(e -> {
                double zoom = 1.05;
                if (e.getDeltaY() < 0) {
                    zoom = 2.0 - zoom;
                }

                if (e.getDeltaY() > 0) {
                    rectangle.setScaleX(rectangle.getScaleX() * (-zoom));
                    rectangle.setScaleY(rectangle.getScaleY() * (-zoom));
                } else if (e.getDeltaY() < 0) {
                    rectangle.setScaleX(rectangle.getScaleX() * (zoom));
                    rectangle.setScaleY(rectangle.getScaleY() * (zoom));
                }
            });

            Rotate rotate = new Rotate();
            obrot(rotate);
            rectangle.getTransforms().add(rotate);


            wyczyscPunktyNaPlanszy();
        }

        if (punktyNaPlanszy.toArray().length == 2 && wybranyKsztalt == Ksztalty.KOLO) {
            Circle kolo = new Circle();
            promien = Math.sqrt(Math.pow((punktyNaPlanszy.get(0).getLayoutX() - punktyNaPlanszy.get(1).getLayoutX()), 2) + Math.pow((punktyNaPlanszy.get(0).getLayoutY() - punktyNaPlanszy.get(1).getLayoutY()), 2));
            kolo.setRadius(promien);
            kolo.setLayoutX(punktyNaPlanszy.get(0).getLayoutX());
            kolo.setLayoutY(punktyNaPlanszy.get(0).getLayoutY());

            panelRysowania.getChildren().add(kolo);

            kolo.setOnMousePressed(e -> {
                promien = e.getSceneX() - kolo.getTranslateX();
                promien = e.getSceneY() - kolo.getTranslateY();
            });

            kolo.setOnMouseDragged(e -> {
                kolo.setTranslateX(e.getSceneX() - promien);
                kolo.setTranslateY(e.getSceneY() - promien);
            });

            kolo.setOnScroll(e -> {
                double zoom = 1.05;
                if (e.getDeltaY() < 0) {
                    zoom = 2.0 - zoom;
                }

                if (e.getDeltaY() > 0) {
                    kolo.setScaleX(kolo.getScaleX() * (-zoom));
                    kolo.setScaleY(kolo.getScaleY() * (-zoom));
                } else if (e.getDeltaY() < 0) {
                    kolo.setScaleX(kolo.getScaleX() * (zoom));
                    kolo.setScaleY(kolo.getScaleY() * (zoom));
                }
            });

            wyczyscPunktyNaPlanszy();
        }

        if (punktyNaPlanszy.toArray().length == 3 && wybranyKsztalt == Ksztalty.TROJKAT) {
            Polygon trojkat = new Polygon();
            trojkat.getPoints().addAll(punktyNaPlanszy.get(0).getLayoutX(), punktyNaPlanszy.get(0).getLayoutY(), punktyNaPlanszy.get(1).getLayoutX(), punktyNaPlanszy.get(1).getLayoutY(), punktyNaPlanszy.get(2).getLayoutX(), punktyNaPlanszy.get(2).getLayoutY());

            x1 = punktyNaPlanszy.get(0).getLayoutX();
            y1 = punktyNaPlanszy.get(0).getLayoutY();

            panelRysowania.getChildren().add(trojkat);
            wyczyscPunktyNaPlanszy();

            trojkat.setOnMousePressed(e -> {
                x1 = e.getSceneX() - trojkat.getTranslateX();
                y1 = e.getSceneY() - trojkat.getTranslateY();
            });

            trojkat.setOnMouseDragged(e -> {
                trojkat.setTranslateX(e.getSceneX() - x1);
                trojkat.setTranslateY(e.getSceneY() - y1);
            });

            trojkat.setOnScroll(e -> {
                double zoom = 1.05;
                if (e.getDeltaY() < 0) {
                    zoom = 2.0 - zoom;
                }

                if (e.getDeltaY() > 0) {
                    trojkat.setScaleX(trojkat.getScaleX() * (-zoom));
                    trojkat.setScaleY(trojkat.getScaleY() * (-zoom));
                } else if (e.getDeltaY() < 0) {
                    trojkat.setScaleX(trojkat.getScaleX() * (zoom));
                    trojkat.setScaleY(trojkat.getScaleY() * (zoom));
                }
            });
        }

    }

    private void wyczyscPunktyNaPlanszy() {
        panelRysowania.getChildren().removeAll(punktyNaPlanszy);
        punktyNaPlanszy.removeAll(punktyNaPlanszy);
    }

    private void czyDodacPunkt(MouseEvent e, boolean czyDodac) {
        if (czyDodac) {
            Circle punktNaPlanszy = new Circle();
            punktNaPlanszy.setLayoutX(e.getX());
            punktNaPlanszy.setLayoutY(e.getY());
            punktNaPlanszy.setRadius(1);
            punktNaPlanszy.setFill(Color.BLACK);
            panelRysowania.getChildren().add(punktNaPlanszy);
            punktyNaPlanszy.add(punktNaPlanszy);
        }
    }

    private void obrot(Rotate rotate) {
        Slider slider = new Slider(0, 360, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(90);
        slider.setBlockIncrement(10);
        slider.setOrientation(Orientation.VERTICAL);
        slider.setLayoutX(2);
        slider.setLayoutY(195);
        rotate.setPivotX(300);
        rotate.setPivotY(100);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                rotate.setAngle((double) newValue);
            }
        });
        panelRysowania.getChildren().;


    }
}

