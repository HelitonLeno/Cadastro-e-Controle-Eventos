package com.app.app.controller;

import com.app.app.model.Convidado;
import com.app.app.model.Evento;
import com.app.app.repository.ConvidadoRepository;
import com.app.app.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public String form() {
        return "/evento/FormEvento";
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String form(Evento evento) {
        eventoRepository.save(evento);
        return "redirect:/cadastro";
    }

    @RequestMapping("/eventos")
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
        mav.addObject("idEvento", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        mav.addObject("convidados", convidados);
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("id") long id, Convidado convidado) {
        Evento evento = eventoRepository.findById(id);
        convidado.setEvento(evento);
        System.out.println(convidadoRepository.count());
        convidadoRepository.save(convidado);
        return "redirect:/{id}";
    }
}