package main;

import models.Model;
import view.View;

public class Main {

    public static void main(String[] args){
        Model model = new Model();
        View delegate = new View(model);
    }
}
