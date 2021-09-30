package com.erlandumah.example.dicesample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class DiceController {

    @Autowired
    private DiceRollTotalRepository diceRollTotalRepository;

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
            // Store the result in our database
            DiceRollTotalID diceRollTotalID = new DiceRollTotalID(nDice, nSides);
            Optional<DiceRollTotal> diceRollTotalOptional = diceRollTotalRepository.findById(diceRollTotalID);
            if(diceRollTotalOptional.isPresent()) {
                DiceRollTotal diceRollTotal = diceRollTotalOptional.get();
                diceRollTotal.setTotalSimulations(diceRollTotal.getTotalSimulations() + 1);
                diceRollTotal.setTotalRolls(diceRollTotal.getTotalRolls() + (long)nRolls);
                for(HashMap.Entry<Long, Integer> individualroll: result.entrySet()) {
                    Integer previousTotal = diceRollTotal.getIndividualRolls().get(individualroll.getKey());
                    if(previousTotal == null) {
                        diceRollTotal.getIndividualRolls().put(individualroll.getKey(), individualroll.getValue());
                    } else {
                        diceRollTotal.getIndividualRolls().put(individualroll.getKey(), previousTotal + individualroll.getValue());
                    }
                }
                diceRollTotalRepository.save(diceRollTotal);
            } else {
                DiceRollTotal diceRollTotal = new DiceRollTotal(diceRollTotalID, 1, nRolls, result);
                diceRollTotalRepository.save(diceRollTotal);
            }
            return ResponseEntity.ok(result);
        }
        catch(NumberOfDiceBelowMinimumException | NumberOfRollsBelowMinimumException | NumberOfSidesBelowMinimumException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/dicerolltotal")
    public ResponseEntity dicerolltotal(@RequestParam int nSides,
                                        @RequestParam int nDice) {
        DiceRollTotalID id = new DiceRollTotalID(nDice, nSides);

        Optional<DiceRollTotal> diceRollTotalOptional = diceRollTotalRepository.findById(id);

        if(diceRollTotalOptional.isPresent()) {
            return ResponseEntity.ok(diceRollTotalOptional.get());
        } else {
            return ResponseEntity.ok("No data available");
        }
    }

    @DeleteMapping("/dicerolltotal")
    public void dicerolltotalDelete(@RequestParam int nSides,
                                    @RequestParam int nDice) {
        DiceRollTotalID id = new DiceRollTotalID(nDice, nSides);

        diceRollTotalRepository.deleteById(id);
    }

    @GetMapping("/dicerolltotalpercentage")
    public ResponseEntity dicerolltotalpercentage(@RequestParam int nSides,
                                                  @RequestParam int nDice) {
        DiceRollTotalID id = new DiceRollTotalID(nDice, nSides);

        Optional<DiceRollTotal> diceRollTotalOptional = diceRollTotalRepository.findById(id);

        if(diceRollTotalOptional.isPresent()) {
            DiceRollTotalPercentage diceRollTotalPercentage = DiceRollTotalPercentage.createFrom(diceRollTotalOptional.get());
            return ResponseEntity.ok(diceRollTotalPercentage);
        } else {
            return ResponseEntity.ok("No data available");
        }
    }
}
