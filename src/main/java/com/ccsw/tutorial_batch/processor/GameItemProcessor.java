package com.ccsw.tutorial_batch.processor;

import com.ccsw.tutorial_batch.model.Game;
import com.ccsw.tutorial_batch.model.GameAvailability;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class GameItemProcessor implements ItemProcessor<Game, GameAvailability> {
    @Override
    public GameAvailability process(Game game) {
        System.out.println("Processing game: " + game);
        String hasStock = game.getStock() > 0 ? "available" : "NOT available";
        return new GameAvailability(game.getTitle(), hasStock);
    }
}
