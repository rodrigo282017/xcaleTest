package com.xcale.xcaletest.schedule;

import com.xcale.xcaletest.model.api.CartDTO;
import com.xcale.xcaletest.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Scheduler component to periodically delete inactive carts.
 * This scheduler is responsible for periodically checking for inactive carts and deleting them.
 */
@Component
@RequiredArgsConstructor
public class CartInactivityScheduler {
    private static final long CART_DELETION_SCHEDULED_TIME = 60000;
    private static final int CART_DELETION_INTERVAL_MINUTES = 10;
    private final ICartService cartService;

    /**
     * Scheduled task to delete inactive carts.
     * This task runs at a fixed rate and deletes carts that have been inactive for a specified duration.
     * Inactivity is determined based on the timestamp recorded in the cart.
     */
    @Scheduled(fixedRate = CART_DELETION_SCHEDULED_TIME)
    // This will be called each 10 minutes... It is not exactly but it is approximate
    public void deleteInactiveCarts() {
        Instant time = Instant.now().minus(CART_DELETION_INTERVAL_MINUTES, ChronoUnit.MINUTES);
        List<CartDTO> inactiveCarts = cartService.findInactiveCarts(time);

        for (CartDTO cartDTO : inactiveCarts) {
            cartService.deleteCart(cartDTO.getId().toString());
        }
    }
}
