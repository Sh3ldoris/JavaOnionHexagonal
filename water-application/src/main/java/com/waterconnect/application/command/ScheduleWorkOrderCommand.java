package com.waterconnect.application.command;

import java.time.LocalDate;
import java.util.UUID;

public record ScheduleWorkOrderCommand(UUID workOrderId, LocalDate date, String team) {
}
