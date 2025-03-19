package com.ryan.bookstore.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BooksResponse extends ResponseBody {
    @Schema(description = "책 ID", example = "1")
    private long bid;
}