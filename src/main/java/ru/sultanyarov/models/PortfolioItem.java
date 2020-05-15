package ru.sultanyarov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class PortfolioItem {
    private final String repoName;
    private final String url;
    private final String about;
    private final Date lastModify;
}
