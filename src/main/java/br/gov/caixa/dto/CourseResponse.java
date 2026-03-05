package br.gov.caixa.dto;

import java.util.List;

public record CourseResponse(Long id, String name, List<LessonResponse> lessons) {
}
