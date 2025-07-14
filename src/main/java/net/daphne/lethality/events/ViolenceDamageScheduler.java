package net.daphne.lethality.events;

import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class ViolenceDamageScheduler {
    public static final List<ScheduledViolence> scheduled = new ArrayList<>();
    private static final List<ScheduledViolence> toAdd = new ArrayList<>();

    public static void schedule(LivingEntity target, float damage, int ticks) {
        UUID uuid = target.getUUID();

        // Verificar si ya existe una tarea pendiente para este target
        boolean alreadyScheduled = scheduled.stream().anyMatch(v -> v.targetUUID.equals(uuid))
                || toAdd.stream().anyMatch(v -> v.targetUUID.equals(uuid));

        if (!alreadyScheduled) {
            toAdd.add(new ScheduledViolence(uuid, damage, ticks));
        }
    }

    public static void flushNew() {
        scheduled.addAll(toAdd);
        toAdd.clear();
    }
}


