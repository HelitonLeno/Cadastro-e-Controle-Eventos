package com.app.app.controller;

import com.app.app.model.Convidado;
import com.app.app.model.Evento;
import com.app.app.repository.ConvidadoRepository;
import com.app.app.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@org.springframework.stereotype.Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public String form() {
        return "FormEvento";
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult result,
                       RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "FormEvento";
        }

        eventoRepository.save(evento);
        return "FormEvento";
    }

    @RequestMapping("/")
    public ModelAndView listaEventos() {
        ModelAndView mav = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        mav.addObject("eventos", eventos);
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("id") long id) {
        Evento evento = eventoRepository.findById(id);
        ModelAndView mav = new ModelAndView("DetalhesEvento");
        mav.addObject("id", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        mav.addObject("convidados", convidados);
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("id") long id, @Valid Convidado convidado, BindingResult result,
                                     RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{id}";
        }

        Evento evento = eventoRepository.findById(id);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso");

        return "redirect:/{id}";
    }

    @RequestMapping("/deletar")
    public String deletarEvento(long id) {
        Evento evento = eventoRepository.findById(id);
        eventoRepository.delete(evento);

        return "redirect:/";
    }

    @RequestMapping("deletar_convidado")
    public String deletarConvidado(String rg) {
        Convidado convidado = convidadoRepository.findByRg(rg);
        convidadoRepository.delete(rg);

        Evento evento = convidado.getEvento();
        String condigoLong = String.valueOf(evento.getId());
        return "redirect:/" + condigoLong;
    }
}