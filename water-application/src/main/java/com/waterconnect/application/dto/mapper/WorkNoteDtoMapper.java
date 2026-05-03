package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.WorkNoteDto;
import com.waterconnect.domain.model.valueobject.WorkNote;

public class WorkNoteDtoMapper {

    private WorkNoteDtoMapper() {}

    public static WorkNoteDto toDto(WorkNote workNote) {
        return new WorkNoteDto(
                workNote.content(),
                workNote.author(),
                workNote.createdAt()
        );
    }
}
