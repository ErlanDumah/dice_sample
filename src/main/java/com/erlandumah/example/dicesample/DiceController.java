package com.erlandumah.example.dicesample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class DiceController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello REST user :)";
    }

    // A REST controller that causes an endpoint state to be changed isn't a GET.
    @PostMapping("/dice")
    public ResponseEntity dice(@RequestParam int nSides,
                               @RequestParam int nRolls,
                               @RequestParam int nDice) {
        try {
            // Do a dice roll
            DiceRoll diceRoll = DiceRoll.create(nSides, nRolls, nDice);
            HashMap<Long, Integer> result = diceRoll.execute();
            return ResponseEntity.ok(result);
        }
        catch(NumberOfDiceBelowMinimumException | NumberOfRollsBelowMinimumException | NumberOfSidesBelowMinimumException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
