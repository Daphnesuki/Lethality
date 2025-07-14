package net.daphne.lethality.events;

import java.util.UUID;

public class ScheduledViolence {
    public final UUID targetUUID;
    public final float damage;
    public int ticksRemaining;

    public ScheduledViolence(UUID targetUUID, float damage, int delayTicks) {
        this.targetUUID = targetUUID;
        this.damage = damage;
        this.ticksRemaining = delayTicks;
    }
}
