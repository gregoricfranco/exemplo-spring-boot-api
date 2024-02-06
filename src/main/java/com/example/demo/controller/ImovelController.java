package com.example.demo.controller;

import com.example.demo.dto.ImovelDTO;
import com.example.demo.model.Imovel;
import com.example.demo.service.ImagemService;
import com.example.demo.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imovel")
public class ImovelController {

    private final ImovelService imovelService;
    private final ImagemService imagemService;

    @Autowired
    public ImovelController(ImovelService imovelService, ImagemService imagemService) {
        this.imovelService = imovelService;
        this.imagemService = imagemService;
    }


    @GetMapping("/")
    public List<ImovelDTO> all(){
        List<ImovelDTO> imoveis = imovelService.listarTodosImoveis()
                .stream()
                .map(ImovelDTO::new)
                .toList();
        return imoveis;
    }

    @PostMapping("/")
    public Imovel create(@RequestBody ImovelDTO imovelDTO){
        Imovel imovel = new Imovel(imovelDTO);
        return imovelService.cadastrarImovel(imovel);
    }
}
