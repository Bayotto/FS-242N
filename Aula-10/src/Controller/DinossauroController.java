package Controller;

import Model.DAO.DinoDAO;
import Model.Dino;

import java.util.List;

public class DinossauroController {
    private DinoDAO dinossauroDAO;

    public DinossauroController() {
        this.dinossauroDAO = new DinoDAO();
    }

    public void cadastrarDinossauro(String nome, String especie, String dieta, String idade_estimada, String idade, String status) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do dinossauro é obrigatório.");
        }
        if (especie == null || especie.trim().isEmpty()) {
            throw new Exception("A espécie do dinossauro é obrigatória.");
        }
        if (dieta == null || dieta.trim().isEmpty()) {
            throw new Exception("Dieta é obrigatória");
        }
        if (idade_estimada == null || idade_estimada.trim().isEmpty()) {
            throw new Exception("idade estimada é obrigatória");
        }
        if (idade == null || idade.trim().isEmpty()) {
            throw new Exception("idade é obrigatória");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new Exception("status é obrigatória");
        }
        Dino dinossauro = new Dino(nome, especie, dieta, idade_estimada, idade, status);
        dinossauroDAO.inserir(dinossauro);
    }

    public Dino buscarDinossauroPorId(int id) {
        return dinossauroDAO.buscarPorId(id);
    }

    public void atualizarDinossauro(int id, String nome, String especie, String dieta, String idade_estimada, String idade, String status) throws Exception {
        if (nome == null || nome.trim().isEmpty() || especie == null || especie.trim().isEmpty() || dieta.trim().isEmpty() || idade_estimada.trim().isEmpty()  || idade.trim().isEmpty()|| status.trim().isEmpty()  ) {
            throw new Exception("Todos os campos do dinossauro são obrigatórios e devem ser válidos.");
        }
        Dino dinossauro = new Dino(id, nome, especie, dieta, idade_estimada, idade, status);
        dinossauroDAO.updateDinossauro(dinossauro);
    }


    public List<Dino> listarTodosDinossauros() {
        return dinossauroDAO.listarTodos();
    }


    public void removerDinossauro(int id) {
        dinossauroDAO.remover(id);
    }


    public List<Dino> buscarDinossauroPorNome(String nome) {
        return dinossauroDAO.buscarPorNome(nome);
    }




}
