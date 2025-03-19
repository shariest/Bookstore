package com.ryan.bookstore.rest;

import com.ryan.bookstore.bean.BooksResponse;
import com.ryan.bookstore.bean.ResponseBody;
import com.ryan.bookstore.entity.BooksEntity;
import com.ryan.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 책 API 서비스 입니다.
 *
 * @version 1.0
 * @auther ryan
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books Rest API", description = "책 API")
public class BooksRestLet {

    private final BookService bookService;

    /**
     * 책 정보를 저장합니다.
     *
     * @param body
     * @return
     */
    @PostMapping
    @Operation(summary = "책 저장", description = "책 정보를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "책 저장",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BooksResponse.class)
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "오류 발생",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseBody.class)
                    ))
    })
    public ResponseEntity<Object> createBook(
            @Parameter @RequestBody @Valid BooksEntity body
    ) {
        long bid = bookService.createBook(body);
        return ResponseEntity.ok(
                BooksResponse.builder()
                        .bid(bid)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    /**
     * 책 정보를 조회합니다.
     *
     * @param bid
     * @return
     */
    @GetMapping("/{bid}")
    @Operation(summary = "책 조회", description = "책 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "bid", description = "Book ID", required = true, in = ParameterIn.PATH)
            })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "책 조회",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BooksEntity.class)
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "오류 발생",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseBody.class)
                    ))
    })
    public ResponseEntity<BooksEntity> retrieveBook(
            @PathVariable("bid") long bid
    ) {
        return ResponseEntity.ok(bookService.retrieveBook(bid));
    }

    /**
     * 책 제목과 저자로 검색합니다.
     *
     * @param keyword
     * @param pageable
     * @return
     */
    @GetMapping
    @Operation(summary = "책 검색", description = "책 제목과 저자로 검색합니다.",
            parameters = {
                    @Parameter(
                            name = "keyword",
                            description = "검색 키워드 (제목/저자)",
                            in = ParameterIn.QUERY,
                            example = "제목"
                    ),
                    @Parameter(
                            name = "page",
                            description = "페이지 (0부터 시작)",
                            in = ParameterIn.QUERY,
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "페이지 당 항목 수",
                            in = ParameterIn.QUERY,
                            example = "10"
                    ),
                    @Parameter(
                            name = "sort",
                            description = "정렬 (컬럼 명,asc|desc)",
                            in = ParameterIn.QUERY,
                            example = "bid,desc"
                    )
            })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "책 조회",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = BooksEntity.class)
                            )
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "오류 발생",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseBody.class)
                    ))
    })
    public ResponseEntity<Page<BooksEntity>> searchBooks(
            @Parameter(hidden = true) @RequestParam(required = false) String keyword,
            @Parameter(hidden = true) @PageableDefault(sort = "bid", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.searchBooks(keyword, pageable));
    }

    /**
     * 책 정보를 수정합니다.
     *
     * @param bid
     * @param body
     * @return
     */
    @PutMapping("/{bid}")
    @Operation(summary = "책 수정", description = "책 정보를 수정합니다.",
            parameters = {
                    @Parameter(name = "bid", description = "Book ID", required = true, in = ParameterIn.PATH)
            })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "책 수정",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseBody.class)
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "오류 발생",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseBody.class)
                    ))
    })
    public ResponseEntity<ResponseBody> updateBook(
            @PathVariable("bid") long bid,
            @Parameter @RequestBody @Valid BooksEntity body
    ) {
        bookService.updateBook(
                body.setBid(bid)
        );
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    /**
     * 책을 삭제합니다.
     *
     * @param bid
     * @return
     */
    @DeleteMapping("/{bid}")
    @Operation(summary = "책 삭제", description = "책을 삭제합니다.",
            parameters = {
                    @Parameter(name = "bid", description = "Book ID", required = true, in = ParameterIn.PATH)
            })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "책 삭제",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseBody.class)
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "오류 발생",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseBody.class)
                    ))
    })
    public ResponseEntity<ResponseBody> deleteBook(
            @PathVariable("bid") long bid
    ) {
        bookService.deleteBook(bid);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}