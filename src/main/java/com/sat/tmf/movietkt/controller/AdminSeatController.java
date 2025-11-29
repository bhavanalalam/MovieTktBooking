package com.sat.tmf.movietkt.controller.admin;

import com.sat.tmf.movietkt.entities.TemplateSeat;
import com.sat.tmf.movietkt.service.TemplateSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/seats")
public class AdminSeatController {

    @Autowired
    private TemplateSeatService templateSeatService;

    @PostMapping("/save")
    public String saveSeats(
            @RequestParam Integer templateId,
            @RequestParam List<Integer> ids,
            @RequestParam List<String> codes,
            @RequestParam List<String> types
    ) {
        List<TemplateSeat> seats = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            TemplateSeat seat = templateSeatService.findById(ids.get(i));
            seat.setSeatCode(codes.get(i));
            seat.setSeatType(types.get(i));
            seats.add(seat);
        }

        templateSeatService.saveAllSeats(seats);

        return "redirect:/admin/templates/edit/" + templateId;
    }
}
