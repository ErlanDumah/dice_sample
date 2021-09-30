package com.erlandumah.example.dicesample;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DiceRollTotalRepository extends CrudRepository<DiceRollTotal, DiceRollTotalID> {
    public Optional<DiceRollTotal> findById(DiceRollTotalID id);
}
