import Dao.dinoDAO;
import Model.dino;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("\n--- Inserindo  ---");
        dino mosassauro = new dino("Mosassauro", "Mosasauridae", "Herbívoro", 68, 12, "Seguro");
        dino quetzalcoatlus = new dino("Quetzalcoatlus", "Quetzalcoatlus northropi", "Piscívoro", 68, 7, "Seguro");
        dino velociraptor = new dino("Velociraptors", "Velociraptor mongoliensis", "Carnívoro", 75, 5, "Contido");

        dinoDAO.setDinossauro(mosassauro);
        dinoDAO.setDinossauro(quetzalcoatlus);
        dinoDAO.setDinossauro(velociraptor);
//
//        System.out.println("\n--- Listando Todos ---");
//        List<Dino> listaDinos = dinoDAO.getDinossauro();
//        System.out.println("\n--- Listando Carnívoros ---");
//        List<Dino> dietaDinos = dinoDAO.getDieta("Carnívoro");
//        System.out.println("\n--- Listando Status Contido ---");
//        List<Dino> statusDinos = dinoDAO.getStatus("Contido");
//
//        System.out.println("\n--- Testando Atualização ---");
//        Dino mosassauroUpdate = new Dino(1, "Mosassauro", "Mosasauridae", "Carnívoro", 68, 12, "Seguro");
//        dinoDAO.updateDinossauro(mosassauroUpdate);
//        Dino velociraptorUpdate = new Dino(3, "Velociraptors", "Velociraptor mongoliensis", "Carnívoro", 75, 5, "Seguro");
//        dinoDAO.updateDinossauro(velociraptorUpdate);
//        System.out.println("\n--- Listando Todos ---");
//        List<Dino> listaDinos = dinoDAO.getDinossauro();
//        System.out.println("\n--- Listando Carnívoros ---");
//        List<Dino> dietaDinos = dinoDAO.getDieta("Carnívoro");

        dino brach = new dino(4,"Brachiosaurus", "Brachiosaurus altithorax", "Herbívoro", 155, 20, "Seguro");
        dino trex = new dino(5,"T-Rex", "Tyrannosaurus Rex", "Carnívoro", 66, 15, "Em fuga");
        dino diloph = new dino(6,"Dilophosaurus", "Dilophosaurus Wetherilli", "Carnívoro", 193, 8, "Contido");

        System.out.println("\n--- Listando Todos ---");
        List<dino> listaDinos = dinoDAO.getDinossauro();
        dinoDAO.setDinossauro(brach);
        dinoDAO.setDinossauro(trex);
        dinoDAO.setDinossauro(diloph);
        dinoDAO.getDinossauro();

        System.out.println("\n--- Extinguindo Dinossauro(s) ---");
        dinoDAO.removerDinossauro(trex,5);
        dinoDAO.getDinossauro();
    }
}