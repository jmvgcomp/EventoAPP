package dev.jmvg.eventoapp.controllers;

import dev.jmvg.eventoapp.model.Convidados;
import dev.jmvg.eventoapp.model.Evento;
import dev.jmvg.eventoapp.repository.ConvidadoRepository;
import dev.jmvg.eventoapp.repository.EventoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventoController {

    private final EventoRepository eventoRepository;
    private final ConvidadoRepository convidadoRepository;
    public EventoController(EventoRepository eventoRepository, ConvidadoRepository convidadoRepository) {
        this.eventoRepository = eventoRepository;
        this.convidadoRepository = convidadoRepository;
    }

    @GetMapping(value = "/cadastrarEvento")
    public String form(){
        return "evento/formEvento";
    }

    @PostMapping(value = "/cadastrarEvento")
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/cadastrarEvento";
        }
        eventoRepository.save(evento);

        attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping(value = "/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @GetMapping("/{codigo}")
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidados> convidados = convidadoRepository.findByEvento(evento);
        mv.addObject("convidados", convidados);
        return mv;
    }

    @PostMapping("/{codigo}")
    public String adicionaConvidado(@PathVariable("codigo") long codigo, @Valid Convidados convidados, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{codigo}";
        }
        Evento evento = eventoRepository.findByCodigo(codigo);
        convidados.setEvento(evento);
        convidadoRepository.save(convidados);
        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
        return "redirect:/{codigo}";
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo, RedirectAttributes attributes){
        Evento evento = eventoRepository.findByCodigo(codigo);
        Iterable<Convidados> convidados = convidadoRepository.findByEvento(evento);
        if(convidados != null){

            attributes.addFlashAttribute("mensagem", "Erro ao deletar, verifique se não há convidados na lista de eventos");
            return "redirect:/eventos";
        }
        eventoRepository.delete(evento);
        attributes.addFlashAttribute("mensagem", "Evento deletado com sucesso!");
        return "redirect:/eventos";
    }
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(long id){
        Convidados convidados = convidadoRepository.findById(id);
        convidadoRepository.delete(convidados);

        Evento evento = convidados.getEvento();
        long codigo = evento.getCodigo();
        String codigoEvento = ""+codigo;
        return "redirect:/"+codigoEvento;
    }
}
