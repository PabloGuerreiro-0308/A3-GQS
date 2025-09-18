package instrutor.controller;


import instrutor.service.InstrutorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instrutores")
public class InstrutorController {

    private final InstrutorService instrutorServices;
    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorServices = instrutorService;
    }


    

}
