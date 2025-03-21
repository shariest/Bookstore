package com.ryan.bookstore.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksEntity {

    @Id
    @Column(name = "bid", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Schema(description = "책 고유 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long bid;

    @NotNull
    @Column(name = "title", nullable = false)
    @Schema(description = "책 제목", example = "책 제목")
    @NotBlank
    private String title;

    @NotNull
    @Column(name = "author", nullable = false)
    @Schema(description = "저자", example = "저자")
    @NotBlank
    private String author;

    @NotNull
    @Column(name = "price", nullable = false)
    @Schema(description = "가격", example = "10000")
    private int price;

    @NotNull
    @Column(name = "cnt", nullable = false)
    @Schema(description = "수량", example = "1")
    private int cnt;
}