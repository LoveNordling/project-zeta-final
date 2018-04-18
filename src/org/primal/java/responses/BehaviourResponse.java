package org.primal.java.responses;

import org.primal.java.entity.Animal;

public class BehaviourResponse {

    public BehaviourResponse() {

    }

    public final void applyEffects(Animal animal) {
        effects(animal);
    }

    // Override this when you initilize responses
    public void effects(Animal animal) {
    }
}